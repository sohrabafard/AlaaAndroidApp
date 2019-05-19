package ir.sanatisharif.android.konkur96.dialog;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.preference.DialogPreference;
import android.util.AttributeSet;
import android.util.Log;

import ir.sanatisharif.android.konkur96.R;

/**
 * Created by Mohamad on 11/11/2018.
 */

public class DialogPrefDownload extends DialogPreference {

    private String mValue;

    public DialogPrefDownload(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public DialogPrefDownload(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public DialogPrefDownload(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DialogPrefDownload(Context context) {
        super(context);
    }

    public String getValue() {
        return mValue;
    }

    public void setValue(String value) {
        this.mValue = value;

        // Save to SharedPreference
        persistString(mValue);
    }

    /**
     * Called when a Preference is being inflated and the default value attribute needs to be read
     */
    @Override
    protected Object onGetDefaultValue(TypedArray a, int index) {
        // The type of this preference is Int, so we read the default value from the attributes
        // as Int. Fallback value is set to 0.
        return a.getInt(index, 0);
    }

    //

    /**
     * Returns the layout resource that is used as the content View for the dialog
     */
    @Override
    public int getDialogLayoutResource() {
        return R.layout.dialog_setting_custom_download;
    }

    @Override
    public CharSequence getPositiveButtonText() {
        return "بله";
    }

    @Override
    public CharSequence getNegativeButtonText() {
        return "لغو";
    }


    /**
     * Implement this to set the initial value of the Preference.
     */
    @Override
    protected void onSetInitialValue(boolean restorePersistedValue, Object defaultValue) {
        // If the value can be restored, do it. If not, use the default value.
        //  Log.i("LOG", "onDialogClosed: " + (String) defaultValue + " " + restorePersistedValue);
        setValue(restorePersistedValue ? getPersistedString(mValue) : (String) defaultValue);
    }
}
