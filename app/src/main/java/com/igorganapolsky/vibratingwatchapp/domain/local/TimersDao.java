package com.igorganapolsky.vibratingwatchapp.domain.local;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.igorganapolsky.vibratingwatchapp.domain.local.entity.TimerEntity;

import java.util.List;

@Dao
public interface TimersDao {

    @Query("SELECT * FROM TimerEntity")
    LiveData<List<TimerEntity>> getAll();

    @Query("SELECT * FROM TimerEntity WHERE id = :id")
    LiveData<TimerEntity> observeById(int id);

    @Query("SELECT * FROM TimerEntity WHERE id = :id")
    TimerEntity getById(int id);

    @Query("DELETE FROM TimerEntity WHERE id = :id")
    void deleteById(int id);

    @Query("UPDATE TimerEntity SET time_left =:newTimeLeft WHERE id =:timerId")
    void updateTimerLeft(int timerId, long newTimeLeft);

    @Query("UPDATE TimerEntity SET state =:timerState")
    void disableAll(String timerState);

    @Query("UPDATE TimerEntity SET state =:timerState WHERE id =:timerId")
    void updateTimerState(int timerId, String timerState);

    @Insert
    void insert(TimerEntity entity);

    @Update
    void update(TimerEntity entity);
}
