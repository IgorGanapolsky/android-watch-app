package com.igorganapolsky.vibratingwatchapp.presentation.details_screen

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.igorganapolsky.vibratingwatchapp.R
import com.igorganapolsky.vibratingwatchapp.data.CountModel
import com.igorganapolsky.vibratingwatchapp.data.TimerEntity.TIMER_ID
import com.igorganapolsky.vibratingwatchapp.domain.model.TimerModel
import com.igorganapolsky.vibratingwatchapp.presentation.details_screen.dialog.TimerDeleteDialogFragment
import com.igorganapolsky.vibratingwatchapp.presentation.settings_screen.SetTimerActivity
import com.igorganapolsky.vibratingwatchapp.core.extensions.observe

class TimerDetailsActivity : AppCompatActivity(), View.OnClickListener {

//    private var mViewModel: TimerDetailsViewModel? = null

    private var pbTime: ProgressBar? = null
    private var tvTime: TextView? = null
    private var ivStart: ImageView? = null
    private var ivContinueTimer: ImageView? = null
    private var ivStop: ImageView? = null
    private var ivRestart: ImageView? = null
    private var ivTimerSettings: ImageView? = null
    private var ivTimerRemove: ImageView? = null
    private var blinking: Animation? = null

    private val mViewModel by viewModels<TimerDetailsViewModel>()

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == SETTING_REQUEST_CODE && resultCode == SetTimerActivity.SETTING_SUCCESS_CODE) {
            mViewModel.checkUpdates()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timer_details)
//        mViewModel = ViewModelProviders.of(this, ViewModelFactory.instance)
//            .get(TimerDetailsViewModel::class.java)

        setupView()
        setupViewModel()
        setupObservers()
    }

    private fun setupViewModel() {
        val bundle = intent.extras
        val currentId = bundle?.getInt(TIMER_ID) ?: 0
        mViewModel!!.prepareData(currentId)
    }

    private fun setupView() {
        pbTime = findViewById(R.id.pbTime)
        tvTime = findViewById(R.id.tvTime)
        ivTimerSettings = findViewById(R.id.ivTimerSettings)
        ivTimerRemove = findViewById(R.id.ivTimerRemove)
        ivContinueTimer = findViewById(R.id.ivContinueTimer)
        ivContinueTimer!!.setOnClickListener(this)
        ivTimerRemove!!.setOnClickListener(this)
        ivTimerSettings!!.setOnClickListener(this)

        // CONTROLS
        ivStart = findViewById(R.id.ivStart)
        ivStop = findViewById(R.id.ivStop)
        ivRestart = findViewById(R.id.ivRestart)

        ivStop!!.setOnClickListener(this)
        ivStart!!.setOnClickListener(this)
        ivRestart!!.setOnClickListener(this)

        blinking = AlphaAnimation(1f, .25f)
        blinking!!.duration = 500
        blinking!!.repeatMode = Animation.REVERSE
        blinking!!.repeatCount = Animation.INFINITE
    }

    private fun setupObservers() {
        mViewModel!!.activeTimerData.observe(this, ::updateTimerData)
        mViewModel!!.viewStateData.observe(this, ::swapActionMenuState)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.ivStart -> {
                view.isSelected = !view.isSelected
                if (view.isSelected) {
                    mViewModel!!.onStart()
                } else {
                    mViewModel!!.onPause()
                }
            }
            R.id.ivStop -> mViewModel!!.onStop()
            R.id.ivRestart -> mViewModel!!.onRestart()
            R.id.ivTimerSettings -> {
                val bundle = intent.extras
                val currentId = bundle?.getInt(TIMER_ID) ?: TimerModel.UNDEFINE_ID
                startActivityForResult(
                    SetTimerActivity.createIntent(this, currentId),
                    SETTING_REQUEST_CODE
                )
            }
            R.id.ivContinueTimer -> mViewModel!!.onNextLap()
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
                ivStart!!.isSelected = false
                ivStart!!.isEnabled = true
                ivStop!!.isEnabled = true
                ivRestart!!.isEnabled = true
                disableAdditionalButtons(true)
                tvTime!!.startAnimation(blinking)
                ivContinueTimer!!.visibility = View.GONE
            }
            TimerModel.State.RUNNING -> {
                ivStart!!.isSelected = true
                disableAdditionalButtons(true)
                ivStart!!.isEnabled = true
                ivStop!!.isEnabled = true
                ivRestart!!.isEnabled = true
                ivContinueTimer!!.visibility = View.GONE
                blinking!!.cancel()
                blinking!!.reset()
            }
            TimerModel.State.FINISHED -> {
                ivStart!!.isSelected = false
                disableAdditionalButtons(false)
                ivStart!!.isEnabled = true
                ivStop!!.isEnabled = true
                ivRestart!!.isEnabled = true
                blinking!!.cancel()
                blinking!!.reset()
                ivContinueTimer!!.visibility = View.GONE
            }
            TimerModel.State.BEEPING -> {
                ivStart!!.isEnabled = false
                ivStop!!.isEnabled = false
                ivRestart!!.isEnabled = false
                tvTime!!.startAnimation(blinking)
                ivContinueTimer!!.visibility = View.VISIBLE
            }
        }
    }

    private fun updateTimerData(data: CountModel) {
        pbTime!!.setProgress(100 - data.currentProgress, data.isAnimationNeeded)
        tvTime!!.text = data.currentTime
    }

    private fun disableAdditionalButtons(disable: Boolean) {
        ivTimerSettings!!.isClickable = !disable
        ivTimerRemove!!.isClickable = !disable
        ivTimerSettings!!.alpha = if (disable) .5f else 1f
        ivTimerRemove!!.alpha = if (disable) .5f else 1f
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
