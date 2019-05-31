package ir.sanatisharif.android.konkur96.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import androidx.annotation.Nullable;

import ir.sanatisharif.android.konkur96.account.AllaAuthenticator;

/**
 * Created by Mohamad on 2/9/2019.
 */

public class AuthenticatorService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        AllaAuthenticator authenticator = new AllaAuthenticator(this);
        return authenticator.getIBinder();
    }
}
