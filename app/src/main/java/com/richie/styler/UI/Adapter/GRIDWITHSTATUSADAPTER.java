package com.richie.styler.UI.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.richie.styler.R;
import com.richie.styler.UI.Models.GridhomemodelwithStatus;

import java.util.ArrayList;

/**
 * Created by User on 18-05-2017.
 */

public class GRIDWITHSTATUSADAPTER extends RecyclerView.Adapter<GRIDWITHSTATUSADAPTER.MYholder> {
    int width,height;
    private Context context;
    public ArrayList<GridhomemodelwithStatus> imagegridModels;
    public GRIDWITHSTATUSADAPTER(Context context,ArrayList<GridhomemodelwithStatus> imagegridModels)
    {
        this.context=context;
        this.imagegridModels=imagegridModels;
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager()
                .getDefaultDisplay()
                .getMetrics(displayMetrics);
        height = displayMetrics.heightPixels;
        width = displayMetrics.widthPixels;
    }
    public GridhomemodelwithStatus getobject(int position){
        return imagegridModels.get(position);
    }
    @Override
    public MYholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.gridhomewithcount, parent, false);
        return new GRIDWITHSTATUSADAPTER.MYholder(itemView);
    }

    @Override
    public void onBindViewHolder(MYholder holder, int position) {
        holder.textView.setText(imagegridModels.get(position).getImagename());
        Glide.with(context).load(imagegridModels.get(position).getImagepath()).placeholder(R.drawable.user_default).into(holder.imageView);
//        Picasso.with(context).load(imagegridModels.get(position).getImagepath()).placeholder(R.drawable.user_default).into(holder.imageView);
        int h= (width-32)/4;
        ViewGroup.LayoutParams params = holder.layout.getLayoutParams();
        params.height=h;
        holder.layout.setLayoutParams(params);
        try
        {
            int online=imagegridModels.get(position).getOnline();
            int messagecount=imagegridModels.get(position).getMessagecount();
            if (online==1)
            {
                holder.online.setImageResource(R.drawable.green);

            }
            else
            {
                holder.online.setImageResource(R.drawable.yellow);
            }
            if (messagecount==0)
            {
                holder.container.setVisibility(View.GONE);
            }
            else
            {
                holder.count.setText(String.valueOf(messagecount));
                holder.container.setVisibility(View.VISIBLE);
            }

        }catch (Exception e)
        {


        }
    }

    @Override
    public int getItemCount() {
        return this.imagegridModels.size();
    }

    public class MYholder extends RecyclerView.ViewHolder {
        RelativeLayout layout;
        ImageView imageView;
        TextView textView;
        ImageView online;
        LinearLayout container;
        TextView count;
        public MYholder(View itemView) {
            super(itemView);
            layout= (RelativeLayout) itemView.findViewById(R.id.grid_home_layout);
            imageView= (ImageView) itemView.findViewById(R.id.imagegridhome);
            textView= (TextView) itemView.findViewById(R.id.textView_grid_home);
            online= (ImageView) itemView.findViewById(R.id.imageOnline);
            container= (LinearLayout) itemView.findViewById(R.id.badgecontainer);
            count= (TextView) itemView.findViewById(R.id.badgetext);
        }
    }
}
