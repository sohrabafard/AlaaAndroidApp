package ir.sanatisharif.android.konkur96.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
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
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
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

import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.adapter.CommentAdapter;
import ir.sanatisharif.android.konkur96.adapter.MainItemAdapter;
import ir.sanatisharif.android.konkur96.adapter.PlayListAdapter;
import ir.sanatisharif.android.konkur96.app.AppConfig;
import ir.sanatisharif.android.konkur96.dialog.DownloadDialogFrg;
import ir.sanatisharif.android.konkur96.helper.FileManager;
import ir.sanatisharif.android.konkur96.listener.OnItemClickListener;
import ir.sanatisharif.android.konkur96.model.CategoryItemSet;
import ir.sanatisharif.android.konkur96.model.Comment;
import ir.sanatisharif.android.konkur96.model.DownloadUrl;
import ir.sanatisharif.android.konkur96.model.Item;
import ir.sanatisharif.android.konkur96.model.MainItem;
import ir.sanatisharif.android.konkur96.model.PlayList;
import ir.sanatisharif.android.konkur96.utils.Utils;

import static android.content.Context.KEYGUARD_SERVICE;
import static android.content.Context.POWER_SERVICE;
import static ir.sanatisharif.android.konkur96.app.AppConfig.context;
import static ir.sanatisharif.android.konkur96.app.AppConfig.itemHeight;

/**
 * Created by Mohamad on 10/13/2018.
 */

public class DetailsVideoFrg extends BaseFragment implements View.OnClickListener {

    private final String STATE_RESUME_WINDOW = "resumeWindow";
    private final String STATE_RESUME_POSITION = "resumePosition";
    private final String STATE_PLAYER_FULLSCREEN = "playerFullscreen";
    private final static String TAG = "LOG";

    private static Item item;
    private boolean showPlayList = false;
    private int mResumeWindow;
    private long mResumePosition;
    private String mUrl;///= "https://cdn.sanatisharif.ir/media/170/240p/170023fghg.mp4";
    private ArrayList<DownloadUrl> downloadUrls = new ArrayList<>();

    //ui
    private ProgressBar progressBar;
    private RecyclerView myRecyclerView;
    private RecyclerView recyclerViewComment;
    private ImageView imgDownload;
    private ImageView imgReady;
    private ImageView imgLike;
    private ImageView imgShare;
    private ImageView imgFavorite;
    private FrameLayout mediaVideoFrame;
    private TextView txtVote;
    private TextView txtAuthor;
    private TextView txtTitle;

    //preview
    private RelativeLayout relativePreview;
    private ImageView imgPreview;
    private ImageView imgPlay;

    //play list
    private LinearLayout linRootPlayList;
    private LinearLayout linBodyPlayList;
    private LinearLayout linHeaderPlayList;
    private ImageView imgShowPlayList;
    private RecyclerView recyclerPlayList;
    private PlayListAdapter playListAdapter;
    private ArrayList<PlayList> playLists = new ArrayList<>();

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
    private CommentAdapter commentAdapter;
    private ArrayList<MainItem> items = new ArrayList<>();
    private ArrayList<Comment> comments = new ArrayList<>();

    //lock
    private PowerManager pm;
    private PowerManager.WakeLock wl;
    private KeyguardManager km;
    private KeyguardManager.KeyguardLock kl;


    public static DetailsVideoFrg newInstance(Item item_in) {

        Bundle args = new Bundle();
        //  args.putSerializable("item", item);
        item = item_in;
        DetailsVideoFrg fragment = new DetailsVideoFrg();
        fragment.setArguments(args);
        return fragment;
    }

