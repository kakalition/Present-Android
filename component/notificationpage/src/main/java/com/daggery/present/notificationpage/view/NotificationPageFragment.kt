package com.daggery.present.notificationpage.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.daggery.present.notificationpage.R
import com.daggery.present.notificationpage.adapter.NotificationListAdapter
import com.daggery.present.notificationpage.databinding.FragmentNotificationPageBinding
import com.daggery.present.notificationpage.entities.NotificationsState
import com.daggery.present.notificationpage.viewmodel.NotificationPageViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NotificationPageFragment : Fragment() {

    private var _viewBinding: FragmentNotificationPageBinding? = null
    private val viewBinding get() = _viewBinding!!

    private val viewModel: NotificationPageViewModel by activityViewModels()

    private val notificationListAdapter = NotificationListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _viewBinding = FragmentNotificationPageBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.notificationRecyclerView.adapter = notificationListAdapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.notificationState.collect {
                    ensureActive()
                    when(it) {
                        NotificationsState.Loading -> {}
                        is NotificationsState.Result -> notificationListAdapter.submitList(it.value)
                        is NotificationsState.Error -> {}
                    }
                }
            }
        }
    }
}