package ir.sanatisharif.android.konkur96.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.KeyguardManager;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
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
import com.google.android.exoplayer2.util.Util;
import com.google.android.gms.common.wrappers.InstantApps;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.account.AccountInfo;
import ir.sanatisharif.android.konkur96.activity.ActivityBase;
import ir.sanatisharif.android.konkur96.adapter.MainItemAdapter;
import ir.sanatisharif.android.konkur96.adapter.PlayListAdapter;
import ir.sanatisharif.android.konkur96.api.MainApi;
import ir.sanatisharif.android.konkur96.api.Models.ProductModel;
import ir.sanatisharif.android.konkur96.app.AppConfig;
import ir.sanatisharif.android.konkur96.app.AppConstants;
import ir.sanatisharif.android.konkur96.dialog.DeleteFileDialogFrg;
import ir.sanatisharif.android.konkur96.dialog.DownloadDialogFrg;
import ir.sanatisharif.android.konkur96.helper.FileManager;
import ir.sanatisharif.android.konkur96.listener.DownloadComplete;
import ir.sanatisharif.android.konkur96.listener.OnItemClickListener;
import ir.sanatisharif.android.konkur96.listener.api.IServerCallbackContentCredit;
import ir.sanatisharif.android.konkur96.listener.api.IServerCallbackObject;
import ir.sanatisharif.android.konkur96.model.ContentCredit;
import ir.sanatisharif.android.konkur96.model.DataCourse;
import ir.sanatisharif.android.konkur96.model.MainItem;
import ir.sanatisharif.android.konkur96.model.Video;
import ir.sanatisharif.android.konkur96.model.filter.Filter;
import ir.sanatisharif.android.konkur96.model.filter.FilterBaseModel;
import ir.sanatisharif.android.konkur96.model.filter.Pagination;
import ir.sanatisharif.android.konkur96.model.filter.VideoCourse;
import ir.sanatisharif.android.konkur96.model.main_page.Content;
import ir.sanatisharif.android.konkur96.model.user.User;
import ir.sanatisharif.android.konkur96.ui.view.MDToast;
import ir.sanatisharif.android.konkur96.utils.EndlessRecyclerViewScrollListener;
import ir.sanatisharif.android.konkur96.utils.MyPreferenceManager;
import ir.sanatisharif.android.konkur96.utils.Utils;
import me.gujun.android.taggroup.TagGroup;

import static android.content.Context.KEYGUARD_SERVICE;
import static android.content.Context.POWER_SERVICE;
import static ir.sanatisharif.android.konkur96.activity.MainActivity.addFrg;
import static ir.sanatisharif.android.konkur96.app.AppConfig.context;
import static ir.sanatisharif.android.konkur96.app.AppConstants.ACCOUNT_TYPE;
import static ir.sanatisharif.android.konkur96.app.AppConstants.AUTHTOKEN_TYPE_FULL_ACCESS;

/**
 * Created by Mohamad on 10/13/2018.
 */

public class DetailsVideoFrg extends BaseFragment implements View.OnClickListener {

    private final static String TAG = "LOG";
    private final String STATE_RESUME_WINDOW = "resumeWindow";
    private final String STATE_RESUME_POSITION = "resumePosition";
    private final String STATE_PLAYER_FULLSCREEN = "playerFullscreen";
    private static final int LOAD_URL = 0;
    private static final int LOAD_CONTENT = 1;
    private static final int LOAD_LIST = 2;
    private static int kind_of_Load = -1;


    private SharedPreferences sharedPreferences;
    private String quality = "";

    private Bundle savedInsPlayer;

