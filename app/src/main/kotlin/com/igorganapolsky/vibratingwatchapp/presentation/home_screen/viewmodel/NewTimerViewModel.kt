package com.igorganapolsky.vibratingwatchapp.presentation.home_screen.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.igorganapolsky.vibratingwatchapp.data.ITimersRepository
import com.igorganapolsky.vibratingwatchapp.data.TimeMeasurement
import com.igorganapolsky.vibratingwatchapp.domain.VibrationModel
import com.igorganapolsky.vibratingwatchapp.domain.TimerModel

/**
 * Data model for creating a new timer.
 */
class NewTimerViewModel(private val repository: ITimersRepository) : ViewModel() {
    private val timerData = MutableLiveData<TimerModel>()
    private val countdownData = MutableLiveData<TimeMeasurement>()
    private val swipeState = MutableLiveData<Boolean>()
    private val buzzData = MutableLiveData<VibrationModel>()

    private var currentType =
        Type.NEW
    private var currentTimer: TimerModel
    private var setup: TimeMeasurement

    val currentTimeValue: Int
        get() = currentTimer.getValue(setup)

    val repeatPosition: Int
        get() = if (currentTimer.repeat - 1 >= 0) currentTimer.repeat - 1 else 0

    internal enum class Type {
        NEW, EDIT
    }

    init {
        this.currentTimer = TimerModel.createDefault()
        this.setup = TimeMeasurement.HOURS
    }

    internal fun setCurrentModelId(currentId: Int) {
        if (currentId >= 0) {
            currentType =
                Type.EDIT
            currentTimer = repository.getTimerById(currentId)
        }
        swipeState.value = currentTimer.hasTime()
        countdownData.value = setup
        timerData.value = currentTimer
    }

    fun getTimerData(): LiveData<TimerModel> {
        return timerData
    }

    fun getCountdownData(): LiveData<TimeMeasurement> {
        return countdownData
    }

    fun getVibrationData(): LiveData<VibrationModel> {
        return buzzData
    }

    internal fun getSwipeState(): LiveData<Boolean> {
        return swipeState
    }

    fun calculateProgress(): Int {
        return (currentTimer.getValue(setup).toDouble() / setup.measure * 100).toInt()
    }

    fun setTimerRepeat(repeatValue: Int) {
        currentTimer.repeat = repeatValue + 1
    }

    fun setBuzz(newBuzz: VibrationModel) {
        currentTimer.vibrationTimeInSecs = newBuzz.buzzTime
        currentTimer.vibrationCount = newBuzz.buzzCount
        currentTimer.type = newBuzz.buzzType
    }

    fun setSelection(newSelection: TimeMeasurement) {
        this.setup = newSelection
        countdownData.value = setup
    }

    fun setTimeSelection(progress: Int) {
        val calculatedValue = (setup.measure / 100 * progress).toInt()

        when (setup) {
            TimeMeasurement.HOURS -> currentTimer.hours = calculatedValue
            TimeMeasurement.MINUTES -> currentTimer.minutes = calculatedValue
            TimeMeasurement.SECONDS -> currentTimer.seconds = calculatedValue
        }

        countdownData.value = setup
        swipeState.value = currentTimer.hasTime()
    }

    internal fun saveTimer() {
        if (currentType == Type.NEW) {
            repository.saveTimer(currentTimer)
        } else {
            repository.updateTimer(currentTimer)
        }
    }
}
