package com.richie.styler.UTILS.Retrofit;





import com.richie.styler.UI.Models.Block.ModelBlocked;
import com.richie.styler.UI.Models.Checkin.ModelCheckinnn;
import com.richie.styler.UI.Models.Ethnicity.ModelEthnicity;
import com.richie.styler.UI.Models.Fblogin.ModelFblogin;
import com.richie.styler.UI.Models.Gallery.ModelGallery;
import com.richie.styler.UI.Models.Likes.ModelMyLikes;
import com.richie.styler.UI.Models.Login.ModelLogin;
import com.richie.styler.UI.Models.Match.ModelMatch;
import com.richie.styler.UI.Models.Messages.ModelMessages;
import com.richie.styler.UI.Models.ModelForgotpassword.Modelforgot;
import com.richie.styler.UI.Models.Myfavorites.ModelMyfavorite;
import com.richie.styler.UI.Models.NewStylers.ModelnewStylers;
import com.richie.styler.UI.Models.Profile.ModelProfile;
import com.richie.styler.UI.Models.RecordCheckin.ModelRecordCheckin;
import com.richie.styler.UI.Models.Register.ModelRegister;
import com.richie.styler.UI.Models.StylerDetail.ModelStylerDetail;
import com.richie.styler.UI.Models.StylerSearch.ModelStylerSearch;
import com.richie.styler.UI.Models.Tribes.ModelTribes;
import com.richie.styler.UI.Models.Venues.ModelVenue;
import com.richie.styler.UI.Models.update_profile.ModelUpdateProfile;
import com.richie.styler.UTILS.Urls;

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
    @Headers("Oakey:stylapp@XYZ")
    @GET(Urls.ETHNICITY_URL)
    Call<ModelEthnicity>getethnic(@QueryMap Map<String,String>params);
    @Headers("Oakey:stylapp@XYZ")
    @FormUrlEncoded
    @POST(Urls.TRIBES_URL)
    Call<ModelTribes>gettribes(@FieldMap HashMap<String,String>params);
    @Headers("Oakey:stylapp@XYZ")
    @FormUrlEncoded
    @POST(Urls.REGISTER_URL)
    Call<ModelRegister>register(@FieldMap HashMap<String,String>params);
    @Headers(("Oakey:stylapp@XYZ"))
    @FormUrlEncoded
    @POST(Urls.FBREGISTRATION)
    Call<ModelRegister>fbregister(@FieldMap HashMap<String,String>params);
    @Headers("Oakey:stylapp@XYZ")
    @FormUrlEncoded
    @POST(Urls.FB_SIGN_IN_URL)
    Call<ModelFblogin>fblogin(@FieldMap HashMap<String,String>params);
    @Multipart
    @POST(Urls.FILE_UPLOAD)
    Call<ResponseBody>postImage(@Part MultipartBody.Part image, @Part("user_id")RequestBody userid);
    @Multipart
    @POST(Urls.FILE_UPLOAD_MULTIPLE)
    Call<ResponseBody>postmultiple(@Part MultipartBody.Part image,@Part("user_id")RequestBody userid);
    @Headers("Oakey:stylapp@XYZ")
    @FormUrlEncoded
    @POST(Urls.UPDATE_PROFILE)
    Call<ModelUpdateProfile>updateprofile(@FieldMap HashMap<String,String>params);
    @Headers("Oakey:stylapp@XYZ")
    @FormUrlEncoded
    @POST(Urls.LOGIN_URL)
    Call<ModelLogin>login(@FieldMap HashMap<String,String>params);
    @Headers("Oakey:stylapp@XYZ")
    @FormUrlEncoded
    @POST(Urls.FORGOT_PASSWORD)
    Call<Modelforgot>forgotpass(@FieldMap HashMap<String,String>params);
    @Headers("Oakey:stylapp@XYZ")
    @FormUrlEncoded
    @POST(Urls.GET_NEW_STYLERS)
    Call<ModelnewStylers>getnewstylers(@FieldMap HashMap<String,String>params);
    @Headers("Oakey:stylapp@XYZ")
    @FormUrlEncoded
    @POST(Urls.SEARCHPROFILES)
    Call<ModelStylerSearch>getsearch(@FieldMap HashMap<String,String>params);
    @Headers("Oakey:stylapp@XYZ")
    @FormUrlEncoded
    @POST(Urls.PROFILE)
    Call<ModelProfile>getprofile(@FieldMap HashMap<String,String>params);
    @Headers("Oakey:stylapp@XYZ")
    @FormUrlEncoded
    @POST(Urls.GALLERY)
    Call<ModelGallery>getgallery(@FieldMap HashMap<String,String>params);
    @Headers("Oakey:stylapp@XYZ")
    @FormUrlEncoded
    @POST(Urls.BLOCK)
    Call<ModelBlocked>getblocked(@FieldMap HashMap<String,String>params);
    @Headers("Oakey:stylapp@XYZ")
    @FormUrlEncoded
    @POST(Urls.GETSTYLERDETAIL)
    Call<ModelStylerDetail>getstylerdetail(@FieldMap HashMap<String,String>params);
    @Headers("Oakey:stylapp@XYZ")
    @FormUrlEncoded
    @POST(Urls.TOGGLEFAVOURITES)
    Call<ModelBlocked>gettogglefavorite(@FieldMap HashMap<String,String>params);
    @Headers("Oakey:stylapp@XYZ")
    @FormUrlEncoded
    @POST(Urls.TOGGLELIKES)
    Call<ModelBlocked>gettogglelikes(@FieldMap HashMap<String,String>params);
    @Headers("Oakey:stylapp@XYZ")
    @FormUrlEncoded
    @POST(Urls.MYFAVORITES)
    Call<ModelMyfavorite>getmyfavorite(@FieldMap HashMap<String,String>params);
    @Headers("Oakey:stylapp@XYZ")
    @FormUrlEncoded
    @POST(Urls.MYLIKES)
    Call<ModelMyLikes>getmylikes(@FieldMap HashMap<String,String>params);
    @Headers("Oakey:stylapp@XYZ")
    @FormUrlEncoded
    @POST(Urls.GETMESSAGES)
    Call<ModelMessages>getmessages(@FieldMap HashMap<String,String>params);
    @GET(Urls.GETVENUES)
    Call<ModelCheckinnn>getvenues(@QueryMap HashMap<String,String>params);//foursquare api calling
    @Headers("Oakey:stylapp@XYZ")
    @FormUrlEncoded
    @POST(Urls.NEARBYLOCATIONS)
    Call<ModelVenue>getnearby(@FieldMap HashMap<String,String>params);
    @Headers("Oakey:stylapp@XYZ")
    @FormUrlEncoded
    @POST(Urls.RECORDCHECKINS)
    Call<ModelRecordCheckin>recordcheckin(@FieldMap HashMap<String,String>params);
    @Headers("Oakey:stylapp@XYZ")
    @FormUrlEncoded
    @POST(Urls.MATCHEDUSERS)
    Call<ModelMatch>getmatchedusers(@FieldMap HashMap<String,String>params);
    @Headers("Oakey:stylapp@XYZ")
    @FormUrlEncoded
    @POST(Urls.LIKEDISLIKE)
    Call<ResponseBody>likedislike(@FieldMap HashMap<String,String>params);
    @Headers("Oakey:stylapp@XYZ")
    @FormUrlEncoded
    @POST(Urls.CHAT_HELPER)
    Call<ResponseBody>chathelper(@FieldMap HashMap<String,String>params);
    @Headers("Oakey:stylapp@XYZ")
    @FormUrlEncoded
    @POST(Urls.DELETECHAT)
    Call<ResponseBody>deletechat(@FieldMap HashMap<String,String>params);
    @Headers("Oakey:stylapp@XYZ")
    @FormUrlEncoded
    @POST(Urls.DELETEIMAGE)
    Call<ResponseBody>deleteimage(@FieldMap HashMap<String,String>params);

}
