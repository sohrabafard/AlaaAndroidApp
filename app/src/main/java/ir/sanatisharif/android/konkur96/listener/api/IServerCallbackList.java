package ir.sanatisharif.android.konkur96.listener.api;


import java.util.ArrayList;

/**
 * Created by Mohamad on 7/25/2016.
 */
public interface IServerCallbackList {

    public void onStart();
    public void onFinish();
    public void onSuccess(ArrayList<?> list);
    public void onFailure(String message);
}
