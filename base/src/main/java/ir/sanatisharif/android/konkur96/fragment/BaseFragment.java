package ir.sanatisharif.android.konkur96.fragment;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
import android.content.Context;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;

import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.app.AppConfig;

/**
 * Created by Mohamad on 11/2/2018.
 */

public abstract class BaseFragment extends Fragment implements LifecycleOwner {

    private Toolbar toolbar;
    private LifecycleRegistry mLifecycleRegistry;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanseState) {
        return createFragmentView(inflater, container, savedInstanseState);
    }

    public abstract View createFragmentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);


    @Override
    public LifecycleRegistry getLifecycle() {
        return mLifecycleRegistry;
    }

    @Override
    public void onStart() {
        super.onStart();

        mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_START);
    }

    @Override
    public void onStop() {
        super.onStop();
        mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_STOP);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mLifecycleRegistry = new LifecycleRegistry(this);
        mLifecycleRegistry.markState(Lifecycle.State.CREATED);
    }

    public void setToolbar(Toolbar mToolbar, String txtTitle) {

        setHasOptionsMenu(true);
        mToolbar = (Toolbar) getView().findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_back);

        ((TextView) (mToolbar.findViewById(R.id.txtToolbarTitle))).setText(txtTitle);

        toolbar = mToolbar;
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

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

    void ripple(View view, int radius) {
        MaterialRippleLayout.on(view)
                .rippleOverlay(true)
                .rippleAlpha(0.1f)
                .rippleColor(Color.parseColor("#FFB700"))
                .rippleRoundedCorners(radius)
                .rippleHover(true)
                .create();
    }

}
