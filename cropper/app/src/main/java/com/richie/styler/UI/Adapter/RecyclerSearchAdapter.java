package com.richie.styler.UI.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.richie.styler.R;
import com.richie.styler.UI.Models.ImagegridModel;
import com.richie.styler.UTILS.BlockinterfaceCallback;
import com.richie.styler.UTILS.BlocktodetailCallback;

import java.util.ArrayList;

/**
 * Created by User on 18-04-2017.
 */

public class RecyclerSearchAdapter extends RecyclerView.Adapter<RecyclerSearchAdapter.ViewHolder> {
    private Context context;
    int width,height;
    private BlockinterfaceCallback callback;
    private BlocktodetailCallback blocktodetailCallback;
    public ArrayList<ImagegridModel> imagegridModels;
    public RecyclerSearchAdapter(Context context,ArrayList<ImagegridModel>imagegridModels)
    {
        this.context=context;
        this.imagegridModels=imagegridModels;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_recycler_search, parent, false);
        return new RecyclerSearchAdapter.ViewHolder(itemView);


    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Glide.with(context).load(imagegridModels.get(position).getTribeImage()).placeholder(R.drawable.user_default).into(holder.imageView);
        holder.textuser.setText(imagegridModels.get(position).getTribeName());

        if (imagegridModels.get(position).isSelected())
        {
            holder.blocklayout.setBackground(context.getResources().getDrawable(R.drawable.rectangle_3_layout));
            holder.blocktext.setText("UNBLOCK");
            holder.blocktext.setTextColor(context.getResources().getColor(R.color.white));

        }
        else {
            holder.blocklayout.setBackground(context.getResources().getDrawable(R.drawable.rectangle_layout2));
            holder.blocktext.setText("BLOCK");
            holder.blocktext.setTextColor(context.getResources().getColor(R.color.dark_slate_blue));
        }
        holder.blocklayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback!=null)
                {if (imagegridModels.get(position).isSelected())
                {

                    holder.blocklayout.setBackground(context.getResources().getDrawable(R.drawable.rectangle_layout2));
                    holder.blocktext.setText("BLOCK");
                    holder.blocktext.setTextColor(context.getResources().getColor(R.color.dark_slate_blue));
                    callback.setBlock(position,0);
                    imagegridModels.get(position).setSelected(false);
                    notifyDataSetChanged();
                }
                else {
                    holder.blocklayout.setBackground(context.getResources().getDrawable(R.drawable.rectangle_3_layout));
                    holder.blocktext.setText("UNBLOCK");
                    holder.blocktext.setTextColor(context.getResources().getColor(R.color.white));
                    callback.setBlock(position,1);
                    imagegridModels.get(position).setSelected(true);
                    notifyDataSetChanged();
                }

                }
            }
        });
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                blocktodetailCallback.setcallback(position);
            }
        });
        
    }
    public void setOnBlockOnlistener(BlockinterfaceCallback l) {
        callback = l;
    }
    public void setonBlockdetaillistener(BlocktodetailCallback m){
        blocktodetailCallback=m;
    }
    @Override
    public int getItemCount() {
        return this.imagegridModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textuser;
        LinearLayout blocklayout;
        TextView blocktext;
        public ViewHolder(View itemView) {
            super(itemView);
            imageView= (ImageView) itemView.findViewById(R.id.image_blockuser);
            textuser= (TextView) itemView.findViewById(R.id.text_blockuser);
            blocklayout= (LinearLayout) itemView.findViewById(R.id.layoutblockuser);
            blocktext= (TextView) itemView.findViewById(R.id.text_block_layout);
        }
    }
}
