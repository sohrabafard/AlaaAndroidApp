package ir.sanatisharif.android.konkur96;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import ir.sanatisharif.android.konkur96.api.Models.ContentModel;
import ir.sanatisharif.android.konkur96.model.ContentCredit;
import ir.sanatisharif.android.konkur96.ui.alaacontent.AlaaContentFragment;
import ir.sanatisharif.android.konkur96.ui.alaacontent.AlaaContentViewModel;

public class AlaaContentActivity extends AppCompatActivity {
    public static final String
                                             LOAD_BUY_URL                                   =
            "load_buy_url";
    public static final String
                                             SET_TOKEN                                      =
            "load_buy_url_token";
    public static final String
                                             LOAD_BUY_CONTENT_ALSO_USER_CAN_SEE_CONTENT     =
            "load_buy_content_also_see";
    public static final String
                                             LOAD_BUY_CONTENT_ALSO_USER_CAN_NOT_SEE_CONTENT =
            "load_buy_content_also_can_not_see";
    private             AlaaContentViewModel mViewModel;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alaa_content_activity);
        mViewModel = ViewModelProviders.of(this).get(AlaaContentViewModel.class);
        
        if (savedInstanceState == null) {
            
            handleIntent();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, AlaaContentFragment.newInstance())
                    .commitNow();
        }
    }
    
    private void handleIntent() {
        Intent intent = getIntent();
        if (intent == null)
            return;
        
        String token  = intent.getStringExtra(SET_TOKEN);
        String action = intent.getAction();  // android.intent.action.VIEW
        String data   = intent.getDataString();// https://sanatisharif.ir/c/8087
        mViewModel.setToken(token);
        
        if (Intent.ACTION_VIEW.equals(action)) {
            Uri appLinkData = intent.getData();
            if (appLinkData != null) {
                String path = appLinkData.getPath();
                if (path != null && path.startsWith("/c/") && path.length() > 3) {
                    mViewModel.setUrl(data);
                    return;
                }
            }
        }
        ContentModel
                content =
                intent.getParcelableExtra(LOAD_BUY_CONTENT_ALSO_USER_CAN_SEE_CONTENT);
        if (content != null) {
            mViewModel.setCanSee(true);
            mViewModel.setContent(content);
            return;
        }
        
        String url = intent.getStringExtra(LOAD_BUY_URL);
        
        if (url != null) {
            mViewModel.setUrl(url);
            return;
        }
        
        
        ContentCredit
                error =
                intent.getParcelableExtra(LOAD_BUY_CONTENT_ALSO_USER_CAN_NOT_SEE_CONTENT);
        if (error != null) {
            mViewModel.setCanSee(false);
            mViewModel.setError(error);
        }
    }
    
    
}
