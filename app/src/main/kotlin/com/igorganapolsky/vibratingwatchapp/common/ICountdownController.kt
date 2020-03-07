package com.igorganapolsky.vibratingwatchapp.common

import androidx.lifecycle.LiveData
import com.igorganapolsky.vibratingwatchapp.domain.TimerModel

interface ICountdownController {

    val activeTimerModel: TimerModel

    val activeId: Int

    val activeTimeLeft: Long

    val activeProgress: Int

    val isActive: Boolean

    fun hasMoreRepeats(): Boolean

    fun observeActiveModel(): LiveData<TimerModel>

    fun setTickListener(listener: ITickListener)

    fun setupTimer(timerModel: TimerModel)

    fun onStart()

    fun onPause()

    fun onStop()

    fun onRestart()

    fun onNextLap(): Boolean
}
