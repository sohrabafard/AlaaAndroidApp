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
import android.view.LayoutInflater;
import android.widget.ProgressBar;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.common.wrappers.InstantApps;

import io.fabric.sdk.android.Fabric;
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

    //new
    public static final String TAG = AppConfig.class.getSimpleName();
    public static AppConfig mInstance;
    public static Context context;
    public static Activity currentActivity;
    public static Handler HANDLER = new Handler();
    public static LayoutInflater layoutinflater;
    public static int width = 140, height = 140, itemHeight = 140, shopItemHeight = 100;
    public static boolean showNoInternetDialog = false;
    // Font
    public static Typeface fontIRSensLight;
    public static Typeface fontIRSensNumber;
    public static SharedPreferences sharedPreferencesSetting;
    public static int[] colorSwipeRefreshing;
    public static String BASE_URL = "https://alaatv.com/";
    static ConnectivityManager Manager = null;
    private final AppComponent mAppComponent = DaggerAppComponent.builder().apiModule(new ApiModule()).build();

    public static synchronized AppConfig getInstance() {
        return mInstance;
    }

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

        mInstance = this;
        context = getApplicationContext();
        BASE_URL = getString(R.string.alla_url);


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

    public AppComponent getAppComponent() {
        return mAppComponent;
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
