package com.richie.styler.UI.BottomFragments;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.applozic.mobicomkit.api.conversation.Message;
import com.applozic.mobicomkit.api.conversation.MobiComConversationService;
import com.bumptech.glide.Glide;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
//import com.lorentzos.flingswipe.SwipeFlingAdapterView;


import com.richie.styler.R;
import com.richie.styler.UI.Base.BaseFragment;
import com.richie.styler.UI.Models.Data;

import com.richie.styler.UI.Models.Match.ModelMatch;
import com.richie.styler.UI.Models.Match.OtherTribe;
import com.richie.styler.UI.Models.Match.Styler;
import com.richie.styler.UI.Models.Match.Tribe;
import com.richie.styler.UI.Models.Matchlistitem;
import com.richie.styler.UTILS.Constants;
import com.richie.styler.UTILS.Retrofit.APIService;
import com.richie.styler.UTILS.Retrofit.ServiceGenerator;
import com.mindorks.placeholderview.SwipeDecor;
import com.mindorks.placeholderview.SwipePlaceHolderView;
import com.mindorks.placeholderview.annotations.Click;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.swipe.SwipeCancelState;
import com.mindorks.placeholderview.annotations.swipe.SwipeIn;
import com.mindorks.placeholderview.annotations.swipe.SwipeInState;
import com.mindorks.placeholderview.annotations.swipe.SwipeOut;
import com.mindorks.placeholderview.annotations.swipe.SwipeOutState;
import com.skyfishjy.library.RippleBackground;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by User on 22-05-2017.
 */

public class Matchfragment extends BaseFragment implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener ,LocationListener
        {   Location mCurrentLocation;
    String Lat="",Long="";
            private SwipePlaceHolderView mSwipeView;
            private Context mContext;
    private static final long FASTEST_INTERVAL1 = 1000 * 5;
    protected static final long UPDATE_INTERVAL = 5;
    private static final int LOCATION_SERVICE_ENABLE =1001 ;
    GoogleApiClient mGoogleApiClient;
            int pos=0;
            int height1;
            int width1;
    public static MyAppAdapter myAppAdapter;
    public static ViewHolder viewHolder;
    private ArrayList<Data> array;
    StringBuilder builder;
    private ArrayList<Matchlistitem>list;
            TextView nostylers;
    private LocationRequest locationRequest;
//    private SwipeFlingAdapterView flingContainer;
    RippleBackground rippleBackground;
            CircleImageView imageViewcenter;
            @Override
            public void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                if (mGoogleApiClient == null) {
                    mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                            .addConnectionCallbacks(this)
                            .addOnConnectionFailedListener(this)
                            .addApi(LocationServices.API)
                            .build();
                    processLocation();
                }

            }
            public void processLocation() {
                //  if (Utils.hasMarshmallow()) {
                //  new ApplozicPermissions(MobicomLocationActivity.this, layout).checkRuntimePermissionForLocation();
                // } else {
                processingLocation();
                // }
            }
            public void processingLocation() {
                if (!((LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE))
                        .isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle(com.applozic.mobicomkit.uiwidgets.R.string.location_services_disabled_title)
                            .setMessage(com.applozic.mobicomkit.uiwidgets.R.string.location_services_disabled_message)
                            .setCancelable(false)
                            .setPositiveButton(com.applozic.mobicomkit.uiwidgets.R.string.location_service_settings, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                    startActivityForResult(intent, LOCATION_SERVICE_ENABLE);
                                }
                            })
                            .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                    Toast.makeText(getContext(), com.applozic.mobicomkit.uiwidgets.R.string.location_sending_cancelled, Toast.LENGTH_LONG).show();
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                } else {
                    mGoogleApiClient.disconnect();
                    mGoogleApiClient.connect();
                }
            }
            public static Point getDisplaySize(WindowManager windowManager) {
                try {
                    if (Build.VERSION.SDK_INT > 16) {
                        Display display = windowManager.getDefaultDisplay();
                        DisplayMetrics displayMetrics = new DisplayMetrics();
                        display.getMetrics(displayMetrics);
                        return new Point(displayMetrics.widthPixels, displayMetrics.heightPixels);
                    } else {
                        return new Point(0, 0);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return new Point(0, 0);
                }
            }
            public static int dpToPx(int dp) {
                return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
            }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.matchlayout, null);
        hideProgress();
        initui(v);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager()
                .getDefaultDisplay()
                .getMetrics(displayMetrics);
        height1 = displayMetrics.heightPixels;
        width1 = displayMetrics.widthPixels;
        list=new ArrayList<>();
        array = new ArrayList<>();
        int h=getStatusBarHeight();
