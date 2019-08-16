package com.igorganapolsky.vibratingwatchapp.presentation.details;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.igorganapolsky.vibratingwatchapp.domain.repo.Repository;
import com.igorganapolsky.vibratingwatchapp.domain.model.CountData;
import com.igorganapolsky.vibratingwatchapp.domain.model.TimerModel;
import com.igorganapolsky.vibratingwatchapp.core.timer.CountdownManager;
import com.igorganapolsky.vibratingwatchapp.core.timer.TickListener;
import com.igorganapolsky.vibratingwatchapp.util.TimerTransform;

public class TimerDetailsViewModel extends ViewModel implements TickListener {

    private final Repository repository;
    private final CountdownManager countdownManager;

    private final MutableLiveData<CountData> activeTimerData = new MutableLiveData<>();
    private final MutableLiveData<TimerModel.State> viewStateData = new MutableLiveData<>();

    private TimerModel currentTimer;
    private int currentId = TimerModel.UNDEFINE_ID;
    private final CountData countData;

    public TimerDetailsViewModel(Repository repository, CountdownManager countdownManager) {
        this.repository = repository;
        this.countData = CountData.getDefault();
        this.countdownManager = countdownManager;
        this.countdownManager.setTickListener(this);
    }

    LiveData<CountData> getActiveTimerData() {
        return activeTimerData;
    }

    LiveData<TimerModel.State> getViewStateData() {
        return viewStateData;
    }

    @Override
    public void onTick(Long timeLeft, int progress) {
        if (currentId == countdownManager.getActiveId()) {
            updateCountData(timeLeft, progress, true);
            activeTimerData.setValue(countData);
        }
    }

    @Override
    public void onLapEnd(Long timeLeft, int progress) {
        if (currentId == countdownManager.getActiveId()) {
            viewStateData.setValue(TimerModel.State.BEEPING);
            updateCountData(timeLeft, progress, true);
            activeTimerData.setValue(countData);
        }
    }

    @Override
    public void onFinish(Long timeLeft, int progress, boolean isStop) {
        if (currentId == countdownManager.getActiveId()) {
            viewStateData.setValue(TimerModel.State.FINISHED);
            updateCountData(timeLeft, progress, isStop);
            activeTimerData.setValue(countData);
        }
    }

    private void updateCountData(Long timeLeft, int progress, boolean isAnimationNeeded) {
        countData.setCurrentTime(TimerTransform.millisToString(timeLeft));
        countData.setCurrentProgress(progress);
    }

    void prepareData(int currentId) {
        boolean isActive = defineCurrentTimer(currentId);
        long timeToSetup = prepareTime(isActive);
        int progress = prepareProgress(isActive);
        updateState(timeToSetup, progress);
    }

    private boolean defineCurrentTimer(int currentId) {
        this.currentId = currentId;
        int activeId = countdownManager.getActiveId();
        boolean isActive = this.currentId == activeId && countdownManager.isActive();

        if (isActive) {
            currentTimer = countdownManager.getActive();
        } else {
            currentTimer = repository.getTimerById(this.currentId);
        }
        return isActive;
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
            return TimerTransform.timeToMillis(
                currentTimer.getHours(),
                currentTimer.getMinutes(),
                currentTimer.getSeconds());
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
        viewStateData.setValue(TimerModel.State.RUNNING);
        int currentActiveTimerId = countdownManager.getActiveId();

        if (currentTimer.getId() == currentActiveTimerId) {
            countdownManager.onStart();
        } else {
            if (currentActiveTimerId != TimerModel.UNDEFINE_ID) {
                countdownManager.onStop();
            }
            countdownManager.setupTimer(currentTimer);
            countdownManager.onStart();
        }
    }

    void onPause() {
        viewStateData.setValue(TimerModel.State.PAUSED);
        countdownManager.onPause();
    }

    void onStop() {
        if (currentTimer.getId() == countdownManager.getActiveId()) {
            viewStateData.setValue(TimerModel.State.FINISHED);
            countdownManager.onStop();
        }
    }

    void onRestart() {
        if (currentTimer.getId() == countdownManager.getActiveId() &&
            currentTimer.getState() != TimerModel.State.FINISHED) {

            viewStateData.setValue(TimerModel.State.RUNNING);
            countdownManager.onRestart();
        }
    }

    void onNextLap() {
        if (currentTimer.getId() == countdownManager.getActiveId()) {
            boolean isNextLapStarted = countdownManager.onNextLap();
            TimerModel.State newState = isNextLapStarted ? TimerModel.State.RUNNING : TimerModel.State.FINISHED;
            viewStateData.setValue(newState);

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
