package ir.sanatisharif.android.konkur96.handler;

import android.app.Activity;

import ir.sanatisharif.android.konkur96.app.AppConfig;

public class RepositoryImpl implements Repository {

    public RepositoryImpl(Activity activity) {

        ((AppConfig) activity.getApplication()).getAppComponent().inject(this);
    }

    @Override
    public void getMainShop(ApiCallBack callBack) {

        String temp = "test";
        String temp1 = "test1";

         callBack.onResponse(new Result.Error(temp));
    }
}
