package ir.sanatisharif.android.konkur96.api;

import android.support.annotation.NonNull;
import android.telecom.Call;

import ir.sanatisharif.android.konkur96.listener.api.IServerCallbackObject;
import ir.sanatisharif.android.konkur96.model.filter.Filter;
import ir.sanatisharif.android.konkur96.model.filter.VideoCourse;
import ir.sanatisharif.android.konkur96.model.filter.VideoRoot;

/**
 * Created by Mohamad on 2/27/2019.
 */

public class SearchDataSource {
} /*extends PageKeyedDataSource<Integer, VideoCourse> {

    // private final MutableLiveData<RequestFailure> requestFailureLiveData;

    private String url;

    public SearchDataSource(String url) {

        this.url = url;
        //this.requestFailureLiveData = new MutableLiveData<>();
    }


    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, VideoCourse> callback) {

        int page = 1;

        MainApi.getInstance().getFilterTagsByUrl(url, new IServerCallbackObject() {
            @Override
            public void onSuccess(Object obj) {
                Filter f = (Filter) obj;

                callback.
                        onResult(f.getResult().
                                getVideo().
                                getData(), 0, 100, null, page + 1);
            }

            @Override
            public void onFailure(String message) {
                // Allow user to retry the failed request
                loadInitial(params, callback);
            }
        });


    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, VideoCourse> callback) {

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, VideoCourse> callback) {

        // Next page.
        final int page = params.key;

        MainApi.getInstance().getFilterTagsByUrl(url, new IServerCallbackObject() {
            @Override
            public void onSuccess(Object obj) {

                Filter f = (Filter) obj;
                callback.onResult(f.getResult().getVideo().getData(), page + 1);
            }

            @Override
            public void onFailure(String message) {
                loadAfter(params, callback);
            }
        });
    }
}
*/