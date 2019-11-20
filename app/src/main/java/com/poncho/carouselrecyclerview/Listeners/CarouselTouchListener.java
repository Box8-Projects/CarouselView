package com.poncho.carouselrecyclerview.Listeners;

import android.annotation.SuppressLint;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.poncho.carouselrecyclerview.ZoomLayoutManager;

/**
 * Created by Shivansh ON 2019-08-26.
 */
public class CarouselTouchListener implements View.OnTouchListener {
    private ViewGroup viewGroup;
    private ZoomLayoutManager layoutManager;

    public CarouselTouchListener(ViewGroup viewGroup, ZoomLayoutManager layoutManager) {
        this.viewGroup = viewGroup;
        this.layoutManager = layoutManager;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            layoutManager.stopSliderAutoScroll();
            if (viewGroup != null) {
                viewGroup.setEnabled(false);
            }
        }

        if (motionEvent.getAction() == MotionEvent.ACTION_UP || motionEvent.getAction() == MotionEvent.ACTION_CANCEL) {
            layoutManager.startSliderAutoScroll();
            if (viewGroup != null) {
                viewGroup.setEnabled(true);
            }
        }

        return false;
    }
}
