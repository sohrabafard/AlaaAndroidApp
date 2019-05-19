package ir.sanatisharif.android.konkur96.service;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessagingService;

import ir.sanatisharif.android.konkur96.utils.MyPreferenceManager;

/**
 * Created by Mohamad on 12/11/2017.
 */

public class MyFirebaseInstanceIDService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseIIDService";


    @Override
    public void onNewToken(String refreshedToken) {
        super.onNewToken(refreshedToken);

        //  When you need to retrieve the current token, call
        // FirebaseInstanceId.getInstance().getInstanceId().getResult().getToken();
        //Displaying token on logcat
        Log.d(TAG, "Refreshed token: " + refreshedToken);
        Log.d(TAG, "Refreshed token: " + FirebaseInstanceId.getInstance().getInstanceId().getResult().getToken());

        storeToken(refreshedToken);

    }

    private void storeToken(String token) {
        //save the SFM later
        MyPreferenceManager.getInatanse().setFirebaseToken(token);
    }
}
