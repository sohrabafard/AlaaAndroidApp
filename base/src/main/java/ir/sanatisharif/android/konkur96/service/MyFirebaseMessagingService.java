package ir.sanatisharif.android.konkur96.service;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

import ir.sanatisharif.android.konkur96.account.AccountInfo;
import ir.sanatisharif.android.konkur96.handler.MainRepository;
import ir.sanatisharif.android.konkur96.listener.api.IServerCallbackObject;
import ir.sanatisharif.android.konkur96.model.user.User;
import ir.sanatisharif.android.konkur96.utils.AuthToken;
import ir.sanatisharif.android.konkur96.utils.MyPreferenceManager;
import ir.sanatisharif.android.konkur96.utils.Notif;

import static ir.sanatisharif.android.konkur96.app.AppConstants.ACCOUNT_TYPE;


/**
 * Created by Mohamad on 12/11/2017.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "Alaa\\MyFirebaseMsgServ";

    private MainRepository mainRepository;

    private Notif mNotif;

    @Override
    public void onCreate() {
        Log.e(TAG, "onCreate");
        super.onCreate();
        mainRepository = new MainRepository();
        mNotif = Notif.get(this);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.e(TAG, "getMessage");
        // Check if message contains a data payload.
        Map<String, String> remoteMessageData = remoteMessage.getData();
        if (remoteMessageData.size() > 0) {

            handleDataNow(remoteMessageData);
        }

        // Check if message contains a notification payload.
        RemoteMessage.Notification notification = remoteMessage.getNotification();
        if (notification != null) {
            handleNotificationNow(notification);
        }

        mNotif.send();
    }


    @Override
    public void onNewToken(String token) {
        Log.e(TAG, "onNewToken");
        MyPreferenceManager.getInatanse().setSendTokenToServer(false);
        sendRegistrationToServer(token);
    }

    private void handleDataNow(Map<String, String> remoteMessageData) {
        mNotif.setImage(remoteMessageData.get("image"))
                .setActionUrl(remoteMessageData.get("action_url"))
                .setActionTXT(remoteMessageData.get("action_txt"));
    }

    private void handleNotificationNow(RemoteMessage.Notification notification) {
        mNotif.setTitle(notification.getTitle())
                .setBody(notification.getBody());
    }

    private void sendRegistrationToServer(String firebaseToken) {

        AccountInfo accountInfo = new AccountInfo(getApplicationContext());
        MyPreferenceManager.getInatanse().setFirebaseToken(firebaseToken);
        final User user = accountInfo.getInfo(ACCOUNT_TYPE);

        AuthToken.getInstant().get(this, new AuthToken.Callback() {
            @Override
            public void run(@NonNull String token) {
                mainRepository.sendRegistrationToServer(user.getId(), firebaseToken, token, new IServerCallbackObject() {
                    @Override
                    public void onSuccess(Object obj) {
                        MyPreferenceManager.getInatanse().setSendTokenToServer(true);
                    }

                    @Override
                    public void onFailure(String message) {

                    }
                });
            }

            @Override
            public void nill() {
                MyPreferenceManager.getInatanse().setSendTokenToServer(false);
            }
        });
    }

}
