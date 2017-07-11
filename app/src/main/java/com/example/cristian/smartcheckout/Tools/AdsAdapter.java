package com.example.cristian.smartcheckout.Tools;

import android.content.res.Resources;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.cristian.smartcheckout.R;

import java.util.ArrayList;

/**
 * Created by Cristian on 7/7/2017.
 */

public class AdsAdapter extends RecyclerView.Adapter<AdsAdapter.MyViewHolder> {

    private ArrayList<Integer> ads;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;

        public MyViewHolder(View view) {
            super(view);
            image = (ImageView)view.findViewById(R.id.ad_image);

        }
    }

    public AdsAdapter(ArrayList<Integer> ads) {
        this.ads = ads;
    }

    @Override
    public AdsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_row_ads, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AdsAdapter.MyViewHolder holder, int position) {
        Integer ad = ads.get(position);
        holder.image.setImageResource(ad);
    }

    @Override
    public int getItemCount() {

        return ads.size();
    }
}
