package com.richie.styler.UI.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.richie.styler.R;
import com.richie.styler.UI.Models.SpinnerModel;

import java.util.List;

/**
 * Created by User on 20-04-2017.
 */

public class RecyclerHeightAdapter extends RecyclerView.Adapter<RecyclerHeightAdapter.MyVIEWHOLDER> {
    private Context context;
    int width,height;
    public List<SpinnerModel> list ;
    public RecyclerHeightAdapter(Context context,List<SpinnerModel>list)
    {
        this.context=context;
        this.list=list;
    }
    @Override
    public MyVIEWHOLDER onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_height, parent, false);
        return new RecyclerHeightAdapter.MyVIEWHOLDER(itemView);
    }

    @Override
    public void onBindViewHolder(MyVIEWHOLDER holder, int position) {
        Typeface typeface=Typeface.createFromAsset(context.getAssets(),"fonts/brandon_grotesque_bold.ttf");
        Typeface typeface1=Typeface.createFromAsset(context.getAssets(),"fonts/BrandonGrotesque-Regular.ttf");
        holder.textView.setTypeface(typeface);
        holder.textView.setText(list.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return this.list.size();
    }

    public class MyVIEWHOLDER extends RecyclerView.ViewHolder {
        TextView textView;
        public MyVIEWHOLDER(View itemView) {
            super(itemView);
            textView= (TextView) itemView.findViewById(R.id.company);
        }
    }
}
