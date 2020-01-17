package com.igorganapolsky.vibratingwatchapp.domain.model

/**
 * Represents timer vibration (buzz) info.
 */
class VibrationModel(val buzzType: Type, val buzzCount: Int, val buzzTime: Int) {

    enum class Type {
        SHORT_BUZZ, LONG_BUZZ
    }

}
