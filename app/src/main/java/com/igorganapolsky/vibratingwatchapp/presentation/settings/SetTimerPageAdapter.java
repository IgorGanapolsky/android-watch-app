package com.igorganapolsky.vibratingwatchapp.presentation.settings;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
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
