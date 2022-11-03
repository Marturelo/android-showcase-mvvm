package pe.com.bcp.guidelineunittest.presentation.users

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import pe.com.bcp.guidelineunittest.R
import pe.com.bcp.guidelineunittest.databinding.FragmentFirstBinding
import pe.com.bcp.guidelineunittest.presentation.core.BaseFragment

class UsersFragment : BaseFragment<UsersViewModel>() {

    private var _binding: FragmentFirstBinding? = null

    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }

        viewModel.test()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}