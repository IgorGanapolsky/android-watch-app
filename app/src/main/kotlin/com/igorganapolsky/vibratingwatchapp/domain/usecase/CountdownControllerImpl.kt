package com.igorganapolsky.vibratingwatchapp.domain.model

import android.os.CountDownTimer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.igorganapolsky.vibratingwatchapp.domain.model.model.TimerModel

class CountdownControllerImpl(private val vibrateMgr: IVibrationController) :
    ICountdownController {

    private val activeModelData = MutableLiveData<TimerModel>()

    private lateinit var tickListener: ITickListener
    private var currentCountdownTimer: CountDownTimer? = null

    private lateinit var timerModel: TimerModel
    private var totalTime: Long = 0
    private var repeatCount: Int = 0
    private var timeLeft: Long = 0

    override fun setupTimer(timerModel: TimerModel) {
        this.timerModel = timerModel

        // prepare countdown's data
        val timerTimeInMillis = this.timerModel.timeInMillis
        totalTime = timerTimeInMillis
        timeLeft = timerTimeInMillis
        repeatCount = timerModel.repeat

        // fetch beep manager with data
        vibrateMgr.setup(
            Mappers.mapToBuzzSetup(
                timerModel
            )
        )
    }

    override fun onStart() {
        timerModel.state = TimerModel.State.RUNNING
        activeModelData.postValue(timerModel)
        currentCountdownTimer = prepareCountdownTimer()
        currentCountdownTimer!!.start()
    }

    override fun onPause() {
        timerModel.state = TimerModel.State.PAUSED
        if (currentCountdownTimer != null) {
            currentCountdownTimer!!.cancel()
        }
    }

    override fun onStop() {
        clearCountDown()
        notifyOnFinish(true)
    }

    override fun onRestart() {
        timerModel.state = TimerModel.State.RUNNING
        clearCountDown()
        onStart()
    }

    override fun onNextLap(): Boolean {
        if (repeatCount <= 0) return false
        vibrateMgr.cancel()
        clearCountDown()
        onStart()
        return true
    }

    private fun prepareCountdownTimer(): CountDownTimer {
        val millisecondsLeft = if (timeLeft > 0) timeLeft else totalTime

        return object : CountDownTimer(millisecondsLeft, 100) {
            override fun onTick(millis: Long) {
                timeLeft = millis
                notifyOnTick()
            }

            override fun onFinish() {
                vibrateMgr.start()
                timeLeft = 0L
                repeatCount--
                if (repeatCount <= 0) {
                    notifyOnFinish(false)
                } else {
                    notifyOnLapEnd()
                }
            }
        }
    }

    private fun notifyOnTick() {
        val progress = calculateProgress()
        tickListener.onTick(timeLeft, progress)
    }

    private fun notifyOnFinish(isStop: Boolean) {
        timerModel.state = TimerModel.State.FINISHED
        activeModelData.value = timerModel
        tickListener.onFinish(timeLeft, 100, isStop)
    }

    private fun notifyOnLapEnd() {
        timerModel.state = TimerModel.State.BEEPING
        activeModelData.value = timerModel
        tickListener.onLapEnd(timeLeft, 100)
    }

    private fun clearCountDown() {
        timeLeft = 0L
        if (currentCountdownTimer != null) {
            currentCountdownTimer!!.cancel()
            currentCountdownTimer = null
        }
    }

    private fun calculateProgress(): Int {
        return (timeLeft.toFloat() / totalTime.toFloat() * 100.0).toInt()
    }

    override fun observeActiveModel(): LiveData<TimerModel> {
        return activeModelData
    }

    override fun setTickListener(listener: ITickListener) {
        this.tickListener = listener
    }

    override val activeId: Int
        get() = timerModel.id

    override fun hasMoreRepeats(): Boolean {
        return repeatCount > 0
    }

    override val isActive: Boolean
        get() = timerModel.state !== TimerModel.State.FINISHED

    override val activeTimeLeft: Long
        get() = timeLeft

    override val activeProgress: Int
        get() = if (timerModel.state === TimerModel.State.FINISHED) 100 else calculateProgress()

    override val activeTimerModel: TimerModel
        get() = timerModel

}
