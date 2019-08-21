package com.igorganapolsky.vibratingwatchapp.presentation.settings;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.igorganapolsky.vibratingwatchapp.domain.repo.Repository;
import com.igorganapolsky.vibratingwatchapp.domain.model.VibrationModel;
import com.igorganapolsky.vibratingwatchapp.domain.model.TimerModel;
import com.igorganapolsky.vibratingwatchapp.domain.model.Time;

public class SetTimerViewModel extends ViewModel {

    enum Type {NEW, EDIT}

    private final Repository repository;

    private final MutableLiveData<TimerModel> timerData = new MutableLiveData<>();
    private final MutableLiveData<Time> setupData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> swipeState = new MutableLiveData<>();
    private final MutableLiveData<VibrationModel> buzzData = new MutableLiveData<>();

    private Type currentType = Type.NEW;
    private TimerModel currentTimer;
    private Time setup;

    public SetTimerViewModel(Repository repository) {
        this.repository = repository;
        this.currentTimer = TimerModel.Companion.createDefault();
        this.setup = Time.HOURS;
    }

    void setCurrentModelId(int currentId) {
        if (currentId >= 0) {
            currentType = Type.EDIT;
            currentTimer = repository.getTimerById(currentId);
        }
        swipeState.setValue(currentTimer.hasTime());
        setupData.setValue(setup);
        timerData.setValue(currentTimer);
    }

    public LiveData<TimerModel> getTimerData() {
        return timerData;
    }

    public LiveData<Time> getSetupData() {
        return setupData;
    }

    public LiveData<VibrationModel> getBuzzData() {
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

    public void setBuzz(VibrationModel newBuzz) {
        currentTimer.setVibrationTimeInSecs(newBuzz.getBuzzTime());
        currentTimer.setVibrationCount(newBuzz.getBuzzCount());
        currentTimer.setType(newBuzz.getBuzzType());
    }

    public void setSelection(Time newSelection) {
        this.setup = newSelection;
        setupData.setValue(setup);
    }

    public void setTimeSelection(int progress) {
        int calculatedValue = (int) (setup.getMeasure() / 100 * progress);

        switch (setup) {
            case HOURS:
                currentTimer.setHours(calculatedValue);
                break;
            case MINUTES:
                currentTimer.setMinutes(calculatedValue);
                break;
            case SECONDS:
                currentTimer.setSeconds(calculatedValue);
                break;
        }

        setupData.setValue(setup);
        swipeState.setValue(currentTimer.hasTime());
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
