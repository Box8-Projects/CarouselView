package com.poncho.carouselrecyclerview.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.poncho.carouselrecyclerview.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static java.lang.Math.max;

/**
 * Created by Shivansh ON 2019-08-21.
 */
public class BannerRecyclerAdapter extends RecyclerView.Adapter<BannerRecyclerAdapter.BannerViewHolder> {
    private ArrayList<String> urls;
    private int width;
    private int size;

    public BannerRecyclerAdapter(ArrayList<String> urls, int screen_width, int size) {
        this.urls = urls;
        width = screen_width;
        this.size = max(size, urls.size());
    }

    class BannerViewHolder extends RecyclerView.ViewHolder {
        LinearLayout layout;

        BannerViewHolder(LinearLayout v) {
            super(v);
            layout = v;
        }
    }

    @NonNull
    @Override
    public BannerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.banner_recycler_layout, parent, false);
        v.findViewById(R.id.imgHolder).setClipToOutline(true);
        return new BannerViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BannerViewHolder holder, int position) {
        if (width > 0) {
            holder.layout.getLayoutParams().width = width;
        }
        Picasso.get().load(urls.get(position % urls.size()))
                .fit()
                .into((ImageView) holder.layout.findViewById(R.id.imgHolder));
    }

    @Override
    public int getItemCount() {
        return size;
    }
}

