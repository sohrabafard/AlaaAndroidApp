package ir.sanatisharif.android.konkur96.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.animation.SpringAnimation;
import android.support.animation.SpringForce;
import android.widget.ImageView;

import ir.sanatisharif.android.konkur96.app.AppConfig;
import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.utils.MyPreferenceManager;

public class SplashActivity extends ActivityBase {


    private ImageView img1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        containerHeight(this);

        img1 = findViewById(R.id.img1);
        SpringAnimation springAnim = new SpringAnimation(img1, SpringAnimation.TRANSLATION_Y, 0);

        SpringForce springForce = new SpringForce();
        springForce.setFinalPosition(-(AppConfig.height / 3));
        springForce.setStiffness(SpringForce.STIFFNESS_LOW);
        springForce.setDampingRatio(SpringForce.DAMPING_RATIO_HIGH_BOUNCY);
        //springAnim.setStartVelocity(40);
        springAnim.setSpring(springForce);
        springAnim.start();


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
            }, 2000);
        }
        // ATTENTION: This was auto-generated to handle app links.
        Intent appLinkIntent = getIntent();
        String appLinkAction = appLinkIntent.getAction();
        Uri appLinkData = appLinkIntent.getData();
    }
}
