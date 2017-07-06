package com.richie.styler.UI.DETAIL;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.richie.styler.R;
import com.richie.styler.UI.Base.BaseActivity;

/**
 * Created by User on 26-04-2017.
 */

public class MoreDetailsActivity extends BaseActivity {
    TextView textViewhead,textViewstatus,textViewusername,textViewOnlinestatus,textViewage,textViewdistance,textViewMyStylerTribehead,textViewmystyler;
    TextView textViewIamhead,textViewIam,textViewIlikehead,textViewilike,textViewdesignerhead,textViewdesigner,textViewstylehead,textViewstyle;
    TextView textViewnevergoHead,textViewnevergo;
    ImageView onlineimage,back;
    String Tusername,Tage,Tstylertribe,Tbrands,Tiam,Tilike,Tnevergo,Tdistance,Tsyleicons,Online;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.moredetaillayout);
        initUi();
        try {
            Bundle Extras=getIntent().getExtras();
            Tusername=Extras.getString("username");
            Tage=Extras.getString("age");
            Tstylertribe=Extras.getString("tribes");
            Tbrands=Extras.getString("brands");
            Tiam=Extras.getString("iam");
            Tilike=Extras.getString("ilike");
            Tnevergo=Extras.getString("nevergo");
            Tdistance=Extras.getString("distance");
            Tsyleicons=Extras.getString("styleicons");
            Online= Extras.getString("online");
            int onlinetemp=Integer.valueOf(Online);
            textViewhead.setText(Tusername);
            if (onlinetemp==1)
            {
                textViewstatus.setText("ONLINE NOW");
                textViewOnlinestatus.setText("Online");
                onlineimage.setColorFilter(ContextCompat.getColor(this,R.color.green));
            }
            else {
                textViewstatus.setText("OFFLINE NOW");
                textViewOnlinestatus.setText("Offline");
                onlineimage.setColorFilter(ContextCompat.getColor(this,R.color.yellow));
            }
            if (Tiam.equals("m"))
            {
                textViewIam.setText("male");
            }
            else
            {
                textViewIam.setText("male");
            }
            textViewusername.setText(Tusername);

            textViewnevergo.setText(Tnevergo);
            textViewage.setText(Tage);
            textViewdesigner.setText(Tbrands);
            textViewdistance.setText(Tdistance);
            textViewilike.setText(Tilike);
            textViewmystyler.setText(Tstylertribe);
            textViewstyle.setText(Tsyleicons);
        }catch (Exception e)
        {

        }
        setui();
    }

    private void setui() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                onBackPressed();
                overridePendingTransition(R.anim.stay, R.anim.slide_out_up);

            }
        });
    }


    private void initUi() {
        textViewhead= (TextView) findViewById(R.id.text_head_userdeatil);
        textViewstatus= (TextView) findViewById(R.id.textuserdetail_onlinestatus);
        textViewusername= (TextView) findViewById(R.id.textUsername);
        textViewage= (TextView) findViewById(R.id.textuseryear);
        textViewOnlinestatus= (TextView) findViewById(R.id.textOnlineStatus);
        textViewdistance= (TextView) findViewById(R.id.textdistance);
        textViewMyStylerTribehead= (TextView) findViewById(R.id.textMystylerTribehead);
        textViewmystyler= (TextView) findViewById(R.id.textMystylerTribe);
        textViewIamhead= (TextView) findViewById(R.id.textIamhead);
        textViewIam= (TextView) findViewById(R.id.textIam);
        textViewIlikehead= (TextView) findViewById(R.id.textIlikehead);
        textViewilike= (TextView) findViewById(R.id.textIlike);
        textViewdesignerhead= (TextView) findViewById(R.id.textfavhead);
        textViewdesigner= (TextView) findViewById(R.id.textfav);
        textViewstylehead= (TextView) findViewById(R.id.textstyleiconhead);
        textViewstyle= (TextView) findViewById(R.id.textstyleicon);
        textViewnevergoHead= (TextView) findViewById(R.id.textnevergohead);
        textViewnevergo= (TextView) findViewById(R.id.textnevergo);
        onlineimage= (ImageView) findViewById(R.id.imageround);
        back= (ImageView) findViewById(R.id.back);
        Typeface typeface=Typeface.createFromAsset(getAssets(),"fonts/brandon_grotesque_bold.ttf");
        Typeface typeface1=Typeface.createFromAsset(getAssets(),"fonts/BrandonGrotesque-Regular.ttf");
        textViewhead.setTypeface(typeface);

    }
}
