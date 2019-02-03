package ir.sanatisharif.android.konkur96.handler;

import android.annotation.SuppressLint;
import android.app.Activity;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ir.sanatisharif.android.konkur96.api.ShopAPI;
import ir.sanatisharif.android.konkur96.app.AppConfig;

public class RepositoryImpl implements Repository {

    @Inject
    ShopAPI shopAPI;

    public RepositoryImpl(Activity activity) {

        ((AppConfig) activity.getApplication()).getAppComponent().inject(this);
    }

    @SuppressLint("CheckResult")
    @Override
    public void getMainShop(ApiCallBack callBack) {

        shopAPI.getMain()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mainModel -> {
                    callBack.onResponse(new Result.Success(mainModel));
                }, throwable -> {
                    callBack.onResponse(new Result.Error(throwable.getMessage()));
                });
    }
}
