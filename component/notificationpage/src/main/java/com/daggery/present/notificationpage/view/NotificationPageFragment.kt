package com.daggery.present.notificationpage.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.daggery.present.notificationpage.R
import com.daggery.present.notificationpage.databinding.FragmentNotificationPageBinding
import com.daggery.present.notificationpage.viewmodel.NotificationPageViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationPageFragment : Fragment() {

    private var _viewBinding: FragmentNotificationPageBinding? = null
    private val viewBinding get() = _viewBinding!!

    private val viewModel: NotificationPageViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _viewBinding = FragmentNotificationPageBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

}