package com.richie.styler.UI.PrivacyPolicy;


import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment ;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.richie.styler.R;

/**
 * Created by User on 01-03-2017.
 */

public class PrivacyPolicy extends DialogFragment{
    View view;
    ImageView close;
   public static PrivacyPolicy newInstance()
    {
        PrivacyPolicy p=new PrivacyPolicy();
        return p;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(android.support.v4.app.DialogFragment.STYLE_NORMAL, R.style.Theme_FullScren);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view=inflater.inflate(R.layout.privacy_layout,container,false);
        initUI(view);
        return  view;

    }

    private void initUI(View view) {
        close= (ImageView) view.findViewById(R.id.imageclose);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new Dialog(getActivity(), getTheme()){
            @Override
            public void onBackPressed() {
                //do your stuff
            }
        };
    }
}
