package com.igorganapolsky.vibratingwatchapp.presentation.existing_timer_info_screen

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.igorganapolsky.vibratingwatchapp.R
import com.igorganapolsky.vibratingwatchapp.data.TimerEntity.Companion.TIMER_ID
import com.igorganapolsky.vibratingwatchapp.databinding.ActivityTimerDetailsBinding
import com.igorganapolsky.vibratingwatchapp.domain.model.model.CountModel
import com.igorganapolsky.vibratingwatchapp.domain.model.model.TimerModel
import com.igorganapolsky.vibratingwatchapp.other.extensions.observe
import com.igorganapolsky.vibratingwatchapp.presentation.existing_timer_info_screen.dialog.TimerDeleteDialogFragment
import com.igorganapolsky.vibratingwatchapp.presentation.timer_creation_screen.SetTimerActivity

class TimerDetailsActivity : AppCompatActivity(), View.OnClickListener {
    private val mViewModel by viewModels<TimerControlViewModel>()

    private var blinking: Animation? = null
    private lateinit var binding: ActivityTimerDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTimerDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
        setupViewModel()
        setupObservers()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == SETTING_REQUEST_CODE && resultCode == SetTimerActivity.SETTING_SUCCESS_CODE) {
            mViewModel.checkUpdates()
        }
    }

    private fun setupViewModel() {
        val bundle = intent.extras
        val currentId = bundle?.getInt(TIMER_ID) ?: 0
        mViewModel.prepareData(currentId)
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
        mViewModel.activeTimerData.observe(this, ::updateTimerData)
        mViewModel.viewStateData.observe(this, ::swapActionMenuState)
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
                val bundle = intent.extras
                val currentId = bundle?.getInt(TIMER_ID) ?: TimerModel.UNDEFINE_ID
                startActivityForResult(
                    SetTimerActivity.createIntent(this, currentId),
                    SETTING_REQUEST_CODE
                )
            }
            R.id.ivContinueTimer -> mViewModel.onNextLap()
            R.id.ivTimerRemove -> supportFragmentManager
                .beginTransaction()
                .addToBackStack(null)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .replace(R.id.timer_details_fragment, TimerDeleteDialogFragment())
                .commit()
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
                disableAdditionalButtons(true)
                binding.ivStart.isEnabled = true
                binding.ivStop.isEnabled = true
                binding.ivRestart.isEnabled = true
                binding.ivContinueTimer.visibility = View.GONE
                blinking!!.cancel()
                blinking!!.reset()
            }
            TimerModel.State.FINISHED -> {
                binding.ivStart.isSelected = false
                disableAdditionalButtons(false)
                binding.ivStart.isEnabled = true
                binding.ivStop.isEnabled = true
                binding.ivRestart.isEnabled = true
                blinking!!.cancel()
                blinking!!.reset()
                binding.ivContinueTimer.visibility = View.GONE
            }
            TimerModel.State.BEEPING -> {
                binding.ivStart!!.isEnabled = false
                binding.ivStop!!.isEnabled = false
                binding.ivRestart!!.isEnabled = false
                binding.timeTextView!!.startAnimation(blinking)
                binding.ivContinueTimer!!.visibility = View.VISIBLE
            }
        }
    }

    private fun updateTimerData(data: CountModel) {
        binding.pbTime!!.setProgress(100 - data.currentProgress, data.isAnimationNeeded)
        binding.timeTextView!!.text = data.currentTime
    }

    private fun disableAdditionalButtons(disable: Boolean) {
        binding.ivTimerSettings!!.isClickable = !disable
        binding.ivTimerRemove!!.isClickable = !disable
        binding.ivTimerSettings!!.alpha = if (disable) .5f else 1f
        binding.ivTimerRemove!!.alpha = if (disable) .5f else 1f
    }

    companion object {
        private const val SETTING_REQUEST_CODE = 100

        fun createIntent(context: Context, timerId: Int): Intent {
            val intent = Intent(context, TimerDetailsActivity::class.java)
            intent.putExtra(TIMER_ID, timerId)
            return intent
        }
    }
}
