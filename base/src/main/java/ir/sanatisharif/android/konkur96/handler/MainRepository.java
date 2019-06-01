package ir.sanatisharif.android.konkur96.handler;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;

import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import ir.sanatisharif.android.konkur96.api.ApiModule;
import ir.sanatisharif.android.konkur96.api.MainApi;
import ir.sanatisharif.android.konkur96.Models.ContentModel;
import ir.sanatisharif.android.konkur96.Models.MainModel;
import ir.sanatisharif.android.konkur96.Models.filter.FilterModel;
import ir.sanatisharif.android.konkur96.app.AppConfig;
import ir.sanatisharif.android.konkur96.app.DaggerAppComponent;
import ir.sanatisharif.android.konkur96.listener.api.IServerCallbackContentCredit;
import ir.sanatisharif.android.konkur96.listener.api.IServerCallbackObject;
import ir.sanatisharif.android.konkur96.model.ContentCredit;
import ir.sanatisharif.android.konkur96.model.main_page.lastVersion.LastVersion;
import ir.sanatisharif.android.konkur96.model.user.User;
import ir.sanatisharif.android.konkur96.model.user.UserInfo;

public class MainRepository implements MainRepositoryInterface {
    
    @Inject
    MainApi mainApi;
    private String TAG = "Alaa\\MainRepository";
    
    public MainRepository(Activity activity) {
        ((AppConfig) activity.getApplication()).getAppComponent().inject(this);
    }
    
    public MainRepository() {
        DaggerAppComponent.builder().apiModule(new ApiModule()).build().inject(this);
    }
    
    @SuppressLint("CheckResult")
    @Override
    public void mainPages(IServerCallbackObject iServerCallbackObject) {
        mainApi.getMainPage()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<MainModel>() {
                    @Override
                    public void accept(MainModel mainPagesInfo) throws Exception {
                        iServerCallbackObject.onSuccess(mainPagesInfo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        iServerCallbackObject.onFailure(throwable.getMessage());
                    }
                });
    }
    
    @SuppressLint("CheckResult")
    @Override
    public void userInfo(User user, IServerCallbackObject iServerCallbackObject) {
        mainApi.getLoginUserInfo(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<UserInfo>() {
                    @Override
                    public void accept(UserInfo response) throws Exception {
                        iServerCallbackObject.onSuccess(response);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e(TAG, throwable.getMessage());
                        iServerCallbackObject.onFailure(throwable.getMessage());
                    }
                });
    }
    
    @SuppressLint("CheckResult")
    @Override
    public void getContent(String url, String token, IServerCallbackContentCredit iServerCallbackObject) {
        mainApi.getContent(url, ("Bearer " + token))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ContentModel>() {
                    @Override
                    public void accept(ContentModel response) throws Exception {
                        iServerCallbackObject.onSuccess(response);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.i(TAG, "accept-throwable: " + throwable.getClass());
                        
                        if (throwable instanceof com.jakewharton.retrofit2.adapter.rxjava2.HttpException) {
                            if (((HttpException) throwable).code() == 403) {
                                
                                iServerCallbackObject.onSuccessCredit((ContentCredit) ((HttpException) throwable).response().body());
                            }
                        } else {
                            try {
                                throw throwable;
                            }
                            catch (Throwable throwable1) {
                                throwable1.printStackTrace();
                            }
                            Log.e(TAG, throwable.getMessage());
                            Log.e(TAG, "Info:\n" + "url: " + url + "\n\r" + "token:\n\r" + token);
                            iServerCallbackObject.onFailure(throwable.getMessage());
                        }
                    }
                });
    }
    
    @SuppressLint("CheckResult")
    @Override
    public void getFilterBySearchCall(String search, IServerCallbackObject iServerCallbackObject) {
        mainApi.getFilterBySearch(search)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<FilterModel>() {
                    @Override
                    public void accept(FilterModel response) throws Exception {
                        iServerCallbackObject.onSuccess(response);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        iServerCallbackObject.onFailure(throwable.getMessage());
                    }
                });
    }
    
    @SuppressLint("CheckResult")
    @Override
    public void getContentOnlyCall(String id, IServerCallbackObject iServerCallbackObject) {
        mainApi.getContentOnly(id, 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<FilterModel>() {
                    @Override
                    public void accept(FilterModel response) throws Exception {
                        iServerCallbackObject.onSuccess(response);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        iServerCallbackObject.onFailure(throwable.getMessage());
                    }
                });
    }
    
    @SuppressLint("CheckResult")
    @Override
    public void getFilterTagsByUrl(String url, IServerCallbackObject iServerCallbackObject) {
        mainApi.getFilterTagsByUrl(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<FilterModel>() {
                    @Override
                    public void accept(FilterModel response) throws Exception {
                        iServerCallbackObject.onSuccess(response);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        iServerCallbackObject.onFailure(throwable.getMessage());
                    }
                });
    }
    
    @SuppressLint("CheckResult")
    @Override
    public void getFilterTagsByList(List<String> params, IServerCallbackObject iServerCallbackObject) {
        mainApi.getFilterTags(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<FilterModel>() {
                    @Override
                    public void accept(FilterModel response) throws Exception {
                        iServerCallbackObject.onSuccess(response);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        iServerCallbackObject.onFailure(throwable.getMessage());
                    }
                });
    }
    
    @SuppressLint("CheckResult")
    @Override
    public void sendRegistrationToServer(int user_id, String firebaseToken, String token, IServerCallbackObject iServerCallbackObject) {
        mainApi.sendFirebaseToken(user_id, firebaseToken, ("Bearer " + token))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object response) throws Exception {
                        iServerCallbackObject.onSuccess(response);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        iServerCallbackObject.onFailure(throwable.getMessage());
                    }
                });
    }
    
    @SuppressLint("CheckResult")
    @Override
    public void getLastVersion(IServerCallbackObject iServerCallbackObject) {
        mainApi.getLastVersion()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<LastVersion>() {
                    @Override
                    public void accept(LastVersion response) throws Exception {
                        iServerCallbackObject.onSuccess(response);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        iServerCallbackObject.onFailure(throwable.getMessage());
                    }
                });
    }
}
