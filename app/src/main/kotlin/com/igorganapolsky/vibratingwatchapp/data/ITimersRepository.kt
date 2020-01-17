package com.igorganapolsky.vibratingwatchapp.data

import androidx.lifecycle.LiveData

import com.igorganapolsky.vibratingwatchapp.domain.model.TimerModel

interface ITimersRepository {

    fun getAll(): LiveData<List<TimerModel>>

    fun getTimerById(timerId: Int): TimerModel

    fun updateTimer(timer: TimerModel)

    fun saveTimer(timer: TimerModel)

    fun deleteTimer(timerId: Int)

    fun updateTimerState(timerId: Int, newState: TimerModel.State)
}