    public static DetailsVideoFrg newInstance() {

        Bundle args = new Bundle();
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

        initView(view);
        setData();

        if (savedInstanceState != null) {
            mResumeWindow = savedInstanceState.getInt(STATE_RESUME_WINDOW);
            mResumePosition = savedInstanceState.getLong(STATE_RESUME_POSITION);
            mExoPlayerFullscreen = savedInstanceState.getBoolean(STATE_PLAYER_FULLSCREEN);
        }
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


        } else if (i == R.id.imgShowPlayList) {
            if (!showPlayList) {
                linBodyPlayList.setVisibility(View.VISIBLE);
                imgShowPlayList.setImageResource(R.drawable.ic_down);
                showPlayList = true;
            } else {
                linBodyPlayList.setVisibility(View.GONE);
                imgShowPlayList.setImageResource(R.drawable.ic_arrow_up);
                showPlayList = false;
            }

        } else if (i == R.id.imgDownload) {
            DownloadDialogFrg.newInstance(downloadUrls).
                    show(((AppCompatActivity) getActivity()).getSupportFragmentManager(), "dialog");
        } else if (i == R.id.imgPlay) {

            if (!checkExistVideo(downloadUrls))
                mUrl = downloadUrls.get(0).getUrl();
            initExoPlayer(mUrl);

            relativePreview.setVisibility(View.GONE);
            mediaVideoFrame.setVisibility(View.VISIBLE);
            mExoPlayerView.setVisibility(View.VISIBLE);

        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        outState.putInt(STATE_RESUME_WINDOW, mResumeWindow);
        outState.putLong(STATE_RESUME_POSITION, mResumePosition);
        outState.putBoolean(STATE_PLAYER_FULLSCREEN, mExoPlayerFullscreen);

        super.onSaveInstanceState(outState);
    }

