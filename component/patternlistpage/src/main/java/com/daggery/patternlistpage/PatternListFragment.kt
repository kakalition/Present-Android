package com.daggery.patternlistpage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.daggery.patternlistpage.databinding.FragmentPatternListBinding
import com.daggery.patternlistpage.viewmodel.PatternListPageViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PatternListFragment : Fragment() {

    private var _viewBinding: FragmentPatternListBinding? = null
    private val viewBinding get() = _viewBinding!!

    private val viewModel: PatternListPageViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _viewBinding = FragmentPatternListBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.collectState()

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.patternListState.collect {

                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.changeScreenState(true)
    }

    override fun onPause() {
        super.onPause()
        viewModel.changeScreenState(false)
    }
}