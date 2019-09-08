package com.igorganapolsky.vibratingwatchapp.core

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

/**
 * A ViewPager that controls swiping availability on timer creation screens.
 */
class SwipeRestrictViewPager(context: Context, attrs: AttributeSet?) : ViewPager(context, attrs) {
    var isSwipeAvailable = false

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        return isSwipeAvailable && super.onTouchEvent(event)
    }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        return isSwipeAvailable && super.onInterceptTouchEvent(event)
    }

}
