package com.richie.styler.UI.POPUPS;

import android.app.Dialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment ;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.richie.styler.R;
import com.richie.styler.UTILS.Constants;
import com.richie.styler.UTILS.Util;

/**
 * Created by User on 02-03-2017.
 */

public class PopUpTermsAndConditions extends DialogFragment {
    LinearLayout agree,disagree;
    View view;
    TextView head,terms;
    static PopUpTermsAndConditions newInstance()
    {
        PopUpTermsAndConditions p=new PopUpTermsAndConditions();
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

        view=inflater.inflate(R.layout.popuptermsandconditions,container,false);
        initUI(view);
        return  view;

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

    private void initUI(View view) {
        agree= (LinearLayout) view.findViewById(R.id.AgreeButton);
        disagree= (LinearLayout) view.findViewById(R.id.DisAgreeButton);
        head= (TextView) view.findViewById(R.id.text_head_terms);
        terms=(TextView)view.findViewById(R.id.textterms);
        Typeface typeface=Typeface.createFromAsset(getContext().getAssets(),"fonts/brandon_grotesque_bold.ttf");
        Typeface typeface1=Typeface.createFromAsset(getContext().getAssets(),"fonts/BrandonGrotesque-Regular.ttf");
        head.setTypeface(typeface);
        terms.setTypeface(typeface1);
        agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.getUtils().savePref(Constants.FirstTime_TERMS,false);
                dismiss();
            }
        });
        disagree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }
}
