package com.igorganapolsky.vibratingwatchapp

import android.app.Application
import android.content.Context
import android.os.StrictMode
import android.os.StrictMode.VmPolicy
import android.os.Vibrator
import androidx.room.Room
import com.igorganapolsky.vibratingwatchapp.data.TimersDatabase
import com.igorganapolsky.vibratingwatchapp.data.WatchRepository
import com.igorganapolsky.vibratingwatchapp.common.ViewModelFactory
import com.igorganapolsky.vibratingwatchapp.common.CountdownControllerImpl
import com.igorganapolsky.vibratingwatchapp.common.CrashlyticsTree
import com.igorganapolsky.vibratingwatchapp.common.VibrationControllerImpl
import timber.log.Timber
import java.util.concurrent.Executors

class VibratingWatchApp : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(CrashlyticsTree())
        }

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
            VibrationControllerImpl(
                getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            )

        // step 4- > create countdown manager;
        val countdownManager =
            CountdownControllerImpl(
                beepManager
            )

        // step 5 > create view model factory;
        ViewModelFactory.initFactory(repository, countdownManager)
    }

    private fun enableStrictMode() {
        StrictMode.setThreadPolicy(
            StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()
                .penaltyLog()
                .build()
        )
        StrictMode.setVmPolicy(
            VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .detectLeakedClosableObjects()
                .penaltyLog()
                .penaltyDeath()
                .build()
        )
    }

}
