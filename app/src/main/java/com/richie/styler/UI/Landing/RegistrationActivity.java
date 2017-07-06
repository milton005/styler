package com.richie.styler.UI.Landing;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationManager;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.AppCompatSpinner;
import android.text.Html;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.applozic.mobicomkit.api.account.user.UserLoginTask;
import com.bruce.pickerview.popwindow.DatePickerPopWin;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.maps.android.SphericalUtil;
import com.richie.styler.R;
import com.richie.styler.UI.Adapter.SpinnerAdapter;
import com.richie.styler.UI.Base.BaseActivity;
import com.richie.styler.UI.POPUPS.POPUPHEIGHT;
import com.richie.styler.UI.POPUPS.PopUpWeiight;
import com.richie.styler.UI.Models.Ethnicity.ModelEthnicity;
import com.richie.styler.UI.Models.Ethnicity.Result;
import com.richie.styler.UI.Models.Register.ModelRegister;
import com.richie.styler.UI.Models.SpinnerModel;
import com.richie.styler.UI.POPUPS.PopupEditImage;
import com.richie.styler.UI.PrivacyPolicy.PrivacyPolicy;
import com.richie.styler.UTILS.CircleTransform;
import com.richie.styler.UTILS.Constants;
import com.richie.styler.UTILS.InternalStorageContentProvider;
import com.richie.styler.UTILS.Retrofit.APIService;
import com.richie.styler.UTILS.Retrofit.ServiceGenerator;
import com.richie.styler.UTILS.Util;
;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;


//import eu.janmuller.android.simplecropimage.CropImage;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.richie.styler.R.id.radio;


/**
 * Created by User on 01-03-2017.
 */

public class RegistrationActivity extends BaseActivity implements LocationListener{
    String Fname="",Lname="",Semail="",Spassword="",Sdob="",Sloc="",Slike,Sweight="",Sheight="",Sethnicity="",Slookingfor="",SuserName="";
    String gender="";
    TextView iam;
    boolean error=false;
    String EthnicityTitle="";
    String EthnicityId="";
    File f;
    String iLike="";
    int pos;
    MaterialDialog builder;
    String userID;
    private android.app.AlertDialog progressDialog;
    String fbid="",fbname="",email,genderfb="",first_name,last_name;
    LinearLayout likemale,likefemale,likeboth;
    TextView textlikemale,textlikefemale,textlikeboth,textheadlike;
    SpinnerAdapter adapter;
    Uri mImageCaptureUri = null;
    RelativeLayout errorFirstname,errorlastname,errorusername,erroremail,errorpassword,errorconfirmpassword,errordob,errorlocation;
    CircleImageView shadowimage;

    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    private static final int ACCESS_FINE_LOCATION = 0;
    private LocationManager locationManager;
    private String provider;
    ImageView userpic;
    LatLng southwest,northeast;
    String cityname;
    String Lat;
    String LONG;
    boolean imagedata=false;
    ImageView imageViewSpinner;
    boolean others=false;
    LatLng temp;
    LatLngBounds BOUNDS_MOUNTAIN_VIEW;
    public static final int PLACE_PICKER_REQUEST = 00;
    public static final int IMAGE_EDIT=0x4;
    public static final int EDITWEIGHT=0x5;
    public static final int EDITHEIGHT=0x6;
    ImageView picedit,reg_next;
    private File mFileTemp;
    LinearLayout Linearmale,Linearfemale;
    TextView textmale,textfemale,textilike,textloooking;
    EditText editemail,editpass,editdob,editloc,editheight,editweight,editethnicity,editlookingfor,editTextfirstname,editTextlastname,editTextUsername;
    AppCompatSpinner spinethnic;
    EditText Confirmpass;
    CheckBox checkBoxprivacy;
    TextView textViewPrivacy;
    RadioGroup rg;
    ScrollView scrollingView;
    AppCompatSpinner spinweight,spinheight;
    double lat,lng;
    boolean selected=false;
    public List<SpinnerModel> listitems;
    public List<SpinnerModel>listweight;
    public List<SpinnerModel>listHeight;
    List<Result>list;
    ImageView imageedit;
    private UserLoginTask mAuthTask = null;
    boolean isphoto=false;
    TextView taphere;
    ArrayList<String>listelements;
     String datechoose;
    public static final int REQUEST_CODE_GALLERY      = 0x1;
    public static final int REQUEST_CODE_TAKE_PICTURE = 0x2;
    public static final int REQUEST_CODE_CROP_IMAGE   = 0x3;
    String []interval={"No Interval","15 Minutes","30 Minutes","1 Hour","2 Hour","4 Hour","6 Hour","8 Hour","12 Hour","5 Minutes"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_layout);
//        progressDialog = new SpotsDialog(this, R.style.Custom);
        setupUI(findViewById(R.id.registrationlayout));
        imageedit= (ImageView) findViewById(R.id.imageEdit);


        listitems=new ArrayList<SpinnerModel>();
        listHeight=new ArrayList<SpinnerModel>();
        listweight=new ArrayList<SpinnerModel>();

        initui();
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        Initializepicker();
        adapter=new SpinnerAdapter(RegistrationActivity.this,listitems);
        spinethnic.setAdapter(adapter);
        if (isNetworkAvailable())
        {
            getEthnicity();

        }
        else {
            showToast("Please check network connectivity",false);
        }

        setupUi();
        setSpinnner();
        try{
            Bundle i=getIntent().getExtras();
            fbid =i.getString("fbid");
            fbname=i.getString("name");
            genderfb=i.getString("genderfb");
            email=i.getString("email");
//            first_name=i.getString("first_name");
//            last_name=i.getString("last_name");
//            Toast.makeText(RegistrationActivity.this,genderfb,Toast.LENGTH_LONG).show();
            SetFb();

        }catch (Exception e){}
      try
      {
//          Getlocation();
      }
      catch (Exception e)
      {

      }


        Resources res = getResources();

//        ArrayAdapter<String> adapter=new ArrayAdapter<String>(RegistrationActivity.this,R.layout.spinner_item,interval);
//        adapter.setDropDownViewResource(R.layout.spinner_item);
//        spinethnic.setAdapter(adapter);
        BOUNDS_MOUNTAIN_VIEW=new LatLngBounds(southwest,northeast);

    }

    private void setSpinnner() {
        SpinnerModel model2=new SpinnerModel();
        model2.setName("SELECT ");
        listHeight.add(model2);
       for(int i=120;i<241;i++)

       {
           double d=i*.01;
           DecimalFormat numberFormat = new DecimalFormat("#.00");
           String s=String.valueOf(numberFormat.format(d))+" m";
           SpinnerModel model=new SpinnerModel();
           model.setName(s);
           listHeight.add(model);


       }
       SpinnerAdapter adapter=new SpinnerAdapter(RegistrationActivity.this,listHeight);
        spinheight.setAdapter(adapter);
        SpinnerModel model3=new SpinnerModel();
        model3.setName("SELECT ");
        listweight.add(model3);
        for (int i=40;i<141;i++)
        {
            String s=String.valueOf(i)+" kg";
            SpinnerModel model4=new SpinnerModel();
            model4.setName(s);
            listweight.add(model4);
        }
        SpinnerAdapter adapter1=new SpinnerAdapter(RegistrationActivity.this,listweight);
        spinweight.setAdapter(adapter1);

    }



    private void SetFb() {
        if(!fbname.isEmpty()) {
//            editTextfirstname.setText(fbname);
            String[]Splitted=fbname.split("\\s");
            editTextfirstname.setText(Splitted[0]);
            editTextlastname.setText(Splitted[1]);

        }
//        if(!last_name.isEmpty()) {
//            editTextlastname.setText(fbname);
//        }
//        showToast(email);
        if (!email.isEmpty()){
         editemail.setText(email);
      }

       editpass.setVisibility(View.GONE);
        Confirmpass.setVisibility(View.GONE);
        if (!genderfb.isEmpty())
        {
            if (gender.equals("female"))
           {
                Linearfemale.setBackgroundColor(getResources().getColor(R.color.black));
                textfemale.setTextColor(getResources().getColor(R.color.white));
                Linearmale.setBackgroundColor(getResources().getColor(R.color.white));
                textmale.setTextColor(getResources().getColor(R.color.black));
                gender="f";

            }
            else {
                Linearmale.setBackgroundColor(getResources().getColor(R.color.black));
                textmale.setTextColor(getResources().getColor(R.color.white));
                Linearfemale.setBackgroundColor(getResources().getColor(R.color.white));
                textfemale.setTextColor(getResources().getColor(R.color.black));
                gender="m";

            }

        }

       final String user_photo="https://graph.facebook.com/" + fbid + "/picture?type=large&redirect=true&width=400&height=400";
        Picasso.with(RegistrationActivity.this).load(user_photo).transform(new CircleTransform()).into(userpic);
        Glide.with(this).load(user_photo).asBitmap().into(new BitmapImageViewTarget(imageedit) {
            @Override
            public void onResourceReady(Bitmap  drawable, GlideAnimation anim) {
                super.onResourceReady(drawable, anim);
                Bitmap bitmap=drawable;
                        imagedata=true;
                        isphoto=true;

                        f =new  File(Environment.getExternalStorageDirectory()
                                + File.separator + Constants.TEMP_PHOTO_FILE);
                        try {
                            f.createNewFile();
                            ByteArrayOutputStream bos = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG,60,bos);
                            byte[] bitmapdata = bos.toByteArray();
                            FileOutputStream fos = new FileOutputStream(f);
                            fos.write(bitmapdata);
                            fos.flush();
                            fos.close();
                        } catch (IOException e) {
                            Log.e("IOException", e.getLocalizedMessage());
                        }
                    }

        });

