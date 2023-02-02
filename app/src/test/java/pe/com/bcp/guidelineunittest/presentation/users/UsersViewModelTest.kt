package pe.com.bcp.guidelineunittest.presentation.users

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import pe.com.bcp.guidelineunittest.core.BaseViewModelTest
import pe.com.bcp.guidelineunittest.core.CaptureObservableField
import pe.com.bcp.guidelineunittest.core.MainCoroutineRule
import pe.com.bcp.guidelineunittest.domain.usecase.MockGetUsersUseCase
import pe.com.bcp.guidelineunittest.utils.DummyValuesVO

class UsersViewModelTest : BaseViewModelTest() {

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    lateinit var getPeoplesUseCase: MockGetUsersUseCase

    private lateinit var viewModel: UsersViewModel

    @Before
    @Throws(Exception::class)
    fun setUp() {
        getPeoplesUseCase = MockGetUsersUseCase()
        viewModel = UsersViewModel(getPeoplesUseCase)
    }

    @Test
    fun `given users when populate then verify interactions`() {
        //given
        val captureObservableField = CaptureObservableField<String>()
        viewModel.contentState.addOnPropertyChangedCallback(captureObservableField)
        viewModel.setUsers(DummyValuesVO.users())

        //when
        viewModel.populate()

        //then
        if (getPeoplesUseCase.runExactly == 0) {
            throw Exception("No se visito getPeoplesUseCase")
        }
        if (0 == captureObservableField.capture.size) {
            throw Exception("No se son iguales")
        }
    }

    @Test
    fun `given empty users when populate then verify interactions`() {
        //given
        val captureObservableField = CaptureObservableField<String>()
        viewModel.contentState.addOnPropertyChangedCallback(captureObservableField)

        //when
        viewModel.populate()

        //then
        if (getPeoplesUseCase.runExactly == 0) {
            throw Exception("No se visito getPeoplesUseCase")
        }
        if (0 == captureObservableField.capture.size) {
            throw Exception("El content state no es igual")
        }
        if (UsersState.LOADING != captureObservableField.capture[0]) {
            throw Exception("El primer estado no es LOADING")
        }
    }

}