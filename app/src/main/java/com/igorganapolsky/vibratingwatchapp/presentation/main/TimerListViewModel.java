package com.igorganapolsky.vibratingwatchapp.presentation.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import com.igorganapolsky.vibratingwatchapp.domain.Repository;
import com.igorganapolsky.vibratingwatchapp.domain.model.TimerModel;
import com.igorganapolsky.vibratingwatchapp.manager.timer.CountdownManager;

import java.util.List;

public class TimerListViewModel extends ViewModel {

    private Repository repository;
    private final CountdownManager countdownManager;

    public TimerListViewModel(Repository repository, CountdownManager countdownManager) {
        this.repository = repository;
        this.countdownManager = countdownManager;
        countdownManager.observeActiveModel().observeForever(activeObserver);
    }

    private Observer<TimerModel> activeObserver = model -> {
        if (model == null) return;
        if (model.getState() == TimerModel.State.RUN && model.getState() == TimerModel.State.FINISH) {
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
