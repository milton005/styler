package com.richie.styler.UI.HOME;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.richie.styler.R;
import com.richie.styler.UI.Adapter.GRIDHOMEADAPTER;
import com.richie.styler.UI.Base.BaseActivity;
import com.richie.styler.UI.DETAIL.DetailActivity;
import com.richie.styler.UI.Models.GridHomeModel;
import com.richie.styler.UI.Models.Myfavorites.Favorite;
import com.richie.styler.UI.Models.Myfavorites.ModelMyfavorite;
import com.richie.styler.UI.Models.Myfavorites.Result;
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
 * Created by User on 07-04-2017.
 */

public class FavouriteActivity extends BaseActivity {
    RecyclerView recyclerview;
    ImageView back;
    RecyclerView.LayoutManager mLayoutManager;
    TextView text;
    List<Favorite> newStylers;
    GRIDHOMEADAPTER adpter;
    public ArrayList<GridHomeModel> list1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favouritelayout);
        iniui();
        list1=new ArrayList<>();

        setrecyclerview();
        setadapter();
        getWebService();
        setui();
    }

    private void setui() {
        back= (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        ItemClickSupport.addTo(recyclerview).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                String userid=newStylers.get(position).getUserId();
                String online=newStylers.get(position).getOnlineStatus();
//                String distance=newStylers.get(position).getDistance();
                String userphoto=newStylers.get(position).getUserPhoto();
                Intent intent=new Intent(FavouriteActivity.this, DetailActivity.class);
                intent.putExtra("user_id",userid);
//                intent.putExtra("photo",userphoto);
//                intent.putExtra("online",online);

//                intent.putExtra("distance",distance);
                startActivity(intent);
                finish();
            }
        });
    }

    private void getWebService() {
        showProgress();
        HashMap<String, String> params = new HashMap<>();
        String userid=getPref(Constants.USER_ID);
        params.put("user_id",userid);
        APIService service = ServiceGenerator.createService(APIService.class, FavouriteActivity.this);
        Call<ModelMyfavorite>call=service.getmyfavorite(params);
        call.enqueue(new Callback<ModelMyfavorite>() {
            @Override
            public void onResponse(Call<ModelMyfavorite> call, Response<ModelMyfavorite> response) {
                if (response.isSuccessful()) {
//                    showToast("Success",true);
                    hideProgress();
                    if (response.body() != null && response.body().getResult() != null) {
                        ModelMyfavorite modelMyfavorite=response.body();
                        Result result=modelMyfavorite.getResult();
                        newStylers=result.getFavorites();
                        list1.clear();

                        for (int i=0;i<newStylers.size();i++)
                        {
                            GridHomeModel model=new GridHomeModel();
                            model.setImagename(newStylers.get(i).getUsername());
                            model.setImagepath(newStylers.get(i).getUserPhoto());
                            String onlinestatus=newStylers.get(i).getOnlineStatus();
                            model.setOnline(Integer.valueOf(onlinestatus));
                            list1.add(model);

                        }
                        adpter.notifyDataSetChanged();

                    }
                }
            }

            @Override
            public void onFailure(Call<ModelMyfavorite> call, Throwable t) {

            }
        });
    }


    private void setadapter() {
        adpter = new GRIDHOMEADAPTER(FavouriteActivity.this, list1);
        recyclerview.setAdapter(adpter);
    }

    private void setrecyclerview() {
        recyclerview.setHasFixedSize(true);
        mLayoutManager = new GridLayoutManager(this, 4);
        recyclerview.setLayoutManager(mLayoutManager);
    }

    private void iniui() {
        recyclerview= (RecyclerView) findViewById(R.id.recyclerViewmore);
        back= (ImageView) findViewById(R.id.back);
        Typeface typeface=Typeface.createFromAsset(getAssets(),"fonts/brandon_grotesque_bold.ttf");
        Typeface typeface1=Typeface.createFromAsset(getAssets(),"fonts/BrandonGrotesque-Regular.ttf");
        text= (TextView) findViewById(R.id.text_head_newStylers);
        text.setTypeface(typeface);
    }



}
