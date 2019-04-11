package com.example.ivykiai.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import android.util.Base64;
import java.io.IOException;
import okhttp3.Interceptor;


public class RetrofitClient {



    private static final String AUTH = "Basic " + Base64.encodeToString(("marius:admin").getBytes(), Base64.NO_WRAP);

    private static final String BASE_URL = "http://192.168.43.65/ivykiai/public/";
    private static RetrofitClient mInstance;
    private Retrofit retrofit;

    private RetrofitClient() {

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(
                        new Interceptor() {
                            @Override
                            public Response intercept(Chain chain) throws IOException {
                                Request original = chain.request();

                                Request.Builder requestBuilder = original.newBuilder()
                                        .addHeader("Authorization", AUTH)
                                        .method(original.method(), original.body());

                                Request request = requestBuilder.build();
                                return chain.proceed(request);
                            }
                        }
                ).build();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                //CONVERTERIS
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
    }

    public static synchronized RetrofitClient getInstance() {
        if (mInstance == null) {
            mInstance = new RetrofitClient();
        }
        return mInstance;
    }

    public API getApi(){
        return retrofit.create(API.class);
    }
}