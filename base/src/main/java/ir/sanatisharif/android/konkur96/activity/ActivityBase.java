package ir.sanatisharif.android.konkur96.activity;

import android.app.Activity;
import android.content.Context;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;

import android.view.animation.Animation;
import android.widget.TextView;
import ir.sanatisharif.android.konkur96.app.AppConfig;
import ir.sanatisharif.android.konkur96.ui.view.MDToast;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ActivityBase extends AppCompatActivity {

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onResume() {
        super.onResume();
        AppConfig.currentActivity = this;

    }

    public static void toastShow(final String message, int MDToastType) {

        int toastDurationInMilliSeconds = 700;

        final MDToast mToastToShow = MDToast.makeText(AppConfig.context, message, MDToastType);

        AppConfig.HANDLER.postDelayed(new Runnable() {
            @Override
            public void run() {

                mToastToShow.cancel();
            }
        }, toastDurationInMilliSeconds);

        mToastToShow.show();

    }

    public static int containerHeight(Activity ac) {
        DisplayMetrics dm = new DisplayMetrics();
        ac.getWindowManager().getDefaultDisplay().getMetrics(dm);

        AppConfig.width = dm.widthPixels;
        AppConfig.height = dm.heightPixels;


        return (int) (dm.heightPixels / 3);
    }

}
