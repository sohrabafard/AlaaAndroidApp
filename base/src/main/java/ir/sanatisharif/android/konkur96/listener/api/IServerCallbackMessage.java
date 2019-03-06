package ir.sanatisharif.android.konkur96.listener.api;


import java.util.ArrayList;

/**
 * Created by Mohamad on 7/25/2016.
 */
public interface IServerCallbackMessage {

    public void onStart();
    public void onSuccess(String str);
    public void onFinish();
    public void onFailure(String message);
}