    private void setData() {

        // "https://cdn.sanatisharif.ir/upload/contentset/departmentlesson/physics1809261648.jpg?w=253&h=142"
        if (item != null) {
            Utils.loadGlide(imgPreview,
                    item.getImageUrl(),
                    mExoPlayerView.getLayoutParams().width,
                    mExoPlayerView.getLayoutParams().height);

            txtTitle.setText(item.getTitle());
            txtAuthor.setText(item.getAuthor());

        }

        DownloadUrl downloadUrl = new DownloadUrl();
        downloadUrl.setUrl("https://cdn.sanatisharif.ir/media/202/hq/202063wkjk.mp4");
        downloadUrl.setQuality("کیفیت بالا (59 مگابایت)");
        downloadUrl.setTitle("فصل دوم فیزیک دوازدهم");
        downloadUrl.setDescription("");
        downloadUrls.add(downloadUrl);

        downloadUrl = new DownloadUrl();
        downloadUrl.setUrl("https://cdn.sanatisharif.ir/media/202/HD_720p/202063wkjk.mp4");
        downloadUrl.setQuality("کیفیت عالی (734 مگابایت)");
        downloadUrl.setTitle("فصل دوم فیزیک دوازدهم");
        downloadUrl.setDescription("");
        downloadUrls.add(downloadUrl);

        downloadUrl = new DownloadUrl();
        downloadUrl.setUrl("https://cdn.sanatisharif.ir/media/202/240p/202063wkjk.mp4");
        downloadUrl.setQuality("کیفیت متوسط (25 مگابایت)");
        downloadUrl.setTitle("فصل دوم فیزیک دوازدهم");
        downloadUrl.setDescription("");
        downloadUrls.add(downloadUrl);

        //video
        ArrayList<CategoryItemSet> videoItemSets = new ArrayList<>();

        CategoryItemSet videoItemSet = new CategoryItemSet();
        videoItemSet.setId(0);
        videoItemSet.setTitle("صفر تا صد فیزیک کنکور");
        videoItemSet.setAuthor(" کازیان ");
        videoItemSet.setImageUrl("https://cdn.sanatisharif.ir/upload/contentset/departmentlesson/physics1809261648.jpg?w=253&h=142");
        videoItemSets.add(videoItemSet);

        videoItemSet = new CategoryItemSet();
        videoItemSet.setId(1);
        videoItemSet.setTitle("صفر تا صد شیمی کنکور");
        videoItemSet.setAuthor(" مهدی صنیعی ");
        videoItemSet.setImageUrl("https://cdn.sanatisharif.ir/upload/contentset/departmentlesson/shimi_1809301705.jpg?w=253&h=142");
        videoItemSets.add(videoItemSet);

        videoItemSet = new CategoryItemSet();
        videoItemSet.setId(2);
        videoItemSet.setTitle("صفر تا صد حسابان کنکور");
        videoItemSet.setAuthor(" محمد صادق ثابتی ");
        videoItemSet.setImageUrl("https://cdn.sanatisharif.ir/upload/contentset/departmentlesson/hesaban_1806091555.jpg?w=253&h=142");
        videoItemSets.add(videoItemSet);


        videoItemSet = new CategoryItemSet();
        videoItemSet.setId(3);
        videoItemSet.setTitle("صفر تا صد ریاضی تجربی کنکور");
        videoItemSet.setAuthor(" محمد صادق ثابتی ");
        videoItemSet.setImageUrl("https://cdn.sanatisharif.ir/upload/contentset/departmentlesson/riyazi_tajrobi_1809261626.jpg?w=253&h=142");
        videoItemSets.add(videoItemSet);

        videoItemSet = new CategoryItemSet();
        videoItemSet.setId(4);
        videoItemSet.setTitle("صفر تا صد عربی کنکور");
        videoItemSet.setAuthor(" پدرام علیمرادی");
        videoItemSet.setImageUrl("https://cdn.sanatisharif.ir/upload/contentset/departmentlesson/arabi_1806091641.jpg?w=253&h=142");
        videoItemSets.add(videoItemSet);

        videoItemSet = new CategoryItemSet();
        videoItemSet.setId(5);
        videoItemSet.setTitle("صفر تا صد منطق کنکور");
        videoItemSet.setAuthor(" حسام الدین حلالی");
        videoItemSet.setImageUrl("https://cdn.sanatisharif.ir/upload/contentset/departmentlesson/171005032754.jpg?w=253&h=142");
        videoItemSets.add(videoItemSet);


        MainItem item = new MainItem();
        item.setId(0);
        item.setType(0);
        item.setTitle("کنکور نظام جدید ");
        item.setItems(videoItemSets);
        items.add(item);


        //---------------------------------
        videoItemSets = new ArrayList<>();
        videoItemSet = new CategoryItemSet();
        videoItemSet.setId(0);
        videoItemSet.setTitle("صفر تا صد زبان");
        videoItemSet.setAuthor("علی اکبر عزتی");
        videoItemSet.setImageUrl("https://cdn.sanatisharif.ir/upload/contentset/departmentlesson/zaban11_1810070959.jpg?w=253&h=142");
        videoItemSets.add(videoItemSet);

        videoItemSet = new CategoryItemSet();
        videoItemSet.setId(1);
        videoItemSet.setTitle("زیست یازدهم");
        videoItemSet.setAuthor(" جلال موقاری");
        videoItemSet.setImageUrl("https://cdn.sanatisharif.ir/upload/contentset/departmentlesson/zist_yazdahom_1810011438.jpg?w=253&h=142");
        videoItemSets.add(videoItemSet);

        videoItemSet = new CategoryItemSet();
        videoItemSet.setId(2);
        videoItemSet.setTitle("صفر تا صد فیزیک یازدهم");
        videoItemSet.setAuthor(" فرشید داداشی");
        videoItemSet.setImageUrl("https://cdn.sanatisharif.ir/upload/contentset/departmentlesson/170920041635.jpg?w=253&h=142");
        videoItemSets.add(videoItemSet);

        videoItemSet = new CategoryItemSet();
        videoItemSet.setId(3);
        videoItemSet.setTitle("زیست یازدهم");
        videoItemSet.setAuthor(" عباس راسنی");
        videoItemSet.setImageUrl("https://cdn.sanatisharif.ir/upload/contentset/departmentlesson/171019113948.jpg?w=253&h=142");
        videoItemSets.add(videoItemSet);


        item = new MainItem();
        item.setId(3);
        item.setType(0);
        item.setTitle("مقطع یازدهم");
        item.setItems(videoItemSets);
        items.add(item);

        adapter.notifyDataSetChanged();

        //comment

        Comment c = new Comment();
        c.setName("علی");
        c.setContent("خوب");
        comments.add(c);

        c = new Comment();
        c.setName("زهرا");
        c.setContent("عالیییی");
        comments.add(c);

        c = new Comment();
        c.setName("هومن");
        c.setContent("لایک");
        comments.add(c);

        c = new Comment();
        c.setName("احمدی");
        c.setContent("عالی بود");
        comments.add(c);

        commentAdapter.notifyDataSetChanged();


        //playList

        PlayList pl = new PlayList();
        pl.setTitle("زیست یازدهم");
        pl.setImageUrl("https://cdn.sanatisharif.ir/upload/contentset/departmentlesson/171019113948.jpg?w=460&h=259");

        ArrayList<DownloadUrl> downloadUrls1 = new ArrayList<>();

        downloadUrl = new DownloadUrl();
        downloadUrl.setUrl("https://cdn.sanatisharif.ir/media/204/HD_720p/204034acde.mp4");
        downloadUrl.setQuality("کیفیت عالی (473 مگابایت)");
        downloadUrl.setTitle("زیست یادهم");
        downloadUrls1.add(downloadUrl);

        downloadUrl = new DownloadUrl();
        downloadUrl.setUrl("https://cdn.sanatisharif.ir/media/204/240p/204034acde.mp4?download=1");
        downloadUrl.setQuality("کیفیت بالا (70 مگابایت)");
        downloadUrl.setTitle("زیست یادهم");
        downloadUrl.setDescription("");
        downloadUrls1.add(downloadUrl);

        downloadUrl = new DownloadUrl();
        downloadUrl.setUrl("https://cdn.sanatisharif.ir/media/204/240p/204034acde.mp4");
        downloadUrl.setQuality("کیفیت متوسظ (70 مگابایت)");
        downloadUrl.setTitle("زیست یادهم");
        downloadUrl.setDescription("");
        downloadUrls1.add(downloadUrl);

        pl.setDownloadUrls(downloadUrls1);
        playLists.add(pl);


        pl = new PlayList();
        pl.setTitle("صفرتا صد زبان");
        pl.setImageUrl("https://cdn.sanatisharif.ir/upload/contentset/departmentlesson/zaban11_1810070959.jpg?w=460&h=259");
        pl.setDownloadUrls(downloadUrls1);
        playLists.add(pl);


        pl = new PlayList();
        pl.setTitle("صفرتا صد زبان");
        pl.setImageUrl("https://cdn.sanatisharif.ir/upload/contentset/departmentlesson/zaban11_1810070959.jpg?w=460&h=259");
        pl.setDownloadUrls(downloadUrls1);
        playLists.add(pl);

        pl = new PlayList();
        pl.setTitle("صفرتا صد زبان");
        pl.setImageUrl("https://cdn.sanatisharif.ir/upload/contentset/departmentlesson/zaban11_1810070959.jpg?w=460&h=259");
        pl.setDownloadUrls(downloadUrls1);
        playLists.add(pl);

        playListAdapter.notifyDataSetChanged();

    }

