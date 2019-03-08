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
import android.os.Bundle;
import android.os.PowerManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

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
import com.google.android.exoplayer2.util.Util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.adapter.MainItemAdapter;
import ir.sanatisharif.android.konkur96.adapter.PlayListAdapter;
import ir.sanatisharif.android.konkur96.api.MainApi;
import ir.sanatisharif.android.konkur96.app.AppConfig;
import ir.sanatisharif.android.konkur96.dialog.DeleteFileDialogFrg;
import ir.sanatisharif.android.konkur96.dialog.DownloadDialogFrg;
import ir.sanatisharif.android.konkur96.helper.FileManager;
import ir.sanatisharif.android.konkur96.listener.DownloadComplete;
import ir.sanatisharif.android.konkur96.listener.OnItemClickListener;
import ir.sanatisharif.android.konkur96.listener.api.IServerCallbackObject;
import ir.sanatisharif.android.konkur96.model.DataCourse;
import ir.sanatisharif.android.konkur96.model.MainItem;
import ir.sanatisharif.android.konkur96.model.Video;
import ir.sanatisharif.android.konkur96.model.filter.FilterBaseModel;
import ir.sanatisharif.android.konkur96.model.filter.VideoCourse;
import ir.sanatisharif.android.konkur96.ui.GlideApp;
import ir.sanatisharif.android.konkur96.utils.Utils;
import me.gujun.android.taggroup.TagGroup;

import static android.content.Context.KEYGUARD_SERVICE;
import static android.content.Context.POWER_SERVICE;
import static ir.sanatisharif.android.konkur96.activity.MainActivity.addFrg;
import static ir.sanatisharif.android.konkur96.app.AppConfig.context;

/**
 * Created by Mohamad on 10/13/2018.
 */

public class DetailsVideoFrg extends BaseFragment implements View.OnClickListener {

    private final String STATE_RESUME_WINDOW = "resumeWindow";
    private final String STATE_RESUME_POSITION = "resumePosition";
    private final String STATE_PLAYER_FULLSCREEN = "playerFullscreen";
    private final static String TAG = "LOG";

    private static List<VideoCourse> videoCourses;
    private DataCourse course;
    private static int positionPlaying;
    private boolean showPlayList = true;
    private int mResumeWindow;
    private long mResumePosition;
    private String mUrl;
    private ProgressBar progressBarExoplaying;
    private RecyclerView myRecyclerView;
    private ImageView imgDownload;
    private ImageView imgReady;
    private ImageView imgShare;
    private FrameLayout mediaVideoFrame;
    private TextView txtDesc;
    private TextView txtAuthor;
    private TextView txtTitle;
    private TagGroup tagGroup;
    private ProgressBar loader;

    //preview
    private FrameLayout relativePreview;
    private ImageView imgPreview;
    private ImageView imgPlay;

    //play list
    private ImageView imgArrow;
    private RecyclerView recyclerPlayList;
    private PlayListAdapter playListAdapter;

    //player
    private PlaybackControlView controlView;
    private SimpleExoPlayer player;
    private SimpleExoPlayerView mExoPlayerView;
    private MediaSource mVideoSource;
    private FrameLayout mFullScreenButton;
    private ImageView mFullScreenIcon;
    private Dialog mFullScreenDialog;
    private boolean mExoPlayerFullscreen = false;

    //comment
    private MainItemAdapter adapter;
    private ArrayList<MainItem> items = new ArrayList<>();

    //lock
    private PowerManager pm;
    private PowerManager.WakeLock wl;
    private KeyguardManager km;
    private KeyguardManager.KeyguardLock kl;

    public static DetailsVideoFrg newInstance(List<? extends FilterBaseModel> v, int pos) {

        Bundle args = new Bundle();
        DetailsVideoFrg fragment = new DetailsVideoFrg();
        fragment.setArguments(args);
        videoCourses = (List<VideoCourse>) v;
        positionPlaying = pos;
        return fragment;
    }

