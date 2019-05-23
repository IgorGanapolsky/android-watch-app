package com.igorganapolsky.vibratingwatchapp.domain.model;

/**
 * Represents timer info.
 */
public class BuzzSetup {

    public enum Type {SHORT_BUZZ, LONG_BUZZ}

    private final Type buzzType;
    private final int buzzCount;
    private final int buzzTime;

    public BuzzSetup(Type buzzType, int buzzCount, int buzzTime) {
        this.buzzType = buzzType;
        this.buzzCount = buzzCount;
        this.buzzTime = buzzTime;
    }

    public final Type getBuzzType() {
        return buzzType;
    }

    public final int getBuzzCount() {
        return buzzCount;
    }

    public final int getBuzzTime() {
        return buzzTime;
    }
}
