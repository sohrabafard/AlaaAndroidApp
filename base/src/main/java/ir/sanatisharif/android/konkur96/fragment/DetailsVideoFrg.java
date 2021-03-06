package ir.sanatisharif.android.konkur96.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

import com.crashlytics.android.Crashlytics;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlaybackControlView;
import com.google.android.exoplayer2.ui.PlayerControlView;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.google.android.gms.common.wrappers.InstantApps;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import ir.sanatisharif.android.konkur96.Models.BlockDataModel;
import ir.sanatisharif.android.konkur96.Models.ContentModel;
import ir.sanatisharif.android.konkur96.Models.PaginationModel;
import ir.sanatisharif.android.konkur96.Models.ProductModel;
import ir.sanatisharif.android.konkur96.Models.filter.FilterBaseModel;
import ir.sanatisharif.android.konkur96.Models.filter.FilterModel;
import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.account.AccountInfo;
import ir.sanatisharif.android.konkur96.account.AuthenticatorActivity;
import ir.sanatisharif.android.konkur96.activity.ActivityBase;
import ir.sanatisharif.android.konkur96.adapter.BlockAdapter;
import ir.sanatisharif.android.konkur96.adapter.PlayListAdapter;
import ir.sanatisharif.android.konkur96.app.AppConfig;
import ir.sanatisharif.android.konkur96.app.AppConstants;
import ir.sanatisharif.android.konkur96.dialog.DeleteFileDialogFrg;
import ir.sanatisharif.android.konkur96.dialog.DownloadDialogFrg;
import ir.sanatisharif.android.konkur96.handler.MainRepository;
import ir.sanatisharif.android.konkur96.interfaces.LogUserActionsOnPublicContentInterface;
import ir.sanatisharif.android.konkur96.listener.DownloadComplete;
import ir.sanatisharif.android.konkur96.listener.OnItemClickListener;
import ir.sanatisharif.android.konkur96.listener.api.IServerCallbackContentCredit;
import ir.sanatisharif.android.konkur96.listener.api.IServerCallbackObject;
import ir.sanatisharif.android.konkur96.model.ContentCredit;
import ir.sanatisharif.android.konkur96.model.FileDiskModel;
import ir.sanatisharif.android.konkur96.ui.view.MDToast;
import ir.sanatisharif.android.konkur96.utils.AuthToken;
import ir.sanatisharif.android.konkur96.utils.EndlessRecyclerViewScrollListener;
import ir.sanatisharif.android.konkur96.utils.ExistsOnSD;
import ir.sanatisharif.android.konkur96.utils.Utils;
import me.gujun.android.taggroup.TagGroup;

import static android.content.Context.KEYGUARD_SERVICE;
import static android.content.Context.POWER_SERVICE;
import static ir.sanatisharif.android.konkur96.activity.MainActivity.addFrg;
import static ir.sanatisharif.android.konkur96.app.AppConfig.context;
import static ir.sanatisharif.android.konkur96.app.AppConfig.currentActivity;

/**
 * Created by Mohamad on 10/13/2018.
 */

public class DetailsVideoFrg extends BaseFragment implements View.OnClickListener {
    
    private final static String
                                                       TAG          =
            "Alaa\\DetailsVideoFrg";
    private static final int                           LOAD_URL     = 0;
    private static final int                           LOAD_CONTENT = 1;
    private static final int                           LOAD_LIST    = 2;
    public static        PaginationModel<ContentModel> pagination;
    private static       int                           kind_of_Load = -1;
    private static       List<ContentModel>            ContentModels;
    private static       ContentModel                  mContent;
    private static       int                           positionPlaying;
    
    private final String STATE_RESUME_WINDOW     = "resumeWindow";
    private final String STATE_RESUME_POSITION   = "resumePosition";
    private final String STATE_PLAYER_FULLSCREEN = "playerFullscreen";
    
