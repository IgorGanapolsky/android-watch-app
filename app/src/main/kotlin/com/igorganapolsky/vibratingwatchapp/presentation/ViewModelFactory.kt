package com.igorganapolsky.vibratingwatchapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.igorganapolsky.vibratingwatchapp.data.ITimersRepository
import com.igorganapolsky.vibratingwatchapp.presentation.home_screen.NewTimerViewModel
import com.igorganapolsky.vibratingwatchapp.presentation.home_screen.ExistingTimersListViewModel
import com.igorganapolsky.vibratingwatchapp.presentation.existing_timer_info.TimerControlViewModel
import com.igorganapolsky.vibratingwatchapp.domain.usecase.ICountdownController

class ViewModelFactory private constructor(
    private val repository: ITimersRepository,
    private val countdownManager: ICountdownController
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val viewModel: ViewModel
        if (modelClass.isAssignableFrom(NewTimerViewModel::class.java)) {
            viewModel = NewTimerViewModel(repository)
        } else if (modelClass.isAssignableFrom(ExistingTimersListViewModel::class.java)) {
            viewModel = ExistingTimersListViewModel(repository, countdownManager)
        } else if (modelClass.isAssignableFrom(TimerControlViewModel::class.java)) {
            viewModel = TimerControlViewModel(repository, countdownManager)
        } else {
            throw IllegalStateException("No associated view model with " + modelClass.name)
        }
        return viewModel as T
    }

    companion object {
        private lateinit var factory: ViewModelFactory

        fun initFactory(repository: ITimersRepository, countdownController: ICountdownController) {
            factory = ViewModelFactory(repository, countdownController)
        }

        val instance: ViewModelProvider.Factory?
            get() = factory
    }
}
