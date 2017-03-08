package com.mindbees.stylerapp.UTILS;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.provider.SyncStateContract;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.io.File;

public class Util {
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
        Toast localToast = Toast.makeText(context, paramString, Toast.LENGTH_SHORT);
        localToast.setGravity(16, 0, 0);
        localToast.show();
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

        editor.commit();
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
