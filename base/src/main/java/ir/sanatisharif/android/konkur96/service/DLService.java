package ir.sanatisharif.android.konkur96.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;


import ir.sanatisharif.android.konkur96.app.AppConfig;
import ir.sanatisharif.android.konkur96.utils.DownloadNotification;
import ir.sanatisharif.android.konkur96.utils.Utils;

/**
 * Created by Mohamad on 10/29/2018.
 */

public class DLService {//extends IntentService {

/*    private static int mId;
    //private static DownloadNotification notif;

    public DLService() {
        super("");
    }


    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        Log.i("LOG", "onHandleIntent1111: " + intent.getAction());
        switch (intent.getAction()) {
            case "start":

                DownloadNotification.getInstance().showNotification(getBaseContext());
                Log.i("LOG", "onHandleIntent: start " + Utils.getRootDirPath(getApplicationContext()));
                startDL(Utils.getRootDirPath(getApplicationContext()), "", "");
                //PRDownloader.resume(-1785662517);

                break;
            case "resume":
                mId = -1785662517;
                resumeDL();

                break;

            case "pause":
                mId = -1785662517;
                pauseDL();
                break;
        }
    }


    private void resumeDL() {

        Log.i("LOG", "onHandleIntent11:resumeDL " + PRDownloader.getStatus(mId) + " " + mId);
        if (Status.PAUSED == PRDownloader.getStatus(mId)) {

            PRDownloader.resume(mId);
            DownloadNotification.getInstance().changeToPause(getApplicationContext());
            return;
        }
    }

    private void pauseDL() {

        Log.i("LOG", "onHandleIntent11: pauseDL " + PRDownloader.getStatus(mId) + " " + mId);
        if (Status.RUNNING == PRDownloader.getStatus(mId)) {
            PRDownloader.pause(mId);
            Log.i("LOG", "onHandleIntent11: pauseDL");
            DownloadNotification.getInstance().changeToResume(getApplicationContext());
        }
    }

    public void startDL(String dirPath, String url, String fileName) {

        mId = PRDownloader.download("https://sanatisharif.ir/image/9/1280/500/BIG-SLIDE-7_20181015143731.jpg",
                dirPath, "2.jpg").build()
                .setOnStartOrResumeListener(new OnStartOrResumeListener() {
                    @Override
                    public void onStartOrResume() {

                        Log.i("LOG", "onHandleIntent: onStartOrResume " + mId);

                    }
                }).setOnPauseListener(new OnPauseListener() {
                    @Override
                    public void onPause() {


                    }
                }).setOnProgressListener(new OnProgressListener() {
                    @Override
                    public void onProgress(Progress progress) {

                        int progressPercent = (int) (progress.currentBytes * 100 / progress.totalBytes);
                        Log.i("LOG", "onHandleIntent: onStartOrResume" + progressPercent);
                        DownloadNotification.getInstance().progress(progressPercent);

                    }
                }).start(new OnDownloadListener() {
                    @Override
                    public void onDownloadComplete() {

                        stopDL();
                    }

                    @Override
                    public void onError(Error error) {

                    }
                });

    }

    public void stopDL() {

        stopSelf();
    }*/
}
