package ir.sanatisharif.android.konkur96.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;
import com.google.android.gms.common.wrappers.InstantApps;

import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.account.AccountInfo;
import ir.sanatisharif.android.konkur96.account.AuthenticatorActivity;

import static ir.sanatisharif.android.konkur96.app.AppConstants.ACCOUNT_TYPE;

/**
 * Created by Mohamad on 10/26/2018.
 */

public class OnBoardingActivity extends ActivityBase {

    private static final int MAX_STEP = 4;
    private AccountInfo accountInfo;
    private ViewPager viewPager;
    private Button btnNext;
    private String[] desc;
    //  viewpager change listener
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(final int position) {
            bottomProgressDots(position);
            if (viewPager.getCurrentItem() == desc.length - 1) {
                btnNext.setText(getString(R.string.go));
            } else {
                btnNext.setText(getString(R.string.next));
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };
    private int[] image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding);

        accountInfo = new AccountInfo(getApplicationContext(), this);
        loadData();
        initUI();
        bottomProgressDots(0); // adding bottom dots
    }

    private void loadData() {

        desc = getResources().getStringArray(R.array.onBoarding);
        image = new int[]{
                R.drawable.illustrator_1,
                R.drawable.illustrator_2,
                R.drawable.illustrator_3,
                R.drawable.illustrator_4
        };
    }

    private void initUI() {

        //init ui
        viewPager = findViewById(R.id.view_pager);
        btnNext = findViewById(R.id.btn_next);
        ripple(btnNext, 8);
        // init pager
        MyViewPagerAdapter myViewPagerAdapter = new MyViewPagerAdapter();
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);
        viewPager.setOffscreenPageLimit(4);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int current = viewPager.getCurrentItem() + 1;
                if (current < MAX_STEP) {
                    // move to next screen
                    viewPager.setCurrentItem(current);
                } else {
                    //register
                    if (!InstantApps.isInstantApp(getApplicationContext())) {
                        if (accountInfo.ExistAccount(ACCOUNT_TYPE)) {
                            startActivity(new Intent(OnBoardingActivity.this, MainActivity.class));
                        }
                    } else {
                        if (accountInfo.ExistAccount(ACCOUNT_TYPE)) {
                            startActivity(new Intent(OnBoardingActivity.this, MainActivity.class));
                        }else {
                            startActivity(new Intent(OnBoardingActivity.this, AuthenticatorActivity.class));
                        }
                    }
                    finish();
                }
            }
        });
    }

    private void bottomProgressDots(int current_index) {
        LinearLayout dotsLayout = findViewById(R.id.layoutDots);
        ImageView[] dots = new ImageView[MAX_STEP];

        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new ImageView(this);
            int width_height = 15;
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(width_height, width_height));
            params.setMargins(10, 10, 10, 10);
            dots[i].setLayoutParams(params);
            dots[i].setImageResource(R.drawable.shape_circle);
            dots[i].setColorFilter(getResources().getColor(R.color.White), PorterDuff.Mode.SRC_IN);
            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0) {
            dots[current_index].setImageResource(R.drawable.shape_circle);
            dots[current_index].setColorFilter(getResources().getColor(R.color.shades_4), PorterDuff.Mode.SRC_IN);
        }
    }

    private void ripple(View view, int radius) {
        MaterialRippleLayout.on(view)
                .rippleOverlay(true)
                .rippleAlpha(0.1f)
                .rippleColor(Color.parseColor("#FFF1CC"))
                .rippleRoundedCorners(radius)
                .rippleHover(true)
                .create();
    }

    /**
     * View pager adapter
     */
    public class MyViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;

        MyViewPagerAdapter() {
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(R.layout.item_card_wizard, container, false);
            //((TextView) view.findViewById(R.id.title)).setText(about_title_array[position]);
            ((TextView) view.findViewById(R.id.description)).setText(desc[position]);
            ((ImageView) view.findViewById(R.id.image)).setImageResource(image[position]);

            container.addView(view);
            return view;
        }

        @Override
        public int getCount() {
            return desc.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }
}
