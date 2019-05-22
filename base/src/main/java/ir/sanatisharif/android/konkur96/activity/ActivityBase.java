package ir.sanatisharif.android.konkur96.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.webkit.WebView;

import com.google.firebase.analytics.FirebaseAnalytics;

import ir.sanatisharif.android.konkur96.account.AccountInfo;
import ir.sanatisharif.android.konkur96.app.AppConfig;
import ir.sanatisharif.android.konkur96.model.user.User;
import ir.sanatisharif.android.konkur96.ui.view.MDToast;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static ir.sanatisharif.android.konkur96.app.AppConstants.ACCOUNT_TYPE;

public class ActivityBase extends AppCompatActivity {

    FirebaseAnalytics mFirebaseAnalytics;

    public static void toastShow(final String message, int MDToastType) {

        int toastDurationInMilliSeconds = 900;

        final MDToast mToastToShow = MDToast.makeText(AppConfig.context, message, 0, MDToastType);

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

        return (dm.heightPixels / 3);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(false);
        }
        AccountInfo accountInfo = new AccountInfo(this, this);
        User user = accountInfo.getInfo(ACCOUNT_TYPE);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        if (user != null)
            mFirebaseAnalytics.setUserId("" + user.getId());
    }

    @Override
    protected void onResume() {
        super.onResume();
        AppConfig.currentActivity = this;

    }

}
