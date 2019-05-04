package ir.sanatisharif.android.konkur96.utils;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.BehindLiveWindowException;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.fragment.VideoPlayFrg;

public class VideoPlayerComponent implements LifecycleObserver {

    private static final String TAG = "VideoPlayerComponent";

    private final String STATE_RESUME_WINDOW = "resumeWindow";
    private final String STATE_RESUME_POSITION = "resumePosition";
    private final String STATE_PLAYER_FULLSCREEN = "playerFullscreen";
    private final Context context;
    private final SimpleExoPlayerView mExoPlayerView;
    private final String videoUrl;
    private int resumeWindow;
    private long resumePosition;

    private SimpleExoPlayer player;
    private DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
    private AdaptiveTrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
    private DefaultTrackSelector trackSelector;

    VideoPlayerComponent(Context context, SimpleExoPlayerView simpleExoPlayerView, String videoUrl) {
        this.context = context;
        this.mExoPlayerView = simpleExoPlayerView;
        this.videoUrl = videoUrl;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate() {
        clearResumePosition();
       // simpleExoPlayerView.requestFocus();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    void onStart() {
        if (Util.SDK_INT > 23) {
            initExoPlayer();
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    void onStop() {
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    void onPause() {
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    private void releasePlayer() {
        if (player != null) {
            updateResumePosition();
            player.release();
            player = null;
            trackSelector = null;
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    void onResume() {
        if ((Util.SDK_INT <= 23)) {
            initExoPlayer();
        }
    }


//    private void initializePlayer() {
//        if (player == null) {
//            trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
//            player = ExoPlayerFactory.newSimpleInstance(context, trackSelector);
//            DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(context,
//                    Util.getUserAgent(context, "testApp"), bandwidthMeter);
//
//            ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
//            ExtractorMediaSource videoSource = new ExtractorMediaSource(Uri.parse(videoUrl),
//                    dataSourceFactory, extractorsFactory, null, null);
//            simpleExoPlayerView.setPlayer(player);
//            player.setPlayWhenReady(true);
//
//            boolean haveResumePosition = resumeWindow != C.INDEX_UNSET;
//            if (haveResumePosition) {
//                Log.d(TAG, "Have Resume position true!" + resumePosition);
//                player.seekTo(resumeWindow, resumePosition);
//            }
//
//            player.prepare(videoSource, !haveResumePosition, false);
//
//        }
//    }

    private void updateResumePosition() {
        resumeWindow = player.getCurrentWindowIndex();
        resumePosition = player.isCurrentWindowSeekable() ? Math.max(0, player.getCurrentPosition())
                : C.TIME_UNSET;
    }

    private void clearResumePosition() {
        resumeWindow = C.INDEX_UNSET;
        resumePosition = C.TIME_UNSET;
    }


    private void showToast(String message) {
        Toast.makeText(context.getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }


    private static boolean isBehindLiveWindow(ExoPlaybackException e) {
        if (e.type != ExoPlaybackException.TYPE_SOURCE) {
            return false;
        }
        Throwable cause = e.getSourceException();
        while (cause != null) {
            if (cause instanceof BehindLiveWindowException) {
                return true;
            }
            cause = cause.getCause();
        }
        return false;
    }

    private void initExoPlayer() {

//        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
//        TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
//        TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
//        LoadControl loadControl = new DefaultLoadControl();
//
//        player = ExoPlayerFactory.newSimpleInstance(new DefaultRenderersFactory(getContext()), trackSelector, loadControl);
//        mExoPlayerView.setPlayer(player);
//
//        boolean haveResumePosition = mResumeWindow != C.INDEX_UNSET;
//        Log.i("LOG", "onPause: savedInsPlayer2 " + mResumePosition);
//        if (haveResumePosition) {
//            player.seekTo(mResumeWindow, mResumePosition);
//        }
//
//        player.addListener(new VideoPlayFrg.PlayerEventListener());
//        player.prepare(mVideoSource, !haveResumePosition, false);
//        player.setPlayWhenReady(true);
    }


}
