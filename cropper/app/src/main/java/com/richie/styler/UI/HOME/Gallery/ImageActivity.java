package com.richie.styler.UI.HOME.Gallery;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.richie.styler.R;
import com.richie.styler.UI.Adapter.ImageAdapter;
import com.richie.styler.UI.Base.BaseActivity;
import com.richie.styler.UI.Models.Gallery.Gallery;
import com.richie.styler.UI.Models.Gallery.ModelGallery;
import com.richie.styler.UI.Models.SupportImages;
import com.richie.styler.UTILS.Constants;
import com.richie.styler.UTILS.ItemClickSupport;
import com.richie.styler.UTILS.Retrofit.APIService;
import com.richie.styler.UTILS.Retrofit.ServiceGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by User on 12-04-2017.
 */

public class ImageActivity extends BaseActivity {
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView recyclerView;
    ImageAdapter adapter;
    List<Gallery>list;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    private ArrayList<SupportImages> images;
    ImageView back;
    TextView nolabel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imageactivity);
        initUi();
        list=new ArrayList<>();
        images=new ArrayList<>();
        setRecyclerView();
        if (isNetworkAvailable())
        {
            getwebservice();
        }
//        int[]imagearray={R.drawable.image1,R.drawable.image2,R.drawable.image3,R.drawable.image4,R.drawable.image6,R.drawable.image7};
//        for (int i=0;i<imagearray.length;i++)
//        {
//            SupportImages supportImages=new SupportImages();
//            supportImages.setImages(imagearray[i]);
//            images.add(supportImages);
//        }
        adapter.notifyDataSetChanged();
        initlistener();

    }

    @Override
    protected void onStop() {
        super.onStop();
        hideProgress();
    }

    private void getwebservice() {
        showProgress();
        HashMap<String, String> params = new HashMap<>();
        final String userid=getPref(Constants.USER_ID);
        params.put("user_id",userid);
        params.put("limit","40");
        APIService service= ServiceGenerator.createService(APIService.class,ImageActivity.this);
        Call<ModelGallery>call=service.getgallery(params);
        call.enqueue(new Callback<ModelGallery>() {
            @Override
            public void onResponse(Call<ModelGallery> call, Response<ModelGallery> response) {
                if (response.isSuccessful())
                {
                    hideProgress();
                    if (response.body()!=null&&response.body().getGallery()!=null)
                    {

                        ModelGallery gallery=response.body();
                       list=gallery.getGallery();
                        if (list.size()>0) {
                            nolabel.setVisibility(View.GONE);
                            for (int i = 0; i < list.size(); i++) {
                                SupportImages image = new SupportImages();
                                image.setImages(list.get(i).getImageUrl());
                                image.setId(list.get(i).getImageId());
                                images.add(image);

                            }
                            adapter.notifyDataSetChanged();
                        }
                        else
                        {
                            adapter.notifyDataSetChanged();
                            showToast("No photos!",false);
                            nolabel.setVisibility(View.VISIBLE);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ModelGallery> call, Throwable t) {
                hideProgress();

            }
        });
    }

    private void initlistener() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                Bundle bundle=new Bundle();
                bundle.putSerializable("images",images);
                bundle.putInt("position",position);


                mFragmentManager = getSupportFragmentManager();
                mFragmentTransaction = mFragmentManager.beginTransaction();
                SlideshowDialogFragment newFragment = SlideshowDialogFragment.newInstance();

                newFragment.setArguments(bundle);
                newFragment.show(mFragmentTransaction,"slideshow");

            }
        });
        ItemClickSupport.addTo(recyclerView).setOnItemLongClickListener(new ItemClickSupport.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClicked(RecyclerView recyclerView, final int position, View v) {
               final String imageid= list.get(position).getImageId();

                String sq="Are you sure you want to delete this image";
                Spanned s = Html.fromHtml(sq);
                new MaterialDialog.Builder(ImageActivity.this)
                        .titleGravity(GravityEnum.CENTER)
                        .contentGravity(GravityEnum.CENTER)
                        .buttonsGravity(GravityEnum.CENTER)
                        .title("DELETE")
                        .titleColor(getResources().getColor(R.color.black))
                        .backgroundColor(getResources().getColor(R.color.white_transparent))
                        .content(s)
                        .positiveText("Delete")
                        .contentColor(getResources().getColor(R.color.black))
                        .positiveColor(Color.RED)
                        .negativeText("Cancel")
                        .negativeColor(Color.BLUE)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                images.remove(position);
                                int imgid=Integer.parseInt(imageid);
                                getwebservice1(imgid);

                            }
                        })
                        .onNegative(null)
                        .show();

                return false;



            }
        });
    }
  public void   getwebservice1(int imageid)

    {
        showProgress();
        final HashMap<String, String> params = new HashMap<>();
        params.put("image_id",String.valueOf(imageid));
        String userid=getPref(Constants.USER_ID);
        params.put("user_id",userid);
        APIService service=ServiceGenerator.createService(APIService.class,ImageActivity.this);
        Call<ResponseBody>call=service.deleteimage(params);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful())
                {
                    hideProgress();
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                hideProgress();
            }
        });

    }
    private void setRecyclerView() {
        adapter=new ImageAdapter(this,images);
        recyclerView.setAdapter(adapter);
    }

    private void initUi() {
        back= (ImageView) findViewById(R.id.back);
        nolabel= (TextView) findViewById(R.id.nolabel);
        recyclerView = (RecyclerView) findViewById(R.id.Recycleview_imageActivity);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new GridLayoutManager(this, 4);
        recyclerView.setLayoutManager(mLayoutManager);
    }
}
