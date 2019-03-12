package com.igorganapolsky.vibratingwatchapp.domain.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.igorganapolsky.vibratingwatchapp.domain.local.entity.TimerEntity;

@Database(entities = {TimerEntity.class}, version = 5)
public abstract class TimersDatabase extends RoomDatabase {

    public abstract TimersDao timersDao();
}
