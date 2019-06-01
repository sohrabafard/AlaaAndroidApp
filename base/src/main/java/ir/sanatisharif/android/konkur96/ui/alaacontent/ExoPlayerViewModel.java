package ir.sanatisharif.android.konkur96.ui.alaacontent;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ExoPlayerViewModel extends ViewModel {
    private String                  TAG = "Alaa\\ExoPlayerViewModel";
    private MutableLiveData<String> mUrl;
    private String                  mToken;
    private int                     mPositionPlaying = 0;
    
    public String getToken() {
        return mToken;
    }
    
    public void setToken(String token) {
        this.mToken = token;
    }
    
    public LiveData<String> getUrl() {
        if (mUrl == null)
            mUrl = new MutableLiveData<>();
        
        return mUrl;
    }
    
    public void setUrl(String url) {
        if (mUrl == null)
            mUrl = new MutableLiveData<>();
        mPositionPlaying = 0;
        mUrl.setValue(url);
    }
    
    public int getPositionPlaying(){
        return mPositionPlaying;
    }
    public void setPositionPlaying(int positionPlaying){
        mPositionPlaying = positionPlaying;
    }
}
