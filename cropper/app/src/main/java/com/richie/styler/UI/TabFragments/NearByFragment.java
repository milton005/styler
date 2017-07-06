package com.richie.styler.UI.TabFragments;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
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
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.richie.styler.R;
import com.richie.styler.UI.Adapter.GRIDHOMEADAPTER;
import com.richie.styler.UI.Base.BaseFragment;
import com.richie.styler.UI.DETAIL.DetailActivity;
import com.richie.styler.UI.Landing.MyInterface;
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
 * Created by User on 03-04-2017.
 */

public class NearByFragment extends BaseFragment implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,LocationListener{
    RecyclerView recyclerview;
    private static final int LOCATION_SERVICE_ENABLE =1001 ;
    GRIDHOMEADAPTER adapter;
    RecyclerView.LayoutManager mLayoutManager;
    public ArrayList<GridHomeModel>list;
    Button back;
    protected static final long UPDATE_INTERVAL = 5;
    SwipeRefreshLayout refreshLayout;
    String LAT="",Long="";
    private static final int CONNECTION_FAILURE_RESOLUTION_REQUEST =9000 ;
    MyInterface myInterface;
    private static final long INTERVAL = 1000 * 10;
    private static final long FASTEST_INTERVAL1 = 1000 * 5;
    List<Styler> list1;
    private LocationRequest locationRequest;
    Location mCurrentLocation;
    GoogleApiClient mGoogleApiClient;
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
    public void processLocation() {
        //  if (Utils.hasMarshmallow()) {
        //  new ApplozicPermissions(MobicomLocationActivity.this, layout).checkRuntimePermissionForLocation();
        // } else {
        processingLocation();
        // }
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {

            getWebService();

            // Refresh your fragment here
        }
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
        hideProgress();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.nearby_fragment_layout,null);
        list=new ArrayList<>();
//        for (int i=0;i<99;i++)
//        {
//            GridHomeModel model=new GridHomeModel();
//            model.setImagename("JAKUB");
//            list.add(model);
//        }
        initui(v);
        setAdapter();
        setui();
//        getWebService();
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getWebService();
            }
        });
        return v;
    }

        private void setui() {
            ItemClickSupport.addTo(recyclerview).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                @Override
                public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                    String userid=list1.get(position).getUserId();
                    String online=list1.get(position).getOnlineStatus();
                    String distance=list1.get(position).getDistance();
                    String userphoto=list1.get(position).getUserPhoto();
                    Intent intent=new Intent(getContext(), DetailActivity.class);
                    intent.putExtra("user_id",userid);
                    intent.putExtra("photo",userphoto);
                    intent.putExtra("online",online);
                    intent.putExtra("distance",distance);
                    startActivity(intent);

                }
            });

        }


    private void setAdapter() {
        adapter=new GRIDHOMEADAPTER(getContext(),list);
        recyclerview.setAdapter(adapter);
    }
    private void getWebService() {
        Activity activity = getActivity();
        if(activity != null && isAdded())
        {
            showProgress();
        }
        HashMap<String, String> params = new HashMap<>();
        String userid=getPref(Constants.USER_ID);
        params.put("userid",userid);
        params.put("limit","40");
//        LAT=getPref(Constants.LAT);
//        Long=getPref(Constants.LONG);
        if (!LAT.isEmpty())
        {
            params.put("myLat",LAT);
        }
        if (!Long.isEmpty())
        {
            params.put("myLon",Long);
        }
        APIService service = ServiceGenerator.createService(APIService.class, getContext());
        Call<ModelStylerSearch> call=service.getsearch(params);
        call.enqueue(new Callback<ModelStylerSearch>() {
            @Override
            public void onResponse(Call<ModelStylerSearch> call, Response<ModelStylerSearch> response) {
                if (response.isSuccessful())
                {
                    Activity activity = getActivity();
                    if(activity != null && isAdded())
                    {
                       hideProgress();
                    }
                    if (response.body()!=null&&response.body().getResult()!=null)
                    {
                        ModelStylerSearch model=response.body();
                        Result result=model.getResult();
                       list1=result.getStylers();
                        list.clear();
                        for (int  i=0;i<list1.size();i++)
                        {
                            GridHomeModel m=new GridHomeModel();
                            m.setImagename(list1.get(i).getUsername());
                            m.setImagepath(list1.get(i).getUserPhoto());
                            String online=list1.get(i).getOnlineStatus();
                            m.setOnline(Integer.valueOf(online));

                            list.add(m);
                        }
                        adapter.notifyDataSetChanged();
                        if ( refreshLayout.isRefreshing())
                        {
                            refreshLayout.setRefreshing(false);
                        }
                    }
                }
                else {
                    Activity activity = getActivity();
                    if(activity != null && isAdded())
                    {
                        hideProgress();
                    }
                }
            }

            @Override
            public void onFailure(Call<ModelStylerSearch> call, Throwable t) {
                if ( refreshLayout.isRefreshing())
                {
                    refreshLayout.setRefreshing(false);
                }
                Activity activity = getActivity();
                if(activity != null && isAdded())
                {
                    hideProgress();
                }
            }
        });
    }
    private void initui(View v) {
        back= (Button) v.findViewById(R.id.back);
        refreshLayout= (SwipeRefreshLayout) v.findViewById(R.id.swiperefreshlayout);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                myInterface.setVisibility(false);

            }
        });
        recyclerview= (RecyclerView) v.findViewById(R.id.recyclerViewNearby);
        recyclerview.setHasFixedSize(true);
        mLayoutManager=new GridLayoutManager(getContext(),4);
        recyclerview.setLayoutManager(mLayoutManager);

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
        if (mCurrentLocation != null) {
            LAT=String.valueOf(mCurrentLocation.getLatitude());
            Long=String.valueOf(mCurrentLocation.getLongitude());
            savePref(Constants.LAT,LAT);
            savePref(Constants.LONG,Long);
//            getWebService();
//           showToast(String.valueOf(mLastLocation.getLatitude()),true);
        }
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
    public void onConnectionSuspended(int i) {
        Log.w(((Object) this).getClass().getSimpleName(),
                "onConnectionSuspended() called.");

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
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
    }

    @Override
    public void onLocationChanged(Location location) {

    }
}
