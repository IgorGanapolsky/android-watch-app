package com.igorganapolsky.vibratingwatchapp.domain.usecase

import com.igorganapolsky.vibratingwatchapp.domain.model.TimerModel
import java.util.*

object TimerTransform {

    fun timeToMillis(hours: Int, minutes: Int, seconds: Int): Long {
        return (seconds * 1000 + minutes * 60000 + hours * 3600000).toLong()
    }

    fun millisToString(milliseconds: Long): String {
        val seconds = (milliseconds / 1000).toInt() % 60
        val minutes = (milliseconds / (1000 * 60)).toInt() % 60
        val hours = (milliseconds / (1000 * 60 * 60)).toInt() % 24
        return String.format(Locale.ENGLISH, "%02d : %02d : %02d", hours, minutes, seconds)
    }

    fun millisToString(value: TimerModel): String {
        return String.format(
            Locale.ENGLISH, "%02d : %02d : %02d",
            value.hours,
            value.minutes,
            value.seconds
        )
    }

    fun secondsToMillis(seconds: Int): Long {
        return (seconds * 1000).toLong()
    }

    fun getHours(milliseconds: Long): Int {
        return (milliseconds / (1000 * 60 * 60)).toInt() % 24
    }

    fun getMinutes(milliseconds: Long): Int {
        return (milliseconds / (1000 * 60)).toInt() % 60
    }

    fun getSeconds(milliseconds: Long): Int {
        return (milliseconds / 1000).toInt() % 60
    }
}
