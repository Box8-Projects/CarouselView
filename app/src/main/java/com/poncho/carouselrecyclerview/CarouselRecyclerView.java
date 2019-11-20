package com.poncho.carouselrecyclerview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.poncho.carouselrecyclerview.Adapters.BannerRecyclerAdapter;
import com.poncho.carouselrecyclerview.Listeners.CarouselScrollListener;
import com.poncho.carouselrecyclerview.Listeners.CarouselTouchListener;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * Created by Shivansh ON 2019-08-26.
 */
public class CarouselRecyclerView extends RelativeLayout {
    private WeakReference<Context> mContext;
    private RecyclerView recyclerView;

    public CarouselRecyclerView(Context context) {
        super(context);
        mContext = new WeakReference<>(context);
        init();
    }

    public CarouselRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = new WeakReference<>(context);
        init();
    }

    public CarouselRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = new WeakReference<>(context);
        init();
    }


    /**
     * Inflate the XML and initialize view objects
     */
    private void init() {
        inflate(mContext.get(), R.layout.banner_layout, this);
        initializeViews();
    }

    private void initializeViews() {
        recyclerView = findViewById(R.id.recycler_view);
    }


    /**
     * Configuration method to populate the values of CarouselViewPager with the builder
     *
     * @param builder Builder object
     */
    @SuppressLint("ClickableViewAccessibility")
    public void config(Builder builder) {
        int lengthRecyclerView = 500;
        if (!builder.isInfinite)
            lengthRecyclerView = builder.urls.size();

        ZoomLayoutManager layoutManager = new ZoomLayoutManager(mContext.get(),
                LinearLayoutManager.HORIZONTAL,
                false,
                builder.changeInterval,
                lengthRecyclerView,
                builder.isInfinite);
        BannerRecyclerAdapter adapter = new BannerRecyclerAdapter(builder.urls, builder.width, lengthRecyclerView);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

        if (builder.isInfinite) {
            recyclerView.scrollToPosition(lengthRecyclerView / 2);
        }

        recyclerView.scrollBy(builder.adjustX, 0);
        recyclerView.setOnFlingListener(builder.flingListener);

        builder.snapHelper.attachToRecyclerView(recyclerView);

        recyclerView.setOnTouchListener(new CarouselTouchListener(builder.viewGroup, layoutManager));
        recyclerView.addOnScrollListener(new CarouselScrollListener(lengthRecyclerView, builder.isInfinite));

        if (builder.isAutoScroll)
            layoutManager.startSliderAutoScroll();
    }


    /**
     * Configuration builder class
     */
    public static class Builder {
        private ArrayList<String> urls;
        private long changeInterval = 4000;
        private boolean isAutoScroll = true;
        private boolean isInfinite = true;
        private int width = 350;
        private int adjustX = 32;
        private RecyclerView.OnFlingListener flingListener = null;
        private SnapHelper snapHelper = new CarouselSnapHelper();
        private ViewGroup viewGroup = null;

        public Builder(ArrayList<String> urls) {
            this.urls = urls;
        }

        /**
         * Set whether infinite scrolling is enabled.
         *
         * @param isInfinite :: true for infinite scrolling
         * @return builder object for method chaining
         */
        public Builder setIsInfinite(boolean isInfinite) {
            this.isInfinite = isInfinite;
            return this;
        }

        /**
         * Set the time changeInterval after which the slide automatically switches to the next slide.
         *
         * @param milliSeconds :: time in milliseconds
         * @return builder object for method chaining
         */
        public Builder setChangeInterval(long milliSeconds) {
            changeInterval = milliSeconds;
            return this;
        }

        /**
         * Set if the automatic isAutoScroll should be enabled or not
         *
         * @param scroll :: boolean value, true for isAutoScroll; false for no isAutoScroll
         * @return builder object for method chaining
         */
        public Builder setAutoScroll(boolean scroll) {
            isAutoScroll = scroll;
            return this;
        }

        /**
         * Set the width of a single banner
         *
         * @param width ::
         * @return builder object for method chaining
         */
        public Builder setWidth(int width) {
            this.width = width;
            return this;
        }

        /**
         * Set the amount by which recyclerView should be scrolled to keep the banner in center.
         *
         * @param adjustX :: pixels by which recyclerView is scrolled
         * @return builder object for method chaining
         */
        public Builder setAdjustX(int adjustX) {
            this.adjustX = adjustX;
            return this;
        }

        /**
         * Set the fling listener for recycler view.
         *
         * @param flingListener :: Fling Listener
         * @return builder object for method chaining
         */
        public Builder setFlingListener(RecyclerView.OnFlingListener flingListener) {
            this.flingListener = flingListener;
            return this;
        }

        /**
         * Set the snap helper for recycler view.
         *
         * @param snapHelper :: Fling Listener
         * @return builder object for method chaining
         */
        public Builder setSnapHelper(SnapHelper snapHelper) {
            this.snapHelper = snapHelper;
            return this;
        }

        /**
         * Set View Group (eg. Swipe Refresh Layout) to co-ordinate nested scrolling
         *
         * @param viewGroup :: ViewGroup
         * @return builder object for method chaining
         */
        public Builder setViewGroup(ViewGroup viewGroup) {
            this.viewGroup = viewGroup;
            return this;
        }
    }

}
