package com.igorganapolsky.vibratingwatchapp

import android.app.Application
import androidx.room.Room
import android.content.Context
import android.os.Vibrator
import com.igorganapolsky.vibratingwatchapp.presentation.ViewModelFactory
import com.igorganapolsky.vibratingwatchapp.data.WatchRepository
import com.igorganapolsky.vibratingwatchapp.data.TimersDatabase
import com.igorganapolsky.vibratingwatchapp.core.CountdownControllerImpl
import com.igorganapolsky.vibratingwatchapp.core.VibrationControllerImpl

import java.util.concurrent.Executors

class VibratingWatchApp : Application() {

    override fun onCreate() {
        super.onCreate()

        /* simple self implemented DI */

        // step 1 - > create database instance;
        val database = Room.databaseBuilder(this, TimersDatabase::class.java, "timers")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()


        // step 2 - > create repository;
        val repository = WatchRepository(database, Executors.newSingleThreadExecutor())

        // step 3 -> create os vibrator wrapper
        val beepManager =
            VibrationControllerImpl(getSystemService(Context.VIBRATOR_SERVICE) as Vibrator)

        // step 4- > create countdown manager;
        val countdownManager = CountdownControllerImpl(beepManager)

        // step 5 > create view model factory;
        ViewModelFactory.initFactory(repository, countdownManager)
    }
}
