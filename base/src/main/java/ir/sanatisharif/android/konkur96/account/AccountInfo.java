package ir.sanatisharif.android.konkur96.account;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.google.android.gms.common.wrappers.InstantApps;
import com.google.gson.Gson;

import java.io.IOException;

import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.app.AppConfig;
import ir.sanatisharif.android.konkur96.model.user.User;
import ir.sanatisharif.android.konkur96.ui.view.MDToast;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static ir.sanatisharif.android.konkur96.activity.ActivityBase.toastShow;
import static ir.sanatisharif.android.konkur96.app.AppConstants.AUTHTOKEN_TYPE_FULL_ACCESS;

/**
 * Created by Mohamad on 2/15/2019.
 */

public class AccountInfo {

    public String TAG = "Alla";
    private AccountManager mAccountManager;
    private Context mContext;
    private Activity activity;

    public AccountInfo setActivity(Activity activity){
        this.activity = activity;
        return this;
    }
    public AccountInfo setmContext(Context context){
        this.mContext = context.getApplicationContext();
        return this;
    }


    public AccountInfo(Context context) {
        this.mContext = context.getApplicationContext();
        mAccountManager = AccountManager.get(this.mContext);
    }

    public AccountInfo(Context context, Activity activity) {
        this.mContext = context.getApplicationContext();
        this.activity = activity;
        mAccountManager = AccountManager.get(this.mContext);
    }


    /**
     * Add new account to the account manager
     *
     * @param accountType
     * @param authTokenType
     */
    public void addNewAccount(String accountType, String authTokenType) throws Exception {
        if(activity == null){
            throw new Exception("Activity is Null!");
        }
        final AccountManagerFuture<Bundle> future =
                mAccountManager.addAccount(accountType, authTokenType, null, null,
                        activity, new AccountManagerCallback<Bundle>() {
                            @Override
                            public void run(AccountManagerFuture<Bundle> future) {
                                try {
                                    Bundle bnd = future.getResult();
                                    //  Log.i(TAG, "addNewAccount  : " + bnd);
                                    toastShow(mContext.getResources().getString(R.string.register_success), MDToast.TYPE_SUCCESS);

                                } catch (Exception e) {
                                    e.printStackTrace();
                                    // toastShow(mContext.getResources().getString(R.string.register_success), MDToast.TYPE_ERROR);
                                }
                            }
                        }, null);
    }

    /**
     * Get the auth token for an existing account on the AccountManager
     *
     * @param authTokenType
     */
    public void getExistingAccountAuthToken(String accountType, String authTokenType,@NonNull final AuthToken listener) {
        Account[] account = mAccountManager.getAccountsByType(accountType);
        if ( account.length == 0){
            listener.onNullToken();
            return;
        }
        final AccountManagerFuture<Bundle> future;
        if(activity == null ){
            future = mAccountManager.getAuthToken(account[0], authTokenType, null, true, null, null);
        } else {
            future = mAccountManager.getAuthToken(account[0], authTokenType, null, activity, null, null);
        }

        Thread t = new Thread(() -> {
            try {
                Bundle bnd = future.getResult();
                Log.i(TAG, "onCreate: " + bnd);
                final String authtoken = bnd.getString(AccountManager.KEY_AUTHTOKEN);
                if (authtoken == null){
                    listener.onNullToken();
                }
                else{
                    listener.onToken(authtoken);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        t.start();
    }

    public void invalidateAuthToken(final Account account, String authTokenType) throws Exception {
        if(activity == null ){
            throw new Exception("Activity is Null!");
        }
        final AccountManagerFuture<Bundle> future =
                mAccountManager.getAuthToken(account, authTokenType, null, activity, null, null);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Bundle bnd = future.getResult();

                    final String authtoken = bnd.getString(AccountManager.KEY_AUTHTOKEN);
                    mAccountManager.invalidateAuthToken(account.type, authtoken);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public User getInfo(String accountType) {

        Gson gson = new Gson();
        Account[] account = mAccountManager.getAccountsByType(accountType);
        if (account.length == 0)
            return null;
        String userData = mAccountManager.getUserData(account[0], AccountManager.KEY_USERDATA);

        if (userData != null)
            return gson.fromJson(userData, User.class);

        return null;

    }

    public void removeAccount(String accountType, RemoveAccount removeAccount) {

        Account[] account = mAccountManager.getAccountsByType(accountType);
        if (account == null || account.length == 0)
            return;
        mAccountManager.removeAccount(account[0], new AccountManagerCallback<Boolean>() {
            @Override
            public void run(AccountManagerFuture<Boolean> future) {

                try {
                    if (future.getResult()) {
                        if (removeAccount != null) {
                            //   Log.i("LOG", "future ");
                            removeAccount.onRemove(future.isDone());
                        }
                    }
                } catch (OperationCanceledException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (AuthenticatorException e) {
                    e.printStackTrace();
                }
            }
        }, null);
    }

    public boolean ExistAccount(String type)  {
        if (!InstantApps.isInstantApp(mContext)) {
            Account[] availableAccounts = mAccountManager.getAccountsByType(type);
            Log.i(TAG, "ExistAccount: " + availableAccounts.length);
            if (availableAccounts.length == 0) {
                try {
                    addNewAccount(type, AUTHTOKEN_TYPE_FULL_ACCESS);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return false;
            }
            return true;
        }
        new AlertDialog.Builder(AppConfig.currentActivity)
                .setView(R.layout.alert_dialog)
                .setPositiveButton("بله", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent goToMarket = new Intent(Intent.ACTION_VIEW)
                                .setData(Uri.parse("https://play.google.com/store/apps/details?id=ir.sanatisharif.android.konkur96"));
                        goToMarket.setFlags(FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(goToMarket);
                    }
                })
                .setNegativeButton("خیر", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .create().show();

        return false;
    }

    public interface AuthToken {
        void onToken(@NonNull  String token);

        void onNullToken();
    }

    public interface RemoveAccount {
        void onRemove(boolean done);
    }
}
