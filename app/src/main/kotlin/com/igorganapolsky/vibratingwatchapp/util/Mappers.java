package com.igorganapolsky.vibratingwatchapp.util;

import com.igorganapolsky.vibratingwatchapp.domain.repo.TimerEntity;
import com.igorganapolsky.vibratingwatchapp.domain.model.VibrationModel;
import com.igorganapolsky.vibratingwatchapp.domain.model.TimerModel;

public class Mappers {

    public static TimerModel mapToTimerModel(TimerEntity entity) {
        TimerModel model = new TimerModel();
        model.setId(entity.getId());
        model.setRepeat(entity.getRepeat());
        model.setVibrationCount(entity.getBuzzCount());
        model.setType(VibrationModel.Type.valueOf(entity.getBuzzType()));
        model.setState(TimerModel.State.valueOf(entity.getState()));
        model.setVibrationTimeInSecs(entity.getBuzzTime());
        model.setHours(TimerTransform.getHours(entity.getMilliseconds()));
        model.setMinutes(TimerTransform.getMinutes(entity.getMilliseconds()));
        model.setSeconds(TimerTransform.getSeconds(entity.getMilliseconds()));
        return model;
    }

    public static TimerEntity mapToTimerEntity(TimerModel model) {
        TimerEntity entity = new TimerEntity();
        entity.setRepeat(model.getRepeat());
        entity.setState(TimerModel.State.FINISHED.name());
        entity.setBuzzCount(model.getVibrationCount());
        entity.setBuzzType(model.getType().name());
        entity.setBuzzTime(model.getVibrationTimeInSecs());
        entity.setMilliseconds(model.getTimeInMillis());
        return entity;
    }

    public static VibrationModel mapToBuzzSetup(TimerModel model) {
        return new VibrationModel(model.getType(), model.getVibrationCount(), model.getVibrationTimeInSecs());
    }
}
