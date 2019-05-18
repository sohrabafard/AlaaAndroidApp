package ir.sanatisharif.android.konkur96.listener.api;


import java.util.ArrayList;

/**
 * Created by Mohamad on 7/25/2016.
 */
public interface IServerCallbackList {

    void onStart();

    void onFinish();

    void onSuccess(ArrayList<?> list);

    void onFailure(String message);
}
