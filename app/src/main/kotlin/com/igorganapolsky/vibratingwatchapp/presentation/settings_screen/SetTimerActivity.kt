package com.igorganapolsky.vibratingwatchapp.presentation.settings_screen

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import com.google.android.material.tabs.TabLayout
import com.igorganapolsky.vibratingwatchapp.core.IStepActionListener
import com.igorganapolsky.vibratingwatchapp.core.SwipeRestrictViewPager
import com.igorganapolsky.vibratingwatchapp.core.extensions.observe
import com.igorganapolsky.vibratingwatchapp.data.TimerEntity.TIMER_ID
import com.igorganapolsky.vibratingwatchapp.data.TimeMeasurement
import com.igorganapolsky.vibratingwatchapp.domain.model.TimerModel
import com.igorganapolsky.vibratingwatchapp.presentation.home_screen.TimerViewModel
import java.util.*

/**
 * Screen responsible for creating a timer.
 */
class SetTimerActivity : AppCompatActivity(), View.OnClickListener, IStepActionListener {
    //    private var mViewModel: TimerViewModel? = null
    private var vpWizard: SwipeRestrictViewPager? = null
    private var tvTimeHours: TextView? = null
    private var tvTimeMinutes: TextView? = null
    private var tvTimeSeconds: TextView? = null
    private var isProgressChanging = false
    private var isSwipeGranted = false

    private val mViewModel by viewModels<TimerViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.igorganapolsky.vibratingwatchapp.R.layout.activity_set_timer)
//        mViewModel = ViewModelProviders.of(this, ViewModelFactory.instance)
//            .get(TimerViewModel::class.java)

        setupViewModel()
        setupView()
        setupObservers()
    }

    private fun setupViewModel() {
        val bundle = intent.extras
        val currentId = bundle?.getInt(TIMER_ID) ?: TimerModel.UNDEFINE_ID
        mViewModel.setCurrentModelId(currentId)
    }

    private fun setupView() {
        vpWizard = findViewById(com.igorganapolsky.vibratingwatchapp.R.id.vpWizard)
        val ivNextPage =
            findViewById<AppCompatImageView>(com.igorganapolsky.vibratingwatchapp.R.id.ivNextPage)

        tvTimeHours = findViewById(com.igorganapolsky.vibratingwatchapp.R.id.tvTimeHours)
        tvTimeMinutes = findViewById(com.igorganapolsky.vibratingwatchapp.R.id.tvTimeMinutes)
        tvTimeSeconds = findViewById(com.igorganapolsky.vibratingwatchapp.R.id.tvTimeSeconds)

        val tlDots = findViewById<TabLayout>(com.igorganapolsky.vibratingwatchapp.R.id.tlDots)

        vpWizard!!.adapter = SetTimerPageAdapter(supportFragmentManager)
        vpWizard!!.offscreenPageLimit = 2
        ivNextPage.setOnClickListener(this)

        tlDots.setupWithViewPager(vpWizard, true)
        disableTabs(tlDots)
    }

    private fun setupObservers() {
        mViewModel.getCountdownData().observe(this, ::updateTime)
        mViewModel.getSwipeState().observe(this, ::updateSwipeStateIfNeeded)
        mViewModel.getTimerData().observe(this) { timerModel ->
            updateTimerData(TimeMeasurement.HOURS, timerModel.hours)
            updateTimerData(TimeMeasurement.MINUTES, timerModel.minutes)
            updateTimerData(TimeMeasurement.SECONDS, timerModel.seconds)
        }
    }

    private fun updateTime(setupTimeMeasurement: TimeMeasurement) {
        updateTimerData(setupTimeMeasurement, mViewModel.currentTimeValue)
    }


    private fun updateTimerData(timerSetup: TimeMeasurement, newValue: Int) {
        when (timerSetup) {
            TimeMeasurement.HOURS -> tvTimeHours!!.text =
                String.format(Locale.ENGLISH, "%02d", newValue)
            TimeMeasurement.MINUTES -> tvTimeMinutes!!.text =
                String.format(Locale.ENGLISH, "%02d", newValue)
            TimeMeasurement.SECONDS -> tvTimeSeconds!!.text =
                String.format(Locale.ENGLISH, "%02d", newValue)
        }
    }

    private fun updateSwipeStateIfNeeded(isSwipeGranted: Boolean?) {
        if (isProgressChanging) {
            this.isSwipeGranted = isSwipeGranted!!
        } else {
            vpWizard!!.isSwipeAvailable = isSwipeGranted!!
        }
    }

    override fun onClick(view: View) {
        if (!isProgressChanging && vpWizard!!.isSwipeAvailable) {
            val currentPage = vpWizard!!.currentItem
            if (currentPage < 2) {
                vpWizard!!.currentItem = currentPage + 1
            } else {
                mViewModel.saveTimer()
                setResult(SETTING_SUCCESS_CODE)
                finish()
            }
        }
    }

    override fun onActionStart() {
        isProgressChanging = true
        vpWizard!!.isSwipeAvailable = false
    }

    override fun onActionEnd() {
        isProgressChanging = false
        vpWizard!!.isSwipeAvailable = isSwipeGranted
    }

    private fun disableTabs(layout: TabLayout) {
        val tabStrip = layout.getChildAt(0) as LinearLayout
        for (i in 0 until tabStrip.childCount) {
            tabStrip.getChildAt(i).setOnTouchListener { v, event -> true }
        }
    }

    companion object {
        const val SETTING_SUCCESS_CODE = 101

        fun createIntent(context: Context, timerId: Int): Intent {
            val intent = Intent(context, SetTimerActivity::class.java)
            intent.putExtra(TIMER_ID, timerId)
            return intent
        }
    }
}
