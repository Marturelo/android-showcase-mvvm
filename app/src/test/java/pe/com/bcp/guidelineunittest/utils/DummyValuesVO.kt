package pe.com.bcp.guidelineunittest.utils

import pe.com.bcp.guidelineunittest.presentation.users.vo.UserListItemVO

object DummyValuesVO {
    fun user(): UserListItemVO {
        return UserListItemVO(
            -1,
            "Fake",
            "Fake",
        )
    }

    fun users(): List<UserListItemVO> {
        return (0..10).map { user() }
    }
}