package ir.sanatisharif.android.konkur96.utils;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.util.Log;

import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.activity.ActivityBase;
import ir.sanatisharif.android.konkur96.app.AppConfig;
import ir.sanatisharif.android.konkur96.listener.DownloadComplete;
import ir.sanatisharif.android.konkur96.ui.view.MDToast;

import static android.content.Context.DOWNLOAD_SERVICE;

/**
 * Created by Mohamad on 10/7/2017.
 */

public class DownloadFile {

    private static DownloadFile dm;
    private DownloadManager mDManager;
    private DownloadCompleteReceiver mReceiver;
    private DownloadManager.Request req = null;
    private long id = 0;
    private DownloadComplete d;

    public static DownloadFile getInstance() {

        if (dm == null) {
            dm = new DownloadFile();
        }
        return dm;
    }


    public void init(DownloadComplete d) {
        mReceiver = new DownloadCompleteReceiver();
        IntentFilter filter = new IntentFilter("android.intent.action.DOWNLOAD_COMPLETE");
        AppConfig.context.registerReceiver(mReceiver, filter);
        mDManager = (DownloadManager) AppConfig.context.getSystemService(DOWNLOAD_SERVICE);

        this.d = d;
    }

    public void start(String url, String path, String fileName, String title, String desc, String apiToken) {
        Uri uri = Uri.parse(url);
        req = new DownloadManager.Request(uri);
        req.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        // req.setAllowedOverRoaming(false);

        req.setTitle(title);
        req.setDescription(desc);

        if (apiToken != null) {
            req.addRequestHeader("Authorization", "Bearer " + apiToken);
            Log.e("url", url);
        }

        req.setDestinationInExternalPublicDir(path, fileName);
        id = mDManager.enqueue(req);
    }

    /**
     * @param url
     * @param path
     * @param fileName
     * @param title
     * @param desc
     */
    public void start(String url, String path, String fileName, String title, String desc) {

        start(url, path, fileName, title, desc, null);
    }

    public void stop() {
        mDManager.remove(id);
        id = 0;
    }

    private class DownloadCompleteReceiver extends BroadcastReceiver {

        /**
         * Will be executed when the download is completed
         */
        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.getAction().equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
                ActivityBase.toastShow(AppConfig.context.getString(R.string.completeDownload), MDToast.TYPE_SUCCESS);
                if (d != null) {
                    d.complete();
                }
            }
        }
    }
}
