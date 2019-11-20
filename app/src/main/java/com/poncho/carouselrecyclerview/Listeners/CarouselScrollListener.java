package com.poncho.carouselrecyclerview.Listeners;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import com.poncho.carouselrecyclerview.ZoomLayoutManager;

/**
 * Created by Shivansh ON 2019-08-26.
 */
public class CarouselScrollListener extends RecyclerView.OnScrollListener {
    private int size;
    private boolean isInfinite;

    public CarouselScrollListener(int size, boolean isInfinite) {
        this.size = size;
        this.isInfinite = isInfinite;
    }

    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        if (recyclerView.getLayoutManager() != null) {
            int firstItemVisible = ((ZoomLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
            if ((firstItemVisible == 0 || firstItemVisible % (size - 1) == 0) && isInfinite) {
                recyclerView.scrollToPosition(size / 2);
                ((ZoomLayoutManager) recyclerView.getLayoutManager()).smoothScrollToPosition(size / 2);
            }
        }
    }
}
