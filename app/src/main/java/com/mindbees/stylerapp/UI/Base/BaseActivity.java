package com.mindbees.stylerapp.UI.Base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mindbees.stylerapp.R;
import com.mindbees.stylerapp.UTILS.Util;


/**
 * Created by User on 01-03-2017.
 */

public class BaseActivity extends AppCompatActivity {

    ProgressDialog pDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    private ProgressDialog getProgressDialog() {
        if (this.pDialog == null) {
//            this.pDialog = CustomProgressDialog.nowRunningDialog(this);
        }
        return this.pDialog;
    }

    public void showProgress() {
        getProgressDialog();
        if (this.pDialog != null) {
            this.pDialog.show();
        }

    }

    public void hideProgress() {
        if (pDialog != null && this.pDialog.isShowing()) {
            this.pDialog.dismiss();
            pDialog = null;
        }
    }
    public void setupUI(View view) {

        //Set up touch listener for non-text box views to hide keyboard.
        if(!(view instanceof EditText)) {

            view.setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    // TODO Auto-generated method stub
                    Util.hideSoftKeyboard(BaseActivity.this);
                    return false;
                }

            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {

            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {

                View innerView = ((ViewGroup) view).getChildAt(i);

                setupUI(innerView);
            }
        }
    }
    public void showSnackBar(String msg, boolean isSuccess){

        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), msg, Snackbar.LENGTH_SHORT);
        View view  = snackbar.getView();
        TextView textView = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        if (isSuccess){
            view.setBackgroundColor(getResources().getColor(R.color.green_button));
        }else {
            view.setBackgroundColor(getResources().getColor(R.color.red_orange));
        }

        snackbar.show();

//        Snackbar.make(context.findViewById(android.R.id.content), msg, Snackbar.LENGTH_SHORT).show();
    }
    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    public void delete(String key) {
        Util.getUtils().delete(key);
    }
    public void showToast(String Toast){
        Util.getUtils().showToastMessage(Toast);
    }
    public  boolean isValidEmail(CharSequence target) {
        return Util.getUtils().isValidEmail(target);
    }

    public void savePref(String key, Object value) {
        Util.getUtils().savePref(key, value);
    }



    @SuppressWarnings("unchecked")
    public <T> T getPref(String key) {
        return (T) Util.getUtils().getPref(key);
    }

    @SuppressWarnings("unchecked")
    public <T> T getPref(String key, T defValue) {
        return Util.getUtils().getPref(key, defValue);
    }

    public boolean isPrefExists(String key) {
        return Util.getUtils().isPrefExists(key);
    }

}
