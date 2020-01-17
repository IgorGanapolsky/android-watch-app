package com.igorganapolsky.vibratingwatchapp.domain.model

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
