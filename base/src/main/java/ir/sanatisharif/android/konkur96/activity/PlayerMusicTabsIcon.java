package ir.sanatisharif.android.konkur96.activity;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.fragment.AllaMainFrg;
import ir.sanatisharif.android.konkur96.fragment.DashboardMainFrg;
import ir.sanatisharif.android.konkur96.fragment.VideoDownloadedFrg;

public class PlayerMusicTabsIcon extends AppCompatActivity {
    
    
    public View parent_view;
    
    private SectionsPagerAdapter viewPagerAdapter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_player_music);
        parent_view = findViewById(R.id.parent_view);
        
        initToolbar();
        initComponent();
    }
    
    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Songs");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setSystemBarColor(this);
    }
    
    private void initComponent() {
        ViewPager view_pager = findViewById(R.id.view_pager);
        TabLayout tab_layout = findViewById(R.id.tab_layout);
        
        setupViewPager(view_pager);
        tab_layout.setupWithViewPager(view_pager);
        
        tab_layout.getTabAt(0).setIcon(R.drawable.ic_person);
        tab_layout.getTabAt(1).setIcon(R.drawable.ic_person);
        tab_layout.getTabAt(2).setIcon(R.drawable.ic_person);
        tab_layout.getTabAt(3).setIcon(R.drawable.ic_person);
        
        // set icon color pre-selected
        tab_layout.getTabAt(0).getIcon().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
        tab_layout.getTabAt(1).getIcon().setColorFilter(getResources().getColor(R.color.Gray), PorterDuff.Mode.SRC_IN);
        tab_layout.getTabAt(2).getIcon().setColorFilter(getResources().getColor(R.color.Gray), PorterDuff.Mode.SRC_IN);
        tab_layout.getTabAt(3).getIcon().setColorFilter(getResources().getColor(R.color.Gray), PorterDuff.Mode.SRC_IN);
        
        tab_layout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                getSupportActionBar().setTitle(viewPagerAdapter.getTitle(tab.getPosition()));
                tab.getIcon().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
            }
            
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter(getResources().getColor(R.color.Gray), PorterDuff.Mode.SRC_IN);
            }
            
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            
            }
        });
    }
    
    public void setSystemBarColor(Activity act) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = act.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(act.getResources().getColor(R.color.colorPrimaryDark));
        }
    }
    
    private void setupViewPager(ViewPager viewPager) {
        viewPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(VideoDownloadedFrg.newInstance(), "Songs");
        viewPagerAdapter.addFragment(AllaMainFrg.newInstance(), "Albums");
        viewPagerAdapter.addFragment(VideoDownloadedFrg.newInstance(), "Artists");
        viewPagerAdapter.addFragment(DashboardMainFrg.newInstance(), "Playlist");
        viewPager.setAdapter(viewPagerAdapter);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_search_setting, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == android.R.id.home) {
//            finish();
//        } else {
//            Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
//        }
//        return super.onOptionsItemSelected(item);
//    }
    
    private class SectionsPagerAdapter extends FragmentPagerAdapter {
        
        private final List<Fragment> mFragmentList      = new ArrayList<>();
        private final List<String>   mFragmentTitleList = new ArrayList<>();
        
        SectionsPagerAdapter(FragmentManager manager) {
            super(manager);
        }
        
        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }
        
        @Override
        public int getCount() {
            return mFragmentList.size();
        }
        
        void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }
        
        public String getTitle(int position) {
            return mFragmentTitleList.get(position);
        }
        
        @Override
        public CharSequence getPageTitle(int position) {
            return null;
        }
    }
    
}

