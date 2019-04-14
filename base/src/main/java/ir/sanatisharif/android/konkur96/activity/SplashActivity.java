package ir.sanatisharif.android.konkur96.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.animation.SpringAnimation;
import android.support.animation.SpringForce;
import android.util.Log;
import android.widget.ImageView;

import com.google.android.gms.common.wrappers.InstantApps;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.account.AccountInfo;
import ir.sanatisharif.android.konkur96.app.AppConfig;
import ir.sanatisharif.android.konkur96.fragment.DashboardMainFrg;
import ir.sanatisharif.android.konkur96.fragment.DetailsVideoFrg;
import ir.sanatisharif.android.konkur96.fragment.FilterTagsFrg;
import ir.sanatisharif.android.konkur96.fragment.ShopMainFragment;
import ir.sanatisharif.android.konkur96.model.user.User;
import ir.sanatisharif.android.konkur96.utils.MyPreferenceManager;

import static ir.sanatisharif.android.konkur96.app.AppConstants.ACCOUNT_TYPE;
import static ir.sanatisharif.android.konkur96.app.AppConstants.AUTHTOKEN_TYPE_FULL_ACCESS;

public class SplashActivity extends ActivityBase {

    private String TAG = this.getClass().getSimpleName();
    private AccountInfo accountInfo;
    private ImageView img1;
    private AlertDialog mAlertDialog;
    private boolean mInvalidate;

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseApp.initializeApp(AppConfig.context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        img1 = findViewById(R.id.img1);
        containerHeight(this);
        loadAnimation();

        if (InstantApps.isInstantApp(getApplicationContext())) {
            if (!MyPreferenceManager.getInatanse().getOnboarding()) {
                MyPreferenceManager.getInatanse().setOnboarding(true);
                AppConfig.HANDLER.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(SplashActivity.this, OnBoardingActivity.class));
                        finish();
                    }
                }, 2000);

            } else {

                AppConfig.HANDLER.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                        finish();
                    }
                }, 1500);
            }
        } else {
            if (!MyPreferenceManager.getInatanse().getOnboarding()) {
                MyPreferenceManager.getInatanse().setOnboarding(true);
                AppConfig.HANDLER.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        startActivity(new Intent(SplashActivity.this, OnBoardingActivity.class));
                        finish();
                    }
                }, 2000);

            } else {
                accountInfo = new AccountInfo(getApplicationContext(), this);

                AppConfig.HANDLER.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        if (accountInfo.ExistAccount(ACCOUNT_TYPE)) {
                            startActivity(new Intent(SplashActivity.this, MainActivity.class));
                        }
                        finish();
                    }
                }, 1500);
            }
        }
        Log.i(TAG, "onCreate: " + MyPreferenceManager.getInatanse().getFirebaseToken());

        if (MyPreferenceManager.getInatanse().getFirebaseToken().length() == 0) {

            new Thread(new Runnable() {
                @Override
                public void run() {
                    retrieveToken();
                }
            }).start();
        }

    }
    private void handleIntent(Intent intent) {

//        String action = intent.getAction();  // android.intent.action.VIEW
//        String data = intent.getDataString();// https://sanatisharif.ir/c/8087
//
//        if (action == null)
//            return;
//        if (action.equals("ir.sanatisharif.android.SETTING")) {
//            startActivity(new Intent(AppConfig.currentActivity, SettingActivity.class));
//        } else if (Intent.ACTION_VIEW.equals(action) && data != null) {
//            Uri appLinkData = intent.getData();
//            //  Log.i(TAG, "handleIntent1: " + appLinkData);
//            // Log.i(TAG, "handleIntent: " + appLinkData.getPath());
//
//            if (appLinkData.getPath().startsWith("/c")) {
//                if (data.contains("tags")) {
//                    addFrg(FilterTagsFrg.newInstance(data, null), "FilterTagsFrg");
//                } else {
//                    addFrg(DetailsVideoFrg.newInstance(data), "DetailsVideoFrg");
//                }
//            } else if (appLinkData.getPath().startsWith("/product")) {
//
//            } else if (appLinkData.getPath().startsWith("/login")) {
//                if (accountInfo.ExistAccount(ACCOUNT_TYPE)) {
//                    addFrg(DashboardMainFrg.newInstance(), "DashboardMainFrg");
//                }
//            } else if (appLinkData.getPath().startsWith("/zarinpal")) {
//
//                if (data.contains("Status")) {
//
//                    String mStatus = appLinkData.getQueryParameter("Status");
//                    String amount = appLinkData.getQueryParameter("a");
//                    String authority = appLinkData.getQueryParameter("Authority");
//
//                    handlerZarinPalCallBack(amount, authority);
//                }
//
//            } else if (appLinkData.getPath().startsWith("/shop")) {
//
//                addFrg(ShopMainFragment.newInstance(), "ShopMainFragment");
//
//            } else if (appLinkData.getPath().startsWith("/")) {
//
//            }
//        }
    }
    private void loadAnimation() {

        SpringAnimation springAnim = new SpringAnimation(img1, SpringAnimation.TRANSLATION_Y, 0);

        SpringForce springForce = new SpringForce();
        springForce.setFinalPosition(-(AppConfig.height / 3));
        springForce.setStiffness(SpringForce.STIFFNESS_LOW);
        springForce.setDampingRatio(SpringForce.DAMPING_RATIO_HIGH_BOUNCY);
        //springAnim.setStartVelocity(40);
        springAnim.setSpring(springForce);
        springAnim.start();
    }

    private void retrieveToken() {
        Log.i(TAG, "onCreate: 2 ");
        FirebaseApp.initializeApp(this);
        FirebaseInstanceId.getInstance().getInstanceId().
                addOnSuccessListener(SplashActivity.this, new OnSuccessListener<InstanceIdResult>() {
                    @Override
                    public void onSuccess(InstanceIdResult instanceIdResult) {
                        Log.i(TAG, "onCreate: " + instanceIdResult.getToken());
                        MyPreferenceManager.getInatanse().setFirebaseToken(instanceIdResult.getToken());
                    }
                });
    }
}