    private VideoPlayer videoPlayer;
    private static List<VideoCourse> videoCourses;
    private static Content content;
    private DataCourse course;
    private static int positionPlaying;
    private boolean showPlayList = true;
    private int mResumeWindow;
    private long mResumePosition;
    private String mUrl;
    private ProgressBar progressBarExoplaying;
    private RecyclerView myRecyclerViewProduct;
    private ImageView imgDownload;
    private ImageView imgReady;
    private ImageView imgShare;
    private FrameLayout mediaVideoFrame;
    private TextView txtDesc;
    private TextView txtAuthor;
    private TextView txtTitle;
    private TagGroup tagGroup;
    private ProgressBar loader, loaderPlayList;
    private LinearLayout root;

    //preview
    private FrameLayout relativePreview;
    private ImageView imgPreview;
    private ImageView imgPlay;

    //play list
    private ImageView imgArrow;
    private RecyclerView recyclerPlayList;
    private PlayListAdapter playListAdapter;
    private LinearLayoutManager managerPlayList;
    private EndlessRecyclerViewScrollListener endLess;
    public static Pagination pagination;
    //player
    private PlaybackControlView controlView;
    private SimpleExoPlayer player;
    private SimpleExoPlayerView mExoPlayerView;
    private MediaSource mVideoSource;
    private FrameLayout mFullScreenButton;
    private ImageView mFullScreenIcon;
    private TextView txtQuality;
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

    private AccountInfo accountInfo;

    public class VideoPlayer implements LifecycleObserver {

