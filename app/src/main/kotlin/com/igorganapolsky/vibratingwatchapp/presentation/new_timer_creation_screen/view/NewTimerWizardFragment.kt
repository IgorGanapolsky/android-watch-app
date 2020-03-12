package com.igorganapolsky.vibratingwatchapp.presentation.new_timer_creation_screen.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.igorganapolsky.vibratingwatchapp.common.IStepTouchActionListener
import com.igorganapolsky.vibratingwatchapp.common.extensions.observe
import com.igorganapolsky.vibratingwatchapp.data.TimeMeasurement
import com.igorganapolsky.vibratingwatchapp.databinding.FragmentNewTimerWizardBinding
import com.igorganapolsky.vibratingwatchapp.presentation.new_timer_creation_screen.adapter.SetTimerPageAdapter
import com.igorganapolsky.vibratingwatchapp.presentation.new_timer_creation_screen.viewmodel.NewTimerViewModel

/**
 * Screen responsible for creating a new timer.
 */
class NewTimerWizardFragment : Fragment(), View.OnClickListener, IStepTouchActionListener {
    private var isProgressChanging = false
    private var isSwipeGranted = false
    private lateinit var binding: FragmentNewTimerWizardBinding

    private val mViewModel by viewModels<NewTimerViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewTimerWizardBinding.inflate(layoutInflater)

        setupViews()
        observeUi()

        return this.binding.root
    }

    private fun setupViews() {
        binding.newTimerPager.adapter = SetTimerPageAdapter(this)

        TabLayoutMediator(binding.pagerDotsLayout, binding.newTimerPager) { tab, position ->
                        tab.text = "text"
        }.attach()

        binding.newTimerPager.offscreenPageLimit = 2
        binding.arrowNextPage.setOnClickListener(this)

//        binding.pagerDotsLayout.setupWithViewPager(binding.vpWizard, true)
        disableDots(binding.pagerDotsLayout)
    }

    private fun observeUi() {
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
            TimeMeasurement.HOURS -> binding.timeHours.text =
                String.format("%02d", newValue)
            TimeMeasurement.MINUTES -> binding.timeMinutes.text =
                String.format("%02d", newValue)
            TimeMeasurement.SECONDS -> binding.timeSeconds.text =
                String.format("%02d", newValue)
        }
    }

    private fun updateSwipeStateIfNeeded(isSwipeGranted: Boolean?) {
        if (isProgressChanging) {
            this.isSwipeGranted = isSwipeGranted!!
        } else {
//            binding.newTimerPager.isSwipeAvailable = isSwipeGranted!!
        }
    }

    override fun onClick(view: View) {
//        if (!isProgressChanging && binding.newTimerPager.isSwipeAvailable) {
        val currentPage = binding.newTimerPager.currentItem
        if (currentPage < 2) {
            binding.newTimerPager.currentItem = currentPage + 1
        } else {
            mViewModel.saveTimer()
//                setResult(SETTING_SUCCESS_CODE)
//                finish()
        }
//        }
    }

    override fun onActionStart() {
        isProgressChanging = true
//        binding.newTimerPager.isSwipeAvailable = false
    }

    override fun onActionEnd() {
        isProgressChanging = false
//        binding.newTimerPager.isSwipeAvailable = isSwipeGranted
    }

    private fun disableDots(layout: TabLayout) {
        val tabStrip = layout.getChildAt(0) as LinearLayout
        for (i in 0 until tabStrip.childCount) {
            tabStrip.getChildAt(i).setOnTouchListener { v, _ -> true }
        }
    }

}
