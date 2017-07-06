package com.richie.styler.UI.Landing;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.applozic.mobicomkit.api.account.user.UserLoginTask;
import com.richie.styler.R;
import com.richie.styler.UI.Base.BaseActivity;
import com.richie.styler.UI.HOME.HomeActivity;
import com.richie.styler.UI.Models.Login.ModelLogin;
import com.richie.styler.UI.POPUPS.PopUpForgotPassword;
import com.richie.styler.UTILS.Constants;
import com.richie.styler.UTILS.Retrofit.APIService;
import com.richie.styler.UTILS.Retrofit.ServiceGenerator;

import java.util.HashMap;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by User on 01-03-2017.
 */

public class LoginActivity extends BaseActivity {
    EditText Email,Password;
    LinearLayout Login;
    private UserLoginTask mAuthTask = null;
    private AlertDialog progressDialog;
    String Semail,Spass;
    MaterialDialog builder;
    RelativeLayout errorname,errorpassword;

    TextView Forgotpassword;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        initUI();


        SetUi();
    }

    private void SetUi() {
        Email.setFocusable(false);
        Password.setFocusable(false);
        Email.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                errorname.setVisibility(View.GONE);
                Email.setFocusableInTouchMode(true);

                return false;
            }
        });
        Password.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                errorpassword.setVisibility(View.GONE);
                Password.setFocusableInTouchMode(true);
                return false;
            }
        });
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkEmail()&&checkPassword())

                {
                    SubmitLogin();

                }
            }
        });
        Forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFragmentManager = getSupportFragmentManager();
                mFragmentTransaction = mFragmentManager.beginTransaction();
                PopUpForgotPassword popUpForgotPassword=PopUpForgotPassword.newInstance();
                popUpForgotPassword.show(mFragmentTransaction,"POPOPFORGOT");
            }
        });
    }

    private void SubmitLogin() {
//        final ProgressDialog pd = new ProgressDialog(this,R.style.MyTheme);
//        pd.setMessage("Loading");
//        pd.setCancelable(false);
//        pd.show();
//        showProgress();

//       builder= new MaterialDialog.Builder(this)
//               .backgroundColor(Color.TRANSPARENT)
//               .backgroundColorRes(R.color.thistle)
//                .content(R.string.please_wait)
//                .progress(true, 0)
//                .show();
//        progressDialog.show();
        showProgress();
        HashMap<String, String> params = new HashMap<>();
        params.put("username",Semail);
        params.put("password",Spass);
        final APIService service = ServiceGenerator.createService(APIService.class, LoginActivity.this);
        Call<ModelLogin>call=service.login(params);
        call.enqueue(new Callback<ModelLogin>() {
            @Override
            public void onResponse(Call<ModelLogin> call, Response<ModelLogin> response) {
                if (response.isSuccessful())
                {
//                    progressDialog.dismiss();
//                    builder.dismiss();
                    hideProgress();
                    if (response.body()!=null&&response.body().getResult()!=null)
                    {
                        ModelLogin modelLogin=response.body();
                        String status=modelLogin.getResult().get(0).getUserStatus();
                        if (status.equals("1"))
                        {
                            String malePreference=modelLogin.getResult().get(0).getMalePreference();
                            String femalepreference=modelLogin.getResult().get(0).getFemalePreference();
                            String userid=modelLogin.getResult().get(0).getUserId();
                            String username=modelLogin.getResult().get(0).getUsername();
                            String gender=modelLogin.getResult().get(0).getGender();
                            String imagepath=modelLogin.getResult().get(0).getUserPhoto();
//                            UserLoginTask.TaskListener listener=new UserLoginTask.TaskListener() {
//                                @Override
//                                public void onSuccess(RegistrationResponse registrationResponse, Context context) {
//
//                                    ApplozicClient.getInstance(context).setContextBasedChat(true).setHandleDial(true).setIPCallEnabled(true);
//                                    Map<ApplozicSetting.RequestCode, String> activityCallbacks = new HashMap<ApplozicSetting.RequestCode, String>();
//                                    activityCallbacks.put(ApplozicSetting.RequestCode.AUDIO_CALL, AudioCallActivityV2.class.getName());
//                                    activityCallbacks.put(ApplozicSetting.RequestCode.VIDEO_CALL, VideoActivity.class.getName());
//                                    ApplozicSetting.getInstance(context).setActivityCallbacks(activityCallbacks);
////                                    ApplozicSetting.getInstance(context).setChatBackgroundColorOrDrawableResource(R.drawable.bg_splash);
////                                    ApplozicSetting.getInstance(context).setSentMessageBackgroundColor(R.color.white);
////                                    ApplozicSetting.getInstance(context).setReceivedMessageBackgroundColor(R.color.sentmessage_black);
////                                    ApplozicSetting.getInstance(context).setSentMessageTextColor(R.color.black);
////                                    ApplozicSetting.getInstance(context).setReceivedMessageTextColor(R.color.white);
//                                    PushNotificationTask.TaskListener pushNotificationTaskListener=  new PushNotificationTask.TaskListener() {
//                                        @Override
//                                        public void onSuccess(RegistrationResponse registrationResponse) {
//
//                                        }
//
//                                        @Override
//                                        public void onFailure(RegistrationResponse registrationResponse, Exception exception) {
//
//                                        }
//                                    };
//                                    PushNotificationTask pushNotificationTask = new PushNotificationTask(Applozic.getInstance(context).getDeviceRegistrationId(),pushNotificationTaskListener,context);
//                                    pushNotificationTask.execute((Void)null);
//                                }
//
//                                @Override
//                                public void onFailure(RegistrationResponse registrationResponse, Exception exception) {
//
//                                }
//                            };
//                            User user = new User();
//                            user.setUserId(userid);
//                            user.setDisplayName(username);
//                            user.setAuthenticationTypeId(User.AuthenticationType.APPLOZIC.getValue());
//                            List<String> featureList =  new ArrayList<>();
//                            featureList.add(User.Features.IP_AUDIO_CALL.getValue());// FOR AUDIO
//                            featureList.add(User.Features.IP_VIDEO_CALL.getValue());// FOR VIDEO
//                            user.setFeatures(featureList);
//                            mAuthTask = new UserLoginTask(user, listener, LoginActivity.this);
//                            mAuthTask.execute((Void) null);
                            if (malePreference.equals("1")&&femalepreference.equals("1"))
                            {
                                savePref(Constants.ILIKE,"mf");
                            }
                            if (malePreference.equals("1")&&femalepreference.equals("0"))
                            {
                               savePref(Constants.ILIKE,"m");
                            }
                            if (malePreference.equals("0")&&femalepreference.equals("1"))
                            {
                                savePref(Constants.ILIKE,"f");
                            }
                            if (malePreference.equals("0")&&femalepreference.equals("0"))
                            {
                                savePref(Constants.ILIKE,"mf");
                            }
                            savePref(Constants.IMAGE_PATH,imagepath);
                            savePref(Constants.GENDER,gender);
                            savePref(Constants.USERNAME,username);
                            savePref(Constants.USER_ID,userid);
                            savePref(Constants.PASSWORD,Spass);
                            savePref(Constants.FBREGISTRATION,false);
                            savePref(Constants.EMAIL_REGISTRATION,true);
                            savePref(Constants.TAG_ISLOGGED_IN,true);
                            Intent intent=new Intent(LoginActivity.this,HomeActivity.class);
                            startActivity(intent);
                        }
                        else {
                            showToast("invalid username/email or password",false);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ModelLogin> call, Throwable t)
            {
//                builder.dismiss();
                hideProgress();
//                progressDialog.dismiss();
            }
        });
    }

    private boolean checkPassword() {
        Spass= Password.getText().toString().trim();
        if (Spass.isEmpty())
        {
//            Password.setError("Please enter a valid password");
//            showToast("Please enter a valid password");
            errorpassword.setVisibility(View.VISIBLE);
            Password.requestFocus();
            return  false;
        }
        else {
            return true;
        }

    }

    private boolean checkEmail() {
        Semail=Email.getText().toString().trim();
        if (Semail.isEmpty())
        {
//            showToast("Please enter valid username");
           errorname.setVisibility(View.VISIBLE);
//            Email.setError("Please enter valid username");
            Email.requestFocus();
            return false;
        }
        else {
            return true;
        }




    }

    private void initUI() {
        Email= (EditText) findViewById(R.id.editextloginEmail);
        Password= (EditText) findViewById(R.id.editextloginPassword);
        Login= (LinearLayout) findViewById(R.id.LOGIN_NOW_BUTTON);
        TextView logintext=(TextView)findViewById(R.id.logintext);
        Forgotpassword= (TextView) findViewById(R.id.forgotPasswordButton);
        Typeface typeface=Typeface.createFromAsset(getAssets(),"fonts/brandon_grotesque_bold.ttf");
        Typeface typeface1=Typeface.createFromAsset(getAssets(),"fonts/BrandonGrotesque-Regular.ttf");
        Email.setTypeface(typeface1);
        Password.setTypeface(typeface1);
        Forgotpassword.setTypeface(typeface);
        errorname= (RelativeLayout) findViewById(R.id.errornamelogin);
        logintext.setTypeface(typeface);
        errorpassword= (RelativeLayout) findViewById(R.id.errorpasswordlogin);

    }
}
