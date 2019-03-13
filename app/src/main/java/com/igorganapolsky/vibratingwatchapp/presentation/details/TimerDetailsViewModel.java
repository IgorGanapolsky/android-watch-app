package com.igorganapolsky.vibratingwatchapp.presentation.details;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import com.igorganapolsky.vibratingwatchapp.core.util.TimerTransform;
import com.igorganapolsky.vibratingwatchapp.domain.Repository;
import com.igorganapolsky.vibratingwatchapp.domain.model.CountData;
import com.igorganapolsky.vibratingwatchapp.domain.model.TimerModel;
import com.igorganapolsky.vibratingwatchapp.manager.timer.CountdownManager;
import com.igorganapolsky.vibratingwatchapp.manager.timer.TickListener;

public class TimerDetailsViewModel extends ViewModel implements TickListener {

    private final Repository repository;
    private final CountdownManager countdownManager;

    private final MutableLiveData<CountData> activeTimerData = new MutableLiveData<>();
    private final MutableLiveData<TimerModel.State> viewStateData = new MutableLiveData<>();

    private TimerModel currentTimer;
    private int currentId;

    public TimerDetailsViewModel(Repository repository, CountdownManager countdownManager) {
        this.repository = repository;
        this.countdownManager = countdownManager;
    }

    LiveData<CountData> getActiveTimerData() {
        return activeTimerData;
    }

    LiveData<TimerModel.State> getViewStateData() {
        return viewStateData;
    }

    @Override
    public void onTick(String newValue, int progress) {
        if (currentId == countdownManager.getActiveId()) {
            activeTimerData.setValue(new CountData(newValue, progress, true));
        }
    }

    @Override
    public void onFinish(String newValue, int progress, boolean isStop) {
        if (currentId == countdownManager.getActiveId()) {
            viewStateData.setValue(TimerModel.State.FINISH);
            activeTimerData.setValue(new CountData(newValue, progress, isStop));
        }
    }

    void prepareData(int currentId) {
        this.currentId = currentId;
        countdownManager.setTickListener(this);

        TimerModel timer = repository.getTimerById(currentId);
        int activeId = countdownManager.getActiveId();

        boolean isActive = timer.getId() == activeId && countdownManager.isActive();

        if (isActive) {
            currentTimer = countdownManager.getActive();
        } else {
            currentTimer = timer;
        }

        long timeToSetup = prepareTime(isActive);
        int progress = prepareProgress(isActive);
        updateState(timeToSetup, progress);
    }

    void checkUpdates() {
        currentTimer = repository.getTimerById(currentId);
        countdownManager.setupTimer(currentTimer);
        long timeToSetup = prepareTime(true);
        updateState(timeToSetup, 100);
    }

    private void updateState(long timeToSetup, int progress) {
        viewStateData.setValue(currentTimer.getState());
        activeTimerData.setValue(new CountData(
            TimerTransform.millisToString(timeToSetup),
            progress,
            false));
    }

    private long prepareTime(boolean isActive) {
        if (isActive) {
            return countdownManager.getActiveTimeLeft();
        } else {
            return TimerTransform.timeToMillis(currentTimer.getHoursTotal(), currentTimer.getMinutesTotal(), currentTimer.getSecondsTotal());
        }
    }

    private int prepareProgress(boolean isActive) {
        if (isActive) {
            return countdownManager.getActiveProgress();
        } else {
            return 100;
        }
    }

    void onStart() {
        viewStateData.setValue(TimerModel.State.RUN);
        int currentActiveTimerId = countdownManager.getActiveId();

        if (currentTimer.getId() == currentActiveTimerId) {
            countdownManager.onStart();
        } else {
            if (currentActiveTimerId != -1) {
                countdownManager.onStop();
            }
            countdownManager.setupTimer(currentTimer);
            countdownManager.onStart();
        }
    }

    void onPause() {
        viewStateData.setValue(TimerModel.State.PAUSE);
        countdownManager.onPause();
    }

    void onStop() {
        if (currentTimer.getId() == countdownManager.getActiveId()) {
            viewStateData.setValue(TimerModel.State.FINISH);
            countdownManager.onStop();
        }
    }

    void onRestart() {
        if (currentTimer.getId() == countdownManager.getActiveId() &&
            currentTimer.getState() != TimerModel.State.FINISH) {
            viewStateData.setValue(TimerModel.State.RUN);
            countdownManager.onRestart();
        }
    }

    public void deleteTimer() {
        repository.deleteTimer(currentTimer.getId());
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        countdownManager.setTickListener(null);
    }
}
