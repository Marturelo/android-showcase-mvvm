package pe.com.bcp.guidelineunittest.presentation.details

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pe.com.bcp.guidelineunittest.presentation.core.SingleLiveEvent
import pe.com.bcp.guidelineunittest.presentation.users.vo.UserListItemVO
import javax.inject.Inject

class UserDetailsViewModel @Inject constructor() : ViewModel() {

    private val _goToBack: SingleLiveEvent<Any> = SingleLiveEvent()
    val goToBack: LiveData<Any> = _goToBack

    val login = ObservableField<String>()

    private val _avatarUrl = MutableLiveData<String>()
    val avatarUrl: LiveData<String> = _avatarUrl

    fun initWithPeople(user: UserListItemVO) {
        login.set(user.login)
        _avatarUrl.value = user.avatarUrl
    }

    fun handleBackNavigatorPressed() {
        _goToBack.value = Any()
    }
}