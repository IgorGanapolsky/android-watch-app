package com.igorganapolsky.vibratingwatchapp.domain.usecase

import com.igorganapolsky.vibratingwatchapp.data.TimerEntity
import com.igorganapolsky.vibratingwatchapp.domain.model.TimerModel
import com.igorganapolsky.vibratingwatchapp.domain.model.VibrationModel

object Mappers {

    fun mapToTimerModel(entity: TimerEntity): TimerModel {
        val model = TimerModel()
        model.id = entity.id
        model.repeat = entity.repeat
        model.vibrationCount = entity.buzzCount
        model.type = VibrationModel.Type.valueOf(entity.buzzType.toString())
        model.state = TimerModel.State.valueOf(entity.state.toString())
        model.vibrationTimeInSecs = entity.buzzTime
        model.hours =
            TimerTransform.getHours(entity.milliseconds)
        model.minutes =
            TimerTransform.getMinutes(entity.milliseconds)
        model.seconds =
            TimerTransform.getSeconds(entity.milliseconds)
        return model
    }

    fun mapToTimerEntity(model: TimerModel): TimerEntity {
        val entity = TimerEntity()
        entity.repeat = model.repeat
        entity.state = TimerModel.State.FINISHED.name
        entity.buzzCount = model.vibrationCount
        entity.buzzType = model.type!!.name
        entity.buzzTime = model.vibrationTimeInSecs
        entity.milliseconds = model.timeInMillis
        return entity
    }

    fun mapToBuzzSetup(model: TimerModel): VibrationModel {
        return VibrationModel(
            model.type!!,
            model.vibrationCount,
            model.vibrationTimeInSecs
        )
    }
}
