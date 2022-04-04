package com.daggery.patternlistpage.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.daggery.patternlistpage.adapter.PatternListAdapter
import com.daggery.patternlistpage.databinding.FragmentPatternListBinding
import com.daggery.patternlistpage.entities.PatternListState
import com.daggery.patternlistpage.viewmodel.PatternListPageViewModel
import com.daggery.present.sharedassets.BundleKeys
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PatternListFragment : Fragment() {

    private var _viewBinding: FragmentPatternListBinding? = null
    private val viewBinding get() = _viewBinding!!

    private val viewModel: PatternListPageViewModel by activityViewModels()

    private val patternListAdapter = PatternListAdapter { uuid -> showModal(uuid) }

    companion object {
        const val UUID_KEY = "UUID_KEY"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _viewBinding = FragmentPatternListBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindRecyclerView()

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.collectState()
                viewModel.patternListState.collect {
                    when (it) {
                        PatternListState.Loading -> {}
                        is PatternListState.Error -> {}
                        is PatternListState.Result -> patternListAdapter.submitList(it.listOfPattern)
                    }
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

    private fun bindRecyclerView() {
        viewBinding.patternRecyclerView.adapter = patternListAdapter
    }

    private fun showModal(uuid: String) {
        val bundle = Bundle()
        bundle.putString(BundleKeys.PATTERN_UUID, uuid)
        val modal = PatternModalSheetFragment()
        modal.arguments = bundle
        modal.show(childFragmentManager, PatternModalSheetFragment.TAG)
    }
}