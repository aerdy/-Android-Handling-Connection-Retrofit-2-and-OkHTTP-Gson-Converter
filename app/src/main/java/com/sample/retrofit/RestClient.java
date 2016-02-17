package com.sample.retrofit;

/**
 * Created by Jarcode on 2016-01-26.
 */
/**
 * Created by Jarcode on 2016-01-25.
 */
import android.content.Context;
import android.util.Log;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.http.Field;
import retrofit.http.FieldMap;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by Ashiq Uz Zoha on 9/13/15.
 * Dhrubok Infotech Services Ltd.
 * ashiq.ayon@gmail.com
 */
public class RestClient {

    private static ApiInterface ApiInterface ;

    public static ApiInterface getClient(Context context) {
        if (ApiInterface == null) {

//
            OkHttpClient okClient = new OkHttpClient();
            okClient.interceptors().add(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Response response = chain.proceed(chain.request());
                    return response;
                }
            });

            Retrofit client = new Retrofit.Builder()
                    .baseUrl("https://api.github.com")
                    .client(okClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            ApiInterface = client.create(ApiInterface.class);
        }
        return ApiInterface ;
    }

    public interface ApiInterface {

//        @FormUrlEncoded
//        @POST("/api/agent/activation")
//        @Headers("Content-Type: application/x-www-form-urlencoded")
//        Call<ResponseBody> pinActivation(@FieldMap() Map a);


        @GET("/search/users")
        Call<ResponseBody> getData(@Query("q") String name);

    }

}

