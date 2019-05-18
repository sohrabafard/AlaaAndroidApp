package ir.sanatisharif.android.konkur96.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.util.Log;

import ir.sanatisharif.android.konkur96.activity.ActivityBase;
import ir.sanatisharif.android.konkur96.listener.ICheckNetwork;
import ir.sanatisharif.android.konkur96.ui.view.MDToast;

import static ir.sanatisharif.android.konkur96.activity.ActivityBase.toastShow;


/**
 * Created by Mohamad on 8/27/2017.
 */

public class NetworkChangedReceiver extends BroadcastReceiver {

    private static final String TAG = "NetworkChangeReceiver";
    public static ICheckNetwork iCheckNetwork;

    private static boolean isNetworkAvailable(Context context) {

        boolean isConnect = false;
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {

            NetworkInfo networkInfo = connectivity.getActiveNetworkInfo();
            isConnect = networkInfo != null && networkInfo.isConnectedOrConnecting();
        }

        return isConnect;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        if (iCheckNetwork != null)
            iCheckNetwork.onCheckNetwork(isNetworkAvailable(context));
    }
}
