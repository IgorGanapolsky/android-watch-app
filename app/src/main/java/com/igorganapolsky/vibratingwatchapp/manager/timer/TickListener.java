package com.igorganapolsky.vibratingwatchapp.manager.timer;

public interface TickListener {

    void onTick(Long timeLeft, int progress);

    void onLapEnd(Long timeLeft, int progress);

    void onFinish(Long timeLeft, int progress, boolean isStop);
}
