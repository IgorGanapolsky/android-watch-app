package com.igorganapolsky.vibratingwatchapp.presentation.new_timer_creation_screen.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayout
import com.igorganapolsky.vibratingwatchapp.data.TimeMeasurement
import com.igorganapolsky.vibratingwatchapp.data.TimerEntity.Companion.TIMER_ID
import com.igorganapolsky.vibratingwatchapp.databinding.ActivitySetTimerBinding
import com.igorganapolsky.vibratingwatchapp.domain.TimerModel
import com.igorganapolsky.vibratingwatchapp.presentation.home_screen.viewmodel.NewTimerViewModel
import com.igorganapolsky.vibratingwatchapp.common.IStepTouchActionListener
import com.igorganapolsky.vibratingwatchapp.common.extensions.observe
import com.igorganapolsky.vibratingwatchapp.presentation.new_timer_creation_screen.adapter.SetTimerPageAdapter
import java.util.*

/**
 * Screen responsible for creating a timer.
 */
class SetTimerActivity : AppCompatActivity(), View.OnClickListener,
    IStepTouchActionListener {
    private var isProgressChanging = false
    private var isSwipeGranted = false

    private val mViewModel by viewModels<NewTimerViewModel>()
    private lateinit var binding: ActivitySetTimerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySetTimerBinding.inflate(layoutInflater)
        setContentView(binding.root)
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
        binding.vpWizard!!.adapter =
            SetTimerPageAdapter(
                supportFragmentManager
            )
        binding.vpWizard!!.offscreenPageLimit = 2
        binding.ivNextPage.setOnClickListener(this)

        binding.tlDots.setupWithViewPager(binding.vpWizard, true)
        disableTabs(binding.tlDots)
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
            TimeMeasurement.HOURS -> binding.textViewTimeHours!!.text =
                String.format(Locale.ENGLISH, "%02d", newValue)
            TimeMeasurement.MINUTES -> binding.textViewTimeMinutes!!.text =
                String.format(Locale.ENGLISH, "%02d", newValue)
            TimeMeasurement.SECONDS -> binding.textViewTimeSeconds!!.text =
                String.format(Locale.ENGLISH, "%02d", newValue)
        }
    }

    private fun updateSwipeStateIfNeeded(isSwipeGranted: Boolean?) {
        if (isProgressChanging) {
            this.isSwipeGranted = isSwipeGranted!!
        } else {
            binding.vpWizard!!.isSwipeAvailable = isSwipeGranted!!
        }
    }

    override fun onClick(view: View) {
        if (!isProgressChanging && binding.vpWizard!!.isSwipeAvailable) {
            val currentPage = binding.vpWizard!!.currentItem
            if (currentPage < 2) {
                binding.vpWizard!!.currentItem = currentPage + 1
            } else {
                mViewModel.saveTimer()
                setResult(SETTING_SUCCESS_CODE)
                finish()
            }
        }
    }

    override fun onActionStart() {
        isProgressChanging = true
        binding.vpWizard!!.isSwipeAvailable = false
    }

    override fun onActionEnd() {
        isProgressChanging = false
        binding.vpWizard!!.isSwipeAvailable = isSwipeGranted
    }

    private fun disableTabs(layout: TabLayout) {
        val tabStrip = layout.getChildAt(0) as LinearLayout
        for (i in 0 until tabStrip.childCount) {
            tabStrip.getChildAt(i).setOnTouchListener { v, _ -> true }
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