//        Picasso.with(this).load(user_photo).into(new Target() {
//            @Override
//            public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
//                new Thread(new Runnable() {
//
//                    @Override
//                    public void run() {
//                        Bitmap bm=bitmap;
//                        imagedata=true;
//                        isphoto=true;
//
//                        f =new  File(Environment.getExternalStorageDirectory()
//                                + File.separator + Constants.TEMP_PHOTO_FILE);
//                        try {
//                            f.createNewFile();
//                            ByteArrayOutputStream bos = new ByteArrayOutputStream();
//                            bitmap.compress(Bitmap.CompressFormat.JPEG,40,bos);
//                            byte[] bitmapdata = bos.toByteArray();
//                            FileOutputStream fos = new FileOutputStream(f);
//                            fos.write(bitmapdata);
//                            fos.flush();
//                            fos.close();
//                        } catch (IOException e) {
//                            Log.e("IOException", e.getLocalizedMessage());
//                        }
//                    }
//                }).start();
//
//            }
//
//            @Override
//            public void onBitmapFailed(Drawable errorDrawable) {
//
//            }
//
//            @Override
//            public void onPrepareLoad(Drawable placeHolderDrawable) {
//
//            }
//        });
//        try {
//            Bitmap m=getBitmapFromURL(user_photo);
//            bimapTofile(m);
//        }catch (Exception e)
//        {
//
//        }
    }
    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            // Log exception
            return null;
        }
    }
    private void setImageBitmap(String imageurl) throws IOException{
        URL url = new URL(imageurl);
        Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        bimapTofile(bmp);
    }

    private void getEthnicity() {
        HashMap<String, String> params = new HashMap<>();
        APIService service = ServiceGenerator.createService(APIService.class, RegistrationActivity.this);
        Call<ModelEthnicity>call=service.getethnic(params);
        call.enqueue(new Callback<ModelEthnicity>() {
            @Override
            public void onResponse(Call<ModelEthnicity> call, Response<ModelEthnicity> response) {
                if(response.isSuccessful())
                {
                    listelements=new ArrayList<String>();
                    if (response.body()!=null&&response.body().getResult()!=null)
                    {
                        ModelEthnicity model=response.body();
                        list=model.getResult();
                        final String[]items=new String[list.size()];
                        SpinnerModel model2=new SpinnerModel();
                       model2.setName(" SELECT  ETHNICITY");
                        listitems.add(model2);


                        for(int i=0;i<list.size();i++)
                        {
                            SpinnerModel model1=new SpinnerModel();
                            model1.setName(list.get(i).getEthnicityTitle());
                            listitems.add(model1);
//                            String item=list.get(i).getEthnicityTitle();
//                            items[i]=item;
                        }
//                        ArrayAdapter<String> adapter=new ArrayAdapter<String>(RegistrationActivity.this,R.layout.spinner_item,items);
//                        adapter.setDropDownViewResource(R.layout.spinner_item);
//                        spinethnic.setAdapter(adapter);
                        SpinnerModel model3=new SpinnerModel();
                        model3.setName("Other");
                        listitems.add(model3);
                        adapter=new SpinnerAdapter(RegistrationActivity.this,listitems);
                        spinethnic.setAdapter(adapter);
                    }
                }

            }

            @Override
            public void onFailure(Call<ModelEthnicity> call, Throwable t) {

            }
        });

    }
    public void hideSoftKeyboard() {
        if(getCurrentFocus()!=null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }
    private void setupUi() {
        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
//        editTextUsername.setFocusable(false);
//        editlookingfor.setFocusable(false);
//
//        editTextfirstname.requestFocus();
//        editTextlastname.setFocusable(false);
        editethnicity.setFocusable(false);
        editdob.setFocusable(false);
//        editemail.setFocusable(false);
//        editpass.setFocusable(false);
//        editloc.setFocusable(false);
       editheight.setFocusable(false);
       editweight.setFocusable(false);
//        editTextfirstname.setFocusable(false);

        editTextfirstname.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
               errorFirstname.setVisibility(View.GONE);
                return false;
            }
        });
        editTextlastname.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
               errorlastname.setVisibility(View.GONE);
                return false;
            }
        });
        editTextUsername.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
               errorusername.setVisibility(View.GONE);
                return false;
            }
        });
        editemail.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
               erroremail.setVisibility(View.GONE);
                return false;
            }
        });
        editloc.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                errorlocation.setVisibility(View.GONE);
                return false;
            }
        });
        editpass.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
              errorpassword.setVisibility(View.GONE);
                return false;
            }
        });
        Confirmpass.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                errorconfirmpassword.setVisibility(View.GONE);
                return false;
            }
        });
        final Calendar calender=Calendar.getInstance(TimeZone.getDefault());
        final int Year=calender.get(Calendar.YEAR);
        int yeartemp=Year-18;
        final int Month=calender.get(Calendar.MONTH);
        int Date=calender.get(Calendar.DAY_OF_MONTH);
        datechoose=yeartemp+"-"+(Month+1)+"-"+Date;
//        editTextfirstname.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)||(actionId==EditorInfo.IME_ACTION_NEXT)) {
//                    View view = getCurrentFocus();
//                    if (view != null) {
//                        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
//                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
//                    }
//
//                }
//                return false;
//
//            }
//        });
//        editTextlastname.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)||(actionId==EditorInfo.IME_ACTION_NEXT)) {
//                    View view = getCurrentFocus();
//                    if (view != null) {
//                        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
//                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
//                    }
//
//                }
//                return false;
//            }
//        });
//        editTextUsername.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)||(actionId==EditorInfo.IME_ACTION_NEXT)) {
//                    View view = getCurrentFocus();
//                    if (view != null) {
//                        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
//                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
//                    }
//
//                }
//                return false;
//            }
//        });
        editemail.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)||(actionId==EditorInfo.IME_ACTION_NEXT)) {
                    View view = getCurrentFocus();
//
                    if (!fbid.isEmpty())
                    {
                        if (view != null) {
                        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
//                        final Calendar calender = Calendar.getInstance(TimeZone.getDefault());
//                        final int Year = calender.get(Calendar.YEAR);
//                        int yeartemp = Year - 18;
//                        final int Month = calender.get(Calendar.MONTH);
//                        int Date = calender.get(Calendar.DAY_OF_MONTH);
//                        String datechoose = yeartemp + "-" + (Month + 1) + "-" + Date;
                        DatePickerPopWin pickerPopWin = new DatePickerPopWin.Builder(RegistrationActivity.this, new DatePickerPopWin.OnDatePickedListener() {
                            @Override
                            public void onDatePickCompleted(int year, int month, int day, String dateDesc) {
//                        Toast.makeText(RegistrationActivity.this, dateDesc, Toast.LENGTH_SHORT).show();
                                if (Year - year < 18) {
                                    showToast("You must be atleast 18 years old !", false);
                                } else {
                                    String s[]=dateDesc.split("-");
                                    int m=Integer.parseInt(s[1]);
//                            showToast(dateDesc,true);
                                    String mon=theMonth(m-1);
                                    String temp=year+"-"+mon+"-"+s[2];
                                    Sdob=dateDesc;
                                    datechoose=dateDesc;

                                    editdob.setText(temp);
                                    editloc.requestFocus();

                                }
                            }
                        }).textConfirm("Done") //text of confirm button
                                .textCancel("Cancel") //text of cancel button
                                .btnTextSize(20) // button text size
                                .viewTextSize(40) // pick view text size.
                                .colorCancel(Color.parseColor("#000000")) //color of cancel button
                                .colorConfirm(Color.parseColor("#000000"))//color of confirm button
                                .minYear(1950) //min year in loop
                                .maxYear(Year+1) // max year in loop
                                .showDayMonthYear(true) // shows like dd mm yyyy (default is false)
                                .dateChose(datechoose) // date chose when init popwindow
                                .build();

//                pickerPopWin.setBackgroundDrawable(getResources().getDrawable(R.drawable.tags_rounded_corners));


                        pickerPopWin.showPopWin(RegistrationActivity.this);
                    }

                }
                return false;
            }
        });
