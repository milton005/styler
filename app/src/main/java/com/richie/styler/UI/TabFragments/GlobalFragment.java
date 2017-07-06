package com.richie.styler.UI.TabFragments;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.richie.styler.R;
import com.richie.styler.UI.Adapter.GRIDHOMEADAPTER;
import com.richie.styler.UI.Base.BaseFragment;
import com.richie.styler.UI.DETAIL.DetailActivity;
import com.richie.styler.UI.Landing.MyInterface;
import com.richie.styler.UI.Models.GridHomeModel;
import com.richie.styler.UI.Models.NewStylers.ModelnewStylers;
import com.richie.styler.UI.Models.NewStylers.MostLiked;
import com.richie.styler.UI.Models.NewStylers.MostLikedInHour;
import com.richie.styler.UI.Models.NewStylers.NewStyler;
import com.richie.styler.UI.Models.NewStylers.OnlineStyler;
import com.richie.styler.UI.Models.NewStylers.Result;
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

import static com.richie.styler.R.id.rllayout2;

/**
 * Created by User on 03-04-2017.
 */

public class GlobalFragment extends BaseFragment implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener ,LocationListener{
    private static final int LOCATION_SERVICE_ENABLE =1001 ;
    private static final int CONNECTION_FAILURE_RESOLUTION_REQUEST =9000 ;
    private static final String TAG = "styler";
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;
    RecyclerView recyclerView1;
    RecyclerView recyclerView2;
    RecyclerView recyclerView4;
    private LocationRequest locationRequest;
    Location mCurrentLocation;
    private static final long INTERVAL = 1000 * 10;
    private static final long FASTEST_INTERVAL1 = 1000 * 5;
    public ArrayList<GridHomeModel> list1, list2, list3,list4;
    RecyclerView recyclerView3;
    RelativeLayout layout1, layout2, layout3,layout4;
    TextView textView1, textView2, textView3,textView4;
    TextView textViewmore1, textViewmore2, textViewmore3,textViewmore4;
    RelativeLayout morelayout1, morelayout2, morelayout3,morelayout4;
    GRIDHOMEADAPTER adpter1, adapter2, adapter3,adapter4;
    RecyclerView.LayoutManager mLayoutManager;
    GoogleApiClient mGoogleApiClient;
    String Lat="",Long="";
MyInterface myInterface;
    RelativeLayout moreonline;
    SwipeRefreshLayout refreshLayout;
    GPSTracker gps;
    protected static final long UPDATE_INTERVAL = 5;
    protected static final long FASTEST_INTERVAL = 1;
    List<NewStyler>newStylers;
    List<OnlineStyler>onlineStylers;
    List<MostLiked>mostLikeds;
    List<MostLikedInHour>mostLikedInHours;
    private static final int ACCESS_FINE_LOCATION = 90;

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
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (!Lat.isEmpty()) {

                getWebService();
            }

            // Refresh your fragment here
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
            showProgress();
            mGoogleApiClient.disconnect();
            mGoogleApiClient.connect();
        }
    }
    @Override
    public void onStart() {
        super.onStart();
        if(  mGoogleApiClient!= null){
            mGoogleApiClient.connect();
        }
    }
    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(getContext());
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, getActivity(),
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
//                Toast.makeText(getApplicationContext(),
//                        "This device is not supported.", Toast.LENGTH_LONG)
//                        .show();
//                finish();
            }
            return false;
        }
        return true;
    }
    private void displayLocationSettingsRequest(Context context) {
        GoogleApiClient googleApiClient = new GoogleApiClient.Builder(context)
                .addApi(LocationServices.API).build();
        googleApiClient.connect();

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(10000 / 2);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        Log.i(TAG, "All location settings are satisfied.");
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        Log.i(TAG, "Location settings are not satisfied. Show the user a dialog to upgrade location settings ");

//                        try {
//                            // Show the dialog by calling startResolutionForResult(), and check the result
//                            // in onActivityResult().
////                            status.startResolutionForResult(getActivity(), REQUEST_CHECK_SETTINGS);
//                        } catch (IntentSender.SendIntentException e) {
//                            Log.i(TAG, "PendingIntent unable to execute request.");
//                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        Log.i(TAG, "Location settings are inadequate, and cannot be fixed here. Dialog not created.");
                        break;
                }
            }
        });
    }
    @Override
    public void onStop() {
        super.onStop();
        if(mGoogleApiClient != null){
            mGoogleApiClient.disconnect();
        }
        hideProgress();
    }
    @Override
    public void onConnectionSuspended(int i) {
        Log.w(((Object) this).getClass().getSimpleName(),
                "onConnectionSuspended() called.");

    }
    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.global_fragment_layout, null);
        initui(v);
        list1 = new ArrayList<>();
        list2 = new ArrayList<>();
        list3 = new ArrayList<>();
        list4=new ArrayList<>();
