package com.poncho.carouselrecyclerview;

import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Shivansh ON 2019-08-26.
 */
class CarouselSnapHelper extends LinearSnapHelper {
    @Override
    public int findTargetSnapPosition(RecyclerView.LayoutManager layoutManager, int velocityX, int velocityY) {
        View centerView = findSnapView(layoutManager);
        if (centerView == null)
            return RecyclerView.NO_POSITION;

        int position = layoutManager.getPosition(centerView);
        int targetPosition = -1;
        if (layoutManager.canScrollHorizontally()) {
            if (velocityX < 0) {
                targetPosition = position - 1;
            } else {
                targetPosition = position + 1;
            }
        }

        if (layoutManager.canScrollVertically()) {
            if (velocityY < 0) {
                targetPosition = position - 1;
            } else {
                targetPosition = position + 1;
            }
        }

        final int firstItem = 0;
        final int lastItem = layoutManager.getItemCount() - 1;
        targetPosition = Math.min(lastItem, Math.max(targetPosition, firstItem));

        return targetPosition;
    }
}
