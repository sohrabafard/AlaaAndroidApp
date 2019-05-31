package ir.sanatisharif.android.konkur96.utils;

import android.content.Context;
import android.content.SharedPreferences;

import ir.sanatisharif.android.konkur96.app.AppConfig;


/**
 * Created by Mohamad on 4/26/2017.
 */
public class MyPreferenceManager {

    private static final String                   PREFERENCE_FILE_NAME  = "Prefs";
    private final static String                   API_TOKEN             = "api_token";
    private final static String                   FIREBASE_TOKEN        = "firebase_token";
    private final static String                   SEND_TOKEN_TO_SERVER  = "sendTokenToServer";
    private final static String                   ONBOARDING            = "onboarding";
    private final static String                   TURN_OFF_NOTIFICATION = "turn_off_notification";
    private final static String                   UPDATE_SHOW_DIALOG    = "";
    private final static String                   LAST_VERSION_CODE     = "last_version_code";
    private final static String                   AUTHORIZE             = "authorization";
    public static        SharedPreferences.Editor editor;
    private static       MyPreferenceManager      preferenceManager     = null;
    private static       SharedPreferences        sharedPreferences;


    private MyPreferenceManager() {
        sharedPreferences =
                AppConfig.context.getApplicationContext().getSharedPreferences(PREFERENCE_FILE_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public static MyPreferenceManager getInatanse() {

        if (preferenceManager == null)
            preferenceManager = new MyPreferenceManager();
        return preferenceManager;
    }

    public String getApiToken() {
        return getSharedString(API_TOKEN, "");
    }

    /**
     * Api Token
     *
     * @param token
     */
    public void setApiToken(String token) {
        setSharedString(API_TOKEN, token);
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

    public boolean getOnboarding() {
        return getSharedBoolean(ONBOARDING, false);
    }

    public void setOnboarding(boolean onboarding) {
        setSharedBoolean(ONBOARDING, onboarding);
    }

    public String getFirebaseToken() {
        return getSharedString(FIREBASE_TOKEN, "");
    }

    public void setFirebaseToken(String firebaseToken) {
        setSharedString(FIREBASE_TOKEN, firebaseToken);
    }

    public boolean isSendTokenToServer() {
        return getSharedBoolean(SEND_TOKEN_TO_SERVER, false);
    }

    /**
     * send firebase Token to server
     *
     * @param sended
     */
    public void setSendTokenToServer(boolean sended) {
        setSharedBoolean(SEND_TOKEN_TO_SERVER, sended);
    }

    public Boolean getTurnOffNotification() {
        return getSharedBoolean(TURN_OFF_NOTIFICATION, false);
    }

    //send turn off
    public void setTurnOffNotification(Boolean value) {
        setSharedBoolean(TURN_OFF_NOTIFICATION, value);
    }

    public int getLastVersionCode() {
        return getSharedInt(LAST_VERSION_CODE, 44);
    }

    public void setLastVersionCode(int value) {
        setSharedInt(LAST_VERSION_CODE, value);
    }
}