        @OnLifecycleEvent(Lifecycle.Event.ON_START)
        public void start() {
            //play logic
            Log.i(TAG, "play:1 ");
            initExoPlayer();
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
        public void play() {
            //play logic
            Log.i(TAG, "play:2 ");
            resume();
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
        public void pause() {
            Log.i(TAG, "play:3");
            if (Util.SDK_INT <= 23)
                pausePlayer();
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
        public void stop() {
            Log.i(TAG, "play: 4");
            if (Util.SDK_INT > 23)
                releasePlayer();
        }
    }

    // from list
    public static DetailsVideoFrg newInstance(List<? extends FilterBaseModel> v, int pos) {

        Bundle args = new Bundle();
        DetailsVideoFrg fragment = new DetailsVideoFrg();
        fragment.setArguments(args);
        videoCourses = (List<VideoCourse>) v;//get video list
        positionPlaying = pos;
        kind_of_Load = LOAD_LIST;
        return fragment;
    }

    // from content
    public static DetailsVideoFrg newInstance(Content c) {

        Bundle args = new Bundle();
        DetailsVideoFrg fragment = new DetailsVideoFrg();
        fragment.setArguments(args);
        content = c;
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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onStart() {
        super.onStart();

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

    @Override
    public void onResume() {
        super.onResume();
        FirebaseAnalytics.getInstance(getContext()).setCurrentScreen(getActivity(), this.getClass().getSimpleName(), this.getClass().getSimpleName());
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
        super.onStop();
        getActivity().unregisterReceiver(phoneStateReceiver);
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
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view, savedInstanceState);

        if (kind_of_Load == LOAD_CONTENT) {
            course = content;
            setData();
            getPlayListFromContentByUrl(content.getSet().getContentUrl());

        } else if (kind_of_Load == LOAD_URL) { //get data from api and url
            String url = getArguments().getString("url");
            String id = url.substring(url.lastIndexOf("/") + 1);
            this.mUrl = url;
            getData(url);
        } else if (kind_of_Load == LOAD_LIST) {
            //get data from list
            course = videoCourses.get(positionPlaying);
            setData();
        }

        // videoPlayer = new VideoPlayer();
        //  getLifecycle().addObserver(videoPlayer);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (videoCourses != null) {
            videoCourses = null;
            positionPlaying = 0;
        }
        if (playListAdapter != null) {
            playListAdapter = null;
        }
    }

    private void getData(String url) {

        if (InstantApps.isInstantApp(getContext())) {
            MainApi.getInstance().getDetailsCourse(url, new IServerCallbackContentCredit() {
                @Override
                public void onSuccess(Object obj) {
                    course = (DataCourse) obj;
                    setData();
                    getPlayListFromContentByUrl(course.getSet().getContentUrl());

                }

                @Override
                public void onSuccessCredit(ContentCredit obj) {
                    if (obj != null) {
                        course = obj.getContent();
                        setData();
                        showSnackBar(obj.getMessage());
                        setProduct(obj.getProduct());
                        getPlayListFromContentByUrl(course.getSet().getContentUrl());

                    }
                }

                @Override
                public void onFailure(String message) {
                    Log.i(TAG, "onSuccess:error " + message);
                }
            });
        } else {

            accountInfo = new AccountInfo(getContext(), getActivity());
            accountInfo.getExistingAccountAuthToken(ACCOUNT_TYPE, AUTHTOKEN_TYPE_FULL_ACCESS, new AccountInfo.AuthToken() {
                @Override
                public void onToken(String token) {
                    // setAuth
                    MyPreferenceManager.getInatanse().setApiToken(token);
                    MyPreferenceManager.getInatanse().setAuthorize(true);
                    // call api
                    MainApi.getInstance().getDetailsCourse(url, new IServerCallbackContentCredit() {
                        @Override
                        public void onSuccess(Object obj) {
                            course = (DataCourse) obj;
                            setData();
                            getPlayListFromContentByUrl(course.getSet().getContentUrl());

                            // reset
                            MyPreferenceManager.getInatanse().setApiToken("");
                            MyPreferenceManager.getInatanse().setAuthorize(false);
                        }

                        @Override
                        public void onSuccessCredit(ContentCredit obj) {
                            if (obj != null) {
                                course = obj.getContent();
                                setData();
                                showSnackBar(obj.getMessage());
                                setProduct(obj.getProduct());
                                getPlayListFromContentByUrl(course.getSet().getContentUrl());

                                // reset
                                MyPreferenceManager.getInatanse().setApiToken("");
                                MyPreferenceManager.getInatanse().setAuthorize(false);
                            }
                        }

                        @Override
                        public void onFailure(String message) {
                            Log.i(TAG, "onSuccess:error " + message);

                            // reset
                            MyPreferenceManager.getInatanse().setApiToken("");
                            MyPreferenceManager.getInatanse().setAuthorize(false);
                        }
                    });
                }
            });
        }

    }

    private void setProduct(List<ProductModel> productModels) {

        // set adapter to recyclerView
        myRecyclerViewProduct.setVisibility(View.VISIBLE);
        myRecyclerViewProduct.setNestedScrollingEnabled(false);
        myRecyclerViewProduct.setHasFixedSize(true);
        myRecyclerViewProduct.setNestedScrollingEnabled(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(AppConfig.context, LinearLayoutManager.VERTICAL, false);
        myRecyclerViewProduct.setLayoutManager(layoutManager);
        adapter = new MainItemAdapter(AppConfig.context, items);
        adapter.setSize(AppConfig.width, AppConfig.height);
        myRecyclerViewProduct.setAdapter(adapter);

        // setData
        MainItem item = new MainItem();
        item = new MainItem();
        item.setType(AppConstants.ITEM_PRODUCT);
        item.setProducts(productModels);
        items.add(item);

        adapter.notifyDataSetChanged();
    }

    private void getDataByUrl(String url) {

        loaderPlayList.setVisibility(View.VISIBLE);
        MainApi.getInstance().getFilterTagsByUrl(url, new IServerCallbackObject() {
            @Override
            public void onSuccess(Object obj) {
                Filter filter = (Filter) obj;

                if (filter.getResult().getVideo() != null) {
                    videoCourses.addAll(filter.getResult().getVideo().getData());
                    pagination = filter.getResult().getVideo();
                    playListAdapter.notifyItemMoved(playListAdapter.getItemCount(), videoCourses.size() - 1);
                }
                loaderPlayList.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(String message) {
                Log.i(TAG, "onSuccess:error " + message);
                loaderPlayList.setVisibility(View.GONE);
            }
        });
    }

    private void getPlayListFromContentByUrl(String url) {

        loaderPlayList.setVisibility(View.VISIBLE);
        MainApi.getInstance().getFilterTagsByUrl(url, new IServerCallbackObject() {
            @Override
            public void onSuccess(Object obj) {
                Filter filter = (Filter) obj;

                if (filter.getResult().getVideo() != null) {

                    videoCourses = filter.getResult().getVideo().getData();
                    pagination = filter.getResult().getVideo();

                    initPlayList();
                    // playListAdapter = new PlayListAdapter(getContext(), videoCourses);
                    // recyclerPlayList.setAdapter(playListAdapter);

                    if (videoCourses.size() > 1) {
                        playListAdapter.notifyItemMoved(playListAdapter.getItemCount(), videoCourses.size() - 1);
                    } else {
                        playListAdapter.notifyDataSetChanged();
                    }
                    loaderPlayList.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(String message) {
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

            if (null != course) {
                if (!checkExistVideoToSD(course.getFile().getVideo())) {
                    // not Exist
                    handleQualityLink();
                }
            }
            startPlayer(mUrl);

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
            //  player.prepare(mVideoSource, !haveResumePosition, false);
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

        if (course.getFile() != null && course.getFile().getVideo().size() > 0)
            checkExistVideoToSD(course.getFile().getVideo());

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
                } catch (Exception ex) {
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

            mExoPlayerView = (SimpleExoPlayerView) getView().findViewById(R.id.exoplayer);

            initFullscreenDialog();
            initFullscreenButton();
        }

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        root = view.findViewById(R.id.root);
        myRecyclerViewProduct = view.findViewById(R.id.recyclerView);
        imgDownload = view.findViewById(R.id.imgDownload);
        imgReady = view.findViewById(R.id.imgReady);
        imgShare = view.findViewById(R.id.imgShare);
        mediaVideoFrame = view.findViewById(R.id.mediaVideoFrame);
        txtDesc = view.findViewById(R.id.txtDesc);
        txtTitle = view.findViewById(R.id.txtTitle);
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

        progressBarExoplaying = (ProgressBar) view.findViewById(R.id.progressBar);
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
        playListAdapter = new PlayListAdapter(getContext(), videoCourses);
        managerPlayList = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerPlayList.setLayoutManager(managerPlayList);
        recyclerPlayList.setAdapter(playListAdapter);
        playListAdapter.setOnClick(new OnItemClickListener() {
            @Override
            public void onItemClick(int position, Object item, View view, RecyclerView.ViewHolder vh) {

                // player.setPlayWhenReady(false);
                // releasePlayer();
                loader.setVisibility(View.VISIBLE);
                positionPlaying = position;
                course = (DataCourse) item;
                loader.setVisibility(View.GONE);
                playListAdapter.setItemSelect(positionPlaying);
                setData();

                if (mExoPlayerFullscreen) {
                    ((ViewGroup) mExoPlayerView.getParent()).removeView(mExoPlayerView);
                    mFullScreenDialog.addContentView(mExoPlayerView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                    mFullScreenIcon.setImageDrawable(ContextCompat.getDrawable(AppConfig.currentActivity, R.drawable.ic_fullscreen_skrink));
                    mFullScreenDialog.show();
                }

                if (!checkExistVideoToSD(course.getFile().getVideo())) {
                    handleQualityLink();
                }

                if (isPlaying()) {
                    AppConfig.HANDLER.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            mResumePosition = 0;
                            DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(context,
                                    Util.getUserAgent(context, "mediaPlayerSample"));
                            mVideoSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                                    .createMediaSource(Uri.parse(mUrl));

                            boolean haveResumePosition = mResumeWindow != C.INDEX_UNSET;

                            if (haveResumePosition) {
                                player.seekTo(mResumeWindow, mResumePosition);
                            }
                            player.seekTo(0);
                            player.prepare(mVideoSource, !haveResumePosition, false);
                            player.setPlayWhenReady(true);

                        }
                    }, 200);

                }
            }
        });
        endLess = new EndlessRecyclerViewScrollListener(managerPlayList) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {

                if (pagination.getNextPageUrl() != null) {
                    getDataByUrl(pagination.getNextPageUrl());
                }
            }
        };
        recyclerPlayList.addOnScrollListener(endLess);
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
        TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
        LoadControl loadControl = new DefaultLoadControl();

        player = ExoPlayerFactory.newSimpleInstance(
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

        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(context,
                Util.getUserAgent(context, "mediaPlayerSample"));
        mVideoSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                .createMediaSource(Uri.parse(mUrl));

        player.addListener(eventListener);
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

    Player.EventListener eventListener=new Player.EventListener() {
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
                    ActivityBase.toastShow("خطا در بارگذاری ویدیو",MDToast.TYPE_ERROR);
                    player.stop();
                    relativePreview.setVisibility(View.VISIBLE);
                    mediaVideoFrame.setVisibility(View.GONE);
                    break;

                case ExoPlaybackException.TYPE_RENDERER:
                    Log.e(TAG, "TYPE_RENDERER: " + error.getRendererException().getMessage());
                    ActivityBase.toastShow("خطا در خواندن ویدیو",MDToast.TYPE_ERROR);
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

    //</editor-fold>

    private void setRipple() {

        ripple(imgArrow, 24);
        // ripple(imgDownload, 24);
        // ripple(imgReady, 24);
        ripple(imgShare, 24);

    }

    /**
     * if return true ie file is exist ito SD
     * if return false file not exist
     *
     * @param videos
     * @return
     */
    private boolean checkExistVideoToSD(List<Video> videos) {

        if (InstantApps.isInstantApp(getContext())) {
            imgDownload.setVisibility(View.GONE);
            imgReady.setVisibility(View.GONE);
            return false;
        }

        for (int i = 0; i < videos.size(); i++) {

            String url = videos.get(i).getLink();

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

    private void handleQualityLink() {
        String pref = sharedPreferences.getString(getString(R.string.player_quality), "240");
        mUrl = course.getFile().getVideo().get(0).getLink();
        txtQuality.setText("");
        txtQuality.setVisibility(View.VISIBLE);
        try {
            if (pref.contains("720")) {
                mUrl = course.getFile().getVideo().get(0).getLink();
                quality = toString(course.getFile().getVideo().get(0).getCaption(), course.getFile().getVideo().get(0).getRes());
            } else if (pref.contains("hq")) {
                mUrl = course.getFile().getVideo().get(1).getLink();
                quality = toString(course.getFile().getVideo().get(1).getCaption(), course.getFile().getVideo().get(1).getRes());
            } else if (pref.contains("240")) {
                quality = toString(course.getFile().getVideo().get(2).getCaption(), course.getFile().getVideo().get(2).getRes());
                mUrl = course.getFile().getVideo().get(2).getLink();
            }
        } catch (Exception ex) {

            if (course.getFile().getVideo().size() == 1) {
                mUrl = course.getFile().getVideo().get(0).getLink();
                quality = toString(course.getFile().getVideo().get(0).getCaption(), course.getFile().getVideo().get(0).getRes());
            } else if (course.getFile().getVideo().size() == 0) {
                ActivityBase.toastShow("لینکی موحود نیست!", MDToast.TYPE_ERROR);
            }
        }
        txtQuality.setText(quality);
        AppConfig.HANDLER.postDelayed(new Runnable() {
            @Override
            public void run() {
                txtQuality.setVisibility(View.GONE);
            }
        }, 5000);
        // txtQuality.animate().alpha(0).setDuration(3000).start();
    }

    public String toString(String caption, String title) {
        return String.format("%s - %s", caption, title);
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
        if (null != km) {
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

    private void showSnackBar(String message) {
        Snackbar snack = Snackbar.make(root, message, Snackbar.LENGTH_LONG);
        View view = snack.getView();
        TextView tv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
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
}
