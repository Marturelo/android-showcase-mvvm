package pe.com.bcp.guidelineunittest.presentation.users

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import pe.com.bcp.guidelineunittest.databinding.FragmentUsersBinding
import pe.com.bcp.guidelineunittest.exception.Failure
import pe.com.bcp.guidelineunittest.exception.failure
import pe.com.bcp.guidelineunittest.exception.observe
import pe.com.bcp.guidelineunittest.presentation.core.BaseFragment
import pe.com.bcp.guidelineunittest.presentation.users.adapter.UserController
import pe.com.bcp.guidelineunittest.presentation.users.vo.UserListItemVO

class UsersFragment : BaseFragment<UsersViewModel>() {

    private lateinit var controller: UserController

    private var _binding: FragmentUsersBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUsersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        with(viewModel) {
            observe(users, ::handleUsers)
            observe(isLoading, ::handleIsLoading)
            failure(failure, ::handleFailure)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.populate()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupRecyclerView() {
        controller = UserController()
        binding.rvProduct.adapter = controller.adapter
    }

    private fun handleUsers(values: List<UserListItemVO>?) {
        values ?: return
        controller.setData(values)
    }

    private fun handleFailure(failure: Failure?) {
        failure ?: return
    }

    private fun handleIsLoading(isLoading: Boolean?) {
        binding.srlUsers.isRefreshing = isLoading ?: false
    }
}