//        editpass.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)||(actionId==EditorInfo.IME_ACTION_NEXT)) {
//                    View view = getCurrentFocus();
//                    if (view != null) {
//                        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
//                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
//                    }
//
//                }
//                return false;
//            }
//        });

        Confirmpass.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
              if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId==EditorInfo.IME_ACTION_DONE) ){
//                    View view = getCurrentFocus();
//                    if (view != null) {
//                       InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
//                       imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
//                   }

                  View view = getCurrentFocus();
                  if (view != null) {
                      InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                      imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                  }
                  String temp=editpass.getText().toString().trim();
                  Spassword=String.valueOf(Html.fromHtml(temp));
                  if (Confirmpass.getText().toString().trim().equals(Spassword)) {
                      if (Spassword.isEmpty()) {
//                    editpass.setError("Please enter valid password");
//                    showToast("Please enter valid password");
                          errorpassword.setVisibility(View.VISIBLE);
                          editpass.post(new Runnable() {
                              @Override
                              public void run() {
                                  editpass.setFocusable(true);
                                  editpass.setFocusableInTouchMode(true);
                                  editpass.requestFocus();
                                  editpass.setImeOptions(EditorInfo.IME_ACTION_DONE);
                                  error=true;
                              }
                          });



                      } else {
                          if (Spassword.length() < 6) {
//                        editpass.setError("Password length must be greater than 6");
//                        showToast("Password needs  minimum 6 characters !");
                              errorpassword.setVisibility(View.VISIBLE);
                              editpass.post(new Runnable() {
                                  @Override
                                  public void run() {
                                      editpass.setFocusable(true);
                                      editpass.setFocusableInTouchMode(true);
//                                      editpass.requestFocus();
//                                      editpass.setImeOptions(EditorInfo.IME_ACTION_DONE);
                                      error = true;
                                  }
                              });


                          } else {
                              errorconfirmpassword.setVisibility(View.GONE);
                              errorpassword.setVisibility(View.GONE);
//                              final Calendar calender = Calendar.getInstance(TimeZone.getDefault());
//                              final int Year = calender.get(Calendar.YEAR);
//                              int yeartemp = Year - 18;
//                              final int Month = calender.get(Calendar.MONTH);
//                              int Date = calender.get(Calendar.DAY_OF_MONTH);
//                              String datechoose = yeartemp + "-" + (Month + 1) + "-" + Date;
                              DatePickerPopWin pickerPopWin = new DatePickerPopWin.Builder(RegistrationActivity.this, new DatePickerPopWin.OnDatePickedListener() {
                                  @Override
                                  public void onDatePickCompleted(int year, int month, int day, String dateDesc) {
//                        Toast.makeText(RegistrationActivity.this, dateDesc, Toast.LENGTH_SHORT).show();
                                      if (Year - year < 18) {
                                          showToast("You must be atleast 18 years old !", false);
                                      } else {
                                          String s[]=dateDesc.split("-");
                                          int m=Integer.parseInt(s[1]);
//                            showToast(dateDesc,true);
                                          String mon=theMonth(m-1);
                                          String temp=year+"-"+mon+"-"+s[2];
                                          Sdob=dateDesc;
                                          datechoose=dateDesc;

                                          editdob.setText(temp);
                                          editloc.requestFocus();

                                      }
                                  }
                              }).textConfirm("Done") //text of confirm button
                                      .textCancel("Cancel") //text of cancel button
                                      .btnTextSize(20) // button text size
                                      .viewTextSize(40) // pick view text size.
                                      .colorCancel(Color.parseColor("#000000")) //color of cancel button
                                      .colorConfirm(Color.parseColor("#000000"))//color of confirm button
                                      .minYear(1950) //min year in loop
                                      .maxYear(Year+1) // max year in loop
                                      .showDayMonthYear(true) // shows like dd mm yyyy (default is false)
                                      .dateChose(datechoose) // date chose when init popwindow
                                      .build();

//                pickerPopWin.setBackgroundDrawable(getResources().getDrawable(R.drawable.tags_rounded_corners));


                              pickerPopWin.showPopWin(RegistrationActivity.this);

                          }
                      }

                  }
                  else {
//                Confirmpass.setError("Password and confirm password must be same");
//                showToast("Password and confirm password does not match !");
                      errorconfirmpassword.setVisibility(View.VISIBLE);
//                      scrollingView.smoothScrollTo(0,370);
                      error=true;
//                      Confirmpass.requestFocus();
//                      Confirmpass.setImeOptions(EditorInfo.IME_ACTION_DONE);
//                      return false;
                  }

                  if (error)
                  {
                      return false;
                  }
                  else
                  {

                  }
              }
               return false;
          }
      });
//        editloc.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)||(actionId==EditorInfo.IME_ACTION_NEXT)) {
//                    View view = getCurrentFocus();
//                    if (view != null) {
//                        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
//                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
//                    }
//
//                }
//                return false;
//            }
//        });
//        editweight.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//
//                return false;
//            }
//        });
//        editheight.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                editheight.setFocusableInTouchMode(true);
//                return false;
//            }
//        });
//        editlookingfor.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                editlookingfor.setFocusableInTouchMode(true);
//                return false;
//            }
//        });
        Linearmale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Linearmale.setBackgroundColor(getResources().getColor(R.color.black));
                textmale.setTextColor(getResources().getColor(R.color.white));
                Linearfemale.setBackgroundColor(getResources().getColor(R.color.white));
                textfemale.setTextColor(getResources().getColor(R.color.black));
                gender="m";
                if (!imagedata){
                    if (fbid.isEmpty()) {
                        Picasso.with(RegistrationActivity.this).load(R.drawable.cmra).transform(new CircleTransform()).into(userpic);
                    }
                }




            }
        });
        Linearfemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Linearfemale.setBackgroundColor(getResources().getColor(R.color.black));
                textfemale.setTextColor(getResources().getColor(R.color.white));
                Linearmale.setBackgroundColor(getResources().getColor(R.color.white));
                textmale.setTextColor(getResources().getColor(R.color.black));
                gender="f";
                if (!imagedata){
                    if (fbid.isEmpty()) {
                        Picasso.with(RegistrationActivity.this).load(R.drawable.cmra).transform(new CircleTransform()).into(userpic);
                    }

                }

            }
        });
        likemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textlikemale.setTextColor(getResources().getColor(R.color.white));
                likemale.setBackgroundColor(getResources().getColor(R.color.black));

                likefemale.setBackgroundColor(getResources().getColor(R.color.white));
                likeboth.setBackgroundColor(getResources().getColor(R.color.white));
                textlikeboth.setTextColor(getResources().getColor(R.color.black));
                textlikefemale.setTextColor(getResources().getColor(R.color.black));
                iLike="m";
            }
        });
        likefemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                likefemale.setBackgroundColor(getResources().getColor(R.color.black));
                textlikefemale.setTextColor(getResources().getColor(R.color.white));
                likemale.setBackgroundColor(getResources().getColor(R.color.white));
                likeboth.setBackgroundColor(getResources().getColor(R.color.white));
                textlikeboth.setTextColor(getResources().getColor(R.color.black));
                textlikemale.setTextColor(getResources().getColor(R.color.black));
                iLike="f";
            }
        });
        likeboth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                likeboth.setBackgroundColor(getResources().getColor(R.color.black));
                textlikeboth.setTextColor(getResources().getColor(R.color.white));
                likemale.setBackgroundColor(getResources().getColor(R.color.white));
                likefemale.setBackgroundColor(getResources().getColor(R.color.white));
                textlikemale.setTextColor(getResources().getColor(R.color.black));
                textlikefemale.setTextColor(getResources().getColor(R.color.black));
                iLike="mf";
            }
        });
        userpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFileTemp= Util.createFile(RegistrationActivity.this);
                pickPhotoDialog();
            }
        });
//        final Calendar calender=Calendar.getInstance(TimeZone.getDefault());
//        final int Year=calender.get(Calendar.YEAR);
//        int yeartemp=Year-18;
//        final int Month=calender.get(Calendar.MONTH);
//        int Date=calender.get(Calendar.DAY_OF_MONTH);
//        datechoose=yeartemp+"-"+(Month+1)+"-"+Date;
        editdob.setFocusable(false);
        ((RadioButton)rg.getChildAt(0)).setChecked(true);
        editdob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                errordob.setVisibility(View.GONE);

//                DatePickerDialog datepicker=new DatePickerDialog(RegistrationActivity.this, new DatePickerDialog.OnDateSetListener() {
//                    @Override
//                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                        if (Year-year<18)
//                        {
////                            showSnackBar("You should be above 18 years",false);
//                            showToast("You must be atleast 18 years old !");
//                        }
//                        else {
//                            editdob.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
//                        }
//
//
//
//                    }
//                },yeartemp,Month,Date);
//                datepicker.setTitle("Select date");
//                datepicker.show();
                DatePickerPopWin pickerPopWin = new DatePickerPopWin.Builder(RegistrationActivity.this, new DatePickerPopWin.OnDatePickedListener() {
                    @Override
                    public void onDatePickCompleted(int year, int month, int day, String dateDesc) {
//                        Toast.makeText(RegistrationActivity.this, dateDesc, Toast.LENGTH_SHORT).show();
                        if (Year-year<18)
                        {
                            showToast("You must be atleast 18 years old !",false);
                        }
                        else
                        {
                            String s[]=dateDesc.split("-");
                            int m=Integer.parseInt(s[1]);
//                            showToast(dateDesc,true);
                            String mon=theMonth(m-1);
                            String temp=year+"-"+mon+"-"+s[2];
                            Sdob=dateDesc;
                            datechoose=dateDesc;

                            editdob.setText(temp);
//                            editdob.setText(year + "-" + (month) + "-" + day);
                            editloc.requestFocus();

                        }
                    }
                }).textConfirm("Done") //text of confirm button
                        .textCancel("Cancel") //text of cancel button
                        .btnTextSize(20) // button text size
                        .viewTextSize(40) // pick view text size.
                        .colorCancel(Color.parseColor("#000000")) //color of cancel button
                        .colorConfirm(Color.parseColor("#000000"))//color of confirm button
                        .minYear(1950) //min year in loop
                        .maxYear(Year+1) // max year in loop
                        .showDayMonthYear(true) // shows like dd mm yyyy (default is false)
                        .dateChose(datechoose) // date chose when init popwindow
                        .build();

