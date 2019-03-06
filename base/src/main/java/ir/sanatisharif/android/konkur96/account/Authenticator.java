package ir.sanatisharif.android.konkur96.account;

/**
 * Created by Mohamad on 2/15/2019.
 */

public interface Authenticator {

    public void  addNewAccount();
    /**
     * Synchronously retrieves an auth token.
     *
     */
    public String getAuthToken() ;

    /**
     * Invalidates the provided auth token.
     */
    public void invalidateAuthToken(String authToken);
}