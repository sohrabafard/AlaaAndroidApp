package ir.sanatisharif.android.konkur96.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;


import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.app.AppConfig;
import ir.sanatisharif.android.konkur96.service.DLService;

/**
 * Created by Mohamad on 10/29/2018.
 */

public class DownloadNotification {

    public static final int PAUSE = 0;
    public static final int RESUME = 1;
    public static final int START = 2;
    private static DownloadNotification downloadNotification;
    private Context mContext;
    private Notification mNotification;
    private NotificationManager mNotificationManager;
    private int mId;
    public static int actionStatus = 0;

    public static synchronized DownloadNotification getInstance() {

        if (downloadNotification == null)
            downloadNotification = new DownloadNotification();
        return downloadNotification;
    }

    public void progress(int mProgress) {
        mNotification.contentView.setTextViewText(R.id.txtProgress, mProgress + "%");
        mNotification.contentView.setProgressBar(R.id.notify_processbar, 100, mProgress, false);
        mNotificationManager.notify(100, mNotification);
    }

    public void changeToResume(Context c) {

        Intent intent = new Intent(c, DLService.class);
        intent.setAction("resume");
        mNotification.contentView.setOnClickPendingIntent(R.id.imgResume, PendingIntent.getService(c, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT));
        mNotification.contentView.setImageViewResource(R.id.imgResume, R.drawable.ic_play_arrow_black_24dp);
        actionStatus = RESUME;
        mNotificationManager.notify(100, mNotification);
        Log.i("LOG", "onHandleIntent: pause1 mNotificationManager");
    }

    public void changeToPause(Context c) {

        Intent leftIntent = new Intent(c, DLService.class);
        leftIntent.setAction("pause");
        Log.i("LOG", "onHandleIntent: changeToPause");
        mNotification.contentView.setOnClickPendingIntent(R.id.imgResume, PendingIntent.getService(c, 0, leftIntent, PendingIntent.FLAG_UPDATE_CURRENT));
        mNotification.contentView.setImageViewResource(R.id.imgResume, R.drawable.ic_pause_black_24dp);
        actionStatus = PAUSE;
        mNotificationManager.notify(100, mNotification);

    }

    public void showNotification(Context c) {

        mNotificationManager = (NotificationManager) c.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new Notification(R.mipmap.ic_launcher, "Tittle", System.currentTimeMillis());
        RemoteViews contentView = new RemoteViews(AppConfig.context.getPackageName(), R.layout.download_notify);
        contentView.setProgressBar(R.id.notify_processbar, 100, 0, false);
        contentView.setTextViewText(R.id.txtProgress, "");
        contentView.setTextViewText(R.id.notifyTitle, "دانلود");
        contentView.setImageViewResource(R.id.imgResume, R.drawable.ic_pause_black_24dp);

        Intent intent = new Intent(c, DLService.class);
        intent.setAction("pause");
        contentView.setOnClickPendingIntent(R.id.imgResume, PendingIntent.getService(c, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT));
        actionStatus = START;

        notification.contentView = contentView;


      /*  Notification notification1 = new NotificationCompat.Builder(c)
                .setTicker("")
                .setSmallIcon(android.R.drawable.ic_menu_view)
                .setContentTitle("title")
                .setContentText("con")
                .setContent(contentView)
                .setAutoCancel(true)
                .setContentIntent(PendingIntent.getActivity(c, 0, new Intent(c, Main2Activity.class), 0))
                .build();*/

        mNotification = notification;

        mNotificationManager = (NotificationManager) c.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(100, notification);
    }

    public void cancel() {

        mNotificationManager.notify(100, mNotification);
        mNotificationManager.cancel(100);
    }

}
