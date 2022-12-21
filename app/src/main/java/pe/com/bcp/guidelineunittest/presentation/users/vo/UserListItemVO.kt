package pe.com.bcp.guidelineunittest.presentation.users.vo

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import pe.com.bcp.guidelineunittest.domain.entity.UserEntity

@Parcelize
data class UserListItemVO(val id: Int, val login: String, val avatarUrl: String) : Parcelable

fun UserEntity.toVO(): UserListItemVO {
    return UserListItemVO(id, login, avatarUrl)
}