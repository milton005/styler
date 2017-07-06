package com.richie.styler.UI.Splash;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.applozic.audiovideo.activity.AudioCallActivityV2;
import com.applozic.audiovideo.activity.VideoActivity;
import com.applozic.mobicomkit.Applozic;
import com.applozic.mobicomkit.ApplozicClient;
import com.applozic.mobicomkit.api.account.register.RegistrationResponse;
import com.applozic.mobicomkit.api.account.user.PushNotificationTask;
import com.applozic.mobicomkit.api.account.user.User;
import com.applozic.mobicomkit.api.account.user.UserLoginTask;
import com.applozic.mobicomkit.api.conversation.database.MessageDatabaseService;
import com.applozic.mobicomkit.uiwidgets.ApplozicSetting;
import com.richie.styler.R;
import com.richie.styler.UI.Base.BaseActivity;
import com.richie.styler.UI.HOME.HomeActivity;
import com.richie.styler.UI.Landing.LandingActivity;
import com.richie.styler.UTILS.Constants;
import com.richie.styler.UTILS.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by User on 01-03-2017.
 */

public class SplashActivity extends BaseActivity {
    private static final long TAG_TIMER_DELAY = 2000;
    private UserLoginTask mAuthTask = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashlayoutactivity);
        initializeTimerTask();
    }
    private void initializeTimerTask() {



        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if (getPref(Constants.TAG_ISLOGGED_IN, false)) {

                    UserLoginTask.TaskListener listener=new UserLoginTask.TaskListener() {
                        @Override
                        public void onSuccess(RegistrationResponse registrationResponse, Context context) {

                            ApplozicClient.getInstance(context).setContextBasedChat(true).setHandleDial(true).setIPCallEnabled(true);
                            Map<ApplozicSetting.RequestCode, String> activityCallbacks = new HashMap<ApplozicSetting.RequestCode, String>();
                            activityCallbacks.put(ApplozicSetting.RequestCode.AUDIO_CALL, AudioCallActivityV2.class.getName());
                            activityCallbacks.put(ApplozicSetting.RequestCode.VIDEO_CALL, VideoActivity.class.getName());
                            ApplozicSetting.getInstance(context).setActivityCallbacks(activityCallbacks);
                            ApplozicClient.getInstance(context).hideChatListOnNotification();
                            ApplozicClient.getInstance(context).showAppIconInNotification(true);

//                                    ApplozicSetting.getInstance(context).setChatBackgroundColorOrDrawableResource(R.drawable.bg_splash);
//                                    ApplozicSetting.getInstance(context).setSentMessageBackgroundColor(R.color.white);
//                                    ApplozicSetting.getInstance(context).setReceivedMessageBackgroundColor(R.color.sentmessage_black);
//                                    ApplozicSetting.getInstance(context).setSentMessageTextColor(R.color.black);
//                                    ApplozicSetting.getInstance(context).setReceivedMessageTextColor(R.color.white);
                            PushNotificationTask.TaskListener pushNotificationTaskListener=  new PushNotificationTask.TaskListener() {
                                @Override
                                public void onSuccess(RegistrationResponse registrationResponse) {
                                    int totalUnreadCount = new MessageDatabaseService(getBaseContext()).getTotalUnreadCount();
                                    Util.getUtils().savePref(Constants.TOTALUNREADCOUNT,totalUnreadCount);

//                                    Toast.makeText(getApplicationContext(),String.valueOf(totalUnreadCount),Toast.LENGTH_LONG).show();
                                }

                                @Override
                                public void onFailure(RegistrationResponse registrationResponse, Exception exception) {

                                }
                            };
                            PushNotificationTask pushNotificationTask = new PushNotificationTask(Applozic.getInstance(context).getDeviceRegistrationId(),pushNotificationTaskListener,context);
                            pushNotificationTask.execute((Void)null);
                            ApplozicClient.getInstance(context).enableNotification();
                        }

                        @Override
                        public void onFailure(RegistrationResponse registrationResponse, Exception exception) {

                        }
                    };
                    User user = new User();
                    String userid=getPref(Constants.USER_ID);
                    if (userid!=null) {
                        user.setUserId(userid);
                    }
                    String username=getPref(Constants.USERNAME);
                    if (username!=null)
                    {
                        user.setDisplayName(username);
                    }

                    user.setAuthenticationTypeId(User.AuthenticationType.APPLOZIC.getValue());
                    List<String> featureList =  new ArrayList<>();
                    featureList.add(User.Features.IP_AUDIO_CALL.getValue());// FOR AUDIO
                    featureList.add(User.Features.IP_VIDEO_CALL.getValue());// FOR VIDEO
                    user.setFeatures(featureList);
                    mAuthTask = new UserLoginTask(user, listener, SplashActivity.this);
                    mAuthTask.execute((Void) null);

                    Intent i = new Intent(SplashActivity.this, HomeActivity.class);
                 startActivity(i);
//                    MainActivity.start(SplashActivity.this);
                } else {
                   startActivity(new Intent(SplashActivity.this,LandingActivity.class));
//                    InitialActivity.start(SplashActivity.this);
                }
                finish();
            }


        }, TAG_TIMER_DELAY);
    }

}
