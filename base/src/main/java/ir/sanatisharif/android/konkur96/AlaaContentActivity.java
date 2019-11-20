package ir.sanatisharif.android.konkur96;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.Optional;

import ir.sanatisharif.android.konkur96.Models.ContentModel;
import ir.sanatisharif.android.konkur96.account.AuthenticatorActivity;
import ir.sanatisharif.android.konkur96.activity.MainActivity;
import ir.sanatisharif.android.konkur96.interfaces.LogUserActionsOnPublicContentInterface;
import ir.sanatisharif.android.konkur96.model.ContentCredit;
import ir.sanatisharif.android.konkur96.model.FileDiskModel;
import ir.sanatisharif.android.konkur96.ui.alaacontent.AlaaContentFragment;
import ir.sanatisharif.android.konkur96.ui.alaacontent.AlaaContentViewModel;
import ir.sanatisharif.android.konkur96.ui.alaacontent.ExoPlayerFragment;
import ir.sanatisharif.android.konkur96.ui.alaacontent.ExoPlayerViewModel;
import ir.sanatisharif.android.konkur96.utils.AuthToken;
import ir.sanatisharif.android.konkur96.utils.ExistsOnSD;

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
    private             ExoPlayerViewModel   mPlayerViewModel;
    private             SharedPreferences    sharedPreferences;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alaa_content_activity);
        
        mContentViewModel = ViewModelProviders.of(this).get(AlaaContentViewModel.class);
        mPlayerViewModel = ViewModelProviders.of(this).get(ExoPlayerViewModel.class);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());
        
        
        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        
        
        if (savedInstanceState == null) {
            Intent intent = getIntent();
            fetchToken(intent);
            handleIntent(intent);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, AlaaContentFragment.newInstance())
                    .commitNow();
            
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.top_video_player, ExoPlayerFragment.newInstance())
                    .commitNow();
        }
        
        mContentViewModel.getContent().observe(this, content -> {
            if (content != null) {
                ArrayList<FileDiskModel> fileDiskModels = content.getVideoFiles();
                ExistsOnSD.checkExistVideoToSD(fileDiskModels, new ExistsOnSD.BooleanCallback() {
                    @Override
                    public void onFalse() {
                        FileDiskModel video = handleQualityLink(fileDiskModels);
                        mPlayerViewModel.setTxtQuality(video.getCaption());
                        mPlayerViewModel.setUri(Uri.parse(video.getLink()));
            
                    }
        
                    @Override
                    public void onTrue(String path) {
                        mPlayerViewModel.setTxtQuality("پخش از حافظه");
                        mPlayerViewModel.setUri(Uri.parse(path));
                    }
                });
                
            }
        });
    }
    
    private FileDiskModel handleQualityLink(final ArrayList<FileDiskModel> fileDiskModels) {
        String
                      selectedQuality =
                sharedPreferences.getString(getString(R.string.player_quality), "240");
        FileDiskModel selectedVideo   = null;
        
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            Optional<FileDiskModel>
                    fileDiskModel =
                    fileDiskModels.stream().filter(o -> o.getRes().contains(selectedQuality)).findFirst();
            if (fileDiskModel.isPresent())
                selectedVideo = fileDiskModel.get();
        } else {
            for (FileDiskModel video :
                    fileDiskModels) {
                if (video.getRes().contains(selectedQuality)) {
                    selectedVideo = video;
                    break;
                }
            }
        }
        if (selectedVideo == null)
            selectedVideo = fileDiskModels.get(0);
        
        return selectedVideo;
    }
    
    private void handleIntent(Intent intent) {
        
        if (intent == null)
            return;
        
        String action = intent.getAction();  // android.intent.action.VIEW
        String data   = intent.getDataString();// https://sanatisharif.ir/c/8087
        
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
    
    private void setToken(@NonNull String token) {
        mContentViewModel.setToken(token);
        mPlayerViewModel.setToken(token);
    }
    
    private void fetchToken(Intent intent) {
        if (intent != null && fetchTokenFromIntent(intent)) {
            return;
        }
        fetchTokenFromAccountManager();
    }
    
    private boolean fetchTokenFromIntent(@NonNull Intent intent) {
        String token = intent.getStringExtra(SET_TOKEN);
        if (token != null) {
            setToken(token);
            return true;
        }
        return false;
    }
    
    private void fetchTokenFromAccountManager() {
        Activity activity = this;
        AuthToken.getInstant().get(this, this, new AuthToken.Callback() {
            @Override
            public void run(@NonNull String token) {
                setToken(token);
            }
            
            @Override
            public void nill() {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(activity, AuthenticatorActivity.class));
                    }
                });
            }
        });
    }
    
    
}
