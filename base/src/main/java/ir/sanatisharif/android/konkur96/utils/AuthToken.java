package ir.sanatisharif.android.konkur96.utils;

import android.app.Activity;
import android.content.Context;

import org.jetbrains.annotations.NotNull;

import ir.sanatisharif.android.konkur96.account.AccountInfo;

import static ir.sanatisharif.android.konkur96.app.AppConstants.ACCOUNT_TYPE;
import static ir.sanatisharif.android.konkur96.app.AppConstants.AUTHTOKEN_TYPE_FULL_ACCESS;

public class AuthToken {
    private static AuthToken getToken = null;

    private AuthToken() {

    }

    public static AuthToken getInstant() {

        if (getToken != null)
            return getToken;
        getToken = new AuthToken();
        return getToken;
    }

    public void get(@NotNull Context context, @NotNull Activity activity, @NotNull Callback callback) {
        AccountInfo accountInfo = new AccountInfo(context, activity);
        accountInfo.getExistingAccountAuthToken(ACCOUNT_TYPE, AUTHTOKEN_TYPE_FULL_ACCESS, callback::run);
    }

    public void get(@NotNull Context context, @NotNull Callback callback){

        AccountInfo accountInfo = new AccountInfo(context);

    }

    public interface Callback {
        /**
         * Callback for when an item has been selected.
         */
        void run(String token);
    }
}