//        showToast(String.valueOf(h),true);
        Point windowSize = getDisplaySize(getActivity().getWindowManager());
        int bottomMargin = dpToPx(50);
        mSwipeView.getBuilder()
                .setDisplayViewCount(3)
                .setHeightSwipeDistFactor(10)
                .setWidthSwipeDistFactor(5)
                .setSwipeDecor(new SwipeDecor()
                        .setViewWidth(windowSize.x)
                        .setViewHeight(windowSize.y-bottomMargin-h)
                        .setViewGravity(Gravity.TOP)
                        .setPaddingTop(20)
                        .setRelativeScale(0.01f)
                        .setSwipeInMsgLayoutId(R.layout.tinder_swipe_in_msg_view)
                        .setSwipeOutMsgLayoutId(R.layout.tinder_swipe_out_msg_view));
//        myAppAdapter = new MyAppAdapter(list,getActivity());
//        flingContainer.setAdapter(myAppAdapter);
        setui();
        return v;

    }
            public int getStatusBarHeight() {
                int result = 0;
                int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
                if (resourceId > 0) {
                    result = getResources().getDimensionPixelSize(resourceId);
                }
                return result;
            }
            @Layout(R.layout.item)
            public class TinderCard {


                @com.mindorks.placeholderview.annotations.Position
                int position;
                @com.mindorks.placeholderview.annotations.View(R.id.cardImage)
                private ImageView profileImageView;
                @com.mindorks.placeholderview.annotations.View(R.id.imageliked)
                private ImageView imageliked;
                @com.mindorks.placeholderview.annotations.View(R.id.downarrow)
                private ImageView downarrow;
                @com.mindorks.placeholderview.annotations.View(R.id.like_dislikelayout)
                private LinearLayout layout;
                @com.mindorks.placeholderview.annotations.View(R.id.imageround)
                private ImageView imageround;
                @com.mindorks.placeholderview.annotations.View(R.id.imagedislike)
                private ImageView imagedislike;
                @com.mindorks.placeholderview.annotations.View(R.id.textUsername)
                private TextView textusername;
                @com.mindorks.placeholderview.annotations.View(R.id.textuseryear)
                private TextView textuseryear;
                @com.mindorks.placeholderview.annotations.View(R.id.imageuparrow)
                private ImageView uparrow;
                @com.mindorks.placeholderview.annotations.View(R.id.swipelayout)
                private NestedScrollView swipelayout;
                @com.mindorks.placeholderview.annotations.View(R.id.textOnlineStatus)
                private TextView textonlinestatus;
                @com.mindorks.placeholderview.annotations.View(R.id.textdistance)
                private TextView textdistsnce;
                @com.mindorks.placeholderview.annotations.View(R.id.textIamhead)
                private TextView textViewIamhead;
                @com.mindorks.placeholderview.annotations.View(R.id.textIam)
                private TextView textViewIam;
                @com.mindorks.placeholderview.annotations.View(R.id.textIlikehead)
                private TextView textViewIlikehead;
                @com.mindorks.placeholderview.annotations.View(R.id.textIlike)
                private TextView textViewIlike;
                @com.mindorks.placeholderview.annotations.View(R.id.textfavhead)
                private TextView textViewfavhead;
                @com.mindorks.placeholderview.annotations.View(R.id.textfav)
                private TextView textViewfav;
                @com.mindorks.placeholderview.annotations.View(R.id.textstyleiconhead)
                private TextView textViewstyleiconhead;
                @com.mindorks.placeholderview.annotations.View(R.id.textstyleicon)
                private TextView textViewstyleicon;
                @com.mindorks.placeholderview.annotations.View(R.id.textnevergohead)
                private TextView textViewnevergohead;
                @com.mindorks.placeholderview.annotations.View(R.id.textnevergo)
                private TextView textViewnevergo;
                @com.mindorks.placeholderview.annotations.View(R.id.textotherinterestshead)
                private TextView textViewotherinterestshead;
                @com.mindorks.placeholderview.annotations.View(R.id.textotherinterests)
                private TextView textViewotherinterests;
                @com.mindorks.placeholderview.annotations.View(R.id.textMystylerTribehead)
                private TextView textViewmyStylertribehead;
                @com.mindorks.placeholderview.annotations.View(R.id.textMystylerTribe)
                private TextView textViewmyStylertribe;
//                @com.mindorks.placeholderview.annotations.View(R.id.myStylerbrands)
//                private TextView mystylerbrands;
//                @com.mindorks.placeholderview.annotations.View(R.id.myStylericons)
//                private TextView mystylericons;
                private Matchlistitem mProfile;
                private Context mContext;
                private SwipePlaceHolderView mSwipeView;

                public TinderCard(Context context, Matchlistitem profile, SwipePlaceHolderView swipeView) {
                    mContext = context;
                    mProfile = profile;
                    mSwipeView = swipeView;
                }

                @Resolve
                private void onResolved(){
                    RelativeLayout.LayoutParams params1 = (RelativeLayout.LayoutParams) swipelayout
                            .getLayoutParams();
                    int h=(height1-(height1/4));
                    params1.setMargins(0,(h+10),0,0);
                    swipelayout.setLayoutParams(params1);
                    Typeface typeface=Typeface.createFromAsset(getActivity().getAssets(),"fonts/brandon_grotesque_bold.ttf");
                    Typeface typeface1=Typeface.createFromAsset(getActivity().getAssets(),"fonts/BrandonGrotesque-Regular.ttf");
//                    Toast.makeText(getContext(),String.valueOf(position),Toast.LENGTH_LONG).show();
                    textusername.setTypeface(typeface1);
                    textuseryear.setTypeface(typeface1);
                    textonlinestatus.setTypeface(typeface1);
                    textdistsnce.setTypeface(typeface1);
                    textViewIam.setTypeface(typeface1);
                    textViewIamhead.setTypeface(typeface1);
                    textViewIlike.setTypeface(typeface1);
                    textViewIlikehead.setTypeface(typeface1);
                    textViewstyleicon.setTypeface(typeface1);
                    textViewstyleiconhead.setTypeface(typeface1);
                    textViewmyStylertribe.setTypeface(typeface1);
                    textViewmyStylertribehead.setTypeface(typeface1);
                    textViewfav.setTypeface(typeface1);
                    textViewfavhead.setTypeface(typeface1);
                    textViewnevergo.setTypeface(typeface1);
                    textViewnevergohead.setTypeface(typeface1);
                    textViewotherinterests.setTypeface(typeface1);
                    textViewotherinterestshead.setTypeface(typeface1);
                    Glide.with(mContext).load(mProfile.getImagepath())
//                            .bitmapTransform(new RoundedCornersTransformation(mContext, Utils.dpToPx(7), 0,
//                                    RoundedCornersTransformation.CornerType.TOP))
                            .into(profileImageView);
                    int onlinetemp=Integer.valueOf(mProfile.getOnlinestatus());
//            Online=onlineStatus;
                    if (onlinetemp==1)
                    {
                        textonlinestatus.setText("ONLINE NOW");
//                textStatus.setText("Online");
                        imageround.setColorFilter(ContextCompat.getColor(getContext(),R.color.green));
                    }
                    else {
                        textonlinestatus.setText("OFFLINE NOW");
//                textStatus.setText("Online");
                        imageround.setColorFilter(ContextCompat.getColor(getContext(),R.color.yellow));
                    }
//            Uri imageUri = Uri.parse(parkingList.get(position).getImagepath());
//            viewHolder.cardImage.setImageURI(imageUri);
//                    Glide.with(getContext()).load(parkingList.get(position).getImagepath()).into(viewHolder.cardImage);
                    textusername.setText(mProfile.getUsername());
                    textuseryear.setText(mProfile.getAge());
                    textdistsnce.setText(mProfile.getDistance());
                    textViewfav.setText(mProfile.getBrands());
                    textViewmyStylertribe.setText(mProfile.getTribes());
                    textViewotherinterests.setText(mProfile.getOtherinterests());
                    textViewstyleicon.setText(mProfile.getStylicons());

                    if (mProfile.getIam().equals("m"))
                    {
                        textViewIam.setText("male");
                    }
                    else
                    {
                        textViewIam.setText("female");
                    }
                    textViewIlike.setText(mProfile.getILIke());
                    textViewnevergo.setText(mProfile.getNevergo());

//                    mystylertribes.setText(Html.fromHtml(mProfile.getTribes()));
//                    mystylericons.setText(Html.fromHtml(mProfile.getStylicons()));
//                    mystylerbrands.setText(Html.fromHtml(mProfile.getBrands()));
//                    nameAgeTxt.setText(mProfile.getName() + ", " + mProfile.getAge());
//                    locationNameTxt.setText(mProfile.getLocation());
                }

                @Click(R.id.imageliked)
                private void onClick(){
                    Log.d("EVENT", "profileImageView click");


                      mSwipeView.doSwipe(true);
                }
                @Click(R.id.downarrow)
                private void downArrowclick()
                {Log.d("EVENT", "down arrow click");
                    Handler handler=new Handler();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            RelativeLayout.LayoutParams params1 = (RelativeLayout.LayoutParams) swipelayout
                                    .getLayoutParams();
                            int h=(height1-(height1/4));
                            params1.setMargins(0,(h+10),0,0);
//                            params1.setMargins(0,0,0,60);
                            swipelayout.setLayoutParams(params1);
                            profileImageView.setAlpha(1.0f);
                            layout.setVisibility(View.GONE);
                            uparrow.setVisibility(View.VISIBLE);

                        }
                    });
                }
                @Click(R.id.imageuparrow)
                private void onclikeduparrow()
                {  Log.d("EVENT", "up arrow click");
                    Handler handler=new Handler();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            RelativeLayout.LayoutParams params1 = (RelativeLayout.LayoutParams) swipelayout
                                    .getLayoutParams();

                            params1.setMargins(0,0,0,60);
                            swipelayout.setLayoutParams(params1);
                            profileImageView.setAlpha(.3f);
                            layout.setVisibility(View.VISIBLE);
                            uparrow.setVisibility(View.INVISIBLE);
                            downarrow.setVisibility(View.VISIBLE);

                        }
                    });

                }


                @SwipeOut
                private void onSwipedOut(){
//                    pos=position;
                    for (int i=0;i<list.size();i++)
                    {
                        if (mProfile.getUserid()==list.get(i).getUserid())
                        {
                            pos=i;
                        }
                    }
//                    pos=    mSwipeView.getVerticalScrollbarPosition();
                    String myid=list.get(pos).getUserid();
                getwebservice1(myid,false);
//                   list.remove(pos);

                    Log.d("EVENT",String.valueOf(pos)+"On swiped out");
                    if (pos==list.size()-1)
                    {
                        mSwipeView.setVisibility(View.GONE);
                        rippleBackground.startRippleAnimation();
                        getwebservice();
                    }
                 //   mSwipeView.removeViewAt(position);

                    Log.d("EVENT", "onSwipedOut");


                }
                @Click(R.id.imagedislike)
                private void ondisClick(){
                    Log.d("EVENT", "profileImageView click");
//                    mSwipeView.addView(this);
            mSwipeView.doSwipe(false);
                }
                @SwipeCancelState
                private void onSwipeCancelState(){
                    Log.d("EVENT", "onSwipeCancelState");
                }

                @SwipeIn
                private void onSwipeIn(){
                    for (int i=0;i<list.size();i++)
                    {
                        if (mProfile.getUserid()==list.get(i).getUserid())
                        {
                            pos=i;
                        }
                    }
//                    pos=position;

                    String myid=list.get(pos).getUserid();
                    getwebservice1(myid,true);
                    getwebservice2(myid);
                    new MobiComConversationService(getActivity()).sendMessage(new Message(myid, "I Like Your Profile .Do You Like To Connect?"));
////                    list.remove(pos);
                    if (pos==list.size()-1)
                    {
                        mSwipeView.setVisibility(View.GONE);
                        rippleBackground.startRippleAnimation();
                        getwebservice();
                    }
                  //  mSwipeView.removeViewAt(position);
                    Log.d("EVENT", "onSwipedIn");
                }

                @SwipeInState
                private void onSwipeInState(){
                    Log.d("EVENT", "onSwipeInState");
                }

                @SwipeOutState
                private void onSwipeOutState(){
                    Log.d("EVENT", "onSwipeOutState");
                }
            }
   public void getwebservice1(String userid,boolean likedislike)
   {
       final HashMap<String, String> params = new HashMap<>();
       String Myid=getPref(Constants.USER_ID);
       params.put("user_id",Myid);
       params.put("action_user_id",userid);
       if (likedislike)
       {
           params.put("like_dislike","1");
       }
       else
       {
           params.put("like_dislike","2");
       }
       APIService service=ServiceGenerator.createService(APIService.class,getContext());
       Call<ResponseBody>call=service.likedislike(params);
       call.enqueue(new Callback<ResponseBody>() {
           @Override
           public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

           }

           @Override
           public void onFailure(Call<ResponseBody> call, Throwable t) {

           }
       });
   }
            public void getwebservice()
    {
    final Handler handler=new Handler();
    nostylers.setVisibility(View.GONE);
    final HashMap<String, String> params = new HashMap<>();
    String userid=getPref(Constants.USER_ID);
    params.put("userid",userid);
    params.put("myLat",Lat);
    params.put("myLon",Long);
    params.put("limit","40");
    APIService service= ServiceGenerator.createService(APIService.class,getContext());
    Call<ModelMatch>call=service.getmatchedusers(params);
    call.enqueue(new Callback<ModelMatch>() {
        @Override
        public void onResponse(Call<ModelMatch> call, Response<ModelMatch> response) {
            if (response.isSuccessful())
            {
                if (response.body()!=null&&response.body().getResult()!=null)
                {
                    ModelMatch modelMatch=response.body();
                    list.clear();
                    List<Styler>stylers=modelMatch.getResult().getStylers();
                    if (stylers.size()>0)
                    {
                        mSwipeView.removeAllViews();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                rippleBackground.stopRippleAnimation();
//                       imageViewcenter.setVisibility(View.GONE);
                               mSwipeView.setVisibility(View.VISIBLE);

                            }
                        },3000);
                        for (int i=0;i<stylers.size();i++) {
                            String nevergo=stylers.get(i).getUserUnliked();
                            String userid=stylers.get(i).getUserId();
                            String username = stylers.get(i).getUsername();
                            String userbrands = stylers.get(i).getUserbrands();
                            String useericons = stylers.get(i).getUserStylesIcons();
                            String online = stylers.get(i).getOnlineStatus();
                            String imagepath = stylers.get(i).getUserPhoto();
                            String genders=stylers.get(0).getGender();
                            String male_pref=stylers.get(0).getMalePreference();
                            String female_pref=stylers.get(0).getFemalePreference();
                            String other_interests=stylers.get(0).getOtherInterest();
//                            String distance=stylers.get(i).getDistance();
                            String lat = stylers.get(i).getLatitude();
                            String lon = stylers.get(i).getLongitude();
                            String dob = stylers.get(i).getDob();
                            String temp = dob.substring(0, dob.indexOf("-"));
                            int yeartemp = Integer.valueOf(temp);
                            final Calendar calender = Calendar.getInstance(TimeZone.getDefault());
                            final int Year = calender.get(Calendar.YEAR);
                            int agetemp = Year - yeartemp;
                            String age = agetemp + " Years Old";
                            List<Tribe> tribes = stylers.get(i).getTribes();
                            List<OtherTribe> othertribes = stylers.get(i).getOtherTribes();
                            double lat1 = Double.parseDouble(lat);
                            double lon1 = Double.parseDouble(lon);
//                                String tem1=getPref(Constants.LAT);
//                                String tem2=getPref(Constants.LONG);
                            double lat2 = Double.parseDouble(Lat);
                            double lon2 = Double.parseDouble(Long);
                            double s = distance(lat1, lon1, lat2, lon2);
                            DecimalFormat numberFormat = new DecimalFormat("#.00");
                            String st = String.valueOf(numberFormat.format(s)) + " km away";
                            builder=new StringBuilder();
                            if (tribes != null) {
                                for (int j = 0; j < tribes.size(); j++) {
                                    builder.append(tribes.get(j).getTribeTitle());
                                    builder.append(", ");

                                }


                            }

                            if (othertribes != null) {
                                for (int j = 0; j < othertribes.size(); j++) {
                                    builder.append(othertribes.get(j).getTribeTitle());
                                    builder.append(", ");

                                }
                            }
                            String Tilike="";
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
                            String t = builder.toString();
                            String tribetem="";
                            if (!t.isEmpty()) {
                                 tribetem  = t.substring(0, t.indexOf(","));
                            }
//                            String T1 = "My styler tribes: " + "<small>" + tribetem + "</small>" + "  ";
//                            String T2 = "My designer/brands: " + "<small>" + userbrands + "</small>" + "  ";
//                            String T3 = "My style icons: " + "<small>" + useericons + "</small>";
                            Matchlistitem matchlistitem = new Matchlistitem();
                                matchlistitem.setUserid(userid);
                                matchlistitem.setAge(age);
                                matchlistitem.setUsername(username);
                                matchlistitem.setBrands(userbrands);
                            matchlistitem.setImagepath(imagepath);
                                matchlistitem.setDistance(st);
                                matchlistitem.setOnlinestatus(online);
                                matchlistitem.setTribes(t);
                                matchlistitem.setStylicons(useericons);
                            matchlistitem.setIam(genders);
                            matchlistitem.setILIke(Tilike);
                            matchlistitem.setOtherinterests(other_interests);
                            matchlistitem.setNevergo(nevergo);
                                list.add(matchlistitem);

//                            Data d = new Data(imagepath, "");
//                            array.add(d);

                        }
                        for (Matchlistitem profiles:list)
                        {
                            mSwipeView.addView(new TinderCard(mContext, profiles, mSwipeView));
                        }
//                        myAppAdapter.notifyDataSetChanged();

                    }
                    else {
                        nostylers.setVisibility(View.VISIBLE);
                    }


                }

            }
        }

        @Override
        public void onFailure(Call<ModelMatch> call, Throwable t) {
            nostylers.setVisibility(View.VISIBLE);

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
    private void setui() {
//        array.add(new Data("https://www.androidtutorialpoint.com/wp-content/uploads/2016/11/Katrina-Kaif.jpg", "Hi I am Katrina Kaif. Wanna chat with me ?. \n" +
//                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."));
//        array.add(new Data("https://www.androidtutorialpoint.com/wp-content/uploads/2016/11/Emma-Watson.jpg", "Hi I am Emma Watson. Wanna chat with me ? \n" +
//                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."));
//        array.add(new Data("https://www.androidtutorialpoint.com/wp-content/uploads/2016/11/Scarlett-Johansson.jpg", "Hi I am Scarlett Johansson. Wanna chat with me ? \n" +
//                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."));
//        array.add(new Data("https://www.androidtutorialpoint.com/wp-content/uploads/2016/11/Priyanka-Chopra.jpg", "Hi I am Priyanka Chopra. Wanna chat with me ? \n" +
//                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."));
//        array.add(new Data("https://www.androidtutorialpoint.com/wp-content/uploads/2016/11/Deepika-Padukone.jpg", "Hi I am Deepika Padukone. Wanna chat with me ? \n" +
//                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."));
//        array.add(new Data("https://www.androidtutorialpoint.com/wp-content/uploads/2016/11/Anjelina-Jolie.jpg", "Hi I am Anjelina Jolie. Wanna chat with me ? \n" +
//                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."));
//        array.add(new Data("https://www.androidtutorialpoint.com/wp-content/uploads/2016/11/Aishwarya-Rai.jpg", "Hi I am Aishwarya Rai. Wanna chat with me ? \n" +
//                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."));

//        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
//            @Override
//            public void removeFirstObjectInAdapter() {
//
//            }
//
//            @Override
//            public void onLeftCardExit(Object dataObject) {
//                String myid=list.get(0).getUserid();
//                getwebservice1(myid,false);
//
//                    list.remove(0);
//                    myAppAdapter.notifyDataSetChanged();
//                if (list.size()==0)
//                {
//                    flingContainer.setVisibility(View.GONE);
//                    rippleBackground.startRippleAnimation();
//                    getwebservice();
//
//                }
//
//
//
//                //Do something on the left!
//                //You also have access to the original object.
//                //If you want to use it just cast it (String) dataObject
//            }
//
//            @Override
//            public void onRightCardExit(Object dataObject) {
//                String myid=list.get(0).getUserid();
//                getwebservice1(myid,true);
//                getwebservice2(myid);
//                new MobiComConversationService(getActivity()).sendMessage(new Message(myid, "I Like Your Profile .Do You Like To Connect?"));
//                    list.remove(0);
//                    myAppAdapter.notifyDataSetChanged();
//                if (list.size()==0)
//                {
//                    flingContainer.setVisibility(View.GONE);
//                    rippleBackground.startRippleAnimation();
//                    getwebservice();
//
//                }
//
//            }
//
//            @Override
//            public void onAdapterAboutToEmpty(int itemsInAdapter) {
////                flingContainer.setVisibility(View.GONE);
////                getwebservice();
////                String imagepath=getPref(Constants.IMAGE_PATH);
//////        Toast.makeText(getContext(),imagepath,Toast.LENGTH_LONG).show();
////                if (imagepath!=null)
////                {
////                    Picasso.with(getContext()).load(imagepath).placeholder(R.drawable.user_default).error(R.drawable.user_default)
////                            .into(imageViewcenter);
////                }
////                rippleBackground.startRippleAnimation();
//
//            }
//
//            @Override
//            public void onScroll(float scrollProgressPercent) {
//
//                View view = flingContainer.getSelectedView();
//                view.findViewById(R.id.background).setAlpha(0);
//                view.findViewById(R.id.item_swipe_right_indicator).setAlpha(scrollProgressPercent < 0 ? -scrollProgressPercent : 0);
//                view.findViewById(R.id.item_swipe_left_indicator).setAlpha(scrollProgressPercent > 0 ? scrollProgressPercent : 0);
//            }
//        });


        // Optionally add an OnItemClickListener
//        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClicked(int itemPosition, Object dataObject) {
//
//                View view = flingContainer.getSelectedView();
//                view.findViewById(R.id.background).setAlpha(0);
//
//                myAppAdapter.notifyDataSetChanged();
//            }
//        });
    }

            private void getwebservice2(String recieverid) {
                final HashMap<String, String> params = new HashMap<>();
                String userid=getPref(Constants.USER_ID);
                params.put("sender_id",userid);
                params.put("receiver_id",recieverid);
                APIService service=ServiceGenerator.createService(APIService.class,getContext());
                Call<ResponseBody>call=service.chathelper(params);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
            }

            @Override
            public void onStart() {
                super.onStart();
                if(  mGoogleApiClient!= null){
                    mGoogleApiClient.connect();
                }
            }
            @Override
            public void onStop() {
                super.onStop();
                if(mGoogleApiClient != null){
                    mGoogleApiClient.disconnect();
                }
            }
            @Override
            public void onConnectionSuspended(int i) {
                Log.w(((Object) this).getClass().getSimpleName(),
                        "onConnectionSuspended() called.");

            }
            @Override
            public void onConnected(@Nullable Bundle bundle) {
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                if (mCurrentLocation == null) {
                    Toast.makeText(getContext(), com.applozic.mobicomkit.uiwidgets.R.string.waiting_for_current_location, Toast.LENGTH_SHORT).show();
                    locationRequest = new LocationRequest();
                    locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                    locationRequest.setInterval(UPDATE_INTERVAL);
                    locationRequest.setFastestInterval(FASTEST_INTERVAL1);
                    LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, locationRequest, this);

                }
//        hideProgress();
                if (mCurrentLocation != null) {
                    Lat=String.valueOf(mCurrentLocation.getLatitude());
                    Long=String.valueOf(mCurrentLocation.getLongitude());
                    savePref(Constants.LAT,Lat);
                    savePref(Constants.LONG,Long);
//                    Latlng=Lat+","+Long;
                    final Calendar calender=Calendar.getInstance(TimeZone.getDefault());
                    int year=calender.get(Calendar.YEAR);
                    int month=calender.get(Calendar.MONTH);
                    int day=calender.get(Calendar.DAY_OF_MONTH);
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
//                    date=String.valueOf(year)+monthtemp+datetemp;
//            Toast.makeText(getActivity(),date,Toast.LENGTH_LONG).show();
                   getwebservice();


//           showToast(String.valueOf(mLastLocation.getLatitude()),true);
                }
            }
            @Override
            public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {


            }

            @Override
            public void onLocationChanged(Location location) {

            }
        public static class ViewHolder {
            public static FrameLayout background;
            public TextView DataText;
            public ImageView cardImage;
            public ImageView liked;
            public ImageView unliked;
            public TextView Username;
            public TextView age;
            public ImageView round;
            public TextView distance;
            public TextView oninenow;
            public TextView mytribes;
            public TextView mybrands;
            public TextView myicons;
        }

    public class MyAppAdapter extends BaseAdapter {


        public List<Matchlistitem> parkingList;
        public Context context;

        private MyAppAdapter(List<Matchlistitem> apps, Context context) {
            this.parkingList = apps;
            this.context = context;
        }

        @Override
        public int getCount() {
            return parkingList.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            View rowView = convertView;


            if (rowView == null) {
//                Fresco.initialize(context);

                LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                rowView = inflater.inflate(R.layout.item, parent, false);
                // configure view holder
                viewHolder = new ViewHolder();
//                viewHolder.DataText = (TextView) rowView.findViewById(R.id.bookText);
                viewHolder.liked= (ImageView) rowView.findViewById(R.id.imageliked);
                viewHolder.unliked= (ImageView) rowView.findViewById(R.id.imagedislike);
                viewHolder.Username= (TextView) rowView.findViewById(R.id.textUsername);
                viewHolder.age= (TextView) rowView.findViewById(R.id.textuseryear);
                viewHolder.round= (ImageView) rowView.findViewById(R.id.imageround);
                viewHolder.oninenow= (TextView) rowView.findViewById(R.id.textOnlineStatus);
                viewHolder.distance= (TextView) rowView.findViewById(R.id.textdistance);
                viewHolder.mytribes= (TextView) rowView.findViewById(R.id.myStylerTribes);
//                viewHolder.mybrands= (TextView) rowView.findViewById(R.id.myStylerbrands);
//                viewHolder.myicons= (TextView) rowView.findViewById(R.id.myStylericons);
                viewHolder.background = (FrameLayout) rowView.findViewById(R.id.background);
                viewHolder.cardImage = (ImageView) rowView.findViewById(R.id.cardImage);
//                viewHolder.cardImage= (SimpleDraweeView) rowView.findViewById(R.id.sdvImage);
                rowView.setTag(viewHolder);

            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
//           viewHolder.DataText.setText(parkingList.get(position).getDescription() + "");

            int onlinetemp=Integer.valueOf(parkingList.get(position).getOnlinestatus());
//            Online=onlineStatus;
            if (onlinetemp==1)
            {
                viewHolder.oninenow.setText("ONLINE NOW");
//                textStatus.setText("Online");
               viewHolder.round.setColorFilter(ContextCompat.getColor(getContext(),R.color.green));
            }
            else {
                viewHolder.oninenow.setText("OFFLINE NOW");
//                textStatus.setText("Online");
                viewHolder.round.setColorFilter(ContextCompat.getColor(getContext(),R.color.yellow));
            }
//            Uri imageUri = Uri.parse(parkingList.get(position).getImagepath());
//            viewHolder.cardImage.setImageURI(imageUri);
            Glide.with(getContext()).load(parkingList.get(position).getImagepath()).into(viewHolder.cardImage);
          viewHolder.Username.setText(parkingList.get(position).getUsername());
            viewHolder.age.setText(parkingList.get(position).getAge());
            viewHolder.distance.setText(parkingList.get(position).getDistance());
            viewHolder.mytribes.setText(Html.fromHtml(parkingList.get(position).getTribes()));
//            viewHolder.myicons.setText(Html.fromHtml(parkingList.get(position).getStylicons()));
//            viewHolder.mybrands.setText(Html.fromHtml(parkingList.get(position).getBrands()));
            viewHolder.liked.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    flingContainer.getTopCardListener().selectRight();
                }
            });
            viewHolder.unliked.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    flingContainer.getTopCardListener().selectLeft();
                }
            });

            return rowView;
        }
    }
    private void initui(View v) {
        rippleBackground=(RippleBackground)v.findViewById(R.id.content);
//        flingContainer = (SwipeFlingAdapterView)v.findViewById(R.id.frame);
//       flingContainer.setVisibility(View.GONE);
        mSwipeView = (SwipePlaceHolderView)v.findViewById(R.id.swipeView);
        mSwipeView.setVisibility(View.GONE);
        mContext =v.getContext();
        nostylers= (TextView) v.findViewById(R.id.nostylers);
        Typeface typeface=Typeface.createFromAsset(getContext().getAssets(),"fonts/brandon_grotesque_bold.ttf");
        Typeface typeface1=Typeface.createFromAsset(getContext().getAssets(),"fonts/BrandonGrotesque-Regular.ttf");
        nostylers.setTypeface(typeface1);
//        myAppAdapter = new MyAppAdapter(list,getActivity());
//        flingContainer.setAdapter(myAppAdapter);
        imageViewcenter= (CircleImageView) v.findViewById(R.id.centerImage);
//        imageViewcenter.setVisibility(View.GONE);
        String imagepath=getPref(Constants.IMAGE_PATH);
//        Toast.makeText(getContext(),imagepath,Toast.LENGTH_LONG).show();
        if (imagepath!=null)
        {
            Picasso.with(getContext()).load(imagepath).placeholder(R.drawable.user_default).error(R.drawable.user_default)
                    .into(imageViewcenter);
        }
        rippleBackground.startRippleAnimation();
//        final Handler handler=new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                rippleBackground.stopRippleAnimation();
//                imageViewcenter.setVisibility(View.GONE);
//                flingContainer.setVisibility(View.VISIBLE);
//
//            }
//        },5000);




    }
}

