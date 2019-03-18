package ir.sanatisharif.android.konkur96.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.animation.SpringAnimation;
import android.support.animation.SpringForce;
import android.util.Log;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.account.AccountInfo;
import ir.sanatisharif.android.konkur96.app.AppConfig;
import ir.sanatisharif.android.konkur96.model.user.User;
import ir.sanatisharif.android.konkur96.utils.MyPreferenceManager;

import static ir.sanatisharif.android.konkur96.app.AppConstants.ACCOUNT_TYPE;

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
                        Log.i(TAG, "onCreate: " + ((User) accountInfo.getInfo(ACCOUNT_TYPE)).getFirstName());

//                        accountInfo.getExistingAccountAuthToken(ACCOUNT_TYPE, AUTHTOKEN_TYPE_FULL_ACCESS, new AccountInfo.AuthToken() {
//                            @Override
//                            public void onToken(String token) {
//                                Log.i(TAG, "onCreate: " + token);
//                            }
//                        });
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    }

                    finish();

                }
            }, 1500);
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

        FirebaseInstanceId.getInstance().getInstanceId().
                addOnSuccessListener(SplashActivity.this, new OnSuccessListener<InstanceIdResult>() {
                    @Override
                    public void onSuccess(InstanceIdResult instanceIdResult) {

                        MyPreferenceManager.getInatanse().setFirebaseToken(instanceIdResult.getToken());
                    }
                });


    }
}
