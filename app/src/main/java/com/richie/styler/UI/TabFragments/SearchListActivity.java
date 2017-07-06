package com.richie.styler.UI.TabFragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.richie.styler.R;
import com.richie.styler.UI.Adapter.GRIDHOMEADAPTER;
import com.richie.styler.UI.Base.BaseActivity;
import com.richie.styler.UI.DETAIL.DetailActivity;
import com.richie.styler.UI.Models.GridHomeModel;
import com.richie.styler.UI.Models.StylerSearch.ModelStylerSearch;
import com.richie.styler.UI.Models.StylerSearch.Result;
import com.richie.styler.UI.Models.StylerSearch.Styler;
import com.richie.styler.UTILS.Constants;
import com.richie.styler.UTILS.ItemClickSupport;
import com.richie.styler.UTILS.Retrofit.APIService;
import com.richie.styler.UTILS.Retrofit.ServiceGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by User on 06-04-2017.
 */

public class SearchListActivity extends BaseActivity{
    RecyclerView recyclerview;
    ImageView back;
    RecyclerView.LayoutManager mLayoutManager;
    TextView text;
    GRIDHOMEADAPTER adpter;
    public ArrayList<GridHomeModel> list1;
    String TRIBE_ID="";
    String AgeFrom="",AgeTo="";
    String Profile_name="";
    String Long="",Lat="";
    String Brandname="";
    List<Styler>list;
    RelativeLayout noprofiles;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.more_stylers_online);
        iniui();
        list1=new ArrayList<>();
        try{
            Bundle Extras=getIntent().getExtras();
            TRIBE_ID=Extras.getString("TRIBE_ID");
            AgeFrom=Extras.getString("AGE_FROM");
            AgeTo=Extras.getString("AGE_TO");
            Profile_name=Extras.getString("PROFILE_NAME");
            Lat=Extras.getString("LAT");
            Long=Extras.getString("LONG");
            Brandname=Extras.getString("BRAND");
            if(TRIBE_ID.isEmpty()&&AgeFrom.isEmpty()&&AgeTo.isEmpty()&&Profile_name.isEmpty()&&Lat.isEmpty()&&Long.isEmpty()&&Brandname.isEmpty())
            {
                text.setText("By search result");
            }
            else if (TRIBE_ID.isEmpty()&&(!AgeFrom.isEmpty())&&(!AgeTo.isEmpty())&&Profile_name.isEmpty()&&Lat.isEmpty()&&Long.isEmpty()&&Brandname.isEmpty())
            {
                text.setText("By age result");
            }
            else if (TRIBE_ID.isEmpty()&&AgeFrom.isEmpty()&&AgeTo.isEmpty()&&(!Profile_name.isEmpty())&&Lat.isEmpty()&&Long.isEmpty()&&Brandname.isEmpty())
            {
                text.setText("By profile name result");
            }
            else if (TRIBE_ID.isEmpty()&&AgeFrom.isEmpty()&&AgeTo.isEmpty()&&Profile_name.isEmpty()&&(!Lat.isEmpty())&&(!Long.isEmpty())&&Brandname.isEmpty())
            {
                text.setText("By location result");
            }
            else if (TRIBE_ID.isEmpty()&&AgeFrom.isEmpty()&&AgeTo.isEmpty()&&Profile_name.isEmpty()&&Lat.isEmpty()&&Long.isEmpty()&&(!Brandname.isEmpty()))
            {
                text.setText("By brands/designer result");
            }
            else
            {
                text.setText("By search result");
            }

        }catch (Exception e)
        {

        }
        setrecyclerview();
        setadapter();
        getWebService();
        setui();
    }

    private void setui() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        ItemClickSupport.addTo(recyclerview).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                String userid=list.get(position).getUserId();
                Intent intent=new Intent(SearchListActivity.this, DetailActivity.class);
                intent.putExtra("user_id",userid);
                startActivity(intent);
            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent returnIntent = new Intent();
        returnIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        setResult(Activity.RESULT_OK, returnIntent);


        super.onBackPressed();
        overridePendingTransition(0,0);
    }
    private void getWebService() {
        showProgress();
        HashMap<String, String> params = new HashMap<>();
        String userid=getPref(Constants.USER_ID);
        params.put("userid",userid);
        params.put("limit","40");
        if (!TRIBE_ID.isEmpty())
        {
          params.put("tribeId",TRIBE_ID);
        }
        if (!Profile_name.isEmpty())
        {
            params.put("profilename",Profile_name);
        }
        if (!Lat.isEmpty())
        {
            params.put("myLat",Lat);
        }
        if (!Long.isEmpty())
        {
            params.put("myLon",Long);
        }
        if (!AgeFrom.isEmpty())
        {
            params.put("agefrom",AgeFrom);
        }
        if (!AgeTo.isEmpty())
        {
            params.put("ageto",AgeTo);

        }
        if (!Brandname.isEmpty())
        {
            params.put("brand",Brandname);
        }
        APIService service = ServiceGenerator.createService(APIService.class, SearchListActivity.this);
        Call<ModelStylerSearch>call=service.getsearch(params);
        call.enqueue(new Callback<ModelStylerSearch>() {
            @Override
            public void onResponse(Call<ModelStylerSearch> call, Response<ModelStylerSearch> response) {
                if (response.isSuccessful())
                {
                    hideProgress();
                    if (response.body()!=null&&response.body().getResult()!=null)
                    {
                        ModelStylerSearch model=response.body();
                        Result result=model.getResult();
                        list1.clear();
                     list=result.getStylers();

                        if (list.size()>0) {
                            for (int i = 0; i < list.size(); i++) {
                                GridHomeModel m = new GridHomeModel();
                                m.setImagename(list.get(i).getUsername());
                                m.setImagepath(list.get(i).getUserPhoto());
                                String online=list.get(i).getOnlineStatus();
                                m.setOnline(Integer.valueOf(online));
                                list1.add(m);
                            }
                            adpter.notifyDataSetChanged();
                        }else
                        {
                            showToast("No Result Found!",false);
                            noprofiles.setVisibility(View.VISIBLE);
                        }

                    }
                }
                else {
                    hideProgress();
                }
            }

            @Override
            public void onFailure(Call<ModelStylerSearch> call, Throwable t) {
                hideProgress();

            }
        });
    }

    private void setadapter() {
        adpter = new GRIDHOMEADAPTER(SearchListActivity.this, list1);
        recyclerview.setAdapter(adpter);
    }
    private void iniui() {
        noprofiles= (RelativeLayout) findViewById(R.id.nostylerslayout);
        recyclerview= (RecyclerView) findViewById(R.id.recyclerViewmore);
        back= (ImageView) findViewById(R.id.back);
        text= (TextView) findViewById(R.id.text_head_newStylers);
    }
    private void setrecyclerview() {
        recyclerview.setHasFixedSize(true);
        mLayoutManager = new GridLayoutManager(this, 4);
        recyclerview.setLayoutManager(mLayoutManager);
    }
}
