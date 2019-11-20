package com.poncho.carouselrecyclerview;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Shivansh ON 2019-08-21.
 */
public class ZoomLayoutManager extends LinearLayoutManager {
    private long scrollInterval;
    private Handler autoScrollHandler = new Handler();
    private Context context;
    private int length;
    private boolean isInfinite;

    ZoomLayoutManager(Context context, int orientation,
                      boolean reverseLayout, long scrollInterval, int length, boolean isInfinite) {
        super(context, orientation, reverseLayout);
        this.scrollInterval = scrollInterval;
        this.context = context;
        this.length = length;
        this.isInfinite = isInfinite;
    }

    private Runnable autoInfiniteScrollRunnable = new Runnable() {
        @Override
        public void run() {
            int position = findLastVisibleItemPosition();
            if (position >= 0) {
                if (position == length - 1 && isInfinite) {
                    scrollToPosition(length / 2);
                    smoothScrollToPosition(length / 2);
                } else if (!isInfinite && findLastCompletelyVisibleItemPosition() == length - 1) {
                    smoothScrollToPosition(0);
                } else {
                    smoothScrollToPosition(position);
                }
                autoScrollHandler.postDelayed(this, scrollInterval);
            }
        }
    };

    @Override
    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        int scrolled = super.scrollHorizontallyBy(dx, recycler, state);
        float mShrinkDistance = 1f;

        float midpoint = getWidth() / 2.f;
        float d0 = 0.f;
        float d1 = mShrinkDistance * midpoint;
        float s0 = 1.f;
        float s1X = 1.f - 0.035f;
        float s1Y = 1.f - 0.15f;

        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (child != null) {
                float childMidpoint =
                        (getDecoratedRight(child) + getDecoratedLeft(child)) / 2.f;
                float d = Math.min(d1, Math.abs(midpoint - childMidpoint));
                float scaleY = s0 + (s1Y - s0) * (d - d0) / (d1 - d0);
                child.setScaleY(scaleY);

                float scaleX = s0 + (s1X - s0) * (d - d0) / (d1 - d0);
                child.setScaleX(scaleX);

                child.setTranslationX(scaleX);
            }
        }
        return scrolled;
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        super.onLayoutChildren(recycler, state);
        scrollHorizontallyBy(1, recycler, state);
    }

    public void smoothScrollToPosition(int position) {
        LinearSmoothScroller smoothScroller = new LinearSmoothScroller(context) {
            @Override
            public int calculateDtToFit(int viewStart, int viewEnd, int boxStart, int boxEnd, int snapPreference) {
                return (boxStart + (boxEnd - boxStart) / 2) - (viewStart + (viewEnd - viewStart) / 2);
            }
        };
        smoothScroller.setTargetPosition(position);
        startSmoothScroll(smoothScroller);
    }

    public void stopSliderAutoScroll() {
        autoScrollHandler.removeCallbacks(autoInfiniteScrollRunnable);
    }

    public void startSliderAutoScroll() {
        autoScrollHandler.removeCallbacks(autoInfiniteScrollRunnable);
        autoScrollHandler.postDelayed(autoInfiniteScrollRunnable, scrollInterval);
    }
}