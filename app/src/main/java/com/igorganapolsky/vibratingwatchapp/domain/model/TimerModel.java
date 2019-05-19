package com.igorganapolsky.vibratingwatchapp.domain.model;

import com.igorganapolsky.vibratingwatchapp.util.TimerTransform;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;
import java.util.Objects;

//TODO (need to be refactored)
public class TimerModel {

    public static final int UNDEFINE_ID = -1;

    public enum State {RUN, PAUSE, BEEPING, FINISH}

    /* Buzz */
    private int buzzCount;
    private int buzzTime;
    private BuzzSetup.Type type;

    /* Repeat */
    private int repeat;

    /* Setup time */
    private int hours;
    private int minutes;
    private int seconds;

    private int id;
    private State state = State.FINISH;

    public static TimerModel createDefault() {
        TimerModel model = new TimerModel();
        model.setType(BuzzSetup.Type.SHORT);
        model.setRepeat(1);
        model.setBuzzCount(1);
        model.setBuzzTime(5);
        return model;
    }

    public TimerModel() {
        this(0, 0, 0);
    }

    private TimerModel(int hours, int minutes, int seconds) {
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
    }

    public long getTimeInMillis() {
        return TimerTransform.timeToMillis(hours, minutes, seconds);
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    public int getBuzzCount() {
        return buzzCount;
    }

    public void setBuzzCount(int buzzCount) {
        this.buzzCount = buzzCount;
    }

    public int getRepeat() {
        return repeat;
    }

    public void setRepeat(int repeat) {
        this.repeat = repeat;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public int getValue(TimerSetup timerSetup) {
        switch (timerSetup) {
            case HOURS:
                return getHours();
            case MINUTES:
                return getMinutes();
            case SECONDS:
                return getSeconds();
        }
        return -1;
    }

    public BuzzSetup.Type getType() {
        return type;
    }

    public void setType(BuzzSetup.Type type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean hasTime() {
        return hours != 0 || minutes != 0 || seconds != 0;
    }

    public int getBuzzTime() {
        return buzzTime;
    }

    public void setBuzzTime(int buzzTime) {
        this.buzzTime = buzzTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimerModel model = (TimerModel) o;
        return id == model.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @NotNull
    @Override
    public String toString() {
        return String.format(Locale.ENGLISH, "%02d : %02d : %02d", hours, minutes, seconds);
    }
}
