package com.igorganapolsky.vibratingwatchapp.util;

import com.igorganapolsky.vibratingwatchapp.domain.local.entity.TimerEntity;
import com.igorganapolsky.vibratingwatchapp.domain.model.BuzzSetup;
import com.igorganapolsky.vibratingwatchapp.domain.model.TimerModel;

public class Mappers {

    public static TimerModel mapToTimerModel(TimerEntity entity) {
        TimerModel model = new TimerModel();
        model.setId(entity.getId());
        model.setRepeat(entity.getRepeat());
        model.setBuzzCount(entity.getBuzzCount());
        model.setType(BuzzSetup.Type.valueOf(entity.getBuzzType()));
        model.setState(TimerModel.State.valueOf(entity.getState()));
        model.setBuzzTime(entity.getBuzzTime());
        model.setHours(TimerTransform.getHours(entity.getMilliseconds()));
        model.setMinutes(TimerTransform.getMinutes(entity.getMilliseconds()));
        model.setSeconds(TimerTransform.getSeconds(entity.getMilliseconds()));
        return model;
    }

    public static TimerEntity mapToTimerEntity(TimerModel model) {
        TimerEntity entity = new TimerEntity();
        entity.setRepeat(model.getRepeat());
        entity.setState(TimerModel.State.FINISH.name());
        entity.setBuzzCount(model.getBuzzCount());
        entity.setBuzzType(model.getType().name());
        entity.setBuzzTime(model.getBuzzTime());
        entity.setMilliseconds(model.getTimeInMillis());
        return entity;
    }

    public static BuzzSetup mapToBuzzSetup(TimerModel model) {
        return new BuzzSetup(model.getType(), model.getBuzzCount(), model.getBuzzTime());
    }
}
