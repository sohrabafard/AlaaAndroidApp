package ir.sanatisharif.android.konkur96.handler;

import android.annotation.SuppressLint;
import android.app.Activity;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ir.sanatisharif.android.konkur96.api.EncryptedDownloadApi;
import ir.sanatisharif.android.konkur96.app.AppConfig;

import static ir.sanatisharif.android.konkur96.handler.Result.Error;
import static ir.sanatisharif.android.konkur96.handler.Result.Success;

public class EncryptedDownloadRepository implements EncryptedDownloadInterface {

    @Inject
    EncryptedDownloadApi encryptedDownloadRepo;

    public EncryptedDownloadRepository(Activity activity) {
        ((AppConfig) activity.getApplication()).getAppComponent().inject(this);
    }

    @SuppressLint("CheckResult")
    @Override
    public void getDirectLink(String url, String token, ApiCallBack callBack) {
        encryptedDownloadRepo.getDownloadLink(url, ("Bearer " + token))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> callBack.onResponse(new Success(response)), throwable -> callBack.onResponse(new Error(throwable.getMessage())));

    }
}
