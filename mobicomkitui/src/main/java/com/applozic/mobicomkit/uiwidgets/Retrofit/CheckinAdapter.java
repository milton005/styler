package com.applozic.mobicomkit.uiwidgets.Retrofit;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.applozic.mobicomkit.uiwidgets.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by User on 22-05-2017.
 */

public class CheckinAdapter extends RecyclerView.Adapter<CheckinAdapter.MyHolder>{
    private Context context;
    public ArrayList<ModelCheckin>list;
    public CheckinAdapter(Context context, ArrayList<ModelCheckin>list)
    {
        this.context=context;
        this.list=list;
    }
    @Override
    public CheckinAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.checkinlayout, parent, false);
        return new CheckinAdapter.MyHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CheckinAdapter.MyHolder holder, int position) {
        Picasso.with(context).load(list.get(position).getImagePath()).into(holder.imageView);
        String name=list.get(position).getName();

        Typeface typeface=Typeface.createFromAsset(context.getAssets(),"fonts/brandon_grotesque_bold.ttf");
        Typeface typeface1=Typeface.createFromAsset(context.getAssets(),"fonts/BrandonGrotesque-Regular.ttf");
        holder.name.setTypeface(typeface1);


        holder.name.setText(Html.fromHtml(name));
//        String distance=list.get(position).getDistance();
//        int temp=Integer.parseInt(distance);
//        double d=temp*.001;
//        DecimalFormat numberFormat = new DecimalFormat("#.00");
//        String s=String.valueOf(numberFormat.format(d))+" km";
//        holder.distance.setText(s);
//        if (temp>1000)
//        {
//            int t=temp/1000;
//            int rem=temp%1000;
//            String s=String.valueOf(t)+"."+String.valueOf(rem)+"km";
//            holder.distance.setText(s);
//        }
//        else
//        {
//            String tp=distance+"m";
//            holder.distance.setText(tp);
//        }



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        TextView distance,checkedin,name;
        ImageView imageView;
        public MyHolder(View itemView) {
            super(itemView);

            name= (TextView) itemView.findViewById(R.id.text_Checkedin_Name);
            imageView= (ImageView) itemView.findViewById(R.id.image_checkedin);
        }
    }
}
