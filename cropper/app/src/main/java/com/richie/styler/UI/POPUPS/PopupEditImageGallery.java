package com.richie.styler.UI.POPUPS;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.richie.styler.R;
import com.richie.styler.UI.Base.BaseActivity;

/**
 * Created by User on 12-04-2017.
 */

public class PopupEditImageGallery extends BaseActivity {
    ImageView gallery,camera,close_button;
    TextView haed,gallerytext,camera_text;
    ImageView viewGallery;
    ImageView addgallery;
    TextView textAddgallery;
    TextView TextViewGallery;
    TextView mygallery;
    LinearLayout layoutgallery,layoutprofile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popupgalllery);


        initui();
        try {
            Bundle Extras=getIntent().getExtras();
            boolean isprofile=Extras.getBoolean("image");
            if (isprofile)
            {
                layoutprofile.setVisibility(View.VISIBLE);
                layoutgallery.setVisibility(View.GONE);
                haed.setVisibility(View.VISIBLE);
                mygallery.setVisibility(View.GONE);

            }
            else
            {
                layoutprofile.setVisibility(View.GONE);
                layoutgallery.setVisibility(View.VISIBLE);
                haed.setVisibility(View.GONE);
                mygallery.setVisibility(View.VISIBLE);
            }
        }catch (Exception e)
        {

        }
        setupui();
    }
    private void setupui() {
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent=new Intent();

                returnIntent.putExtra("gallery",true);
                returnIntent.putExtra("add_gallery",false);
                returnIntent.putExtra("view_gallery",false);
                returnIntent.putExtra("camera",false);
                returnIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
                overridePendingTransition(0,0);
            }
        });
        viewGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent=new Intent();
                returnIntent.putExtra("view_gallery",true);
                returnIntent.putExtra("gallery",false);
                returnIntent.putExtra("add_gallery",false);
                returnIntent.putExtra("camera",false);
                returnIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
                overridePendingTransition(0,0);
            }
        });
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent=new Intent();
                returnIntent.putExtra("view_gallery",false);
                returnIntent.putExtra("gallery",false);
                returnIntent.putExtra("add_gallery",false);
                returnIntent.putExtra("camera",true);
                returnIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
                overridePendingTransition(0,0);
            }
        });
        addgallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent=new Intent();
                returnIntent.putExtra("view_gallery",false);
                returnIntent.putExtra("gallery",false);
                returnIntent.putExtra("add_gallery",true);
                returnIntent.putExtra("camera",false);
                returnIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
                overridePendingTransition(0,0);
            }
        });
        close_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }
    @Override
    public void onBackPressed() {
        Intent returnIntent = new Intent();
        returnIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        setResult(Activity.RESULT_CANCELED, returnIntent);

        super.onBackPressed();
        overridePendingTransition(0,0);
    }
    private void initui() {
        gallery= (ImageView) findViewById(R.id.edit_image_upload);
        camera= (ImageView) findViewById(R.id.edit_image_take_picture);
        close_button= (ImageView) findViewById(R.id.close_button);
        viewGallery= (ImageView) findViewById(R.id.edit__view_gallery);
        TextViewGallery= (TextView) findViewById(R.id.textViewgallery);
        haed= (TextView) findViewById(R.id.text_head);
        gallerytext= (TextView) findViewById(R.id.textViewuploadgallery);
        camera_text= (TextView) findViewById(R.id.textViewtakepic);
        addgallery= (ImageView) findViewById(R.id.edit__add_gallery);
        textAddgallery= (TextView) findViewById(R.id.textViewaddgallery);
        mygallery= (TextView) findViewById(R.id.textmygallery);
        layoutgallery= (LinearLayout) findViewById(R.id.layoutgallery);
        layoutprofile= (LinearLayout) findViewById(R.id.layoutprofileimage);


        Typeface typeface=Typeface.createFromAsset(getAssets(),"fonts/brandon_grotesque_bold.ttf");
        Typeface typeface1=Typeface.createFromAsset(getAssets(),"fonts/BrandonGrotesque-Regular.ttf");
        haed.setTypeface(typeface1);
        TextViewGallery.setTypeface(typeface1);
        gallerytext.setTypeface(typeface1);
        camera_text.setTypeface(typeface1);
        textAddgallery.setTypeface(typeface1);
        mygallery.setTypeface(typeface1);
    }
}
