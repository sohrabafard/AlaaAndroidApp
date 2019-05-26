package ir.sanatisharif.android.konkur96.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.NotificationTarget;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.account.AccountInfo;
import ir.sanatisharif.android.konkur96.activity.MainActivity;
import ir.sanatisharif.android.konkur96.handler.MainRepository;
import ir.sanatisharif.android.konkur96.listener.api.IServerCallbackObject;
import ir.sanatisharif.android.konkur96.model.user.User;
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
    private String mActionUrl = "";
    private String mImage = "";
    private String mActionTXT = "";

    private MainRepository mainRepository;

    @Override
    public void onCreate() {
        Log.e(TAG, "onCreate");
        super.onCreate();
        mainRepository = new MainRepository();
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

        sendNotification();
    }

    private void handleNotificationNow(RemoteMessage.Notification notification) {
        mTitle = notification.getTitle();
        mBody = notification.getBody();
    }

    @Override
    public void onNewToken(String token) {
        Log.e(TAG, "onNewToken");
        MyPreferenceManager.getInatanse().setSendTokenToServer(false);
        sendRegistrationToServer(token);
    }

    private void handleDataNow(Map<String, String> remoteMessageData) {
        mImage = remoteMessageData.get("image");
        mActionUrl = remoteMessageData.get("action_url");
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
        int notificationID = NotificationID.getID();
        final Context context = this.getApplicationContext();

        final RemoteViews remoteViews = provideRemoteView();
        String channelId = getString(R.string.default_notification_channel_id);

        final Notification notification = getNotification(context, remoteViews, channelId);

        notifUser(notificationID, channelId, notification);

        if (mImage != null) {
            loadImageToRemoteViewOfNotification(context, remoteViews, notificationID, notification);
        }
    }

    private void notifUser(int notificationID, String channelId, Notification notification) {
        NotificationManager notificationManager = provideNotificationManager(channelId);
        notificationManager.notify(notificationID, notification);
    }

    private Notification getNotification(Context context, RemoteViews remoteViews, String channelId) {
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        PendingIntent pendingIntent = provideIntent();
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(context, channelId)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(mTitle)
                        .setContentText(mBody)
                        .setCustomBigContentView(remoteViews)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                        .addAction(R.drawable.ic_arrow_back,
                                mActionTXT, pendingIntent);

        return notificationBuilder.build();
    }

    private void loadImageToRemoteViewOfNotification(Context context, RemoteViews remoteViews, int notificationID, Notification notification) {
        final NotificationTarget notificationTarget = new NotificationTarget(
                context,
                R.id.image_view,
                remoteViews,
                notification,
                notificationID);


        Glide.with(context)
                .asBitmap()
                .load(mImage)
                .into(notificationTarget);
            /*GlideApp
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

                            return false;
                        }
                    })
                    .submit();*/
    }

    @NonNull
    private RemoteViews provideRemoteView() {
        final RemoteViews remoteViews = new RemoteViews(this.getPackageName(), R.layout.remote_notification);

        remoteViews.setTextViewText(R.id.txt_title, mTitle);
        remoteViews.setTextViewText(R.id.txt_body, mBody);
        return remoteViews;
    }

    private NotificationManager provideNotificationManager(String channelId) {
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "اخبار آلاء",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }
        return notificationManager;
    }

    private PendingIntent provideIntent() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);


        if (mActionUrl != null) {
            Log.d(TAG, mActionUrl);
            intent.setAction(Intent.ACTION_VIEW);
            Uri data = Uri.parse(mActionUrl);
            intent.setData(data);
        }

        return PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);
    }

}
