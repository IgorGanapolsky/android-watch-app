package com.igorganapolsky.vibratingwatchapp.common

import com.igorganapolsky.vibratingwatchapp.domain.VibrationModel

interface IVibrationController {

    fun setup(setup: VibrationModel)

    fun start()

    fun cancel()
}
