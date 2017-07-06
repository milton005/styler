package com.richie.styler.UI.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.richie.styler.R;
import com.richie.styler.UI.Models.Spinnermodelwithimage;

import java.util.List;

/**
 * Created by User on 26-05-2017.
 */

public class SpinnerArrayAdapter extends ArrayAdapter<Spinnermodelwithimage> {
    int groupid;
    public View v;
    public List<Spinnermodelwithimage> list ;
    Context _activity;
    LayoutInflater inflator;
    public SpinnerArrayAdapter(@NonNull Context context, @LayoutRes int resource, @IdRes int textViewResourceId, @NonNull List <Spinnermodelwithimage>list) {
        super(context, resource, textViewResourceId, list);
        this._activity=context;
        this.list=list;
        this.inflator=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.groupid=resource;
    }
    public View getView1(int position, View convertView, ViewGroup parent ){
        View itemView=inflator.inflate(groupid,parent,false);

        ImageView imageView=(ImageView)itemView.findViewById(R.id.imagespinnertribe);
        TextView textView=(TextView)itemView.findViewById(R.id.company);
        if (list.get(position).getImagepath().isEmpty())
        {
            imageView.setVisibility(View.GONE);

        }

        else {
            imageView.setVisibility(View.VISIBLE);
            Glide.with(_activity).load(list.get(position).getImagepath()).into(imageView);
        }
        Typeface typeface=Typeface.createFromAsset(_activity.getAssets(),"fonts/brandon_grotesque_bold.ttf");
        Typeface typeface1=Typeface.createFromAsset(_activity.getAssets(),"fonts/BrandonGrotesque-Regular.ttf");
        textView.setTypeface(typeface1);
        textView.setText(list.get(position).getName());
        return itemView;
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent){
        this.v = convertView;
        if(v==null) {
            LayoutInflater vi = (LayoutInflater)_activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v=vi.inflate(R.layout.spinner_item, null);
        }



        return v;
    }
    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getView1(position,convertView,parent);
    }

}
