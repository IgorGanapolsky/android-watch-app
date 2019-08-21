package com.igorganapolsky.vibratingwatchapp.presentation.settings.step;

import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.igorganapolsky.vibratingwatchapp.R;
import com.igorganapolsky.vibratingwatchapp.util.StepActionListener;
import com.igorganapolsky.vibratingwatchapp.domain.model.Time;
import com.igorganapolsky.vibratingwatchapp.presentation.settings.SetTimerActivity;
import com.igorganapolsky.vibratingwatchapp.presentation.settings.SetTimerViewModel;
import com.igorganapolsky.vibratingwatchapp.presentation.ViewModelFactory;
import com.triggertrap.seekarc.SeekArc;

import java.util.Locale;
import java.util.Objects;

public class SetTimerTimeFragment extends Fragment implements View.OnClickListener, SeekArc.OnSeekArcChangeListener {

    private SetTimerViewModel mViewModel;
    private SeekArc seekArc;

    private int activeColor;
    private int inactiveColor;

    private TextView tvLabel;
    private TextView tvLabelMeasure;
    private TextView tvHours;
    private TextView tvMinutes;
    private TextView tvSeconds;

    private StepActionListener actionListener;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        actionListener = (SetTimerActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.set_timer_time_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity()), ViewModelFactory.getInstance()).get(SetTimerViewModel.class);

        setupView(view);
        setupObservers();
    }

    private void setupView(View view) {

        activeColor = ContextCompat.getColor(requireContext(), R.color.white_active);
        inactiveColor = ContextCompat.getColor(requireContext(), R.color.white_inactive);

        tvLabel = view.findViewById(R.id.tvLabel);
        tvLabelMeasure = view.findViewById(R.id.tvLabelMeasure);

        tvHours = view.findViewById(R.id.tvHours);
        tvHours.setOnClickListener(this);
        tvMinutes = view.findViewById(R.id.tvMinutes);
        tvMinutes.setOnClickListener(this);
        tvSeconds = view.findViewById(R.id.tvSeconds);
        tvSeconds.setOnClickListener(this);

        seekArc = view.findViewById(R.id.seekArc);
        seekArc.setOnSeekArcChangeListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvHours:
                mViewModel.setSelection(Time.HOURS);
                break;
            case R.id.tvMinutes:
                mViewModel.setSelection(Time.MINUTES);
                break;
            case R.id.tvSeconds:
                mViewModel.setSelection(Time.SECONDS);
                break;
        }
    }

    @Override
    public void onProgressChanged(SeekArc seekArc, int progress, boolean byUser) {
        if (byUser) {
            mViewModel.setTimeSelection(progress);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekArc seekArc) {
        actionListener.onActionStart();
    }

    @Override
    public void onStopTrackingTouch(SeekArc seekArc) {
        actionListener.onActionEnd();
    }

    private void setupObservers() {
        mViewModel.getSetupData().observe(Objects.requireNonNull(getActivity()), this::setSelection);
    }

    private void setSelection(Time selection) {
        seekArc.setProgress(mViewModel.calculateProgress());

        tvLabelMeasure.setText(selection.getShortcut());
        tvLabel.setText(String.format(Locale.ENGLISH, "%d", mViewModel.getCurrentTimeValue()));

        switch (selection) {
            case HOURS:
                tvHours.setTextColor(activeColor);
                tvMinutes.setTextColor(inactiveColor);
                tvSeconds.setTextColor(inactiveColor);
                break;
            case MINUTES:
                tvMinutes.setTextColor(activeColor);
                tvHours.setTextColor(inactiveColor);
                tvSeconds.setTextColor(inactiveColor);
                break;
            case SECONDS:
                tvSeconds.setTextColor(activeColor);
                tvHours.setTextColor(inactiveColor);
                tvMinutes.setTextColor(inactiveColor);
                break;
        }
    }
}

