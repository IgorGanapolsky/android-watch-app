package com.igorganapolsky.vibratingwatchapp.presentation.new_timer_creation_screen.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.igorganapolsky.vibratingwatchapp.presentation.new_timer_creation_screen.view.SetTimerRepeatFragment
import com.igorganapolsky.vibratingwatchapp.presentation.new_timer_creation_screen.view.SetTimerTimeFragment
import com.igorganapolsky.vibratingwatchapp.presentation.new_timer_creation_screen.view.SetTimerVibrationFragment

internal class SetTimerPageAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    private val fragments =
        arrayOf(
            SetTimerTimeFragment(),
            SetTimerVibrationFragment(),
            SetTimerRepeatFragment()
        )

    override fun getItem(i: Int): Fragment {
        return fragments[i]
    }

    override fun getCount(): Int {
        return fragments.size
    }
}
