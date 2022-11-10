package pe.com.bcp.guidelineunittest.presentation.details.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import pe.com.bcp.guidelineunittest.presentation.details.UserDetailsViewModel

@Suppress("UNCHECKED_CAST")
class UserDetailsViewModelFactory(
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return UserDetailsViewModel() as T
    }
}