package com.igorganapolsky.vibratingwatchapp.presentation.new_timer_creation_screen.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.igorganapolsky.vibratingwatchapp.data.ITimersRepository
import com.igorganapolsky.vibratingwatchapp.data.TimeMeasurement
import com.igorganapolsky.vibratingwatchapp.domain.TimerModel
import com.igorganapolsky.vibratingwatchapp.domain.VibrationModel

/**
 * Data model for creating a new timer.
 */
class NewTimerViewModel(private val repository: ITimersRepository) : ViewModel() {
    private val timerData = MutableLiveData<TimerModel>()
    private val countdownData = MutableLiveData<TimeMeasurement>()
    private val swipeState = MutableLiveData<Boolean>()
    private val buzzData = MutableLiveData<VibrationModel>()

    private var currentType = Type.NEW
    private var timerModel: TimerModel
    private var timeMeasurement: TimeMeasurement

    val currentTimeValue: Int
        get() = timerModel.getValue(timeMeasurement)

    val repeatPosition: Int
        get() = if (timerModel.repeat - 1 >= 0) timerModel.repeat - 1 else 0

    internal enum class Type {
        NEW, EDIT
    }

    init {
        this.timerModel = TimerModel.createDefault()
        this.timeMeasurement = TimeMeasurement.HOURS
    }

    internal fun setCurrentModelId(currentId: Int) {
        if (currentId >= 0) {
            currentType =
                Type.EDIT
            timerModel = repository.getTimerById(currentId)
        }
        swipeState.value = timerModel.hasTime()
        countdownData.value = timeMeasurement
        timerData.value = timerModel
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
        return (timerModel.getValue(timeMeasurement).toDouble() / timeMeasurement.measure * 100).toInt()
    }

    fun setTimerRepeat(repeatValue: Int) {
        timerModel.repeat = repeatValue + 1
    }

    fun setBuzz(newBuzz: VibrationModel) {
        timerModel.vibrationTimeInSecs = newBuzz.buzzTime
        timerModel.vibrationCount = newBuzz.buzzCount
        timerModel.type = newBuzz.buzzType
    }

    fun setSelection(newSelection: TimeMeasurement) {
        this.timeMeasurement = newSelection
        countdownData.value = timeMeasurement
    }

    fun setTimeSelection(progress: Int) {
        val calculatedValue = (timeMeasurement.measure / 100 * progress).toInt()

        when (timeMeasurement) {
            TimeMeasurement.HOURS -> timerModel.hours = calculatedValue
            TimeMeasurement.MINUTES -> timerModel.minutes = calculatedValue
            TimeMeasurement.SECONDS -> timerModel.seconds = calculatedValue
        }

        countdownData.value = timeMeasurement
        swipeState.value = timerModel.hasTime()
    }

    internal fun saveTimer() {
        if (currentType == Type.NEW) {
            repository.saveTimer(timerModel)
        } else {
            repository.updateTimer(timerModel)
        }
    }
}
