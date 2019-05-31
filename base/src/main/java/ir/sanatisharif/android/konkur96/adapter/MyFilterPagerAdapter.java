package ir.sanatisharif.android.konkur96.adapter;

import android.content.Context;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.fragment.FilterShowEntityFrg;

/**
 * Created by Mohamad on 3/2/2019.
 */

public class MyFilterPagerAdapter extends FragmentStatePagerAdapter {

    private static final String TAG = "MyFilterPagerAdapter";
    private List<FilterShowEntityFrg> mFrgArrayList;
    private Context context;

    public MyFilterPagerAdapter(Context context, FragmentManager fm, List<FilterShowEntityFrg> mFrgArrayList) {
        super(fm);
        this.mFrgArrayList = mFrgArrayList;
        this.context = context;
    }


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
            return mFrgArrayList.indexOf(object);
        } else {
            return POSITION_NONE;
        }
    }

    public View getTabView(int position, String title) {

        View v = LayoutInflater.from(context).inflate(R.layout.custom_tab, null);
        TextView tv = v.findViewById(R.id.txtTab);
        tv.setText(title);
        ImageView img = v.findViewById(R.id.imgTab);
        // img.setImageResource(imageResId[position]);
        return v;
    }
}
