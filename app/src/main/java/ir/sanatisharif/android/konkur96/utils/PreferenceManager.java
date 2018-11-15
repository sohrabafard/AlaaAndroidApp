package ir.sanatisharif.android.konkur96.utils;

import android.content.Context;
import android.content.SharedPreferences;

import ir.sanatisharif.android.konkur96.app.AppConfig;


/**
 * Created by Mohamad on 4/26/2017.
 */
public class PreferenceManager {

    private static final String PREFERENCE_FILE_NAME = "Prefs";
    private static PreferenceManager preferenceManager = null;
    private static SharedPreferences sharedPreferences;
    public static SharedPreferences.Editor editor;

    private final static String USERNAME = "userName";
    private final static String USE_ID = "user_id";
    private final static String EMAIL = "email";
    private final static String API_TOKEN = "api_token";
    private final static String FIREBASE_TOKEN = "firebase_token";
    private final static String SEND_TOKEN_SERVER = "sendTokenOnServer";
    private final static String ONBOARDING = "onboarding";
    private final static String TURN_OFF_NOTIFICATION = "turn_off_notification";


    private PreferenceManager() {
        sharedPreferences = AppConfig.context.getApplicationContext().getSharedPreferences(PREFERENCE_FILE_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public static PreferenceManager getInatanse() {

        if (preferenceManager == null)
            preferenceManager = new PreferenceManager();
        return preferenceManager;
    }

    /**
     * String
     *
     * @param key
     * @param value
     */
    public void setSharedString(final String key, final String value) {

        AppConfig.HANDLER.post(new Runnable() {
            @Override
            public void run() {
                editor.putString(key, value).commit();
            }
        });

    }

    public String getSharedString(final String key, final String value) {

        return sharedPreferences.getString(key, value);
    }

    /**
     * Boolean
     *
     * @param key
     * @param value
     */
    public void setSharedBoolean(final String key, final Boolean value) {

        AppConfig.HANDLER.post(new Runnable() {
            @Override
            public void run() {
                editor.putBoolean(key, value).commit();
            }
        });

    }

    public Boolean getSharedBoolean(final String key, final Boolean value) {

        return sharedPreferences.getBoolean(key, value);
    }

    /**
     * int
     *
     * @param key
     * @param value
     */
    public void setSharedInt(final String key, final int value) {

        AppConfig.HANDLER.post(new Runnable() {
            @Override
            public void run() {
                editor.putInt(key, value).commit();
            }
        });

    }

    public int getSharedInt(final String key, final int value) {

        return sharedPreferences.getInt(key, value);
    }

    //-------------------------------------------------------

    public void setOnboarding(boolean onboarding) {
        setSharedBoolean(ONBOARDING, onboarding);
    }

    public boolean getOnboarding() {
        return getSharedBoolean(ONBOARDING, false);
    }

    public void setFirebaseToken(String firebaseToken) {
        setSharedString(FIREBASE_TOKEN, firebaseToken);
    }

    public String getFirebaseToken() {
        return getSharedString(FIREBASE_TOKEN, "");
    }

    //send turn off
    public void setTurnOffNotification(Boolean value) {
        setSharedBoolean(TURN_OFF_NOTIFICATION, value);
    }

    public Boolean getTurnOffNotification() {
        return getSharedBoolean(TURN_OFF_NOTIFICATION, false);
    }
}
