package pe.com.bcp.guidelineunittest.presentation.users

import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import io.mockk.spyk
import io.mockk.verify
import org.junit.Assert
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import pe.com.bcp.guidelineunittest.commons.utils.Either
import pe.com.bcp.guidelineunittest.core.BaseViewModelTest
import pe.com.bcp.guidelineunittest.core.CaptureObservableField
import pe.com.bcp.guidelineunittest.core.CaptureObserver
import pe.com.bcp.guidelineunittest.domain.usecase.GetUsersUseCase
import pe.com.bcp.guidelineunittest.exception.Failure
import pe.com.bcp.guidelineunittest.presentation.users.UsersState.CONTENT
import pe.com.bcp.guidelineunittest.presentation.users.UsersState.EMPTY
import pe.com.bcp.guidelineunittest.presentation.users.UsersState.ERROR
import pe.com.bcp.guidelineunittest.presentation.users.UsersState.LOADING
import pe.com.bcp.guidelineunittest.presentation.users.vo.UserListItemVO
import pe.com.bcp.guidelineunittest.presentation.users.vo.toViewObject
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
    fun `given init viewModel when populate then verify`() {
        //given
        val captureContentState = CaptureObservableField<String>()
        viewModel.contentState.addOnPropertyChangedCallback(captureContentState)

        //when
        viewModel.populate()

        //then
        verify { getPeoplesUseCase(any(), any(), any()) }
        Assert.assertEquals(listOf(LOADING), captureContentState.capture)
    }

    @Test
    fun `given users when populate then verify`() {
        //given
        val captureContentState = CaptureObservableField<String>()
        viewModel.contentState.addOnPropertyChangedCallback(captureContentState)
        viewModel.setUsers(FakeValuesVO.users())

        //when
        viewModel.populate()

        //then
        verify { getPeoplesUseCase(any(), any(), any()) }
        Assert.assertEquals(listOf<String>(), captureContentState.capture)
    }

    @Test
    fun `given empty users when populate then verify`() {
        //given
        val captureContentState = CaptureObservableField<String>()
        viewModel.contentState.addOnPropertyChangedCallback(captureContentState)

        //when
        viewModel.populate()

        //then
        verify { getPeoplesUseCase(any(), any(), any()) }
        Assert.assertEquals(listOf(LOADING), captureContentState.capture)
    }

    @Test
    fun `given viewModel when refresh then verify`() {
        //given
        val captureObservableField = CaptureObservableField<String>()
        viewModel.contentState.addOnPropertyChangedCallback(captureObservableField)

        //when
        viewModel.refresh()

        //then
        verify { viewModel.populate() }
    }

    @Test
    fun `given users success result when handleGetUsersUseCaseResult then verify`() {
        //given
        val captureContentState = CaptureObservableField<String>()
        viewModel.contentState.addOnPropertyChangedCallback(captureContentState)
        val observerUsers = CaptureObserver<List<UserListItemVO>>()
        val observerIsLoading = CaptureObserver<Boolean>()
        viewModel.users.observeForever(observerUsers)
        viewModel.isLoading.observeForever(observerIsLoading)
        val usersEntity = FakeValuesEntity.users()

        //when
        viewModel.handleGetUsersUseCaseResult(
            Either.Right(usersEntity)
        )

        //then
        Assert.assertEquals(listOf(CONTENT), captureContentState.capture)
        Assert.assertEquals(listOf(usersEntity.map { it.toViewObject() }), observerUsers.capture)
        Assert.assertEquals(listOf(false), observerIsLoading.capture)
    }

    @Test
    fun `given empty success result when handleGetUsersUseCaseResult then verify`() {
        //given
        val captureContentState = CaptureObservableField<String>()
        viewModel.contentState.addOnPropertyChangedCallback(captureContentState)
        val observerUsers = CaptureObserver<List<UserListItemVO>>()
        val observerIsLoading = CaptureObserver<Boolean>()
        viewModel.users.observeForever(observerUsers)
        viewModel.isLoading.observeForever(observerIsLoading)

        //when
        viewModel.handleGetUsersUseCaseResult(
            Either.Right(listOf())
        )

        //then
        Assert.assertEquals(listOf(EMPTY), captureContentState.capture)
        Assert.assertEquals(listOf<List<UserListItemVO>>(listOf()), observerUsers.capture)
        Assert.assertEquals(listOf(false), observerIsLoading.capture)
    }

    @Test
    fun `given get users failed result then handleGetUsersUseCaseResult then verify`() {
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
        Assert.assertEquals(listOf(Failure.ServerError), observerFailure.capture)
        Assert.assertEquals(listOf(ERROR), captureContentStateObservableField.capture)
        verify { viewModel.handleFailure(any()) }
        Assert.assertEquals(0, observerPayload.capture.size)
        Assert.assertEquals(listOf(false), observerIsLoading.capture)
    }

    @Test
    fun `given loaded users then handleItemPressed then verify`() {
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