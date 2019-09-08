package com.igorganapolsky.vibratingwatchapp.presentation.details_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.igorganapolsky.vibratingwatchapp.core.ICountdownController
import com.igorganapolsky.vibratingwatchapp.core.ITickListener
import com.igorganapolsky.vibratingwatchapp.core.TimerTransform
import com.igorganapolsky.vibratingwatchapp.data.CountModel
import com.igorganapolsky.vibratingwatchapp.data.IRepository
import com.igorganapolsky.vibratingwatchapp.domain.model.TimerModel

class TimerDetailsViewModel(
    private val repository: IRepository,
    private val countdownController: ICountdownController
) : ViewModel(), ITickListener {

    internal val activeTimerData = MutableLiveData<CountModel>()
    val viewStateData = MutableLiveData<TimerModel.State>()

    private lateinit var currentTimer: TimerModel
    private var currentId = TimerModel.UNDEFINE_ID
    private val countData: CountModel = CountModel.default

    init {
        this.countdownController.setTickListener(this)
    }

    internal fun getActiveTimerData(): LiveData<CountModel> {
        return activeTimerData
    }

    internal fun getViewStateData(): LiveData<TimerModel.State> {
        return viewStateData
    }

    override fun onTick(timeLeft: Long?, progress: Int) {
        if (currentId == countdownController.activeId) {
            updateCountData(timeLeft, progress, true)
            activeTimerData.value = countData
        }
    }

    override fun onLapEnd(timeLeft: Long?, progress: Int) {
        if (currentId == countdownController.activeId) {
            viewStateData.value = TimerModel.State.BEEPING
            updateCountData(timeLeft, progress, true)
            activeTimerData.value = countData
        }
    }

    override fun onFinish(timeLeft: Long?, progress: Int, isStop: Boolean) {
        if (currentId == countdownController.activeId) {
            viewStateData.value = TimerModel.State.FINISHED
            updateCountData(timeLeft, progress, isStop)
            activeTimerData.value = countData
        }
    }

    private fun updateCountData(timeLeft: Long?, progress: Int, isAnimationNeeded: Boolean) {
        countData.currentTime = TimerTransform.millisToString(timeLeft!!)
        countData.currentProgress = progress
    }

    internal fun prepareData(currentId: Int) {
        val isActive = defineCurrentTimer(currentId)
        val timeToSetup = prepareTime(isActive)
        val progress = prepareProgress(isActive)
        updateState(timeToSetup, progress)
    }

    private fun defineCurrentTimer(currentId: Int): Boolean {
        this.currentId = currentId
        val activeId = countdownController.activeId
        val isActive = this.currentId == activeId && countdownController.isActive

        currentTimer = if (isActive) {
            countdownController.activeTimerModel
        } else {
            repository.getTimerById(this.currentId)
        }
        return isActive
    }

    internal fun checkUpdates() {
        currentTimer = repository.getTimerById(currentId)
        countdownController.setupTimer(currentTimer)
        val timeToSetup = prepareTime(true)
        updateState(timeToSetup, 100)
    }

    private fun updateState(timeToSetup: Long, progress: Int) {
        viewStateData.value = currentTimer.state
        activeTimerData.value = CountModel(
            TimerTransform.millisToString(timeToSetup),
            progress,
            false
        )
    }

    private fun prepareTime(isActive: Boolean): Long {
        return if (isActive) {
            countdownController.activeTimeLeft
        } else {
            TimerTransform.timeToMillis(
                currentTimer.hours,
                currentTimer.minutes,
                currentTimer.seconds
            )
        }
    }

    private fun prepareProgress(isActive: Boolean): Int {
        return if (isActive) {
            countdownController.activeProgress
        } else {
            100
        }
    }

    internal fun onStart() {
        viewStateData.value = TimerModel.State.RUNNING
        val currentActiveTimerId = countdownController.activeId

        if (currentTimer.id == currentActiveTimerId) {
            countdownController.onStart()
        } else {
            if (currentActiveTimerId != TimerModel.UNDEFINE_ID) {
                countdownController.onStop()
            }
            countdownController.setupTimer(currentTimer)
            countdownController.onStart()
        }
    }

    internal fun onPause() {
        viewStateData.value = TimerModel.State.PAUSED
        countdownController.onPause()
    }

    internal fun onStop() {
        if (currentTimer.id == countdownController.activeId) {
            viewStateData.value = TimerModel.State.FINISHED
            countdownController.onStop()
        }
    }

    internal fun onRestart() {
        if (currentTimer.id == countdownController.activeId && currentTimer.state !== TimerModel.State.FINISHED) {

            viewStateData.value = TimerModel.State.RUNNING
            countdownController.onRestart()
        }
    }

    internal fun onNextLap() {
        if (currentTimer.id == countdownController.activeId) {
            val isNextLapStarted = countdownController.onNextLap()
            val newState =
                if (isNextLapStarted) TimerModel.State.RUNNING else TimerModel.State.FINISHED
            viewStateData.value = newState

        }
    }

    fun deleteTimer() {
        repository.deleteTimer(currentTimer.id)
    }

    override fun onCleared() {
        super.onCleared()
        countdownController.setTickListener(null)
    }
}