    private void initView(View view) {

        if (mExoPlayerView == null) {

            mExoPlayerView = (SimpleExoPlayerView) getView().findViewById(R.id.exoplayer);

            initFullscreenDialog();
            initFullscreenButton();
        }

        myRecyclerView = view.findViewById(R.id.recyclerView);
        recyclerViewComment = view.findViewById(R.id.recyclerViewComment);
        imgDownload = view.findViewById(R.id.imgDownload);
        imgReady = view.findViewById(R.id.imgReady);
        imgLike = view.findViewById(R.id.imgLike);
        imgShare = view.findViewById(R.id.imgShare);
        imgFavorite = view.findViewById(R.id.imgFavorite);
        mediaVideoFrame = view.findViewById(R.id.mediaVideoFrame);
        txtVote = view.findViewById(R.id.txtVote);
        txtTitle = view.findViewById(R.id.txtTitle);
        txtAuthor = view.findViewById(R.id.txtAuthor);


        //preview
        relativePreview = view.findViewById(R.id.relativePreview);
        imgPlay = view.findViewById(R.id.imgPlay);
        imgPreview = view.findViewById(R.id.imgPreview);

        //play list

        linRootPlayList = view.findViewById(R.id.linRootPlayList);
        linBodyPlayList = view.findViewById(R.id.linBodyPlayList);
        linHeaderPlayList = view.findViewById(R.id.linHeaderPlayList);
        imgShowPlayList = view.findViewById(R.id.imgShowPlayList);
        recyclerPlayList = view.findViewById(R.id.recyclerPlayList);

        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);


