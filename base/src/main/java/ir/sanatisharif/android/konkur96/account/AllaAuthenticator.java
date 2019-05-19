package ir.sanatisharif.android.konkur96.account;

import android.accounts.AbstractAccountAuthenticator;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.accounts.NetworkErrorException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;

import java.util.function.Predicate;

import ir.sanatisharif.android.konkur96.api.MainApi;
import ir.sanatisharif.android.konkur96.app.AppConfig;
import ir.sanatisharif.android.konkur96.listener.api.IServerCallbackObject;
import ir.sanatisharif.android.konkur96.model.user.User;
import ir.sanatisharif.android.konkur96.model.user.UserInfo;
import ir.sanatisharif.android.konkur96.utils.MyPreferenceManager;

import static android.accounts.AccountManager.KEY_BOOLEAN_RESULT;
import static android.content.Context.ACCOUNT_SERVICE;
import static ir.sanatisharif.android.konkur96.app.AppConstants.ACCOUNT_TYPE;
import static ir.sanatisharif.android.konkur96.app.AppConstants.ARG_ACCOUNT_NAME;
import static ir.sanatisharif.android.konkur96.app.AppConstants.ARG_ACCOUNT_TYPE;
import static ir.sanatisharif.android.konkur96.app.AppConstants.ARG_AUTH_TYPE;

/**
 * Created by Mohamad on 2/9/2019.
 */

public class AllaAuthenticator extends AbstractAccountAuthenticator {

    private String TAG = "udinic";
    private Context mContext;
    private String authToken;

    public AllaAuthenticator(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public Bundle editProperties(AccountAuthenticatorResponse accountAuthenticatorResponse, String s) {
        return null;
    }

    @Override
    public Bundle addAccount(AccountAuthenticatorResponse response, String accountType, String authTokenType, String[] requiredFeatures, Bundle options) throws NetworkErrorException {

        final Intent intent = new Intent(mContext, AuthenticatorActivity.class);
        intent.putExtra(ARG_ACCOUNT_TYPE, accountType);
        intent.putExtra(ARG_AUTH_TYPE, authTokenType);
        intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);
        final Bundle b = new Bundle();
        b.putParcelable(AccountManager.KEY_INTENT, intent);
        return b;
    }

    @Override
    public Bundle confirmCredentials(AccountAuthenticatorResponse accountAuthenticatorResponse, Account account, Bundle bundle) throws NetworkErrorException {
        return null;
    }

    @Override
    public Bundle getAuthToken(AccountAuthenticatorResponse response, Account account, String authTokenType, Bundle options) throws NetworkErrorException {

        // Extract the username and password from the Account Manager, and ask
        // the server for an appropriate AuthToken.
        final AccountManager am = AccountManager.get(mContext);

        authToken = am.peekAuthToken(account, authTokenType);

        // Log.d("udinic", TAG + "> peekAuthToken returned - " + authToken);

        // Lets give another try to authenticate the user
        if (TextUtils.isEmpty(authToken)) {
            final String password = am.getPassword(account);
            if (password != null) {
                try {

                    authToken = MyPreferenceManager.getInatanse().getApiToken();
                    AppConfig.HANDLER.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            MyPreferenceManager.getInatanse().setApiToken("");
                        }
                    }, 200);

                    // authToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6Ijk2ZjNiODg2YjVlZjJmZTBkNmU1ODQ3YjdiOGQ1MWU3MmM5MjdkOTE0ZDk5YTQ3NDg5NjhmMmMxMDk4NTIxN2RlOGJmNDhmZDk0YWYyZTY4In0.eyJhdWQiOiIxIiwianRpIjoiOTZmM2I4ODZiNWVmMmZlMGQ2ZTU4NDdiN2I4ZDUxZTcyYzkyN2Q5MTRkOTlhNDc0ODk2OGYyYzEwOTg1MjE3ZGU4YmY0OGZkOTRhZjJlNjgiLCJpYXQiOjE1NTI5MDA5MzYsIm5iZiI6MTU1MjkwMDkzNiwiZXhwIjoxNTg0NTIzMzM2LCJzdWIiOiIxNzg5ODIiLCJzY29wZXMiOltdfQ.fkyKNv2CyfQpk114qpbf_s2dQyXZmcC-FOQw1auYD6t1ubtqWis6oH2XSNxJjj1UFHyk3bMasB32T-Q8VGYi6qjvecK69ZTe-zw-IMfw-n6yMeM7eVGKUIJpa9ZlM245K4YfwNztGs0h72HdYMH1D4zpCl998VQQuRWILE1O59SfRTvfWYgWudC2BlFBndAhkRtPvgnp0Awews1SWr4SnpvYhE76wMO9p6ztaMHRK3KHhZagyArwsOXd2fRLxYlGcdzhAjvdeQ1W-aQuXt200-5X2bHt9b38_jozvYz_y4TNZd7o1f1uamuT1bvFA70qX2UFjuDItY8aDd2Noui3U7lnuV9oDniH1kurNP4IkzzoydjMjjITd7zFyxOyo07X2xgwttEtx0mcVD8RYicn06HNkw5lDjGwwVusBQraE9Vfkm5iu2Tb0JNJVxYW4mViXatOgb_EXjKsGq_5uBG2igYBi1HTdt0nyUG-sxzGrhClVja3MV-_yymmx5-Q41ox7oMJeelxhUhH5q8hCtj_qSIiLBVzxyAQ39SMBMoXy4kNeJVLnM0TmLrAUFo5chNWhkNqhSBLiByRX794FXfCPQXg0HSqNnKhDcviI5gi9ik-kQIWEbOK7jUsktE3FfB8rDIFpN4gGKSX_ULdwQKKq3zn-5NWfyeWn-67U6fQ_ac";
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        // If we get an authToken - we return it
        if (!TextUtils.isEmpty(authToken)) {
            final Bundle result = new Bundle();
            result.putString(AccountManager.KEY_ACCOUNT_NAME, account.name);
            result.putString(AccountManager.KEY_ACCOUNT_TYPE, account.type);
            result.putString(AccountManager.KEY_AUTHTOKEN, authToken);
            // Log.i(TAG, "getAuthToken: " + authToken + " " + authTokenType);
            return result;
        }

        // If we get here, then we couldn't access the user's password - so we
        // need to re-prompt them for their credentials. We do that by creating
        // an intent to display our AuthenticatorActivity.
        final Intent intent = new Intent(mContext, AuthenticatorActivity.class);
        intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);
        intent.putExtra(ARG_ACCOUNT_TYPE, account.type);
        intent.putExtra(ARG_AUTH_TYPE, authTokenType);
        intent.putExtra(ARG_ACCOUNT_NAME, account.name);
        final Bundle bundle = new Bundle();
        bundle.putParcelable(AccountManager.KEY_INTENT, intent);


        return bundle;
    }

    @Override
    public String getAuthTokenLabel(String s) {
        return "Full";
    }

    @Override
    public Bundle updateCredentials(AccountAuthenticatorResponse accountAuthenticatorResponse, Account account, String s, Bundle bundle) throws NetworkErrorException {
        return null;
    }

    @Override
    public Bundle hasFeatures(AccountAuthenticatorResponse accountAuthenticatorResponse, Account account, String[] strings) throws NetworkErrorException {
        final Bundle result = new Bundle();
        result.putBoolean(KEY_BOOLEAN_RESULT, false);
        return result;
    }
}
