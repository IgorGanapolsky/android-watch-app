package com.igorganapolsky.vibratingwatchapp.domain;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import com.igorganapolsky.vibratingwatchapp.core.util.Mappers;
import com.igorganapolsky.vibratingwatchapp.domain.local.TimersDatabase;
import com.igorganapolsky.vibratingwatchapp.domain.local.entity.TimerEntity;
import com.igorganapolsky.vibratingwatchapp.domain.model.TimerModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class WatchRepository implements Repository {

    private TimersDatabase timerDb;
    private ExecutorService executor;

    public WatchRepository(TimersDatabase timerDb, ExecutorService executor) {
        this.timerDb = timerDb;
        this.executor = executor;
        disableAll();
    }

    @Override
    public TimerModel getTimerById(int id) {
        TimerEntity timer = timerDb.timersDao().getById(id);
        return Mappers.mapToTimerModel(timer);
    }

    @Override
    public LiveData<List<TimerModel>> getAll() {
        return Transformations.map(timerDb.timersDao().getAll(), (list -> {
            List<TimerModel> mappedList = new ArrayList<>(list.size());
            for (TimerEntity timer : list) {
                mappedList.add(Mappers.mapToTimerModel(timer));
            }
            return mappedList;
        }));
    }

    @Override
    public void updateTimer(TimerModel model) {
        executor.execute(() -> {
            TimerEntity timerEntity = Mappers.mapToTimerEntity(model);
            timerEntity.setId(model.getId());
            timerDb.timersDao().update(timerEntity);
        });
    }

    @Override
    public void saveTimer(TimerModel model) {
        executor.execute(() -> timerDb.timersDao().insert(Mappers.mapToTimerEntity(model)));
    }

    @Override
    public void deleteTimer(int id) {
        executor.execute(() -> timerDb.timersDao().deleteById(id));
    }

    @Override
    public void updateTimerState(int timerId, TimerModel.State newState) {
        executor.execute(() -> timerDb.timersDao().updateTimerState(timerId, newState.name()));
    }

    private void disableAll() {
        executor.execute(() -> timerDb.timersDao().disableAll(TimerModel.State.FINISH.name()));
    }
}
