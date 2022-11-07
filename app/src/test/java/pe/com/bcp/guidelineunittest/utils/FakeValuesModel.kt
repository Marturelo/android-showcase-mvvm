package pe.com.bcp.guidelineunittest.utils

import pe.com.bcp.guidelineunittest.data.model.UserModel

object FakeValuesModel {
    fun user(): UserModel {
        return UserModel(
            avatarUrl = "Fake",
            id = -1,
            login = "Fake",
        )
    }

    fun users(): List<UserModel> {
        return (0..10).map { user() }
    }
}