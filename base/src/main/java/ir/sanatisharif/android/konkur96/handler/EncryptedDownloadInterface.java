package ir.sanatisharif.android.konkur96.handler;

public interface EncryptedDownloadInterface {

    void getDirectLink(String url, String token, Callback callBack);

    interface Callback {
        void fetch(String header);

        void error(String message);
    }
}
