package com.igorganapolsky.vibratingwatchapp.presentation.settings.step;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.wear.widget.WearableRecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.igorganapolsky.vibratingwatchapp.R;
import com.igorganapolsky.vibratingwatchapp.core.custom.RecyclerViewSnapLayoutManager;
import com.igorganapolsky.vibratingwatchapp.presentation.settings.SetTimerViewModel;
import com.igorganapolsky.vibratingwatchapp.presentation.settings.adapter.HolderClickListener;
import com.igorganapolsky.vibratingwatchapp.presentation.settings.adapter.RepeatsAdapter;
import com.igorganapolsky.vibratingwatchapp.core.util.ViewModelFactory;

public class SetTimerRepeatFragment extends Fragment implements HolderClickListener {

    private WearableRecyclerView wrvRepeats;
    private RecyclerViewSnapLayoutManager layoutManager;
    private SetTimerViewModel mViewModel;
    private RepeatsAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = ViewModelProviders.of(getActivity(), ViewModelFactory.getInstance()).get(SetTimerViewModel.class);
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

        adapter = new RepeatsAdapter(this);
        layoutManager = new RecyclerViewSnapLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL);
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
