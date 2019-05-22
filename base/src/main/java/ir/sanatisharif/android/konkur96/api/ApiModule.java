package ir.sanatisharif.android.konkur96.api;

import android.content.Context;
import android.net.NetworkInfo;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ir.sanatisharif.android.konkur96.activity.ActivityBase;
import ir.sanatisharif.android.konkur96.app.AppConfig;
import ir.sanatisharif.android.konkur96.ui.view.MDToast;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ApiModule {

    private static final long cacheSize = 100 * 1024 * 1024; // 10 MB
    private static Interceptor onlineInterceptor = chain -> {
        Request original = chain.request();
        Request.Builder builder = original.newBuilder();
        okhttp3.Response response = chain.proceed(builder.build());

        handelStatusCode(response.code());

        int maxAge = 10; // read from cache for 10 seconds even if there is internet connection
        return response.newBuilder()
                .header("Cache-Control", "public, max-age=" + maxAge)
                .removeHeader("Pragma")
                .build();
    };
    private static Interceptor offlineInterceptor = chain -> {
        Request request = chain.request();
        if (!isConnected()) {
            int maxStale = 60 * 60 * 24 * 30; // Offline cache available for 30 days
            request = request.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                    .removeHeader("Pragma")
                    .build();
        }
        return chain.proceed(request);
    };

    private static boolean isConnected() {
        try {
            android.net.ConnectivityManager e = (android.net.ConnectivityManager) AppConfig.context.getSystemService(
                    Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = e.getActiveNetworkInfo();
            return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        } catch (Exception e) {
            Log.w("LOG", e.toString());
        }

        return false;
    }

    @Provides
    @Singleton
    OkHttpClient provideClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        builder.addInterceptor(offlineInterceptor);
        builder.addNetworkInterceptor(onlineInterceptor);
        builder.cache(new Cache(AppConfig.context.getCacheDir(), cacheSize));

        builder.connectTimeout(10, TimeUnit.SECONDS);
        builder.readTimeout(10, TimeUnit.SECONDS);
        builder.writeTimeout(10, TimeUnit.SECONDS);

        return builder.build();

    }

    @Provides
    @Singleton
    Retrofit.Builder provideRetrofit(OkHttpClient okHttpClient) {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        return new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create());
    }

    @Provides
    @Singleton
    ShopAPI provideApi(Retrofit.Builder builder) {
        return builder.baseUrl(AppConfig.BASE_URL).build().create(ShopAPI.class);
    }

    @Provides
    @Singleton
    ZarinPalAPI provideApiZarinPal(Retrofit.Builder builder) {
        return builder.baseUrl(ZarinPalAPI.BASE_URL).build().create(ZarinPalAPI.class);
    }


    @Provides
    @Singleton
    HeadRequestInterface provideHeadRequest(Retrofit.Builder builder) {
        Log.e("Alaa\\ApiModule", builder.toString());

        OkHttpClient okHttpClient = new OkHttpClient()
                .newBuilder()
                .addInterceptor(offlineInterceptor)
                .cache(new Cache(AppConfig.context.getCacheDir(), cacheSize))
                .addNetworkInterceptor(onlineInterceptor)
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .followRedirects(false)
                .followSslRedirects(false)
                .build();

        return builder
                .client(okHttpClient)
                .baseUrl(AppConfig.BASE_URL)
                .build()
                .create(HeadRequestInterface.class);
    }

    ///http://developer.android.com/
    private static boolean handelStatusCode(int code) {

        boolean valid = true;
        if (code == 401) {
            Log.i("LOG", "handelStatusCode: " + 400);
            handleToast("دسترسی نا معتبر");
            valid = false;
        } else if (code == 403) {
            Log.i("LOG", "handelStatusCode: " + 403);
            handleToast("دسترسی غیر مجاز ");
            valid = false;
        } else if (code == 404) {
            Log.i("LOG", "handelStatusCode: " + 404);
            handleToast("منبع درخواستی پیدا نشد");
            valid = false;
        } else if (code == 408) {
            Log.i("LOG", "handelStatusCode: " + 408);
            handleToast("پایان حداکثر زمان درخواست");
            valid = false;
        } else if (code == 413) {
            Log.i("LOG", "handelStatusCode: " + 413);
            handleToast("درخواست خیلی طولانی");
            valid = false;
        } else if (code == 500) {
            Log.i("LOG", "handelStatusCode: " + 500);
            handleToast("خطای داخلی سرور");
            valid = false;
        } else if (code >= 300 && code != 302 && code < 510) {
            Log.i("LOG", "handelStatusCode:510  " + code);
            handleToast("خطا");
            valid = false;
        }
        return valid;
    }

    private static void handleToast(String msg) {
        AppConfig.HANDLER.post(new Runnable() {
            @Override
            public void run() {
                ActivityBase.toastShow(msg, MDToast.TYPE_ERROR);
            }
        });

    }

    @Provides
    @Singleton
    MainApi provideMainApi(Retrofit.Builder builder) {
        return builder.baseUrl(AppConfig.BASE_URL).build().create(MainApi.class);
    }
}
