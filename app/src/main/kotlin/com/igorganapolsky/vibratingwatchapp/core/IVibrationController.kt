package com.igorganapolsky.vibratingwatchapp.core

import com.igorganapolsky.vibratingwatchapp.data.VibrationModel

interface IVibrationController {

    fun setup(setup: VibrationModel)

    fun start()

    fun cancel()
}
