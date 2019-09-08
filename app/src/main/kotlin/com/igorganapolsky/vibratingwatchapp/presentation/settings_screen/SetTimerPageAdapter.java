package com.igorganapolsky.vibratingwatchapp.presentation.settings_screen;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import com.igorganapolsky.vibratingwatchapp.presentation.settings_screen.step.SetTimerRepeatFragment;
import com.igorganapolsky.vibratingwatchapp.presentation.settings_screen.step.SetTimerTimeFragment;
import com.igorganapolsky.vibratingwatchapp.presentation.settings_screen.step.SetTimerVibrationFragment;

class SetTimerPageAdapter extends FragmentStatePagerAdapter {

    private final Fragment[] fragments = {
        new SetTimerTimeFragment(),
        new SetTimerVibrationFragment(),
        new SetTimerRepeatFragment()
    };

    SetTimerPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        return fragments[i];
    }

    @Override
    public int getCount() {
        return fragments.length;
    }
}
