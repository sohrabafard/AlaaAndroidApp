package ir.sanatisharif.android.konkur96.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.app.AppConfig;

/**
 * Created by Mohamad on 11/2/2018.
 */

public abstract class BaseFragment extends Fragment {

    public void setToolbar(Toolbar mToolbar) {

        mToolbar = (Toolbar) getView().findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanseState) {
        return createFragmentView(inflater, container, savedInstanseState);
    }

    public abstract View createFragmentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    public void overrideFonts(Context context, final View v) {
        try {
            if (v instanceof ViewGroup) {
                ViewGroup vg = (ViewGroup) v;
                for (int i = 0; i < vg.getChildCount(); i++) {
                    View child = vg.getChildAt(i);
                    overrideFonts(context, child);
                }
            } else if (v instanceof TextView) {
                TextView v1 = (TextView) v;
                v1.setTypeface(AppConfig.fontIRSensNumber);
            }
        } catch (Exception e) {
        }
    }


}
