package com.daggery.patternlistpage.view

import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.daggery.patternlistpage.databinding.FragmentPatternModalSheetListDialogBinding
import com.daggery.patternlistpage.viewmodel.PatternListPageViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PatternModalSheetFragment : BottomSheetDialogFragment() {

    private var _viewBinding: FragmentPatternModalSheetListDialogBinding? = null
    private val viewBinding get() = _viewBinding!!

    private val viewModel: PatternListPageViewModel by activityViewModels()

    companion object {
        const val TAG = "PATTERN_LIST_FRAGMENT"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _viewBinding = FragmentPatternModalSheetListDialogBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewBinding.textInstanceName.text = viewModel.getPattern(arguments?.getString(PatternListFragment.UUID_KEY) ?: "")?.name
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _viewBinding = null
    }
}