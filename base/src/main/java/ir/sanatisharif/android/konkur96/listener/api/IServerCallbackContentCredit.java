package ir.sanatisharif.android.konkur96.listener.api;


import ir.sanatisharif.android.konkur96.model.ContentCredit;

/**
 * Created by Mohamad on 7/25/2016.
 */
public interface IServerCallbackContentCredit {

    public void onSuccess(Object obj);
    public void onSuccessCredit(ContentCredit obj);
    public void onFailure(String message);
}
