package com.richie.styler.UI.Adapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.richie.styler.R;
import com.richie.styler.UI.Models.Spinnermodelwithimage;

import java.util.ArrayList;

/**
 * Created by User on 17-04-2017.
 */

public class CustomAdapter extends ArrayAdapter<String> {
    private Context activity;
    private ArrayList data;
    public Resources res;
    LayoutInflater inflater;
    Spinnermodelwithimage tempValues=null;
    public CustomAdapter(@NonNull Context context, @LayoutRes int resource, ArrayList objects, Resources reslocal) {
        super(context, resource);
        activity=context;
        res=reslocal;
        data=objects;
        inflater.from(context).inflate(R.layout.spinner_item,null);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return super.getView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }
    public View getCustomView(int position, View convertView, ViewGroup parent) {

        /********** Inflate spinner_rows.xml file for each row ( Defined below ) ************/
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.newrow, null);
        tempValues = null;
        tempValues = (Spinnermodelwithimage) data.get(position);
        ImageView pic= (ImageView) row.findViewById(R.id.imagespinnertribe);
        TextView tvtitle= (TextView) row.findViewById(R.id.company);
        ImageView imageView= (ImageView) row.findViewById(R.id.imageLine);
        /***** Get each Model object from Arraylist ********/


        return row;
    }
}
