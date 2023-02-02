package pe.com.bcp.guidelineunittest.utils

import pe.com.bcp.guidelineunittest.domain.entity.UserEntity

object DummyValuesEntity {
    fun user(): UserEntity {
        return UserEntity(
            "Fake",
            -1,
            "Fake",
        )
    }

    fun users(): List<UserEntity> {
        return (0..10).map { user() }
    }
}