package pe.com.bcp.guidelineunittest.presentation.details

import io.mockk.MockKAnnotations
import io.mockk.spyk
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import pe.com.bcp.guidelineunittest.core.BaseViewModelTest
import pe.com.bcp.guidelineunittest.core.CaptureObservableField
import pe.com.bcp.guidelineunittest.core.CaptureObserver
import pe.com.bcp.guidelineunittest.utils.FakeValuesVO


class UserDetailsViewModelTest : BaseViewModelTest() {

    private lateinit var viewModel: UserDetailsViewModel

    @Before
    @Throws(Exception::class)
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = spyk(UserDetailsViewModel())
    }

    @Test
    fun notNullViewModel() {
        Assert.assertNotNull(viewModel)
    }

    @Test
    fun `given userListItem when initWithPeople then verify`() {
        //given
        val observerAvatarUrl = CaptureObserver<String>()
        viewModel.avatarUrl.observeForever(observerAvatarUrl)
        val observerLogin = CaptureObservableField<String>()
        viewModel.login.addOnPropertyChangedCallback(observerLogin)
        val userListItemVO = FakeValuesVO.user()

        //when
        viewModel.initWithPeople(userListItemVO)

        //then
        Assert.assertEquals(listOf(userListItemVO.avatarUrl), observerAvatarUrl.capture)
        Assert.assertEquals(listOf(userListItemVO.login), observerLogin.capture)
    }

    @Test
    fun `given viewModel when handleBackNavigatorPressed then verify`() {
        //given
        val observerGoToBack = CaptureObserver<Any>()
        viewModel.goToBack.observeForever(observerGoToBack)

        //when
        viewModel.handleBackNavigatorPressed()

        //then
        Assert.assertEquals(1, observerGoToBack.capture.size)
    }
}
