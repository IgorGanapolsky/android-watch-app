package com.igorganapolsky.vibratingwatchapp.domain.usecase

import com.igorganapolsky.vibratingwatchapp.domain.model.VibrationModel

interface IVibrationController {

    fun setup(setup: VibrationModel)

    fun start()

    fun cancel()
}
