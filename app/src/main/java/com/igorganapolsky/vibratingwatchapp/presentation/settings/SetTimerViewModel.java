package com.igorganapolsky.vibratingwatchapp.presentation.settings;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import com.igorganapolsky.vibratingwatchapp.domain.Repository;
import com.igorganapolsky.vibratingwatchapp.domain.model.BuzzSetup;
import com.igorganapolsky.vibratingwatchapp.domain.model.TimerModel;
import com.igorganapolsky.vibratingwatchapp.domain.model.TimerSetup;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SetTimerViewModel extends ViewModel {

    enum Type {NEW, EDIT}

    private final Repository repository;

    private final MutableLiveData<TimerModel> timerData = new MutableLiveData<>();
    private final MutableLiveData<TimerSetup> setupData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> swipeState = new MutableLiveData<>();
    private final MutableLiveData<BuzzSetup> buzzData = new MutableLiveData<>();

    private Type currentType = Type.NEW;
    private TimerModel currentTimer;
    private TimerSetup setup;

    public SetTimerViewModel(Repository repository) {
        this.repository = repository;
        this.currentTimer = TimerModel.createDefault();
        this.setup = TimerSetup.HOURS;
    }

    void setCurrentModelId(int currentId) {
        if (currentId != 0) {
            currentType = Type.EDIT;
            currentTimer = repository.getTimerById(currentId);
        }
        swipeState.setValue(!currentTimer.isDefaultTime());
        setupData.setValue(setup);
        timerData.setValue(currentTimer);
    }

    public LiveData<TimerModel> getTimerData() {
        return timerData;
    }

    public LiveData<TimerSetup> getSetupData() {
        return setupData;
    }

    public LiveData<BuzzSetup> getBuzzData() {
        return buzzData;
    }

    LiveData<Boolean> getSwipeState() {
        return swipeState;
    }

    public int calculateProgress() {
        return (int) ((double) currentTimer.getValue(setup) / setup.getMeasure() * 100);
    }

    public void setTimerRepeat(int repeatValue) {
        currentTimer.setRepeat(repeatValue + 1);
    }

    public void setBuzz(BuzzSetup newBuzz) {
        currentTimer.setBuzzTime(newBuzz.getBuzzTime());
        currentTimer.setBuzzCount(newBuzz.getBuzzCount());
        currentTimer.setType(newBuzz.getBuzzType());
    }

    public void setSelection(TimerSetup newSelection) {
        this.setup = newSelection;
        setupData.setValue(setup);
    }

    public void setTimeSelection(int progress) {
        int calculatedValue = (int) (setup.getMeasure() / 100 * progress);

        switch (setup) {
            case HOURS:
                currentTimer.setHoursTotal(calculatedValue);
                break;
            case MINUTES:
                currentTimer.setMinutesTotal(calculatedValue);
                break;
            case SECONDS:
                currentTimer.setSecondsTotal(calculatedValue);
                break;
        }

        setupData.setValue(setup);
        swipeState.setValue(!currentTimer.isDefaultTime());
    }

    public int getCurrentTimeValue() {
        return currentTimer.getValue(setup);
    }

    public int getRepeatPosition() {
        return currentTimer.getRepeat() - 1 >= 0 ? currentTimer.getRepeat() - 1 : 0;
    }

    void saveTimer() {
        if (currentType == Type.NEW) {
            repository.saveTimer(currentTimer);
        } else {
            repository.updateTimer(currentTimer);
        }
    }
}
