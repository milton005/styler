package com.richie.styler.UI.HOME;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.applozic.mobicomkit.api.account.user.UserLogoutTask;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.richie.styler.R;
import com.richie.styler.UI.Base.BaseActivity;
import com.richie.styler.UI.Landing.LandingActivity;
import com.richie.styler.UI.Models.Profile.ModelProfile;
import com.richie.styler.UI.Models.update_profile.ModelUpdateProfile;
import com.richie.styler.UTILS.CircleTransform;
import com.richie.styler.UTILS.Constants;
import com.richie.styler.UTILS.Retrofit.APIService;
import com.richie.styler.UTILS.Retrofit.ServiceGenerator;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by User on 07-04-2017.
 */

public class SettingsActivity extends BaseActivity {
    LinearLayout Logout;
    private FirebaseAnalytics mFirebaseAnalytics;
    ImageView back;
    boolean switchtemp;
    LinearLayout passwordlayout;
    TextView head;
    LinearLayout switchlayout;
    Switch aSwitch;
    ImageView profile_image;
    boolean isfb=false;;
    boolean isonline;
    LinearLayout online,offline;
    TextView textOnline,textoffline;
    TextView mystatus;
    String oline;
    private UserLogoutTask userLogoutTask;
    TextView textViewimage,textViewprofiledit,textViewchangepass,textViewgoOffline,textViewHide,textViewApppreferences,textViewPrivacy,textViewlogout,textviewterms;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_layout);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        initui();
        getWebservice();
    }

    private void getWebservice() {
        showProgress();
        HashMap<String, String> params = new HashMap<>();
        String userid=getPref(Constants.USER_ID);
        params.put("user_id",userid);
        APIService service= ServiceGenerator.createService(APIService.class,SettingsActivity.this);
        Call<ModelProfile>call=service.getprofile(params);
        call.enqueue(new Callback<ModelProfile>() {
            @Override
            public void onResponse(Call<ModelProfile> call, Response<ModelProfile> response) {
                if (response.isSuccessful()&&response.body()!=null)
                {
                    hideProgress();
                    ModelProfile profile=response.body();
                   oline=profile.getUser().get(0).getVisibilityStatus();
                    if (oline.equals("1"))
                    {
//                        aSwitch.setChecked(false);
//                        isonline=true;
                        online.setBackgroundColor(getResources().getColor(R.color.black));
                        textOnline.setTextColor(getResources().getColor(R.color.white));
                        offline.setBackgroundColor(getResources().getColor(R.color.white));
                        textoffline.setTextColor(getResources().getColor(R.color.black));

                    }
                    if (oline.equals("0"))
                    {
                        online.setBackgroundColor(getResources().getColor(R.color.white));
                        textOnline.setTextColor(getResources().getColor(R.color.black));
                        offline.setBackgroundColor(getResources().getColor(R.color.black));
                        textoffline.setTextColor(getResources().getColor(R.color.white));
//                        aSwitch.setChecked(true);
//                        isonline=false;
                    }
                    else
                    {
                        online.setBackgroundColor(getResources().getColor(R.color.black));
                        textOnline.setTextColor(getResources().getColor(R.color.white));
                        offline.setBackgroundColor(getResources().getColor(R.color.white));
                        textoffline.setTextColor(getResources().getColor(R.color.black));
//                        aSwitch.setChecked(false);
//                        isonline=true;
                    }
                    String imagePath=profile.getUser().get(0).getUserPhoto();
                    savePref(Constants.IMAGE_PATH,imagePath);
                    if (!imagePath.isEmpty())
                    {
                        Picasso.with(SettingsActivity.this).load(imagePath).transform(new CircleTransform()).placeholder(R.drawable.cmra1).into(profile_image);

                    }
                    String name=profile.getUser().get(0).getUsername();
                    if (!name.isEmpty())
                    {
                        textViewimage.setText(name);
                    }


                }
            }

            @Override
            public void onFailure(Call<ModelProfile> call, Throwable t) {
                hideProgress();

            }
        });
    }

    private void initui() {
        Logout= (LinearLayout) findViewById(R.id.logout);
        back= (ImageView) findViewById(R.id.back);
        switchlayout= (LinearLayout) findViewById(R.id.switchlayout);
        aSwitch= (Switch) findViewById(R.id.switchoffline);
        aSwitch.setClickable(false);
        textviewterms= (TextView) findViewById(R.id.textTerms);
        online= (LinearLayout) findViewById(R.id.layout_online);
        offline= (LinearLayout) findViewById(R.id.layout_offline);
        textoffline= (TextView) findViewById(R.id.textoffline);
        textOnline= (TextView) findViewById(R.id.textonline);
        mystatus= (TextView) findViewById(R.id.mystatus);


        head= (TextView) findViewById(R.id.text_head_Settings);
        profile_image= (ImageView) findViewById(R.id.profileimage);
        Picasso.with(SettingsActivity.this).load(R.drawable.cmra1).into(profile_image);
        passwordlayout= (LinearLayout) findViewById(R.id.password_layout);
        isfb=getPref(Constants.FBREGISTRATION);
        if (isfb)
        {
            passwordlayout.setVisibility(View.GONE);
        }


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        textViewimage= (TextView) findViewById(R.id.imagetext);
        textViewApppreferences= (TextView) findViewById(R.id.textApppreference);

        textViewchangepass= (TextView) findViewById(R.id.textchangePassword);
        textViewgoOffline= (TextView) findViewById(R.id.textGooffline);
        textViewApppreferences= (TextView) findViewById(R.id.textApppreference);
        textViewHide= (TextView) findViewById(R.id.texthide);
        textViewprofiledit= (TextView) findViewById(R.id.textprofileEditor);
        textViewlogout= (TextView) findViewById(R.id.textlogout);
        textViewPrivacy= (TextView) findViewById(R.id.textprivacy);
        Typeface typeface=Typeface.createFromAsset(getAssets(),"fonts/brandon_grotesque_bold.ttf");
        Typeface typeface1=Typeface.createFromAsset(getAssets(),"fonts/BrandonGrotesque-Regular.ttf");
        textViewimage.setTypeface(typeface);
        textoffline.setTypeface(typeface1);
        textOnline.setTypeface(typeface1);

        textViewHide.setTypeface(typeface);
        textViewgoOffline.setTypeface(typeface);
        textViewlogout.setTypeface(typeface);
        textViewPrivacy.setTypeface(typeface);
        textViewprofiledit.setTypeface(typeface);
        textViewApppreferences.setTypeface(typeface);
        textViewApppreferences.setTypeface(typeface);
        textViewchangepass.setTypeface(typeface);
        textviewterms.setTypeface(typeface);
        mystatus.setTypeface(typeface);
//        switchlayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (aSwitch.isChecked())
//                {
//                    aSwitch.setChecked(false);
//                    Setonline();
//
//                }
//                else
//                {
//
//                    aSwitch.setChecked(true);
//                    Setonline();
//                }
//
//            }
//        });
        offline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!oline.equals("0")) {


                    Spanned s = Html.fromHtml(getString(R.string.going_offline));
                    new MaterialDialog.Builder(SettingsActivity.this)
                            .titleGravity(GravityEnum.CENTER)
                            .contentGravity(GravityEnum.CENTER)
                            .buttonsGravity(GravityEnum.CENTER)
                            .title("Going Offline")
                            .titleColor(getResources().getColor(R.color.black))
                            .backgroundColor(getResources().getColor(R.color.white_transparent))
                            .content(s)
                            .positiveText("Go Offline")
                            .contentColor(getResources().getColor(R.color.black))
                            .positiveColor(Color.RED)
                            .negativeText("Cancel")
                            .negativeColor(Color.BLUE)
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    Setonline(0);
                                    oline="0";
                                    online.setBackgroundColor(getResources().getColor(R.color.white));
                                    textOnline.setTextColor(getResources().getColor(R.color.black));
                                    offline.setBackgroundColor(getResources().getColor(R.color.black));
                                    textoffline.setTextColor(getResources().getColor(R.color.white));
                                }
                            })
                            .onNegative(null)
                            .show();
