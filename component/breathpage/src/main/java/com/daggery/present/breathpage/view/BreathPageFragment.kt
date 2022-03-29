package com.daggery.present.breathpage.view

import android.animation.ValueAnimator
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import android.view.animation.Interpolator
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.daggery.present.sharedassets.R
import com.daggery.present.breathpage.databinding.FragmentBreathPageBinding
import com.daggery.present.breathpage.viewmodel.BreathPageViewModel
import com.daggery.present.sharedassets.BundleKeys
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BreathPageFragment : Fragment() {

    private var _viewBinding: FragmentBreathPageBinding? = null
    private val viewBinding get() = _viewBinding!!

    private val viewModel: BreathPageViewModel by activityViewModels()

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

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getBreathPatternStateHolder(breathPatternUuid ?: "")
            bindsState()

        }
    }

    private fun animateBackground() {
        val background = viewBinding.baseLayout.background as GradientDrawable
        val gradientOne: ValueAnimator
        val gradientTwo: ValueAnimator

        background.colors?.let {
            gradientOne = ValueAnimator.ofArgb(it[0], resources.getColor(R.color.blue_one))
            gradientOne.apply {
                duration = 3000
                interpolator = DecelerateInterpolator()
            }
            gradientTwo = ValueAnimator.ofArgb(it[1], resources.getColor(R.color.blue_two))
            gradientTwo.apply {
                duration = 3000
                interpolator = DecelerateInterpolator()
            }

            gradientOne.addUpdateListener { color ->
                val temp = background.colors
                temp?.set(0, color.animatedValue as Int)
                background.colors = temp
            }
            gradientTwo.addUpdateListener { color ->
                val temp = background.colors
                temp?.set(1, color.animatedValue as Int)
                background.colors = temp
            }

            gradientOne.start()
            gradientTwo.start()
        }
    }

    private fun animatePlayButton(valueAnimator: ValueAnimator) {
        val value = (valueAnimator.animatedValue as Int) * requireContext().resources.displayMetrics.density
        val params = viewBinding.playBg.layoutParams.apply {
            height = value.toInt()
            width = value.toInt()
        }
        viewBinding.playBg.layoutParams = params
        val background = viewBinding.baseLayout.background as GradientDrawable
        Log.d("LOL COLOR", background.colors.toString())
    }

    private fun bindsState() {
        with(viewBinding) {
            breathPageAppBar.title = viewModel.breathPatternStateHolder.name
            currentStateText.text = viewModel.breathPatternStateHolder.state.name.lowercase().replaceFirstChar { it.uppercase() }
        }
    }
}