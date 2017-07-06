package com.richie.styler.UI.HOME;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.richie.styler.R;
import com.richie.styler.UI.Base.BaseActivity;

/**
 * Created by User on 11-04-2017.
 */

public class AppPreferenceActivity extends BaseActivity {
    ImageView back;
    TextView Editbrands,EditTribes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.apppreferencelayout);
        initui();
    }

    private void initui() {
        back= (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        Editbrands= (TextView) findViewById(R.id.texteditbrands);
        EditTribes= (TextView) findViewById(R.id.texteditTribes);
        Editbrands.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AppPreferenceActivity.this,EditBrandsActivity.class);
                startActivity(intent);
            }
        });
        EditTribes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AppPreferenceActivity.this,EditTribesActivity.class);
                startActivity(intent);
            }
        });
    }
}
