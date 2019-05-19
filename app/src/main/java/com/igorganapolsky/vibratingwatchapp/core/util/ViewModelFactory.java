package com.igorganapolsky.vibratingwatchapp.core.util;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.annotation.NonNull;
import com.igorganapolsky.vibratingwatchapp.domain.Repository;
import com.igorganapolsky.vibratingwatchapp.manager.timer.CountdownManager;
import com.igorganapolsky.vibratingwatchapp.presentation.details.TimerDetailsViewModel;
import com.igorganapolsky.vibratingwatchapp.presentation.main.TimerListViewModel;
import com.igorganapolsky.vibratingwatchapp.presentation.settings.SetTimerViewModel;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private static ViewModelFactory factory;

    private final Repository repository;
    private final CountdownManager countdownManager;

    private ViewModelFactory(Repository repository, CountdownManager countdownManager) {
        this.repository = repository;
        this.countdownManager = countdownManager;
    }

    public static void initFactory(Repository repository,
                                   CountdownManager countdownManager) {
        if (factory == null) {
            factory = new ViewModelFactory(repository, countdownManager);
        }
    }

    public static ViewModelProvider.Factory getInstance() {
        return factory;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        ViewModel viewModel;
        if (modelClass.isAssignableFrom(SetTimerViewModel.class)) {
            viewModel = new SetTimerViewModel(repository);
        } else if (modelClass.isAssignableFrom(TimerListViewModel.class)) {
            viewModel = new TimerListViewModel(repository, countdownManager);
        } else if (modelClass.isAssignableFrom(TimerDetailsViewModel.class)) {
            viewModel = new TimerDetailsViewModel(repository, countdownManager);
        } else {
            throw new IllegalStateException("No associated view model with " + modelClass.getName());
        }
        return (T) viewModel;
    }
}
