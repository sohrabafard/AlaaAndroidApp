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
import android.content.Intent;
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
import ir.sanatisharif.android.konkur96.account.AuthenticatorActivity;
import ir.sanatisharif.android.konkur96.app.AppConfig;
import ir.sanatisharif.android.konkur96.utils.AuthToken;

import static android.content.Context.KEYGUARD_SERVICE;
import static android.content.Context.POWER_SERVICE;
import static ir.sanatisharif.android.konkur96.app.AppConfig.context;
import static ir.sanatisharif.android.konkur96.app.AppConfig.currentActivity;

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

    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG, "onStart: onStart");
        if (Util.SDK_INT > 23) {
            if (player == null)
                initExoPlayer();
            else {
                player.setPlayWhenReady(false);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Util.SDK_INT <= 23) {
            if (player == null) {
                initExoPlayer();
            } else {
                player.setPlayWhenReady(false);
                Log.i(TAG, "onStart:onResume 11");
            }
        }
        Log.i(TAG, "onStart:onResume ");
    }

    @Override
    public void onStop() {
        super.onStop();
        //  getActivity().unregisterReceiver(phoneStateReceiver);
        if (Util.SDK_INT > 23) {
            player.setPlayWhenReady(false);
            Log.i(TAG, "onStart:onStop ");
        }
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
        return inflater.inflate(R.layout.fragment_video_play, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        savedInsPlayer = new Bundle();
        initUI();
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

        String userAgent = Util.getUserAgent(context, "ExoPlayer");
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(context, userAgent);

        mUrl = getArguments().getString("path");
        mVideoSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                .createMediaSource(Uri.parse(mUrl));

        boolean haveResumePosition = mResumeWindow != C.INDEX_UNSET;
        if (haveResumePosition) {
            player.seekTo(mResumeWindow, mResumePosition);
        }

        player.addListener(new PlayerEventListener());
        player.prepare(mVideoSource, !haveResumePosition, false);
        player.setPlayWhenReady(true);
    }


    private void initUI() {
        if (mExoPlayerView == null) {

            mExoPlayerView = getView().findViewById(R.id.exoplayer);

            Log.i(TAG, "startPlayer: " + mUrl);
            initFullscreenDialog();
            initFullscreenButton();
        }
    }

    private void startPlayer() {
        if (mExoPlayerView == null) {

            mExoPlayerView = getView().findViewById(R.id.exoplayer);

            Log.i(TAG, "startPlayer: " + mUrl);
            initFullscreenDialog();
            initFullscreenButton();
            String userAgent = Util.getUserAgent(context, "ExoPlayer");
            DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(context, userAgent);


            if (mUrl.contains("cdn")) {
                mVideoSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                        .createMediaSource(Uri.parse(mUrl));
            } else {

                AuthToken.getInstant().get(context, currentActivity, new AuthToken.Callback() {
                    @Override
                    public void run(@NonNull String token) {
                        currentActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                DefaultHttpDataSourceFactory httpDataSourceFactory = new DefaultHttpDataSourceFactory(userAgent, null);
                                httpDataSourceFactory.getDefaultRequestProperties().set("Authorization", "Bearer " + token);
                                DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(context, null, httpDataSourceFactory);

                                Log.i(TAG, "run: asaas");
                                mVideoSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                                        .createMediaSource(Uri.parse(mUrl));
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
