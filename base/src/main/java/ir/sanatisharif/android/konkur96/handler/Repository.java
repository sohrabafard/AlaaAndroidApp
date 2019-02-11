package ir.sanatisharif.android.konkur96.handler;

public interface Repository {

    void getMainShop(ApiCallBack callBack);
    void getNextPage(String url, ApiCallBack callBack);
}
