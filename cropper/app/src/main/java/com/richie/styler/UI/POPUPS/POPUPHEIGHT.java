package com.richie.styler.UI.POPUPS;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.richie.styler.R;
import com.richie.styler.UI.Adapter.RecyclerHeightAdapter;
import com.richie.styler.UI.Base.BaseActivity;
import com.richie.styler.UI.Models.SpinnerModel;
import com.richie.styler.UTILS.ItemClickSupport;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 20-04-2017.
 */

public class POPUPHEIGHT extends BaseActivity {
    LinearLayout kgs,pounds;
    TextView textkgs,textpounds;
    RecyclerView recyclerView;
    ImageView Back;
    RecyclerHeightAdapter adapter;
    List<SpinnerModel> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popupheight);
        initui();
        setrecyclerview();
        list=new ArrayList<SpinnerModel>();
        for(int i=120;i<241;i++)

        {
            double d=i*.01;
            DecimalFormat numberFormat = new DecimalFormat("#.00");
            String s=String.valueOf(numberFormat.format(d))+" m";
            SpinnerModel model=new SpinnerModel();
            model.setName(s);
            list.add(model);


        }
        setAdapter();

        setClicks();
    }

    private void setClicks() {
        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                String name=list.get(position).getName();
                Intent returnIntent=new Intent();
                returnIntent.putExtra("name",name);
                returnIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
                overridePendingTransition(0,0);

            }
        });
        kgs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kgs.setBackgroundColor(getResources().getColor(R.color.black));
                textkgs.setTextColor(getResources().getColor(R.color.white));
                pounds.setBackgroundColor(getResources().getColor(R.color.white));
                textpounds.setTextColor(getResources().getColor(R.color.black));
                list.clear();
                for(int i=120;i<241;i++)

                {
                    double d=i*.01;
                    DecimalFormat numberFormat = new DecimalFormat("#.00");
                    String s=String.valueOf(numberFormat.format(d))+" m";
                    SpinnerModel model=new SpinnerModel();
                    model.setName(s);
                    list.add(model);


                }
                adapter.notifyDataSetChanged();

            }
        });
        pounds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kgs.setBackgroundColor(getResources().getColor(R.color.white));
                textkgs.setTextColor(getResources().getColor(R.color.black));
                pounds.setBackgroundColor(getResources().getColor(R.color.black));
                textpounds.setTextColor(getResources().getColor(R.color.white));
                list.clear();
                for (int i=3;i<=7;i++)
                {
                    for (int k=0;k<12;k++)
                    {
                        if (i==3&&k<9)
                        {
                            continue;
                        }
                        if (i==7&&k==5)
                        {
                            break;
                        }

                        String s=i+"'"+k+"\"";
                        SpinnerModel model4=new SpinnerModel();
                        model4.setName(s);
                        list.add(model4);
                    }



                }
                adapter.notifyDataSetChanged();
            }
        });
        Back.setOnClickListener(new View.OnClickListener() {
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

    private void setAdapter() {
        adapter=new RecyclerHeightAdapter(POPUPHEIGHT.this,list);
        recyclerView.setAdapter(adapter);
    }

    private void setrecyclerview() {
        final LinearLayoutManager llm = new LinearLayoutManager(POPUPHEIGHT.this);
        recyclerView.setHasFixedSize(true);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
    }

    private void initui() {
        kgs= (LinearLayout) findViewById(R.id.layout_kg);
        pounds= (LinearLayout) findViewById(R.id.layout_pounds);
        textpounds= (TextView) findViewById(R.id.textpounds);
        textkgs= (TextView) findViewById(R.id.textkg);
        recyclerView= (RecyclerView) findViewById(R.id.recyclerViewHeight);
        Back= (ImageView) findViewById(R.id.close_button);

    }
}
