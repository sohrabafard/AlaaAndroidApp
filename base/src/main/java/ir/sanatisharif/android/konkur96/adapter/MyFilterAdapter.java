package ir.sanatisharif.android.konkur96.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.fragment.FilterShowEntityFrg;
import ir.sanatisharif.android.konkur96.fragment.RegisterFrg;

/**
 * Created by Mohamad on 3/2/2019.
 */

public class MyFilterAdapter extends FragmentStatePagerAdapter {

    private static final String TAG = "MyFilterAdapter";
    private List<FilterShowEntityFrg> mFrgArrayList;
    private FragmentManager mFragmentManager;
    private Context context;

    public MyFilterAdapter(Context context, FragmentManager fm, List<FilterShowEntityFrg> mFrgArrayList) {
        super(fm);
        this.mFragmentManager = fm;
        this.mFrgArrayList = mFrgArrayList;
        this.context = context;
    }

//    @Override
//    public Object instantiateItem(ViewGroup container, int position) {
//        return super.instantiateItem(container, position);
//    }

    @Override
    public Fragment getItem(int position) {
        return mFrgArrayList.get(position);
    }

    @Override
    public int getCount() {
        return mFrgArrayList.size();
    }

    /**
     * This method is only gets called when we invoke {@link #notifyDataSetChanged()} on this adapter.
     * Returns the index of the currently active fragments.
     * There could be minimum two and maximum three active fragments(suppose we have 3 or more  fragments to show).
     * If there is only one fragment to show that will be only active fragment.
     * If there are only two fragments to show, both will be in active state.
     * PagerAdapter keeps left and right fragments of the currently visible fragment in ready/active state so that it could be shown immediate on swiping.
     * Currently Active Fragments means one which is currently visible one is before it and one is after it.
     *
     * @param object Active Fragment reference
     * @return Returns the index of the currently active fragments.
     */
    @Override
    public int getItemPosition(Object object) {

        if (mFrgArrayList.contains(object)) {
            Log.i("LOG", "setupViewPager: getItemPosition ");
            return mFrgArrayList.indexOf((FilterShowEntityFrg) object);
        } else {
            Log.i("LOG", "setupViewPager: getItemPosition POSITION_NONE ");
            return POSITION_NONE;
        }
    }

    public View getTabView(int position, String title) {

        View v = LayoutInflater.from(context).inflate(R.layout.custom_tab, null);
        TextView tv = (TextView) v.findViewById(R.id.txtTab);
        tv.setText(title);
        ImageView img = (ImageView) v.findViewById(R.id.imgTab);
        // img.setImageResource(imageResId[position]);
        return v;
    }
}