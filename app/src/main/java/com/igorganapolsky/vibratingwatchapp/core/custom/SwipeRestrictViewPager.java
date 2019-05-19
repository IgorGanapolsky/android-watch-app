package com.igorganapolsky.vibratingwatchapp.core.custom;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class SwipeRestrictViewPager extends ViewPager {

    private boolean isSwipeAvailable = false;

    public SwipeRestrictViewPager(@NonNull Context context) {
        super(context);
    }

    public SwipeRestrictViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return isSwipeAvailable && super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return isSwipeAvailable && super.onInterceptTouchEvent(event);
    }

    public void setIsSwipeAvailable(boolean isSwipeAvailable) {
        this.isSwipeAvailable = isSwipeAvailable;
    }

    public boolean isSwipeAvailable() {
        return isSwipeAvailable;
    }

}
