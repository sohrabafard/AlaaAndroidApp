package ir.sanatisharif.android.konkur96.api;

import java.io.IOException;


import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Mohamad on 12/26/2018.
 */

public class RetrofitClient {

    private static Retrofit retrofit;
    private static OkHttpClient.Builder httpClient;
    private static final String BASE_URL = "http://79.127.123.246:8080/";
    //private static final String BASE_URL = "http://edu-edu.ir/";


    public static Retrofit getInstance() {

        if (retrofit == null) {
            httpClient = new OkHttpClient.Builder();
            httpClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request().
                            newBuilder().
                            addHeader("Content-Type", "application/json")
                            .addHeader("Accept", "application/json")
                            .addHeader("X-Requested-With", "XMLHttpRequest")
                            .build();
                    return chain.proceed(request);
                }
            });

            retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .baseUrl(BASE_URL)
                    .build();
        }
        return retrofit;
    }
}