//                pickerPopWin.setBackgroundDrawable(getResources().getDrawable(R.drawable.tags_rounded_corners));


                pickerPopWin.showPopWin(RegistrationActivity.this);
            }
        });
//        editloc.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                try {
//                    PlacePicker.IntentBuilder intentBuilder =
//                            new PlacePicker.IntentBuilder();
//                    intentBuilder.setLatLngBounds(BOUNDS_MOUNTAIN_VIEW);
//
//                    Intent intent = intentBuilder.build(( getApplicationContext()));
//                    startActivityForResult(intent, PLACE_PICKER_REQUEST);
//                } catch (GooglePlayServicesRepairableException e) {
//                    e.printStackTrace();
//                } catch (GooglePlayServicesNotAvailableException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
        editheight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                LayoutInflater layoutInflater = (LayoutInflater)getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
//                View popupView = layoutInflater.inflate(R.layout.popupwindowheight, null);
//                final PopupWindow popupWindow = new PopupWindow(popupView,android.view.ViewGroup.LayoutParams.WRAP_CONTENT,android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
//
//                popupWindow.setOutsideTouchable(false);
//                popupWindow.setFocusable(true);
//                popupWindow.update();
//                View view = getCurrentFocus();
//                if (view != null) {
//                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
//                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
//                }
//                spinheight.setVisibility(View.VISIBLE);
//                spinheight.performClick();
                Intent intent= new Intent(RegistrationActivity.this,POPUPHEIGHT.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivityForResult(intent,EDITHEIGHT);
                overridePendingTransition(0,0);
            }
        });
        spinheight.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position!=0)
                {
                    editheight.setText(listHeight.get(position).getName());
                    spinheight.setVisibility(View.INVISIBLE);
                }
                else {
                    spinheight.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        editweight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(RegistrationActivity.this,PopUpWeiight.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivityForResult(intent,EDITWEIGHT);
                overridePendingTransition(0,0);
//                View view = getCurrentFocus();
//                if (view != null) {
//                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
//                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
//                }
//                spinweight.setVisibility(View.VISIBLE);
//                spinweight.performClick();
            }
        });
        spinweight.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position!=0)
                {
                    editweight.setText(listweight.get(position).getName());
                    spinweight.setVisibility(View.INVISIBLE);
                }
                else {
                    spinweight.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        textViewPrivacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFragmentManager = getSupportFragmentManager();
                mFragmentTransaction = mFragmentManager.beginTransaction();
                PrivacyPolicy privacyPolicy=PrivacyPolicy.newInstance();
                privacyPolicy.show(mFragmentTransaction,"POP_UP_PRIVACY");
            }
        });

//        editethnicity.setText("Ethnicity:");
        editethnicity.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    View view = getCurrentFocus();
                    if (view != null) {
                        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }

                }
                return false;

            }
        });
        imageViewSpinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editethnicity.post(new Runnable() {
                    @Override
                    public void run() {
                        editethnicity.setFocusable(false);

                        editethnicity.setFocusableInTouchMode(false);
                        editethnicity.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                editethnicity.setFocusableInTouchMode(false);
                                return false;
                            }
                        });
                    }
                });
                editethnicity.setText("Ethnicity:");
                others=false;
                spinethnic.setVisibility(View.VISIBLE);
                spinethnic.performClick();
                selected=true;


            }
        });

        spinethnic.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                pos=position;
              if(selected) {
                  if(position!=0) {
                      if(!listitems.get(position).getName().equals("Other")){

                          EthnicityId = list.get(position-1).getEthnicityId();
                          String name = list.get(position-1).getEthnicityTitle();
//                    showToast(EthnicityTitle);
                          editethnicity.setText(name);


                          spinethnic.setVisibility(View.INVISIBLE);
                          selected = false;
                      }
                      else {
                          spinethnic.setVisibility(View.INVISIBLE);
                          others=true;
                          EthnicityId="0";

                          editethnicity.post(new Runnable() {
                              @Override
                              public void run() {
                                  editethnicity.setFocusable(true);

                                  editethnicity.setFocusableInTouchMode(true);
                                  editethnicity.requestFocus();
                                  InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                  imm.showSoftInput(editethnicity, InputMethodManager.SHOW_IMPLICIT);
                              }
                          });

                          editethnicity.setHint("Please specify");
                          editethnicity.setText("");
                          editethnicity.setOnTouchListener(new View.OnTouchListener() {
                              @Override
                              public boolean onTouch(View v, MotionEvent event) {
                                  editethnicity.setFocusableInTouchMode(true);
                                  return false;
                              }
                          });




                      }
                  }


              }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        editethnicity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!others) {
                    try {


                        spinethnic.setVisibility(View.VISIBLE);
                        adapter.notifyDataSetChanged();


                        spinethnic.performClick();

                        selected = true;
                    }catch (Exception e)
                    {

                    }
                }


            }
        });
//        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
//                int pos=rg.indexOfChild(findViewById(checkedId));
//                switch (pos)
//                {
//                    case 0:iLike="m";
//                        break;
//                    case 1:iLike="f";
//                        break;
//                    case 2:iLike="mf";
//                        break;
//                    default:
//                        iLike="m";
//                        break;
//                }
//
//
//            }
//        });
        reg_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//        Intent intent=new Intent(RegistrationActivity.this,Registration_extended.class);
