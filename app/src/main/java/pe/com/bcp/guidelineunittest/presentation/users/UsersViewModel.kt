package pe.com.bcp.guidelineunittest.presentation.users

import androidx.annotation.VisibleForTesting
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import pe.com.bcp.guidelineunittest.commons.utils.Either
import pe.com.bcp.guidelineunittest.commons.utils.getOrElse
import pe.com.bcp.guidelineunittest.commons.utils.onFailure
import pe.com.bcp.guidelineunittest.domain.core.UseCase
import pe.com.bcp.guidelineunittest.domain.entity.UserEntity
import pe.com.bcp.guidelineunittest.domain.usecase.GetUsersUseCase
import pe.com.bcp.guidelineunittest.exception.Failure
import pe.com.bcp.guidelineunittest.presentation.commons.StatefulLayout
import pe.com.bcp.guidelineunittest.presentation.core.SingleLiveEvent
import pe.com.bcp.guidelineunittest.presentation.users.vo.UserListItemVO
import pe.com.bcp.guidelineunittest.presentation.users.vo.toVO
import javax.inject.Inject

class UsersViewModel @Inject constructor(
    private val getUsersUseCase: GetUsersUseCase,
) : ViewModel() {

    private val _goToDetails: SingleLiveEvent<UserListItemVO> = SingleLiveEvent()
    val goToDetails: LiveData<UserListItemVO> = _goToDetails

    private val _users: MutableLiveData<List<UserListItemVO>> = MutableLiveData()
    val users: LiveData<List<UserListItemVO>> = _users

    private val _failure: MutableLiveData<Failure> = MutableLiveData()
    val failure: LiveData<Failure> = _failure

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading = _isLoading

    val contentState = ObservableField<String>()

    fun populate() {
        if (users.value.isNullOrEmpty()) {
            contentState.set(UsersState.LOADING)
        }
        getUsersUseCase(UseCase.None, viewModelScope, ::handleGetUsersUseCaseResult)
    }

    fun refresh() {
        this.populate()
    }

    @VisibleForTesting
    fun handleGetUsersUseCaseResult(result: Either<Failure, List<UserEntity>>) {
        if (result.isLeft) {
            contentState.set(UsersState.ERROR)
            result.onFailure {
                handleFailure(it)
            }
        } else {
            contentState.set(UsersState.CONTENT)
            val results = result.getOrElse(listOf())
            _users.value = results.map { it.toVO() }
        }
        _isLoading.value = false
    }

    @VisibleForTesting
    fun handleFailure(failure: Failure) {
        _failure.value = failure
    }

    @VisibleForTesting
    fun setUsers(users: List<UserListItemVO>) {
        _users.value = users
    }

    fun handleItemPressed(item: UserListItemVO) {
        _goToDetails.value = item
    }
}

object UsersState {
    const val CONTENT = StatefulLayout.State.CONTENT
    const val ERROR = "STATE_ERROR"
    const val LOADING = "STATE_LOADING"
}