package com.igorganapolsky.vibratingwatchapp.presentation.home_screen.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.igorganapolsky.vibratingwatchapp.domain.TimerModel

/**
 * Data model for viewing all existing timers.
 */
class TimersListViewModel(
//    private val repository: ITimersRepository
//    ,countDownController: ICountdownController
) : ViewModel() {

    private val _allTimers = MutableLiveData<List<TimerModel>>()
    val allTimers: LiveData<List<TimerModel>>
        get() = _allTimers //repository.getAllTimers()

    private val activeObserver = { model: TimerModel ->
        if (model.state === TimerModel.State.RUNNING && model.state === TimerModel.State.FINISHED) {
//            repository.updateTimerState(model.id, model.state)
        }
    }

    init {
//        countdownManager.observeActiveModel().observeForever(activeObserver)
    }

}
