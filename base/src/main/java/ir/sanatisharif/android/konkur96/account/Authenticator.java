package ir.sanatisharif.android.konkur96.account;

/**
 * Created by Mohamad on 2/15/2019.
 */

public interface Authenticator {
    
    void addNewAccount();
    
    /**
     * Synchronously retrieves an auth token.
     */
    String getAuthToken();
    
    /**
     * Invalidates the provided auth token.
     */
    void invalidateAuthToken(String authToken);
}