//           startActivity(intent);
//               UploadImageTask(f);
                try {
                    View view = getCurrentFocus();
                    if (view != null) {
                        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                }catch (Exception e)
                {}
           checkRegistration();
           }
        });

    }
    public static String theMonth(int month){
        String[] monthNames = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        return monthNames[month];
    }
    private void checkRegistration() {
        if(checkname()&&checklastname()&&checkUsername()&&checkemail()&&checkpassword()&&checkdob()&&checkloc()&&checkheight()&&checkweight()&&checkethnicity()&&checkrequired()&&checkgender()&&checkilike()&&checkprivacy()&&checkpic())
        {
            submitreg();
//            showToast(iLike);
        }
    }

    private boolean checkpic() {
        if (userpic.getDrawable()==null)
        {
            scrollingView.fullScroll(View.FOCUS_UP);
            showToast("Please select a profile picture to continue",false);
            return false;

        }
        else
        {
            return true;
        }


    }

    private boolean checkilike() {
        if (iLike.isEmpty())
        {
            scrollingView.fullScroll(View.FOCUS_UP);
//            StyleableToast.makeText(this, "Please select like preference", Toast.LENGTH_LONG, R.style.myStyle).show();
           showToast("Please select you like male, female or both",false);
            return false;
        }
        else {
            return true;
        }

    }

    private boolean checkgender() {
        if (gender.isEmpty())
        {
//            StyleableToast.makeText(this, "Please select gender", Toast.LENGTH_LONG, R.style.StyledToast).show();
            scrollingView.smoothScrollTo(0,0);
          showToast("Please select your gender",false);
            return false;
        }
        else {
            return true;
        }

    }



    private boolean checkpassword() {



        String temp=editpass.getText().toString().trim();
        Spassword=String.valueOf(Html.fromHtml(temp));
        if (fbid.isEmpty()) {
            if (Confirmpass.getText().toString().trim().equals(Spassword)) {
                if (Spassword.isEmpty()) {
//                    editpass.setError("Please enter valid password");
//                    showToast("Please enter valid password");
                    errorpassword.setVisibility(View.VISIBLE);
                    editpass.post(new Runnable() {
                        @Override
                        public void run() {
                            editpass.setFocusable(true);
                            editpass.setFocusableInTouchMode(true);
                            editpass.requestFocus();
                            editpass.setImeOptions(EditorInfo.IME_ACTION_DONE);
                        }
                    });
                    scrollingView.smoothScrollTo(0,320);

                    return false;
                } else {
                    if (Spassword.length() < 6) {
//                        editpass.setError("Password length must be greater than 6");
//                        showToast("Password needs  minimum 6 characters !");
                        errorpassword.setVisibility(View.VISIBLE);
                        editpass.post(new Runnable() {
                            @Override
                            public void run() {
                                editpass.setFocusable(true);
                                editpass.setFocusableInTouchMode(true);
                                editpass.requestFocus();
                                editpass.setImeOptions(EditorInfo.IME_ACTION_DONE);
                            }
                        });
                        scrollingView.smoothScrollTo(0,320);
                        return false;

                    } else {
                        return true;
                    }
                }
            }
            else {
//                Confirmpass.setError("Password and confirm password must be same");
//                showToast("Password and confirm password does not match !");
                errorconfirmpassword.setVisibility(View.VISIBLE);
                scrollingView.smoothScrollTo(0,370);
                error=true;
                Confirmpass.requestFocus();
                Confirmpass.setImeOptions(EditorInfo.IME_ACTION_DONE);
                return false;
            }
        }
        else {
            return true;
        }
    }

    private boolean checklastname() {
        String temp=editTextlastname.getText().toString().trim();
        Lname=String.valueOf(Html.fromHtml(temp));
        if (Lname.isEmpty())
        {
//            editTextlastname.setError("Please enter last name");
//            showToast("Please enter last name");
            errorlastname.setVisibility(View.VISIBLE);
            editTextlastname.post(new Runnable() {
                @Override
                public void run() {
                    editTextlastname.setFocusable(true);
                    editTextlastname.setFocusableInTouchMode(true);
                    editTextlastname.requestFocus();
                    editTextlastname.setImeOptions(EditorInfo.IME_ACTION_DONE);
                }
            });
            scrollingView.smoothScrollTo(0,170);

            return false;
        }
       else
        {
            return true;
        }
    }

    private boolean checkprivacy() {
        if (checkBoxprivacy.isChecked())
        {
            return true;
        }
        else {
            scrollingView.fullScroll(View.FOCUS_DOWN);
            showSnackBar("Please accept Terms and Conditions",false);
            return false;
        }
    }



    private boolean checkrequired() {

        Slookingfor=editlookingfor.getText().toString().trim();


        return true;
    }

    private boolean checkethnicity() {
//        Sethnicity=listitems.get(pos).getName();
        Sethnicity=editethnicity.getText().toString().trim();
        return true;
//        if (EthnicityId.equals("0"))
//        {
//            if (!Sethnicity.isEmpty())
//            {
//                EthnicityTitle=Sethnicity;
//                return true;
//            }
//            else {
//                showToast("Please Select Ethnicity");
//                return false;
//            }
//        }
//        else {
//            if (Sethnicity.equals("ETHNICITY"))
//            {
//                showToast("Please select Ethnicity");
//                return false;
//            }
//            else {
//                return true;
//            }
//        }



    }

    private boolean checkheight() {
        String temp=editheight.getText().toString().trim();
        if (!temp.isEmpty())
        {
//            Sheight=temp.substring(0,temp.indexOf(" "));
            Sheight=temp;
        }


//        {
//            editheight.setError("please Enter Weight");
//            return false;
//        }

//        else
//        {
            return true;
//        }
    }

    private boolean checkweight() {
        String temp=editweight.getText().toString().trim();
        if (!temp.isEmpty())
        {
//            Sweight=temp.substring(0,temp.indexOf(" "));
            Sweight=temp;
        }
//        if (Sweight.isEmpty())
//        {
//            editweight.setError("please enter weight");
//            return false;
//        }
//        else
//        {
            return true;
//        }
    }

    private boolean checkloc() {
        String temp= editloc.getText().toString().trim();
        Sloc=String.valueOf(Html.fromHtml(temp));
        if (Sloc.isEmpty())
        {

//            editloc.setError("please select location");
//            showToast("Please enter where you live !");
            errorlocation.setVisibility(View.VISIBLE);
            editloc.requestFocus();
            editloc.setImeOptions(EditorInfo.IME_ACTION_DONE);
            scrollingView.smoothScrollTo(0,520);
            return false;
        }
        else
        {
            cityname=Sloc;
            return true;
        }
    }

    private boolean checkdob() {
       String Sdob=editdob.getText().toString().trim();
        if (Sdob.isEmpty())
        {
//            editdob.setError("please select date of birth");
//            showToast("Please select date of birth !");
            errordob.setVisibility(View.VISIBLE);
            editdob.requestFocus();

            scrollingView.smoothScrollTo(0,420);
            return false;
        }
        else
        {
            return true;
        }
    }

    private boolean checkemail() {
        String temp=editemail.getText().toString().trim();
        Semail=String.valueOf(Html.fromHtml(temp));
        if (Semail.isEmpty())
        {
//            editemail.setError("please enter valid email");
//            showToast("Invalid Email Address !");
            erroremail.setVisibility(View.VISIBLE);
            editemail.post(new Runnable() {
                @Override
                public void run() {
                    editemail.setFocusable(true);
                    editemail.setFocusableInTouchMode(true);

                    editemail.requestFocus();
                    editemail.setImeOptions(EditorInfo.IME_ACTION_DONE);
                }
            });
            scrollingView.smoothScrollTo(0,270);

            return false;
        }
        else {
            if (isValidEmail(Semail))
            {
                boolean correct=false;
                String[]a=Semail.split("@");
                String temp1=a[1];

                String temp2=temp1;
                if (null!=temp1&&temp1.length()>0)
                {

                    String temp3=temp2.substring(temp2.lastIndexOf(".")+1);
                    temp2=temp3;

                }
//                showToast(temp2);
                String[] b={"aero", "asia", "biz", "cat", "com", "coop", "edu", "gov", "info", "int", "jobs", "mil", "mobi", "museum", "name", "net", "org", "pro", "tel", "travel", "ac", "ad", "ae", "af", "ag", "ai", "al", "am", "an", "ao", "aq", "ar", "as", "at", "au", "aw", "ax", "az", "ba", "bb", "bd", "be", "bf", "bg", "bh", "bi", "bj", "bm", "bn", "bo", "br", "bs", "bt", "bv", "bw", "by", "bz", "ca", "cc", "cd", "cf", "cg", "ch", "ci", "ck", "cl", "cm", "cn", "co", "cr", "cu", "cv", "cx", "cy", "cz", "de", "dj", "dk", "dm", "do", "dz", "ec", "ee", "eg", "er", "es", "et", "eu", "fi", "fj", "fk", "fm", "fo", "fr", "ga", "gb", "gd", "ge", "gf", "gg", "gh", "gi", "gl", "gm", "gn", "gp", "gq", "gr", "gs", "gt", "gu", "gw", "gy", "hk", "hm", "hn", "hr", "ht", "hu", "id", "ie", " No", "il", "im", "in", "io", "iq", "ir", "is", "it", "je", "jm", "jo", "jp", "ke", "kg", "kh", "ki", "km", "kn", "kp", "kr", "kw", "ky", "kz", "la", "lb", "lc", "li", "lk", "lr", "ls", "lt", "lu", "lv", "ly", "ma", "mc", "md", "me", "mg", "mh", "mk", "ml", "mm", "mn", "mo", "mp", "mq", "mr", "ms", "mt", "mu", "mv", "mw", "mx", "my", "mz", "na", "nc", "ne", "nf", "ng", "ni", "nl", "no", "np", "nr", "nu", "nz", "om", "pa", "pe", "pf", "pg", "ph", "pk", "pl", "pm", "pn", "pr", "ps", "pt", "pw", "py", "qa", "re", "ro", "rs", "ru", "rw", "sa", "sb", "sc", "sd", "se", "sg", "sh", "si", "sj", "sk", "sl", "sm", "sn", "so", "sr", "st", "su", "sv", "sy", "sz", "tc", "td", "tf", "tg", "th", "tj", "tk", "tl", "tm", "tn", "to", "tp", "tr", "tt", "tv", "tw", "tz", "ua", "ug", "uk", "us", "uy", "uz", "va", "vc", "ve", "vg", "vi", "vn", "vu", "wf", "ws", "ye", "yt", "za", "zm", "zw"};
                for (int i=0;i<b.length;i++)
                {
                    if (temp2.equals(b[i]))
                    {
                        correct=true;

                    }
                }
                if (correct)
                { return true;

                }
                else {

                    erroremail.setVisibility(View.VISIBLE);

                    editemail.post(new Runnable() {
                        @Override
                        public void run() {
                            editemail.setFocusable(true);

                            editemail.setFocusableInTouchMode(true);

                            editemail.requestFocus();
                            editemail.setImeOptions(EditorInfo.IME_ACTION_DONE);
                        }
                    });
                    scrollingView.smoothScrollTo(0,270);
                    return false;
                }
            }
            else {
                erroremail.setVisibility(View.VISIBLE);

                editemail.post(new Runnable() {
                    @Override
                    public void run() {
                        editemail.setFocusable(true);

                        editemail.setFocusableInTouchMode(true);

                        editemail.requestFocus();
                        editemail.setImeOptions(EditorInfo.IME_ACTION_DONE);
                    }
                });
                scrollingView.smoothScrollTo(0,270);
                return false;
            }
        }
    }

    private boolean checkname() {
        String temp=editTextfirstname.getText().toString().trim();
        Fname=String.valueOf(Html.fromHtml(temp));
        if (Fname.isEmpty())
        {
            errorFirstname.setVisibility(View.VISIBLE);


//            editTextfirstname.setError("Please enter first name");
            editTextfirstname.post(new Runnable() {
                @Override
                public void run() {
                    editTextfirstname.setFocusable(true);
                    editTextfirstname.setFocusableInTouchMode(true);
                    editTextfirstname.requestFocus();
                    editTextfirstname.setImeOptions(EditorInfo.IME_ACTION_DONE);

                }
            });

            scrollingView.smoothScrollTo(0,120);
            return false;
        }
        else {
            return true;
        }
    }
    private boolean checkUsername() {
        String temp=editTextUsername.getText().toString().trim();
        SuserName= String.valueOf(Html.fromHtml(temp));
//        showToast(SuserName);
        if (SuserName.isEmpty())
        {
//            editTextUsername.setError("Please enter user name");
//            showToast("Please enter user name");
            errorusername.setVisibility(View.VISIBLE);
            editTextUsername.post(new Runnable() {
                @Override
                public void run() {
                    editTextUsername.requestFocus();
                    editTextUsername.setFocusable(true);
                    editTextUsername.setFocusableInTouchMode(true);
                    editTextUsername.setImeOptions(EditorInfo.IME_ACTION_DONE);
                }
            });
            scrollingView.smoothScrollTo(0,220);

            return false;
        }
        else
//        {Pattern pattern = Pattern.compile("\\s");
//            Matcher matcher = pattern.matcher(SuserName);
//            boolean found = matcher.find();
//            if (found)
//            {
//
//            }
            return true;


    }
    @Override
    protected void onResume() {
        super.onResume();
        int PERMISSION_ALL = 1;
        String[] PERMISSIONS = {Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
        if(!hasPermissions(this, PERMISSIONS)){
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }
        if (others)
        {
            editethnicity.setFocusable(true);

        }

    }
    public static boolean hasPermissions(Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
    private void initui() {
        TextView youcanalways=(TextView)findViewById(R.id.you_can_always);
        taphere= (TextView) findViewById(R.id.textTapHere);
        shadowimage= (CircleImageView) findViewById(R.id.shadowimage);
        userpic= (ImageView) findViewById(R.id.user_profilepic);
        Picasso.with(RegistrationActivity.this).load(R.drawable.cmra).transform(new CircleTransform()).into(userpic);
        iam= (TextView) findViewById(R.id.iam);
        picedit= (ImageView) findViewById(R.id.imageEdit);
        Linearfemale= (LinearLayout) findViewById(R.id.layout_female);
        Linearmale= (LinearLayout) findViewById(R.id.layout_male);
        textfemale= (TextView) findViewById(R.id.textfemale);
        textmale= (TextView) findViewById(R.id.textMale);
        textilike= (TextView) findViewById(R.id.textilike);
        textloooking= (TextView) findViewById(R.id.textiamlookingfor);
        editemail= (EditText) findViewById(R.id.editTextemail);
        editpass= (EditText) findViewById(R.id.editTextpass);
        editdob= (EditText) findViewById(R.id.editTextDob);
        editloc= (EditText) findViewById(R.id.editTextlocation);
        rg= (RadioGroup) findViewById(radio);

        editheight= (EditText) findViewById(R.id.editTextHeight);
        editweight= (EditText) findViewById(R.id.editTextWeight);
        editethnicity= (EditText) findViewById(R.id.editTextehnic);
        editlookingfor= (EditText) findViewById(R.id.editTextiamLookingfor);
        spinethnic= (AppCompatSpinner) findViewById(R.id.spinnerEthnicity);
        spinheight= (AppCompatSpinner) findViewById(R.id.spinnerHeight);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(spinheight);
            int h=height/2;

            // Set popupWindow height to 500px
            popupWindow.setHeight(h-20);
        }
        catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }
        spinweight= (AppCompatSpinner) findViewById(R.id.spinnerweight);
        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(spinweight);
            int h=height/2;
            // Set popupWindow height to 500px
            popupWindow.setHeight(h-20);
        }
        catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }
        reg_next= (ImageView) findViewById(R.id.register_next);
        Confirmpass= (EditText) findViewById(R.id.editTextconfirmpass);
        editTextfirstname= (EditText) findViewById(R.id.editextfirstName);
        editTextlastname= (EditText) findViewById(R.id.editextlastname);
        checkBoxprivacy= (CheckBox) findViewById(R.id.checkboxPrivacy);
        textViewPrivacy= (TextView) findViewById(R.id.textprivacypolicy);
        Typeface typeface=Typeface.createFromAsset(getAssets(),"fonts/brandon_grotesque_bold.ttf");
        Typeface typeface1=Typeface.createFromAsset(getAssets(),"fonts/BrandonGrotesque-Regular.ttf");
        youcanalways.setTypeface(typeface1);
        Confirmpass.setTypeface(typeface1);
        textilike.setTypeface(typeface1);
        textloooking.setTypeface(typeface1);
        editTextfirstname.setTypeface(typeface1);
        editTextlastname.setTypeface(typeface1);
        editemail.setTypeface(typeface1);
        editpass.setTypeface(typeface1);
        editdob.setTypeface(typeface1);
        editloc.setTypeface(typeface1);
        editheight.setTypeface(typeface1);
        editweight.setTypeface(typeface1);
        editlookingfor.setTypeface(typeface1);
        iam.setTypeface(typeface1);
        editTextUsername= (EditText) findViewById(R.id.editTextusername);
        editTextUsername.setTypeface(typeface1);
        editethnicity.setTypeface(typeface1);
        likemale= (LinearLayout) findViewById(R.id.layout_like_male);
        likefemale= (LinearLayout) findViewById(R.id.layout_like_female);
        likeboth= (LinearLayout) findViewById(R.id.layout_like_both);
        textlikemale= (TextView) findViewById(R.id.text_like_Male);
        textlikefemale= (TextView) findViewById(R.id.text_like_female);
        textlikeboth= (TextView) findViewById(R.id.text_like_both);
        textheadlike= (TextView) findViewById(R.id.ilike);
        textlikeboth.setTypeface(typeface);
        textfemale.setTypeface(typeface);
        textmale.setTypeface(typeface);
        textViewPrivacy.setTypeface(typeface1);
        textlikefemale.setTypeface(typeface);
        textlikemale.setTypeface(typeface);
        textheadlike.setTypeface(typeface1);
        imageViewSpinner= (ImageView) findViewById(R.id.imageSpinner);
        taphere.setTypeface(typeface1);
        errorFirstname= (RelativeLayout) findViewById(R.id.errorfirstname);
        errorlastname= (RelativeLayout) findViewById(R.id.errorlastname);
        errorusername= (RelativeLayout) findViewById(R.id.errorusername);
        erroremail= (RelativeLayout) findViewById(R.id.erroremail);
        errorpassword= (RelativeLayout) findViewById(R.id.errorpassword);
        errorconfirmpassword= (RelativeLayout) findViewById(R.id.errorconfirmpassword);
        errordob= (RelativeLayout) findViewById(R.id.errordob);
        errorlocation= (RelativeLayout) findViewById(R.id.errorlocation);
        scrollingView= (ScrollView) findViewById(R.id.scroll);

        InputFilter filter = new InputFilter() {
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                for (int i = start; i < end; i++) {
                    if (Character.isWhitespace(source.charAt(i))) {
                        return "";
                    }
                }
                return null;
            }

        };
        editTextUsername.setFilters(new InputFilter[]{filter});

    }
    private void submitreg() {

//        final ProgressDialog pd = new ProgressDialog(this);
//        pd.setMessage("Loading");
//        pd.setCancelable(false);
//        pd.show();
//        progressDialog.show();
        showProgress();

//        builder= new MaterialDialog.Builder(this)
//                .backgroundColor(Color.TRANSPARENT)
//                .backgroundColorRes(R.color.thistle)
//                .content(R.string.please_wait)
//                .progress(true, 0)
//                .show();
        HashMap<String, String> params = new HashMap<>();
        params.put("user_email", Semail);
        params.put("firstname", Fname);
        params.put("lastname",Lname);
        params.put("username",SuserName);
        params.put("dob", Sdob);
        if (!fbid.isEmpty()) {
            params.put("fb_id", fbid);
        } else {
            params.put("user_password", Spassword);
        }
        params.put("location", cityname);
//        params.put("latitude",Lat);
//        params.put("longitude",LONG);
        params.put("gender", gender);

        params.put("height", Sheight);
        params.put("preference", iLike);
        if (EthnicityId.equals("0")) {
            params.put("ethnicity_id", EthnicityId);
            params.put("other_ethinicity", Sethnicity);
        } else {
            params.put("ethnicity_id", EthnicityId);
        }

        params.put("weight", Sweight);

//        params.put("looking_for",Slookingfor);
//        params.put("preference",iLike);
        APIService service = ServiceGenerator.createService(APIService.class, RegistrationActivity.this);
        if (!fbid.isEmpty()) {
            Call<ModelRegister> call = service.fbregister(params);
            call.enqueue(new Callback<ModelRegister>() {
                @Override
                public void onResponse(Call<ModelRegister> call, Response<ModelRegister> response) {
                    if (response.isSuccessful()) {
//                        progressDialog.dismiss();
                     hideProgress();
                        if (response.body() != null && response.body().getResult() != null) {
                            ModelRegister register = response.body();
                            String msg = register.getResult().getMessage();
                            String id = register.getResult().getUserId();
                            int value = register.getResult().getValue();
                            if (value == 1) {
//                                showToast(msg);
//                                UserLoginTask.TaskListener listener=new UserLoginTask.TaskListener() {
//                                    @Override
//                                    public void onSuccess(RegistrationResponse registrationResponse, Context context) {
//
//                                        ApplozicClient.getInstance(context).setContextBasedChat(true).setHandleDial(true).setIPCallEnabled(true);
//                                        Map<ApplozicSetting.RequestCode, String> activityCallbacks = new HashMap<ApplozicSetting.RequestCode, String>();
//                                        activityCallbacks.put(ApplozicSetting.RequestCode.AUDIO_CALL, AudioCallActivityV2.class.getName());
//                                        activityCallbacks.put(ApplozicSetting.RequestCode.VIDEO_CALL, VideoActivity.class.getName());
//                                        ApplozicSetting.getInstance(context).setActivityCallbacks(activityCallbacks);
////                                    ApplozicSetting.getInstance(context).setChatBackgroundColorOrDrawableResource(R.drawable.bg_splash);
////                                    ApplozicSetting.getInstance(context).setSentMessageBackgroundColor(R.color.white);
////                                    ApplozicSetting.getInstance(context).setReceivedMessageBackgroundColor(R.color.sentmessage_black);
////                                    ApplozicSetting.getInstance(context).setSentMessageTextColor(R.color.black);
////                                    ApplozicSetting.getInstance(context).setReceivedMessageTextColor(R.color.white);
//                                        PushNotificationTask.TaskListener pushNotificationTaskListener=  new PushNotificationTask.TaskListener() {
//                                            @Override
//                                            public void onSuccess(RegistrationResponse registrationResponse) {
//
//                                            }
//
//                                            @Override
//                                            public void onFailure(RegistrationResponse registrationResponse, Exception exception) {
//
//                                            }
//                                        };
//                                        PushNotificationTask pushNotificationTask = new PushNotificationTask(Applozic.getInstance(context).getDeviceRegistrationId(),pushNotificationTaskListener,context);
//                                        pushNotificationTask.execute((Void)null);
//                                    }
//
//                                    @Override
//                                    public void onFailure(RegistrationResponse registrationResponse, Exception exception) {
//
//                                    }
//                                };
//                                User user = new User();
//                                user.setUserId(id);
//                                user.setDisplayName(SuserName);
//                                user.setAuthenticationTypeId(User.AuthenticationType.APPLOZIC.getValue());
//                                List<String> featureList =  new ArrayList<>();
//                                featureList.add(User.Features.IP_AUDIO_CALL.getValue());// FOR AUDIO
//                                featureList.add(User.Features.IP_VIDEO_CALL.getValue());// FOR VIDEO
//                                user.setFeatures(featureList);
//
//                                mAuthTask = new UserLoginTask(user, listener, RegistrationActivity.this);
//                                mAuthTask.execute((Void) null);
//                                savePref(Constants.GENDER,gender);
//                                savePref(Constants.USER_ID,id);
//                                savePref(Constants.PASSWORD,Spass);
//                                savePref(Constants.FBREGISTRATION,false);
//                                savePref(Constants.EMAIL_REGISTRATION,true);
                                savePref(Constants.TAG_ISLOGGED_IN,true);
                                savePref(Constants.ILIKE,iLike);
//                                Intent intent=new Intent(LoginAct.this,HomeActivity.class);
//                                startActivity(intent);
                                savePref(Constants.USER_ID, id);
                                savePref(Constants.USERNAME,SuserName);
                                userID = id;
                                savePref(Constants.GENDER, gender);
                                if (fbid.isEmpty()) {
                                    savePref(Constants.EMAIL_REGISTRATION, true);
                                    savePref(Constants.FBREGISTRATION, false);
                                } else {
                                    savePref(Constants.EMAIL_REGISTRATION, false);
                                    savePref(Constants.FBREGISTRATION, true);
                                    savePref(Constants.PASSWORD,Spassword);
                                }
                                try{
                                    if (f.length() != 0) {
                                        UploadImageTask(f);
                                    }
                                }catch (Exception e)
                                {

                                }

                                Intent intent = new Intent(RegistrationActivity.this, Registration_2_Activity.class);
                                startActivity(intent);
                            } else {
                                showToast(msg,false);
                            }

                        }
                    }
                }

                @Override
                public void onFailure(Call<ModelRegister> call, Throwable t) {
                    showLog(t.toString(), 2);
//                    builder.dismiss();
                    hideProgress();
//                    progressDialog.dismiss();
                }
            });
        } else {
            Call<ModelRegister> call = service.register(params);
            call.enqueue(new Callback<ModelRegister>() {
                @Override
                public void onResponse(Call<ModelRegister> call, Response<ModelRegister> response) {
                    if (response.isSuccessful()) {
//                        progressDialog.dismiss();
//                        builder.dismiss();
//                        hideProgress();
                        if (response.body() != null && response.body().getResult() != null) {
                            ModelRegister register = response.body();
                            String msg = register.getResult().getMessage();
                            String id = register.getResult().getUserId();
                            int value = register.getResult().getValue();
                            if (value == 1) {
//                                showToast(msg);
                                savePref(Constants.ILIKE,iLike);
                                savePref(Constants.USER_ID, id);
                                savePref(Constants.USERNAME,SuserName);
                                userID = id;
                                savePref(Constants.TAG_ISLOGGED_IN,true);
                                savePref(Constants.GENDER, gender);
                                if (fbid.isEmpty()) {
                                    savePref(Constants.EMAIL_REGISTRATION, true);
                                    savePref(Constants.FBREGISTRATION, false);
                                } else {
                                    savePref(Constants.EMAIL_REGISTRATION, false);
                                    savePref(Constants.FBREGISTRATION, true);
                                }
                                try{
                                    if (f.length() != 0) {
                                        UploadImageTask(f);
                                    }
                                }catch (Exception e)
                                {

                                }


                            } else {
                                hideProgress();
                                showToast(msg,false);
                            }

                        }
                    }
                }

                @Override
                public void onFailure(Call<ModelRegister> call, Throwable t) {
                    showLog(t.toString(), 2);
//                    builder.dismiss();
                    hideProgress();
//                    progressDialog.dismiss();
                }
            });
        }
    }
    void pickPhotoDialog() {
//        String[] item = { "Take picture", "Upload from library" };
//        AlertDialog.Builder builder = new AlertDialog.Builder(RegistrationActivity.this, R.style.AppCompatAlertDialogStyle);
//
//        builder.setTitle("UPLOAD IMAGE").setItems(item,
//                new DialogInterface.OnClickListener() {
//
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//                        switch (which) {
//                            case 0:
//                                takePicture();
//                                break;
//                            case 1:
//
//                                openGallery();
//
//
//                                break;
//                            default:
//                                break;
//                        }
//                    }
//
//
//                }).setNegativeButton("CANCEL",null);
//        builder.show();
        Intent intent= new Intent(RegistrationActivity.this,PopupEditImage.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivityForResult(intent,IMAGE_EDIT);
        overridePendingTransition(0,0);
    }
    private void takePicture() {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        try {

            String state = Environment.getExternalStorageState();
            if (Environment.MEDIA_MOUNTED.equals(state)) {
                mImageCaptureUri = Uri.fromFile(mFileTemp);
            }
            else {
                mImageCaptureUri = InternalStorageContentProvider.CONTENT_URI;
            }
            intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
            intent.putExtra("return-data", true);
            startActivityForResult(intent, REQUEST_CODE_TAKE_PICTURE);
        } catch (ActivityNotFoundException e) {

//            Log.d(TAG, "cannot take picture", e);
        }
    }
    private void openGallery() {

        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, REQUEST_CODE_GALLERY);
    }
    private void startCropImage(Uri uri) {
        com.theartofdev.edmodo.cropper.CropImage.activity(uri).setGuidelines(CropImageView.Guidelines.ON).start(this);

//        Intent intent = new Intent(RegistrationActivity.this, CropImage.class);
//        intent.putExtra(CropImage.IMAGE_PATH, mFileTemp.getPath());
//        intent.putExtra(CropImage.SCALE, true);
//
//        intent.putExtra(CropImage.ASPECT_X, 5);
//        intent.putExtra(CropImage.ASPECT_Y, 5);
//
//        startActivityForResult(intent, REQUEST_CODE_CROP_IMAGE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode)
        {
            case REQUEST_CODE_GALLERY:
                if (resultCode == Activity.RESULT_OK) {
                    try {
                        imagedata=true;
                        Uri uri=data.getData();

                        InputStream inputStream = getContentResolver().openInputStream(data.getData());
                        FileOutputStream fileOutputStream = new FileOutputStream(mFileTemp);
                        copyStream(inputStream, fileOutputStream);
                        fileOutputStream.close();
                        inputStream.close();

                        startCropImage(uri);

                    } catch (Exception e) {

                    }
                }
                break;
            case REQUEST_CODE_TAKE_PICTURE:

                if (resultCode == Activity.RESULT_OK) {
                    imagedata=true;
                   startCropImage(mImageCaptureUri);
                }
                break;
            // ACTION_TAKE_PHOTO_S
            case  CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE:
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                if (resultCode == RESULT_OK) {
                    final Uri resultUri = result.getUri();
                    final String path=resultUri.getPath();
                    if (path==null)
                    {
                        return;
                    }
                    isphoto=true;

                    final Bitmap photo=BitmapFactory.decodeFile(path);

                    ExifInterface ei = null;
                    try {
                        ei = new ExifInterface(path);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                            ExifInterface.ORIENTATION_UNDEFINED);

                    switch(orientation) {

                        case ExifInterface.ORIENTATION_ROTATE_90:
                            rotateImage(photo, 90);
                            break;

                        case ExifInterface.ORIENTATION_ROTATE_180:
                            rotateImage(photo, 180);
                            break;

                        case ExifInterface.ORIENTATION_ROTATE_270:
                            rotateImage(photo, 270);
                            break;

                        case ExifInterface.ORIENTATION_NORMAL:

                        default:
                            break;
                    }

                        userpic.post(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    bimapTofile(photo);
                                }catch (IOException e){e.printStackTrace();}
                            }
                        });



                    picedit.post(new Runnable() {

                        @Override
                        public void run() {
                            try {
                                Bitmap photo=BitmapFactory.decodeFile(path);
//                                userpic.setImageBitmap(photo);
                                Picasso.with(RegistrationActivity.this).load(resultUri).transform(new CircleTransform()).into(userpic);
//                                Picasso.with(getApplicationContext()).load(new File(mFileTemp.getAbsolutePath())).placeholder(R.drawable.cmra).into(userpic);
//                                userpic.setImageBitmap(photo);

//                                img_cover_pic.setImageBitmap(photo);
                                AlphaAnimation alpha = new AlphaAnimation(0.3F, 0.3F);
                                alpha.setDuration(0); // Make animation instant
                                alpha.setFillAfter(true); // Tell it to persist after the animation ends
//                                img_cover_pic.startAnimation(alpha);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });

                } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    Exception error = result.getError();
                }
                break;
            case REQUEST_CODE_CROP_IMAGE :
//                if (resultCode == Activity.RESULT_OK) {
//
//                    String path = data.getStringExtra(CropImage.IMAGE_PATH);
//                    if (path == null) {
//
//                        return;
//                    }
//
//                    final Bitmap photo = BitmapFactory.decodeFile(mFileTemp.getPath());
//                    try {
//                        bimapTofile(photo);
//                    } catch (IOException e1) {
//                        // TODO Auto-generated catch block
//                        e1.printStackTrace();
//                    }
//                    picedit.post(new Runnable() {
//
//                        @Override
//                        public void run() {
//                            try {
//
//                                userpic.setImageBitmap(photo);
//
////                                img_cover_pic.setImageBitmap(photo);
//                                AlphaAnimation alpha = new AlphaAnimation(0.3F, 0.3F);
//                                alpha.setDuration(0); // Make animation instant
//                                alpha.setFillAfter(true); // Tell it to persist after the animation ends
////                                img_cover_pic.startAnimation(alpha);
//
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    });
//                }
                break;
            case IMAGE_EDIT:
                if (resultCode==Activity.RESULT_OK)
                {
                    try {
                        boolean gallery=data.getBooleanExtra("gallery",false);
                        boolean camera=data.getBooleanExtra("camera",false);
                        if (gallery)
                        {
                            openGallery();
                        }
                        if (camera)
                        {
                            takePicture();
                        }

                    }catch (Exception e)
                    {

                    }
                }
                break;
            case EDITWEIGHT:
                if (resultCode==Activity.RESULT_OK)
                {
                    try
                    {
                        String name=data.getStringExtra("name");
                        if (!name.isEmpty())
                        {
                            editweight.setText(name);
                        }

                    }catch (Exception e)
                    {

                    }
                }
                break;
            case EDITHEIGHT:
                if (resultCode==Activity.RESULT_OK)
                {
                    try
                    {
                        String name=data.getStringExtra("name");
                        if (!name.isEmpty())
                        {
                            editheight.setText(name);
                        }

                    }catch (Exception e)
                    {

                    }
                }
                break;
            case PLACE_PICKER_REQUEST:
                if(resultCode==Activity.RESULT_OK)
                {
                    Place place = PlacePicker.getPlace(data, this);
                    StringBuilder builder=new StringBuilder();
//               String Name= String.valueOf(place.getName());
//               builder.append(Name);
                    String Adress=String.valueOf(place.getAddress());
                    String str[]=Adress.split(",");
                    int j=str.length;
                    if(j>4)
                    {
                        String addr=str[1];
                        String s2=str[2];
                        builder.append(addr);
                        builder.append(s2);
                    }
                    if(j==4)
                    {
                        String addr=str[1];
                        builder.append(addr);
                    }
                    else if(j<=3)
                    {
                        String addr=str[0];
                        builder.append(addr);
                    }
//                builder.append(" ")



                    String toastMsg=builder.toString();
                    cityname=toastMsg;
                    editloc.setText(toastMsg);

                    temp=  place.getLatLng();
                    Lat=String.valueOf(temp.latitude);
                    LONG=String.valueOf(temp.longitude);
//                    Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();
                }
                else {
                    super.onActivityResult(requestCode, resultCode, data);
                }
                break;

        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    public static void copyStream(InputStream input, OutputStream output)
            throws IOException {

        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = input.read(buffer)) != -1) {
            output.write(buffer, 0, bytesRead);
        }
    }
    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }
    public void bimapTofile(Bitmap bitmap) throws IOException{

        //create a file to write bitmap data
         f =new  File(Environment.getExternalStorageDirectory()
                + File.separator + Constants.TEMP_PHOTO_FILE);
        f.createNewFile();

        //Convert bitmap to byte array
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
        bitmap.compress(Bitmap.CompressFormat.JPEG,60,bos);
        byte[] bitmapdata = bos.toByteArray();


//		String encodedimage = encodeImage(bitmapdata);
        //write the bytes in file
        FileOutputStream fos = new FileOutputStream(f);
        fos.write(bitmapdata);

        fos.flush();
        fos.close();
//        Picasso.with(getApplicationContext()).load(new File(mFileTemp.getAbsolutePath())).placeholder(R.drawable.cmra).into(userpic);


    }

    private void UploadImageTask(File f) {
//        final Handler handler=new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                hideProgress();
//
//                Intent intent = new Intent(RegistrationActivity.this, Registration_2_Activity.class);
//                startActivity(intent);
//            }
//        },6000);
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        APIService service = new Retrofit.Builder().baseUrl("http://preview.proyectoweb.com/stylerapp/").client(client).build().create(APIService.class);

        RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), f);
        MultipartBody.Part body = MultipartBody.Part.createFormData("userfile", f.getName(), reqFile);
        RequestBody userid = RequestBody.create(MediaType.parse("text/plain"),userID );
        Call<ResponseBody>req=service.postImage(body,userid);
        req.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful())
                {
                    try{
                        String image=response.body().toString();
                        hideProgress();
                        Intent intent = new Intent(RegistrationActivity.this, Registration_2_Activity.class);
                        startActivity(intent);
//                        showToast(image);
                    }catch (Exception e){}

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
            hideProgress();
            }
        });
    }

    private void Getlocation() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//      Criteria criteria = new Criteria();
