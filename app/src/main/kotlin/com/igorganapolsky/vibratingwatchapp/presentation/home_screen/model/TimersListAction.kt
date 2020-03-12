package com.igorganapolsky.vibratingwatchapp.presentation.home_screen.model

/**
 * Value object for specified actions a user can take on the timers list.
 */
sealed class TimersListAction

object HitPlayPause : TimersListAction()
object GoTimerDetails : TimersListAction()
object LongPress : TimersListAction()
object SwipeToDelete : TimersListAction()

