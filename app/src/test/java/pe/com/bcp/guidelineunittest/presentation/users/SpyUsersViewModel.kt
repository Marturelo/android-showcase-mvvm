package pe.com.bcp.guidelineunittest.presentation.users

import pe.com.bcp.guidelineunittest.domain.usecase.GetUsersUseCase
import javax.inject.Inject

open class SpyUsersViewModel @Inject constructor(
        getUsersUseCase: GetUsersUseCase,
) : UsersViewModel(getUsersUseCase) {

    var populateExactly = 0

    override fun populate() {
        populateExactly += 1
        super.populate()
    }
}

