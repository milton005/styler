package com.richie.styler.UI.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.richie.styler.R;
import com.richie.styler.UI.Models.SupportImages;

import java.util.ArrayList;

/**
 * Created by User on 12-04-2017.
 */

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.MyHolder> {
    private Context context;
    int width,height;
    private ArrayList<SupportImages> images;
    public ImageAdapter(Context context, ArrayList<SupportImages> images) {
        this.context=context;
        this.images=images;
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager()
                .getDefaultDisplay()
                .getMetrics(displayMetrics);
        height = displayMetrics.heightPixels;
        width = displayMetrics.widthPixels;
    }
    public SupportImages getObject(int position) {
        return images.get(position);
    }
    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_images, parent, false);
        return new MyHolder(itemView);

    }


    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        int h= (width-32)/4;
        ViewGroup.LayoutParams params = holder.imageView.getLayoutParams();
        params.height=h;
        holder.imageView.setLayoutParams(params);
        Glide.with(context).load(images.get(position).getImages()).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return this.images.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        public MyHolder(View itemView) {
            super(itemView);
            imageView= (ImageView) itemView.findViewById(R.id.homeImages);
        }
    }
}
