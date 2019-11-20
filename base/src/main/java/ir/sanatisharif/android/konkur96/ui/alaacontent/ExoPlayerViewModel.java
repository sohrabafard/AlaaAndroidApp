package ir.sanatisharif.android.konkur96.ui.alaacontent;

import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ExoPlayerViewModel extends ViewModel {
    private String               TAG = "Alaa\\ExoPlayerViewModel";
    private MutableLiveData<Uri> mVideoUri;
    private String               mTxtQuality;
    private String               mToken;
    
    public String getTxtQuality() {
        return mTxtQuality;
    }
    
    public void setTxtQuality(String txtQuality) {
        mTxtQuality = txtQuality;
    }
    
    public String getToken() {
        return mToken;
    }
    
    public void setToken(String token) {
        this.mToken = token;
    }
    
    public LiveData<Uri> getUrl() {
        if (mVideoUri == null)
            mVideoUri = new MutableLiveData<>();
        
        return mVideoUri;
    }
    
    public void setUri(Uri uri) {
        if (mVideoUri == null)
            mVideoUri = new MutableLiveData<>();
        mVideoUri.setValue(uri);
    }
}
