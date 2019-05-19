package com.igorganapolsky.vibratingwatchapp.domain.local;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import com.igorganapolsky.vibratingwatchapp.domain.local.entity.TimerEntity;

@Database(entities = {TimerEntity.class}, version = 7, exportSchema = false)
public abstract class TimersDatabase extends RoomDatabase {

    public abstract TimersDao timersDao();
}
