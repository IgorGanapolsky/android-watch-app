package com.igorganapolsky.vibratingwatchapp.presentation.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import com.igorganapolsky.vibratingwatchapp.domain.repo.Repository;
import com.igorganapolsky.vibratingwatchapp.domain.model.TimerModel;
import com.igorganapolsky.vibratingwatchapp.core.timer.ICountdownManager;

import java.util.List;

public class TimerListViewModel extends ViewModel {

    private Repository repository;

    private final ICountdownManager countdownManager;

    public TimerListViewModel(Repository repository, ICountdownManager countdownManager) {
        this.repository = repository;
        this.countdownManager = countdownManager;
        countdownManager.observeActiveModel().observeForever(activeObserver);
    }

    private final Observer<TimerModel> activeObserver = model -> {
        if (model == null) return;
        if (model.getState() == TimerModel.State.RUNNING && model.getState() == TimerModel.State.FINISHED) {
            repository.updateTimerState(model.getId(), model.getState());
        }
    };

    LiveData<List<TimerModel>> getAllTimers() {
        return repository.getAll();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        countdownManager.observeActiveModel().removeObserver(activeObserver);
    }
}
