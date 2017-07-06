package com.applozic.mobicomkit.uiwidgets.conversation.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.applozic.mobicomkit.broadcast.ConnectivityReceiver;
import com.applozic.mobicomkit.uiwidgets.R;
import com.applozic.mobicomkit.uiwidgets.Retrofit.APIService;
import com.applozic.mobicomkit.uiwidgets.Retrofit.Checkin.ModelCheckinnn;
import com.applozic.mobicomkit.uiwidgets.Retrofit.Checkin.Venue;
import com.applozic.mobicomkit.uiwidgets.Retrofit.CheckinAdapter;
import com.applozic.mobicomkit.uiwidgets.Retrofit.ItemClickSupport;
import com.applozic.mobicomkit.uiwidgets.Retrofit.ModelCheckin;
import com.applozic.mobicomkit.uiwidgets.conversation.ConversationUIService;
import com.applozic.mobicommons.commons.core.utils.PermissionsUtils;
import com.applozic.mobicommons.commons.core.utils.Utils;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import com.applozic.mobicomkit.uiwidgets.instruction.ApplozicPermissions;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MobicomLocationActivity extends AppCompatActivity implements OnMapReadyCallback, LocationListener, GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks, ActivityCompat.OnRequestPermissionsResultCallback {
    final String CLIENT_ID = "ZNO2KW4ZB245I0MSU1F24JQ412SXUSFHE4OPBTM1SRJUFGAM";
    final String CLIENT_SECRET = "DC4HETXW2A4Z2WJZTZJKLUYONUCCPBR4EHGTGC4SOZCNL1L4";
    public static final int LOCATION_SERVICE_ENABLE = 1001;
    protected static final long UPDATE_INTERVAL = 5;
    protected static final long FASTEST_INTERVAL = 1;
    public Snackbar snackbar;
    protected GoogleApiClient googleApiClient;
    SupportMapFragment mapFragment;
    LatLng position;
    RecyclerView recyclerView;
    RelativeLayout sendLocation;
    Location mCurrentLocation;
    private LinearLayout layout;
    private LocationRequest locationRequest;
    private ConnectivityReceiver connectivityReceiver;
    CheckinAdapter adapter;
    String Latlng;
    String date;
    public ArrayList<ModelCheckin> list;
    public List<Venue> venuelist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applozic_location);
        recyclerView= (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        final LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        list=new ArrayList<>();
        venuelist=new ArrayList<>();
        setadapter();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_map_screen);
        toolbar.setTitle("Send Location");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sendLocation = (RelativeLayout) findViewById(R.id.sendLocation);
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        googleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
        onNewIntent(getIntent());

        processLocation();
        setclicks();
        connectivityReceiver = new ConnectivityReceiver();
        registerReceiver(connectivityReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

    }

    private void setclicks() {
        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, final int position, View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MobicomLocationActivity.this);
                builder.setTitle("SEND LOCATION")
                        .setMessage("Send this location?")
                        .setCancelable(false)
                        .setPositiveButton("send", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = new Intent();
                                intent.putExtra("latitude", Double.parseDouble(list.get(position).getLat()));
                                intent.putExtra("longitude", Double.parseDouble(list.get(position).getLon()));
                                setResult(RESULT_OK, intent);
                                finish();
                            }
                        })
                        .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }

    private void setadapter() {
        adapter=new CheckinAdapter(this,list);
        recyclerView.setAdapter(adapter);
    }
    public void   getwebservice()
    {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        HashMap<String, String> params = new HashMap<>();
        params.put("client_id",CLIENT_ID);
        params.put("client_secret",CLIENT_SECRET);
        params.put("ll",Latlng);
        params.put("v",date);
        APIService service = new Retrofit.Builder().baseUrl("https://api.foursquare.com/v2/")  .addConverterFactory(GsonConverterFactory.create()).client(client).build().create(APIService.class);
        Call<ModelCheckinnn> call=service.getvenues(params);
        call.enqueue(new Callback<ModelCheckinnn>() {
            @Override
            public void onResponse(Call<ModelCheckinnn> call, Response<ModelCheckinnn> response) {
                if (response.isSuccessful()) {


                    ModelCheckinnn m = response.body();
                    if (response.body() != null) {
                        venuelist.clear();
                        venuelist = response.body().getResponse().getVenues();
                        if (venuelist.size()>0)
                        {  list.clear();

                            for (int i = 0; i < venuelist.size(); i++) {
                                String name = venuelist.get(i).getName();
                                String prefix="";
                                String postfix="";
                                if (venuelist.get(i).getCategories().size()>0) {
                                    prefix     = venuelist.get(i).getCategories().get(0).getIcon().getPrefix();
                                    postfix     = venuelist.get(i).getCategories().get(0).getIcon().getSuffix();
                                }
                                String lat=String.valueOf(venuelist.get(i).getLocation().getLat());
                                String lng=String.valueOf(venuelist.get(i).getLocation().getLng());
                                ModelCheckin mc = new ModelCheckin();
                                mc.setName(name);
                                mc.setLat(lat);
                                mc.setLon(lng);

                                String path = prefix + "bg_64" + postfix;
                                mc.setImagePath(path);

                                list.add(mc);
                            }
                            adapter.notifyDataSetChanged();
                        }



                    }



                }

            }

            @Override
            public void onFailure(Call<ModelCheckinnn> call, Throwable t) {

            }
        });

    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        position = new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());

        googleMap.addMarker(new MarkerOptions().position(position).title(""));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 20));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(17), 2000, null);

        sendLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("test", "in map");
                Intent intent = new Intent();
                intent.putExtra("latitude", mCurrentLocation.getLatitude());
                intent.putExtra("longitude", mCurrentLocation.getLongitude());
                setResult(RESULT_OK, intent);
                finish();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        new ConversationUIService(this).onActivityResult(requestCode, resultCode, data);
        if (requestCode == LOCATION_SERVICE_ENABLE) {
            if (((LocationManager) getSystemService(Context.LOCATION_SERVICE))
                    .isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                googleApiClient.connect();
            } else {
                Toast.makeText(MobicomLocationActivity.this, R.string.unable_to_fetch_location, Toast.LENGTH_LONG).show();
            }
            return;
        }
    }

    public void processingLocation() {
        if (!((LocationManager) getSystemService(Context.LOCATION_SERVICE))
                .isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.location_services_disabled_title)
                    .setMessage(R.string.location_services_disabled_message)
                    .setCancelable(false)
                    .setPositiveButton(R.string.location_service_settings, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivityForResult(intent, LOCATION_SERVICE_ENABLE);
                        }
                    })
                    .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                            Toast.makeText(MobicomLocationActivity.this, R.string.location_sending_cancelled, Toast.LENGTH_LONG).show();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        } else {
            googleApiClient.disconnect();
            googleApiClient.connect();
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
    protected void onStart() {
        super.onStart();
        if (googleApiClient != null) {
            googleApiClient.connect();
        }
    }


    @Override
    protected void onStop() {
        super.onStop();
        if (googleApiClient != null) {
            googleApiClient.disconnect();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.w(((Object) this).getClass().getSimpleName(),
                "onConnectionSuspended() called.");

    }

    @Override
    public void onConnected(Bundle bundle) {
        try {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
            if (mCurrentLocation == null) {
                Toast.makeText(this, R.string.waiting_for_current_location, Toast.LENGTH_SHORT).show();
                locationRequest = new LocationRequest();
                locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                locationRequest.setInterval(UPDATE_INTERVAL);
                locationRequest.setFastestInterval(FASTEST_INTERVAL);
                LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
            }

            if (mCurrentLocation != null) {
                mapFragment.getMapAsync(this);
                String lat=String.valueOf(mCurrentLocation.getLatitude());
                String lon=String.valueOf(mCurrentLocation.getLongitude());
                Latlng=lat+","+lon;
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
                date=String.valueOf(year)+monthtemp+datetemp;
                getwebservice();
            }


        } catch (Exception e) {
        }

    }

    @Override
    public void onLocationChanged(Location location) {
        try {
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
            if (location != null) {
                mCurrentLocation = location;
            }
        } catch (Exception e) {
        }
    }

    public void showSnackBar(int resId) {
        snackbar = Snackbar.make(layout, resId,
                Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            if (connectivityReceiver != null) {
                unregisterReceiver(connectivityReceiver);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
