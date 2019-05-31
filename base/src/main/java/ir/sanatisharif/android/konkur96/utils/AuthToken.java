package ir.sanatisharif.android.konkur96.utils;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;

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

    public void get(@NonNull Context context, @NonNull Activity activity, @NonNull Callback callback) {
        AccountInfo accountInfo = new AccountInfo(context, activity);
        accountInfo.getExistingAccountAuthToken(ACCOUNT_TYPE, AUTHTOKEN_TYPE_FULL_ACCESS, new AccountInfo.AuthToken() {
            @Override
            public void onToken(@NonNull String token) {
                callback.run(token);
            }

            @Override
            public void onNullToken() {
                callback.nill();
            }
        });
    }

    public void get(@NonNull Context context, @NonNull Callback callback) {

        AccountInfo accountInfo = new AccountInfo(context);
        accountInfo.getExistingAccountAuthToken(ACCOUNT_TYPE, AUTHTOKEN_TYPE_FULL_ACCESS, new AccountInfo.AuthToken() {
            @Override
            public void onToken(@NonNull String token) {
                callback.run(token);
            }

            @Override
            public void onNullToken() {
                callback.nill();
            }
        });

    }

    public interface Callback {
        /**
         * Callback for when an item has been selected.
         */
        void run(@NonNull String token);

        void nill();
    }
}
