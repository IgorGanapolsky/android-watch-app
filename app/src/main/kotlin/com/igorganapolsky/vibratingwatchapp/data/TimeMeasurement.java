package com.igorganapolsky.vibratingwatchapp.data;

public enum TimeMeasurement {
    HOURS("H", 12.), MINUTES("M", 59.), SECONDS("S", 59.);

    private final String shortcut;
    private final double measure;

    TimeMeasurement(String shortcut, double measure) {
        this.shortcut = shortcut;
        this.measure = measure;
    }

    public String getShortcut() {
        return shortcut;
    }

    public double getMeasure() {
        return measure;
    }
}
