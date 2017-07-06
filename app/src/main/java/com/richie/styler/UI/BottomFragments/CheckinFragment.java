package com.richie.styler.UI.BottomFragments;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.richie.styler.R;
import com.richie.styler.UI.Adapter.CheckinAdapter;
import com.richie.styler.UI.Base.BaseFragment;
import com.richie.styler.UI.Models.Checkin.ModelCheckinnn;
import com.richie.styler.UI.Models.Checkin.Venue;
import com.richie.styler.UI.Models.FoursquareVenue;
import com.richie.styler.UI.Models.ModelCheckin;
import com.richie.styler.UI.Models.Venues.ModelVenue;
import com.richie.styler.UI.Models.Venues.User;
import com.richie.styler.UTILS.Constants;
import com.richie.styler.UTILS.ItemClickSupport;
import com.richie.styler.UTILS.Retrofit.APIService;
import com.richie.styler.UTILS.Retrofit.ServiceGenerator;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
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

/**
 * Created by User on 22-05-2017.
 */

public class CheckinFragment extends BaseFragment implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener ,LocationListener {
    private LocationRequest locationRequest;
    Location mCurrentLocation;
    String Lat="",Long="";
    SwipeRefreshLayout refreshLayout;
    List<User>user;
    final String CLIENT_ID = "ZNO2KW4ZB245I0MSU1F24JQ412SXUSFHE4OPBTM1SRJUFGAM";
    final String CLIENT_SECRET = "DC4HETXW2A4Z2WJZTZJKLUYONUCCPBR4EHGTGC4SOZCNL1L4";
    String date;
    TextView textView;
    String Latlng;
    ArrayList venuesList;
    RecyclerView recyclerView;
    CheckinAdapter adapter;
 public    ArrayList<FoursquareVenue >list1;
    public List<Venue>venuelist;
    public List<com.richie.styler.UI.Models.Venues.Venue>venues;
    public ArrayList <ModelCheckin>list;
    private static final long FASTEST_INTERVAL1 = 1000 * 5;
    protected static final long UPDATE_INTERVAL = 5;
    private static final int LOCATION_SERVICE_ENABLE =1001 ;
    GoogleApiClient mGoogleApiClient;
    StringBuilder builder;
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
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.checkin_layout, null);
        list1=new ArrayList<>();
        list=new ArrayList<>();
        venuelist=new ArrayList<>();
        initUi(v);
        venues=new ArrayList<>();
        user=new ArrayList<>();
        hideProgress();
        setAdapter();
        setclicks();
        return v;
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {

            getwebservice();

            // Refresh your fragment here
        }
    }
    private void setclicks() {
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getwebservice();
            }
        });
        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                if (venuelist.size()>0) {
                  String checkedinnum=list.get(position).getCheckedinNumber();
                    String temp="";
                    if (checkedinnum!=null)
                    {
                        temp=checkedinnum;

                    }
                    else
                    {
                        temp="0";
                    }
                   String name = list.get(position).getName();
                    String imagepath=list.get(position).getImagePath();
                    String dist = list.get(position).getDistance();
                    int distance=Integer.parseInt(dist);
                    String lat=list.get(position).getLat();
                    String lng=list.get(position).getLng();
                    String country=list.get(position).getCountry();
                    Intent intent=new Intent(getActivity(),CheckinDetailActivity.class);
                    intent.putExtra("name",name);
                    intent.putExtra("checkedinnum",temp);
                    intent.putExtra("imagepath",imagepath);
                    intent.putExtra("distance",distance);
                    intent.putExtra("lat",lat);
                    intent.putExtra("lng",lng);
                    intent.putExtra("country",country);
                    startActivity(intent);
                }
            }
        });
    }

    private void setAdapter() {
        adapter=new CheckinAdapter(getActivity(),list);
        recyclerView.setAdapter(adapter);
    }

    private void initUi(View v) {
        recyclerView= (RecyclerView) v.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        refreshLayout= (SwipeRefreshLayout) v.findViewById(R.id.swiperefreshlayout);
        final LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        textView= (TextView) v.findViewById(R.id.text_head_Settings);
        Typeface typeface=Typeface.createFromAsset(getContext().getAssets(),"fonts/brandon_grotesque_bold.ttf");
        Typeface typeface1=Typeface.createFromAsset(getContext().getAssets(),"fonts/BrandonGrotesque-Regular.ttf");
        textView.setTypeface(typeface);
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
            Latlng=Lat+","+Long;
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
//            Toast.makeText(getActivity(),date,Toast.LENGTH_LONG).show();
            getwebservice();

//           showToast(String.valueOf(mLastLocation.getLatitude()),true);
        }
    }
  public void   getwebservice()
    {
        Activity activity = getActivity();
        if(activity != null && isAdded())
        {
            showProgress();
        }
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        HashMap<String, String> params = new HashMap<>();
        params.put("client_id",CLIENT_ID);
        params.put("client_secret",CLIENT_SECRET);
        params.put("ll",Latlng);
        params.put("v",date);
        APIService service = new Retrofit.Builder().baseUrl("https://api.foursquare.com/v2/")  .addConverterFactory(GsonConverterFactory.create()).client(client).build().create(APIService.class);
        Call<ModelCheckinnn>call=service.getvenues(params);
        call.enqueue(new Callback<ModelCheckinnn>() {
            @Override
            public void onResponse(Call<ModelCheckinnn> call, Response<ModelCheckinnn> response) {
                if (response.isSuccessful()) {


                        ModelCheckinnn m = response.body();
                        if (response.body() != null) {
                            venuelist.clear();

                            venuelist = response.body().getResponse().getVenues();

                            if (venuelist.size() > 0) {
                                builder=new StringBuilder();
                                list.clear();
                                for (int i = 0; i < venuelist.size(); i++) {
                                    String name = venuelist.get(i).getName();
                                    builder.append(name);
                                    builder.append("@");
                                    int distance = venuelist.get(i).getLocation().getDistance();
                                    String prefix="";
                                    String postfix="";
                                    if (venuelist.get(i).getCategories().size()>0) {
                                   prefix     = venuelist.get(i).getCategories().get(0).getIcon().getPrefix();
                                   postfix     = venuelist.get(i).getCategories().get(0).getIcon().getSuffix();
                                    }
                                    String lat=String.valueOf(venuelist.get(i).getLocation().getLat());
                                    String lng=String.valueOf(venuelist.get(i).getLocation().getLng());
                                    String country=String.valueOf(venuelist.get(i).getLocation().getCountry());
                                    ModelCheckin mc = new ModelCheckin();
                                    mc.setName(name);
                                    mc.setCheckedinNumber("");
                                    mc.setDistance(String.valueOf(distance));
                                    String path = prefix + "bg_64" + postfix;
                                    mc.setImagePath(path);
                                    mc.setCountry(country);
                                    mc.setLat(lat);
                                    mc.setLng(lng);

                                    list.add(mc);
                                }
                                getwebservice1();
//                                adapter.notifyDataSetChanged();
                            }

                        }



                }

            }

            @Override
            public void onFailure(Call<ModelCheckinnn> call, Throwable t) {
                Activity activity = getActivity();
                if(activity != null && isAdded())
                {
                    hideProgress();
                }
                if (refreshLayout.isRefreshing()) {
                    refreshLayout.setRefreshing(false);
                }
            }
        });

    }

    private void getwebservice1() {
        String temp=builder.toString();
        if (!temp.isEmpty())
        {
            temp=temp.substring(0,temp.lastIndexOf("@"));

        }
        final HashMap<String, String> params = new HashMap<>();
        if ((!temp.isEmpty()))
        {
            params.put("nearbylocations",temp);
        }
        APIService service= ServiceGenerator.createService(APIService.class,getActivity());
        Call<ModelVenue>call=service.getnearby(params);
        call.enqueue(new Callback<ModelVenue>() {
            @Override
            public void onResponse(Call<ModelVenue> call, Response<ModelVenue> response) {
                if (response.isSuccessful())
                {

                    Activity activity = getActivity();
                    if(activity != null && isAdded())
                    {
                        hideProgress();
                    }
                    if (response.body()!=null&&response.body().getVenue()!=null)
                    {
                        ModelVenue venue=response.body();
                        venues.clear();
                        venues=venue.getVenue();
                        for (int i=0;i<venues.size();i++)
                        {
                            if (venues.get(i).getTotal()>0)
                            {
                                list.get(i).setCheckedinNumber(String.valueOf(venues.get(i).getTotal()));
                                user=venues.get(i).getUsers().getUser();
                                StringBuilder builder=new StringBuilder();
                                for (int k=0;k<user.size();k++)
                                {
                                   builder.append(user.get(k).getUserPhoto());
                                    builder.append(",");
                                }
                                String temp=builder.toString();
                                String userphotos=temp.substring(0,temp.lastIndexOf(","));
                                list.get(i).setUserphotos(userphotos);





                            }
                            else
                            {
                                list.get(i).setCheckedinNumber(String.valueOf(0));
                            }
                        }
                        Collections.sort(list, new Comparator<ModelCheckin>() {
                            @Override
                            public int compare(ModelCheckin o1, ModelCheckin o2) {
                                return o1.getCheckedinNumber().compareTo(o2.getCheckedinNumber());
                            }
                        });
                        Collections.reverse(list);
                       adapter.notifyDataSetChanged();
                        if (refreshLayout.isRefreshing()) {
                            refreshLayout.setRefreshing(false);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ModelVenue> call, Throwable t) {
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



    }
//private class Foursquare extends AsyncTask{
//    String temp;
//    @Override
//    protected Object doInBackground(Object[] params) {
//        temp = makeCall("https://api.foursquare.com/v2/venues/search?client_id=" + CLIENT_ID + "&client_secret=" + CLIENT_SECRET + "&v="+date+"&ll="+Latlng);
//        return "";
//    }
//
//    @Override
//    protected void onPreExecute() {
//        super.onPreExecute();
//    }
//
//    @Override
//    protected void onPostExecute(Object o) {
//        super.onPostExecute(o);
//        if (temp == null) {
//            // we have an error to the call
//            // we can also stop the progress bar
//        } else {
//            // all things went right
//
//            // parseFoursquare venues search result
//
//            int q=parseFoursquare(temp);
//            if (q==1)
//            {
//                for (int i=0;i<list1.size();i++)
//                {
//                    ModelCheckin modelCheckin=new ModelCheckin();
//                    modelCheckin.setName(list1.get(i).getName());
//                    modelCheckin.setDistance(list1.get(i).getDistance());
//                    String prefix=list1.get(i).getPrefix();
//                    String postfix=list1.get(i).getPostfix();
//                    String imagepath=prefix+"bg_64"+postfix;
//                    modelCheckin.setImagePath(imagepath);
//                    list.add(modelCheckin);
//
//                }
//            }
//            adapter.notifyDataSetChanged();
//
//
//
//            // set the results to the list
//            // and show them in the xml
////            myAdapter = new ArrayAdapter(AndroidFoursquare.this, R.layout.row_layout, R.id.listText, listTitle);
////            setListAdapter(myAdapter);
//        }
//    }
//}
//  public int    parseFoursquare(final String response) {
//
//        ArrayList temp = new ArrayList();
//        try {
//
//            // make an jsonObject in order to parse the response
//            JSONObject jsonObject = new JSONObject(response);
//
//            // make an jsonObject in order to parse the response
//            if (jsonObject.has("response")) {
//                if (jsonObject.getJSONObject("response").has("venues")) {
//                    JSONArray jsonArray = jsonObject.getJSONObject("response").getJSONArray("venues");
//
//                    for (int i = 0; i<jsonArray.length();i++) {
//                        FoursquareVenue f=new FoursquareVenue();
//                        if (jsonArray.getJSONObject(i).has("name"))
//                        {
//                            f.setName(jsonArray.getJSONObject(i).getString("name"));
//
//                        }
//                        if (jsonArray.getJSONObject(i).getJSONArray("categories").getJSONObject(0).has("icon")) {
//                            f.setPrefix(jsonArray.getJSONObject(i).getJSONArray("categories").getJSONObject(0).getJSONObject("icon").getString("prefix"));
//                            f.setPostfix(jsonArray.getJSONObject(i).getJSONArray("categories").getJSONObject(0).getJSONObject("icon").getString("postfix"));
//                        }
//                        if (jsonArray.getJSONObject(i).has("distance"))
//                        {
//                            f.setDistance(jsonArray.getJSONObject(i).getString("distance"));
//
//                        }
//                        list1.add(f);
//
//                    }
//
//
//
//                }
//
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return 1;
//    }
//
//
//    public static String makeCall(String url) {
//
//        // string buffers the url
//        StringBuffer buffer_string = new StringBuffer(url);
//        String replyString = "";
//
//        // instanciate an HttpClient
//        HttpClient httpclient = new DefaultHttpClient();
//        // instanciate an HttpGet
//        HttpGet httpget = new HttpGet(buffer_string.toString());
//
//        try {
//            // get the responce of the httpclient execution of the url
//            HttpResponse response = httpclient.execute(httpget);
//            InputStream is = response.getEntity().getContent();
//
//            // buffer input stream the result
//            BufferedInputStream bis = new BufferedInputStream(is);
//            ByteArrayBuffer baf = new ByteArrayBuffer(20);
//            int current = 0;
//            while ((current = bis.read()) != -1) {
//                baf.append((byte) current);
//            }
//            // the result as a string is ready for parsing
//            replyString = new String(baf.toByteArray());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        // trim the whitespaces
//        return replyString.trim();
//    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {

    }
}
