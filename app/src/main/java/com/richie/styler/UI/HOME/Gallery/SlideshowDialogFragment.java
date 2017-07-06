package com.richie.styler.UI.HOME.Gallery;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.richie.styler.R;
import com.richie.styler.UI.Models.SupportImages;
import com.richie.styler.UTILS.TouchImageView;

import java.util.ArrayList;



/**
 * Created by user on 8/16/2016.
 */
public class SlideshowDialogFragment extends DialogFragment {
    private String TAG = SlideshowDialogFragment.class.getSimpleName();
    private ArrayList<SupportImages> images;
    ImageView left,right;

    private ViewPager viewPager;
    private MyViewPagerAdapter myViewPagerAdapter;
    private int selectedPosition = 0;
    private TextView lblCount, lblTitle, lblDate;

    static SlideshowDialogFragment newInstance() {
        SlideshowDialogFragment f = new SlideshowDialogFragment();
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_gallery, container, false);
        viewPager = (ViewPager) v.findViewById(R.id.pager);

        lblTitle = (TextView) v.findViewById(R.id.textviewgallery);
        left= (ImageView) v.findViewById(R.id.imageleft);
        right= (ImageView) v.findViewById(R.id.imageright);
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedPosition!=0)
                {
                    selectedPosition=selectedPosition-1;
                    setCurrentItem(selectedPosition);
                }
            }
        });
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedPosition!=images.size()-1)
                {
                    selectedPosition=selectedPosition+1;
                    setCurrentItem(selectedPosition);
                }
            }
        });


        Log.e(TAG, "position: " + selectedPosition);
       Log.e(TAG, "images size: " + images.size());

        myViewPagerAdapter = new MyViewPagerAdapter();
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);
        setCurrentItem(selectedPosition);

        return v;
    }

    private void setCurrentItem(int position) {
        viewPager.setCurrentItem(position, false);
        displayMetaInfo(selectedPosition);
    }

    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            displayMetaInfo(position);
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };

    private void displayMetaInfo(int position) {
        lblTitle.setText((position + 1) + " of " + images.size());

//        Image image = images.get(position);
//        lblTitle.setText(image.getName());
//        lblDate.setText(image.getTimestamp());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        images = (ArrayList<SupportImages>) getArguments().getSerializable("images");
        selectedPosition=getArguments().getInt("position");


    }



    public class MyViewPagerAdapter extends PagerAdapter {

        private LayoutInflater layoutInflater;

        public MyViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.slidingimages_layout, container, false);

            TouchImageView imageViewPreview = (TouchImageView) view.findViewById(R.id.imageview_gallery);

            Glide.with(getActivity()).load(images.get(position).getImages()).thumbnail(0.5f)
                   .crossFade()
                   .diskCacheStrategy(DiskCacheStrategy.ALL).into(imageViewPreview);

//            Glide.with(getActivity()).load(image.getLarge())
//
//                    .into(imageViewPreview);

            container.addView(view);

            return view;
        }

        @Override
        public int getCount() {
            return images.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((View) object);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
