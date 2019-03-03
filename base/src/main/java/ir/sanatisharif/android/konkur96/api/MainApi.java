package ir.sanatisharif.android.konkur96.api;

import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import ir.sanatisharif.android.konkur96.app.AppConstants;
import ir.sanatisharif.android.konkur96.listener.api.IServerCallbackObject;
import ir.sanatisharif.android.konkur96.model.DataCourse;
import ir.sanatisharif.android.konkur96.model.filter.Filter;
import ir.sanatisharif.android.konkur96.model.main_page.MainPagesInfo;
import ir.sanatisharif.android.konkur96.model.user.User;
import ir.sanatisharif.android.konkur96.model.user.UserInfo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Mohamad on 2/11/2019.
 */

public class MainApi {

    private static ApiRetrofit api;
    private static MainApi mainApi;
    private static Gson gson = new Gson();

    private MainApi() {
        api = RetrofitClient.getInstance().create(ApiRetrofit.class);
    }

    //singleton api service
    public static MainApi getInstance() {
        if (mainApi == null)
            mainApi = new MainApi();
        return mainApi;
    }

    public void mainPages(IServerCallbackObject iServerCallbackObject) {

        Call<MainPagesInfo> mainPage = api.getMainPage();

        mainPage.enqueue(new Callback<MainPagesInfo>() {
            @Override
            public void onResponse(Call<MainPagesInfo> call, Response<MainPagesInfo> response) {

                if (response.isSuccessful()) {
                    iServerCallbackObject.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(Call<MainPagesInfo> call, Throwable t) {
                Log.i("LOG", "onFailure: fai");
                iServerCallbackObject.onFailure(t.getMessage());
            }
        });
    }

    public void userInfo(User user, IServerCallbackObject iServerCallbackObject) {

        Call<UserInfo> userInfo = api.getLoginUserInfo(user);

        userInfo.enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {

                if (response.isSuccessful()) {
                    UserInfo u = response.body();
                    iServerCallbackObject.onSuccess(u);
                } else {
                    try {
                        Log.i("LOG", "onResponse: " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<UserInfo> call, Throwable t) {
                iServerCallbackObject.onFailure(t.getMessage());

            }
        });
    }

    public void getDetailsCourse(String id, IServerCallbackObject iServerCallbackObject) {

        Call<DataCourse> detailsCourseCall = api.getDetailsCourseByID(id);
        detailsCourseCall.enqueue(new Callback<DataCourse>() {
            @Override
            public void onResponse(Call<DataCourse> call, Response<DataCourse> response) {

                if (response.isSuccessful()) {
                    iServerCallbackObject.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(Call<DataCourse> call, Throwable t) {
                iServerCallbackObject.onFailure(t.getMessage());
            }
        });
    }

    public void getFilterBySearchCall(String search, IServerCallbackObject iServerCallbackObject) {

        Call<Filter> filterCall = api.getFilterBySearch(search);

        filterCall.enqueue(new Callback<Filter>() {
            @Override
            public void onResponse(Call<Filter> call, Response<Filter> response) {

                if (response.isSuccessful()) {
                    iServerCallbackObject.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(Call<Filter> call, Throwable t) {
                iServerCallbackObject.onFailure(t.getMessage());
            }
        });
    }


    public void getContentOnlyCall(String id, IServerCallbackObject iServerCallbackObject) {

        Call<Filter> filterCall = api.getContentOnly(id, 1);

        filterCall.enqueue(new Callback<Filter>() {
            @Override
            public void onResponse(Call<Filter> call, Response<Filter> response) {

                if (response.isSuccessful()) {
                    iServerCallbackObject.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(Call<Filter> call, Throwable t) {
                iServerCallbackObject.onFailure(t.getMessage());
            }
        });
    }

    public void getFilterTagsByUrl(String url, IServerCallbackObject iServerCallbackObject) {

        Call<Filter> filterCall = api.getFilterTagsByUrl(url);

        Callback<Filter> requestCallback = new Callback<Filter>() {
            @Override
            public void onResponse(Call<Filter> call, Response<Filter> response) {

                if (response.isSuccessful()) {
                    iServerCallbackObject.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(Call<Filter> call, Throwable t) {
                iServerCallbackObject.onFailure(t.getMessage());
            }
        };
        filterCall.enqueue(requestCallback);
    }

    public void getFilterTagsByList(List<String> params, IServerCallbackObject iServerCallbackObject) {

        Call<Filter> objectCall = api.getFilterTags(params);

        objectCall.enqueue(new Callback<Filter>() {
            @Override
            public void onResponse(Call<Filter> call, Response<Filter> response) {

                if (response.isSuccessful()) {
                    iServerCallbackObject.onSuccess(response.body());
                }
            }

            @Override
            public void onFailure(Call<Filter> call, Throwable t) {
                iServerCallbackObject.onFailure(t.getMessage());
            }
        });
    }
}
