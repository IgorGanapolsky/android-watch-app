package com.igorganapolsky.vibratingwatchapp.presentation.details.dialog;

import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.igorganapolsky.vibratingwatchapp.R;
import com.igorganapolsky.vibratingwatchapp.presentation.details.TimerDetailsViewModel;
import com.igorganapolsky.vibratingwatchapp.core.util.ViewModelFactory;

import java.util.Objects;

public class TimerDeleteDialogFragment extends Fragment implements View.OnClickListener {

    private TimerDetailsViewModel mViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.timer_delete_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity()), ViewModelFactory.getInstance()).get(TimerDetailsViewModel.class);

        view.findViewById(R.id.ivCancel).setOnClickListener(this);
        view.findViewById(R.id.ivApprove).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivApprove:
                mViewModel.deleteTimer();
                getActivity().finish();
                break;
            case R.id.ivCancel:
                getFragmentManager().popBackStack();
                break;
        }
    }
}
