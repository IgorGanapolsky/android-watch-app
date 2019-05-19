package com.igorganapolsky.vibratingwatchapp.manager.vibration;

import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import androidx.annotation.RequiresApi;
import com.igorganapolsky.vibratingwatchapp.core.util.TimerTransform;
import com.igorganapolsky.vibratingwatchapp.domain.model.BuzzSetup;

public class WatchBeepManager implements BeepManager {

    private final Vibrator vibrator;
    private BuzzSetup setup;
    private final boolean isVibratorActive;

    private VibrationEffect vibEffect;
    private long[] pattern;

    public WatchBeepManager(Vibrator vibrator) {
        this.vibrator = vibrator;
        isVibratorActive = vibrator != null && vibrator.hasVibrator();
    }

    @Override
    public void setup(BuzzSetup setup) {
        this.setup = setup;
        initVibrationEffect();
    }

    private void initVibrationEffect() {
        if (isVibratorActive) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibEffect = prepareVibrationEffect();
            } else {
                pattern = preparePattern();
            }
        }
    }


    @Override
    public void start() {
        if (isVibratorActive) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(vibEffect);
            } else {
                vibrator.vibrate(pattern, -1);
            }
        }
    }

    @Override
    public void cancel() {
        if (isVibratorActive) {
            vibrator.cancel();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private VibrationEffect prepareVibrationEffect() {
        try {
            long[] pattern = preparePattern();
            return VibrationEffect.createWaveform(pattern, -1);
        } catch (Exception exp) {
            return VibrationEffect.createOneShot(1000, VibrationEffect.DEFAULT_AMPLITUDE);
        }
    }

    private long[] preparePattern() {
        long oneTickTime = TimerTransform.secondsToMillis(setup.getBuzzTime());
        int tickCount = setup.getBuzzCount();

        long[] pattern = new long[(tickCount * 2) + 1];
        pattern[0] = 0;
        for (int i = 1; i < pattern.length; i++) {
            if (i % 2 == 0) {
                pattern[i] = 250;
            } else {
                pattern[i] = oneTickTime;
            }
        }
        return pattern;
    }
}
