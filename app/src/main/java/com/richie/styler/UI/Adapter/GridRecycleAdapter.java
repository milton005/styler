package com.richie.styler.UI.Adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.richie.styler.R;
import com.richie.styler.UI.Models.ImagegridModel;
import com.richie.styler.UTILS.CustomProgressDialog;
import com.richie.styler.UTILS.Progressindicator;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by User on 10-03-2017.
 */

public class GridRecycleAdapter extends RecyclerView.Adapter<GridRecycleAdapter.VIEWHOLDER> {
    private Context context;
    int width,height;
    ProgressDialog pDialog;
    private Progressindicator l;


    public ArrayList<ImagegridModel>imagegridModels;
    public GridRecycleAdapter(Context context,ArrayList<ImagegridModel>imagegridModels)
    {
        this.context=context;
        this.imagegridModels=imagegridModels;
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager()
                .getDefaultDisplay()
                .getMetrics(displayMetrics);
         height = displayMetrics.heightPixels;
         width = displayMetrics.widthPixels;
//        showProgress();
    }
    public ImagegridModel getObject(int position)
    {
        return imagegridModels.get(position);
    }
    @Override
    public GridRecycleAdapter.VIEWHOLDER onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_row, parent, false);
//        showProgress();
        return new VIEWHOLDER(itemView);

    }
   public void setonprogresslistener(Progressindicator progressindicator)
   {
       l=progressindicator;
   }
    @Override
    public void onBindViewHolder(final GridRecycleAdapter.VIEWHOLDER holder, int position) {

        if (imagegridModels.get(position).getTribeImage().isEmpty())
        {
//            hideProgress();
            try {
                l.setprogess(true);
            }catch (Exception e)
            {

            }
            Picasso.with(context).load(R.drawable.logo).placeholder(R.drawable.logo).into(holder.tribe_image);
            holder.otherlayout.setBackground(context.getResources().getDrawable(R.drawable.rectangle_3));
            holder.tribe_selected.setVisibility(View.VISIBLE);
            holder.tribe_selected.setBackgroundColor(Color.TRANSPARENT);

        }
        else {
//           hideProgress();

            if (position==imagegridModels.size()-1) {


//                Toast.makeText(context,"Toast",Toast.LENGTH_LONG).show();
                    Picasso.with(context)
                            .load(imagegridModels.get(position).getTribeImage())
                            .into(holder.tribe_image, new com.squareup.picasso.Callback() {
                                @Override
                                public void onSuccess() {
                                    try {
                                        l.setprogess(true);
                                    }catch (Exception e){

                                    }
//                                hideProgress();


                                }

                                @Override
                                public void onError() {
                                    l.setprogess(true);
                                }
                            });


            } else {
//                hideProgress();


                Picasso.with(context).load(imagegridModels.get(position).getTribeImage()).placeholder(R.drawable.logo).into(holder.tribe_image);
//            Picasso.with(context).load(imagegridModels.get(position).getTribeImage()).into(new Target() {
//                @Override
//                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
//                   hideProgress();
//                }
//
//                @Override
//                public void onBitmapFailed(Drawable errorDrawable) {
//                    hideProgress();
//
//                }
//
//                @Override
//                public void onPrepareLoad(Drawable placeHolderDrawable) {
//                    showProgress();
//
//                }
//            });
            }
                holder.otherlayout.setBackground(context.getResources().getDrawable(R.drawable.rectangle_2));
                if (imagegridModels.get(position).isSelected()) {
                    holder.tribe_selected.setVisibility(View.VISIBLE);
                    holder.tribe_selected.setBackgroundColor(context.getResources().getColor(R.color.grey_white));
                } else {
                    holder.tribe_selected.setVisibility(View.GONE);
                }

            }

            holder.tribe_name.setText(imagegridModels.get(position).getTribeName());
            int h = height / 6;
            ViewGroup.LayoutParams params = holder.imagelayout.getLayoutParams();
            params.height = h;
            holder.imagelayout.setLayoutParams(params);

            Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/brandon_grotesque_bold.ttf");
            Typeface typeface1 = Typeface.createFromAsset(context.getAssets(), "fonts/BrandonGrotesque-Regular.ttf");
            holder.tribe_name.setTypeface(typeface1);


    }

    @Override
    public int getItemCount() {
        return this.imagegridModels.size();
    }

    public class VIEWHOLDER extends RecyclerView.ViewHolder {
        TextView tribe_name;
        ImageView tribe_image;
        RelativeLayout imagelayout;
        RelativeLayout tribe_selected;
        RelativeLayout otherlayout;


        public VIEWHOLDER(View itemView) {
            super(itemView);
            tribe_image= (ImageView) itemView.findViewById(R.id.imageTribe);
            tribe_name= (TextView) itemView.findViewById(R.id.imageTribeName);
            tribe_selected= (RelativeLayout) itemView.findViewById(R.id.image_select);
            imagelayout= (RelativeLayout) itemView.findViewById(R.id.imagelayout);
            otherlayout= (RelativeLayout) itemView.findViewById(R.id.other_image_layout);

        }
    }
    private ProgressDialog getProgressDialog() {
        if (this.pDialog == null) {
            this.pDialog = CustomProgressDialog.nowRunningDialog(context);
        }
        return this.pDialog;
    }

    public void showProgress() {
        getProgressDialog();
        if (this.pDialog != null) {
            this.pDialog.show();
        }

    }

    public void hideProgress() {
        if (pDialog != null && this.pDialog.isShowing()) {
            this.pDialog.dismiss();
            pDialog = null;
        }
    }
}
