package com.igorganapolsky.vibratingwatchapp.presentation.new_timer_creation_screen.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.igorganapolsky.vibratingwatchapp.presentation.new_timer_creation_screen.view.NewTimerRepeatFragment
import com.igorganapolsky.vibratingwatchapp.presentation.new_timer_creation_screen.view.NewTimerTimeFragment
import com.igorganapolsky.vibratingwatchapp.presentation.new_timer_creation_screen.view.NewTimerVibrationFragment

/**
 * Adapter for [NewTimerTimeFragment] screen.
 */
internal class SetTimerPageAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    private val fragments =
        arrayOf(
            NewTimerTimeFragment(),
            NewTimerVibrationFragment(),
            NewTimerRepeatFragment()
        )

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

}
