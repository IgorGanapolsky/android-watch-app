package com.igorganapolsky.vibratingwatchapp.presentation.details;

import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.igorganapolsky.vibratingwatchapp.R;
import com.igorganapolsky.vibratingwatchapp.core.util.ViewModelFactory;
import com.igorganapolsky.vibratingwatchapp.domain.local.entity.TimerEntity;
import com.igorganapolsky.vibratingwatchapp.domain.model.CountData;
import com.igorganapolsky.vibratingwatchapp.domain.model.TimerModel;
import com.igorganapolsky.vibratingwatchapp.presentation.details.dialog.TimerDeleteDialogFragment;
import com.igorganapolsky.vibratingwatchapp.presentation.settings.SetTimerActivity;

import static com.igorganapolsky.vibratingwatchapp.domain.local.entity.TimerEntity.TIMER_ID;

public class TimerDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private final static int SETTING_REQUEST_CODE = 100;

    private TimerDetailsViewModel mViewModel;

    private ProgressBar pbTime;
    private TextView tvTime;
    private ImageView ivStart;
    private ImageView ivContinueTimer;
    private ImageView ivStop;
    private ImageView ivRestart;

    private ImageView ivTimerSettings;
    private ImageView ivTimerRemove;

    private Animation blinking;

    public static Intent createIntent(Context context, int timerId) {
        Intent intent = new Intent(context, TimerDetailsActivity.class);
        intent.putExtra(TimerEntity.TIMER_ID, timerId);
        return intent;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case SETTING_REQUEST_CODE:
                if (resultCode == SetTimerActivity.SETTING_SUCCESS_CODE) {
                    mViewModel.checkUpdates();
                }
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer_details);
        mViewModel = ViewModelProviders.of(this, ViewModelFactory.getInstance()).get(TimerDetailsViewModel.class);

        setupView();
        setupViewModel();
        setupObservers();
    }

    private void setupViewModel() {
        Bundle bundle = getIntent().getExtras();
        int currentId = bundle != null ? bundle.getInt(TIMER_ID) : 0;
        mViewModel.prepareData(currentId);
    }

    private void setupView() {
        pbTime = findViewById(R.id.pbTime);
        tvTime = findViewById(R.id.tvTime);
        ivTimerSettings = findViewById(R.id.ivTimerSettings);
        ivTimerRemove = findViewById(R.id.ivTimerRemove);
        ivContinueTimer = findViewById(R.id.ivContinueTimer);
        ivContinueTimer.setOnClickListener(this);
        ivTimerRemove.setOnClickListener(this);
        ivTimerSettings.setOnClickListener(this);

        // CONTROLS
        ivStart = findViewById(R.id.ivStart);
        ivStop = findViewById(R.id.ivStop);
        ivRestart = findViewById(R.id.ivRestart);

        ivStop.setOnClickListener(this);
        ivStart.setOnClickListener(this);
        ivRestart.setOnClickListener(this);

        blinking = new AlphaAnimation(1f, .25f);
        blinking.setDuration(500);
        blinking.setRepeatMode(Animation.REVERSE);
        blinking.setRepeatCount(Animation.INFINITE);
    }

    private void setupObservers() {
        mViewModel.getActiveTimerData().observe(this, (this::updateTimerData));
        mViewModel.getViewStateData().observe(this, (this::swapActionMenuState));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivStart:
                view.setSelected(!view.isSelected());
                if (view.isSelected()) {
                    mViewModel.onStart();
                } else {
                    mViewModel.onPause();
                }
                break;
            case R.id.ivStop:
                mViewModel.onStop();
                break;
            case R.id.ivRestart:
                mViewModel.onRestart();
                break;
            case R.id.ivTimerSettings:
                Bundle bundle = getIntent().getExtras();
                int currentId = bundle != null ? bundle.getInt(TIMER_ID) : TimerModel.UNDEFINE_ID;
                startActivityForResult(SetTimerActivity.createIntent(this, currentId), SETTING_REQUEST_CODE);
                break;
            case R.id.ivContinueTimer:
                mViewModel.onNextLap();
                break;
            case R.id.ivTimerRemove:
                getSupportFragmentManager()
                    .beginTransaction()
                    .addToBackStack(null)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .replace(R.id.timer_details_fragment, new TimerDeleteDialogFragment())
                    .commit();
                break;
        }
    }

    private void swapActionMenuState(TimerModel.State state) {
        switch (state) {
            case PAUSE:
                ivStart.setSelected(false);
                ivStart.setEnabled(true);
                ivStop.setEnabled(true);
                ivRestart.setEnabled(true);
                disableAdditionalButtons(true);
                tvTime.startAnimation(blinking);
                ivContinueTimer.setVisibility(View.GONE);
                break;
            case RUN:
                ivStart.setSelected(true);
                disableAdditionalButtons(true);
                ivStart.setEnabled(true);
                ivStop.setEnabled(true);
                ivRestart.setEnabled(true);
                ivContinueTimer.setVisibility(View.GONE);
                blinking.cancel();
                blinking.reset();
                break;
            case FINISH:
                ivStart.setSelected(false);
                disableAdditionalButtons(false);
                ivStart.setEnabled(true);
                ivStop.setEnabled(true);
                ivRestart.setEnabled(true);
                blinking.cancel();
                blinking.reset();
                ivContinueTimer.setVisibility(View.GONE);
                break;
            case BEEPING:
                ivStart.setEnabled(false);
                ivStop.setEnabled(false);
                ivRestart.setEnabled(false);
                tvTime.startAnimation(blinking);
                ivContinueTimer.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void updateTimerData(CountData data) {
        if (data == null) return;
        pbTime.setProgress(100 - data.getCurrentProgress(), data.isAnimationNeeded());
        tvTime.setText(data.getCurrentTime());
    }

    private void disableAdditionalButtons(boolean disable) {
        ivTimerSettings.setClickable(!disable);
        ivTimerRemove.setClickable(!disable);
        ivTimerSettings.setAlpha(disable ? .5f : 1f);
        ivTimerRemove.setAlpha(disable ? .5f : 1f);
    }
}
