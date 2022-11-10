package pe.com.bcp.guidelineunittest.presentation.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import pe.com.bcp.guidelineunittest.databinding.FragmentUserDetailsBinding
import pe.com.bcp.guidelineunittest.exception.observe
import pe.com.bcp.guidelineunittest.ext.dp
import pe.com.bcp.guidelineunittest.presentation.details.di.UserDetailsViewModelFactory

class UserDetailsFragment : Fragment() {

    private val viewModel by viewModels<UserDetailsViewModel> {
        UserDetailsViewModelFactory()
    }

    val args: UserDetailsFragmentArgs by navArgs()

    private var _binding: FragmentUserDetailsBinding? = null

    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.initWithPeople(args.user)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel

        setupListeners()

        with(viewModel) {
            observe(avatarUrl, ::handlePeople)
            observe(goToBack, ::handleNavigationBack)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupListeners() {
        binding.toolbar.setNavigationOnClickListener {
            viewModel.handleBackNavigatorPressed()
        }
    }

    private fun handlePeople(avatarUrl: String?) {
        avatarUrl ?: return
        Glide.with(this)
            .load(avatarUrl)
            .transform(CenterCrop(), RoundedCorners(16.dp))
            .into(binding.ivDetailsPeople)
    }

    private fun handleNavigationBack(value: Any?) {
        value ?: return
        findNavController().popBackStack()
    }
}