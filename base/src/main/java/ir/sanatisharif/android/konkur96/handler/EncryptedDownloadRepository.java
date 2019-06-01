package ir.sanatisharif.android.konkur96.handler;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;

import javax.inject.Inject;

import ir.sanatisharif.android.konkur96.api.HeadRequestInterface;
import ir.sanatisharif.android.konkur96.app.AppConfig;
import retrofit2.Call;
import retrofit2.Response;

public class EncryptedDownloadRepository implements EncryptedDownloadInterface {
    
    private static String TAG = "Alaa\\EncryptedDownloadRepository";
    
    
    @Inject
    HeadRequestInterface headRequest;
    
    public EncryptedDownloadRepository(Activity activity) {
        ((AppConfig) activity.getApplication()).getAppComponent().inject(this);
    }
    
    @SuppressLint("CheckResult")
    @Override
    public void getDirectLink(String url, String token, EncryptedDownloadInterface.Callback callBack) {
        
        Log.e(TAG, "start: getDirectLink");
        headRequest.get(url, ("Bearer " + token)).enqueue(new retrofit2.Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                String link = response.headers().get("location");
                if (link == null)
                    link = url;
                callBack.fetch(link);
            }
            
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                callBack.error(t.getMessage());
            }
        });
        Log.e(TAG, "end: getDirectLink");
        
    }
}
