package com.igorganapolsky.vibratingwatchapp.domain

/**
 * Domain representation of timer count-down options.
 */
class CountModel(
    var currentTime: String?,
    var currentProgress: Int,
    var isAnimationNeeded: Boolean
) {
    companion object {
        val default: CountModel
            get() = CountModel(
                "00:00:00",
                0,
                false
            )
    }

}
