package com.igorganapolsky.vibratingwatchapp.presentation.settings.step;

import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.wear.widget.WearableRecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.igorganapolsky.vibratingwatchapp.R;
import com.igorganapolsky.vibratingwatchapp.util.RecyclerViewSnapLayoutManager;
import com.igorganapolsky.vibratingwatchapp.presentation.ViewModelFactory;
import com.igorganapolsky.vibratingwatchapp.presentation.settings.SetTimerViewModel;
import com.igorganapolsky.vibratingwatchapp.presentation.settings.adapter.HolderClickListener;
import com.igorganapolsky.vibratingwatchapp.presentation.settings.adapter.RepeatsAdapter;

public class SetTimerRepeatFragment extends Fragment implements HolderClickListener {

    private WearableRecyclerView wrvRepeats;
    private SetTimerViewModel mViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = ViewModelProviders.of(requireActivity(), ViewModelFactory.getInstance()).get(SetTimerViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.set_timer_repeat_fragment, container, false);
        setupView(rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupObservers();
    }

    private void setupView(View rootView) {
        wrvRepeats = rootView.findViewById(R.id.wrvRepeats);

        RepeatsAdapter adapter = new RepeatsAdapter(this);
        RecyclerViewSnapLayoutManager layoutManager = new RecyclerViewSnapLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL);
        layoutManager.setItemSelectListener((int pos) -> mViewModel.setTimerRepeat(pos));

        wrvRepeats.setAdapter(adapter);
        wrvRepeats.setLayoutManager(layoutManager);
    }

    private void setupObservers() {
        mViewModel.getTimerData().observe(this, (timerValue) -> {
            if (timerValue == null) return;
            wrvRepeats.smoothScrollToPosition(mViewModel.getRepeatPosition());
        });
    }

    @Override
    public void onHolderClick(int position) {
        wrvRepeats.smoothScrollToPosition(position);
    }
}
