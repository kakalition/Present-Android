package com.daggery.present.breathpage.view

import android.animation.*
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import android.view.animation.LinearInterpolator
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.daggery.present.sharedassets.R
import com.daggery.present.breathpage.databinding.FragmentBreathPageBinding
import com.daggery.present.breathpage.entities.BreathStateEnum
import com.daggery.present.breathpage.helper.TimerState
import com.daggery.present.breathpage.viewmodel.BreathPageViewModel
import com.daggery.present.sharedassets.BundleKeys
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

// TODO: Issue: Size value gotten from viewBinding is off by 50dp
// TODO: Ambient Music
// TODO: TTS Instruction

@AndroidEntryPoint
class BreathPageFragment : Fragment() {

    private var _viewBinding: FragmentBreathPageBinding? = null
    private val viewBinding get() = _viewBinding!!

    private val viewModel: BreathPageViewModel by activityViewModels()

    private var breathPatternUuid: String? = null

    private var gradientOneAnimator: ValueAnimator? = null
    private var gradientTwoAnimator: ValueAnimator? = null
    private var timeAnimator: ValueAnimator? = null
    private var sizeAnimator: ValueAnimator? = null

    private val keyframe1 = Keyframe.ofInt(0.0F, 1)
    private val keyframe2 = Keyframe.ofInt(0.6F, 0)
    private val propertyValuesHolder = PropertyValuesHolder.ofKeyframe("alpha", keyframe1, keyframe2)
    private val timeCounterAnimator = ValueAnimator.ofPropertyValuesHolder(propertyValuesHolder).apply {
        duration = 800
        repeatCount = ValueAnimator.INFINITE
        repeatMode = ValueAnimator.REVERSE
        interpolator = LinearInterpolator()
        addUpdateListener { opacity ->
            viewBinding.timeCounter.setTextColor(Color.argb((opacity.animatedValue as Int).toFloat(), 0F, 0F, 0F))
        }
        addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationCancel(animation: Animator?) {
                viewBinding.timeCounter.setTextColor(Color.argb(1F, 0F, 0F, 0F))
            }
        })
    }

    private fun Int.toViewBinding(): Int {
        return this * requireContext().resources.displayMetrics.density.roundToInt()
    }

    private fun Int.fromViewBinding(): Int {
        return this / requireContext().resources.displayMetrics.density.roundToInt()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        breathPatternUuid = arguments?.getString(BundleKeys.PATTERN_UUID)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _viewBinding = FragmentBreathPageBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    private val playBgOnClickListener: (v: View) -> Unit = {
        if (viewModel.timerState.value.first.state != BreathStateEnum.FINISHED) {
            if (viewModel.isSessionPaused.value) {
                viewModel.startSession()
            } else {
                viewModel.stopSession()
            }
        }

        viewBinding.playButton.visibility = View.GONE
        viewBinding.timeCounter.visibility = View.VISIBLE
    }

    private val restartOnClickListener: (v: View) -> Unit = {
        viewBinding.playButton.visibility = View.VISIBLE
        viewBinding.timeCounter.visibility = View.GONE
        viewModel.stopSession()
        restartAnimation()
        viewModel.resetSession()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {

            viewModel.getBreathPatternStateHolder(breathPatternUuid ?: "")
            bindsState()

            viewBinding.playBg.setOnClickListener(playBgOnClickListener)
            viewBinding.restartButton.setOnClickListener(restartOnClickListener)

            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.isSessionPaused.collect {
                        if(it) pauseAnimation()
                        else resumeAnimation()
                    }
                }

                launch {
                    viewModel.timerState.collect { state ->
                        viewBinding.currentStateText.text = state.first.displayName
                        viewBinding.nextStateText.text = state.second.displayName
                        if (state.first.state != BreathStateEnum.GROUND &&
                            state.first.state != BreathStateEnum.FINISHED) {
                            animatePlayButton(state.first)
                        }
                        animateBackground(state.first)
                    }
                }
            }
        }
    }

    private fun pauseAnimation() {
        gradientOneAnimator?.pause()
        gradientTwoAnimator?.pause()
        timeAnimator?.pause()
        sizeAnimator?.pause()
        timeCounterAnimator.start()
    }

    private fun resumeAnimation() {
        gradientOneAnimator?.resume()
        gradientTwoAnimator?.resume()
        timeAnimator?.resume()
        sizeAnimator?.resume()
        timeCounterAnimator.cancel()
    }

    private fun restartAnimation() {
        pauseAnimation()
        sizeAnimator = ValueAnimator.ofInt(viewBinding.playBg.width.fromViewBinding(), 250)
        ?.apply {
            duration = 500
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
        sizeAnimator?.start()
    }

    private fun animateBackground(timerState: TimerState) {
        val background = viewBinding.baseLayout.background as GradientDrawable
        val gradientPair = createGradientPair(timerState.state)

        background.colors?.let {
            gradientOneAnimator = ValueAnimator.ofArgb(it[0], gradientPair.first)
            gradientOneAnimator?.apply {
                duration = 600
                interpolator = DecelerateInterpolator()
                addUpdateListener { color ->
                    val temp = background.colors
                    temp?.set(0, color.animatedValue as Int)
                    background.colors = temp
                }
            }
            gradientTwoAnimator = ValueAnimator.ofArgb(it[1], gradientPair.second)
            gradientTwoAnimator?.apply {
                duration = 600
                interpolator = DecelerateInterpolator()
                addUpdateListener { color ->
                    val temp = background.colors
                    temp?.set(1, color.animatedValue as Int)
                    background.colors = temp
                }
            }

            gradientOneAnimator?.start()
            gradientTwoAnimator?.start()
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
        timeAnimator = ValueAnimator.ofInt(timerState.duration, 0).apply {
            duration = (timerState.duration * 1000L) - 150
            interpolator = LinearInterpolator()
            addUpdateListener {
                viewBinding.timeCounter.text = it.animatedValue.toString()
            }
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationStart(animation: Animator?) {
                    viewModel.changeRunningAnimationState(true)
                }
                override fun onAnimationEnd(animation: Animator?) {
                    viewBinding.timeCounter.text = "0"
                    viewModel.changeRunningAnimationState(false)
                }
            })
        }

        sizeAnimator = when(timerState.state) {
            BreathStateEnum.READY -> ValueAnimator.ofInt(viewBinding.playBg.width.fromViewBinding(), 150)
            BreathStateEnum.INHALE -> ValueAnimator.ofInt(150, 250)
            BreathStateEnum.EXHALE -> ValueAnimator.ofInt(250, 150)

            else -> null
        }?.apply {
            duration = timerState.duration * 1000L
            interpolator = DecelerateInterpolator()
            addUpdateListener {
                val value = (animatedValue as Int).toViewBinding()
                val params = viewBinding.playBg.layoutParams.apply {
                    height = value
                    width = value
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