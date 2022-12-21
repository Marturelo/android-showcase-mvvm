package pe.com.bcp.guidelineunittest.presentation.users

import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import io.mockk.spyk
import io.mockk.verify
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import pe.com.bcp.guidelineunittest.commons.utils.Either
import pe.com.bcp.guidelineunittest.core.BaseViewModelTest
import pe.com.bcp.guidelineunittest.core.CaptureObservableField
import pe.com.bcp.guidelineunittest.core.CaptureObserver
import pe.com.bcp.guidelineunittest.domain.usecase.GetUsersUseCase
import pe.com.bcp.guidelineunittest.exception.Failure
import pe.com.bcp.guidelineunittest.presentation.users.UsersState.ERROR
import pe.com.bcp.guidelineunittest.presentation.users.vo.UserListItemVO
import pe.com.bcp.guidelineunittest.utils.FakeValuesEntity
import pe.com.bcp.guidelineunittest.utils.FakeValuesVO

class UsersViewModelTest : BaseViewModelTest() {
    @MockK(relaxed = true)
    lateinit var getPeoplesUseCase: GetUsersUseCase

    private lateinit var viewModel: UsersViewModel

    @Before
    @Throws(Exception::class)
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = spyk(UsersViewModel(getPeoplesUseCase))
    }

    @Test
    fun notNullViewModel() {
        Assert.assertNotNull(viewModel)
    }

    @Test
    fun `given users when populate then verify`() {
        //given
        val captureObservableField = CaptureObservableField<String>()
        viewModel.contentState.addOnPropertyChangedCallback(captureObservableField)
        viewModel.setUsers(FakeValuesVO.users())

        //when
        viewModel.populate()

        //then
        verify { getPeoplesUseCase(any(), any(), any()) }
        Assert.assertEquals(0, captureObservableField.capture.size)
    }

    @Test
    fun `given empty users when populate then verify`() {
        //given
        val captureObservableField = CaptureObservableField<String>()
        viewModel.contentState.addOnPropertyChangedCallback(captureObservableField)

        //when
        viewModel.populate()

        //then
        verify { getPeoplesUseCase(any(), any(), any()) }
        Assert.assertEquals(1, captureObservableField.capture.size)
        Assert.assertEquals(UsersState.LOADING, captureObservableField.capture[0])
    }

    @Test
    fun `given viewModel when refresh when verify`() {
        //given
        val captureObservableField = CaptureObservableField<String>()
        viewModel.contentState.addOnPropertyChangedCallback(captureObservableField)

        //when
        viewModel.refresh()

        //then
        verify { viewModel.populate() }
    }

    @Test
    fun `given get users success result when handleGetProductsUseCaseResult then verify`() {
        //given
        val captureObservableField = CaptureObservableField<String>()
        viewModel.contentState.addOnPropertyChangedCallback(captureObservableField)
        val observerPayload = CaptureObserver<List<UserListItemVO>>()
        val observerIsLoading = CaptureObserver<Boolean>()
        viewModel.users.observeForever(observerPayload)
        viewModel.isLoading.observeForever(observerIsLoading)

        //when
        viewModel.handleGetUsersUseCaseResult(
            Either.Right(FakeValuesEntity.users())
        )

        //then
        Assert.assertEquals(1, captureObservableField.capture.size)
        Assert.assertEquals(1, observerPayload.capture.size)
        Assert.assertEquals(false, observerIsLoading.capture.first())
    }

    @Test
    fun `given get users failed result when handleGetProductsUseCaseResult then verify`() {
        //given
        val captureContentStateObservableField = CaptureObservableField<String>()
        viewModel.contentState.addOnPropertyChangedCallback(captureContentStateObservableField)
        val observerPayload = CaptureObserver<List<UserListItemVO>>()
        val observerIsLoading = CaptureObserver<Boolean>()
        val observerFailure = CaptureObserver<Failure>()
        viewModel.users.observeForever(observerPayload)
        viewModel.isLoading.observeForever(observerIsLoading)
        viewModel.failure.observeForever(observerFailure)

        //when
        viewModel.handleGetUsersUseCaseResult(
            Either.Left(Failure.ServerError)
        )

        //then
        Assert.assertEquals(1, captureContentStateObservableField.capture.size)
        Assert.assertEquals(1, observerFailure.capture.size)
        Assert.assertEquals(ERROR, captureContentStateObservableField.capture.first())
        verify { viewModel.handleFailure(any()) }
        Assert.assertEquals(0, observerPayload.capture.size)
        Assert.assertEquals(false, observerIsLoading.capture.first())
    }

    @Test
    fun `given loaded users when handleItemPressed then verify`() {
        //given
        val observerGoToDetails = CaptureObserver<UserListItemVO>()
        viewModel.goToDetails.observeForever(observerGoToDetails)
        val user = FakeValuesVO.user()

        //when
        viewModel.handleItemPressed(user)

        //then
        Assert.assertEquals(listOf(user), observerGoToDetails.capture)
    }

}