package com.igorganapolsky.vibratingwatchapp.presentation.home_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.igorganapolsky.vibratingwatchapp.data.ITimersRepository
import com.igorganapolsky.vibratingwatchapp.domain.usecase.ICountdownController
import com.igorganapolsky.vibratingwatchapp.domain.model.TimerModel

/**
 * Data model for viewing all existing timers.
 */
class ExistingTimersListViewModel(
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
        get() = repository.getAll()

}
