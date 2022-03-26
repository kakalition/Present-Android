package com.daggery.present.breathpage.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.daggery.present.breathpage.R
import com.daggery.present.breathpage.databinding.FragmentBreathPageBinding
import com.daggery.present.breathpage.viewmodel.BreathPageViewModel
import com.daggery.present.sharedassets.BundleKeys

class BreathPageFragment : Fragment() {

    private var _viewBinding: FragmentBreathPageBinding? = null
    private val viewBinding get() = _viewBinding!!

    val viewModel: BreathPageViewModel by activityViewModels()

    private var breathPatternUuid: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        breathPatternUuid = arguments?.getString(BundleKeys.BREATH_PATTERN_UUID)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _viewBinding = FragmentBreathPageBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}