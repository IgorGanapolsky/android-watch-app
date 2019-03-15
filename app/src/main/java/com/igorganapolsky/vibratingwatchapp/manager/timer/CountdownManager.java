package com.igorganapolsky.vibratingwatchapp.manager.timer;

import android.arch.lifecycle.LiveData;
import com.igorganapolsky.vibratingwatchapp.domain.model.TimerModel;

public interface CountdownManager {

    LiveData<TimerModel> observeActiveModel();

    void setTickListener(TickListener listener);

    TimerModel getActive();

    int getActiveId();

    boolean isHasMoreRepeats();

    boolean isActive();

    long getActiveTimeLeft();

    int getActiveProgress();


    void setupTimer(TimerModel model);

    void onStart();

    void onPause();

    void onStop();

    void onRestart();

    boolean onNextLap();
}
