package com.richie.styler.UI.TabFragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.applozic.mobicomkit.api.MobiComKitConstants;
import com.applozic.mobicomkit.api.conversation.database.MessageDatabaseService;
import com.richie.styler.R;
import com.richie.styler.UI.Base.BaseFragment;
import com.richie.styler.UTILS.Constants;
import com.richie.styler.UTILS.CustomTabLayout;
import com.richie.styler.UTILS.NonSwipeableViewPager;
import com.richie.styler.UTILS.VisibilityListener;

/**
 * Created by User on 28-03-2017.
 */

public class HomeTabfragment extends BaseFragment implements VisibilityListener {
    CustomTabLayout tabLayout;
    TextView badge;
    int unreadCount =0;
    NonSwipeableViewPager viewPager;
    LinearLayout badgecontainer;
    public static int int_items = 4 ;
    private BroadcastReceiver unreadCountBroadcastReceiver;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public void onResume() {
        super.onResume();
        unreadCount= new MessageDatabaseService(getActivity()).getTotalUnreadCount();
        if (unreadCount>0)
        {
            badge.setText(String.valueOf(unreadCount));
            badgecontainer.setVisibility(View.VISIBLE);
        }
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(unreadCountBroadcastReceiver, new IntentFilter(MobiComKitConstants.APPLOZIC_UNREAD_COUNT));

    }
    @Override
    public void onPause() {
        if(unreadCountBroadcastReceiver != null){
            LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(unreadCountBroadcastReceiver);
        }
        super.onPause();
    }
    private void changeTabsFont() {
        Typeface typeface=Typeface.createFromAsset(getContext().getAssets(),"fonts/brandon_grotesque_bold.ttf");
        Typeface typeface1=Typeface.createFromAsset(getContext().getAssets(),"fonts/BrandonGrotesque-Regular.ttf");
        ViewGroup vg = (ViewGroup) tabLayout.getChildAt(0);
        int tabsCount = vg.getChildCount();
        for (int j = 0; j < tabsCount; j++) {
            ViewGroup vgTab = (ViewGroup) vg.getChildAt(j);
            int tabChildsCount = vgTab.getChildCount();
            for (int i = 0; i < tabChildsCount; i++) {
                View tabViewChild = vgTab.getChildAt(i);
                if (tabViewChild instanceof TextView) {
                    ((TextView) tabViewChild).setTypeface(typeface1);
                }
            }
        }
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View ex = inflater.inflate(R.layout.home_tab_layout, null);
        tabLayout = (CustomTabLayout) ex.findViewById(R.id.tabs);
        int totalUnreadCount = new MessageDatabaseService(getActivity()).getTotalUnreadCount();
        unreadCount=totalUnreadCount;
        int contactUnreadCount = new MessageDatabaseService(getActivity()).getUnreadMessageCountForContact("101");
        unreadCount=contactUnreadCount;
        unreadCount=getPref(Constants.TOTALUNREADCOUNT,0);
        unreadCount= new MessageDatabaseService(getActivity()).getTotalUnreadCount();
//        Toast.makeText(getContext(),String.valueOf(unreadCount),Toast.LENGTH_LONG).show();
        viewPager = (NonSwipeableViewPager) ex.findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(4);
        badge= (TextView) ex.findViewById(R.id.badgetext);
        badgecontainer= (LinearLayout) ex.findViewById(R.id.badgecontainer);
        changeTabsFont();
        if (unreadCount==0)
        {
            badgecontainer.setVisibility(View.GONE);
        }
        else
        {
            badge.setText(String.valueOf(unreadCount));
            badgecontainer.setVisibility(View.VISIBLE);

        }
        final MessageDatabaseService messageDatabaseService =  new MessageDatabaseService(getContext());
        unreadCountBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (MobiComKitConstants.APPLOZIC_UNREAD_COUNT.equals(intent.getAction())) {
                   unreadCount  =  messageDatabaseService.getTotalUnreadCount();
                    badge.setText(String.valueOf(unreadCount));
                    badgecontainer.setVisibility(View.VISIBLE);

                    //Update unread count in UI
                }
            }
        };
//        int totalUnreadCount = new MessageDatabaseService(getContext()).getTotalUnreadCount();
//        badge.setText(totalUnreadCount);
//        Toast.makeText(getContext(),totalUnreadCount,Toast.LENGTH_LONG).show();

        viewPager.setAdapter(new MyAdapter(getChildFragmentManager()));

        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);

            }
        });
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position==3)
                {
//                    badgecontainer.setVisibility(View.GONE);
//                    savePref(Constants.TOTALUNREADCOUNT,0);
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        return ex;
    }

    @Override
    public void OnVisibilty(boolean visible) {
        if (visible) {
            badgecontainer.setVisibility(View.GONE);
        }
    }

    private class MyAdapter extends FragmentPagerAdapter {
        public MyAdapter(FragmentManager childFragmentManager) {
            super(childFragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
               case 0 : return new GlobalFragment();
                case 1 : return new NearByFragment();
                case 2:return new SearchFragment();
                case 3:return  new MessagesFragment();


            }

            return null;
        }

        @Override
        public int getCount() {
            return int_items;

        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                case 0:return "GLOBAL";

                case 1 :
                  return "NEARBY";
                case 2 :
                    return "SEARCH";
                case 3:return "MESSAGES";

            }
            return null;

        }
    }
}
