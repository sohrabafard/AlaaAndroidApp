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

public class CustomStorageDialogFragmentCompat extends PreferenceDialogFragmentCompat {

    String[] storageValue;
    String[] storageEntry;
    private RadioGroup radioGroup;
    private String value;

    public static CustomStorageDialogFragmentCompat newInstance(String key, String value) {
        final CustomStorageDialogFragmentCompat
                fragment = new CustomStorageDialogFragmentCompat();
        final Bundle b = new Bundle();
        b.putString(ARG_KEY, key);
        b.putString("value", value);
        fragment.setArguments(b);
        return fragment;
    }


    @Override
    protected void onBindDialogView(View view) {
        super.onBindDialogView(view);

        storageEntry = getResources().getStringArray(R.array.storage_entries);
        storageValue = getResources().getStringArray(R.array.storage_value);

        init(view, getArguments().getString("value"));

    }

    private void init(View view, String value) {

        radioGroup = view.findViewById(R.id.radioGroup);

        ((RadioButton) radioGroup.getChildAt(0)).setText(storageEntry[0]);
        ((RadioButton) radioGroup.getChildAt(1)).setText(storageEntry[1]);

        if (value != null) {
            if (value.equals(storageValue[0])) {
                ((RadioButton) radioGroup.getChildAt(0)).setChecked(true);
            } else if (value.equals(storageValue[1])) {
                ((RadioButton) radioGroup.getChildAt(1)).setChecked(true);
            }
        }

    }

    @Override
    public void onDialogClosed(boolean positiveResult) {

        if (positiveResult) {

            if (radioGroup.getCheckedRadioButtonId() == R.id.radioInternal) {
                value = storageValue[0];
            } else if (radioGroup.getCheckedRadioButtonId() == R.id.radioExternal) {
                value = storageValue[1];
            }

            // Save the value
            DialogPreference preference = getPreference();
            if (preference instanceof DialogPrefChangeStorage) {
                DialogPrefChangeStorage dialogPrefChangeStorage = ((DialogPrefChangeStorage) preference);
                // This allows the client to ignore the user value.
                if (dialogPrefChangeStorage.callChangeListener(value)) {
                    // Save the value
                    dialogPrefChangeStorage.setValue(value);
                }
            }
        }

    }
}