    public static DetailsVideoFrg newInstance(String url) {

        Bundle args = new Bundle();
        args.putString("url", url);
        DetailsVideoFrg fragment = new DetailsVideoFrg();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onStart() {
        super.onStart();

        IntentFilter filter = new IntentFilter();
        filter.addAction("android.intent.action.PHONE_STATE");
        getActivity().registerReceiver(phoneStateReceiver, filter);
        initWakeLockScreen();
    }


    @Override
    public View createFragmentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view, savedInstanceState);
        //get data from api and url
        if (getArguments().getString("url") != null) {
            String url = getArguments().getString("url");
            String id = url.substring(url.lastIndexOf("/") + 1);
            this.mUrl = url;
            getData(id);
        } else//get data from list
        {
            course = videoCourses.get(positionPlaying);
            setData();
        }

    }

    private void getData(String id) {

        MainApi.getInstance().getDetailsCourse(id, new IServerCallbackObject() {
            @Override
            public void onSuccess(Object obj) {
                course = (DataCourse) obj;
                setData();
            }

            @Override
            public void onFailure(String message) {
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

        } else if (i == R.id.imgDownload) {

            if (course.getFile().getVideo() != null) {
                DownloadDialogFrg dialog = new DownloadDialogFrg();
                dialog.setData(course.getFile().getVideo(), course.getName());
                dialog.setComplete(new DownloadComplete() {
                    @Override
                    public void complete() {
                        imgDownload.setVisibility(View.GONE);
                        imgReady.setVisibility(View.VISIBLE);
                    }
                });
                dialog.show(getFragmentManager(), "dialog");
            }


        } else if (i == R.id.imgPlay) {

           if (null != course){

               if (!checkExistVideo(course.getFile().getVideo()))
                   mUrl = course.getFile().getVideo().get(0).getUrl();
           }
            initExoPlayer(mUrl);

            relativePreview.setVisibility(View.GONE);
            mediaVideoFrame.setVisibility(View.VISIBLE);
            mExoPlayerView.setVisibility(View.VISIBLE);

        } else if (i == R.id.imgReady) {

            if (course.getFile().getVideo() != null) {
                DeleteFileDialogFrg deleteFileDialogFrg = new DeleteFileDialogFrg();
                deleteFileDialogFrg.setVideos(course.getFile().getVideo());
                deleteFileDialogFrg.show(getFragmentManager(), "deleteFileDialogFrg");
            }
        } else if (i == R.id.imgShare) {

            String alla = getResources().getString(R.string.alla_1);
            String title = course.getName();
            String author = course.getAuthor().getFullName();
            String url = course.getUrl();
            int order = course.getOrder();

            String share = String.format("%s \n\n %s \n\n %s جلسه %d \n\n %s", alla, title, author, order, url);

            Utils.share(share, getContext());
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        getActivity().unregisterReceiver(phoneStateReceiver);
        releasePlayer();
    }

    @Override
    public void onResume() {
        super.onResume();

        if (mExoPlayerFullscreen) {
            ((ViewGroup) mExoPlayerView.getParent()).removeView(mExoPlayerView);
            mFullScreenDialog.addContentView(mExoPlayerView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            mFullScreenIcon.setImageDrawable(ContextCompat.getDrawable(AppConfig.currentActivity, R.drawable.ic_fullscreen_skrink));
            mFullScreenDialog.show();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        outState.putInt(STATE_RESUME_WINDOW, mResumeWindow);
        outState.putLong(STATE_RESUME_POSITION, mResumePosition);
        outState.putBoolean(STATE_PLAYER_FULLSCREEN, mExoPlayerFullscreen);

        super.onSaveInstanceState(outState);
    }

    @Override
    public void onPause() {
        super.onPause();
        releasePlayer();
    }

    private void animationRotate(View view, float of, float to, int duration) {

        ObjectAnimator imageViewObjectAnimator = ObjectAnimator.ofFloat(view,
                "rotation", of, to);
        imageViewObjectAnimator.setDuration(duration);
        imageViewObjectAnimator.setInterpolator(new AccelerateInterpolator());
        imageViewObjectAnimator.start();
    }

    private void setData() {

        Utils.loadGlide(imgPreview,
                course.getThumbnail(),
                mExoPlayerView.getLayoutParams().width, mExoPlayerView.getLayoutParams().height);

        txtAuthor.setText(course.getAuthor().getFullName());
        txtTitle.setText(course.getName());
        txtDesc.setText(Html.fromHtml(course.getDescription()));
        tagGroup.setTags(course.getTags().getTags());


        tagGroup.setOnTagClickListener(new TagGroup.OnTagClickListener() {
            @Override
            public void onTagClick(String tag) {
                ArrayList<String> tags = new ArrayList<>();
                tags.add(tag);
                addFrg(FilterTagsFrg.newInstance(null, tags), "FilterTagsFrg");
            }
        });
        if (course.getFile() != null && course.getFile().getVideo().size() > 0)
            checkExistVideo(course.getFile().getVideo());
        playListAdapter.notifyDataSetChanged();
    }

    private void initView(View view, Bundle savedInstanceState) {

        if (savedInstanceState != null) {
            mResumeWindow = savedInstanceState.getInt(STATE_RESUME_WINDOW);
            mResumePosition = savedInstanceState.getLong(STATE_RESUME_POSITION);
            mExoPlayerFullscreen = savedInstanceState.getBoolean(STATE_PLAYER_FULLSCREEN);
        }
        if (mExoPlayerView == null) {

            mExoPlayerView = (SimpleExoPlayerView) getView().findViewById(R.id.exoplayer);

            initFullscreenDialog();
            initFullscreenButton();
        }

        myRecyclerView = view.findViewById(R.id.recyclerView);
        imgDownload = view.findViewById(R.id.imgDownload);
        imgReady = view.findViewById(R.id.imgReady);
        imgShare = view.findViewById(R.id.imgShare);
        mediaVideoFrame = view.findViewById(R.id.mediaVideoFrame);
        txtDesc = view.findViewById(R.id.txtDesc);
        txtTitle = view.findViewById(R.id.txtTitle);
        txtAuthor = view.findViewById(R.id.txtAuthor);
        tagGroup = view.findViewById(R.id.tag_group);
        loader = view.findViewById(R.id.loader);
        AppConfig.getInstance().changeProgressColor(loader);

        //preview
        relativePreview = view.findViewById(R.id.relativePreview);
        imgPlay = view.findViewById(R.id.imgPlay);
        imgPreview = view.findViewById(R.id.imgPreview);

        //play list
        imgArrow = view.findViewById(R.id.imgArrow);
        recyclerPlayList = view.findViewById(R.id.recyclerPlayList);

        progressBarExoplaying = (ProgressBar) view.findViewById(R.id.progressBar);
        AppConfig.getInstance().changeProgressColor(progressBarExoplaying);

        myRecyclerView.setNestedScrollingEnabled(false);
        myRecyclerView.setHasFixedSize(true);
        myRecyclerView.setNestedScrollingEnabled(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(AppConfig.context, LinearLayoutManager.VERTICAL, false);
        myRecyclerView.setLayoutManager(layoutManager);
        adapter = new MainItemAdapter(AppConfig.context, items, GlideApp.with(this));
        adapter.setSize(AppConfig.width, AppConfig.height);
        myRecyclerView.setAdapter(adapter);

        //--------------play list
        playListAdapter = new PlayListAdapter(getContext(), videoCourses, GlideApp.with(getContext()));
        recyclerPlayList.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false));
        recyclerPlayList.setAdapter(playListAdapter);
        playListAdapter.setOnClick(new OnItemClickListener() {
            @Override
            public void onItemClick(int position, Object item, View view, RecyclerView.ViewHolder vh) {

                loader.setVisibility(View.VISIBLE);
                positionPlaying = position;
                course = (DataCourse) item;
                setData();
                loader.setVisibility(View.GONE);
                playListAdapter.setItemSelect(positionPlaying);
            }
        });

        // mediaVideoFrame.setVisibility(View.GONE);
        resizePlayer();
        setRipple();

        imgDownload.setOnClickListener(this);
        imgReady.setOnClickListener(this);
        imgArrow.setOnClickListener(this);
        imgPlay.setOnClickListener(this);
        imgShare.setOnClickListener(this);
    }

    //<editor-fold desc="Player">

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

        mFullScreenDialog = new Dialog(getActivity(), android.R.style.Theme_Black_NoTitleBar_Fullscreen) {
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

    private void initExoPlayer(String mUrl) {

        //estimating available network bandwidth based on measured download speed
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
        LoadControl loadControl = new DefaultLoadControl();

        player = ExoPlayerFactory.newSimpleInstance(
                new DefaultRenderersFactory(getContext()),
                trackSelector,
                loadControl);

        mExoPlayerView.setPlayer(player);

        boolean haveResumePosition = mResumeWindow != C.INDEX_UNSET;

        if (haveResumePosition) {
            player.seekTo(mResumeWindow, mResumePosition);
        }

        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(context,
                Util.getUserAgent(context, "mediaPlayerSample"));
        mVideoSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                .createMediaSource(Uri.parse(mUrl));//http://edu-edu.ir/jokLike/1.mp3

        player.addListener(new PlayerEventListener());
        player.prepare(mVideoSource, !haveResumePosition, false);
        player.setPlayWhenReady(true);

    }

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
                    progressBarExoplaying.setVisibility(View.GONE);
                    controlView.findViewById(R.id.exo_play).animate().alpha(1).setDuration(400).setListener(animatorShow);
                    controlView.findViewById(R.id.exo_pause).animate().alpha(1).setDuration(400).setListener(animatorShow);
                    break;
                case Player.STATE_ENDED:      // The player has finished playing the media
                    progressBarExoplaying.setVisibility(View.GONE);

                    break;
            }
        }

    }

    //</editor-fold>

    private void setRipple() {

        ripple(imgArrow, 24);
        // ripple(imgDownload, 24);
        // ripple(imgReady, 24);
        ripple(imgShare, 24);

    }

    private boolean checkExistVideo(List<Video> videos) {

        for (int i = 0; i < videos.size(); i++) {

            String url = videos.get(i).getUrl();

            String mediaPath = FileManager.getPathFromAllaUrl(url);
            String fileName = FileManager.getFileNameFromUrl(url);
            File file = new File(FileManager.getRootPath() + mediaPath + "/" + fileName);
            if (file.exists()) {
                imgDownload.setVisibility(View.GONE);
                imgReady.setVisibility(View.VISIBLE);
                mUrl = file.getPath();
                return true;
            } else {
                imgDownload.setVisibility(View.VISIBLE);
                imgReady.setVisibility(View.GONE);
            }

        }
        return false;
    }

    //<editor-fold desc="animate">
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
    //</editor-fold>

    //<editor-fold desc="lock">
    @SuppressLint("InvalidWakeLockTag")
    private void initWakeLockScreen() {
        pm = (PowerManager) getContext().getSystemService(POWER_SERVICE);
        wl = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK
                | PowerManager.ACQUIRE_CAUSES_WAKEUP
                | PowerManager.ON_AFTER_RELEASE, "alla");


        km = (KeyguardManager) getContext().getSystemService(KEYGUARD_SERVICE);
        if (null != km){
            kl = km.newKeyguardLock("alla");
        }


    }

    private void enableWakeLockScreen() {
        wl.acquire();
        kl.reenableKeyguard();
    }

    private void disableWakeLockScreen() {
        if (wl.isHeld())
            wl.release();
        kl.disableKeyguard();
    }
    //</editor-fold>

    //<editor-fold desc="phoneStateReceiver">
    BroadcastReceiver phoneStateReceiver = new BroadcastReceiver() {
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
    //</editor-fold>

    RecyclerView.SmoothScroller smoothScroller = new LinearSmoothScroller(context) {
        @Override
        protected int getVerticalSnapPreference() {
            return LinearSmoothScroller.SNAP_TO_START;
        }
    };
}
