package com.richie.styler.UI.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.richie.styler.R;
import com.richie.styler.UI.Models.Spinnermodelwithimage;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by User on 17-04-2017.
 */

public class SpinnerwithimageAdapter extends BaseAdapter {
    public List<Spinnermodelwithimage> list ;
    Context _activity;
    LayoutInflater inflator;
    public SpinnerwithimageAdapter(Context context, List<Spinnermodelwithimage> objects) {
        this.list = objects;
        this._activity = context;
        inflator = LayoutInflater.from(_activity);
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        LayoutInflater inflater = ((Activity) c).getLayoutInflater();
        View v = convertView;

        SpinnerwithimageAdapter.ViewHolder holder;
        if (v == null) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.spinner_item, null);
        }


        else{
            holder = (SpinnerwithimageAdapter.ViewHolder) v.getTag();
        }
        holder = new SpinnerwithimageAdapter.ViewHolder();
        Typeface typeface=Typeface.createFromAsset(_activity.getAssets(),"fonts/brandon_grotesque_bold.ttf");
        Typeface typeface1=Typeface.createFromAsset(_activity.getAssets(),"fonts/BrandonGrotesque-Regular.ttf");
        holder.tvtitle = (TextView) v.findViewById(R.id.textspinner);

        holder.tvtitle.setTypeface(typeface);
//            holder.ivselector = (ImageView) v.findViewById(R.id.imgSelector);
//			holder.imgShop = (ImageView) v.findViewById(R.id.categryIcon);

        v.setTag(holder);
        holder.tvtitle.setText(list.get(position).getName());
        return v;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        SpinnerwithimageAdapter.ViewHolder holder;
        if (v == null) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.newrow, null);}


        else{
            holder = (SpinnerwithimageAdapter.ViewHolder) v.getTag();
        }
        holder = new SpinnerwithimageAdapter.ViewHolder();
        Typeface typeface=Typeface.createFromAsset(_activity.getAssets(),"fonts/brandon_grotesque_bold.ttf");
        Typeface typeface1=Typeface.createFromAsset(_activity.getAssets(),"fonts/BrandonGrotesque-Regular.ttf");

        holder.imageView= (ImageView) v.findViewById(R.id.imageLine);
        holder.pic= (ImageView) v.findViewById(R.id.imagespinnertribe);
        if (list.get(position).getName().equals("Ethnicity"))
        {
            holder.imageView.setVisibility(View.GONE);
            holder.tvtitle = (TextView) v.findViewById(R.id.company);
            holder.tvtitle.setGravity(Gravity.CENTER);
            holder.tvtitle.setTypeface(typeface);
        }
        else
        {
            holder.tvtitle = (TextView) v.findViewById(R.id.company);

            holder.imageView.setVisibility(View.VISIBLE);
            holder.tvtitle.setGravity(Gravity.LEFT);
            holder.tvtitle.setTypeface(typeface1);
        }
//            if (list.get(position).getName().equals("Others"))
//            {
//                holder.imageView.setVisibility(View.INVISIBLE);
//            }


//            holder.ivselector = (ImageView) v.findViewById(R.id.imgSelector);
//			holder.imgShop = (ImageView) v.findViewById(R.id.categryIcon);

        v.setTag(holder);
        try {
            if (list.get(position).getName().equals("SELECT TRIBE"))
           {
               holder.pic.setVisibility(View.GONE);
            }
          else {
                Picasso.with(_activity).load(list.get(position).getImagepath()).placeholder(R.drawable.logo).into(holder.pic);
           }
        }catch (Exception e)
        {

        }


        holder.tvtitle.setText(list.get(position).getName());
        return v;
    }

    class ViewHolder {
        ImageView pic;
        TextView tvtitle;
        ImageView imageView;

    }
}
