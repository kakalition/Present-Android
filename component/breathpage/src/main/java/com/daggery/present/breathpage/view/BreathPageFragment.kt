package com.daggery.present.breathpage.view

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import android.view.animation.Interpolator
import android.view.animation.LinearInterpolator
import androidx.core.animation.addListener
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.daggery.present.sharedassets.R
import com.daggery.present.breathpage.databinding.FragmentBreathPageBinding
import com.daggery.present.breathpage.entities.BreathStateEnum
import com.daggery.present.breathpage.viewmodel.BreathPageViewModel
import com.daggery.present.breathpage.viewmodel.TimerState
import com.daggery.present.sharedassets.BundleKeys
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.launch
import kotlin.time.Duration

@AndroidEntryPoint
class BreathPageFragment : Fragment() {

    private var _viewBinding: FragmentBreathPageBinding? = null
    private val viewBinding get() = _viewBinding!!

    private val viewModel: BreathPageViewModel by activityViewModels()

    private var breathPatternUuid: String? = null

    private var gradientOne: ValueAnimator? = null
    private var gradientTwo: ValueAnimator? = null
    private var timeAnimator: ValueAnimator? = null
    private var sizeAnimator: ValueAnimator? = null

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

            viewBinding.playBg.setOnClickListener {
                launch {
                    if(viewModel.isPaused.value) {
                        viewModel.resume()
                    } else {
                        viewModel.pause()
                    }
                }

                viewBinding.playButton.visibility = View.GONE
                viewBinding.timeCounter.visibility = View.VISIBLE
                launch {
                    viewModel.startSession()
                }
            }

            viewModel.getBreathPatternStateHolder(breathPatternUuid ?: "")
            bindsState()

            // TODO: Check correct behavior
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                launch {
                    viewModel.isPaused.collect {
                        if(it) pauseAnimation()
                        else resumeAnimation()
                    }
                }
                launch {
                    viewModel.timerStateFlow.collect {
                        ensureActive()
                        viewBinding.currentStateText.text = it.currentState.toString()
                        animateBackground(it)
                        animatePlayButton(it)
                        if (it.currentState == BreathStateEnum.FINISHED) cancel()
                    }
                }
            }
        }
    }

    private fun startAnimation() {
        gradientOne?.start()
        gradientTwo?.start()
        timeAnimator?.start()
        sizeAnimator?.start()
    }

    private fun pauseAnimation() {
        gradientOne?.pause()
        gradientTwo?.pause()
        timeAnimator?.pause()
        sizeAnimator?.pause()
    }

    private fun resumeAnimation() {
        gradientOne?.resume()
        gradientTwo?.resume()
        timeAnimator?.resume()
        sizeAnimator?.resume()
    }

    private fun animateBackground(state: TimerState) {
        val background = viewBinding.baseLayout.background as GradientDrawable
        val gradientPair = createGradientPair(state.currentState)

        background.colors?.let {
            gradientOne = ValueAnimator.ofArgb(it[0], gradientPair.first)
            gradientOne?.apply {
                duration = 600
                interpolator = DecelerateInterpolator()
                addUpdateListener { color ->
                    val temp = background.colors
                    temp?.set(0, color.animatedValue as Int)
                    background.colors = temp
                }
            }
            gradientTwo = ValueAnimator.ofArgb(it[1], gradientPair.second)
            gradientTwo?.apply {
                duration = 600
                interpolator = DecelerateInterpolator()
                addUpdateListener { color ->
                    val temp = background.colors
                    temp?.set(1, color.animatedValue as Int)
                    background.colors = temp
                }
            }

            gradientOne?.start()
            gradientTwo?.start()
        }
    }

    private fun createGradientPair(state: BreathStateEnum): Pair<Int, Int> {

        fun getColor(colorRes: Int): Int {
            return resources.getColor(colorRes, requireContext().theme)
        }

        return when(state) {
            BreathStateEnum.INHALE -> {
                Pair(
                    getColor(R.color.blue_one),
                    getColor(R.color.blue_two),
                )
            }
            BreathStateEnum.HOLD_POST_INHALE -> {
                Pair(
                    getColor(R.color.green_one),
                    getColor(R.color.green_two),
                )
            }
            BreathStateEnum.EXHALE -> {
                Pair(
                    getColor(R.color.orange_one),
                    getColor(R.color.orange_two),
                )
            }
            BreathStateEnum.HOLD_POST_EXHALE -> {
                Pair(
                    getColor(R.color.green_one),
                    getColor(R.color.green_two),
                )
            }
            BreathStateEnum.FINISHED -> {
                Pair(
                    getColor(R.color.purple_one),
                    getColor(R.color.purple_two),
                )
            }
            else -> {
                Pair(
                    getColor(R.color.purple_one),
                    getColor(R.color.purple_two),
                )
            }
        }
    }

    private fun animatePlayButton(timerState: TimerState) {
        timeAnimator = ValueAnimator.ofInt(timerState.currentDuration, 0).apply {
            duration = (timerState.currentDuration * 1000L) - 150
            interpolator = LinearInterpolator()
            addUpdateListener {
                viewBinding.timeCounter.text = it.animatedValue.toString()
            }
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationStart(animation: Animator?) {
                    viewModel.startAnim()
                }
                override fun onAnimationEnd(animation: Animator?) {
                    viewBinding.timeCounter.text = "0"
                    viewModel.pauseAnim()
                }
            })
        }

        sizeAnimator = when(timerState.currentState) {
            BreathStateEnum.INHALE -> {
                ValueAnimator.ofInt(150, 250)
            }
            BreathStateEnum.EXHALE -> {
                ValueAnimator.ofInt(250, 150)
            }
            else -> null
        }?.apply {
            duration = timerState.currentDuration * 1000L
            interpolator = DecelerateInterpolator()
            addUpdateListener {
                val value = (animatedValue as Int) * requireContext().resources.displayMetrics.density
                val params = viewBinding.playBg.layoutParams.apply {
                    height = value.toInt()
                    width = value.toInt()
                }
                viewBinding.playBg.layoutParams = params
            }
        }

        timeAnimator?.start()
        sizeAnimator?.start()
    }

    private fun bindsState() {
        with(viewBinding) {
            breathPageAppBar.title = viewModel.breathPatternStateHolder.name
            currentStateText.text = viewModel.breathPatternStateHolder.state.name.lowercase().replaceFirstChar { it.uppercase() }
        }
    }
}