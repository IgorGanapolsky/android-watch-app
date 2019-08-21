package com.igorganapolsky.vibratingwatchapp.core.timer;

import android.os.CountDownTimer;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.igorganapolsky.vibratingwatchapp.core.vibration.IVibrationManager;
import com.igorganapolsky.vibratingwatchapp.domain.model.TimerModel;
import com.igorganapolsky.vibratingwatchapp.util.Mappers;

public class CountdownManagerImpl implements ICountdownManager {

    private final MutableLiveData<TimerModel> activeModelData = new MutableLiveData<>();
    private final IVibrationManager beepManager;

    private TickListener tickListener;
    private CountDownTimer currentCountdownTimer;

    private TimerModel activeModel;
    private long totalTime;
    private int repeatCount;
    private long timeLeft;

    public CountdownManagerImpl(IVibrationManager beepManager) {
        this.beepManager = beepManager;
    }

    @Override
    public void setupTimer(TimerModel timerModel) {
        activeModel = timerModel;

        // prepare countdown's data
        long timerTimeInMillis = activeModel.getTimeInMillis();
        totalTime = timerTimeInMillis;
        timeLeft = timerTimeInMillis;
        repeatCount = timerModel.getRepeat();

        // fetch beep manager with data
        beepManager.setup(Mappers.mapToBuzzSetup(timerModel));
    }

    @Override
    public void onStart() {
        if (activeModel != null) {
            activeModel.setState(TimerModel.State.RUNNING);
        }
        activeModelData.postValue(activeModel);
        currentCountdownTimer = prepareCountdownTimer();
        currentCountdownTimer.start();
    }

    @Override
    public void onPause() {
        if (activeModel != null) {
            activeModel.setState(TimerModel.State.PAUSED);
        }
        if (currentCountdownTimer != null) {
            currentCountdownTimer.cancel();
        }
    }

    @Override
    public void onStop() {
        clearCountDown();
        notifyOnFinish(true);
    }

    @Override
    public void onRestart() {
        if (activeModel != null) {
            activeModel.setState(TimerModel.State.RUNNING);
        }
        clearCountDown();
        onStart();
    }

    @Override
    public boolean onNextLap() {
        if (repeatCount <= 0) return false;
        beepManager.cancel();
        clearCountDown();
        onStart();
        return true;
    }

    private CountDownTimer prepareCountdownTimer() {
        long millisecondsLeft = timeLeft > 0 ? timeLeft : totalTime;

        return new CountDownTimer(millisecondsLeft, 100) {
            @Override
            public void onTick(long millis) {
                timeLeft = millis;
                notifyOnTick();
            }

            @Override
            public void onFinish() {
                beepManager.start();
                timeLeft = 0L;
                repeatCount--;
                if (repeatCount <= 0) {
                    notifyOnFinish(false);
                } else {
                    notifyOnLapEnd();
                }
            }
        };
    }

    private void notifyOnTick() {
        if (tickListener != null) {
            int progress = calculateProgress();
            tickListener.onTick(timeLeft, progress);
        }
    }

    private void notifyOnFinish(boolean isStop) {
        activeModel.setState(TimerModel.State.FINISHED);
        activeModelData.setValue(activeModel);
        if (tickListener != null) {
            tickListener.onFinish(timeLeft, 100, isStop);
        }
    }

    private void notifyOnLapEnd() {
        activeModel.setState(TimerModel.State.BEEPING);
        activeModelData.setValue(activeModel);
        if (tickListener != null) {
            tickListener.onLapEnd(timeLeft, 100);
        }
    }

    private void clearCountDown() {
        timeLeft = 0L;
        if (currentCountdownTimer != null) {
            currentCountdownTimer.cancel();
            currentCountdownTimer = null;
        }
    }

    private int calculateProgress() {
        return (int) ((float) timeLeft / (float) (totalTime) * 100.);
    }

    @Override
    public LiveData<TimerModel> observeActiveModel() {
        return activeModelData;
    }

    @Override
    public void setTickListener(TickListener listener) {
        this.tickListener = listener;
    }

    @Override
    public int getActiveId() {
        return activeModel == null ? TimerModel.UNDEFINE_ID : activeModel.getId();
    }

    @Override
    public boolean isHasMoreRepeats() {
        return repeatCount > 0;
    }

    @Override
    public boolean isActive() {
        return activeModel != null && activeModel.getState() != TimerModel.State.FINISHED;
    }

    @Override
    public long getActiveTimeLeft() {
        return timeLeft;
    }

    @Override
    public int getActiveProgress() {
        return activeModel.getState() == TimerModel.State.FINISHED ? 100 : calculateProgress();
    }

    @Override
    public TimerModel getActive() {
        return activeModel;
    }
}
