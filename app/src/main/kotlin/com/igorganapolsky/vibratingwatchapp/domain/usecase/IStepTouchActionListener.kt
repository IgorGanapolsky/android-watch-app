package com.igorganapolsky.vibratingwatchapp.domain.model

/**
 * Contract for what a step touch in timer setup can do.
 */
interface IStepTouchActionListener {

    fun onActionStart()

    fun onActionEnd()
}
