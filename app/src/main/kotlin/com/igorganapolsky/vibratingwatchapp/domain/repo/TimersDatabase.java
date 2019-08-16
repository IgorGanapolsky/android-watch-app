package com.igorganapolsky.vibratingwatchapp.domain.repo;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {TimerEntity.class}, version = 7, exportSchema = false)
public abstract class TimersDatabase extends RoomDatabase {

    public abstract TimersDao timersDao();
}
