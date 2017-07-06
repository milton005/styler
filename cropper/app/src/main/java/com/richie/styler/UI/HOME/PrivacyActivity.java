package com.richie.styler.UI.HOME;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.richie.styler.R;
import com.richie.styler.UI.Base.BaseActivity;

/**
 * Created by User on 11-04-2017.
 */

public class PrivacyActivity extends BaseActivity{
    ImageView back;
    TextView texthead,textcontent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.privacyactivitylayout);
        initui();

    }

    private void initui() {
        Typeface typeface=Typeface.createFromAsset(getAssets(),"fonts/brandon_grotesque_bold.ttf");
        Typeface typeface1=Typeface.createFromAsset(getAssets(),"fonts/BrandonGrotesque-Regular.ttf");
        back= (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        textcontent= (TextView) findViewById(R.id.textcontent);
        texthead= (TextView) findViewById(R.id.text_head_privacy);
        texthead.setTypeface(typeface);
        textcontent.setTypeface(typeface1);
    }
}
