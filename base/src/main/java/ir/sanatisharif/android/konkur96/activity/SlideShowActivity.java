package ir.sanatisharif.android.konkur96.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.rd.PageIndicatorView;

import java.util.Timer;
import java.util.TimerTask;

import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.adapter.PageViewAdapter;

public class SlideShowActivity extends AppCompatActivity {

    private ViewPager pagerSlideShow;
    private PageIndicatorView pageIndicatorView;
    private PageViewAdapter adapter;

    private int[] path = {};

    private int currentPage = 0;

    public long DelayMS = 5000;
    private long PeriodMS = 5000;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        pagerSlideShow = findViewById(R.id.viewPager);
        pageIndicatorView = findViewById(R.id.pageIndicatorView);


        pageIndicatorView.setCount(path.length);
        pageIndicatorView.setSelection(0);

        adapter = new PageViewAdapter(this, path);
        pagerSlideShow.setAdapter(adapter);

        updateIndicator();

        pagerSlideShow.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentPage = position;
                pageIndicatorView.setSelection(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void updateIndicator() {

        final Handler handler = new Handler();
        handler.postDelayed(() -> {
            if (currentPage == path.length) {
                currentPage = 0;
            }

            pagerSlideShow.setCurrentItem(currentPage++, true);
        }, 100);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                handler.post(this);
            }
        },  DelayMS, PeriodMS);
    }

}
