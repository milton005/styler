package com.richie.styler.UI.TabFragments;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.applozic.mobicomkit.api.MobiComKitConstants;
import com.applozic.mobicomkit.api.account.user.UserLoginTask;
import com.applozic.mobicomkit.api.conversation.MobiComConversationService;
import com.applozic.mobicomkit.api.conversation.database.MessageDatabaseService;
import com.applozic.mobicomkit.contact.AppContactService;
import com.applozic.mobicomkit.uiwidgets.ApplozicSetting;
import com.applozic.mobicomkit.uiwidgets.conversation.ConversationUIService;
import com.applozic.mobicomkit.uiwidgets.conversation.activity.ConversationActivity;
import com.applozic.mobicommons.people.channel.Channel;
import com.applozic.mobicommons.people.contact.Contact;
import com.richie.styler.R;
import com.richie.styler.UI.Adapter.GRIDWITHSTATUSADAPTER;
import com.richie.styler.UI.Base.BaseFragment;
import com.richie.styler.UI.Models.GridhomemodelwithStatus;
import com.richie.styler.UI.Models.Messages.ModelMessages;
import com.richie.styler.UI.Models.Messages.Receiveduser;
import com.richie.styler.UI.Models.Messages.Sentuser;
import com.richie.styler.UTILS.Constants;
import com.richie.styler.UTILS.ItemClickSupport;
import com.richie.styler.UTILS.Retrofit.APIService;
import com.richie.styler.UTILS.Retrofit.ServiceGenerator;
import com.richie.styler.UTILS.VisibilityListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by User on 03-04-2017.
 */

public class MessagesFragment extends BaseFragment {
    GRIDWITHSTATUSADAPTER adapter;
    private UserLoginTask mAuthTask = null;
    RecyclerView recyclerview;
    TextView whostarted;
    TextView notext;
    int selected=1;
    int unreadCount =0;
    private BroadcastReceiver unreadCountBroadcastReceiver;
    public ArrayList<GridhomemodelwithStatus>list;
    RecyclerView.LayoutManager mLayoutManager;
    LinearLayout me,someOneelse;
    List<Receiveduser>list1;
    List<Sentuser>list2;
    TextView textme,textsomeone;
    VisibilityListener mParentListener;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.messages_fragment_layout,null);
        initui(v);
        list1=new ArrayList<>();
        list2=new ArrayList<>();
        list=new ArrayList<>();
        setAdapter();
        setui();
