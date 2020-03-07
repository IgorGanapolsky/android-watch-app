package com.igorganapolsky.vibratingwatchapp.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [TimerEntity::class], version = 7, exportSchema = false)
abstract class TimersDatabase : RoomDatabase() {

    abstract fun timersDao(): ITimersDao
}
