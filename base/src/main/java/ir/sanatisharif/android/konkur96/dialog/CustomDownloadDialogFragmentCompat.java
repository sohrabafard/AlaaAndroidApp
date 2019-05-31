package ir.sanatisharif.android.konkur96.dialog;

import android.os.Bundle;
import androidx.preference.DialogPreference;
import androidx.preference.PreferenceDialogFragmentCompat;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import ir.sanatisharif.android.konkur96.R;

/**
 * Created by Mohamad on 11/11/2018.
 */

public class CustomDownloadDialogFragmentCompat extends PreferenceDialogFragmentCompat {

    String[] playerValue;
    String[] playerEntry;
    private RadioGroup radioGroup;
    private String value;

    public static CustomDownloadDialogFragmentCompat newInstance(String key, String value) {
        final CustomDownloadDialogFragmentCompat
                fragment = new CustomDownloadDialogFragmentCompat();
        final Bundle b = new Bundle(1);
        b.putString(ARG_KEY, key);
        b.putString("value", value);
        fragment.setArguments(b);
        return fragment;
    }


    @Override
    protected void onBindDialogView(View view) {
        super.onBindDialogView(view);

        playerValue = getResources().getStringArray(R.array.player_quality_value);
        playerEntry = getResources().getStringArray(R.array.player_quality_entries);

        init(view, getArguments().getString("value"));

    }

    private void init(View view, String value) {
        radioGroup = view.findViewById(R.id.radioGroup);

        ((RadioButton) radioGroup.getChildAt(0)).setText(playerEntry[0] + " - " + playerValue[0]);
        ((RadioButton) radioGroup.getChildAt(1)).setText(playerEntry[1] + " - " + playerValue[1]);
        ((RadioButton) radioGroup.getChildAt(2)).setText(playerEntry[2] + " - " + playerValue[2]);

        if (value != null) {
            if (value.equals(playerValue[0])) {
                ((RadioButton) radioGroup.getChildAt(0)).setChecked(true);
            } else if (value.equals(playerValue[1])) {
                ((RadioButton) radioGroup.getChildAt(1)).setChecked(true);
            } else if (value.equals(playerValue[2])) {
                ((RadioButton) radioGroup.getChildAt(2)).setChecked(true);
            }
        }

    }

    @Override
    public void onDialogClosed(boolean positiveResult) {

        if (positiveResult) {

            if (radioGroup.getCheckedRadioButtonId() == R.id.radioExcellentQuality) {
                value = playerValue[0];
            } else if (radioGroup.getCheckedRadioButtonId() == R.id.radioHighQuality) {
                value = playerValue[1];
            } else if (radioGroup.getCheckedRadioButtonId() == R.id.radioMediumQuality) {
                value = playerValue[2];
            }

            // Save the value
            DialogPreference preference = getPreference();
            if (preference instanceof DialogPrefDownload) {
                DialogPrefDownload customDialogSettingDownload = ((DialogPrefDownload) preference);
                // This allows the client to ignore the user value.
                if (customDialogSettingDownload.callChangeListener(value)) {
                    // Save the value
                    customDialogSettingDownload.setValue(value);
                }
            }
        }

    }
}
