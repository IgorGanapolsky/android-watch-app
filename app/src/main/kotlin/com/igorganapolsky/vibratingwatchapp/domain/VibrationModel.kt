package com.igorganapolsky.vibratingwatchapp.domain

/**
 * Domain representation of timer vibration options.
 */
class VibrationModel(val buzzType: Type, val buzzCount: Int, val buzzTime: Int) {

    enum class Type {
        SHORT_BUZZ, LONG_BUZZ
    }

}
