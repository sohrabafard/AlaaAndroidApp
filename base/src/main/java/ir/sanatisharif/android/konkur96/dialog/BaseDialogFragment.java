package ir.sanatisharif.android.konkur96.dialog;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;

import ir.sanatisharif.android.konkur96.app.AppConfig;


/**
 * Created by Mohamad on 7/1/2017.
 */

public abstract class BaseDialogFragment<T> extends DialogFragment {

    private T mActivityInstance;

    public final T getActivityInstance() {
        return mActivityInstance;
    }

    @Override
    public void onAttach(Activity activity) {
        mActivityInstance = (T) activity;
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mActivityInstance = null;
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
                v1.setTypeface(AppConfig.fontIRSensLight);
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
