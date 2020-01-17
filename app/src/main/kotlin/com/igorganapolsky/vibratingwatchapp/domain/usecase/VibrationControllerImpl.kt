package com.igorganapolsky.vibratingwatchapp.domain.usecase

import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator

import androidx.annotation.RequiresApi
import com.igorganapolsky.vibratingwatchapp.domain.model.VibrationModel

class VibrationControllerImpl(private val vibrator: Vibrator?) :
    IVibrationController {
    private var setup: VibrationModel? = null
    private val isVibratorActive: Boolean = vibrator != null && vibrator.hasVibrator()

    private var vibEffect: VibrationEffect? = null
    private var pattern: LongArray? = null

    override fun setup(setup: VibrationModel) {
        this.setup = setup
        initVibrationEffect()
    }

    private fun initVibrationEffect() {
        if (isVibratorActive) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibEffect = prepareVibrationEffect()
            } else {
                pattern = preparePattern()
            }
        }
    }

    override fun start() {
        if (isVibratorActive) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator!!.vibrate(vibEffect)
            } else {
                vibrator!!.vibrate(pattern, -1)
            }
        }
    }

    override fun cancel() {
        if (isVibratorActive) {
            vibrator!!.cancel()
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private fun prepareVibrationEffect(): VibrationEffect {
        return try {
            val pattern = preparePattern()
            VibrationEffect.createWaveform(pattern, -1)
        } catch (exp: Exception) {
            VibrationEffect.createOneShot(1000, VibrationEffect.DEFAULT_AMPLITUDE)
        }

    }

    private fun preparePattern(): LongArray {
        val oneTickTime =
            TimerTransform.secondsToMillis(setup!!.buzzTime)
        val tickCount = setup!!.buzzCount

        val pattern = LongArray(tickCount * 2 + 1)
        pattern[0] = 0
        for (i in 1 until pattern.size) {
            if (i % 2 == 0) {
                pattern[i] = 250
            } else {
                pattern[i] = oneTickTime
            }
        }
        return pattern
    }
}
