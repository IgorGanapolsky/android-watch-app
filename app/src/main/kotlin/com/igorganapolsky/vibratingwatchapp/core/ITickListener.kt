package com.igorganapolsky.vibratingwatchapp.core

interface ITickListener {

    fun onTick(timeLeft: Long?, progress: Int)

    fun onLapEnd(timeLeft: Long?, progress: Int)

    fun onFinish(timeLeft: Long?, progress: Int, isStop: Boolean)
}
