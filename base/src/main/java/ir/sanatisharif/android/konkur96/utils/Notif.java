package ir.sanatisharif.android.konkur96.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.concurrent.ExecutionException;

import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.activity.MainActivity;
import ir.sanatisharif.android.konkur96.ui.GlideApp;

public class Notif {
    private static final String TAG = "Alaa\\Notif";
    private String GROUP_KEY_WORK_PUSH = "ir.sanatisharif.android.konkur96.NOTIF_PUSH";
    private int mId;
    private RemoteViews mRemoteViews;
    private Context mContext;
    private String mTitle = null;
    private String mBody = null;
    private String mActionUrl = null;
    private String mImage = null;
    private String mActionTXT = null;
    private String mChannelId;
    private Notification mNotification = null;

    private Notif(Context context) {
        mContext = context;
        mChannelId = context.getString(R.string.default_notification_channel_id);
        mId = NotificationID.getID();
        mRemoteViews =  new RemoteViews(mContext.getPackageName(), R.layout.remote_notification);
    }

    public static Notif get(@NonNull  Context context,@NonNull String title,@NonNull String body)
    {
        return new Notif(context)
                .setTitle(title)
                .setBody(body)
                .initRemoteView();
    }
    public static Notif get(@NonNull  Context context)
    {
        return new Notif(context);
    }

    public Notif setTitle(String title) {
        this.mTitle = title;
        return this;
    }

    public Notif setBody(String body) {
        this.mBody = body;
        return this;
    }

    public Notif setActionUrl(String actionUrl) {
        this.mActionUrl = actionUrl;
        return this;
    }

    public Notif setActionTXT(String actionTXT) {
        this.mActionTXT = actionTXT;
        return this;
    }

    public Notif setImage(String image) {
        this.mImage = image;
        return this;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getBody() {
        return mBody;
    }

    public String getActionUrl() {
        return mActionUrl;
    }

    public String getImage() {
        return mImage;
    }

    public String getActionTXT() {
        return mActionTXT;
    }

    public void send() {
        if(mTitle != null && mBody != null) {
            Log.d(TAG, "sending Notif " + mId + "....");
            notifUser(false);
        }
    }
    public void update() {
        if(mTitle != null && mBody != null) {
            Log.d(TAG, "sending Notif " + mId + "....");
            notifUser(true);
        }
    }

    private void notifUser(boolean isUpdating ) {
        if(mNotification == null) {
            mNotification = initNotification();
        }
        if(!isUpdating) {
            initRemoteView();
        }
        getNotificationManager().notify(mId, mNotification);
        if (mImage != null && !isUpdating) {
            loadImageToRemoteViewOfNotification();
        }
    }

    private Notification initNotification() {
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        PendingIntent pendingIntent = provideIntent();
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(mContext, mChannelId)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(mTitle)
                        .setContentText(mBody)
                        .setCustomBigContentView(mRemoteViews)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                        .addAction(R.drawable.ic_arrow_back,
                                mActionTXT, pendingIntent);

        return notificationBuilder.setGroup(GROUP_KEY_WORK_PUSH).build();
    }

    private void loadImageToRemoteViewOfNotification() {
        if(mImage != null){
            try {
                GlideApp
                        .with(mContext)
                        .asBitmap()
                        .load(mImage)
                        .centerCrop()
                        .listener(new RequestListener<Bitmap>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                                Log.d(TAG,"onLoadFailed");
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                                Log.d(TAG,"onResourceReady");
                                updateRemoteView(resource).update();
                                return false;
                            }
                        })
                        .submit().get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }



    @NonNull
    private Notif initRemoteView() {
        mRemoteViews.setTextViewText(R.id.txt_title, mTitle);
        mRemoteViews.setTextViewText(R.id.txt_body, mBody);
        return this;
    }

    @NonNull
    private Notif updateRemoteView(@NonNull  Bitmap bitmap) {
        mRemoteViews.setImageViewBitmap(R.id.img,bitmap);
        return this;
    }

    private NotificationManager getNotificationManager() {
        NotificationManager notificationManager =
                (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(mChannelId,
                    "اخبار آلاء",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }
        return notificationManager;
    }

    private PendingIntent provideIntent() {
        Intent intent = new Intent(mContext, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);


        if (mActionUrl != null) {
            Log.d(TAG, mActionUrl);
            intent.setAction(Intent.ACTION_VIEW);
            Uri data = Uri.parse(mActionUrl);
            intent.setData(data);
        }

        return PendingIntent.getActivity(mContext, 0 , intent,
                PendingIntent.FLAG_ONE_SHOT);
    }
}
