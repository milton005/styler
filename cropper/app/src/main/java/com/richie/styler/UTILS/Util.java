package com.richie.styler.UTILS;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.richie.styler.R;

import java.io.File;

import static com.richie.styler.R.id.imageToast;

public class Util {
    ProgressDialog pDialog;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    Context context;
    public static final StringBuilder sb;
    private static Util instance;

    private String folderName;

    public static Util getUtils() {
        return instance;
    }

    public Util(Context context) {

        this.context = context;
        String prefsFile = context.getPackageName();
        sharedPreferences = context.getSharedPreferences(prefsFile, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        instance = this;
    }
    public boolean isValidEmail(CharSequence target) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
    static {
        sb = new StringBuilder();
    }
    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager)context. getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    public static String concat(Object... objects) {
        int i = 0;
        sb.setLength(0);
        int length = objects.length;
        while (i < length) {
            sb.append(objects[i]);
            i++;
        }
        return sb.toString();
    }


    public void delete(String key) {
        if (sharedPreferences.contains(key)) {
            editor.remove(key).commit();
        }
    }

    public void showToastMessage(String paramString)
    {
//        Toast localToast = Toast.makeText(context, paramString, Toast.LENGTH_SHORT);
//
//        ViewGroup view = (ViewGroup)localToast.getView();
//        TextView messageTextView = (TextView) view.getChildAt(0);
//        messageTextView.setTextSize(20);
//        messageTextView.setTextColor(Color.WHITE);
//        messageTextView.setShadowLayer(0,0,0,Color.TRANSPARENT);
//        view.setPadding(30,30,30,30);
//       view.setMinimumHeight(50);
//        view.setMinimumWidth(200);

//        view.setBackgroundColor(context.getResources().getColor(R.color.dark_slate_blue));

//        view.setBackgroundResource(R.drawable.tags_rounded_corners);
//        localToast.setGravity(16, 0, 0);
//        localToast.show();
    }
    public void showToastMessage(String params,boolean error)
    {
        Toast toast=new Toast(context);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(16,0,0);
       View view= LayoutInflater.from(context).inflate(R.layout.customtoast,null);
        TextView textView= (TextView) view.findViewById(R.id.toasttext);
        ImageView imageView= (ImageView) view.findViewById(imageToast);
        if (error) {
            imageView.setImageResource(R.drawable.success);
        }
        else
        {
            imageView.setImageResource(R.drawable.error);
        }
        textView.setText(params);
        toast.setView(view);
        toast.show();


    }
    private ProgressDialog getProgressDialog() {
        if (this.pDialog == null) {
            this.pDialog = CustomProgressDialog.nowRunningDialog(context);
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
    public void savePref(String key, Object value) {
        delete(key);

        if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
        } else if (value instanceof Integer) {
            editor.putInt(key, (Integer) value);
        } else if (value instanceof Float) {
            editor.putFloat(key, (Float) value);
        } else if (value instanceof Long) {
            editor.putLong(key, (Long) value);
        } else if (value instanceof String) {
            editor.putString(key, (String) value);
        } else if (value instanceof Enum) {
            editor.putString(key, value.toString());
        } else if (value != null) {
            throw new RuntimeException("Attempting to save non-primitive preference");
        }

        editor.apply();
    }

    @SuppressWarnings("unchecked")
    public <T> T getPref(String key) {
        return (T) sharedPreferences.getAll().get(key);
    }

    @SuppressWarnings("unchecked")
    public <T> T getPref(String key, T defValue) {
        T returnValue = (T) sharedPreferences.getAll().get(key);
        return returnValue == null ? defValue : returnValue;
    }
    public boolean isPrefExists(String key) {
        return sharedPreferences.contains(key);
    }
    public static File createFile(Context context){
        File mFileTemp;
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            mFileTemp = new File(Environment.getExternalStorageDirectory(), Constants.TEMP_PHOTO_FILE);
        }
        else {
            mFileTemp = new File(context.getFilesDir(), Constants.TEMP_PHOTO_FILE);
        }
        return mFileTemp;
    }
    public static  void hideSoftKeyboard(Activity activity) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
