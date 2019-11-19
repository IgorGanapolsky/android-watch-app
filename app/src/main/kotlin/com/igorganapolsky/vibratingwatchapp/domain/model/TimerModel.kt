package com.igorganapolsky.vibratingwatchapp.domain.model.model

import com.igorganapolsky.vibratingwatchapp.data.TimeMeasurement
import com.igorganapolsky.vibratingwatchapp.domain.model.TimerTransform
import java.util.*

/**
 * Setup values for a timer.
 */
class TimerModel private constructor(var hours: Int, var minutes: Int, var seconds: Int) {
    /* Vibration */
    var vibrationCount = 1
    var vibrationTimeInSecs = 5
    var type: VibrationModel.Type? = null

    /* Repeat */
    var repeat: Int = 0

    var id: Int = 0
    var state = State.FINISHED

    val timeInMillis: Long
        get() = TimerTransform.timeToMillis(
            hours,
            minutes,
            seconds
        )

    enum class State {
        RUNNING, PAUSED, BEEPING, FINISHED
    }

    constructor() : this(0, 0, 0)

    fun getValue(timeMeasurement: TimeMeasurement): Int {
        return when (timeMeasurement) {
            TimeMeasurement.HOURS -> hours
            TimeMeasurement.MINUTES -> minutes
            TimeMeasurement.SECONDS -> seconds
        }
    }

    fun hasTime(): Boolean {
        return hours != 0 || minutes != 0 || seconds != 0
    }

//    override fun equals(o: Any?): Boolean {
//        if (this === o) return true
//        if (o == null || javaClass != o.javaClass) return false
//        val model = o as TimerModel?
//        return id == model!!.id
//    }
//
//    override fun hashCode(): Int {
//        return Objects.hash(id)
//    }

    override fun toString(): String =
        String.format(Locale.ENGLISH, "%02d : %02d : %02d", hours, minutes, seconds)

    companion object {
        const val UNDEFINE_ID = -1

        fun createDefault(): TimerModel {
            val defaultBuzzCount = 1
            val defaultBuzzSecs = 5
            val defaultTimerRepeat = 1

            val model = TimerModel()
            model.type =
                VibrationModel.Type.SHORT_BUZZ
            model.vibrationCount = defaultBuzzCount
            model.vibrationTimeInSecs = defaultBuzzSecs
            model.repeat = defaultTimerRepeat
            return model
        }
    }
}
