package com.igorganapolsky.vibratingwatchapp.data

enum class TimeMeasurement private constructor(val shortcut: String, val measure: Double) {
    HOURS("H", 12.0), MINUTES("M", 59.0), SECONDS("S", 59.0)
}
