package com.richie.styler.UI.DETAIL;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.applozic.mobicomkit.uiwidgets.ApplozicSetting;
import com.applozic.mobicomkit.uiwidgets.conversation.ConversationUIService;
import com.applozic.mobicomkit.uiwidgets.conversation.activity.ConversationActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.richie.styler.R;
import com.richie.styler.UI.Base.BaseActivity;
import com.richie.styler.UI.Models.Block.ModelBlocked;


import com.richie.styler.UI.Models.StylerDetail.GalleryImage;
import com.richie.styler.UI.Models.StylerDetail.ModelStylerDetail;
import com.richie.styler.UI.Models.StylerDetail.OtherTribe;
import com.richie.styler.UI.Models.StylerDetail.Tribe;
import com.richie.styler.UI.Models.SupportImages;
import com.richie.styler.UTILS.Constants;
import com.richie.styler.UTILS.OnSwipeTouchListener;
import com.richie.styler.UTILS.Retrofit.APIService;
import com.richie.styler.UTILS.Retrofit.ServiceGenerator;
import com.viewpagerindicator.CirclePageIndicator;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.richie.styler.R.color.white;

/**
 * Created by User on 26-04-2017.
 */

public class DetailActivity extends BaseActivity {
    TextView texthead,textStatus,textusername,textage,textOnlinestatus,textdistance,textdesignerbrand;
    TextView textViewhead,textViewstatus,textViewusername,textViewOnlinestatus,textViewage,textViewdistance,textViewMyStylerTribehead,textViewmystyler;
    TextView textViewIamhead,textViewIam,textViewIlikehead,textViewilike,textViewdesignerhead,textViewdesigner,textViewstylehead,textViewstyle,textViewotherinterests;
    TextView textViewnevergoHead,textViewnevergo,textViewotherinteresthead;
 int value1=0;
    RelativeLayout layoutchat,layoutcamera,layoutloc,layoutfav,layoutliked;
    LinearLayout moreDetails;
    ImageView userpic;
    ImageView imageHeart;
    ImageView imageliked;
    String userid;
    String distance="0.00";
    ImageView Round;
    String Online;
    int favstatus;
    int likestatus=0;
    StringBuilder stringBuilder;
    ImageView back;
    ImageView hideblock;
    String photo;
    String Tusername="",Tage="",Tstylertribe="",Tbrands="",Tiam="",Tilike="",Tnevergo="",Tdistance="",Tsyleicons="",Totherinterests="";
    List<Tribe> tribes;
    List<OtherTribe>othertribes;
    StringBuilder builder;
    TextView textTribe,textStyleicons;
    int TT=0;
    private ViewPager viewPager;
    private MyViewPagerAdapter myViewPagerAdapter;
    private ArrayList<SupportImages> images;
    private CirclePageIndicator circlePageIndicator;
    RelativeLayout frameLayout;
    RelativeLayout swipelayout;
    CoordinatorLayout layout;
    int height1;
    int width1;
    int value=0;
    RelativeLayout layout1;
    ImageView imageuparrow;
    ScrollView nestedScrollView;
    private GestureDetector gestureDetector;
    LinearLayout layouttribes,layoutiam,layoutilike,layoutstyleicons,layoutfavbrands,layoutnevergo,layoutOtherinterests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_layout);
        initui();
        gestureDetector = new GestureDetector( new SwipeDetector() );
        images=new ArrayList<>();
        Setui();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        this.getWindowManager()
                .getDefaultDisplay()
                .getMetrics(displayMetrics);
        height1 = displayMetrics.heightPixels;
        width1 = displayMetrics.widthPixels;
        layout= (CoordinatorLayout) findViewById(R.id.scrolllayout);
        nestedScrollView= (ScrollView) findViewById(R.id.nestedscroll);

        layout1= (RelativeLayout) findViewById(R.id.relativeLayout1);
        frameLayout= (RelativeLayout) findViewById(R.id.swiperef);
        swipelayout= (RelativeLayout) findViewById(R.id.swipelayout);
        FrameLayout.LayoutParams params2=(FrameLayout.LayoutParams)layout1.getLayoutParams();
        params2.height=height1-30;
        layout1.setLayoutParams(params2);
        FrameLayout.LayoutParams params=(FrameLayout.LayoutParams)frameLayout.getLayoutParams();
        params.height=((50+height1+height1/2));
