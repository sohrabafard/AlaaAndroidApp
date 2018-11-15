package ir.sanatisharif.android.konkur96.listener.api;


/**
 * Created by Mohamad on 7/25/2016.
 */
public interface IServerCallbackObject {

    public void onStart();
    public void onFinish();
    public void onSuccess(Object obj);
    public void onFailure(String message);
}
