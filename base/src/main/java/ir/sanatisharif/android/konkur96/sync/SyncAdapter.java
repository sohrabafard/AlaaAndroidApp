package ir.sanatisharif.android.konkur96.sync;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;

import java.io.IOException;

/**
 * Created by Mohamad on 10/22/2018.
 */

public class SyncAdapter extends AbstractThreadedSyncAdapter {

    private AccountManager mAccountManager;


    public SyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);

        mAccountManager = AccountManager.get(context);
    }

    public SyncAdapter(Context context, boolean autoInitialize, boolean allowParallelSyncs) {
        super(context, autoInitialize, allowParallelSyncs);


        mAccountManager = AccountManager.get(context);
    }

    @Override
    public void onPerformSync(Account account, Bundle bundle, String s, ContentProviderClient contentProviderClient, SyncResult syncResult) {

        try {
            String authToken = mAccountManager.blockingGetAuthToken(account, "sd", true);
        }
        catch (OperationCanceledException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (AuthenticatorException e) {
            e.printStackTrace();
        }

    }
}
