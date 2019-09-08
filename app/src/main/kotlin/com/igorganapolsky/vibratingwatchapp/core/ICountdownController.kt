package com.igorganapolsky.vibratingwatchapp.core

import androidx.lifecycle.LiveData
import com.igorganapolsky.vibratingwatchapp.domain.model.TimerModel

interface ICountdownController {

    val activeTimerModel: TimerModel

    val activeId: Int

    val activeTimeLeft: Long

    val activeProgress: Int

    val isActive: Boolean

    fun hasMoreRepeats(): Boolean

    fun observeActiveModel(): LiveData<TimerModel>

    fun setTickListener(listener: ITickListener?)

    fun setupTimer(timerModel: TimerModel)

    fun onStart()

    fun onPause()

    fun onStop()

    fun onRestart()

    fun onNextLap(): Boolean
}
