package ir.sanatisharif.android.konkur96.fragment;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.DialogPreference;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.graphics.drawable.ColorDrawable;

import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.dialog.CustomDialogSettingDownload;
import ir.sanatisharif.android.konkur96.dialog.CustomSelectDialogFragmentCompat;

/**
 * Created by Mohamad on 11/3/2018.
 */

public class SettingsFragment extends PreferenceFragmentCompat implements
        SharedPreferences.OnSharedPreferenceChangeListener {

    private static final String TAG = SettingsFragment.class.getSimpleName();

    SharedPreferences sharedPreferences;


    public static SettingsFragment newInstance() {

        Bundle args = new Bundle();

        SettingsFragment fragment = new SettingsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //  setDivider();
    }

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {

        //add xml
        addPreferencesFromResource(R.xml.settings);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        onSharedPreferenceChanged(sharedPreferences, getString(R.string.setting_external_download));
    }

    @Override
    public void onResume() {
        super.onResume();
        //unregister the preferenceChange listener
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }


    @Override
    public void onPause() {
        super.onPause();
        //unregister the preference change listener
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

        Preference preference = findPreference(key);

        if (key.equals(getString(R.string.setting_external_download))) {
            Preference connectionPref = findPreference(key);
            //Log.i(TAG, "onSharedPreferenceChanged1: " + sharedPreferences.getBoolean(key, true));
        } else if (preference instanceof ListPreference) {
            ListPreference listPreference = (ListPreference) preference;

            int prefIndex = listPreference.findIndexOfValue(sharedPreferences.getString(key, ""));
            if (prefIndex >= 0) {
                preference.setSummary(listPreference.getEntries()[prefIndex]);
            }
          //  Log.i(TAG, "onSharedPreferenceChanged1: " + listPreference.getEntries()[prefIndex]);
        } else if (preference instanceof CustomDialogSettingDownload) {
            preference.setSummary(sharedPreferences.getString(preference.getKey(), ""));
        }


    }

    @Override
    public void onDisplayPreferenceDialog(Preference preference) {

        DialogFragment dialogFragment = null;

        if (preference instanceof CustomDialogSettingDownload) {

            //Log.i(TAG, "onDisplayPreferenceDialog: " + ((CustomDialogSettingDownload) preference).getValue());
            dialogFragment = CustomSelectDialogFragmentCompat.newInstance(
                    preference.getKey(),
                    ((CustomDialogSettingDownload) preference).getValue());
        }

        if (dialogFragment != null) {
            // The dialog was created (it was one of our custom Preferences), show the dialog for it
            dialogFragment.setTargetFragment(this, 0);
            dialogFragment.show(this.getFragmentManager(), "android.support.v7.preference" +
                    ".PreferenceFragment.DIALOG");
        } else {
            // Dialog creation could not be handled here. Try with the super method.
            super.onDisplayPreferenceDialog(preference);
        }
    }
}
