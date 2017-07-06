package com.applozic.mobicomkit.uiwidgets.Retrofit;

import android.content.Context;



import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by tony on 02/26/2016.
 */
public class ServiceGenerator {
    private static Retrofit retrofit;

    private static Context contextt;
    private static boolean loginFalg = false;


    private static HttpLoggingInterceptor interceptor
            = new HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY); // for logging and debugging

    private static OkHttpClient.Builder httpClientBuilder
            = new OkHttpClient.Builder()
            .addInterceptor(interceptor);

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                   .baseUrl(Urls.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());

    public static <S> S createService(Class<S> serviceClass, Context context) {
        contextt = context;
        retrofit = builder
                .client(httpClientBuilder.build())
                .build();
        return retrofit.create(serviceClass);
//        return createService(serviceClass, null, null);
    }


    public static Retrofit getRetrofit() {
        return retrofit;
    }

}

