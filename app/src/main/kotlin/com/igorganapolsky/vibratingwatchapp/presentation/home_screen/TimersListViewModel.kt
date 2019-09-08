package com.igorganapolsky.vibratingwatchapp.presentation.home_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.igorganapolsky.vibratingwatchapp.data.IRepository
import com.igorganapolsky.vibratingwatchapp.domain.model.TimerModel
import com.igorganapolsky.vibratingwatchapp.core.ICountdownController

class TimersListViewModel(
    private val repository: IRepository,
    private val countdownManager: ICountdownController
) : ViewModel() {

    private val activeObserver = { model: TimerModel ->
        if (model.state === TimerModel.State.RUNNING && model.state === TimerModel.State.FINISHED) {
            repository.updateTimerState(model.id, model.state)
        }
    }

    internal val allTimers: LiveData<List<TimerModel>>
        get() = repository.getAll

    init {
        countdownManager.observeActiveModel().observeForever(activeObserver)
    }

    override fun onCleared() {
        super.onCleared()
        countdownManager.observeActiveModel().removeObserver(activeObserver)
    }
}
