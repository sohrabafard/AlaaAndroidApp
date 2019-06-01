package ir.sanatisharif.android.konkur96;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import ir.sanatisharif.android.konkur96.Models.ContentModel;
import ir.sanatisharif.android.konkur96.activity.MainActivity;
import ir.sanatisharif.android.konkur96.interfaces.LogUserActionsOnPublicContentInterface;
import ir.sanatisharif.android.konkur96.model.ContentCredit;
import ir.sanatisharif.android.konkur96.ui.alaacontent.AlaaContentFragment;
import ir.sanatisharif.android.konkur96.ui.alaacontent.AlaaContentViewModel;

public class AlaaContentActivity extends AppCompatActivity implements LogUserActionsOnPublicContentInterface {
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
    private             AlaaContentViewModel mContentViewModel;

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alaa_content_activity);
    
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());
    
    
        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    
        mContentViewModel = ViewModelProviders.of(this).get(AlaaContentViewModel.class);
        
        if (savedInstanceState == null) {
            
            handleIntent();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, AlaaContentFragment.newInstance())
                    .commitNow();
        }
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            navigateUpTo(new Intent(this, MainActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    @Override
    protected void onStop() {
        this.userHasFinishedViewingPage(mContentViewModel.getContent().getValue());
        super.onStop();
    }
    
    private void handleIntent() {
        Intent intent = getIntent();
        if (intent == null)
            return;
        
        String token  = intent.getStringExtra(SET_TOKEN);
        String action = intent.getAction();  // android.intent.action.VIEW
        String data   = intent.getDataString();// https://sanatisharif.ir/c/8087
        mContentViewModel.setToken(token);
        
        if (Intent.ACTION_VIEW.equals(action)) {
            Uri appLinkData = intent.getData();
            if (appLinkData != null) {
                String path = appLinkData.getPath();
                if (path != null && path.startsWith("/c/") && path.length() > 3) {
                    mContentViewModel.setUrl(data);
                    return;
                }
            }
        }
        ContentModel
                content =
                intent.getParcelableExtra(LOAD_BUY_CONTENT_ALSO_USER_CAN_SEE_CONTENT);
        if (content != null) {
            mContentViewModel.setCanSee(true);
            mContentViewModel.setContent(content);
            return;
        }
        
        String url = intent.getStringExtra(LOAD_BUY_URL);
        
        if (url != null) {
            mContentViewModel.setUrl(url);
            return;
        }
        
        
        ContentCredit
                error =
                intent.getParcelableExtra(LOAD_BUY_CONTENT_ALSO_USER_CAN_NOT_SEE_CONTENT);
        if (error != null) {
            mContentViewModel.setCanSee(false);
            mContentViewModel.setError(error);
        }
    }
    
    
}
