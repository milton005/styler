package com.richie.styler.UI.POPUPS;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment ;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.richie.styler.R;
import com.richie.styler.UI.Models.ModelForgotpassword.Modelforgot;
import com.richie.styler.UTILS.Retrofit.APIService;
import com.richie.styler.UTILS.Retrofit.ServiceGenerator;
import com.richie.styler.UTILS.Util;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by User on 20-03-2017.
 */

public class PopUpForgotPassword extends DialogFragment {
    TextView heading,sub;
    EditText editTextForgot;
    ImageView closebutton;
    LinearLayout submit;
    String Email;
    RelativeLayout forgotemail;

    public static PopUpForgotPassword newInstance()
    {
        PopUpForgotPassword p=new PopUpForgotPassword();
        return p;
    }
    View view;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(android.support.v4.app.DialogFragment.STYLE_NORMAL, R.style.AppTheme);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view=inflater.inflate(R.layout.popup_forgot_password,container,false);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getDialog().getWindow().requestFeature(1);
        initUi(view);
        setUpui();
        return view;
    }

    private void setUpui() {
        editTextForgot.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                forgotemail.setVisibility(View.GONE);
                return false;
            }
        });
    }

    private void initUi(View view) {
        closebutton= (ImageView) view.findViewById(R.id.close_button);
        editTextForgot= (EditText) view.findViewById(R.id.editTextForgot);
        heading= (TextView) view.findViewById(R.id.textViewForgotHeading);
        forgotemail= (RelativeLayout) view.findViewById(R.id.errorforgotemail);
        sub= (TextView) view.findViewById(R.id.textViewForgot);
        submit= (LinearLayout) view.findViewById(R.id.layout_submit_Forgot);
        Typeface typeface=Typeface.createFromAsset(getContext().getAssets(),"fonts/brandon_grotesque_bold.ttf");
        Typeface typeface1=Typeface.createFromAsset(getContext().getAssets(),"fonts/BrandonGrotesque-Regular.ttf");
        editTextForgot.setTypeface(typeface1);
        heading.setTypeface(typeface);
        sub.setTypeface(typeface1);
        closebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkEmail())
                {
                    getwebservice();

                }
            }
        });


    }

    private boolean checkEmail() {
        Email=editTextForgot.getText().toString().trim();
        if (Email.isEmpty())
        {
//            editTextForgot.setError("Please Enter a valid email Address");
            forgotemail.setVisibility(View.VISIBLE);
            editTextForgot.requestFocus();
            return false;
        }
        else
        {
            if (Util.getUtils().isValidEmail(Email))
            {
                return true;
            }
            else {
//                editTextForgot.setError("Please Enter a valid email Address");
                forgotemail.setVisibility(View.VISIBLE);
                editTextForgot.requestFocus();
                return false;
            }
        }
    }

    public void getwebservice() {
        final Util util=new Util(getContext());
        util.showProgress();

        HashMap<String, String> params = new HashMap<>();
        params.put("user_email",Email);
        final APIService service = ServiceGenerator.createService(APIService.class, getContext());
        Call<Modelforgot>call=service.forgotpass(params);
        call.enqueue(new Callback<Modelforgot>() {
            @Override
            public void onResponse(Call<Modelforgot> call, Response<Modelforgot> response) {
                if (response.isSuccessful())
                {
                 util.hideProgress();
                    if (response.body()!=null&&response.body().getResult()!=null)
                    {
                        Modelforgot modelforgot=response.body();
                        int status=modelforgot.getResult().get(0).getUserStatus();
                        if (status==0)
                        {
                            String message=modelforgot.getResult().get(0).getMessage();
                            Util.getUtils().showToastMessage(message,false);

                        }
                        else {
                            String message=modelforgot.getResult().get(0).getMessage();
                            Util.getUtils().showToastMessage(message,true);
                            dismiss();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<Modelforgot> call, Throwable t) {
              util.hideProgress();
            }
        });



    }
}
