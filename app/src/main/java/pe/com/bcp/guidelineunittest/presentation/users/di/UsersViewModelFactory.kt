package pe.com.bcp.guidelineunittest.presentation.users.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import pe.com.bcp.guidelineunittest.domain.usecase.GetUsersUseCase
import pe.com.bcp.guidelineunittest.presentation.users.UsersViewModel

@Suppress("UNCHECKED_CAST")
class UsersViewModelFactory(
    private val getUsersUseCase: GetUsersUseCase,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return UsersViewModel(getUsersUseCase) as T
    }
}