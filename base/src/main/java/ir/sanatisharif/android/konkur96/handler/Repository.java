package ir.sanatisharif.android.konkur96.handler;

import java.util.ArrayList;

import ir.sanatisharif.android.konkur96.model.ProductType;

public interface Repository {

    void getMainShop(ApiCallBack callBack);
    void getNextPage(String url, ApiCallBack callBack);
    void getNextPageProduct(String url, ApiCallBack callBack);
    void getMore(String url, ApiCallBack callBack);
    void getPrice(ProductType type, String productId, ArrayList<Integer> products, ArrayList<Integer> mainAttributeValues, ArrayList<Integer> extraAttributeValues, ApiCallBack callBack);
}
