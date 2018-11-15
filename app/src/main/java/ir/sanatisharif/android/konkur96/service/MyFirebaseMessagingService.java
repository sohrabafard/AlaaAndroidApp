package ir.sanatisharif.android.konkur96.service;

import android.content.Intent;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import ir.sanatisharif.android.konkur96.activity.MainActivity;
import ir.sanatisharif.android.konkur96.utils.MyNotificationManager;
import ir.sanatisharif.android.konkur96.utils.PreferenceManager;


/**
 * Created by Mohamad on 12/11/2017.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "LOG";


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);


        if (remoteMessage.getData().size() > 0) {
            try {

                JSONObject json = new JSONObject(remoteMessage.getData().toString());
                Log.e(TAG, "Data Payload1: " + json.toString());
                sendPushNotification(json);

            } catch (Exception e) {
                Log.i(TAG, "Exception: " + e.getMessage());
            }
        }
    }


    @Override
    public void onNewToken(String refreshedToken) {
        super.onNewToken(refreshedToken);

        //  When you need to retrieve the current token, call
        // FirebaseInstanceId.getInstance().getInstanceId().getResult().getToken();
        //Displaying token on logcat

        storeToken(refreshedToken);

    }

    private void storeToken(String token) {
        //save the SFM later
        PreferenceManager.getInatanse().setFirebaseToken(token);
    }

    private void sendPushNotification(JSONObject json) {
        //optionally we can display the json into log
        Log.e(TAG, "Notification JSON " + json.toString());
        try {
            //getting the json data
            JSONObject data = json.getJSONObject("data");

            //parsing json data
            String title = data.getString("title");
            String message = data.getString("message");
            //String imageUrl = data.getString("image");

            //creating MyNotificationManager object
            MyNotificationManager mNotificationManager = new MyNotificationManager(getApplicationContext());

            //creating an intent for the notification
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            mNotificationManager.showSmallNotification(title, message, intent);


        } catch (JSONException e) {
            Log.e(TAG, "Json Exception: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }

}