    RecyclerView.SmoothScroller smoothScroller = new LinearSmoothScroller(context) {
        @Override
        protected int getVerticalSnapPreference() {
            return LinearSmoothScroller.SNAP_TO_START;
        }
    };
    private LogUserActionsOnPublicContentInterface mUserAction;
    private String                                 quality      = "";
    private Bundle                                 savedInsPlayer;
    private ContentModel                           course;
    private boolean                                showPlayList = true;
    private int                                    mResumeWindow;
    private long                                   mResumePosition;
    private String                                 mUrl;
    private ProgressBar                            progressBarExoplaying;
    private RecyclerView                           myRecyclerViewProduct;
    private ImageView                              imgDownload;
    private ImageView                              imgReady;
    private ImageView                              imgShare;
    private FrameLayout                            mediaVideoFrame;
    private TextView                               txtDesc;
    private TextView                               txtAuthor;
    private TextView                               txtTitle;
    private TagGroup                               tagGroup;
    private ProgressBar                            loader, loaderPlayList;
    private LinearLayout                      root;
    //preview
    private FrameLayout                       relativePreview;
    private ImageView                         imgPreview;
    private ImageView                         imgPlay;
    //play list
    private ImageView                         imgArrow;
    private RecyclerView                      recyclerPlayList;
    private PlayListAdapter                   playListAdapter;
    private LinearLayoutManager               managerPlayList;
    private EndlessRecyclerViewScrollListener endLess;
    //player
    private PlaybackControlView               controlView;
    //<editor-fold desc="animate">
    Animator.AnimatorListener animatorHide = new Animator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animator) {
        
        }
        
        @Override
        public void onAnimationEnd(Animator animator) {
            controlView.findViewById(R.id.progressBar);
            controlView.setVisibility(View.VISIBLE);
        }
        
        @Override
        public void onAnimationCancel(Animator animator) {
        
        }
        
        @Override
        public void onAnimationRepeat(Animator animator) {
        
        }
    };
    Animator.AnimatorListener animatorShow = new Animator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animator) {
        
        }
        
        @Override
        public void onAnimationEnd(Animator animator) {
            controlView.findViewById(R.id.progressBar).setVisibility(View.GONE);
        }
        
        @Override
        public void onAnimationCancel(Animator animator) {
        
        }
        
        @Override
        public void onAnimationRepeat(Animator animator) {
        
        }
    };
    
    private MainRepository  repository;
    private SimpleExoPlayer player;
    Player.EventListener eventListener      = new Player.EventListener() {
        @Override
        public void onTimelineChanged(Timeline timeline, @Nullable Object manifest, int reason) {
        
        }
        
        @Override
        public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
        
        }
        
        @Override
        public void onLoadingChanged(boolean isLoading) {
            Log.i(TAG, "onLoadingChanged: isLoading");
        }
        
        @Override
        public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
            
            switch (playbackState) {
                case Player.STATE_IDLE:       // The player does not have any media to play yet.
                    controlView.findViewById(R.id.progressBar);
                    break;
                case Player.STATE_BUFFERING:  // The player is buffering (loading the mContent)
                    
                    controlView.findViewById(R.id.exo_play).animate().alpha(0F).setDuration(400).setListener(animatorHide);
                    controlView.findViewById(R.id.exo_pause).animate().alpha(0F).setDuration(400).setListener(animatorHide);
                    
                    
                    break;
                case Player.STATE_READY:      // The player is able to immediately play
                    if (progressBarExoplaying != null)
                        progressBarExoplaying.setVisibility(View.GONE);
                    controlView.findViewById(R.id.exo_play).animate().alpha(1).setDuration(400).setListener(animatorShow);
                    controlView.findViewById(R.id.exo_pause).animate().alpha(1).setDuration(400).setListener(animatorShow);
                    break;
                case Player.STATE_ENDED:      // The player has finished playing the media
                    if (progressBarExoplaying != null)
                        progressBarExoplaying.setVisibility(View.GONE);
                    
                    break;
            }
        }
        
        @Override
        public void onRepeatModeChanged(int repeatMode) {
        
        }
        
        @Override
        public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {
        
        }
        
        @Override
        public void onPlayerError(ExoPlaybackException error) {
            switch (error.type) {
                case ExoPlaybackException.TYPE_SOURCE:
                    Log.e(TAG, "TYPE_SOURCE: " + error.getSourceException().getMessage());
                    ActivityBase.toastShow("خطا در بارگذاری ویدیو", MDToast.TYPE_ERROR);
                    player.stop();
                    relativePreview.setVisibility(View.VISIBLE);
                    mediaVideoFrame.setVisibility(View.GONE);
                    break;
                
                case ExoPlaybackException.TYPE_RENDERER:
                    Log.e(TAG, "TYPE_RENDERER: " + error.getRendererException().getMessage());
                    ActivityBase.toastShow("خطا در خواندن ویدیو", MDToast.TYPE_ERROR);
                    player.stop();
                    relativePreview.setVisibility(View.VISIBLE);
                    mediaVideoFrame.setVisibility(View.GONE);
                    break;
                
                case ExoPlaybackException.TYPE_UNEXPECTED:
                    Log.e(TAG, "TYPE_UNEXPECTED: " + error.getUnexpectedException().getMessage());
                    player.stop();
                    relativePreview.setVisibility(View.VISIBLE);
                    mediaVideoFrame.setVisibility(View.GONE);
                    break;
            }
        }
        
        @Override
        public void onPositionDiscontinuity(int reason) {
        
        }
        
        @Override
        public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
        
        }
        
        @Override
        public void onSeekProcessed() {
        
        }
    };
    //<editor-fold desc="phoneStateReceiver">
    BroadcastReceiver    phoneStateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle extras = intent.getExtras();
            if (extras != null) {
                String state = extras.getString(TelephonyManager.EXTRA_STATE);
                if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
                    player.setPlayWhenReady(false);
                    
                } else if (state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)) {
                    //pause here
                    player.setPlayWhenReady(false);
                } else if (state.equals(TelephonyManager.EXTRA_STATE_IDLE)) {
                    //play here
                    player.setPlayWhenReady(false);
                }
            }
        }
    };
    private SimpleExoPlayerView          mExoPlayerView;
    private MediaSource                  mVideoSource;
    private FrameLayout                  mFullScreenButton;
    private ImageView                    mFullScreenIcon;
    private TextView                     txtQuality;
    private Dialog                       mFullScreenDialog;
    private boolean                      mExoPlayerFullscreen = false;
    //comment
    private BlockAdapter                 adapter;
    private ArrayList<BlockDataModel>    items                = new ArrayList<>();
    //lock
    private PowerManager                 pm;
    private PowerManager.WakeLock        wl;
    private KeyguardManager              km;
    private KeyguardManager.KeyguardLock kl;
    private AccountInfo                  accountInfo;
    
    // from list
    public static DetailsVideoFrg newInstance(List<? extends FilterBaseModel> v, int pos) {
        
        Bundle          args     = new Bundle();
        DetailsVideoFrg fragment = new DetailsVideoFrg();
        fragment.setArguments(args);
        ContentModels = (List<ContentModel>) v;//get video list
        positionPlaying = pos;
        kind_of_Load = LOAD_LIST;
        return fragment;
    }
    
    // from mContent
    public static DetailsVideoFrg newInstance(ContentModel c) {
        
        Bundle          args     = new Bundle();
        DetailsVideoFrg fragment = new DetailsVideoFrg();
        fragment.setArguments(args);
        mContent = c;
        kind_of_Load = LOAD_CONTENT;
        return fragment;
    }
    
    // from url, i.e load info from deepLink
    public static DetailsVideoFrg newInstance(String url) {
        
        Bundle args = new Bundle();
        args.putString("url", url);
        DetailsVideoFrg fragment = new DetailsVideoFrg();
        fragment.setArguments(args);
        kind_of_Load = LOAD_URL;
        return fragment;
    }
    
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        
        try {
            mUserAction = (LogUserActionsOnPublicContentInterface) context;
        }
        catch (ClassCastException ex) {
            throw new ClassCastException(context.toString()
                                         +
                                         " must implement LogUserActionsOnPublicContentInterface");
        }
    }
    
    @Override
    public void onDetach() {
        super.onDetach();
        mUserAction = null;
    }
    
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
    }
    
    @Override
    public void onStart() {
        try {
            if (mUserAction != null && mContent != null)
                mUserAction.userStartedViewingAParticularPage(mContent);
        }
        catch (Exception ex) {
            Crashlytics.logException(ex);
        }
        try {
            IntentFilter filter = new IntentFilter();
            filter.addAction("android.intent.action.PHONE_STATE");
            getActivity().registerReceiver(phoneStateReceiver, filter);
            initWakeLockScreen();
            savedInsPlayer = new Bundle();
            
            Log.i(TAG, "onStart: ");
            if (Util.SDK_INT > 23) {
                if (player == null)
                    initExoPlayer();
                else {
                    player.setPlayWhenReady(false);
                }
            }
        }
        catch (Exception ex) {
            Crashlytics.logException(ex);
        }
        
        super.onStart();
    }
    
    @Override
    public void onResume() {
        super.onResume();
        if (Util.SDK_INT <= 23) {
            if (player == null) {
                initExoPlayer();
            } else {
                player.setPlayWhenReady(false);
            }
        }
        Log.i(TAG, "onStart:onResume ");
    }
    
    @Override
    public void onStop() {
        try {
            if (mUserAction != null && mContent != null)
                mUserAction.userHasFinishedViewingPage(mContent);
        }
        catch (Exception ex) {
            Crashlytics.logException(ex);
        }
        
        try {
            getActivity().unregisterReceiver(phoneStateReceiver);
            if (Util.SDK_INT > 23) {
                player.setPlayWhenReady(false);
                Log.i(TAG, "onStart:onStop ");
            }
        }
        catch (Exception ex) {
            Crashlytics.logException(ex);
        }
        super.onStop();
    }
    
    @Override
    public void onPause() {
        super.onPause();
        
        if (Util.SDK_INT <= 23 && player != null) {
            player.setPlayWhenReady(false);
            Log.i(TAG, "onStart:onPause ");
        }
    }
    
    @Override
    public void onDestroyView() {
        releasePlayer();
        super.onDestroyView();
        Log.i(TAG, "onStart: onDesc ");
    }
    
    @Override
    public View createFragmentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (repository == null)
            repository = new MainRepository(getActivity());
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }
    
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        if (repository == null)
            repository = new MainRepository(getActivity());
        
        initView(view, savedInstanceState);
        
        if (kind_of_Load == LOAD_CONTENT) {
            course = mContent;
            setData();
            getPlayListFromContentByUrl(mContent.getSet().getContentUrl());
            
        } else if (kind_of_Load == LOAD_URL) { //get data from api and url
            String url = getArguments().getString("url");
            String id  = url.substring(url.lastIndexOf("/") + 1);
            this.mUrl = url;
            getData(url);
        } else if (kind_of_Load == LOAD_LIST) {
            //get data from list
            course = ContentModels.get(positionPlaying);
            setData();
        }
    }
    
    @Override
    public void onDestroy() {
        super.onDestroy();
        
        if (ContentModels != null) {
            ContentModels = null;
            positionPlaying = 0;
        }
        if (playListAdapter != null) {
            playListAdapter = null;
        }
    }
    
    private void getData(String url) {
        
        Context context = getContext();
        if (context != null && InstantApps.isInstantApp(context)) {
            repository.getContent(url, null, new IServerCallbackContentCredit() {
                @Override
                public void onSuccess(Object obj) {
                    if (obj != null) {
                        course = (ContentModel) obj;
                        setData();
                        getPlayListFromContentByUrl(course.getSet().getContentUrl());
                    } else {
                        Log.i(TAG, "getData-onSuccess: \n\r" + url + "object is null");
                    }
                    
                }
                
                @Override
                public void onSuccessCredit(ContentCredit obj) {
                    if (obj != null) {
                        course = obj.getContent();
                        setData();
                        showSnackBar(obj.getMessage());
                        setProduct(obj.getProduct());
                        getPlayListFromContentByUrl(course.getSet().getContentUrl());
                        
                    } else {
                        Log.i(TAG, "getData-if-onSuccessCredit: \n\r" + url + "object is null");
                    }
                }
                
                @Override
                public void onFailure(String message) {
                    Log.i(TAG, "onSuccess:error " + message);
                }
            });
        } else if (context != null) {
            
            AuthToken.getInstant().get(context, currentActivity, new AuthToken.Callback() {
                @Override
                public void run(@NonNull String token) {
                    repository.getContent(url, token, new IServerCallbackContentCredit() {
                        @Override
                        public void onSuccess(Object obj) {
                            if (obj != null) {
                                
                                currentActivity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        course = (ContentModel) obj;
                                        setData();
                                        getPlayListFromContentByUrl(course.getSet().getContentUrl());
                                    }
                                });
                            } else {
                                Log.i(TAG, "getData-else-onSuccess: \n\r" + url + "object is null");
                            }
                        }
                        
                        @Override
                        public void onSuccessCredit(ContentCredit obj) {
                            if (obj != null) {
                                currentActivity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        course = obj.getContent();
                                        setData();
                                        showSnackBar(obj.getMessage());
                                        setProduct(obj.getProduct());
                                        getPlayListFromContentByUrl(course.getSet().getContentUrl());
                                    }
                                });
                            } else {
                                Log.i(TAG, "getData-else-onSuccessCredit: \n\r" + url +
                                           "object is null");
                            }
                        }
                        
                        @Override
                        public void onFailure(String message) {
                            Log.i(TAG, "onFailure:error " + message);
                        }
                    });
                }
                
                @Override
                public void nill() {
                    startActivity(new Intent(currentActivity, AuthenticatorActivity.class));
                }
            });
        }
    }
    
    private void setProduct(ArrayList<ProductModel> productModels) {
        
        // set adapter to recyclerView
        myRecyclerViewProduct.setVisibility(View.VISIBLE);
        myRecyclerViewProduct.setNestedScrollingEnabled(false);
        myRecyclerViewProduct.setHasFixedSize(true);
        myRecyclerViewProduct.setNestedScrollingEnabled(true);
        LinearLayoutManager
                layoutManager =
                new LinearLayoutManager(AppConfig.context, LinearLayoutManager.VERTICAL, false);
        myRecyclerViewProduct.setLayoutManager(layoutManager);
        adapter = new BlockAdapter(AppConfig.context, items);
        adapter.setSize(AppConfig.width, AppConfig.height);
        myRecyclerViewProduct.setAdapter(adapter);
        
        // setData
        BlockDataModel item = new BlockDataModel();
        item.setType(AppConstants.ITEM_PRODUCT);
        item.setProducts(productModels);
        items.add(item);
        
        adapter.notifyDataSetChanged();
    }
    
    private void getDataByUrl(String url) {
        
        if (loaderPlayList != null)
            loaderPlayList.setVisibility(View.VISIBLE);
        repository.getFilterTagsByUrl(url, new IServerCallbackObject() {
            @Override
            public void onSuccess(Object obj) {
                FilterModel filter = (FilterModel) obj;
                
                if (filter.getResult().getVideo() != null) {
                    ContentModels.addAll(filter.getResult().getVideo().getData());
                    pagination = filter.getResult().getVideo();
                    playListAdapter.notifyItemMoved(playListAdapter.getItemCount(),
                            ContentModels.size() - 1);
                }
                if (loaderPlayList != null)
                    loaderPlayList.setVisibility(View.GONE);
            }
            
            @Override
            public void onFailure(String message) {
                Log.i(TAG, "onSuccess:error " + message);
                if (loaderPlayList != null)
                    loaderPlayList.setVisibility(View.GONE);
            }
        });
    }
    
    private void getPlayListFromContentByUrl(String url) {
        
        if (loaderPlayList != null)
            loaderPlayList.setVisibility(View.VISIBLE);
        repository.getFilterTagsByUrl(url, new IServerCallbackObject() {
            @Override
            public void onSuccess(Object obj) {
                FilterModel filter = (FilterModel) obj;
                
                if (filter.getResult().getVideo() != null) {
                    
                    ContentModels = filter.getResult().getVideo().getData();
                    pagination = filter.getResult().getVideo();
                    
                    initPlayList();
                    // playListAdapter = new PlayListAdapter(getContext(), ContentModels);
                    // recyclerPlayList.setAdapter(playListAdapter);
                    
                    if (ContentModels.size() > 1) {
                        playListAdapter.notifyItemMoved(playListAdapter.getItemCount(),
                                ContentModels.size() - 1);
                    } else {
                        playListAdapter.notifyDataSetChanged();
                    }
                    if (loaderPlayList != null)
                        loaderPlayList.setVisibility(View.GONE);
                }
            }
            
            @Override
            public void onFailure(String message) {
                if (loaderPlayList != null)
                    loaderPlayList.setVisibility(View.GONE);
                Log.i(TAG, "onSuccess:error " + message);
            }
        });
    }
    
    @Override
    public void onClick(View view) {
        
        int i = view.getId();
        if (i == R.id.exo_play) {//manually change exoplayer button
            controlView.findViewById(R.id.exo_play).setVisibility(View.GONE);
            controlView.findViewById(R.id.exo_pause).setVisibility(View.VISIBLE);
            //play
            player.setPlayWhenReady(true);
            enableWakeLockScreen();
            
            
        } else if (i == R.id.exo_pause) {//manually change exoplayer button
            controlView.findViewById(R.id.exo_pause).setVisibility(View.GONE);
            controlView.findViewById(R.id.exo_play).setVisibility(View.VISIBLE);
            //pause
            player.setPlayWhenReady(false);
            disableWakeLockScreen();
            
            
        } else if (i == R.id.imgArrow) {
            if (!showPlayList) {
                recyclerPlayList.setVisibility(View.VISIBLE);
                animationRotate(imgArrow, 0, 180, 200);
                showPlayList = true;
            } else {
                recyclerPlayList.setVisibility(View.GONE);
                animationRotate(imgArrow, 180, 360, 200);
                showPlayList = false;
            }
            
        } else if (course != null) {
            List<FileDiskModel> fileDiskModels = course.getVideoFiles();
            if (i == R.id.imgDownload) {
                
                DownloadDialogFrg dialog = new DownloadDialogFrg();
                dialog.setData(fileDiskModels, course.getName(), (course.getIsFree() > 0))
                        .setComplete(new DownloadComplete() {
                            @Override
                            public void complete() {
                                imgDownload.setVisibility(View.GONE);
                                imgReady.setVisibility(View.VISIBLE);
                            }
                        })
                        .show(getFragmentManager(), "dialog");
            } else if (i == R.id.imgPlay) {
                ExistsOnSD.checkExistVideoToSD(fileDiskModels, new ExistsOnSD.BooleanCallback() {
                    @Override
                    public void onFalse() {
                        imgDownload.setVisibility(View.VISIBLE);
                        imgReady.setVisibility(View.GONE);
                    }
                    
                    @Override
                    public void onTrue(String path) {
                        imgDownload.setVisibility(View.GONE);
                        imgReady.setVisibility(View.VISIBLE);
                        mUrl = path;
                    }
                });
                
                startPlayer(mUrl);
                
                relativePreview.setVisibility(View.GONE);
                mediaVideoFrame.setVisibility(View.VISIBLE);
                mExoPlayerView.setVisibility(View.VISIBLE);
                
            } else if (i == R.id.imgReady) {
                
                if (fileDiskModels != null) {
                    (new DeleteFileDialogFrg())
                            .setFileDiskModels(fileDiskModels)
                            .setCallback(new DeleteFileDialogFrg.Callback() {
                                @Override
                                public void fileDeleted() {
                                    imgReady.setVisibility(View.GONE);
                                    imgDownload.setVisibility(View.VISIBLE);
                                }
                            })
                            .show(getFragmentManager(), "deleteFileDialogFrg");
                }
            } else if (i == R.id.imgShare) {
                
                String alla   = getResources().getString(R.string.alla_1);
                String title  = course.getName();
                String author = course.getAuthor().getFullName();
                String url    = course.getUrl();
                int    order  = course.getOrder();
                
                String
                        share =
                        String.format("%s \n\n %s \n\n %s جلسه %d \n\n %s", alla, title, author, order, url);
                
                Utils.share(share, context);
            }
        }
    }
    
    private void resume() {
        if (mExoPlayerFullscreen) {
            ((ViewGroup) mExoPlayerView.getParent()).removeView(mExoPlayerView);
            mFullScreenDialog.addContentView(mExoPlayerView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            mFullScreenIcon.setImageDrawable(ContextCompat.getDrawable(AppConfig.currentActivity, R.drawable.ic_fullscreen_skrink));
            mFullScreenDialog.show();
        }
        
        mResumeWindow = savedInsPlayer.getInt(STATE_RESUME_WINDOW);
        mResumePosition = savedInsPlayer.getLong(STATE_RESUME_POSITION);
        mExoPlayerFullscreen = savedInsPlayer.getBoolean(STATE_PLAYER_FULLSCREEN);
        
        if (player != null && mExoPlayerView.getPlayer() != null) {
            
            boolean haveResumePosition = mResumeWindow != C.INDEX_UNSET;
            
            if (haveResumePosition) {
                player.seekTo(mResumeWindow, mResumePosition);
            }
            Log.i(TAG, "resume: " + mResumePosition);
            player.setPlayWhenReady(true);
        }
    }
    
    private void pausePlayer() {
        
        if (savedInsPlayer != null) {
            
            if (mExoPlayerView != null && mExoPlayerView.getPlayer() != null) {
                mResumeWindow = mExoPlayerView.getPlayer().getCurrentWindowIndex();
                mResumePosition = Math.max(0, mExoPlayerView.getPlayer().getContentPosition());
                
                player.setPlayWhenReady(false);
            }
            savedInsPlayer.putInt(STATE_RESUME_WINDOW, mResumeWindow);
            savedInsPlayer.putLong(STATE_RESUME_POSITION, mResumePosition);
            savedInsPlayer.putBoolean(STATE_PLAYER_FULLSCREEN, mExoPlayerFullscreen);
            
            Log.i(TAG, "resume:pausePlayer " + mResumePosition);
        }
    }
    
    @Override
    public void onSaveInstanceState(Bundle outState) {
        
        outState.putInt(STATE_RESUME_WINDOW, mResumeWindow);
        outState.putLong(STATE_RESUME_POSITION, mResumePosition);
        outState.putBoolean(STATE_PLAYER_FULLSCREEN, mExoPlayerFullscreen);
        
        super.onSaveInstanceState(outState);
    }
    
    //<editor-fold desc="Player">
    
    private void animationRotate(View view, float of, float to, int duration) {
        
        ObjectAnimator imageViewObjectAnimator = ObjectAnimator.ofFloat(view,
                "rotation", of, to);
        imageViewObjectAnimator.setDuration(duration);
        imageViewObjectAnimator.setInterpolator(new AccelerateInterpolator());
        imageViewObjectAnimator.start();
    }
    
    private void setData() {
        
        //load info
        Utils.loadGlide(imgPreview,
                course.getThumbnail(),
                mExoPlayerView.getLayoutParams().width, mExoPlayerView.getLayoutParams().height);
        
        if (course.getAuthor() != null && course.getAuthor().getFullName() != null)
            txtAuthor.setText(course.getAuthor().getFullName());
        if (course.getName() != null)
            txtTitle.setText(course.getName());
        if (course.getDescription() != null)
            txtDesc.setText(Html.fromHtml(course.getDescription()));
        if (course.getTags() != null && course.getTags().getTags() != null)
            tagGroup.setTags(course.getTags().getTags());
        
        if (course != null && course.getFile() != null && course.getFile().getVideo() != null &&
            course.getFile().getVideo().size() > 0) {
        }
        tagGroup.setOnTagClickListener(new TagGroup.OnTagClickListener() {
            @Override
            public void onTagClick(String tag) {
                ArrayList<String> tags = new ArrayList<>();
                tags.add(tag);
                addFrg(FilterTagsFrg.newInstance(null, tags), "FilterTagsFrg");
            }
        });
        
        if (course.getFile() == null) {
            imgDownload.setVisibility(View.GONE);
            imgReady.setVisibility(View.GONE);
            imgPlay.setVisibility(View.GONE);
        }
        
        //select item
        playListAdapter.setItemSelect(positionPlaying);
        playListAdapter.notifyDataSetChanged();
        
        AppConfig.HANDLER.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    recyclerPlayList.smoothScrollToPosition(positionPlaying);
                }
                catch (Exception ex) {
                    Log.e(TAG, ex.getMessage());
                }
            }
        }, 500);
        
    }
    
    private void initView(View view, Bundle savedInstanceState) {
        
        if (savedInstanceState != null) {
            mResumeWindow = savedInstanceState.getInt(STATE_RESUME_WINDOW);
            mResumePosition = savedInstanceState.getLong(STATE_RESUME_POSITION);
            mExoPlayerFullscreen = savedInstanceState.getBoolean(STATE_PLAYER_FULLSCREEN);
        }
        if (mExoPlayerView == null) {
            
            mExoPlayerView = getView().findViewById(R.id.exoplayer);
            
            initFullscreenDialog();
            initFullscreenButton();
        }
        root = view.findViewById(R.id.root);
        myRecyclerViewProduct = view.findViewById(R.id.recyclerView);
        imgDownload = view.findViewById(R.id.imgDownload);
        imgReady = view.findViewById(R.id.imgReady);
        imgShare = view.findViewById(R.id.imgShare);
        mediaVideoFrame = view.findViewById(R.id.mediaVideoFrame);
        txtDesc = view.findViewById(R.id.txtDesc);
        txtTitle = view.findViewById(R.id.txt_title);
        txtAuthor = view.findViewById(R.id.txtAuthor);
        tagGroup = view.findViewById(R.id.tag_group);
        loader = view.findViewById(R.id.loader);
        loaderPlayList = view.findViewById(R.id.loaderPlayList);
        AppConfig.getInstance().changeProgressColor(loader);
        AppConfig.getInstance().changeProgressColor(loaderPlayList);
        
        //preview
        relativePreview = view.findViewById(R.id.relativePreview);
        imgPlay = view.findViewById(R.id.imgPlay);
        imgPreview = view.findViewById(R.id.imgPreview);
        
        //play list
        imgArrow = view.findViewById(R.id.imgArrow);
        recyclerPlayList = view.findViewById(R.id.recyclerPlayList);
        
        progressBarExoplaying = view.findViewById(R.id.progressBar);
        AppConfig.getInstance().changeProgressColor(progressBarExoplaying);
        
        //--------------play list
        initPlayList();
        
        resizePlayer();
        setRipple();
        
        imgDownload.setOnClickListener(this);
        imgReady.setOnClickListener(this);
        imgArrow.setOnClickListener(this);
        imgPlay.setOnClickListener(this);
        imgShare.setOnClickListener(this);
    }
    
    private void initPlayList() {
        playListAdapter = new PlayListAdapter(getContext(), ContentModels);
        managerPlayList =
                new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerPlayList.setLayoutManager(managerPlayList);
        recyclerPlayList.setAdapter(playListAdapter);
        playListAdapter.setOnClick(new OnItemClickListener() {
            @Override
            public void onItemClick(int position, Object item, View view, RecyclerView.ViewHolder vh) {
                
                // player.setPlayWhenReady(false);
                // releasePlayer();
                if (loader != null)
                    loader.setVisibility(View.VISIBLE);
                
                positionPlaying = position;
                course = (ContentModel) item;
                
                if (loader != null)
                    loader.setVisibility(View.GONE);
                playListAdapter.setItemSelect(positionPlaying);
                setData();
                
                if (mExoPlayerFullscreen) {
                    ((ViewGroup) mExoPlayerView.getParent()).removeView(mExoPlayerView);
                    mFullScreenDialog.addContentView(mExoPlayerView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                    mFullScreenIcon.setImageDrawable(ContextCompat.getDrawable(AppConfig.currentActivity, R.drawable.ic_fullscreen_skrink));
                    mFullScreenDialog.show();
                }
                
                if (isPlaying()) {
                    AppConfig.HANDLER.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mResumePosition = 0;
                            boolean haveResumePosition = mResumeWindow != C.INDEX_UNSET;
                            if (haveResumePosition) {
                                player.seekTo(mResumeWindow, mResumePosition);
                            }
                            final String userAgent = Util.getUserAgent(context, "ExoPlayer");
                            player.seekTo(0);
                            player.setPlayWhenReady(true);
                            
                            if (mUrl.contains("cdn")) {
                                DataSource.Factory
                                        dataSourceFactory =
                                        new DefaultDataSourceFactory(context, userAgent);
                                
                                mVideoSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                                        .createMediaSource(Uri.parse(mUrl));
                                player.prepare(mVideoSource, !haveResumePosition, false);
                            } else {
                                AuthToken.getInstant().get(context, currentActivity, new AuthToken.Callback() {
                                    
                                    public void initExo(String token) {
                                        DefaultHttpDataSourceFactory
                                                httpDataSourceFactory =
                                                new DefaultHttpDataSourceFactory(userAgent, null);
                                        if (token != null)
                                            httpDataSourceFactory.getDefaultRequestProperties().set("Authorization",
                                                    "Bearer " + token);
                                        DataSource.Factory
                                                dataSourceFactory =
                                                new DefaultDataSourceFactory(context, null, httpDataSourceFactory);
                                        
                                        mVideoSource =
                                                new ExtractorMediaSource.Factory(dataSourceFactory)
                                                        .createMediaSource(Uri.parse(mUrl));
                                        player.prepare(mVideoSource, !haveResumePosition, false);
                                    }
                                    
                                    @Override
                                    public void run(@NonNull String token) {
                                        Log.i(TAG, "startPlayer, has_token");
                                        currentActivity.runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                initExo(token);
                                            }
                                        });
                                    }
                                    
                                    @Override
                                    public void nill() {
                                        Log.i(TAG, "startPlayer, without_token");
                                        initExo(null);
                                    }
                                });
                            }
                            
                        }
                    }, 200);
                    
                }
            }
        });
        endLess = new EndlessRecyclerViewScrollListener(managerPlayList) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                
                if (pagination != null && pagination.getNextPageUrl() != null) {
                    getDataByUrl(pagination.getNextPageUrl());
                }
            }
        };
        if (recyclerPlayList != null)
            recyclerPlayList.addOnScrollListener(endLess);
    }
    
    private void resizePlayer() {
        
        int h = (int) (AppConfig.width * 0.56);
        mediaVideoFrame.getLayoutParams().height = h;
        mediaVideoFrame.getLayoutParams().width = AppConfig.width;
        
        mExoPlayerView.getLayoutParams().height = h;
        mExoPlayerView.getLayoutParams().width = AppConfig.width;
        
        relativePreview.getLayoutParams().height = h;
        relativePreview.getLayoutParams().width = AppConfig.width;
    }
    
    private void initFullscreenDialog() {
        
        mFullScreenDialog =
                new Dialog(getActivity(), android.R.style.Theme_Black_NoTitleBar_Fullscreen) {
                    public void onBackPressed() {
                        if (mExoPlayerFullscreen)
                            closeFullscreenDialog();
                        super.onBackPressed();
                    }
                };
    }
    
    private void openFullscreenDialog() {
        
        portrait_To_landscape();
        ((ViewGroup) mExoPlayerView.getParent()).removeView(mExoPlayerView);
        mFullScreenDialog.addContentView(mExoPlayerView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mFullScreenIcon.setImageDrawable(ContextCompat.getDrawable(AppConfig.currentActivity, R.drawable.ic_fullscreen_skrink));
        mExoPlayerFullscreen = true;
        mFullScreenDialog.show();
        
    }
    
    public void closeFullscreenDialog() {
        
        landscape_To_portrait();
        ((ViewGroup) mExoPlayerView.getParent()).removeView(mExoPlayerView);
        ((FrameLayout) getView().findViewById(R.id.mediaVideoFrame)).addView(mExoPlayerView);
        mExoPlayerFullscreen = false;
        mFullScreenDialog.dismiss();
        mFullScreenIcon.setImageDrawable(ContextCompat.getDrawable(AppConfig.currentActivity, R.drawable.ic_fullscreen_expand));
        
    }
    
    private boolean isPlaying() {
        return player != null
               && player.getPlaybackState() != Player.STATE_ENDED
               && player.getPlaybackState() != Player.STATE_IDLE
               && player.getPlayWhenReady();
    }
    
    private void initFullscreenButton() {
        
        controlView = mExoPlayerView.findViewById(R.id.exo_controller);
        mFullScreenIcon = controlView.findViewById(R.id.exo_fullscreen_icon);
        mFullScreenButton = controlView.findViewById(R.id.exo_fullscreen_button);
        txtQuality = controlView.findViewById(R.id.txtQuality);
        mFullScreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mExoPlayerFullscreen)
                    openFullscreenDialog();
                else
                    closeFullscreenDialog();
            }
        });
        
        controlView.findViewById(R.id.exo_play).setOnClickListener(this);
        controlView.findViewById(R.id.exo_pause).setOnClickListener(this);
        
        mExoPlayerView.setControllerVisibilityListener(new PlayerControlView.VisibilityListener() {
            @Override
            public void onVisibilityChange(final int visibility) {
                
                if (visibility == View.GONE) {
                    
                    mExoPlayerView.showController();
                    controlView.animate().alpha(0F).setDuration(500L).setListener(new AnimatorListenerAdapter() {
                        
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            controlView.setVisibility(View.GONE);
                        }
                    });
                } else if (visibility == View.VISIBLE) {
                    
                    controlView.animate().alpha(1F).setDuration(500L).setListener(new AnimatorListenerAdapter() {
                        
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            controlView.setVisibility(View.VISIBLE);
                        }
                    });
                }
            }
        });
        
        // ripple(controlView.findViewById(R.id.exo_play),24);
        // ripple(controlView.findViewById(R.id.exo_pause),24);
    }
    
    private void initExoPlayer() {
        
        //estimating available network bandwidth based on measured download speed
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory
                videoTrackSelectionFactory =
                new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector
                trackSelector =
                new DefaultTrackSelector(videoTrackSelectionFactory);
        LoadControl loadControl = new DefaultLoadControl();
        
        player = ExoPlayerFactory.newSimpleInstance(getContext(),
                new DefaultRenderersFactory(getContext()),
                trackSelector,
                loadControl);
        
        mExoPlayerView.setPlayer(player);
    }
    
    private void startPlayer(String mUrl) {
        boolean haveResumePosition = mResumeWindow != C.INDEX_UNSET;
        
        if (haveResumePosition) {
            player.seekTo(mResumeWindow, mResumePosition);
        }
        player.addListener(eventListener);
        player.setPlayWhenReady(true);
        final String userAgent = Util.getUserAgent(context, "ExoPlayer");
        if (mUrl.contains("cdn")) {
            DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(context, userAgent);
            
            mVideoSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(Uri.parse(mUrl));
            player.prepare(mVideoSource, !haveResumePosition, false);
        } else {
            AuthToken.getInstant().get(context, currentActivity, new AuthToken.Callback() {
                public void initExo(String token) {
                    DefaultHttpDataSourceFactory
                            httpDataSourceFactory =
                            new DefaultHttpDataSourceFactory(userAgent, null);
                    if (token != null)
                        httpDataSourceFactory.getDefaultRequestProperties().set("Authorization",
                                "Bearer " + token);
                    DataSource.Factory
                            dataSourceFactory =
                            new DefaultDataSourceFactory(context, null, httpDataSourceFactory);
                    
                    mVideoSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                            .createMediaSource(Uri.parse(mUrl));
                    player.prepare(mVideoSource, !haveResumePosition, false);
                }
                
                @Override
                public void run(@NonNull String token) {
                    Log.i(TAG, "startPlayer, has_token");
                    
                    currentActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            initExo(token);
                        }
                    });
                }
                
                @Override
                public void nill() {
                    Log.i(TAG, "startPlayer, without_token");
                    initExo(null);
                }
            });
        }
    }
    
    //</editor-fold>
    
    private void landscape_To_portrait() {
        
        if (getActivity().getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }
    
    private void portrait_To_landscape() {
        
        if (getActivity().getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }
    
    private void releasePlayer() {
        
        if (mExoPlayerView != null && mExoPlayerView.getPlayer() != null) {
            mResumeWindow = mExoPlayerView.getPlayer().getCurrentWindowIndex();
            mResumePosition = Math.max(0, mExoPlayerView.getPlayer().getContentPosition());
            mExoPlayerView.getPlayer().release();
        }
        
        if (mFullScreenDialog != null)
            mFullScreenDialog.dismiss();
        
        
    }
    
    private void setRipple() {
        
        ripple(imgArrow, 24);
        // ripple(imgDownload, 24);
        // ripple(imgReady, 24);
        ripple(imgShare, 24);
        
    }
    //</editor-fold>
    
    public String toString(String caption, String title) {
        return String.format("%s - %s", caption, title);
    }
    
    //<editor-fold desc="lock">
    @SuppressLint("InvalidWakeLockTag")
    private void initWakeLockScreen() {
        pm = (PowerManager) getContext().getSystemService(POWER_SERVICE);
        wl = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK
                            | PowerManager.ACQUIRE_CAUSES_WAKEUP
                            | PowerManager.ON_AFTER_RELEASE, "alla");
        
        
        km = (KeyguardManager) getContext().getSystemService(KEYGUARD_SERVICE);
        if (null != km) {
            kl = km.newKeyguardLock("alla");
        }
    }
    
    private void enableWakeLockScreen() {
        wl.acquire();
        kl.reenableKeyguard();
    }
    //</editor-fold>
    
    private void disableWakeLockScreen() {
        if (wl.isHeld())
            wl.release();
        kl.disableKeyguard();
    }
    //</editor-fold>
    
    private void showSnackBar(String message) {
        try {
            
            Snackbar snack = Snackbar.make(root, message, Snackbar.LENGTH_LONG);
            View     view  = snack.getView();
            TextView tv    = view.findViewById(com.google.android.material.R.id.snackbar_text);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            } else {
                tv.setGravity(Gravity.CENTER_HORIZONTAL);
            }
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
            params.gravity = Gravity.BOTTOM;
            view.setLayoutParams(params);
            snack.show();
        }
        catch (Exception ex) {
            Log.e(TAG, "start-showSnackBar" + ex.getMessage());
        }
    }
}
