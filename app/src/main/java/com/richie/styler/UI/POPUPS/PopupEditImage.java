package com.richie.styler.UI.POPUPS;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.richie.styler.R;
import com.richie.styler.UI.Base.BaseActivity;

/**
 * Created by User on 30-03-2017.
 */

public class PopupEditImage extends BaseActivity {
    ImageView gallery,camera,close_button;
    TextView haed,gallerytext,camera_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_edit_image);
        initui();
        setupui();

    }

    private void setupui() {
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent=new Intent();

                returnIntent.putExtra("gallery",true);
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

                returnIntent.putExtra("gallery",false);
                returnIntent.putExtra("camera",true);
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
        haed= (TextView) findViewById(R.id.text_head);
        gallerytext= (TextView) findViewById(R.id.textViewuploadgallery);
        camera_text= (TextView) findViewById(R.id.textViewtakepic);
        Typeface typeface=Typeface.createFromAsset(getAssets(),"fonts/brandon_grotesque_bold.ttf");
        Typeface typeface1=Typeface.createFromAsset(getAssets(),"fonts/BrandonGrotesque-Regular.ttf");
        haed.setTypeface(typeface);
        gallerytext.setTypeface(typeface);
        camera_text.setTypeface(typeface);
    }
}
