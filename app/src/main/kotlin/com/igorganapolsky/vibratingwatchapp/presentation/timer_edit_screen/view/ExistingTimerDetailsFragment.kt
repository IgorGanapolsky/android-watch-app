package com.igorganapolsky.vibratingwatchapp.presentation.timer_edit_screen.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.igorganapolsky.vibratingwatchapp.R
import com.igorganapolsky.vibratingwatchapp.databinding.FragmentExistingTimerDetailsBinding
import com.igorganapolsky.vibratingwatchapp.domain.CountModel
import com.igorganapolsky.vibratingwatchapp.domain.TimerModel
import com.igorganapolsky.vibratingwatchapp.presentation.timer_edit_screen.viewmodel.ExistingTimerViewModel

class ExistingTimerDetailsFragment : Fragment(), View.OnClickListener {
    private val mViewModel by viewModels<ExistingTimerViewModel>()

    private var blinking: Animation? = null
    private lateinit var binding: FragmentExistingTimerDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentExistingTimerDetailsBinding.inflate(layoutInflater)
//        setContentView(binding.root)
        setupView()
        setupViewModel()
        setupObservers()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

//        if (requestCode == SETTING_REQUEST_CODE && resultCode == NewTimerWizardFragment.SETTING_SUCCESS_CODE) {
//            mViewModel.checkUpdates()
//        }
    }

    private fun setupViewModel() {
//        val bundle = intent.extras
//        val currentId = bundle?.getInt(TIMER_ID) ?: 0
//        mViewModel.prepareData(currentId)
    }

    private fun setupView() {
        binding.ivContinueTimer.setOnClickListener(this)
        binding.ivTimerRemove.setOnClickListener(this)
        binding.ivTimerSettings.setOnClickListener(this)

        // Timer controls
        binding.ivStop.setOnClickListener(this)
        binding.ivStart.setOnClickListener(this)
        binding.ivRestart.setOnClickListener(this)

        blinking = AlphaAnimation(1f, .25f)
        blinking!!.duration = 500
        blinking!!.repeatMode = Animation.REVERSE
        blinking!!.repeatCount = Animation.INFINITE
    }

    private fun setupObservers() {
//        mViewModel.activeTimerData.observe(this, ::updateTimerData)
//        mViewModel.viewStateData.observe(this, ::swapActionMenuState)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.ivStart -> {
                view.isSelected = !view.isSelected
                if (view.isSelected) {
                    mViewModel.onStart()
                } else {
                    mViewModel.onPause()
                }
            }
            R.id.ivStop -> mViewModel.onStop()
            R.id.ivRestart -> mViewModel.onRestart()
            R.id.ivTimerSettings -> {
//                val bundle = intent.extras
//                val currentId = bundle?.getInt(TIMER_ID) ?: TimerModel.UNDEFINE_ID
//                startActivityForResult(
//                    NewTimerWizardFragment.createIntent(this, currentId),
//                    SETTING_REQUEST_CODE
//                )
            }
            R.id.ivContinueTimer -> mViewModel.onNextLap()
//            R.id.ivTimerRemove -> supportFragmentManager
//                .beginTransaction()
//                .addToBackStack(null)
//                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
//                .replace(
//                    R.id.timer_details_fragment,
//                    ExistingTimerDeleteDialogFragment()
//                )
//                .commit()
//        }
        }
    }

    private fun swapActionMenuState(state: TimerModel.State) {
        when (state) {
            TimerModel.State.PAUSED -> {
                binding.ivStart.isSelected = false
                binding.ivStart.isEnabled = true
                binding.ivStop.isEnabled = true
                binding.ivRestart.isEnabled = true
                disableAdditionalButtons(true)
                binding.timeTextView.startAnimation(blinking)
                binding.ivContinueTimer.visibility = View.GONE
            }
            TimerModel.State.RUNNING -> {
                binding.ivStart.isSelected = true
//                disableAdditionalButtons(true)
                binding.ivStart.isEnabled = true
                binding.ivStop.isEnabled = true
                binding.ivRestart.isEnabled = true
                binding.ivContinueTimer.visibility = View.GONE
                blinking!!.cancel()
                blinking!!.reset()
            }
            TimerModel.State.FINISHED -> {
                binding.ivStart.isSelected = false
//                disableAdditionalButtons(false)
                binding.ivStart.isEnabled = true
                binding.ivStop.isEnabled = true
                binding.ivRestart.isEnabled = true
                blinking!!.cancel()
                blinking!!.reset()
                binding.ivContinueTimer.visibility = View.GONE
            }
            TimerModel.State.BEEPING -> {
                binding.ivStart.isEnabled = false
                binding.ivStop.isEnabled = false
                binding.ivRestart.isEnabled = false
                binding.timeTextView.startAnimation(blinking)
                binding.ivContinueTimer.visibility = View.VISIBLE
            }
        }
    }

    private fun updateTimerData(data: CountModel) {
        binding.pbTime.setProgress(100 - data.currentProgress, data.isAnimationNeeded)
        binding.timeTextView.text = data.currentTime
    }

    private fun disableAdditionalButtons(disable: Boolean) {
        binding.ivTimerSettings.isClickable = !disable
        binding.ivTimerRemove.isClickable = !disable
        binding.ivTimerSettings.alpha = if (disable) .5f else 1f
        binding.ivTimerRemove.alpha = if (disable) .5f else 1f
    }

}
