package pe.com.bcp.guidelineunittest.presentation.users.adapter

import com.airbnb.epoxy.TypedEpoxyController
import pe.com.bcp.guidelineunittest.presentation.users.adapter.model.UserItemModel_
import pe.com.bcp.guidelineunittest.presentation.users.vo.UserListItemVO

class UserController(
    private val itemClickedListener: (UserListItemVO) -> Unit = {},
) :
    TypedEpoxyController<List<UserListItemVO>>() {

    override fun buildModels(employees: List<UserListItemVO>) {
        for (item in employees) {
            UserItemModel_()
                .id(item.id.toString())
                .item(item)
                .itemClickedListener(itemClickedListener)
                .addTo(this)
        }
    }
}