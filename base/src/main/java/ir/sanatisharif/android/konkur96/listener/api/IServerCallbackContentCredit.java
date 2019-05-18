package ir.sanatisharif.android.konkur96.listener.api;


import ir.sanatisharif.android.konkur96.model.ContentCredit;

/**
 * Created by Mohamad on 7/25/2016.
 */
public interface IServerCallbackContentCredit {

    void onSuccess(Object obj);

    void onSuccessCredit(ContentCredit obj);

    void onFailure(String message);
}