//        showToast(String.valueOf(height1),true);
        frameLayout.setLayoutParams(params);
        RelativeLayout.LayoutParams params1=(RelativeLayout.LayoutParams)swipelayout.getLayoutParams();
        int h=(height1-(height1/4));
        params1.setMargins(0,(h+10),0,0);
        swipelayout.setLayoutParams(params1);
        swipelayout.setOnTouchListener(new OnSwipeTouchListener(DetailActivity.this) {
            public void onSwipeTop() {
                Bundle Extras=new Bundle();
                if (!Tusername.isEmpty()) {
                    Extras.putString("username", Tusername);
                }
                if (!Online.isEmpty())
                {
                    Extras.putString("online",Online);
                }
                if (!Tage.isEmpty())
                {
                    Extras.putString("age",Tage);
                }
                if (!Tdistance.isEmpty())
                {
                    Extras.putString("distance",Tdistance);
                }
                if (!Tbrands.isEmpty())
                {
                    Extras.putString("brands",Tbrands);
                }
                if (Tstylertribe.isEmpty())
                {

                    Extras.putString("tribes",Tstylertribe);
                }
                if (!Tsyleicons.isEmpty())
                {
                    Extras.putString("styleicons",Tsyleicons);
                }
                if (!Tiam.isEmpty())
                {
                    Extras.putString("iam",Tiam);
                }
                if (!Tilike.isEmpty())
                {
                    Extras.putString("ilike",Tilike);
                }
                if (!Tnevergo.isEmpty())
                {
                    Extras.putString("nevergo",Tnevergo);
                }







                Intent intent=new Intent(DetailActivity.this,MoreDetailsActivity.class);
                intent.putExtras(Extras);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_up, R.anim.stay);
            }
            public void onSwipeRight() {
//                Toast.makeText(MyActivity.this, "right", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeLeft() {
//                Toast.makeText(MyActivity.this, "left", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeBottom() {
//                Toast.makeText(MyActivity.this, "bottom", Toast.LENGTH_SHORT).show();
            }

        });


        try {
            Bundle Extras=getIntent().getExtras();
            userid=Extras.getString("user_id");
            Online= Extras.getString("online");
//            distance=Extras.getString("distance");
            photo=Extras.getString("photo");
            double d=Double.parseDouble(distance);
//            DecimalFormat numberFormat = new DecimalFormat("#.00");
//            String s=String.valueOf(numberFormat.format(d))+" km away";
//            Tdistance=s;
            if (Online==null)
            {
               TT=1;
            }
//            textdistance.setText(s);
//            int onlinetemp=Integer.valueOf(Online);
//            if (onlinetemp==1)
//            {
//                textOnlinestatus.setText("ONLINE NOW");
//                textStatus.setText("Online");
//                Round.setColorFilter(ContextCompat.getColor(this,R.color.green));
//            }
//            else {
//                textOnlinestatus.setText("OFFLINE NOW");
//                textStatus.setText("Offline");
//                Round.setColorFilter(ContextCompat.getColor(this,R.color.yellow));
//            }
//            if (!photo.isEmpty())
//            {
//                Picasso.with(this).load(photo).into(userpic);
//            }

        }
        catch (Exception e)
        {

        }
        if (isNetworkAvailable())
        {
            getwebservice();

            myViewPagerAdapter = new MyViewPagerAdapter();
            viewPager.setAdapter(myViewPagerAdapter);
//            circlePageIndicator.setViewPager(viewPager);
        }
        else
        {
            showToast("No internet connection",false);
        }
    }
    @Override
    public boolean dispatchTouchEvent( MotionEvent ev ) {
        // TouchEvent dispatcher.
        if( gestureDetector != null ) {
            if( gestureDetector.onTouchEvent( ev ) )
                // If the gestureDetector handles the event, a swipe has been
                // executed and no more needs to be done.
                return true;
        }
        return super.dispatchTouchEvent( ev );
    }

    @Override
    public boolean onTouchEvent( MotionEvent event ) {
        return gestureDetector.onTouchEvent( event );
    }
    private void getwebservice1(final int value, final String blockedid) {
        final HashMap<String, String> params = new HashMap<>();
        final String userid=getPref(Constants.USER_ID);
        showProgress();
        if (value==0)
        {
            String s=Tusername+" was removed from blocked list";
            showToast(s,true);
        }
        params.put("blocked_by",userid);
        params.put("blocked_user_id",blockedid);
        params.put("block_status",String.valueOf(value));
        APIService service=ServiceGenerator.createService(APIService.class,DetailActivity.this);
        Call<ModelBlocked>call=service.getblocked(params);
        call.enqueue(new Callback<ModelBlocked>() {
            @Override
            public void onResponse(Call<ModelBlocked> call, Response<ModelBlocked> response) {
                if (response.isSuccessful())
                {

                    hideProgress();

                    if (response.body()!=null&&response.body().getResult()!=null)
                    {
                        ModelBlocked blocked=response.body();
                        int value1=blocked.getResult().getValue();
                        String msg=blocked.getResult().getMessage();
//                        showToast(msg,true);
                        if (value1==1)
                        {
//                           showToast(msg,true);
                            if (value==1)
                            {
                                String s=Tusername+" was  blocked successfully";
                                showToast(s,true);
//                                list1.get(pos).setSelected(false);
                            }
                            else
                            {
//                                list1.get(pos).setSelected(true);
                            }
//                            getwebservice();


                        }
                    }


                }
            }

            @Override
            public void onFailure(Call<ModelBlocked> call, Throwable t) {
                hideProgress();
            }
        });

    }
    private class SwipeDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY ) {

            // Check movement along the Y-axis. If it exceeds SWIPE_MAX_OFF_PATH,
            // then dismiss the swipe.
            if( Math.abs( e1.getY() - e2.getY() ) > 120 ){
//                layout1.setAlpha(0);

                return false;
            }
//            if (e1.getY() -e2.getY()>120) {
//                nestedScrollView.fullScroll(View.FOCUS_UP);
//
//            }
//
//            if (e2.getY() - e1.getY()>120) {
//                nestedScrollView.fullScroll(View.FOCUS_DOWN);
//            }
            if( Math.abs( e2.getY() - e1.getY() ) > 120 ){
//                layout1.setAlpha(1);

                return false;
            }
            // Swipe from right to left.
            // The swipe needs to exceed a certain distance (SWIPE_MIN_DISTANCE)
            // and a certain velocity (SWIPE_THRESHOLD_VELOCITY).
            if( e1.getX() - e2.getX() > 5 && Math.abs( velocityX ) > 200 ) {
                if (images.size()>1)
                {
                    int pos=   viewPager.getCurrentItem();
                    if (pos<images.size())
                    {
                        viewPager.setCurrentItem(pos+1);
                    }

                }
                return true;
            }

            // Swipe from left to right.
            // The swipe needs to exceed a certain distance (SWIPE_MIN_DISTANCE)
            // and a certain velocity (SWIPE_THRESHOLD_VELOCITY).
            if( e2.getX() - e1.getX() > 5 && Math.abs( velocityX ) > 200 ) {
                if (images.size()>1)
                {
                    int pos=   viewPager.getCurrentItem();
                    if (pos>0)
                    {
                        viewPager.setCurrentItem(pos-1);
                    }

                }
                return true;
            }

            return false;
        }
    }
    @Override
    protected void onResume() {
        super.onResume();



    }
    private  void getwebservice3(final int value,final String likedid)
    {
        final HashMap<String, String> params = new HashMap<>();
        final String userid=getPref(Constants.USER_ID);
        showProgress();
        if (value==0)
        {
            showToast(Tusername+" was disliked",true);
        }
        params.put("like_by",userid);
        params.put("like_user_id",likedid);
        params.put("like_status",String.valueOf(value));
        APIService service=ServiceGenerator.createService(APIService.class,DetailActivity.this);
        Call<ModelBlocked>call=service.gettogglelikes(params);
        call.enqueue(new Callback<ModelBlocked>() {
            @Override
            public void onResponse(Call<ModelBlocked> call, Response<ModelBlocked> response) {
                if (response.isSuccessful())
                {
                    hideProgress();
                    if (response.body()!=null&&response.body().getResult()!=null)
                    {
                        ModelBlocked blocked=response.body();
                        int value1=blocked.getResult().getValue();
                        String msg=blocked.getResult().getMessage();
//                        showToast(msg,true);
                        if (value1==1)
                        {

                            showToast(Tusername+" was successfully liked",true);
                            if (value==1)
                            {
//                                list1.get(pos).setSelected(false);
                            }
                            else
                            {
//                                list1.get(pos).setSelected(true);
                            }
//                            getwebservice();


                        }
                    }

                }
            }

            @Override
            public void onFailure(Call<ModelBlocked> call, Throwable t) {
                hideProgress();
            }
        });

    }
    private void getwebservice2(final int value, final String blockedid) {
        final HashMap<String, String> params = new HashMap<>();
        final String userid=getPref(Constants.USER_ID);
        showProgress();
        if (value==0)
        {
            showToast(Tusername+" was successfully removed from favorites",true);
        }
        params.put("favorite_by",userid);
        params.put("favorite_user_id",blockedid);
        params.put("favorite_status",String.valueOf(value));
        APIService service=ServiceGenerator.createService(APIService.class,DetailActivity.this);
        Call<ModelBlocked>call=service.gettogglefavorite(params);
        call.enqueue(new Callback<ModelBlocked>() {
            @Override
            public void onResponse(Call<ModelBlocked> call, Response<ModelBlocked> response) {
                if (response.isSuccessful())
                {
                    hideProgress();
                    if (response.body()!=null&&response.body().getResult()!=null)
                    {
                        ModelBlocked blocked=response.body();
                        int value1=blocked.getResult().getValue();
                        String msg=blocked.getResult().getMessage();
//                        showToast(msg,true);
                        if (value1==1)
                        {

                            showToast(Tusername+" was successfully added to favorites",true);
                            if (value==1)
                            {
//                                list1.get(pos).setSelected(false);
                            }
                            else
                            {
//                                list1.get(pos).setSelected(true);
                            }
//                            getwebservice();


                        }
                    }

                }
            }

            @Override
            public void onFailure(Call<ModelBlocked> call, Throwable t) {
                hideProgress();
            }
        });

    }

    private void Setui() {
        imageuparrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nestedScrollView.fullScroll(View.FOCUS_DOWN);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        moreDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle Extras=new Bundle();
                if (!Tusername.isEmpty()) {
                    Extras.putString("username", Tusername);
                }
                if (!Online.isEmpty())
                {
                    Extras.putString("online",Online);
                }
                if (!Tage.isEmpty())
                {
                    Extras.putString("age",Tage);
                }
                if (!Tdistance.isEmpty())
                {
                    Extras.putString("distance",Tdistance);
                }
                if (!Tbrands.isEmpty())
                {
                    Extras.putString("brands",Tbrands);
                }
                if (Tstylertribe.isEmpty())
                {

                    Extras.putString("tribes",Tstylertribe);
                }
                if (!Tsyleicons.isEmpty())
                {
                    Extras.putString("styleicons",Tsyleicons);
                }
                if (!Tiam.isEmpty())
                {
                    Extras.putString("iam",Tiam);
                }
                if (!Tilike.isEmpty())
                {
                    Extras.putString("ilike",Tilike);
                }
                if (!Tnevergo.isEmpty())
                {
                    Extras.putString("nevergo",Tnevergo);
                }





                Intent intent=new Intent(DetailActivity.this,MoreDetailsActivity.class);
                intent.putExtras(Extras);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_up, R.anim.stay);
            }
        });
        layoutchat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (value!=1&&value1!=1) {
                    ApplozicSetting.getInstance(DetailActivity.this).setChatBackgroundColorOrDrawableResource(R.drawable.bg_splash);
                    ApplozicSetting.getInstance(DetailActivity.this).setSentMessageBackgroundColor(white);
                    ApplozicSetting.getInstance(DetailActivity.this).setReceivedMessageBackgroundColor(R.color.sentmessage_black);
                    ApplozicSetting.getInstance(DetailActivity.this).setSentMessageTextColor(R.color.black);
                    ApplozicSetting.getInstance(DetailActivity.this).setReceivedMessageTextColor(white);
                    ApplozicSetting.getInstance(DetailActivity.this).set_isimagebuttonclicked(false);
                    ApplozicSetting.getInstance(DetailActivity.this).set_is_locationbuttonclicked(false);
                    Intent intent = new Intent(DetailActivity.this, ConversationActivity.class);
                    intent.putExtra(ConversationUIService.USER_ID, userid);
                    intent.putExtra(ConversationUIService.DISPLAY_NAME, Tusername);
                    intent.putExtra(ConversationUIService.TAKE_ORDER, true);
                    startActivity(intent);
                }
            }
        });
        layoutliked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (value!=1&&value1!=1) {
                    if (likestatus == 1) {
                        imageliked.setColorFilter(ContextCompat.getColor(DetailActivity.this, R.color.dark_slate_blue));
                        layoutliked.setBackgroundDrawable(getResources().getDrawable(R.drawable.round_circle_greywhite));
                        getwebservice3(0, userid);
                        likestatus = 0;
                    } else {
                        imageliked.setColorFilter(ContextCompat.getColor(DetailActivity.this, white));
                        layoutliked.setBackgroundDrawable(getResources().getDrawable(R.drawable.round_circle_purple));
                        getwebservice3(1, userid);
                        likestatus = 1;
//                    imageliked.setColorFilter(ContextCompat.getColor(DetailActivity.this,R.color.white));
                    }
                }
            }
        });
        layoutfav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (value!=1&&value1!=1) {
                    if (favstatus == 1) {
                        imageHeart.setColorFilter(ContextCompat.getColor(DetailActivity.this, R.color.dark_slate_blue));
                        layoutfav.setBackgroundDrawable(getResources().getDrawable(R.drawable.round_circle_greywhite));
                        getwebservice2(0, userid);
                        favstatus = 0;
                    } else {
                        imageHeart.setColorFilter(ContextCompat.getColor(DetailActivity.this, white));
                        layoutfav.setBackgroundDrawable(getResources().getDrawable(R.drawable.round_circle_purple));
                        getwebservice2(1, userid);
                        favstatus = 1;
                    }
                }

            }
        });
        layoutloc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (value!=1&&value1!=1) {
                    ApplozicSetting.getInstance(DetailActivity.this).setChatBackgroundColorOrDrawableResource(R.drawable.bg_splash);
                    ApplozicSetting.getInstance(DetailActivity.this).setSentMessageBackgroundColor(white);
                    ApplozicSetting.getInstance(DetailActivity.this).setReceivedMessageBackgroundColor(R.color.sentmessage_black);
                    ApplozicSetting.getInstance(DetailActivity.this).setSentMessageTextColor(R.color.black);
                    ApplozicSetting.getInstance(DetailActivity.this).setReceivedMessageTextColor(white);
                    ApplozicSetting.getInstance(DetailActivity.this).set_isimagebuttonclicked(false);
                    ApplozicSetting.getInstance(DetailActivity.this).set_is_locationbuttonclicked(true);
                    Intent intent = new Intent(DetailActivity.this, ConversationActivity.class);
                    intent.putExtra(ConversationUIService.USER_ID, userid);
                    intent.putExtra(ConversationUIService.DISPLAY_NAME, Tusername);
                    intent.putExtra(ConversationUIService.TAKE_ORDER, true);
                    startActivity(intent);
                }
            }
        });
        layoutcamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (value!=1&&value1!=1) {
                    ApplozicSetting.getInstance(DetailActivity.this).setChatBackgroundColorOrDrawableResource(R.drawable.bg_splash);
                    ApplozicSetting.getInstance(DetailActivity.this).setSentMessageBackgroundColor(white);
                    ApplozicSetting.getInstance(DetailActivity.this).setReceivedMessageBackgroundColor(R.color.sentmessage_black);
                    ApplozicSetting.getInstance(DetailActivity.this).setSentMessageTextColor(R.color.black);
                    ApplozicSetting.getInstance(DetailActivity.this).setReceivedMessageTextColor(white);
                    ApplozicSetting.getInstance(DetailActivity.this).set_is_locationbuttonclicked(false);
                    ApplozicSetting.getInstance(DetailActivity.this).set_isimagebuttonclicked(true);
                    Intent intent = new Intent(DetailActivity.this, ConversationActivity.class);
                    intent.putExtra(ConversationUIService.USER_ID, userid);
                    intent.putExtra(ConversationUIService.DISPLAY_NAME, Tusername);
                    intent.putExtra(ConversationUIService.TAKE_ORDER, true);
                    startActivity(intent);
                }
            }
        });
        hideblock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String blockedid=userid;
                if (value==0)
                {
                    value=1;
                    hideblock.setColorFilter(ContextCompat.getColor(DetailActivity.this,R.color.dark_slate_blue));
                }
                else
                {
                    value =0;
                    hideblock.setColorFilter(ContextCompat.getColor(DetailActivity.this,R.color.black));
                }

                getwebservice1(value,blockedid);

            }
        });
    }
    private double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return (dist);
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }
    private void getwebservice() {
        showProgress();
        final HashMap<String, String> params = new HashMap<>();
        final String myid=getPref(Constants.USER_ID);
        params.put("stylerid",userid);
        params.put("myid",myid);
        final APIService service= ServiceGenerator.createService(APIService.class,DetailActivity.this);
        Call<ModelStylerDetail> call=service.getstylerdetail(params);
        call.enqueue(new Callback<ModelStylerDetail>() {
            @Override
            public void onResponse(Call<ModelStylerDetail> call, Response<ModelStylerDetail> response) {
                if (response.isSuccessful())
                {
//                    hideProgress();
                    ModelStylerDetail profile=response.body();

                    String imagePath=profile.getUser().get(0).getUserPhoto();
                    String genders=profile.getUser().get(0).getGender();
                    String fbid=profile.getUser().get(0).getFbId();
                    String dob=profile.getUser().get(0).getDob();
                    String temp=dob.substring(0,dob.indexOf("-"));
                    String firstname=profile.getUser().get(0).getFirstname();
                    String lastname=profile.getUser().get(0).getLastname();
                    String username=profile.getUser().get(0).getUsername();
                    String useremail=profile.getUser().get(0).getUserEmail();
                    String brands=profile.getUser().get(0).getUserbrands();
                    String location=profile.getUser().get(0).getLocation();
                    String male_pref=profile.getUser().get(0).getMalePreference();
                    String female_pref=profile.getUser().get(0).getFemalePreference();
                    String other_interests=profile.getUser().get(0).getOtherInterest();
                    String Otherethnicity=profile.getUser().get(0).getOtherEthinicity();
                    String StyleIcons=profile.getUser().get(0).getUserStylesIcons();
                    String onlineStatus=profile.getUser().get(0).getOnlineStatus();
                    likestatus=profile.getUser().get(0).getLikeStatus();
                    int heblockedme=profile.getUser().get(0).getHeBlockedMeStatus();
                    int iblockedhim=profile.getUser().get(0).getIBlockedHimStatus();
                     favstatus=profile.getUser().get(0).getFavoriteStatus();
                    String LAt=profile.getUser().get(0).getLatitude();
                    String LON=profile.getUser().get(0).getLongitude();
                    value=iblockedhim;
                    value1=heblockedme;
                    if (value==1)
                    {
                        hideblock.setColorFilter(ContextCompat.getColor(DetailActivity.this,R.color.dark_slate_blue));
                    }
                    else
                    {
                        hideblock.setColorFilter(ContextCompat.getColor(DetailActivity.this,R.color.black));
                    }
                    if (value1==1)
                    {
                        layoutcamera.setAlpha(.5f);
                       layoutfav.setAlpha(.5f);
                        layoutchat.setAlpha(.5f);
                        layoutloc.setAlpha(.5f);
                    }
                    if (likestatus==1)
                    {
                        imageliked.setColorFilter(ContextCompat.getColor(DetailActivity.this,white));
                        layoutliked.setBackgroundDrawable(getResources().getDrawable(R.drawable.round_circle_purple));

                    }
                    else
                    {
                        imageliked.setColorFilter(ContextCompat.getColor(DetailActivity.this,R.color.dark_slate_blue));
                        layoutliked.setBackgroundDrawable(getResources().getDrawable(R.drawable.round_circle_greywhite));
                    }
//                    if (TT==1)
//                    {

//                    }

                    if (favstatus==1)
                    {
                        imageHeart.setColorFilter(ContextCompat.getColor(DetailActivity.this, white));
                        layoutfav.setBackgroundDrawable(getResources().getDrawable(R.drawable.round_circle_purple));
                    }
                    else
                    {
                        imageHeart.setColorFilter(ContextCompat.getColor(DetailActivity.this,R.color.dark_slate_blue));
                        layoutfav.setBackgroundDrawable(getResources().getDrawable(R.drawable.round_circle_greywhite));
                    }


                    Tsyleicons=StyleIcons;
                    String photo1=profile.getUser().get(0).getUserPhoto();
                    photo=photo1;
                    String unlike=profile.getUser().get(0).getUserUnliked();
                    Tnevergo=unlike;
                    Tiam=genders;
                    Tbrands=brands;
                    Totherinterests=other_interests;
                    int onlinetemp=Integer.valueOf(onlineStatus);
                    Online=onlineStatus;
                    if (onlinetemp==1)
                    {
                        textOnlinestatus.setText("ONLINE NOW");
                        textStatus.setText("Online");
                        Round.setColorFilter(ContextCompat.getColor(DetailActivity.this,R.color.green));
                    }
                    else {
                        textOnlinestatus.setText("OFFLINE NOW");
                        textStatus.setText("Offline");
                        Round.setColorFilter(ContextCompat.getColor(DetailActivity.this,R.color.yellow));
                    }
                    if (male_pref.equals("1")&&female_pref.equals("1"))
                    {
                        Tilike="Male/female";
                    }
                    if (male_pref.equals("1")&&female_pref.equals("0"))
                    {
                        Tilike="Male";
                    }
                    if (male_pref.equals("0")&&female_pref.equals("1"))
                    {
                        Tilike="Female";
                    }
                    if (male_pref.equals("0")&&female_pref.equals("0"))
                    {
                        Tilike="Male/Female";
                    }
                    Tusername=username;


                    SupportImages imag1=new SupportImages();
                    {
                        imag1.setImages(photo);
                        imag1.setId("");
                        images.add(imag1);
                    }
                    try {
                        double lat1=Double.parseDouble(LAt);
                        double lon1=Double.parseDouble(LON);
                        String tem1=getPref(Constants.LAT);
                        String tem2=getPref(Constants.LONG);
                        double lat2=Double.parseDouble(tem1);
                        double lon2=Double.parseDouble(tem2);
                       double s=  distance(lat1,lon1,lat2,lon2);

//                        double s=distance(10.02150726,76.34357452 ,10.02149105,76.34366608);
                        DecimalFormat numberFormat = new DecimalFormat("#.00");
                        String st=String.valueOf(numberFormat.format(s))+" km away";
                        Tdistance=st;
                        textdistance.setText(st);
                        tribes=profile.getUser().get(0).getTribes();
                        othertribes=profile.getUser().get(0).getOtherTribes();
                        stringBuilder=new StringBuilder();

                        if (tribes!=null)
                        {

                            stringBuilder.append(tribes.get(0).getTribeTitle());


                        }

                        else {
                            if (othertribes!=null)
                            {

                                stringBuilder.append(othertribes.get(0).getTribeTitle());


                            }
                        }
                        String T2=stringBuilder.toString();
                        stringBuilder=new StringBuilder();
                        if (!T2.isEmpty())
                        {
                            String T1=  "My styler tribes: " +"<small>"+T2+"</small>"+"  ";
//                            stringBuilder.append(T1);
                            textTribe.setText(Html.fromHtml(T1));
                        }
                        else
                        {
                            textTribe.setVisibility(View.GONE);
                        }
                        if (!brands.isEmpty())
                        {
                            String T1=  "My designer/brands: " +"<small>"+brands+"</small>"+"  ";
//                            stringBuilder.append(T1);
                            textdesignerbrand.setText(Html.fromHtml(T1));
                        }
                        else
                        {
                            textdesignerbrand.setVisibility(View.GONE);
                        }
                        if (!StyleIcons.isEmpty())
                        {
                            String T1= "My style icons: " +"<small>"+StyleIcons+"</small>";
//                            stringBuilder.append(T1);
                            textStyleicons.setText(Html.fromHtml(T1));
                        }
                        else
                        {
                            textStyleicons.setVisibility(View.GONE);
                        }
                        String T3=stringBuilder.toString();
//                        textdesignerbrand.setText(Html.fromHtml(T3));
                        builder=new StringBuilder();

                        if (tribes!=null)
                        {
                            for (int i=0;i<tribes.size();i++)
                            {
                                builder.append(tribes.get(i).getTribeTitle());
                                builder.append(", ");

                            }


                        }

                        if (othertribes!=null)
                        {
                            for (int i=0;i<othertribes.size();i++)
                            {
                                builder.append(othertribes.get(i).getTribeTitle());
                                builder.append(", ");

                            }
                        }
                        String string=builder.toString();
                        if (!string.isEmpty()) {
                            Tstylertribe = string.substring(0, string.lastIndexOf(","));
                            textViewmystyler.setText(Tstylertribe);
                        }
                        else
                        {
                            layouttribes.setVisibility(View.GONE);
                        }
                        List<GalleryImage> list=profile.getUser().get(0).getGalleryImages();
                        if (list.size()>0) {
//                            nolabel.setVisibility(View.GONE);
                            for (int i = 0; i < list.size(); i++) {
                                SupportImages image = new SupportImages();
                                image.setImages(list.get(i).getImageUrl());
                                image.setId(list.get(i).getImageId());
                                images.add(image);

                            }

//                            adapter.notifyDataSetChanged();
                        }
                        else
                        {
//                            circlePageIndicator.setVisibility(View.GONE);
//                            adapter.notifyDataSetChanged();
//                            showToast("No photos!",false);
//                            nolabel.setVisibility(View.VISIBLE);
                        }
                    }catch (Exception e)
                    {

                    }
                    myViewPagerAdapter = new MyViewPagerAdapter();
                    viewPager.setAdapter(myViewPagerAdapter);
                    if (images.size()==1)
                    {
                        circlePageIndicator.setVisibility(View.GONE);
                    }
                    else
                    {
                        circlePageIndicator.setViewPager(viewPager);
                    }



//                    if (!imagePath.isEmpty())
//                    {
//                        Picasso.with(DetailActivity.this).load(imagePath).into(userpic);
//
//                    }
                    if (!username.isEmpty())
                    {
                        texthead.setText(username);
                        textusername.setText(username);
                    }
                    int yeartemp=Integer.valueOf(temp);
                    final Calendar calender = Calendar.getInstance(TimeZone.getDefault());
                    final int Year = calender.get(Calendar.YEAR);
                    int agetemp=Year-yeartemp;
                    String age=agetemp+" Years Old";
                    Tage=age;
                    textage.setText(age);
                    if (Totherinterests.isEmpty())
                    {
                        layoutOtherinterests.setVisibility(View.GONE);
                    }
                    else
                    {
                        textViewotherinterests.setText(Html.fromHtml(Totherinterests));
                    }
                    if (Tbrands.isEmpty())
                    {
                        layoutfavbrands.setVisibility(View.GONE);
                    }
                    else
                    {
                        textViewdesigner.setText(Tbrands);
                    }
                    if (Tnevergo.isEmpty())
                    {
                        layoutnevergo.setVisibility(View.GONE);
                    }
                    else
                    {
                        textViewnevergo.setText(Tnevergo);
                    }
                    if (Tilike.isEmpty())
                    {
                        layoutilike.setVisibility(View.GONE);
                    }
                    else {
                        textViewilike.setText(Tilike);
                    }
                    if (Tsyleicons.isEmpty())
                    {
                        layoutstyleicons.setVisibility(View.GONE);
                    }
                    else
                    {
                        textViewstyle.setText(Tsyleicons);
                    }
//                    stringBuilder.append(brands);
                    if (Tiam.equals("m"))
                    {
                        textViewIam.setText("male");
                    }
                    else
                    {
                        textViewIam.setText("male");
                    }
//                    nestedScrollView.setSmoothScrollingEnabled(true);
//                   nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
//                        @Override
//                        public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//                          if (scrollY>oldScrollY)
//                        {
//                           layout1.setAlpha(scrollY/oldScrollY);
//
//                         }
//                         if (scrollY<oldScrollY)
//                         {
//
//                             layout1.setAlpha(oldScrollY/scrollY);
//                        }
//                       }
//                   });


//
////                Toast.makeText(MyActivity.this, "right", Toast.LENGTH_SHORT).show();
//                        }
//                        public void onSwipeLeft() {
//                            if (images.size()>1)
//                            {
//                                int pos=   viewPager.getCurrentItem();
//                                if (pos<images.size())
//                                {
//                                    viewPager.setCurrentItem(pos+1);
//                                }
//
//                            }
//
////                Toast.makeText(MyActivity.this, "left", Toast.LENGTH_SHORT).show();
//                        }
//
//                    });


                    nestedScrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
                        @Override
                        public void onScrollChanged() {
                             int scrollY = nestedScrollView.getScrollY();
                            int h=  nestedScrollView.getHeight();
                            float s=(float)h+100;

                         final int boundY = 300;
//                            if(scrollY!=0) {
                                 float ratio = (float) (scrollY/ (788));
//                            showSnackBar(String.valueOf(h),false);
                            float scale = ( scrollY / s ) ;
                                layout1.setAlpha(1-scale);
//                            }
                        }
                    });


                }
            }

            @Override
            public void onFailure(Call<ModelStylerDetail> call, Throwable t) {
            hideProgress();
            }
        });
    }


    private void initui() {
        Typeface typeface=Typeface.createFromAsset(getAssets(),"fonts/brandon_grotesque_bold.ttf");
        Typeface typeface1=Typeface.createFromAsset(getAssets(),"fonts/BrandonGrotesque-Regular.ttf");
        Round= (ImageView) findViewById(R.id.imageround);
        imageuparrow= (ImageView) findViewById(R.id.imageuparrow);
        texthead= (TextView) findViewById(R.id.text_head_userdeatil);
        texthead.setTypeface(typeface);
        textStatus= (TextView) findViewById(R.id.textuserdetail_onlinestatus);
        textusername= (TextView) findViewById(R.id.textUsername);
        textusername.setTypeface(typeface);
        textage= (TextView) findViewById(R.id.textuseryear);
        textage.setTypeface(typeface1);
        textOnlinestatus= (TextView) findViewById(R.id.textOnlineStatus);
        textOnlinestatus.setTypeface(typeface1);
        textdistance= (TextView) findViewById(R.id.textdistance);
        textdistance.setTypeface(typeface1);
        textdesignerbrand= (TextView) findViewById(R.id.textTribe_brand);
        textdesignerbrand.setTypeface(typeface1);
        layoutchat= (RelativeLayout) findViewById(R.id.layoutchat);
        hideblock= (ImageView) findViewById(R.id.imagehide_block);
        imageHeart= (ImageView) findViewById(R.id.imageHeart);
        imageliked= (ImageView) findViewById(R.id.imageliked);
        textTribe= (TextView) findViewById(R.id.textTribe_tribe);
        textTribe.setTypeface(typeface1);
        textStyleicons= (TextView) findViewById(R.id.textTribe_icons);
        textStyleicons.setTypeface(typeface1);
        textViewMyStylerTribehead= (TextView) findViewById(R.id.textMystylerTribehead);
        textViewMyStylerTribehead.setTypeface(typeface1);
        textViewmystyler= (TextView) findViewById(R.id.textMystylerTribe);
        textViewmystyler.setTypeface(typeface1);
        textViewIamhead= (TextView) findViewById(R.id.textIamhead);
        textViewIamhead.setTypeface(typeface1);
        textViewIam= (TextView) findViewById(R.id.textIam);
        textViewIam.setTypeface(typeface1);
        textViewIlikehead= (TextView) findViewById(R.id.textIlikehead);
        textViewIlikehead.setTypeface(typeface1);
        textViewilike= (TextView) findViewById(R.id.textIlike);
        textViewilike.setTypeface(typeface1);
        textViewdesignerhead= (TextView) findViewById(R.id.textfavhead);
        textViewdesignerhead.setTypeface(typeface1);
        textViewdesigner= (TextView) findViewById(R.id.textfav);
        textViewdesigner.setTypeface(typeface1);
        textViewstylehead= (TextView) findViewById(R.id.textstyleiconhead);
        textViewstylehead.setTypeface(typeface1);
        textViewstyle= (TextView) findViewById(R.id.textstyleicon);
        textViewstyle.setTypeface(typeface1);
        textViewnevergoHead= (TextView) findViewById(R.id.textnevergohead);
        textViewnevergoHead.setTypeface(typeface1);
        textViewnevergo= (TextView) findViewById(R.id.textnevergo);
        textViewnevergo.setTypeface(typeface1);
        textViewotherinterests= (TextView) findViewById(R.id.textotherinterests);
        textViewotherinteresthead= (TextView) findViewById(R.id.textotherinterestshead);
        textViewotherinteresthead.setTypeface(typeface1);
        textViewotherinterests.setTypeface(typeface1);
        layoutcamera= (RelativeLayout) findViewById(R.id.layoutcamera);
        layoutfav= (RelativeLayout) findViewById(R.id.layoutfav);
        layoutloc= (RelativeLayout) findViewById(R.id.layoutlocation);
        layoutliked= (RelativeLayout) findViewById(R.id.layoutliked);
        back= (ImageView) findViewById(R.id.back);
        viewPager= (ViewPager) findViewById(R.id.viewpager);
        circlePageIndicator = (CirclePageIndicator) findViewById(R.id.circlepager);
        moreDetails= (LinearLayout) findViewById(R.id.layoutMoredetails);
        userpic= (ImageView) findViewById(R.id.userPic);
        layoutfavbrands= (LinearLayout) findViewById(R.id.layout_fav);
        layoutiam= (LinearLayout) findViewById(R.id.layout_iam);
        layoutilike= (LinearLayout) findViewById(R.id.layout_ilike);
        layoutstyleicons= (LinearLayout) findViewById(R.id.layout_styleicons);
        layouttribes= (LinearLayout) findViewById(R.id.layout_stylertribes);
        layoutnevergo= (LinearLayout) findViewById(R.id.layout_nevergo);
        layoutOtherinterests= (LinearLayout) findViewById(R.id.layout_otherinterests);
    }
    public class MyViewPagerAdapter extends PagerAdapter {

        private LayoutInflater layoutInflater;

        public MyViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.sliding_images_layout2, container, false);

            ImageView imageViewPreview = (ImageView) view.findViewById(R.id.imageview_gallery);

            Glide.with(DetailActivity.this).load(images.get(position).getImages()).placeholder(R.drawable.user_default).listener(new RequestListener<String, GlideDrawable>() {
                @Override
                public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                    return false;
                }

                @Override
                public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                    hideProgress();
                    return false;
                }
            })

                    .into(imageViewPreview);

//            Glide.with(getActivity()).load(image.getLarge())
//
//                    .into(imageViewPreview);

            container.addView(view);

            return view;
        }

        @Override
        public int getCount() {
            if (images.size()>5)
            {
                return 5;
            }
            else {
                return images.size();
            }
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((View) object);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
