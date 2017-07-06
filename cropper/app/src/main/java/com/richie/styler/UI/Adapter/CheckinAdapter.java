package com.richie.styler.UI.Adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.richie.styler.R;
import com.richie.styler.UI.Models.ModelCheckin;
import com.richie.styler.UI.Models.Venues.User;
import com.richie.styler.UI.Models.Venues.Venue;
import com.richie.styler.UTILS.CircleTransform;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by User on 22-05-2017.
 */

public class CheckinAdapter extends RecyclerView.Adapter<CheckinAdapter.MyHolder>{
    private Context context;
    List<Venue> venues;
    List<User>user;
    private ImageView[] dots;
    public ArrayList<ModelCheckin>list;
    public CheckinAdapter(Context context,ArrayList<ModelCheckin>list)
    {
        this.context=context;
        this.list=list;
    }
    @Override
    public CheckinAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_checkinlayout, parent, false);
        return new CheckinAdapter.MyHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final CheckinAdapter.MyHolder holder, int position) {
        Picasso.with(context).load(list.get(position).getImagePath()).transform(new CircleTransform()).into(holder.imageView);
        String name=list.get(position).getName();

        Typeface typeface=Typeface.createFromAsset(context.getAssets(),"fonts/brandon_grotesque_bold.ttf");
        Typeface typeface1=Typeface.createFromAsset(context.getAssets(),"fonts/BrandonGrotesque-Regular.ttf");
        holder.name.setTypeface(typeface1);
        holder.checkedin.setTypeface(typeface1);

        holder.name.setText(Html.fromHtml(name));
        String distance=list.get(position).getDistance();
        int temp=Integer.parseInt(distance);
        double d=temp*.001;
        DecimalFormat numberFormat = new DecimalFormat("#.00");
        String s=String.valueOf(numberFormat.format(d))+" km";
        holder.distance.setText(s);
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
        String chno=list.get(position).getCheckedinNumber();
        if (chno!=null) {
            int chtemp = Integer.parseInt(chno);
            if (chtemp > 0) {
                holder.checkedinusers.setVisibility(View.VISIBLE);
                String string = "<b>" + "<font color=\"#76389d\">" + chno + " Person" + "</font>" + "</b> " + "checked-in";
                holder.checkedin.setText(Html.fromHtml(string));
               String userphotos=list.get(position).getUserphotos();

                String sub[]=userphotos.split(",");
//                dots = new ImageView[sub.length];
                holder.image1.setVisibility(View.GONE);
                holder.image2.setVisibility(View.GONE);
                holder.image3.setVisibility(View.GONE);
                holder.image4.setVisibility(View.GONE);
                holder.image5.setVisibility(View.GONE);
                for(int i=0;i<sub.length;i++)
                {
                    if (i==0)
                    {
                        holder.image1.setVisibility(View.VISIBLE);
                        Glide.with(context).load(sub[i]).into(holder.image1);
                    }
                    else if (i==1)
                    {  holder.image2.setVisibility(View.VISIBLE);
                        Glide.with(context).load(sub[i]).into(holder.image2);
                    }
                    else if (i==2)
                    {  holder.image3.setVisibility(View.VISIBLE);
                        Glide.with(context).load(sub[i]).into(holder.image3);
                    }
                    else if (i==3)
                    {
                        holder.image4.setVisibility(View.VISIBLE);
                        Glide.with(context).load(sub[i]).into(holder.image4);
                    }
                    else if(i==4)
                    {  holder.image5.setVisibility(View.VISIBLE);
                        Glide.with(context).load(sub[i]).into(holder.image5);
                    }
//                    dots[i] = new ImageView(context);
//                    Picasso.with(context).load(sub[i]).transform(new CircleTransform()).into(dots[i]);
//                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
//                            LinearLayout.LayoutParams.WRAP_CONTENT,
//                            LinearLayout.LayoutParams.WRAP_CONTENT
//                    );
//                    params.width=dpToPx(25);
//                    params.height=dpToPx(25);
//                    params.setMargins(4,0,4,0);
//                    holder.checkedinusers.addView(dots[i],params);
                }
//                getwebservice(list.get(position).getName(),holder);
            }
            else
            {
                holder.checkedinusers.setVisibility(View.GONE);
                holder.checkedin.setText("");
            }
        }
        else
        {
            holder.checkedinusers.setVisibility(View.GONE);
            holder.checkedin.setText("");
        }


    }
//    private void getwebservice(final String name, final CheckinAdapter.MyHolder holder) {
//
//
//    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }
    public class MyHolder extends RecyclerView.ViewHolder {
        TextView distance,checkedin,name;
        RelativeLayout checkedinusers;
        CircleImageView image1,image2,image3,image4,image5;
        ImageView imageView;
        public MyHolder(View itemView) {
            super(itemView);
            distance= (TextView) itemView.findViewById(R.id.textCheckedinDistance);
            checkedin= (TextView) itemView.findViewById(R.id.textCheckedin);
            checkedinusers= (RelativeLayout) itemView.findViewById(R.id.checedinuserslayout);
            name= (TextView) itemView.findViewById(R.id.text_Checkedin_Name);
            imageView= (ImageView) itemView.findViewById(R.id.image_checkedin);
            image1= (CircleImageView) itemView.findViewById(R.id.image1);
            image2= (CircleImageView) itemView.findViewById(R.id.image2);
            image3= (CircleImageView) itemView.findViewById(R.id.image3);
            image4= (CircleImageView) itemView.findViewById(R.id.image4);
            image5= (CircleImageView) itemView.findViewById(R.id.image5);

        }
    }
}
