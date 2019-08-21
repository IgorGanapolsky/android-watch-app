package com.igorganapolsky.vibratingwatchapp.presentation.settings;

import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.igorganapolsky.vibratingwatchapp.R;
import com.igorganapolsky.vibratingwatchapp.util.StepActionListener;
import com.igorganapolsky.vibratingwatchapp.util.SwipeRestrictViewPager;
import com.igorganapolsky.vibratingwatchapp.presentation.ViewModelFactory;
import com.igorganapolsky.vibratingwatchapp.domain.repo.TimerEntity;
import com.igorganapolsky.vibratingwatchapp.domain.model.TimerModel;
import com.igorganapolsky.vibratingwatchapp.domain.model.Time;

import java.util.Locale;

import static com.igorganapolsky.vibratingwatchapp.domain.repo.TimerEntity.TIMER_ID;

public class SetTimerActivity extends AppCompatActivity implements View.OnClickListener, StepActionListener {

    public static final int SETTING_SUCCESS_CODE = 101;

    private SetTimerViewModel mViewModel;

    private SwipeRestrictViewPager vpWizard;

    private TextView tvTimeHours;
    private TextView tvTimeMinutes;
    private TextView tvTimeSeconds;
    private boolean isProgressChanging = false;
    private boolean isSwipeGranted = false;

    public static Intent createIntent(Context context, int timerId) {
        Intent intent = new Intent(context, SetTimerActivity.class);
        intent.putExtra(TimerEntity.TIMER_ID, timerId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_timer);
        mViewModel = ViewModelProviders.of(this, ViewModelFactory.getInstance()).get(SetTimerViewModel.class);

        setupViewModel();
        setupView();
        setupObservers();
    }

    private void setupViewModel() {
        Bundle bundle = getIntent().getExtras();
        int currentId = bundle != null ? bundle.getInt(TIMER_ID) : TimerModel.UNDEFINE_ID;
        mViewModel.setCurrentModelId(currentId);
    }

    private void setupView() {
        vpWizard = findViewById(R.id.vpWizard);
        AppCompatImageView ivNextPage = findViewById(R.id.ivNextPage);

        tvTimeHours = findViewById(R.id.tvTimeHours);
        tvTimeMinutes = findViewById(R.id.tvTimeMinutes);
        tvTimeSeconds = findViewById(R.id.tvTimeSeconds);

        TabLayout tlDots = findViewById(R.id.tlDots);

        vpWizard.setAdapter(new SetTimerPageAdapter(getSupportFragmentManager()));
        vpWizard.setOffscreenPageLimit(2);
        ivNextPage.setOnClickListener(this);

        tlDots.setupWithViewPager(vpWizard, true);
        disableTabs(tlDots);
    }

    private void setupObservers() {
        mViewModel.getSetupData().observe(this, this::updateTime);
        mViewModel.getSwipeState().observe(this, this::updateSwipeStateIfNeeded);
        mViewModel.getTimerData().observe(this, (timerModel -> {
            if (timerModel == null) return;
            updateTimerData(Time.HOURS, timerModel.getHours());
            updateTimerData(Time.MINUTES, timerModel.getMinutes());
            updateTimerData(Time.SECONDS, timerModel.getSeconds());
        }));
    }

    private void updateTime(Time setup) {
        updateTimerData(setup, mViewModel.getCurrentTimeValue());
    }


    private void updateTimerData(Time timerSetup, int newValue) {
        switch (timerSetup) {
            case HOURS:
                tvTimeHours.setText(String.format(Locale.ENGLISH, "%02d", newValue));
                break;
            case MINUTES:
                tvTimeMinutes.setText(String.format(Locale.ENGLISH, "%02d", newValue));
                break;
            case SECONDS:
                tvTimeSeconds.setText(String.format(Locale.ENGLISH, "%02d", newValue));
                break;
        }
    }

    private void updateSwipeStateIfNeeded(Boolean isSwipeGranted) {
        if (isProgressChanging) {
            this.isSwipeGranted = isSwipeGranted;
        } else {
            vpWizard.setIsSwipeAvailable(isSwipeGranted);
        }
    }

    @Override
    public void onClick(View view) {
        if (!isProgressChanging && vpWizard.isSwipeAvailable()) {
            int currentPage = vpWizard.getCurrentItem();
            if (currentPage < 2) {
                vpWizard.setCurrentItem(currentPage + 1);
            } else {
                mViewModel.saveTimer();
                setResult(SETTING_SUCCESS_CODE);
                finish();
            }
        }
    }

    @Override
    public void onActionStart() {
        isProgressChanging = true;
        vpWizard.setIsSwipeAvailable(false);
    }

    @Override
    public void onActionEnd() {
        isProgressChanging = false;
        vpWizard.setIsSwipeAvailable(isSwipeGranted);
    }

    private void disableTabs(TabLayout layout) {
        LinearLayout tabStrip = ((LinearLayout) layout.getChildAt(0));
        for (int i = 0; i < tabStrip.getChildCount(); i++) {
            tabStrip.getChildAt(i).setOnTouchListener((v, event) -> true);
        }
    }
}
