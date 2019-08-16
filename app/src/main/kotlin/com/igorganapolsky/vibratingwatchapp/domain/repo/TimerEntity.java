package com.igorganapolsky.vibratingwatchapp.domain.repo;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "timers")
public class TimerEntity {

    public static final String TIMER_ID = "timer_id";

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "time")
    private long milliseconds;

    @ColumnInfo(name = "buzz_type")
    private String buzzType;

    @ColumnInfo(name = "buzz_count")
    private int buzzCount;

    @ColumnInfo(name = "buzz_time")
    private int buzzTime;

    @ColumnInfo(name = "repeat")
    private int repeat;

    @ColumnInfo(name = "timestamp")
    private long timestamp;

    @ColumnInfo(name = "state")
    private String state;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getMilliseconds() {
        return milliseconds;
    }

    public void setMilliseconds(long milliseconds) {
        this.milliseconds = milliseconds;
    }

    public String getBuzzType() {
        return buzzType;
    }

    public void setBuzzType(String buzzType) {
        this.buzzType = buzzType;
    }

    public int getRepeat() {
        return repeat;
    }

    public void setRepeat(int repeat) {
        this.repeat = repeat;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getBuzzCount() {
        return buzzCount;
    }

    public void setBuzzCount(int buzzCount) {
        this.buzzCount = buzzCount;
    }

    public int getBuzzTime() {
        return buzzTime;
    }

    public void setBuzzTime(int buzzTime) {
        this.buzzTime = buzzTime;
    }
}
