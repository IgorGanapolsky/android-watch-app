package com.igorganapolsky.vibratingwatchapp.domain.model

import com.igorganapolsky.vibratingwatchapp.domain.model.model.VibrationModel

interface IVibrationController {

    fun setup(setup: VibrationModel)

    fun start()

    fun cancel()
}
