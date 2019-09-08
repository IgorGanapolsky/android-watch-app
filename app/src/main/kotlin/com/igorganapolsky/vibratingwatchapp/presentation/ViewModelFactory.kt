package com.igorganapolsky.vibratingwatchapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import com.igorganapolsky.vibratingwatchapp.core.ICountdownController
import com.igorganapolsky.vibratingwatchapp.data.IRepository
import com.igorganapolsky.vibratingwatchapp.presentation.home_screen.TimerViewModel
import com.igorganapolsky.vibratingwatchapp.presentation.details_screen.TimerDetailsViewModel
import com.igorganapolsky.vibratingwatchapp.presentation.home_screen.TimersListViewModel

class ViewModelFactory private constructor(
    private val repository: IRepository,
    private val countdownManager: ICountdownController
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val viewModel: ViewModel
        if (modelClass.isAssignableFrom(TimerViewModel::class.java)) {
            viewModel = TimerViewModel(repository)
        } else if (modelClass.isAssignableFrom(TimersListViewModel::class.java)) {
            viewModel = TimersListViewModel(repository, countdownManager)
        } else if (modelClass.isAssignableFrom(TimerDetailsViewModel::class.java)) {
            viewModel = TimerDetailsViewModel(repository, countdownManager)
        } else {
            throw IllegalStateException("No associated view model with " + modelClass.name)
        }
        return viewModel as T
    }

    companion object {
        private var factory: ViewModelFactory? = null

        fun initFactory(
            repository: IRepository,
            countdownController: ICountdownController
        ) {
            if (factory == null) {
                factory = ViewModelFactory(repository, countdownController)
            }
        }

        val instance: ViewModelProvider.Factory?
            get() = factory
    }
}
