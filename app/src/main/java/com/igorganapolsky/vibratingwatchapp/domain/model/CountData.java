package com.igorganapolsky.vibratingwatchapp.domain.model;

public class CountData {

    private int currentProgress;
    private String currentTime;
    private boolean isAnimationNeeded;

    public CountData(String currentTime, int currentProgress, boolean isAnimationNeeded) {
        this.currentTime = currentTime;
        this.currentProgress = currentProgress;
        this.isAnimationNeeded = isAnimationNeeded;
    }

    public int getCurrentProgress() {
        return currentProgress;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public boolean isAnimationNeeded() {
        return isAnimationNeeded;
    }
}