//      showProgress();
//        for (int i = 0; i < 12; i++) {
//            GridHomeModel model = new GridHomeModel();
//            model.setImagename("JAKUB");
//            list1.add(model);
//        }

//        for (int i = 0; i < 10; i++) {
//            GridHomeModel model = new GridHomeModel();
//            model.setImagename("ARNOLD");
//            list2.add(model);
//        }
//        for (int i = 0; i < 7; i++) {
//            GridHomeModel model = new GridHomeModel();
//            model.setImagename("GAMER");
//            list3.add(model);
//        }

        textView1.setText("Online");
        textView3.setText("Most liked new stylers");
        textView4.setText("Most liked stylers in last hour");
        textView2.setText("New Stylers");
        setRecyclerview1();
        setRecyclerview2();
        setRecyclerview3();
        setRecyclerview4();
        setAdapter1();
        setAdapter2();
        setAdapter3();
        setAdapter4();
        layout1.setVisibility(View.GONE);
//        layout3.setVisibility(View.GONE);
//       getWebService();
        setui();
//        if (list1.size() < 12) {
//            morelayout1.setVisibility(View.GONE);
//
//        }
//        if (list2.size() < 12) {
//            morelayout2.setVisibility(View.GONE);
//        }
//        if (list3.size() < 12) {
//            morelayout3.setVisibility(View.GONE);
//        }
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getWebService();
            }
        });
        return v;
    }



    private void setui() {
        ItemClickSupport.addTo(recyclerView2).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                String userid=newStylers.get(position).getUserId();
                String online=newStylers.get(position).getOnlineStatus();
//                String distance=newStylers.get(position).getDistance();
                String userphoto=newStylers.get(position).getUserPhoto();
                Intent intent=new Intent(getContext(), DetailActivity.class);
                intent.putExtra("user_id",userid);
                intent.putExtra("photo",userphoto);
                intent.putExtra("online",online);
//                intent.putExtra("distance",distance);
                startActivity(intent);

            }
        });
        ItemClickSupport.addTo(recyclerView3).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                String userid=mostLikeds.get(position).getUserId();
                String online=mostLikeds.get(position).getOnlineStatus();
//                String distance=mostLikeds.get(position).getDistance();
                String userphoto=mostLikeds.get(position).getUserPhoto();
                Intent intent=new Intent(getContext(), DetailActivity.class);
                intent.putExtra("user_id",userid);
                intent.putExtra("photo",userphoto);
                intent.putExtra("online",online);
//                intent.putExtra("distance",distance);
                startActivity(intent);
            }
        });
        ItemClickSupport.addTo(recyclerView4).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                String userid=mostLikedInHours.get(position).getUserId();
                String online=mostLikedInHours.get(position).getOnlineStatus();
//                String distance=mostLikeds.get(position).getDistance();
                String userphoto=mostLikedInHours.get(position).getUserPhoto();
                Intent intent=new Intent(getContext(), DetailActivity.class);
                intent.putExtra("user_id",userid);
                intent.putExtra("photo",userphoto);
                intent.putExtra("online",online);