//                new AlertDialog.Builder(SettingsActivity.this,R.style.AppCompatAlertDialogStyle)
//                        .setTitle(Html.fromHtml(getString(R.string.goofflinehead)))
//                        .setMessage(s)
//                        .setPositiveButton("Go Offline", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//
//                            }
//                        })
//                        .setNegativeButton("Cancel",null)
//                        .show();


                }
            }
        });
        online.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!oline.equals("1")) {
                    Spanned s = Html.fromHtml(getString(R.string.going_online));
                    new MaterialDialog.Builder(SettingsActivity.this)
                            .titleGravity(GravityEnum.CENTER)
                            .contentGravity(GravityEnum.CENTER)
                            .buttonsGravity(GravityEnum.CENTER)
                            .title("Going Online")
                            .titleColor(getResources().getColor(R.color.black))
                            .backgroundColor(getResources().getColor(R.color.white_transparent))
                            .content(s)
                            .positiveText("Go Online")
                            .contentColor(getResources().getColor(R.color.black))
                            .positiveColor(Color.RED)
                            .negativeText("Cancel")
                            .negativeColor(Color.BLUE)
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    online.setBackgroundColor(getResources().getColor(R.color.black));
                                    textOnline.setTextColor(getResources().getColor(R.color.white));
                                    offline.setBackgroundColor(getResources().getColor(R.color.white));
                                    textoffline.setTextColor(getResources().getColor(R.color.black));
                                    Setonline(1);
                                    oline="1";
                                }
                            })
                            .onNegative(null)
                            .show();

                }
            }
        });

        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(SettingsActivity.this)
                        .titleGravity(GravityEnum.CENTER)
                        .contentGravity(GravityEnum.CENTER)
                        .buttonsGravity(GravityEnum.CENTER)
                        .title("Logging out")
                        .titleColor(getResources().getColor(R.color.black))
                        .backgroundColor(getResources().getColor(R.color.white_transparent))
                        .content("Are you sure you want to logout?")
                        .positiveText("Logout")
                        .contentColor(getResources().getColor(R.color.black))
                        .positiveColor(Color.RED)
                        .negativeText("Cancel")
                        .negativeColor(Color.BLUE)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                Setonline1(0);
                                String userid=getPref(Constants.USER_ID);
                                Bundle bundle = new Bundle();
                                bundle.putString("user_id",userid );
                                bundle.putString("event_type", "logout event");
