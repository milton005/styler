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

public class PopupdeleteTribe extends BaseActivity {
    ImageView edit,delete;
    ImageView close_button;
    int position;
    TextView head;
    TextView textedit,textdelete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popupedittribe);
        initUI();
        SetUPUI();
        Bundle extras=getIntent().getExtras();
        position=extras.getInt("position");

    }

    private void SetUPUI() {
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent=new Intent();
                returnIntent.putExtra("position",position);
                returnIntent.putExtra("delete",false);
                returnIntent.putExtra("edit",true);
                returnIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
                overridePendingTransition(0,0);
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent=new Intent();
                returnIntent.putExtra("position",position);
                returnIntent.putExtra("delete",true);
                returnIntent.putExtra("edit",false);
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

    private void initUI() {
        edit= (ImageView) findViewById(R.id.edit_tribe_image);
        delete= (ImageView) findViewById(R.id.delete_tribe_image);
        close_button= (ImageView) findViewById(R.id.close_button);
        textdelete= (TextView) findViewById(R.id.textViewdeletetribe);
        textedit= (TextView) findViewById(R.id.textViewedittribe);
        head= (TextView) findViewById(R.id.text_head);
        Typeface typeface=Typeface.createFromAsset(getAssets(),"fonts/brandon_grotesque_bold.ttf");
        Typeface typeface1=Typeface.createFromAsset(getAssets(),"fonts/BrandonGrotesque-Regular.ttf");
        head.setTypeface(typeface);
        textedit.setTypeface(typeface);
        textdelete.setTypeface(typeface);
    }
}
