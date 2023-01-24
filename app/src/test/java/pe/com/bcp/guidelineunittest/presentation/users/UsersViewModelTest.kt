package pe.com.bcp.guidelineunittest.presentation.users

import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import io.mockk.spyk
import io.mockk.verify
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import pe.com.bcp.guidelineunittest.core.BaseViewModelTest
import pe.com.bcp.guidelineunittest.core.CaptureObservableField
import pe.com.bcp.guidelineunittest.domain.usecase.GetUsersUseCase
import pe.com.bcp.guidelineunittest.utils.FakeValuesVO

class UsersViewModelTest : BaseViewModelTest() {

    @MockK(relaxUnitFun = true)
    lateinit var getPeoplesUseCase: GetUsersUseCase

    private lateinit var viewModel: UsersViewModel

    @Before
    @Throws(Exception::class)
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = spyk(UsersViewModel(getPeoplesUseCase))
    }

    @Test
    fun `given users when populate then verify interactions`() {
        //given
        val captureObservableField = CaptureObservableField<String>()
        viewModel.contentState.addOnPropertyChangedCallback(captureObservableField)
        viewModel.setUsers(FakeValuesVO.users())

        //when
        viewModel.populate()

        //then
        verify { getPeoplesUseCase(any(), any(), any()) }
        Assert.assertEquals(captureObservableField.capture.size, 0)
    }

    @Test
    fun `given empty users when populate then verify interactions`() {
        //given
        val captureObservableField = CaptureObservableField<String>()
        viewModel.contentState.addOnPropertyChangedCallback(captureObservableField)

        //when
        viewModel.populate()

        //then
        verify { getPeoplesUseCase(any(), any(), any()) }
        Assert.assertEquals(captureObservableField.capture, listOf(UsersState.LOADING))
    }

    @Test
    fun `given users when refresh then verify interactions`() {
        //given
        val captureObservableField = CaptureObservableField<String>()
        viewModel.contentState.addOnPropertyChangedCallback(captureObservableField)

        //when
        viewModel.refresh()

        //then
        verify { viewModel.populate() }
    }

}