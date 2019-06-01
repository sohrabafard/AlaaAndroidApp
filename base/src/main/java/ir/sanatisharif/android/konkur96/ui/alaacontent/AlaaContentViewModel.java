package ir.sanatisharif.android.konkur96.ui.alaacontent;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import ir.sanatisharif.android.konkur96.api.Models.ContentModel;
import ir.sanatisharif.android.konkur96.handler.MainRepository;
import ir.sanatisharif.android.konkur96.listener.api.IServerCallbackContentCredit;
import ir.sanatisharif.android.konkur96.model.ContentCredit;

public class AlaaContentViewModel extends ViewModel {
    
    private String                         TAG = "Alaa\\ContentViewModel";
    private String                         mUrl;
    private String                         mToken;
    private MutableLiveData<ContentModel>  mContent;
    private MutableLiveData<ContentCredit> mError;
    private MutableLiveData<Boolean>       mCanSee;
    private MainRepository                 mRepository;
    
    public AlaaContentViewModel() {
        super();
        mRepository = new MainRepository();
    }
    
    public void setUrl(String mUrl) {
        this.mUrl = mUrl;
    }
    
    public void setToken(String mToken) {
        this.mToken = mToken;
    }
    
    public void setCanSee(Boolean canSee) {
        mCanSee.setValue(canSee);
    }
    
    public LiveData<ContentModel> getContent() {
        if (mContent == null) {
            mContent = new MutableLiveData<>();
            loadContent();
        }
        return mContent;
    }
    
    public void setContent(ContentModel content) {
        mContent.setValue(content);
    }
    
    public LiveData<ContentCredit> getError() {
        if (mError == null) {
            mError = new MutableLiveData<>();
            loadContent();
        }
        return mError;
    }
    
    public void setError(ContentCredit error) {
        mError.setValue(error);
    }
    
    public LiveData<Boolean> userCanSeeContent() {
        if (mCanSee == null) {
            mCanSee = new MutableLiveData<>();
            loadContent();
        }
        return mCanSee;
    }
    
    private void init() {
        if (mCanSee == null)
            mCanSee = new MutableLiveData<>();
        if (mError == null)
            mError = new MutableLiveData<>();
        if (mContent == null)
            mContent = new MutableLiveData<>();
    }
    
    private void loadContent() {
        if (mUrl == null)
            return;
        init();
        
        mRepository.getContent(mUrl, mToken, new IServerCallbackContentCredit() {
            @Override
            public void onSuccess(Object obj) {
                mCanSee.setValue(true);
                mContent.setValue((ContentModel) obj);
            }
            
            @Override
            public void onSuccessCredit(ContentCredit obj) {
                mCanSee.setValue(false);
                mError.setValue(obj);
            }
            
            @Override
            public void onFailure(String message) {
                Log.e(TAG, message);
            }
        });
    }
    
    @Override
    protected void onCleared() {
        super.onCleared();
        
    }
}
