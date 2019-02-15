package ir.sanatisharif.android.konkur96.handler;

import android.annotation.SuppressLint;
import android.app.Activity;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ir.sanatisharif.android.konkur96.api.Models.ResultModel;
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
                .subscribe(mainModel -> callBack.onResponse(new Result.Success(mainModel)),
                        throwable -> callBack.onResponse(new Result.Error(throwable.getMessage())));
    }

    @SuppressLint("CheckResult")
    @Override
    public void getNextPage(String url, ApiCallBack callBack) {

        shopAPI.getPagination(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mainModel -> callBack.onResponse(new Result.Success(mainModel)),
                        throwable -> callBack.onResponse(new Result.Error(throwable.getMessage())));

    }

    @SuppressLint("CheckResult")
    @Override
    public void getNextPageProduct(String url, ApiCallBack callBack) {

        shopAPI.getPaginationProduct(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(ResultModel -> callBack.onResponse(new Result.Success(ResultModel)),
                        throwable -> callBack.onResponse(new Result.Error(throwable.getMessage())));

    }


    @SuppressLint("CheckResult")
    @Override
    public void getMore(String url, ApiCallBack callBack) {

        shopAPI.getMore(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(ResultModel -> callBack.onResponse(new Result.Success(ResultModel)),
                        throwable -> callBack.onResponse(new Result.Error(throwable.getMessage())));

    }
}
