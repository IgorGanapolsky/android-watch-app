package com.igorganapolsky.vibratingwatchapp.presentation.timer_creation_screen

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.igorganapolsky.vibratingwatchapp.presentation.timer_creation_screen.step.SetTimerRepeatFragment
import com.igorganapolsky.vibratingwatchapp.presentation.timer_creation_screen.step.SetTimerTimeFragment
import com.igorganapolsky.vibratingwatchapp.presentation.timer_creation_screen.step.SetTimerVibrationFragment

internal class SetTimerPageAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    private val fragments =
        arrayOf(SetTimerTimeFragment(), SetTimerVibrationFragment(), SetTimerRepeatFragment())

    override fun getItem(i: Int): Fragment {
        return fragments[i]
    }

    override fun getCount(): Int {
        return fragments.size
    }
}
