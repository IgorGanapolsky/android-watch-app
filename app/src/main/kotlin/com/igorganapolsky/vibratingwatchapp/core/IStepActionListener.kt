package com.igorganapolsky.vibratingwatchapp.core

/**
 * Contract for what a timer can do.
 */
interface IStepActionListener {

    fun onActionStart()

    fun onActionEnd()
}
