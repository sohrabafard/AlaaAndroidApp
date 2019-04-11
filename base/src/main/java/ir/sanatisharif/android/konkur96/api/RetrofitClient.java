package ir.sanatisharif.android.konkur96.api;

import android.net.NetworkInfo;
import android.util.Log;

import java.io.IOException;


import ir.sanatisharif.android.konkur96.activity.ActivityBase;
import ir.sanatisharif.android.konkur96.app.AppConfig;
import ir.sanatisharif.android.konkur96.ui.view.MDToast;
import ir.sanatisharif.android.konkur96.utils.MyPreferenceManager;
import okhttp3.Cache;
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
    private static final long cacheSize = 10 * 1024 * 1024; // 10 MB


    public static Retrofit getInstance() {

        if (retrofit == null) {
            httpClient = new OkHttpClient.Builder();
            httpClient
                    .addInterceptor(offlineInterceptor)
                    .addNetworkInterceptor(onlineInterceptor)
                    .cache(new Cache(AppConfig.context.getCacheDir(), cacheSize))
                    .build();

            retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .baseUrl(BASE_URL)
                    .build();
        }
        return retrofit;
    }

    static Interceptor onlineInterceptor = new Interceptor() {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {

            Request original = chain.request();
            Request.Builder builder = original.newBuilder();
            builder.addHeader("Content-Type", "application/json");
            builder.addHeader("Accept", "application/json");
            builder.addHeader("X-Requested-With", "XMLHttpRequest");
            if (MyPreferenceManager.getInatanse().isAuthorize())
                builder.addHeader("Authorization", "Bearer " + MyPreferenceManager.getInatanse().getApiToken());
            okhttp3.Response response = chain.proceed(builder.build());

            if (!handelStatusCode(response.code()))
                return response;

            int maxAge = 10; // read from cache for 10 seconds even if there is internet connection
            return response.newBuilder()
                    .header("Cache-Control", "public, max-age=" + maxAge)
                    .removeHeader("Pragma")
                    .build();
        }
    };

    private static boolean handelStatusCode(int code) {

        boolean valid = true;
        if (code == 401) {
            // ActivityBase.toastShow("دسترسی نا معتبر", MDToast.TYPE_ERROR);
            valid = false;
        } else if (code == 403) {
            // ActivityBase.toastShow(" دسترسی غیر مجاز ", MDToast.TYPE_ERROR);
            valid = false;
        } else if (code == 404) {
            // ActivityBase.toastShow("منبع درخواستی پیدا نشد", MDToast.TYPE_ERROR);
            valid = false;
        } else if (code == 408) {
            // ActivityBase.toastShow("پایان حداکثر زمان درخواست", MDToast.TYPE_ERROR);
            valid = false;
        } else if (code == 413) {
            //  ActivityBase.toastShow("درخواست خیلی طولانی", MDToast.TYPE_ERROR);
            valid = false;
        } else if (code == 500) {
            // ActivityBase.toastShow("خطای داخلی سرور", MDToast.TYPE_ERROR);
            valid = false;
        } else if (code >= 300 && code < 510) {
            // ActivityBase.toastShow("خطا", MDToast.TYPE_ERROR);
            valid = false;
        }

        return valid;
    }

    static Interceptor offlineInterceptor = new Interceptor() {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (!isConnected()) {
                int maxStale = 60 * 60 * 24 * 30; // Offline cache available for 30 days
                request = request.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .removeHeader("Pragma")
                        .build();
            }
            return chain.proceed(request);
        }
    };


    private static boolean isConnected() {
        try {
            android.net.ConnectivityManager e = (android.net.ConnectivityManager) AppConfig.context.getSystemService(
                    AppConfig.context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = e.getActiveNetworkInfo();
            return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        } catch (Exception e) {
            Log.w("LOG", e.toString());
        }

        return false;
    }
}
