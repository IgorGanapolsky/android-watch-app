package com.igorganapolsky.vibratingwatchapp.manager.vibration;

import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.annotation.RequiresApi;
import com.igorganapolsky.vibratingwatchapp.core.util.TimerTransform;
import com.igorganapolsky.vibratingwatchapp.domain.model.BuzzSetup;

public class WatchBeepManager implements BeepManager {

    private Vibrator vibrator;
    private BuzzSetup setup;
    private boolean isVibratorActive;

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
        long oneTickTime = TimerTransform.secondsToMillis(setup.getBuzzTime());
        int tickCount = setup.getBuzzCount();

        long[] timings = new long[tickCount];
        int[] amplitude = new int[tickCount];

        for (int i = 0; i < timings.length; i++) {
            timings[i] = oneTickTime;
            amplitude[i] = 255 / 2;
        }
        return VibrationEffect.createWaveform(timings, amplitude, -1);
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
