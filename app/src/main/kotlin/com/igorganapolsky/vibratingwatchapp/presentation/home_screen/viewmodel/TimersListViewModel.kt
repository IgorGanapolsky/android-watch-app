package com.igorganapolsky.vibratingwatchapp.presentation.home_screen.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.igorganapolsky.vibratingwatchapp.common.ICountdownController
import com.igorganapolsky.vibratingwatchapp.data.ITimersRepository
import com.igorganapolsky.vibratingwatchapp.domain.TimerModel

/**
 * Data model for viewing all existing timers.
 */
class TimersListViewModel(
    private val repository: ITimersRepository,
    countdownManager: ICountdownController
) : ViewModel() {

    private val activeObserver = { model: TimerModel ->
        if (model.state === TimerModel.State.RUNNING && model.state === TimerModel.State.FINISHED) {
            repository.updateTimerState(model.id, model.state)
        }
    }

    init {
        countdownManager.observeActiveModel().observeForever(activeObserver)
    }

    internal val allTimersLiveData: LiveData<List<TimerModel>>
        get() = repository.getAllTimers()

}
