package ir.sanatisharif.android.konkur96.handler;

public interface EncryptedDownloadInterface extends ApiRepoInterface {

    void getDirectLink(String url, String token, ApiCallBack callBack);
}
