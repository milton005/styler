package com.richie.styler.UI.BottomFragments;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.richie.styler.R;
import com.richie.styler.UI.Adapter.GRIDHOMEADAPTER;
import com.richie.styler.UI.Base.BaseActivity;
import com.richie.styler.UI.DETAIL.DetailActivity;
import com.richie.styler.UI.Models.GridHomeModel;
import com.richie.styler.UI.Models.RecordCheckin.ModelRecordCheckin;
import com.richie.styler.UI.Models.Venues.ModelVenue;
import com.richie.styler.UI.Models.Venues.User;
import com.richie.styler.UI.Models.Venues.Users;
import com.richie.styler.UI.Models.Venues.Venue;
import com.richie.styler.UTILS.CircleTransform;
import com.richie.styler.UTILS.Constants;
import com.richie.styler.UTILS.ItemClickSupport;
import com.richie.styler.UTILS.Retrofit.APIService;
import com.richie.styler.UTILS.Retrofit.ServiceGenerator;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.richie.styler.R.id.textcheckin;

/**
 * Created by User on 23-05-2017.
 */

public class CheckinDetailActivity extends BaseActivity {
    ImageView back;
    LinearLayout checkedinbutton;
    ImageView checkinimage;
    TextView distance,Name;
    TextView checkedinNumber;
    List<Venue>venues;
    List<User>user;
    TextView heading;
    public ArrayList<GridHomeModel> list;
    RecyclerView recyclerView;
    String name="",imagepath="",Scheckednum="",Lat="",Lng="",Country="";
    int Sdistance=0;
    RecyclerView.LayoutManager mLayoutManager;
    GRIDHOMEADAPTER adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkin_detail_layout);
        initui();
        venues=new ArrayList<>();
        user=new ArrayList<>();
        list=new ArrayList<>();

            Bundle Extras=getIntent().getExtras();
        name=Extras.getString("name");
        imagepath=Extras.getString("imagepath");
        Sdistance=Extras.getInt("distance");
        Scheckednum=Extras.getString("checkedinnum");
        Lat=Extras.getString("lat");
        Lng=Extras.getString("lng");
        Country=Extras.getString("country");
        setUi();
        setrecyclerview();
        setadapter();
        getwebservice();
        setclicks();


    }

    private void setclicks() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        checkedinbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getwebservice1();
            }
        });
        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                if (user.size()>0)
                {
                    String myid=getPref(Constants.USER_ID);
                    String userid=user.get(position).getUserId();
                    if (!myid.equals(userid)) {

                        Intent intent = new Intent(CheckinDetailActivity.this, DetailActivity.class);
                        intent.putExtra("user_id", userid);
                        startActivity(intent);
                    }
                }

            }
        });

    }

    private void getwebservice1() {
        final Calendar calender=Calendar.getInstance(TimeZone.getDefault());
        int year=calender.get(Calendar.YEAR);
        int month=calender.get(Calendar.MONTH);
        int day=calender.get(Calendar.DAY_OF_MONTH);
        String date="";
        String monthtemp="";
        String datetemp="";
        if ((month+1)<10)
        {
            monthtemp="0"+String.valueOf(month+1);
        }
        else {
            monthtemp=String.valueOf(month+1);
        }
        if (day<10)
        {
            datetemp="0"+String.valueOf(day);
        }
        else
        {
            datetemp=String.valueOf(day);
        }
        date=String.valueOf(year)+"-"+monthtemp+"-"+datetemp;
        showProgress();
        final HashMap<String, String> params = new HashMap<>();
        String userid=getPref(Constants.USER_ID);
        params.put("user_id",userid);
        params.put("location",name);
        params.put("country",Country);
        params.put("latitude",Lat);
        params.put("longitude",Lng);
        params.put("checkin_date",date);
        APIService service=ServiceGenerator.createService(APIService.class,CheckinDetailActivity.this);
        Call<ModelRecordCheckin>call=service.recordcheckin(params);
        call.enqueue(new Callback<ModelRecordCheckin>() {
            @Override
            public void onResponse(Call<ModelRecordCheckin> call, Response<ModelRecordCheckin> response) {
                if (response.isSuccessful())
                {
                    hideProgress();
                    if (response.body()!=null);
                    {
                        ModelRecordCheckin checkin=response.body();
                        int value=checkin.getResult().getValue();
                        if (value==1)
                        {
                            String msg=checkin.getResult().getMessage();
                            String m="You have successfully checked-in to "+name;
                            showToast(m,true);
                            onBackPressed();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ModelRecordCheckin> call, Throwable t) {
                hideProgress();

            }
        });
    }

    private void setrecyclerview() {
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new GridLayoutManager(CheckinDetailActivity.this, 4);
        recyclerView.setLayoutManager(mLayoutManager);
    }

    private void setadapter() {
        adapter = new GRIDHOMEADAPTER(CheckinDetailActivity.this, list);
        recyclerView.setAdapter(adapter);
    }

    private void getwebservice() {
        showProgress();
        final HashMap<String, String> params = new HashMap<>();
        params.put("nearbylocations",name);
        APIService service= ServiceGenerator.createService(APIService.class,CheckinDetailActivity.this);
        Call<ModelVenue>call=service.getnearby(params);
        call.enqueue(new Callback<ModelVenue>() {
            @Override
            public void onResponse(Call<ModelVenue> call, Response<ModelVenue> response) {
                if (response.isSuccessful())
                {
                    if (response.body()!=null&&response.body().getVenue()!=null)
                    {

                            hideProgress();

                        venues=response.body().getVenue();
                        try {
                            if (venues.size()>0)
                            {
                                Users userlist=venues.get(0).getUsers();
                                user=userlist.getUser();
                                for (int i=0;i<user.size();i++)
                                {
                                    String name=user.get(i).getUsername();
                                    String imagepath=user.get(i).getUserPhoto();
                                    String online=user.get(i).getOnlineStatus();
                                    GridHomeModel model=new GridHomeModel();
                                    model.setImagename(name);
                                    model.setImagepath(imagepath);
                                    model.setOnline(Integer.parseInt(online));
                                    list.add(model);
                                }
                                adapter.notifyDataSetChanged();
                            }


                        }catch (Exception e)
                        {

                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ModelVenue> call, Throwable t) {
            hideProgress();
            }
        });
    }

    private void setUi() {
        Name.setText(name);
        Picasso.with(this).load(imagepath).transform(new CircleTransform()).into(checkinimage);
         int temp=Sdistance;
        double d=temp*.001;
        DecimalFormat numberFormat = new DecimalFormat("#.00");
        String s=String.valueOf(numberFormat.format(d))+" km";
        distance.setText(s);
//        if (temp>1000)
//        {
//            int t=temp/1000;
//            int rem=temp%1000;
//            String s=String.valueOf(t)+"."+String.valueOf(rem)+"km";
//            distance.setText(s);
//        }
//        else
//        {
//
//            String tp=Sdistance+"m";
//            distance.setText(tp);
//        }
        if (Integer.parseInt(Scheckednum)>0) {
            String string = "<b>" + "<font color=\"#76389d\">" + Scheckednum + " Person" + "</font>" + "</b> " + "checked-in";
            checkedinNumber.setText(Html.fromHtml(string));
        }
        else
        {
            String string = "<b>" + "<font color=\"#76389d\">" + "Nobody"  + "</font>" + "</b> " + "checked-in";
            checkedinNumber.setText(Html.fromHtml(string));
        }
    }

    private void initui() {
        Typeface typeface=Typeface.createFromAsset(getAssets(),"fonts/brandon_grotesque_bold.ttf");
        Typeface typeface1=Typeface.createFromAsset(getAssets(),"fonts/BrandonGrotesque-Regular.ttf");
        back= (ImageView) findViewById(R.id.back);
        heading= (TextView) findViewById(R.id.text_head_Settings);
        heading.setTypeface(typeface);
        checkinimage= (ImageView) findViewById(R.id.imagecheckin);
        distance= (TextView) findViewById(R.id.textCheckedinDistance);
        Name= (TextView) findViewById(textcheckin);
        checkedinNumber= (TextView) findViewById(R.id.textCheckedin);
        recyclerView= (RecyclerView) findViewById(R.id.recyclerView);
        checkedinbutton= (LinearLayout) findViewById(R.id.checkinButton);
        Name.setTypeface(typeface);
        checkedinNumber.setTypeface(typeface1);
    }
}
