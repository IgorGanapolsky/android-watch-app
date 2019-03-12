package com.igorganapolsky.vibratingwatchapp.domain;

import android.arch.lifecycle.LiveData;
import com.igorganapolsky.vibratingwatchapp.domain.model.TimerModel;

import java.util.List;

public interface Repository {

    TimerModel getTimerById(int timerId);

    LiveData<List<TimerModel>> getAll();

    LiveData<TimerModel> getObservableTimerById(int timerId);

    void updateTimer(TimerModel timer);

    void saveTimer(TimerModel timer);

    void deleteTimer(int timerId);

    void updateTimerState(int timerId, TimerModel.State newState);

    void updateTimerTimeLeft(int timerId, long timeLeft);

}