//                intent.putExtra("distance",distance);
                startActivity(intent);
            }
        });
        ItemClickSupport.addTo(recyclerView1).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                try {
                    String userid=onlineStylers.get(position).getUserId();
                    String online=onlineStylers.get(position).getOnlineStatus();
//                    String distance=onlineStylers.get(position).getDistance();
                   String userphoto=onlineStylers.get(position).getUserPhoto();
                    Intent intent=new Intent(getContext(), DetailActivity.class);
                    intent.putExtra("user_id",userid);
                    intent.putExtra("photo",userphoto);
                    intent.putExtra("online",online);
//                    intent.putExtra("distance",distance);
                    startActivity(intent);
                }catch (Exception e)
                {

                }

            }
        });

    }

    private void getWebService() {
        try {




            HashMap<String, String> params = new HashMap<>();
            String userid = getPref(Constants.USER_ID);
            params.put("userid", userid);
            params.put("limit", "40");
//      Lat=getPref(Constants.LAT);
//      Long=getPref(Constants.LONG);
//        showToast("Success",true);
            if (!Lat.isEmpty()) {
                params.put("myLat", Lat);
            }
            if (!Long.isEmpty()) {
                params.put("myLon", Long);
            }
            //params.put("limit","40");
            APIService service = ServiceGenerator.createService(APIService.class, getContext());
            Call<ModelnewStylers> call = service.getnewstylers(params);
            call.enqueue(new Callback<ModelnewStylers>() {
                @Override
                public void onResponse(Call<ModelnewStylers> call, Response<ModelnewStylers> response) {
                    if (response.isSuccessful()) {
//                    showToast("Success",true);
                        Activity activity = getActivity();
                        if(activity != null && isAdded())
                        {
                            hideProgress();
                        }
                        if (response.body() != null && response.body().getResult() != null) {
                            ModelnewStylers modelnewStylers = response.body();
                            Result result = modelnewStylers.getResult();
                            newStylers = result.getNewStylers();
                            list2.clear();
                            textView1.setVisibility(View.GONE);
                            int k = 0;
                            if (newStylers.size() > 12) {
                                k = 12;
                            } else {
                                k = newStylers.size();
                            }
                            for (int i = 0; i < k; i++) {
                                GridHomeModel model = new GridHomeModel();
                                model.setImagename(newStylers.get(i).getUsername());
                                model.setImagepath(newStylers.get(i).getUserPhoto());
                                String onlinestatus = newStylers.get(i).getOnlineStatus();
                                model.setOnline(Integer.valueOf(onlinestatus));
                                list2.add(model);

                            }
                            if (list2.size() > 0) {
                                textView2.setVisibility(View.VISIBLE);
                                layout2.setVisibility(View.VISIBLE);
                                if (newStylers.size()>12) {
                                    morelayout2.setVisibility(View.VISIBLE);
                                }
                                else
                                {
                                    morelayout2.setVisibility(View.GONE);
                                }
                            } else {
                                textView2.setVisibility(View.GONE);
                                morelayout2.setVisibility(View.GONE);
                            }
                            layout2.setVisibility(View.VISIBLE);

                            adapter2.notifyDataSetChanged();

                            try {
                                layout1.setVisibility(View.GONE);
                                list1.clear();
                                list3.clear();
                                list4.clear();

//                                recyclerView1.setAdapter(null);
//                                recyclerView4.setAdapter(null);
//                                recyclerView3.setAdapter(null);

                                mostLikeds = modelnewStylers.getResult().getMostLiked();
                                int n = 0;
                                if (mostLikeds.size() > 12) {
                                    n = 12;
                                } else {
                                    n = mostLikeds.size();
                                }
                                for (int i = 0; i < n; i++) {
                                    GridHomeModel model = new GridHomeModel();
                                    model.setImagename(mostLikeds.get(i).getUsername());
                                    model.setImagepath(mostLikeds.get(i).getUserPhoto());
                                    String onlinestatus = mostLikeds.get(i).getOnlineStatus();
                                    model.setOnline(Integer.valueOf(onlinestatus));
                                    list3.add(model);
                                }
                                if (list3.size() > 0) {
                                    textView3.setVisibility(View.VISIBLE);
                                    ;
                                    layout3.setVisibility(View.VISIBLE);

                                    if (mostLikeds.size() > 12) {
                                        morelayout3.setVisibility(View.VISIBLE);
                                    } else {


                                        morelayout3.setVisibility(View.GONE);
                                    }
                                } else {

                                    morelayout3.setVisibility(View.GONE);
                                    layout3.setVisibility(View.GONE);
                                    textView3.setVisibility(View.GONE);
                                }
                                adapter3.notifyDataSetChanged();
                            }catch (Exception e) {
                            }
                            try {
                                layout1.setVisibility(View.GONE);
                                onlineStylers=modelnewStylers.getResult().getOnlineStylers();

                                int j = 0;
                                if (onlineStylers.size() > 12) {
                                    j = 12;
                                } else {
                                    j = onlineStylers.size();
                                }
                                for (int i = 0; i < j; i++) {
                                    GridHomeModel model = new GridHomeModel();
                                    model.setImagename(onlineStylers.get(i).getUsername());
                                    model.setImagepath(onlineStylers.get(i).getUserPhoto());
                                    String onlinestatus = onlineStylers.get(i).getOnlineStatus();
                                    model.setOnline(Integer.valueOf(onlinestatus));
                                    list1.add(model);
                                }

                                if (list1.size() > 0) {
                                    textView1.setVisibility(View.VISIBLE);
                                    ;
                                    layout1.setVisibility(View.VISIBLE);

                                    if (onlineStylers.size() > 12) {
                                        moreonline.setVisibility(View.VISIBLE);
                                        morelayout1.setVisibility(View.VISIBLE);
                                    } else {

                                        moreonline.setVisibility(View.GONE);
                                        morelayout1.setVisibility(View.GONE);
                                    }
                                } else {
                                    moreonline.setVisibility(View.GONE);
                                    morelayout1.setVisibility(View.GONE);
                                    layout1.setVisibility(View.GONE);
                                    textView1.setVisibility(View.GONE);
                                }

                            }catch (Exception e) {
                            }
                            adpter1.notifyDataSetChanged();
                            try {
                                layout4.setVisibility(View.GONE);
                                mostLikedInHours=modelnewStylers.getResult().getMostLikedInHour();
                                int m=0;
                                if (mostLikedInHours.size()>12)
                                {
                                    m=12;
                                }
                                else
                                {
                                    m=mostLikedInHours.size();
                                }
                                for (int i=0;i<m;i++)
                                {
                                    GridHomeModel model = new GridHomeModel();
                                    model.setImagename(mostLikedInHours.get(i).getUsername());
                                    model.setImagepath(mostLikedInHours.get(i).getUserPhoto());
                                    String onlinestatus = mostLikedInHours.get(i).getOnlineStatus();
                                    model.setOnline(Integer.valueOf(onlinestatus));
                                    list4.add(model);
                                }
                                if (list4.size() > 0) {
                                    textView4.setVisibility(View.VISIBLE);
                                    ;
                                    layout4.setVisibility(View.VISIBLE);

                                    if (mostLikedInHours.size() > 12) {
                                        morelayout4.setVisibility(View.VISIBLE);
                                    } else {


                                        morelayout4.setVisibility(View.GONE);
                                    }
                                } else {

                                    morelayout4.setVisibility(View.GONE);
                                    layout4.setVisibility(View.GONE);
                                    textView4.setVisibility(View.GONE);
                                }



                            } catch (Exception e) {

                            }
                            adapter4.notifyDataSetChanged();
                            if (refreshLayout.isRefreshing()) {
                                refreshLayout.setRefreshing(false);
                            }


                        }
                    } else {
                        Activity activity = getActivity();
                        if(activity != null && isAdded())
                        {
                            hideProgress();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ModelnewStylers> call, Throwable t) {
//                showToast("NETWORK ERROR",false);
                    if (refreshLayout.isRefreshing()) {
                        refreshLayout.setRefreshing(false);
                    }
                    Activity activity = getActivity();
                    if(activity != null && isAdded())
                    {
                        hideProgress();
                    }
                }

            });
        }catch (Exception e){

        }

    }

    private void setAdapter3() {
        adapter3 = new GRIDHOMEADAPTER(getContext(), list3);
        recyclerView3.setAdapter(adapter3);

    }

    private void setAdapter2() {
        adapter2 = new GRIDHOMEADAPTER(getContext(), list2);
        recyclerView2.setAdapter(adapter2);
    }

    private void setAdapter1() {
        adpter1 = new GRIDHOMEADAPTER(getContext(), list1);
        recyclerView1.setAdapter(adpter1);
    }
    private void setAdapter4() {
        adapter4 = new GRIDHOMEADAPTER(getContext(), list4);
        recyclerView4.setAdapter(adapter4);
    }
    private void setRecyclerview3() {
        recyclerView3.setHasFixedSize(true);
        mLayoutManager = new GridLayoutManager(getContext(), 4);
        recyclerView3.setLayoutManager(mLayoutManager);
    }
    private void setRecyclerview4() {
        recyclerView4.setHasFixedSize(true);
        mLayoutManager = new GridLayoutManager(getContext(), 4);
        recyclerView4.setLayoutManager(mLayoutManager);
    }
    private void setRecyclerview2() {
        recyclerView2.setHasFixedSize(true);
        mLayoutManager = new GridLayoutManager(getContext(), 4);
        recyclerView2.setLayoutManager(mLayoutManager);
    }

    private void setRecyclerview1() {
        recyclerView1.setHasFixedSize(true);
        mLayoutManager = new GridLayoutManager(getContext(), 4);
        recyclerView1.setLayoutManager(mLayoutManager);
    }

    private void initui(View v) {
        Typeface typeface=Typeface.createFromAsset(getContext().getAssets(),"fonts/brandon_grotesque_bold.ttf");
        Typeface typeface1=Typeface.createFromAsset(getContext().getAssets(),"fonts/BrandonGrotesque-Regular.ttf");
        recyclerView1 = (RecyclerView) v.findViewById(R.id.recyclerView1);
        recyclerView2 = (RecyclerView) v.findViewById(R.id.recyclerView2);
        recyclerView3 = (RecyclerView) v.findViewById(R.id.recyclerView3);
        recyclerView4= (RecyclerView) v.findViewById(R.id.recyclerView4);
        recyclerView1.setNestedScrollingEnabled(false);
        recyclerView2.setNestedScrollingEnabled(false);
        recyclerView3.setNestedScrollingEnabled(false);
        recyclerView4.setNestedScrollingEnabled(false);
        layout1 = (RelativeLayout) v.findViewById(R.id.rllayout1);
        layout2 = (RelativeLayout) v.findViewById(rllayout2);
        layout3 = (RelativeLayout) v.findViewById(R.id.rllayout3);
        layout4= (RelativeLayout) v.findViewById(R.id.rllayout4);
        textView1 = (TextView) v.findViewById(R.id.textViewrecycle1);
        textView2 = (TextView) v.findViewById(R.id.textViewrecycle2);
        textView3 = (TextView) v.findViewById(R.id.textViewrecycle3);
        textView4= (TextView) v.findViewById(R.id.textViewrecycle4);
        textViewmore1 = (TextView) v.findViewById(R.id.textViewmore1);
        textViewmore2 = (TextView) v.findViewById(R.id.textViewmore2);
        textViewmore3 = (TextView) v.findViewById(R.id.textViewmore3);
        textViewmore4= (TextView) v.findViewById(R.id.textViewmore4);
        morelayout1 = (RelativeLayout) v.findViewById(R.id.more_layout);
        morelayout2 = (RelativeLayout) v.findViewById(R.id.more_layout2);
        morelayout3 = (RelativeLayout) v.findViewById(R.id.more_layout3);
        morelayout4= (RelativeLayout) v.findViewById(R.id.more_layout4);
        moreonline=(RelativeLayout)v.findViewById(R.id.relativeLayoutmoreonline);
        textView2.setTypeface(typeface);
        textView1.setTypeface(typeface);
        textView3.setTypeface(typeface);
        textView4.setTypeface(typeface);
        refreshLayout= (SwipeRefreshLayout) v.findViewById(R.id.swiperefreshlayout);
        morelayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),MorestylersOnline.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.putExtra("LAT",Lat);
                intent.putExtra("LONG",Long);
                intent.putExtra("TYPE",2);
                startActivity(intent);
                getActivity().overridePendingTransition(0,0);
//                myInterface.setVisibility(true);

//                Fragment fragment=new NearByFragment();
//                FragmentManager manager=getFragmentManager();
//                FragmentTransaction fragmentTransaction=manager.beginTransaction();
//                fragmentTransaction.replace(R.id.container,fragment);
//                fragmentTransaction.commit();
            }
        });
        morelayout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),MorestylersOnline.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.putExtra("LAT",Lat);
                intent.putExtra("LONG",Long);
                intent.putExtra("TYPE",3);
                startActivity(intent);
                getActivity().overridePendingTransition(0,0);