//       provider = locationManager.getBestProvider(criteria, false);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    ACCESS_FINE_LOCATION);

            return;
        }
        Location location = getLastKnownLocation();


        if (location != null) {
            System.out.println("Provider " + provider + " has been selected.");
            onLocationChanged(location);
        } else {
            showSnackBar("location not available please turn on gps", false);
        }
    }
    private Location getLastKnownLocation() {
        locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
        List<String> providers = locationManager.getProviders(true);
        Location bestLocation = null;
        for (String bprovider : providers) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        ACCESS_FINE_LOCATION);


            }
            Location l = locationManager.getLastKnownLocation(bprovider);
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                // Found best last known location: %s", l);
                bestLocation = l;
                provider=bprovider;

            }
        }
        return bestLocation;
    }
    private void Initializepicker() {
        showProgress();
        LatLng latng=new LatLng(lat,lng );
        toBounds(latng,5000);



    }
    public LatLngBounds toBounds(LatLng center, double radius) {
        southwest = SphericalUtil.computeOffset(center, radius * Math.sqrt(2.0), 225);
        northeast = SphericalUtil.computeOffset(center, radius * Math.sqrt(2.0), 45);
        hideProgress();
        return new LatLngBounds(southwest, northeast);
    }
    @Override
    public void onLocationChanged(Location location) {
        lat = location.getLatitude();

        lng = location.getLongitude();
    }

}
