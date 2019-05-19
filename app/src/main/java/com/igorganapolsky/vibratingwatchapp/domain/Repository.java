package com.igorganapolsky.vibratingwatchapp.domain;

import androidx.lifecycle.LiveData;
import com.igorganapolsky.vibratingwatchapp.domain.model.TimerModel;

import java.util.List;

public interface Repository {

    TimerModel getTimerById(int timerId);

    LiveData<List<TimerModel>> getAll();

    void updateTimer(TimerModel timer);

    void saveTimer(TimerModel timer);

    void deleteTimer(int timerId);

    void updateTimerState(int timerId, TimerModel.State newState);
}
