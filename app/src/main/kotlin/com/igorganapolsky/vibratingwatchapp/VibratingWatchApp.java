package com.igorganapolsky.vibratingwatchapp;

import android.app.Application;
import androidx.room.Room;
import android.content.Context;
import android.os.Debug;
import android.os.Vibrator;
import com.igorganapolsky.vibratingwatchapp.presentation.ViewModelFactory;
import com.igorganapolsky.vibratingwatchapp.domain.repo.Repository;
import com.igorganapolsky.vibratingwatchapp.domain.repo.WatchRepository;
import com.igorganapolsky.vibratingwatchapp.domain.repo.TimersDatabase;
import com.igorganapolsky.vibratingwatchapp.core.timer.ICountdownManager;
import com.igorganapolsky.vibratingwatchapp.core.timer.CountdownManagerImpl;
import com.igorganapolsky.vibratingwatchapp.core.vibration.IVibrationManager;
import com.igorganapolsky.vibratingwatchapp.core.vibration.VibrationManagerImpl;

import java.util.concurrent.Executors;

public class VibratingWatchApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Debug.startMethodTracing("timer");

        /* simple self implemented DI */

        // step 1 - > create database instance;
        TimersDatabase database = Room.databaseBuilder(this, TimersDatabase.class, "timers")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build();


        // step 2 - > create repository;
        Repository repository = new WatchRepository(database, Executors.newSingleThreadExecutor());

        // step 3 -> create os vibrator wrapper
        IVibrationManager beepManager = new VibrationManagerImpl((Vibrator) getSystemService(Context.VIBRATOR_SERVICE));

        // step 4- > create countdown manager;
        ICountdownManager countdownManager = new CountdownManagerImpl(beepManager);

        // step 5 > create view model factory;
        ViewModelFactory.initFactory(repository, countdownManager);

        Debug.stopMethodTracing();
    }
}
