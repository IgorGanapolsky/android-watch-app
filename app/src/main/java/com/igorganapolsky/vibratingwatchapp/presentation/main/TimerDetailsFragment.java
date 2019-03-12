package com.igorganapolsky.vibratingwatchapp.presentation.main;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.igorganapolsky.vibratingwatchapp.R;
import com.igorganapolsky.vibratingwatchapp.domain.local.entity.TimerEntity;
import com.igorganapolsky.vibratingwatchapp.presentation.details.dialog.TimerDeleteDialogFragment;
import com.igorganapolsky.vibratingwatchapp.presentation.settings.SetTimerActivity;
import com.igorganapolsky.vibratingwatchapp.core.util.TimerTransform;
import com.igorganapolsky.vibratingwatchapp.core.util.ViewModelFactory;

import java.util.Objects;

@Deprecated
public class TimerDetailsFragment extends Fragment implements View.OnClickListener {

    public static final String TAG = "timer_details_tag";

    private final int SETTING_REQUEST_CODE = 100;
    private final int SETTING_SUCCESS_CODE = 101;

    private TimerListViewModel mViewModel;

    private long timeLeft;
    private TimerEntity model;
    private CountDownTimer currentTimer;

    private ProgressBar pbTime;
    private TextView tvTime;
    private ImageView ivStart;
    private ImageView ivStop;
    private ImageView ivRestart;

    private ImageView ivTimerSettings;
    private ImageView ivTimerRemove;

    private Animation blinking;

    public static Fragment createInstance(int id) {
        TimerDetailsFragment newFragment = new TimerDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(TimerEntity.TIMER_ID, id);
        newFragment.setArguments(bundle);
        return newFragment;
    }


    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_timer_details, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        blinking = new AlphaAnimation(1f, .25f);
        blinking.setDuration(500);
        blinking.setRepeatMode(Animation.REVERSE);
        blinking.setRepeatCount(Animation.INFINITE);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity()), ViewModelFactory.getInstance()).get(TimerListViewModel.class);

        setupView(view);
    }

    private void setupView(View view) {
        pbTime = view.findViewById(R.id.pbTime);
        tvTime = view.findViewById(R.id.tvTime);

        ivTimerSettings = view.findViewById(R.id.ivTimerSettings);
        ivTimerRemove = view.findViewById(R.id.ivTimerRemove);
        ivStop = view.findViewById(R.id.ivStop);
        ivStart = view.findViewById(R.id.ivStart);
        ivRestart = view.findViewById(R.id.ivRestart);

        ivTimerSettings.setOnClickListener(this);
        ivTimerRemove.setOnClickListener(this);
        ivStart.setOnClickListener(this);
        ivStop.setOnClickListener(this);
        ivRestart.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivStart:
                view.setSelected(!view.isSelected());
                if (view.isSelected()) {
                    currentTimer = prepareCountDown();
                    showPlayOrPause(true);
                } else {
                    currentTimer.cancel();
                    showPlayOrPause(false);
                }
                break;

            case R.id.ivStop:
                clearCountDown();
                showPlayOrPause(false);
                renderTime(model.getMillisecondsTotal(), true);
                getActivity().onBackPressed();
                break;

            case R.id.ivRestart:
                clearCountDown();
                currentTimer = prepareCountDown();
                showPlayOrPause(true);
                break;

            case R.id.ivTimerSettings:
                Intent settingIntent = new Intent(getContext(), SetTimerActivity.class);
                settingIntent.putExtra("TIMER_MOD,EL", model);
                settingIntent.putExtra("TIMER_ID", model.getId());
                startActivityForResult(settingIntent, SETTING_REQUEST_CODE);
                break;

            case R.id.ivTimerRemove:
                Bundle deleteBundle = new Bundle();
                deleteBundle.putSerializable("TIMER_MODEL", model);

                Fragment delete_timer = new TimerDeleteDialogFragment();
                delete_timer.setArguments(deleteBundle);

                getChildFragmentManager()
                    .beginTransaction()
                    .addToBackStack(null)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .replace(R.id.timer_details_fragment, delete_timer)
                    .commit();

                break;
        }
    }

    private CountDownTimer prepareCountDown() {
        long millisecondsLeft = timeLeft > 0 ? timeLeft : model.getMillisecondsTotal();
        renderTime(millisecondsLeft, false);

        return new CountDownTimer(millisecondsLeft, 50) {
            @Override
            public void onTick(long millis) {
                timeLeft = millis;
                renderTime(millis, true);
            }

            @Override
            public void onFinish() {
                clearCountDown();
                showPlayOrPause(false);
                renderTime(0, true);
                blinking.cancel();
            }
        }.start();
    }

    private void renderTime(long timeLeft, boolean animateProgress) {
        int progress = (int) ((double) timeLeft / (double) model.getMillisecondsTotal() * 100.);
        pbTime.setProgress(100 - progress, animateProgress);
        tvTime.setText(TimerTransform.millisToString(timeLeft));
    }

    private void showPlayOrPause(boolean isPause) {
        if (!isPause) {
            ivStart.setSelected(false);
            disableAdditionalButtons(false);
            tvTime.startAnimation(blinking);
        } else {
            ivStart.setSelected(true);
            disableAdditionalButtons(true);
            blinking.cancel();
        }
    }

    private void disableAdditionalButtons(boolean disable) {
        ivTimerSettings.setClickable(!disable);
        ivTimerRemove.setClickable(!disable);
        ivTimerSettings.setAlpha(disable ? .5f : 1f);
        ivTimerRemove.setAlpha(disable ? .5f : 1f);
    }

    private void clearCountDown() {
        timeLeft = 0;
        if (currentTimer != null) {
            currentTimer.cancel();
            currentTimer = null;
        }
    }
}