//                                bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "image");

                                mFirebaseAnalytics.logEvent("logout", bundle);
                                savePref(Constants.TAG_ISLOGGED_IN,false);
                                savePref(Constants.USER_ID,"");
                                UserLogoutTask.TaskListener userLogoutTaskListener = new UserLogoutTask.TaskListener(){

                                    @Override
                                    public void onSuccess(Context context) {
                                        userLogoutTask = null;


                                    }

                                    @Override
                                    public void onFailure(Exception exception) {
                                        userLogoutTask = null;

//                                        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
//                                        alertDialog.setTitle(getString(R.string.text_alert));
//                                        alertDialog.setMessage(exception.toString());
//                                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, getString(android.R.string.ok),
//                                                new DialogInterface.OnClickListener() {
//                                                    public void onClick(DialogInterface dialog, int which) {
//                                                        dialog.dismiss();
//                                                    }
//                                                });
//                                        if (!isFinishing()) {
//                                            alertDialog.show();
//                                        }
                                    }
                                };

                                userLogoutTask = new UserLogoutTask(userLogoutTaskListener,SettingsActivity.this);
                                userLogoutTask.execute((Void)null);

//                                savePref(Constants.USER_ID,"");
                                Intent intent  =  new Intent(SettingsActivity.this,LandingActivity.class);

//                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                startActivity(intent);
                                finish();

                            }
                        })
                        .onNegative(null)
                        .show();
//                new AlertDialog.Builder(SettingsActivity.this,R.style.AppCompatAlertDialogStyle)
//                        .setTitle("")
//                        .setMessage("Are you sure ?")
//                        .setPositiveButton("LOGOUT", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//
//
//                            }
//                        })
//                        .setNegativeButton("CANCEL",null)
//                        .show();
            }
        });
        textViewprofiledit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SettingsActivity.this,ProfileEditorActivity.class);

                startActivity(intent);
            }
        });
        textViewchangepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SettingsActivity.this,ChangePasswordActivity.class);
                startActivity(intent);


            }
        });
//        textViewApppreferences.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(SettingsActivity.this,AppPreferenceActivity.class);
//                startActivity(intent);
//            }
//        });

        textViewPrivacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SettingsActivity.this,PrivacyActivity.class);
                startActivity(intent);
            }
        });
        textViewHide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itent=new Intent(SettingsActivity.this,HideBlockUsersActivity.class);
                startActivity(itent);
            }
        });
        textviewterms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SettingsActivity.this,TermsAndConditionsActivity.class);
                startActivity(intent);
            }
        });

    }
    @Override
    public void onRestart() {
        super.onRestart();

            getWebservice();

        //When BACK BUTTON is pressed, the activity on the stack is restarted
        //Do what you want on the refresh procedure here
    }
    private void Setonline1(int online) {
//        showProgress();

        HashMap<String, String> params = new HashMap<>();
        String userid = getPref(Constants.USER_ID);
        if (online==0)
        {
            params.put("online_status","0");
            switchtemp=true;
        }
        if (online==1){
            params.put("online_status","1");
            switchtemp=false;
        }
        params.put("user_id", userid);
        APIService service=ServiceGenerator.createService(APIService.class,SettingsActivity.this);
        Call<ModelUpdateProfile>call=service.updateprofile(params);
        call.enqueue(new Callback<ModelUpdateProfile>() {
            @Override
            public void onResponse(Call<ModelUpdateProfile> call, Response<ModelUpdateProfile> response) {
                if (response.isSuccessful()) {
//                    hideProgress();
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
//                            showToast(msg, false);
                        }

                    }
                }
            }

            @Override
            public void onFailure(Call<ModelUpdateProfile> call, Throwable t) {
                hideProgress();

            }
        });


    }


    private void Setonline(int online) {
        showProgress();

        HashMap<String, String> params = new HashMap<>();
        String userid = getPref(Constants.USER_ID);
        if (online==0)
        {
            params.put("visibility_status","0");
            switchtemp=true;
        }
        if (online==1){
            params.put("visibility_status","1");
            switchtemp=false;
        }
        params.put("user_id", userid);
         APIService service=ServiceGenerator.createService(APIService.class,SettingsActivity.this);
        Call<ModelUpdateProfile>call=service.updateprofile(params);
        call.enqueue(new Callback<ModelUpdateProfile>() {
            @Override
            public void onResponse(Call<ModelUpdateProfile> call, Response<ModelUpdateProfile> response) {
                if (response.isSuccessful()) {
                    hideProgress();
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
                            showToast(msg, false);
                        }

                    }
                }
            }

            @Override
            public void onFailure(Call<ModelUpdateProfile> call, Throwable t) {
                hideProgress();

            }
        });


    }
}

