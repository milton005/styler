package com.applozic.mobicomkit.uiwidgets.Retrofit;

import com.applozic.mobicomkit.uiwidgets.Retrofit.Checkin.ModelCheckinnn;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;


/**
 * Created by tony on 14/06/2016.
 */
public interface APIService {
//    @Headers("Oakey:stylapp@XYZ")
//    @GET(Urls.ETHNICITY_URL)
//    Call<ModelEthnicity>getethnic(@QueryMap Map<String, String> params);
//    @Headers("Oakey:stylapp@XYZ")
//    @FormUrlEncoded
//    @POST(Urls.TRIBES_URL)
//    Call<ModelTribes>gettribes(@FieldMap HashMap<String, String> params);
//    @Headers("Oakey:stylapp@XYZ")
//    @FormUrlEncoded
//    @POST(Urls.REGISTER_URL)
//    Call<ModelRegister>register(@FieldMap HashMap<String, String> params);
//    @Headers(("Oakey:stylapp@XYZ"))
//    @FormUrlEncoded
//    @POST(Urls.FBREGISTRATION)
//    Call<ModelRegister>fbregister(@FieldMap HashMap<String, String> params);
//    @Headers("Oakey:stylapp@XYZ")
//    @FormUrlEncoded
//    @POST(Urls.FB_SIGN_IN_URL)
//    Call<ModelFblogin>fblogin(@FieldMap HashMap<String, String> params);
//    @Multipart
//    @POST(Urls.FILE_UPLOAD)
//    Call<ResponseBody>postImage(@Part MultipartBody.Part image, @Part("user_id") RequestBody userid);
//    @Multipart
//    @POST(Urls.FILE_UPLOAD_MULTIPLE)
//    Call<ResponseBody>postmultiple(@Part MultipartBody.Part image, @Part("user_id") RequestBody userid);
//    @Headers("Oakey:stylapp@XYZ")
//    @FormUrlEncoded
//    @POST(Urls.UPDATE_PROFILE)
//    Call<ModelUpdateProfile>updateprofile(@FieldMap HashMap<String, String> params);
//    @Headers("Oakey:stylapp@XYZ")
//    @FormUrlEncoded
//    @POST(Urls.LOGIN_URL)
//    Call<ModelLogin>login(@FieldMap HashMap<String, String> params);
//    @Headers("Oakey:stylapp@XYZ")
//    @FormUrlEncoded
//    @POST(Urls.FORGOT_PASSWORD)
//    Call<Modelforgot>forgotpass(@FieldMap HashMap<String, String> params);
//    @Headers("Oakey:stylapp@XYZ")
//    @FormUrlEncoded
//    @POST(Urls.GET_NEW_STYLERS)
//    Call<ModelnewStylers>getnewstylers(@FieldMap HashMap<String, String> params);
//    @Headers("Oakey:stylapp@XYZ")
//    @FormUrlEncoded
//    @POST(Urls.SEARCHPROFILES)
//    Call<ModelStylerSearch>getsearch(@FieldMap HashMap<String, String> params);
//    @Headers("Oakey:stylapp@XYZ")
//    @FormUrlEncoded
//    @POST(Urls.PROFILE)
//    Call<ModelProfile>getprofile(@FieldMap HashMap<String, String> params);
//    @Headers("Oakey:stylapp@XYZ")
//    @FormUrlEncoded
//    @POST(Urls.GALLERY)
//    Call<ModelGallery>getgallery(@FieldMap HashMap<String, String> params);
//    @Headers("Oakey:stylapp@XYZ")
//    @FormUrlEncoded
//    @POST(Urls.BLOCK)
//    Call<ModelBlocked>getblocked(@FieldMap HashMap<String, String> params);
//    @Headers("Oakey:stylapp@XYZ")
//    @FormUrlEncoded
//    @POST(Urls.GETSTYLERDETAIL)
//    Call<ModelStylerDetail>getstylerdetail(@FieldMap HashMap<String, String> params);
//    @Headers("Oakey:stylapp@XYZ")
//    @FormUrlEncoded
//    @POST(Urls.TOGGLEFAVOURITES)
//    Call<ModelBlocked>gettogglefavorite(@FieldMap HashMap<String, String> params);
//    @Headers("Oakey:stylapp@XYZ")
//    @FormUrlEncoded
//    @POST(Urls.MYFAVORITES)
//    Call<ModelMyfavorite>getmyfavorite(@FieldMap HashMap<String, String> params);
    @Headers("Oakey:stylapp@XYZ")
    @FormUrlEncoded
    @POST(Urls.CHAT_HELPER)
    Call<ResponseBody>chathelper(@FieldMap HashMap<String,String>params);
    @GET(Urls.GETVENUES)
    Call<ModelCheckinnn>getvenues(@QueryMap HashMap<String,String>params);//foursquare api calling
}
