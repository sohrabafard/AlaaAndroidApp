package ir.sanatisharif.android.konkur96.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

/**
 * Created by Mohamad on 10/22/2018.
 */

public class SyncService extends Service {
    
    
    // Object to use as a thread-safe lock
    private static final Object      sSyncAdapterLock = new Object();
    // Storage for an instance of the sync adapter
    private static       SyncAdapter sSyncAdapter     = null;
    
    /*
     * Instantiate the sync adapter object.
     */
    @Override
    public void onCreate() {
        /*
         * Create the sync adapter as a singleton.
         * SetModel the sync adapter as syncable
         * Disallow parallel syncs
         */
        synchronized (sSyncAdapterLock) {
            if (sSyncAdapter == null) {
                sSyncAdapter = new SyncAdapter(getApplicationContext(), true);
            }
        }
    }
    
    /**
     * Return an object that allows the system to invoke
     * the sync adapter.
     */
    
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return sSyncAdapter.getSyncAdapterBinder();
    }
}
