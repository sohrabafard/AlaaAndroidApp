package ir.sanatisharif.android.konkur96.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.os.Environment;
import android.os.Handler;

import android.view.LayoutInflater;


import java.io.File;

import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.helper.FileManager;
import ir.sanatisharif.android.konkur96.utils.Utils;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;


public class AppConfig extends Application {

    //   private GoogleAnalytics sAnalytics;

    public static Context context;
    public static Activity currentActivity;
    public static Handler HANDLER = new Handler();
    public static LayoutInflater layoutinflater;
    static ConnectivityManager Manager = null;
    public static int width = 140, height = 140, itemHeight = 140;

    // Font
    public static Typeface fontIRSensLight;
    public static Typeface fontIRSensNumber;

    //new
    public static final String TAG = AppConfig.class.getSimpleName();


    @Override
    public void onCreate() {
        super.onCreate();

        //  Fabric.with(this, new Crashlytics());

        context = getApplicationContext();

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


        // Enabling database for resume support even after the application is killed:
/*        PRDownloaderConfig prDownloaderConfig = PRDownloaderConfig.newBuilder()
                .setDatabaseEnabled(true)
                .build();
        PRDownloader.initialize(getApplicationContext(), prDownloaderConfig);*/


      //check and create directories
        if (!FileManager.checkFileExist(FileManager.getRootPath())) {
            FileManager.createRootDir();//create root
            FileManager.createVideoDir();//create video
        }
    }
}
