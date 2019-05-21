package ir.sanatisharif.android.konkur96.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.KeyguardManager;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
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

import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.app.AppConfig;
import ir.sanatisharif.android.konkur96.utils.AuthToken;

import static android.content.Context.KEYGUARD_SERVICE;
import static android.content.Context.POWER_SERVICE;
import static ir.sanatisharif.android.konkur96.app.AppConfig.context;

/**
 * Created by Mohamad on 11/17/2018.
 */

public class VideoPlayFrg extends BaseFragment {


    private static final String TAG = "Alaa\\VideoPlayFrag";
    private final String STATE_RESUME_WINDOW = "resumeWindow";
    private final String STATE_RESUME_POSITION = "resumePosition";
    private final String STATE_PLAYER_FULLSCREEN = "playerFullscreen";
    ProgressBar progressBar;
    LifecycleRegistry mLifecycleRegistry;
    PlaybackControlView controlView;
    //lock
    PowerManager pm;
    PowerManager.WakeLock wl;
    KeyguardManager km;
    KeyguardManager.KeyguardLock kl;
    VideoPlayer videoPlayer;
    //<editor-fold desc="animation">
    Animator.AnimatorListener animatorHide = new Animator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animator) {

        }

        @Override
        public void onAnimationEnd(Animator animator) {
            controlView.findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
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
    private Bundle savedInsPlayer;
    private SimpleExoPlayer player;
    private SimpleExoPlayerView mExoPlayerView;
    private MediaSource mVideoSource;
    private boolean mExoPlayerFullscreen = false;
    private FrameLayout mFullScreenButton;
    private ImageView mFullScreenIcon;
    private Dialog mFullScreenDialog;
    //private ImageButton imgPlay, imgPause;
    private int mResumeWindow;
    private long mResumePosition;
    private String mUrl;

    public static VideoPlayFrg newInstance(String path) {

        Bundle args = new Bundle();
        args.putString("path", path);
        VideoPlayFrg fragment = new VideoPlayFrg();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initWakeLockScreen();
        // Fragment locked in landscape screen orientation
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        //  startPlayer();
        // getLifecycle().addObserver(new VideoPlayerComponent(getContext(), mExoPlayerView, videoUrl));
        videoPlayer = new VideoPlayer();
        getLifecycle().addObserver(videoPlayer);
    }

    @Override
    public View createFragmentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_video_play, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        savedInsPlayer = new Bundle();
        if (savedInstanceState != null) {
            mResumeWindow = savedInstanceState.getInt(STATE_RESUME_WINDOW);
            mResumePosition = savedInstanceState.getLong(STATE_RESUME_POSITION);
            mExoPlayerFullscreen = savedInstanceState.getBoolean(STATE_PLAYER_FULLSCREEN);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        outState.putInt(STATE_RESUME_WINDOW, mResumeWindow);
        outState.putLong(STATE_RESUME_POSITION, mResumePosition);
        outState.putBoolean(STATE_PLAYER_FULLSCREEN, mExoPlayerFullscreen);
        super.onSaveInstanceState(outState);
    }

    private void initFullscreenDialog() {

        mFullScreenDialog = new Dialog(AppConfig.currentActivity, android.R.style.Theme_Black_NoTitleBar_Fullscreen) {
            public void onBackPressed() {
                if (mExoPlayerFullscreen)
                    closeFullscreenDialog();
                super.onBackPressed();
            }
        };
    }

    private void openFullscreenDialog() {

        ((ViewGroup) mExoPlayerView.getParent()).removeView(mExoPlayerView);
        mFullScreenDialog.addContentView(mExoPlayerView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mFullScreenIcon.setImageDrawable(ContextCompat.getDrawable(AppConfig.currentActivity, R.drawable.ic_fullscreen_skrink));
        mExoPlayerFullscreen = true;
        mFullScreenDialog.show();
    }

    private void closeFullscreenDialog() {

        ((ViewGroup) mExoPlayerView.getParent()).removeView(mExoPlayerView);
        ((FrameLayout) getView().findViewById(R.id.mediaVideoFrame)).addView(mExoPlayerView);
        mExoPlayerFullscreen = false;
        mFullScreenDialog.dismiss();
        mFullScreenIcon.setImageDrawable(ContextCompat.getDrawable(AppConfig.currentActivity, R.drawable.ic_fullscreen_expand));
    }

    private void initFullscreenButton() {

        controlView = mExoPlayerView.findViewById(R.id.exo_controller);
        mFullScreenIcon = controlView.findViewById(R.id.exo_fullscreen_icon);
        mFullScreenButton = controlView.findViewById(R.id.exo_fullscreen_button);
        progressBar = getView().findViewById(R.id.progressBar);

        mFullScreenButton.setVisibility(View.GONE);

        mFullScreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mExoPlayerFullscreen)
                    openFullscreenDialog();
                else
                    closeFullscreenDialog();
            }
        });


        controlView.findViewById(R.id.exo_play).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //manually change exoplayer button
                controlView.findViewById(R.id.exo_play).setVisibility(View.GONE);
                controlView.findViewById(R.id.exo_pause).setVisibility(View.VISIBLE);
                //play
                player.setPlayWhenReady(true);
                enableWakeLockScreen();
            }
        });

        controlView.findViewById(R.id.exo_pause).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //manually change exoplayer button
                controlView.findViewById(R.id.exo_pause).setVisibility(View.GONE);
                controlView.findViewById(R.id.exo_play).setVisibility(View.VISIBLE);
                //pause
                player.setPlayWhenReady(false);
                disableWakeLockScreen();
            }
        });


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
    }

    private void initExoPlayer() {

        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
        LoadControl loadControl = new DefaultLoadControl();

        player = ExoPlayerFactory.newSimpleInstance(new DefaultRenderersFactory(getContext()), trackSelector, loadControl);
        mExoPlayerView.setPlayer(player);

        boolean haveResumePosition = mResumeWindow != C.INDEX_UNSET;
        Log.i("LOG", "onPause: savedInsPlayer2 " + mResumePosition);
        if (haveResumePosition) {
            player.seekTo(mResumeWindow, mResumePosition);
        }

        player.addListener(new PlayerEventListener());
        player.prepare(mVideoSource, !haveResumePosition, false);
        player.setPlayWhenReady(true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getLifecycle().removeObserver(videoPlayer);
    }

    private void resume() {
        Log.i("LOG", "onPause: savedInsPlayer2 " + mResumePosition);
        mResumeWindow = savedInsPlayer.getInt(STATE_RESUME_WINDOW);
        mResumePosition = savedInsPlayer.getLong(STATE_RESUME_POSITION);
        mExoPlayerFullscreen = savedInsPlayer.getBoolean(STATE_PLAYER_FULLSCREEN);

        Log.i("LOG", "onPause: savedInsPlayer3 " + mResumePosition);
        if (player != null && mExoPlayerView.getPlayer() != null) {

            boolean haveResumePosition = mResumeWindow != C.INDEX_UNSET;

            if (haveResumePosition) {
                player.seekTo(mResumeWindow, mResumePosition);
            }

            player.addListener(new PlayerEventListener());
            player.prepare(mVideoSource, !haveResumePosition, false);
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
            Log.i("LOG", "onPause: savedInsPlayer1 " + mResumePosition);
        }
    }

    private void startPlayer() {
        if (mExoPlayerView == null) {

            mExoPlayerView = getView().findViewById(R.id.exoplayer);

            mUrl = getArguments().getString("path");
            initFullscreenDialog();
            initFullscreenButton();
            String userAgent = Util.getUserAgent(context, "ExoPlayer");
            if (mUrl.contains("cdn")) {
                DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(context, userAgent);

                mVideoSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                        .createMediaSource(Uri.parse(mUrl));
            } else {
                AuthToken.getInstant().get(context, getActivity(), token -> {
                    Log.i(TAG, "startPlayer, has_token: " + (token != null));

                    DefaultHttpDataSourceFactory httpDataSourceFactory = new DefaultHttpDataSourceFactory(userAgent, null);
                    httpDataSourceFactory.getDefaultRequestProperties().set("Authorization", "Bearer " + token);
                    DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(context, null, httpDataSourceFactory);

                    mVideoSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                            .createMediaSource(Uri.parse(mUrl));
                });
            }
        }


        if (mExoPlayerFullscreen) {
            ((ViewGroup) mExoPlayerView.getParent()).removeView(mExoPlayerView);
            mFullScreenDialog.addContentView(mExoPlayerView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            mFullScreenIcon.setImageDrawable(ContextCompat.getDrawable(AppConfig.currentActivity, R.drawable.ic_fullscreen_skrink));
            mFullScreenDialog.show();
        }
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

    private void clearResumePosition() {
        mResumeWindow = C.INDEX_UNSET;
        mResumePosition = C.TIME_UNSET;
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

    public class VideoPlayer implements LifecycleObserver {

//        @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
//        public void play() {
//            startPlayer();
//            Log.i("LOG", "VideoPlayFrg: ON_RESUME");
//        }
//
//        @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
//        public void pause() {
//            Log.i("LOG", "VideoPlayFrg: ON_PAUSE");
//            pausePlayer();
//        }
//
//        @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
//        public void stop() {
//            releasePlayer();
//            Log.i("LOG", "VideoPlayFrg: ON_STOP");
//            //stop logic
//        }

        @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
        void onCreate() {
            Log.i("LOG", "VideoPlayFrg: ON_CREATE");
            clearResumePosition();
            // mExoPlayerView.requestFocus();
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_START)
        void onStart() {
            if (Util.SDK_INT > 23) {
                Log.i("LOG", "VideoPlayFrg: ON_START");
                startPlayer();
            }
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
        void onStop() {
            if (Util.SDK_INT > 23) {
                Log.i("LOG", "VideoPlayFrg: ON_STOP");
                releasePlayer();
            }
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
        void onPause() {
            if (Util.SDK_INT <= 23) {
                Log.i("LOG", "VideoPlayFrg: ON_PAUSE");
                releasePlayer();
            }
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
        void onResume() {
            if ((Util.SDK_INT <= 23)) {
                Log.i("LOG", "VideoPlayFrg: ON_RESUME");
                initExoPlayer();
            }
        }
    }

    private class PlayerEventListener extends Player.DefaultEventListener {

        @Override
        public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {

            switch (playbackState) {
                case Player.STATE_IDLE:       // The player does not have any media to play yet.
                    controlView.findViewById(R.id.progressBar);
                    break;
                case Player.STATE_BUFFERING:  // The player is buffering (loading the content)

                    controlView.findViewById(R.id.exo_play).animate().alpha(0F).setDuration(400).setListener(animatorHide);
                    controlView.findViewById(R.id.exo_pause).animate().alpha(0F).setDuration(400).setListener(animatorHide);

                    break;
                case Player.STATE_READY:      // The player is able to immediately play

                    progressBar.setVisibility(View.GONE);
                    controlView.findViewById(R.id.exo_play).animate().alpha(1).setDuration(400).setListener(animatorShow);
                    controlView.findViewById(R.id.exo_pause).animate().alpha(1).setDuration(400).setListener(animatorShow);

                    break;
                case Player.STATE_ENDED:      // The player has finished playing the media

                    progressBar.setVisibility(View.GONE);

                    break;
            }
        }
    }
    //</editor-fold>
}
