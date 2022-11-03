package pe.com.bcp.guidelineunittest.presentation.core

import androidx.lifecycle.ViewModel
import dagger.android.support.DaggerFragment
import javax.inject.Inject

abstract class BaseFragment<T : ViewModel> : DaggerFragment() {
    @Inject
    lateinit var viewModel: T
}