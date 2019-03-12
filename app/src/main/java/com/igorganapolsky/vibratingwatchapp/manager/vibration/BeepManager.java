package com.igorganapolsky.vibratingwatchapp.manager.vibration;

import com.igorganapolsky.vibratingwatchapp.domain.model.BuzzSetup;

public interface BeepManager {

    void setup(BuzzSetup setup);

    void start();

    void cancel();
}
