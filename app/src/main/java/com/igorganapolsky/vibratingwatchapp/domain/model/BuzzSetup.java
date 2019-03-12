package com.igorganapolsky.vibratingwatchapp.domain.model;

public class BuzzSetup {

   public enum Type {SHORT, LONG}

    private Type buzzType;
    private int buzzCount;
    private int buzzTime;

    public BuzzSetup(Type buzzType, int buzzCount, int buzzTime) {
        this.buzzType = buzzType;
        this.buzzCount = buzzCount;
        this.buzzTime = buzzTime;
    }

    public Type getBuzzType() {
        return buzzType;
    }

    public int getBuzzCount() {
        return buzzCount;
    }

    public int getBuzzTime() {
        return buzzTime;
    }
}
