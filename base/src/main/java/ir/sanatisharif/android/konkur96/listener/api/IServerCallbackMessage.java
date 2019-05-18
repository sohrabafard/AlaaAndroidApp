package ir.sanatisharif.android.konkur96.listener.api;


import java.util.ArrayList;

/**
 * Created by Mohamad on 7/25/2016.
 */
public interface IServerCallbackMessage {

    void onStart();

    void onSuccess(String str);

    void onFinish();

    void onFailure(String message);
}