//                myInterface.setVisibility(true);

//                Fragment fragment=new NearByFragment();
//                FragmentManager manager=getFragmentManager();
//                FragmentTransaction fragmentTransaction=manager.beginTransaction();
//                fragmentTransaction.replace(R.id.container,fragment);
//                fragmentTransaction.commit();
            }
        });
        morelayout4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),MorestylersOnline.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.putExtra("LAT",Lat);
                intent.putExtra("LONG",Long);
                intent.putExtra("TYPE",4);
                startActivity(intent);
                getActivity().overridePendingTransition(0,0);
//                myInterface.setVisibility(true);

//                Fragment fragment=new NearByFragment();
//                FragmentManager manager=getFragmentManager();
//                FragmentTransaction fragmentTransaction=manager.beginTransaction();
//                fragmentTransaction.replace(R.id.container,fragment);
//                fragmentTransaction.commit();
            }
        });
        morelayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),MorestylersOnline.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.putExtra("TYPE",1);
                intent.putExtra("LAT",Lat);
                intent.putExtra("LONG",Long);
                startActivity(intent);
                getActivity().overridePendingTransition(0,0);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LOCATION_SERVICE_ENABLE) {
            if (((LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE))
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                mGoogleApiClient.connect();
            } else {
                Toast.makeText(getContext(), com.applozic.mobicomkit.uiwidgets.R.string.unable_to_fetch_location, Toast.LENGTH_LONG).show();
            }
            return;
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
                        ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    ACCESS_FINE_LOCATION);

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
          getWebService();
//           showToast(String.valueOf(mLastLocation.getLatitude()),true);
        }
    }



    @Override
    public void onResume() {
        super.onResume();

        int PERMISSION_ALL = 1;
        String[] PERMISSIONS = {Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
        if(!hasPermissions(getActivity(), PERMISSIONS)){
            ActivityCompat.requestPermissions(getActivity(), PERMISSIONS, PERMISSION_ALL);
        }
    }
    public static boolean hasPermissions(Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        hideProgress();
         gps = new GPSTracker(getContext());
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(
                        getActivity(),
                        CONNECTION_FAILURE_RESOLUTION_REQUEST);
            } catch (IntentSender.SendIntentException e) {
                // Log the error
                e.printStackTrace();
            }
        } else {
//            showErrorDialog(connectionResult.getErrorCode());
        }
        if(gps.canGetLocation()){
            Lat=String.valueOf(gps.getLatitude());
            Long=String.valueOf(gps.getLongitude());

            getWebService();
        }
        else {
            showToast("No network provider available,please turn on GPS",false);
        }
        }

    @Override
    public void onLocationChanged(Location location) {

    }
}
