package ir.sanatisharif.android.konkur96.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.account.AccountInfo;
import ir.sanatisharif.android.konkur96.activity.MainActivity;
import ir.sanatisharif.android.konkur96.handler.MainRepository;
import ir.sanatisharif.android.konkur96.listener.api.IServerCallbackObject;
import ir.sanatisharif.android.konkur96.model.user.User;
import ir.sanatisharif.android.konkur96.ui.GlideApp;
import ir.sanatisharif.android.konkur96.utils.AuthToken;
import ir.sanatisharif.android.konkur96.utils.MyPreferenceManager;
import ir.sanatisharif.android.konkur96.utils.NotificationID;

import static ir.sanatisharif.android.konkur96.app.AppConstants.ACCOUNT_TYPE;


/**
 * Created by Mohamad on 12/11/2017.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";

    private String mTitle;
    private String mBody;
    private String mUrl ="";
    private String mImage = "";
    private String mActionTXT = "";

    private MainRepository mainRepository;

    @Override
    public void onCreate() {
        Log.e(TAG,"onCreate");
        super.onCreate();
        mainRepository = new MainRepository();
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.e(TAG,"getMessage");
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

        sendNotification();
    }

    private void handleNotificationNow(RemoteMessage.Notification notification) {
        mTitle = notification.getTitle();
        mBody = notification.getBody();
    }

    @Override
    public void onNewToken(String token) {
        Log.e(TAG,"onNewToken");
        MyPreferenceManager.getInatanse().setSendTokenToServer(false);
        sendRegistrationToServer(token);
    }

    private void handleDataNow(Map<String, String> remoteMessageData) {
        mImage = remoteMessageData.get("image");
        mUrl = remoteMessageData.get("action_url");
        mActionTXT = remoteMessageData.get("action_txt");
    }

    private void sendRegistrationToServer(String firebaseToken) {

        AccountInfo accountInfo = new AccountInfo(getApplicationContext());
        MyPreferenceManager.getInatanse().setFirebaseToken(firebaseToken);
        final User user = accountInfo.getInfo(ACCOUNT_TYPE);

        AuthToken.getInstant().get(this, token -> {
            mainRepository.sendRegistrationToServer(user.getId(), firebaseToken, token, new IServerCallbackObject() {
                @Override
                public void onSuccess(Object obj) {
                    MyPreferenceManager.getInatanse().setSendTokenToServer(true);
                }

                @Override
                public void onFailure(String message) {

                }
            });
        });
    }

    private void sendNotification() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);


        if (mUrl != null) {
            Log.d(TAG,mUrl);
            intent.setAction(Intent.ACTION_VIEW);
            Uri data = Uri.parse(mUrl);
            intent.setData(data);
        }

        final Bitmap[] bitmap = new Bitmap[1];
        if (mImage != null) {
            Log.d(TAG,mImage);
            GlideApp
                    .with(getApplicationContext())
                    .asBitmap()
                    .load(mImage)
                    .listener(new RequestListener<Bitmap>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                            bitmap[0] = resource;
                            return false;
                        }
                    })
                    .submit();
        }

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        String channelId = getString(R.string.default_notification_channel_id);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setLargeIcon(bitmap[0])
                        .setStyle(new NotificationCompat.BigPictureStyle()
                                .setSummaryText(mTitle)
                                .bigPicture(bitmap[0]))
                        .setContentTitle(mTitle)
                        .setContentText(mBody)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .addAction(R.drawable.ic_favorite_on,
                                "buy",pendingIntent)
                        .setContentIntent(pendingIntent);


        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "اخبار آلاء",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(NotificationID.getID(), notificationBuilder.build());
    }

}
