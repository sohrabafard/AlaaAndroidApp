package ir.sanatisharif.android.konkur96.handler;

import java.util.List;

import ir.sanatisharif.android.konkur96.listener.api.IServerCallbackContentCredit;
import ir.sanatisharif.android.konkur96.listener.api.IServerCallbackObject;
import ir.sanatisharif.android.konkur96.model.user.User;

public interface MainRepositoryInterface {
    void mainPages(IServerCallbackObject iServerCallbackObject);
    
    void userInfo(User user, IServerCallbackObject iServerCallbackObject);
    
    void getContent(String url, String token, IServerCallbackContentCredit iServerCallbackObject);
    
    void getFilterBySearchCall(String search, IServerCallbackObject iServerCallbackObject);
    
    void getContentOnlyCall(String id, IServerCallbackObject iServerCallbackObject);
    
    void getFilterTagsByUrl(String url, IServerCallbackObject iServerCallbackObject);
    
    void getFilterTagsByList(List<String> params, IServerCallbackObject iServerCallbackObject);
    
    void sendRegistrationToServer(int user_id, String firebaseToken, String token, IServerCallbackObject iServerCallbackObject);
    
    void getLastVersion(IServerCallbackObject iServerCallbackObject);
}
