package com.richie.styler.App;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.multidex.MultiDexApplication;
import android.util.Log;


import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.richie.styler.UI.Models.update_profile.ModelUpdateProfile;
import com.richie.styler.UTILS.Constants;


import com.richie.styler.UTILS.Retrofit.APIService;
import com.richie.styler.UTILS.Retrofit.Foreground;
import com.richie.styler.UTILS.Retrofit.ServiceGenerator;
import com.richie.styler.UTILS.Util;


import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by User on 01-03-2017.
 */

public class Styler extends MultiDexApplication implements SharedPreferences.OnSharedPreferenceChangeListener{
    private static final String TAG = "STyler";
    public static Styler instance;
    boolean isLoggedin;
    String Userid;
    private static SharedPreferences sharedPreferences;
    @Override
    public void onCreate() {
        super.onCreate();
//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            // This process is dedicated to LeakCanary for heap analysis.
//            // You should not init your app in this process.
//            return;
//        }
//        LeakCanary.install(this);
        initApplication();
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        new Util(this);
        Foreground.init(this);
        Foreground.get(this).addListener(myListener);
       isLoggedin =Util.getUtils().getPref(Constants.TAG_ISLOGGED_IN,false);
        if (isLoggedin)
        {
            Userid=Util.getUtils().getPref(Constants.USER_ID);
        }
        String prefsFile = getApplicationContext().getPackageName();
        sharedPreferences = getApplicationContext().getSharedPreferences(prefsFile, Context.MODE_PRIVATE);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }
    Foreground.Listener myListener = new Foreground.Listener() {
        public void onBecameForeground() {
//            boolean isLoggedin=Util.getUtils().getPref(Constants.TAG_ISLOGGED_IN);
            if (isLoggedin)
            {

                HashMap<String, String> params = new HashMap<>();
                params.put("user_id",Userid);
                params.put("online_status","1");
                final APIService service = ServiceGenerator.createService(APIService.class, Styler.this);
                Call<ModelUpdateProfile> call=service.updateprofile(params);
                call.enqueue(new Callback<ModelUpdateProfile>() {
                    @Override
                    public void onResponse(Call<ModelUpdateProfile> call, Response<ModelUpdateProfile> response) {
                        if (response.isSuccessful()) {

                            if (response.body() != null && response.body().getResult() != null) {
                                ModelUpdateProfile modelUpdateProfile = response.body();
                                int value = modelUpdateProfile.getResult().getValue();
                                if (value == 1) {
                                    String msg = modelUpdateProfile.getResult().getMessage();
//                            showToast(msg, true);
//                            if (switchtemp)
//                            {
//                                aSwitch.setChecked(true);
//                            }
//                            else {
//                                aSwitch.setChecked(false);
//                            }

                                } else {
                                    String msg = modelUpdateProfile.getResult().getMessage();

                                }

                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ModelUpdateProfile> call, Throwable t) {


                    }
                });
            }

            Log.e(TAG, "Foreground!");
        }

        public void onBecameBackground() {

            if (isLoggedin)
            {
//                context.startService(new Intent(context, ReminderService.class));

                HashMap<String, String> params = new HashMap<>();
                params.put("user_id",Userid);
                params.put("online_status","0");
                final APIService service = ServiceGenerator.createService(APIService.class, Styler.this);
                Call<ModelUpdateProfile> call=service.updateprofile(params);
                call.enqueue(new Callback<ModelUpdateProfile>() {
                    @Override
                    public void onResponse(Call<ModelUpdateProfile> call, Response<ModelUpdateProfile> response) {
                        if (response.isSuccessful()) {

                            if (response.body() != null && response.body().getResult() != null) {
                                ModelUpdateProfile modelUpdateProfile = response.body();
                                int value = modelUpdateProfile.getResult().getValue();
                                if (value == 1) {
                                    String msg = modelUpdateProfile.getResult().getMessage();
//                            showToast(msg, true);
//                            if (switchtemp)
//                            {
//                                aSwitch.setChecked(true);
//                            }
//                            else {
//                                aSwitch.setChecked(false);
//                            }

                                } else {
                                    String msg = modelUpdateProfile.getResult().getMessage();

                                }

                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ModelUpdateProfile> call, Throwable t) {


                    }
                });
           }

            Log.e(TAG, "Background!");
        }
    };


    private void initApplication() {
        instance = this;
//        ApplozicClient.getInstance(getApplicationContext()).enableNotification();
        initQb();


    }
//    @Override
//    public void onDestroy(){
//        super.onCreate();
//        Foreground.get(this).removeListener(myListener);
//    }




    private void initQb() {

    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
isLoggedin=Util.getUtils().getPref(Constants.TAG_ISLOGGED_IN,false);
Userid=Util.getUtils().getPref(Constants.USER_ID);

    }
}
