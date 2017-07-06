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
import com.richie.styler.UI.Models.SpinnerModel;

import java.util.List;

/**
 * Created by User on 07-03-2017.
 */

public class SpinnerAdapter extends BaseAdapter {
    public List<SpinnerModel> list ;
    Context _activity;
    LayoutInflater inflator;
    public SpinnerAdapter(Context context, List<SpinnerModel> objects) {
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

        ViewHolder holder;
        if (v == null) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.spinner_item, null);
            holder = new ViewHolder();
            Typeface typeface=Typeface.createFromAsset(_activity.getAssets(),"fonts/brandon_grotesque_bold.ttf");
            Typeface typeface1=Typeface.createFromAsset(_activity.getAssets(),"fonts/BrandonGrotesque-Regular.ttf");
            holder.tvtitle = (TextView) v.findViewById(R.id.textspinner);

            holder.tvtitle.setTypeface(typeface);
//            holder.ivselector = (ImageView) v.findViewById(R.id.imgSelector);
//			holder.imgShop = (ImageView) v.findViewById(R.id.categryIcon);

            v.setTag(holder);
        }
        else{
            holder = (ViewHolder) v.getTag();
        }

        holder.tvtitle.setText(list.get(position).getName());
        return v;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        ViewHolder holder;
        if (v == null) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, null);
            holder = new ViewHolder();
            Typeface typeface=Typeface.createFromAsset(_activity.getAssets(),"fonts/brandon_grotesque_bold.ttf");
            Typeface typeface1=Typeface.createFromAsset(_activity.getAssets(),"fonts/BrandonGrotesque-Regular.ttf");

            holder.imageView= (ImageView) v.findViewById(R.id.imageLine);
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
        }
        else{
            holder = (ViewHolder) v.getTag();
        }

        holder.tvtitle.setText(list.get(position).getName());
        return v;
    }

    class ViewHolder {
        TextView tvtitle;
        ImageView imageView;

    }
}
