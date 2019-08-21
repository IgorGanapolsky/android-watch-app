package com.igorganapolsky.vibratingwatchapp.core.vibration;

import com.igorganapolsky.vibratingwatchapp.domain.model.VibrationModel;

public interface IVibrationManager {

    void setup(VibrationModel setup);

    void start();

    void cancel();
}
