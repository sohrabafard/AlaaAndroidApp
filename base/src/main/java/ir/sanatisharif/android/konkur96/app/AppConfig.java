package ir.sanatisharif.android.konkur96.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.support.multidex.MultiDex;
import android.support.v7.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ProgressBar;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.core.CrashlyticsCore;
import com.google.android.exoplayer2.offline.DownloadManager;
import com.google.android.exoplayer2.offline.DownloaderConstructorHelper;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.upstream.FileDataSourceFactory;
import com.google.android.exoplayer2.upstream.HttpDataSource;
import com.google.android.exoplayer2.upstream.cache.Cache;
import com.google.android.exoplayer2.upstream.cache.CacheDataSource;
import com.google.android.exoplayer2.upstream.cache.CacheDataSourceFactory;
import com.google.android.exoplayer2.upstream.cache.NoOpCacheEvictor;
import com.google.android.exoplayer2.upstream.cache.SimpleCache;
import com.google.android.gms.common.wrappers.InstantApps;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.analytics.FirebaseAnalytics;


import java.io.File;

import io.fabric.sdk.android.Fabric;
import ir.sanatisharif.android.konkur96.BuildConfig;
import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.api.ApiModule;
import ir.sanatisharif.android.konkur96.helper.FileManager;
import ir.sanatisharif.android.konkur96.listener.ICheckNetwork;
import ir.sanatisharif.android.konkur96.service.NetworkChangedReceiver;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;


//@ReportsCrashes(formKey = "", formUri = "http://edu-edu.ir/alla/report.php", customReportContent = {
//        ReportField.APP_VERSION_CODE, ReportField.APP_VERSION_NAME,
//        ReportField.ANDROID_VERSION, ReportField.PHONE_MODEL,
//        ReportField.BRAND, ReportField.CUSTOM_DATA, ReportField.STACK_TRACE,
//        ReportField.LOGCAT}, mode = ReportingInteractionMode.SILENT)

public class AppConfig extends Application {

    public static AppConfig mInstance;
    public static Context context;
    public static Activity currentActivity;
    public static Handler HANDLER = new Handler();
    public static LayoutInflater layoutinflater;
    static ConnectivityManager Manager = null;
    public static int width = 140, height = 140, itemHeight = 140, shopItemHeight = 100;
    public static boolean showNoInternetDialog = false;
    // Font
    public static Typeface fontIRSensLight;
    public static Typeface fontIRSensNumber;

    //new
    public static final String TAG = AppConfig.class.getSimpleName();
    public static SharedPreferences sharedPreferencesSetting;

    private FirebaseAnalytics mFirebaseAnalytics;
    public static int[] colorSwipeRefreshing;
    public static String BASE_URL = "https://alaatv.com/";

    @Override
    public void onCreate() {
        super.onCreate();

        // carshlytics
//        CrashlyticsCore crashlyticsCore = new CrashlyticsCore.Builder()
//                .disabled(BuildConfig.DEBUG)
//                .build();
//        Fabric.with(this, new Crashlytics.Builder().core(crashlyticsCore).build());

        Fabric.with(this, new Crashlytics());
        Crashlytics.setBool("InstantApp", InstantApps.isInstantApp(this));

        // MultiDex.install(this);
        mInstance = this;
        context = getApplicationContext();
        BASE_URL = getString(R.string.alla_url);

        //Firebase init by application id
//        if (!InstantApps.isInstantApp(getApplicationContext())) {
//            FirebaseOptions options = new FirebaseOptions.Builder()
//                    .setApplicationId(getString(R.string.firebaseApplicationId)) // Required for Analytics
//                    .build();
//            FirebaseApp.initializeApp(getApplicationContext(), options);
//        }


        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/IRANSansMobile(FaNum).ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );


        //init
        // sAnalytics = GoogleAnalytics.getInstance(this);
        layoutinflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Manager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);


        fontIRSensLight = Typeface.createFromAsset(getAssets(), "fonts/IRANSans(FaNum)_Light.ttf");
        fontIRSensNumber = Typeface.createFromAsset(getAssets(), "fonts/IRANSansMobile(FaNum).ttf");

        sharedPreferencesSetting = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        //check and create directories
        if (!FileManager.checkFileExist(FileManager.getRootPath())) {

            FileManager.createRootDir();//create root dir
            FileManager.createAudioDir();//create audio dir
            FileManager.createPDFDir();//create pdf dir
        }

        if (colorSwipeRefreshing == null)
            colorSwipeRefreshing = new int[]
                    {
                            getResources().getColor(R.color.Monochromatic_1),
                            getResources().getColor(R.color.Monochromatic_2),
                            getResources().getColor(R.color.Monochromatic_3),
                            getResources().getColor(R.color.Monochromatic_4),
                    };
    }


    private final AppComponent mAppComponent = DaggerAppComponent.builder().apiModule(new ApiModule()).build();


    public AppComponent getAppComponent() {
        return mAppComponent;
    }

    public static synchronized AppConfig getInstance() {
        return mInstance;
    }

    public void setICheckNetwork(ICheckNetwork iCheckNetwork) {
        NetworkChangedReceiver.iCheckNetwork = iCheckNetwork;
    }

    public void changeProgressColor(ProgressBar loader) {
        loader.getIndeterminateDrawable().setColorFilter(0xFFFFB700, android.graphics.PorterDuff.Mode.MULTIPLY);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
