package com.igorganapolsky.vibratingwatchapp.common

/**
 * Contract for what a step touch in timer setup can do.
 */
interface IStepTouchActionListener {

    fun onActionStart()

    fun onActionEnd()
}
