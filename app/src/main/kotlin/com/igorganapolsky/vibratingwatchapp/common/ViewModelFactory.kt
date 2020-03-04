package com.igorganapolsky.vibratingwatchapp.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.igorganapolsky.vibratingwatchapp.data.ITimersRepository
import com.igorganapolsky.vibratingwatchapp.presentation.new_timer_creation_screen.viewmodel.NewTimerViewModel
import com.igorganapolsky.vibratingwatchapp.presentation.home_screen.viewmodel.TimersListViewModel
import com.igorganapolsky.vibratingwatchapp.presentation.timer_edit_screen.viewmodel.ExistingTimerViewModel

class ViewModelFactory private constructor(
    private val repository: ITimersRepository,
    private val countdownManager: ICountdownController
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val viewModel: ViewModel
        if (modelClass.isAssignableFrom(NewTimerViewModel::class.java)) {
            viewModel =
                NewTimerViewModel(
                    repository
                )
        } else if (modelClass.isAssignableFrom(TimersListViewModel::class.java)) {
            viewModel =
                TimersListViewModel(
                    repository,
                    countdownManager
                )
        } else if (modelClass.isAssignableFrom(ExistingTimerViewModel::class.java)) {
            viewModel =
                ExistingTimerViewModel(
                    repository,
                    countdownManager
                )
        } else {
            throw IllegalStateException("No associated view model with " + modelClass.name)
        }
        return viewModel as T
    }

    companion object {
        private lateinit var factory: ViewModelFactory

        fun initFactory(repository: ITimersRepository, countdownController: ICountdownController) {
            factory =
                ViewModelFactory(
                    repository,
                    countdownController
                )
        }

        val instance: ViewModelProvider.Factory?
            get() = factory
    }
}
