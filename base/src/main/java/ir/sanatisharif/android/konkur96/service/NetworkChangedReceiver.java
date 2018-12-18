package ir.sanatisharif.android.konkur96.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import ir.sanatisharif.android.konkur96.activity.ActivityBase;
import ir.sanatisharif.android.konkur96.listener.ICheckNetwork;

import static ir.sanatisharif.android.konkur96.activity.ActivityBase.toastShow;


/**
 * Created by Mohamad on 8/27/2017.
 */

public class NetworkChangedReceiver extends BroadcastReceiver {

    private static final String LOG_TAG = "NetworkChangeReceiver";
    private boolean isConnected = false;
    private ICheckNetwork iCheckNetwork;


    @Override
    public void onReceive(Context context, Intent intent) {
      /*  if (isNetworkAvailable(context))
            iCheckNetwork.onCheckNetwork(true);
        else
            iCheckNetwork.onCheckNetwork(false);*/
        if (!isNetworkAvailable(context)) {
            toastShow("not", 1);
        }
    }

    private boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        if (!isConnected) {
                            Log.v(LOG_TAG, "Now you are connected to Internet!");
                            isConnected = true;
                        }
                        return true;
                    }
                }
            }
        }
        Log.v(LOG_TAG, "You are not connected to Internet!");
      //  Toast.makeText(context, "Internet NOT availablle via Broadcast receiver", Toast.LENGTH_SHORT).show();

        return false;
    }
}
