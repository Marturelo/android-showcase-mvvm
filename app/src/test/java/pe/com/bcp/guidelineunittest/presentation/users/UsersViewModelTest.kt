package pe.com.bcp.guidelineunittest.presentation.users

import androidx.lifecycle.MutableLiveData
import io.mockk.MockKAnnotations
import io.mockk.every
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

}