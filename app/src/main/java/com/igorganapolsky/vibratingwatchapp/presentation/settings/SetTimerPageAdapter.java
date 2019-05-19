package com.igorganapolsky.vibratingwatchapp.presentation.settings;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import com.igorganapolsky.vibratingwatchapp.presentation.settings.step.SetTimerRepeatFragment;
import com.igorganapolsky.vibratingwatchapp.presentation.settings.step.SetTimerTimeFragment;
import com.igorganapolsky.vibratingwatchapp.presentation.settings.step.SetTimerVibrationFragment;

public class SetTimerPageAdapter extends FragmentStatePagerAdapter {

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
