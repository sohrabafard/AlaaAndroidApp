package ir.sanatisharif.android.konkur96.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceManager;
import android.util.Log;
import android.view.View;

import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.dialog.CustomStorageDialogFragmentCompat;
import ir.sanatisharif.android.konkur96.dialog.DialogPrefChangeStorage;
import ir.sanatisharif.android.konkur96.dialog.DialogPrefDownload;
import ir.sanatisharif.android.konkur96.dialog.CustomDownloadDialogFragmentCompat;

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

        try {
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

            Preference pref1 = findPreference("player_quality");
            pref1.setSummary(sharedPreferences.getString(pref1.getKey(), "240"));

            // Preference pref2 = findPreference("storage");
            // pref2.setSummary(sharedPreferences.getString(pref2.getKey(), "external"));

            onSharedPreferenceChanged(sharedPreferences, getString(R.string.setting_external_download));
        } catch (Exception ex) {
            Log.i(TAG, "onCreatePreferences: " + ex.getMessage());
        }

    }

    @Override
    public void onResume() {
        super.onResume();
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

        if (preference instanceof ListPreference) {
            ListPreference listPreference = (ListPreference) preference;

            int prefIndex = listPreference.findIndexOfValue(sharedPreferences.getString(key, ""));
            if (prefIndex >= 0) {
                preference.setSummary(listPreference.getEntries()[prefIndex]);
            }
            //  Log.i(TAG, "onSharedPreferenceChanged1: " + listPreference.getEntries()[prefIndex]);
        }
        if (preference instanceof DialogPrefDownload) {
            preference.setSummary(sharedPreferences.getString(preference.getKey(), "240"));

        }
        if (preference instanceof DialogPrefChangeStorage) {
            preference.setSummary(sharedPreferences.getString(preference.getKey(), "external"));
        }


    }

    @Override
    public void onDisplayPreferenceDialog(Preference preference) {

        DialogFragment dialogFragment = null;

        if (preference instanceof DialogPrefDownload) {

            dialogFragment = CustomDownloadDialogFragmentCompat.newInstance(
                    preference.getKey(),
                    ((DialogPrefDownload) preference).getValue());

            if (dialogFragment != null) {
                dialogFragment.setTargetFragment(this, 0);
                dialogFragment.show(this.getFragmentManager(), "android.support.v7.preference" +
                        ".PreferenceFragment.DIALOG");
            }
        } else if (preference instanceof DialogPrefChangeStorage) {

            dialogFragment = CustomStorageDialogFragmentCompat.newInstance(
                    preference.getKey(),
                    ((DialogPrefChangeStorage) preference).getValue());

            if (dialogFragment != null) {
                dialogFragment.setTargetFragment(this, 0);
                dialogFragment.show(this.getFragmentManager(), "android.support.v7.preference" +
                        ".PreferenceFragment.DIALOG");
            }
        } else {
            // Dialog creation could not be handled here. Try with the super method.
            super.onDisplayPreferenceDialog(preference);
        }
    }
}
