package com.daggery.present.routinepage.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.daggery.present.routinepage.adapter.RoutineListAdapter
import com.daggery.present.routinepage.databinding.FragmentRoutinePageBinding
import com.daggery.present.routinepage.entities.RoutineState
import com.daggery.present.routinepage.viewmodel.RoutinePageViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RoutinePageFragment : Fragment() {

    private var _viewBinding: FragmentRoutinePageBinding? = null
    private val viewBinding get() = _viewBinding!!

    private val viewModel: RoutinePageViewModel by activityViewModels()

    private val routinePageListAdapter = RoutineListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _viewBinding = FragmentRoutinePageBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.routineRecyclerView.adapter = routinePageListAdapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.routineState.collect {
                    ensureActive()
                    when(it) {
                        RoutineState.Loading -> {}
                        is RoutineState.Result -> { routinePageListAdapter.submitList(it.value) }
                        is RoutineState.Error -> {}
                    }
                }
            }
        }
    }
}