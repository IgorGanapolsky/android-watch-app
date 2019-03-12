package com.igorganapolsky.vibratingwatchapp.manager.timer;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.CountDownTimer;
import com.igorganapolsky.vibratingwatchapp.core.util.Mappers;
import com.igorganapolsky.vibratingwatchapp.core.util.TimerTransform;
import com.igorganapolsky.vibratingwatchapp.domain.model.TimerModel;
import com.igorganapolsky.vibratingwatchapp.manager.vibration.BeepManager;

public class WatchCountdownManager implements CountdownManager {

    private MutableLiveData<TimerModel> activeModelData = new MutableLiveData<>();

    private TickListener tickListener;
    private CountDownTimer currentCountdownTimer;
    private BeepManager beepManager;

    private TimerModel activeModel;
    private long totalTime;
    private long lapTime;
    private long timeLeft;

    public WatchCountdownManager(BeepManager beepManager) {
        this.beepManager = beepManager;
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
        return activeModel == null ? -1 : activeModel.getId();
    }

    @Override
    public boolean isActive() {
        return activeModel != null && activeModel.getState() != TimerModel.State.FINISH;
    }

    @Override
    public long getActiveTimeLeft() {
        return activeModel.getState() == TimerModel.State.FINISH ? lapTime : timeLeft % lapTime;
    }

    @Override
    public int getActiveProgress() {
        return activeModel.getState() == TimerModel.State.FINISH ? 100 : calculateProgress();
    }

    @Override
    public TimerModel getActive() {
        return activeModel;
    }

    @Override
    public void setupTimer(TimerModel timerModel) {
        activeModel = timerModel;

        // prepare countdown's data
        long currentTimeTotal = TimerTransform.timeToMillis(timerModel.getHoursTotal(), timerModel.getMinutesTotal(), timerModel.getSecondsTotal());
        long currentTimeLeft = TimerTransform.timeToMillis(timerModel.getHoursLeft(), timerModel.getMinutesLeft(), timerModel.getSecondsLeft());

        lapTime = currentTimeTotal;
        totalTime = currentTimeTotal * activeModel.getRepeat();
        timeLeft = currentTimeLeft * activeModel.getRepeat();

        // fetch beep manager with data
        beepManager.setup(Mappers.mapToBuzzSetup(timerModel));
    }

    @Override
    public void onStart() {
        if (activeModel != null) {
            activeModel.setState(TimerModel.State.RUN);
        }
        activeModelData.postValue(activeModel);
        currentCountdownTimer = prepareCountdownTimer();
        currentCountdownTimer.start();
    }

    @Override
    public void onPause() {
        if (activeModel != null) {
            activeModel.setState(TimerModel.State.PAUSE);
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
            activeModel.setState(TimerModel.State.RUN);
        }
        clearCountDown();
        onStart();
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
                timeLeft = 0;
                beepManager.start();
                notifyOnFinish(false);
            }
        };
    }

    private void notifyOnTick() {
        if (tickListener != null) {
            int progress = calculateProgress();
            String time = TimerTransform.millisToString(timeLeft % lapTime);
            tickListener.onTick(time, progress);
        }
    }

    private void notifyOnFinish(boolean isStop) {
        activeModel.setState(TimerModel.State.FINISH);
        activeModelData.setValue(activeModel);
        if (tickListener != null) {
            String time = TimerTransform.millisToString(lapTime);
            tickListener.onFinish(time, 100, isStop);
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
        return (int) ((float) (timeLeft % lapTime) / (float) (lapTime) * 100.);
    }
}
