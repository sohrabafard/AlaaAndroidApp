package ir.sanatisharif.android.konkur96.utils;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;

import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.model.user.User;
import ir.sanatisharif.android.konkur96.ui.view.MDToast;

import static ir.sanatisharif.android.konkur96.activity.ActivityBase.toastShow;
import static ir.sanatisharif.android.konkur96.app.AppConstants.AUTHTOKEN_TYPE_FULL_ACCESS;

/**
 * Created by Mohamad on 2/15/2019.
 */

public class AccountInfo {
    
    public  String         TAG = "Alaa\\AccountInfo";
    private AccountManager mAccountManager;
    private Context        context;
    private Activity       activity;
    private String         token;
    
    public AccountInfo(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
        mAccountManager = AccountManager.get(context);
    }
    
    public AccountInfo(Context context) {
        this.context = context;
        mAccountManager = AccountManager.get(context);
    }
    
    public AccountInfo setActivity(Activity activity) {
        this.activity = activity;
        return this;
    }
    
    public AccountInfo setActivity(Context context) {
        this.context = context;
        return this;
    }
    
    public String getToken() {
        return token;
    }
    
    public void setToken(String token) {
        this.token = token;
    }
    
    /**
     * Add new account to the account manager
     *
     * @param accountType
     * @param authTokenType
     */
    public void addNewAccount(String accountType, String authTokenType) throws Exception {
        if (activity == null) {
            throw new Exception("Activity is Null!");
        }
        final AccountManagerFuture<Bundle> future =
                mAccountManager.addAccount(accountType, authTokenType, null, null,
                        activity, new AccountManagerCallback<Bundle>() {
                            
                            @Override
                            public void run(AccountManagerFuture<Bundle> future) {
                                try {
                                    Bundle bnd = future.getResult();
                                    Log.i(TAG, "addNewAccount  : " + bnd);
                                    toastShow(context.getResources().getString(R.string.register_success), MDToast.TYPE_SUCCESS);
                                    
                                }
                                catch (Exception e) {
                                    e.printStackTrace();
                                    //toastShow(context.getResources().getString(R.string.register_success), MDToast.TYPE_ERROR);
                                }
                            }
                        }, null);
    }
    
    public void invalidateAuthToken(final Account account, String authTokenType) throws Exception {
        if (activity == null) {
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
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    
    public User getInfo(String accountType) {
        
        Gson      gson     = new Gson();
        Account[] account  = mAccountManager.getAccountsByType(accountType);
        String    userData = mAccountManager.getUserData(account[0], AccountManager.KEY_USERDATA);
        
        if (userData != null)
            return gson.fromJson(userData, User.class);
        
        return null;
        
    }
    
    public boolean ExistAccount(String type) throws Exception {
        
        if (activity == null) {
            throw new Exception("Activity is Null!");
        }
        Account[] availableAccounts = mAccountManager.getAccountsByType(type);
        
        if (availableAccounts.length == 0) {
            addNewAccount(type, AUTHTOKEN_TYPE_FULL_ACCESS);
            return false;
        }
        return true;
    }
}
