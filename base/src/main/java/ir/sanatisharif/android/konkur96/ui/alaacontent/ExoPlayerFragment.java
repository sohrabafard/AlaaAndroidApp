package ir.sanatisharif.android.konkur96.ui.alaacontent;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.activity.ActivityBase;
import ir.sanatisharif.android.konkur96.ui.view.MDToast;


public class ExoPlayerFragment extends Fragment {
    
    private static final String             KEY_WINDOW    = "window";
    private static final String             KEY_POSITION  = "position";
    private static final String             KEY_AUTO_PLAY = "auto_play";
    private              String             TAG           = "Alaa\\ExoFrg";
    private              ExoPlayerViewModel mViewModel;
    private              Activity           mActivity;
    private              SimpleExoPlayer    mPlayer;
    Player.EventListener eventListener = new Player.EventListener() {
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
                    
                    break;
                case Player.STATE_BUFFERING:  // The player is buffering (loading the mContent)
                    
                    break;
                case Player.STATE_READY:      // The player is able to immediately play
                    
                    break;
                case Player.STATE_ENDED:      // The player has finished playing the media
                    
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
                    mPlayer.stop();
                    break;
                
                case ExoPlaybackException.TYPE_RENDERER:
                    Log.e(TAG, "TYPE_RENDERER: " + error.getRendererException().getMessage());
                    ActivityBase.toastShow("خطا در خواندن ویدیو", MDToast.TYPE_ERROR);
                    mPlayer.stop();
                    break;
                
                case ExoPlaybackException.TYPE_UNEXPECTED:
                    Log.e(TAG, "TYPE_UNEXPECTED: " + error.getUnexpectedException().getMessage());
                    mPlayer.stop();
                    break;
                case ExoPlaybackException.TYPE_OUT_OF_MEMORY:
                    ActivityBase.toastShow("خطا اتمام حافظه", MDToast.TYPE_ERROR);
                    mPlayer.stop();
                    break;
                case ExoPlaybackException.TYPE_REMOTE:
                    ActivityBase.toastShow("خطا", MDToast.TYPE_ERROR);
                    mPlayer.stop();
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
    private PlayerView playerView;
    private boolean    startAutoPlay;
    private int        startWindow;
    private long       startPosition;
    
    public static ExoPlayerFragment newInstance() {
        return new ExoPlayerFragment();
    }
    
    private void initExoPlayer() {
        mPlayer =
                ExoPlayerFactory.newSimpleInstance(mActivity, new DefaultTrackSelector(), new DefaultLoadControl());
        playerView.setPlayer(mPlayer);
        mPlayer.addListener(eventListener);
        mPlayer.setPlayWhenReady(true);
        
    }
    
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.exo_player_fragment, container, false);
        if (playerView == null) {
            playerView = view.findViewById(R.id.exo_player_in_fragment);
        }
        if (playerView == null) {
            initExoPlayer();
        }
        
        if (savedInstanceState != null) {
            startAutoPlay = savedInstanceState.getBoolean(KEY_AUTO_PLAY);
            startWindow = savedInstanceState.getInt(KEY_WINDOW);
            startPosition = savedInstanceState.getLong(KEY_POSITION);
        } else {
            clearStartPosition();
        }
        return view;
    }
    
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setActivity();
        mViewModel = ViewModelProviders.of(this).get(ExoPlayerViewModel.class);
        mViewModel.getUrl().observe(this, this::initExoPlayerDataSource);
    }
    
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        
        updateStartPosition();
        outState.putBoolean(KEY_AUTO_PLAY, startAutoPlay);
        outState.putInt(KEY_WINDOW, startWindow);
        outState.putLong(KEY_POSITION, startPosition);
    }
    
    private void initExoPlayerDataSource(String url) {
        String userAgent = Util.getUserAgent(mActivity, "ExoPlayer");
        
        DefaultHttpDataSourceFactory
                httpDataSourceFactory =
                new DefaultHttpDataSourceFactory(userAgent, null);
        
        String token = mViewModel.getToken();
        if (token != null) {
            httpDataSourceFactory.getDefaultRequestProperties().set("Authorization", (
                    "Bearer " + token));
        }
        // Produces DataSource instances through which media data is loaded.
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(mActivity,
                Util.getUserAgent(mActivity, userAgent));
        
        MediaSource
                videoSource =
                new ProgressiveMediaSource.Factory(dataSourceFactory)
                        .createMediaSource(Uri.parse(url));
        
        
        boolean haveStartPosition = startWindow != C.INDEX_UNSET;
        if (haveStartPosition) {
            mPlayer.seekTo(startWindow, startPosition);
        }
        mPlayer.prepare(videoSource, !haveStartPosition, false);
    }
    
    private void updateStartPosition() {
        if (mPlayer != null) {
            startAutoPlay = mPlayer.getPlayWhenReady();
            startWindow = mPlayer.getCurrentWindowIndex();
            startPosition = Math.max(0, mPlayer.getContentPosition());
        }
    }
    
    private void clearStartPosition() {
        startAutoPlay = true;
        startWindow = C.INDEX_UNSET;
        startPosition = C.TIME_UNSET;
    }
    
    private void setActivity() {
        if (mActivity == null)
            mActivity = getActivity();
    }
    
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        setActivity();
    }
    
    @Override
    public void onStart() {
        super.onStart();
        
        initWakeLockScreen();
        if (Util.SDK_INT > 23) {
            initExoPlayer();
            if (playerView != null) {
                playerView.onResume();
            }
        }
    }
    
    @Override
    public void onResume() {
        super.onResume();
        if (Util.SDK_INT <= 23 || mPlayer == null) {
            initExoPlayer();
            if (playerView != null) {
                playerView.onResume();
            }
        }
    }
    
    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            if (playerView != null) {
                playerView.onPause();
            }
            mPlayer.release();
        }
    }
    
    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            if (playerView != null) {
                playerView.onPause();
            }
            mPlayer.release();
        }
    }
    
    @SuppressLint("InvalidWakeLockTag")
    private void initWakeLockScreen() {
        //TODO:: wakeLockScreen
    }
    
    @Override
    public void onDestroyView() {
        mPlayer.release();
        mViewModel.setPositionPlaying(0);
        super.onDestroyView();
    }
}