        myRecyclerView.setNestedScrollingEnabled(false);
        myRecyclerView.setHasFixedSize(true);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(AppConfig.context, LinearLayoutManager.VERTICAL, false));
        adapter = new MainItemAdapter(AppConfig.context, items);
        adapter.setSize(AppConfig.width, AppConfig.height);
        myRecyclerView.setAdapter(adapter);

        recyclerViewComment.setLayoutManager(new LinearLayoutManager(AppConfig.context, LinearLayoutManager.VERTICAL, false));
        commentAdapter = new CommentAdapter(AppConfig.context, comments);
        recyclerViewComment.setAdapter(commentAdapter);

        //--------------play list
        playListAdapter = new PlayListAdapter(getContext(), playLists);
        recyclerPlayList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerPlayList.setAdapter(playListAdapter);
        playListAdapter.setOnClick(new OnItemClickListener() {
            @Override
            public void onItemClick(int position, Object item, View view, RecyclerView.ViewHolder vh) {

                if (!checkExistVideo(((PlayList) item).getDownloadUrls())) {
                    mUrl = ((PlayList) item).getDownloadUrls().get(0).getUrl();
                }
                if (player != null)
                    player.release();
                initExoPlayer(mUrl);

            }
        });

        // mediaVideoFrame.setVisibility(View.GONE);
        resizePlayer();
        setRipple();

        imgDownload.setOnClickListener(this);
        imgShowPlayList.setOnClickListener(this);
        imgPlay.setOnClickListener(this);
    }

    private void resizePlayer() {

        int h = (int) (AppConfig.width * 0.7f);
        mediaVideoFrame.getLayoutParams().height = h;
        mediaVideoFrame.getLayoutParams().width = AppConfig.width;

        mExoPlayerView.getLayoutParams().height = h;
        mExoPlayerView.getLayoutParams().width = AppConfig.width;

        relativePreview.getLayoutParams().height = h;
        relativePreview.getLayoutParams().width = AppConfig.width;
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
    public void onPause() {
        super.onPause();

        releasePlayer();
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

    private void setRipple() {

        ripple(imgShowPlayList, 24);
        ripple(imgDownload, 24);
        ripple(imgReady, 24);
        ripple(imgFavorite, 24);
        ripple(imgShare, 24);
        ripple(imgLike, 24);

    }

    private boolean checkExistVideo(ArrayList<DownloadUrl> downloadUrls) {

        for (int i = 0; i < downloadUrls.size(); i++) {

            String url = downloadUrls.get(i).getUrl();

            String mediaPath = FileManager.getPathFromAllaUrl(url);
            String fileName = FileManager.getFileNameFromUrl(url);
            File file = new File(FileManager.getRootPath() + mediaPath + "/" + fileName);
            if (file.exists()) {
                imgDownload.setVisibility(View.GONE);
                imgReady.setVisibility(View.VISIBLE);
                mUrl = file.getPath();
                return true;
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
    private void initWakeLockScreen() {
        pm = (PowerManager) getContext().getSystemService(POWER_SERVICE);
        wl = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK
                | PowerManager.ACQUIRE_CAUSES_WAKEUP
                | PowerManager.ON_AFTER_RELEASE, "alla");


        km = (KeyguardManager) getContext().getSystemService(KEYGUARD_SERVICE);
        kl = km.newKeyguardLock("alla");

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
}