//        getwebservice();
        final MessageDatabaseService messageDatabaseService =  new MessageDatabaseService(getContext());
        unreadCountBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (MobiComKitConstants.APPLOZIC_UNREAD_COUNT.equals(intent.getAction())) {
                    unreadCount  =  messageDatabaseService.getTotalUnreadCount();
                    getwebservice();

                    //Update unread count in UI
                }
            }
        };
        return v;
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try
        {
            if (getParentFragment() instanceof VisibilityListener) {
                mParentListener = (VisibilityListener) getParentFragment();
            } else {
                throw new RuntimeException(activity.toString()
                        + " must implement OnChildFragmentInteractionListener");
            }
        }catch (ClassCastException e)
        {

        }
    }
    @Override
    public void onResume() {
        super.onResume();
        getwebservice();

        LocalBroadcastManager.getInstance(getContext()).registerReceiver(unreadCountBroadcastReceiver, new IntentFilter(MobiComKitConstants.APPLOZIC_UNREAD_COUNT));
    }
    @Override
    public void onPause() {
        if(unreadCountBroadcastReceiver != null){
            LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(unreadCountBroadcastReceiver);
        }
        super.onPause();
        hideProgress();
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
//            UserLoginTask.TaskListener listener=new UserLoginTask.TaskListener() {
//                @Override
//                public void onSuccess(RegistrationResponse registrationResponse, Context context) {
//
//                    ApplozicClient.getInstance(context).setContextBasedChat(true).setHandleDial(true).setIPCallEnabled(true);
//                    Map<ApplozicSetting.RequestCode, String> activityCallbacks = new HashMap<ApplozicSetting.RequestCode, String>();
//                    activityCallbacks.put(ApplozicSetting.RequestCode.AUDIO_CALL, AudioCallActivityV2.class.getName());
//                    activityCallbacks.put(ApplozicSetting.RequestCode.VIDEO_CALL, VideoActivity.class.getName());
//                    ApplozicSetting.getInstance(context).setActivityCallbacks(activityCallbacks);
//                    ApplozicClient.getInstance(context).hideChatListOnNotification();
//                    ApplozicClient.getInstance(context).showAppIconInNotification(true);
//
////                                    ApplozicSetting.getInstance(context).setChatBackgroundColorOrDrawableResource(R.drawable.bg_splash);
////                                    ApplozicSetting.getInstance(context).setSentMessageBackgroundColor(R.color.white);
////                                    ApplozicSetting.getInstance(context).setReceivedMessageBackgroundColor(R.color.sentmessage_black);
////                                    ApplozicSetting.getInstance(context).setSentMessageTextColor(R.color.black);
////                                    ApplozicSetting.getInstance(context).setReceivedMessageTextColor(R.color.white);
//                    PushNotificationTask.TaskListener pushNotificationTaskListener=  new PushNotificationTask.TaskListener() {
//                        @Override
//                        public void onSuccess(RegistrationResponse registrationResponse) {
//                            int totalUnreadCount = new MessageDatabaseService(getActivity()).getTotalUnreadCount();
//                            Util.getUtils().savePref(Constants.TOTALUNREADCOUNT,totalUnreadCount);
//
////                                    Toast.makeText(getApplicationContext(),String.valueOf(totalUnreadCount),Toast.LENGTH_LONG).show();
//                        }
//
//                        @Override
//                        public void onFailure(RegistrationResponse registrationResponse, Exception exception) {
//
//                        }
//                    };
//                    PushNotificationTask pushNotificationTask = new PushNotificationTask(Applozic.getInstance(context).getDeviceRegistrationId(),pushNotificationTaskListener,context);
//                    pushNotificationTask.execute((Void)null);
//                    ApplozicClient.getInstance(context).enableNotification();
//                }
//
//                @Override
//                public void onFailure(RegistrationResponse registrationResponse, Exception exception) {
//
//                }
//            };
//            User user = new User();
//            String userid=getPref(Constants.USER_ID);
//            if (userid!=null) {
//                user.setUserId(userid);
//            }
//            String username=getPref(Constants.USERNAME);
//            if (username!=null)
//            {
//                user.setDisplayName(username);
//            }
//
//            user.setAuthenticationTypeId(User.AuthenticationType.APPLOZIC.getValue());
//            List<String> featureList =  new ArrayList<>();
//            featureList.add(User.Features.IP_AUDIO_CALL.getValue());// FOR AUDIO
//            featureList.add(User.Features.IP_VIDEO_CALL.getValue());// FOR VIDEO
//            user.setFeatures(featureList);
//            mAuthTask = new UserLoginTask(user, listener, getActivity());
//            mAuthTask.execute((Void) null);
            getwebservice();

            // Refresh your fragment here
        }
    }
    private void getwebservice() {
        try {

            Activity activity = getActivity();
            if(activity != null && isAdded())
            {
                showProgress();
            }

            HashMap<String, String> params = new HashMap<>();
            String userid = getPref(Constants.USER_ID);
            params.put("user_id", userid);
            APIService service = ServiceGenerator.createService(APIService.class, getContext());
            Call<ModelMessages> call = service.getmessages(params);
            call.enqueue(new Callback<ModelMessages>() {
                @Override
                public void onResponse(Call<ModelMessages> call, Response<ModelMessages> response) {
                    if (response.isSuccessful()) {
                        Activity activity = getActivity();
                        if(activity != null && isAdded())
                        {
                            hideProgress();
                        }
                        if (response.body() != null && response.body().getResult() != null) {
                            ModelMessages modelMessages = response.body();
                            list1 = modelMessages.getResult().getReceivedusers();
                            list2 = modelMessages.getResult().getSentusers();
                            list.clear();

                            adapter.notifyDataSetChanged();
                            if (selected == 1) {


                                me.setBackgroundColor(getResources().getColor(R.color.black));
                                textme.setTextColor(getResources().getColor(R.color.white));
                                someOneelse.setBackgroundColor(getResources().getColor(R.color.white));
                                textsomeone.setTextColor(getResources().getColor(R.color.black));


                                if (list1.size() > 0) {
                                    notext.setVisibility(View.GONE);
                                    for (int i = 0; i < list1.size(); i++) {
                                        GridhomemodelwithStatus m = new GridhomemodelwithStatus();
                                        m.setImagename(list1.get(i).getUsername());
                                        m.setImagepath(list1.get(i).getUserPhoto());
                                        m.setChathelpid(list1.get(i).getChathelpId());
                                        String userid = list1.get(i).getUserId();
                                        int contactUnreadCount = new MessageDatabaseService(getActivity()).getUnreadMessageCountForContact(userid);
                                        m.setMessagecount(contactUnreadCount);
                                        String online = list1.get(i).getOnlineStatus();
                                        m.setOnline(Integer.valueOf(online));
                                        list.add(m);
                                    }
                                    adapter.notifyDataSetChanged();
//                            int contactUnreadCount = new MessageDatabaseService(getActivity()).getUnreadMessageCountForContact("76");
//                            Toast.makeText(getActivity(),String.valueOf(contactUnreadCount),Toast.LENGTH_LONG).show();

                                }
                                else
                                {
                                    notext.setVisibility(View.VISIBLE);
                                }
                            } else {

                                me.setBackgroundColor(getResources().getColor(R.color.white));
                                textme.setTextColor(getResources().getColor(R.color.black));
                                someOneelse.setBackgroundColor(getResources().getColor(R.color.black));
                                textsomeone.setTextColor(getResources().getColor(R.color.white));


                                if (list2.size() > 0) {
                                    notext.setVisibility(View.GONE);

                                    for (int i = 0; i < list2.size(); i++) {
                                        GridhomemodelwithStatus m = new GridhomemodelwithStatus();
                                        m.setImagename(list2.get(i).getUsername());
                                        m.setChathelpid(list2.get(i).getChathelpId());
                                        m.setImagepath(list2.get(i).getUserPhoto());
                                        String online = list2.get(i).getOnlineStatus();
                                        String userid = list2.get(i).getUserId();
                                        int contactUnreadCount = new MessageDatabaseService(getActivity()).getUnreadMessageCountForContact(userid);
                                        m.setMessagecount(contactUnreadCount);
                                        m.setOnline(Integer.valueOf(online));

                                        list.add(m);
                                    }
                                    adapter.notifyDataSetChanged();

                                }
                                else
                                {
                                    notext.setVisibility(View.VISIBLE);
                                }

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
                public void onFailure(Call<ModelMessages> call, Throwable t) {
                    Activity activity = getActivity();
                    if(activity != null && isAdded())
                    {
                        hideProgress();
                    }

                }
            });

        }catch (Exception e)
        {

        }
    }
public  void getwebservice1(String chatid,int type)
{

    final HashMap<String, String> params = new HashMap<>();
    showProgress();
    params.put("chat_id",chatid);
    if (type==1) {
        params.put("type","sender" );
    }
    if (type==2)
    {
        params.put("type","receiver");
    }
APIService service=ServiceGenerator.createService(APIService.class,getContext());
    Call<ResponseBody>call=service.deletechat(params);
    call.enqueue(new Callback<ResponseBody>() {
        @Override
        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
            if (response.isSuccessful())
            {
                hideProgress();
                getwebservice();

            }
        }

        @Override
        public void onFailure(Call<ResponseBody> call, Throwable t) {

        }
    });

}
    private void setui() {
        ItemClickSupport.addTo(recyclerview).setOnItemLongClickListener(new ItemClickSupport.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClicked(RecyclerView recyclerView, final int position, View v) {
                if (selected==1)
                {
                    final String userid=list1.get(position).getChathelpId();
                    final String contactid=list1.get(position).getUserId();
                    String sq="Are you sure you want to delete conversation with"+" "+list1.get(position).getUsername();
                    Spanned s = Html.fromHtml(sq);
                    new MaterialDialog.Builder(getContext())
                            .titleGravity(GravityEnum.CENTER)
                            .contentGravity(GravityEnum.CENTER)
                            .buttonsGravity(GravityEnum.CENTER)
                            .title("DELETE")
                            .titleColor(getResources().getColor(R.color.black))
                            .backgroundColor(getResources().getColor(R.color.white_transparent))
                            .content(s)
                            .positiveText("Delete")
                            .contentColor(getResources().getColor(R.color.black))
                            .positiveColor(Color.RED)
                            .negativeText("Cancel")
                            .negativeColor(Color.BLUE)
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    mParentListener.OnVisibilty(true);
                                    MobiComConversationService conversationService = new MobiComConversationService(getActivity());
                                    AppContactService appContactService = new AppContactService(getActivity());
                                    Contact contact =    appContactService.getContactById(contactid);

                                    Channel channel=null;
                                    conversationService.read(contact, channel);
                                    getwebservice1(userid,1);
                                }
                            })
                            .onNegative(null)
                            .show();

                }
                if (selected==2)
                {
                    final String userid=list2.get(position).getChathelpId();
                    final String contactid=list2.get(position).getUserId();
                    String sq="Are you sure you want to delete conversation with"+" "+list2.get(position).getUsername();
                    Spanned s = Html.fromHtml(sq);
                    new MaterialDialog.Builder(getContext())
                            .titleGravity(GravityEnum.CENTER)
                            .contentGravity(GravityEnum.CENTER)
                            .buttonsGravity(GravityEnum.CENTER)
                            .title("DELETE")
                            .titleColor(getResources().getColor(R.color.black))
                            .backgroundColor(getResources().getColor(R.color.white_transparent))
                            .content(s)
                            .positiveText("Delete")
                            .contentColor(getResources().getColor(R.color.black))
                            .positiveColor(Color.RED)
                            .negativeText("Cancel")
                            .negativeColor(Color.BLUE)
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    mParentListener.OnVisibilty(true);
                                    MobiComConversationService conversationService = new MobiComConversationService(getActivity());
                                    AppContactService appContactService = new AppContactService(getActivity());
                                    Contact contact =    appContactService.getContactById(contactid);
                                    Channel channel=null;
                                    conversationService.read(contact, channel);
                                    getwebservice1(userid,2);
                                }
                            })
                            .onNegative(null)
                            .show();
                }
                return false;
            }
        });

        ItemClickSupport.addTo(recyclerview).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                try {


                    if (selected == 1) {
                        mParentListener.OnVisibilty(true);
//                    list.get(position).setMessagecount(0);
                        adapter.notifyDataSetChanged();
                        String userid = list1.get(position).getUserId();
                        String username = list1.get(position).getUsername();
                        int blockstatus=list1.get(position).getBlockStatus();
                        if (blockstatus==1)
                        {
                            String s=username+" has blocked you";
                            showToast(s,false);
                        }
                        else {
                            ApplozicSetting.getInstance(getContext()).setChatBackgroundColorOrDrawableResource(R.drawable.bg_splash);
                            ApplozicSetting.getInstance(getContext()).setSentMessageBackgroundColor(R.color.white);
                            ApplozicSetting.getInstance(getContext()).setReceivedMessageBackgroundColor(R.color.sentmessage_black);
                            ApplozicSetting.getInstance(getContext()).setSentMessageTextColor(R.color.black);
                            ApplozicSetting.getInstance(getContext()).set_isimagebuttonclicked(false);
                            ApplozicSetting.getInstance(getContext()).set_is_locationbuttonclicked(false);
                            ApplozicSetting.getInstance(getContext()).setReceivedMessageTextColor(R.color.white);
                            Intent intent = new Intent(getContext(), ConversationActivity.class);
                            intent.putExtra(ConversationUIService.USER_ID, userid);
                            intent.putExtra(ConversationUIService.DISPLAY_NAME, username);
                            intent.putExtra(ConversationUIService.TAKE_ORDER, true);
                            startActivity(intent);
                        }
                    }
                    if (selected == 2) {
                        mParentListener.OnVisibilty(true);
//                    list.get(position).setMessagecount(0);
                        adapter.notifyDataSetChanged();
                        String userid = list2.get(position).getUserId();
                        String username = list2.get(position).getUsername();
                        int blockstatus=list2.get(position).getBlockStatus();
                        if (blockstatus==1)
                        {
                            String s=username+" has blocked you";
                            showToast(s,false);
                        }
                        else {
                            ApplozicSetting.getInstance(getContext()).setChatBackgroundColorOrDrawableResource(R.drawable.bg_splash);
                            ApplozicSetting.getInstance(getContext()).setSentMessageBackgroundColor(R.color.white);
                            ApplozicSetting.getInstance(getContext()).setReceivedMessageBackgroundColor(R.color.sentmessage_black);
                            ApplozicSetting.getInstance(getContext()).setSentMessageTextColor(R.color.black);
                            ApplozicSetting.getInstance(getContext()).set_isimagebuttonclicked(false);
                            ApplozicSetting.getInstance(getContext()).set_is_locationbuttonclicked(false);
                            ApplozicSetting.getInstance(getContext()).setReceivedMessageTextColor(R.color.white);
                            Intent intent = new Intent(getContext(), ConversationActivity.class);
                            intent.putExtra(ConversationUIService.USER_ID, userid);
                            intent.putExtra(ConversationUIService.DISPLAY_NAME, username);
                            intent.putExtra(ConversationUIService.TAKE_ORDER, true);
                            startActivity(intent);
                        }
                    }
                }catch (Exception e)
                {

                }

            }
        });
        me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                me.setBackgroundColor(getResources().getColor(R.color.black));
                textme.setTextColor(getResources().getColor(R.color.white));
                someOneelse.setBackgroundColor(getResources().getColor(R.color.white));
                textsomeone.setTextColor(getResources().getColor(R.color.black));
                list.clear();
                selected=1;
                adapter.notifyDataSetChanged();
                if (list1.size()>0)
                {notext.setVisibility(View.GONE);
                    for (int  i=0;i<list1.size();i++)
                    {
                        GridhomemodelwithStatus m=new GridhomemodelwithStatus();
                        m.setImagename(list1.get(i).getUsername());
                        m.setChathelpid(list1.get(i).getChathelpId());
                        m.setImagepath(list1.get(i).getUserPhoto());
                        String online=list1.get(i).getOnlineStatus();
                        String userid=list1.get(i).getUserId();
                        int contactUnreadCount = new MessageDatabaseService(getActivity()).getUnreadMessageCountForContact(userid);
                        m.setMessagecount(contactUnreadCount);
                        m.setOnline(Integer.valueOf(online));

                        list.add(m);
                    }
                    adapter.notifyDataSetChanged();
                }
                else
                {
                    notext.setVisibility(View.VISIBLE);
                }
            }
        });
        someOneelse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                me.setBackgroundColor(getResources().getColor(R.color.white));
                textme.setTextColor(getResources().getColor(R.color.black));
                someOneelse.setBackgroundColor(getResources().getColor(R.color.black));
                textsomeone.setTextColor(getResources().getColor(R.color.white));
                list.clear();
                selected=2;
                adapter.notifyDataSetChanged();
                if (list2.size()>0)
                {
                    notext.setVisibility(View.GONE);
                    for (int  i=0;i<list2.size();i++)
                    {
                        GridhomemodelwithStatus m=new GridhomemodelwithStatus();
                        m.setImagename(list2.get(i).getUsername());
                        m.setChathelpid(list2.get(i).getChathelpId());
                        m.setImagepath(list2.get(i).getUserPhoto());
                        String online=list2.get(i).getOnlineStatus();
                        String userid=list2.get(i).getUserId();
                        int contactUnreadCount = new MessageDatabaseService(getActivity()).getUnreadMessageCountForContact(userid);
                        m.setMessagecount(contactUnreadCount);
                        m.setOnline(Integer.valueOf(online));

                        list.add(m);
                    }
                    adapter.notifyDataSetChanged();
                }
                else
                {
                    notext.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    @Override
    public void onStop() {
        super.onStop();
        hideProgress();
    }


    private void setAdapter() {
        adapter=new GRIDWITHSTATUSADAPTER(getContext(),list);
        recyclerview.setAdapter(adapter);
    }

    private void initui(View v) {
        Typeface typeface=Typeface.createFromAsset(getContext().getAssets(),"fonts/brandon_grotesque_bold.ttf");
        Typeface typeface1=Typeface.createFromAsset(getContext().getAssets(),"fonts/BrandonGrotesque-Regular.ttf");
        recyclerview= (RecyclerView) v.findViewById(R.id.recyclerView);
        whostarted= (TextView) v.findViewById(R.id.textWhoStarted);
        notext= (TextView) v.findViewById(R.id.notext);
        notext.setTypeface(typeface1);
        whostarted.setTypeface(typeface1);
        textme= (TextView) v.findViewById(R.id.textme);
        textme.setTypeface(typeface1);
        textsomeone= (TextView) v.findViewById(R.id.textsomeone);
        textsomeone.setTypeface(typeface1);
        me= (LinearLayout) v.findViewById(R.id.layout_me);
        someOneelse= (LinearLayout) v.findViewById(R.id.layout_someone);
        recyclerview.setHasFixedSize(true);
        mLayoutManager=new GridLayoutManager(getContext(),4);
        recyclerview.setLayoutManager(mLayoutManager);

    }


}
