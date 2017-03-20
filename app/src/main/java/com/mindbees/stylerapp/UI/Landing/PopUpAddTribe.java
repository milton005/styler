package com.mindbees.stylerapp.UI.Landing;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment ;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mindbees.stylerapp.R;
import com.mindbees.stylerapp.UI.Base.BaseActivity;

/**
 * Created by User on 16-03-2017.
 */

public class PopUpAddTribe extends BaseActivity {
    String Tribename;

    TextView heading,sub;
    EditText editTextTribe;
    ImageView closebutton;
    LinearLayout submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_add_tribe);
        initUI();
        SetUPUI();
    }

    private void SetUPUI() {
        closebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if( check())
               {
                   Intent returnIntent=new Intent();
                   returnIntent.putExtra("TribeName",Tribename);
                   returnIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                   setResult(Activity.RESULT_OK,returnIntent);
                   finish();
                   overridePendingTransition(0,0);
               }
            }
        });

    }

    private boolean check() {
        Tribename=editTextTribe.getText().toString().trim();
        if (Tribename.isEmpty())
        {
            editTextTribe.setError("please Enter Tribe");
            return false;
        }
        else
        {
            return true;
        }

    }


    //    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setStyle(android.support.v4.app.DialogFragment.STYLE_NORMAL, R.style.AppTheme);
//    }
//
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        view=inflater.inflate(R.layout.popup_add_tribe,container,false);
//        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        getDialog().getWindow().requestFeature(1);
//
//        initUI(view);
//        return  view;
//    }

    @Override
    public void onBackPressed() {
        Intent returnIntent = new Intent();
        returnIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        setResult(Activity.RESULT_CANCELED, returnIntent);

        super.onBackPressed();
        overridePendingTransition(0,0);
    }

    private void initUI() {
        closebutton= (ImageView) findViewById(R.id.close_button);
        heading= (TextView) findViewById(R.id.textViewAddTribeHeading);
        sub= (TextView) findViewById(R.id.textViewTribe);
        submit= (LinearLayout) findViewById(R.id.layout_submit_Tribe_add);
        editTextTribe= (EditText) findViewById(R.id.editTextTribe);
        Typeface typeface=Typeface.createFromAsset(getAssets(),"fonts/brandon_grotesque_bold.ttf");
        Typeface typeface1=Typeface.createFromAsset(getAssets(),"fonts/BrandonGrotesque-Regular.ttf");
        heading.setTypeface(typeface);
        sub.setTypeface(typeface1);
        editTextTribe.setTypeface(typeface1);
//        closebutton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dismiss();
//            }
//        });

    }
}
