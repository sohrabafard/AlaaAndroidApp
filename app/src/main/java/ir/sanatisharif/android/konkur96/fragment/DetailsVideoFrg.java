package ir.sanatisharif.android.konkur96.fragment;

import android.animation.Animator;
import android.app.AlertDialog;
import android.app.Dialog;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.MappingTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlaybackControlView;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.ui.TrackSelectionView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Util;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.activity.ActivityBase;
import ir.sanatisharif.android.konkur96.adapter.CommentAdapter;
import ir.sanatisharif.android.konkur96.adapter.MainItemAdapter;
import ir.sanatisharif.android.konkur96.app.AppConfig;
import ir.sanatisharif.android.konkur96.dialog.DownloadDialogFrg;
import ir.sanatisharif.android.konkur96.helper.FileManager;
import ir.sanatisharif.android.konkur96.model.Comment;
import ir.sanatisharif.android.konkur96.model.DownloadUrl;
import ir.sanatisharif.android.konkur96.model.MainItem;
import ir.sanatisharif.android.konkur96.model.Video;
import ir.sanatisharif.android.konkur96.model.VideoItemSet;
import ir.sanatisharif.android.konkur96.utils.DownloadFile;
import ir.sanatisharif.android.konkur96.utils.Utils;

import static ir.sanatisharif.android.konkur96.app.AppConfig.context;

/**
 * Created by Mohamad on 10/13/2018.
 */

public class DetailsVideoFrg extends Fragment implements LifecycleOwner, View.OnClickListener {

    private final String STATE_RESUME_WINDOW = "resumeWindow";
    private final String STATE_RESUME_POSITION = "resumePosition";
    private final String STATE_PLAYER_FULLSCREEN = "playerFullscreen";


    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.recyclerView)
    RecyclerView myRecyclerView;
    @BindView(R.id.recyclerViewComment)
    RecyclerView recyclerViewComment;
    @BindView(R.id.imgDownload)
    ImageView imgDownload;
    @BindView(R.id.imgReady)
    ImageView imgReady;
    @BindView(R.id.mediaVideoFrame)
    FrameLayout mediaVideoFrame;

    @BindView(R.id.txtVote)
    TextView txtVote;

    LifecycleRegistry mLifecycleRegistry;

    PlaybackControlView controlView;
    private SimpleExoPlayerView mExoPlayerView;
    private MediaSource mVideoSource;
    private boolean mExoPlayerFullscreen = false;
    private FrameLayout mFullScreenButton;
    private ImageView mFullScreenIcon;
    private Dialog mFullScreenDialog;

    private MainItemAdapter adapter;
    private CommentAdapter commentAdapter;
    private ArrayList<MainItem> items = new ArrayList<>();
    private ArrayList<Comment> comments = new ArrayList<>();

    private int mResumeWindow;
    private long mResumePosition;
    private String mUrl;///= "https://cdn.sanatisharif.ir/media/170/240p/170023fghg.mp4";
    private ArrayList<DownloadUrl> downloadUrls = new ArrayList<>();
    private boolean downloadReady = false;
    private ImageButton imgPlay, imgPause;

    public static DetailsVideoFrg newInstance() {

        Bundle args = new Bundle();
        DetailsVideoFrg fragment = new DetailsVideoFrg();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mLifecycleRegistry = new LifecycleRegistry(this);
        mLifecycleRegistry.markState(Lifecycle.State.CREATED);
    }

    @Override
    public LifecycleRegistry getLifecycle() {
        return mLifecycleRegistry;
    }

    @Override
    public void onStart() {
        super.onStart();

        mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_START);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);

        initView(view);

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

        setData();

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

    private void setData() {

        //video
        ArrayList<VideoItemSet> videoItemSets = new ArrayList<>();

        VideoItemSet videoItemSet = new VideoItemSet();
        videoItemSet.setId(0);
        videoItemSet.setTitle("صفر تا صد فیزیک کنکور");
        videoItemSet.setAuthor(" کازیان ");
        videoItemSet.setUrl("https://cdn.sanatisharif.ir/upload/contentset/departmentlesson/physics1809261648.jpg?w=253&h=142");
        videoItemSets.add(videoItemSet);

        videoItemSet = new VideoItemSet();
        videoItemSet.setId(1);
        videoItemSet.setTitle("صفر تا صد شیمی کنکور");
        videoItemSet.setAuthor(" مهدی صنیعی ");
        videoItemSet.setUrl("https://cdn.sanatisharif.ir/upload/contentset/departmentlesson/shimi_1809301705.jpg?w=253&h=142");
        videoItemSets.add(videoItemSet);

        videoItemSet = new VideoItemSet();
        videoItemSet.setId(2);
        videoItemSet.setTitle("صفر تا صد حسابان کنکور");
        videoItemSet.setAuthor(" محمد صادق ثابتی ");
        videoItemSet.setUrl("https://cdn.sanatisharif.ir/upload/contentset/departmentlesson/hesaban_1806091555.jpg?w=253&h=142");
        videoItemSets.add(videoItemSet);


        videoItemSet = new VideoItemSet();
        videoItemSet.setId(3);
        videoItemSet.setTitle("صفر تا صد ریاضی تجربی کنکور");
        videoItemSet.setAuthor(" محمد صادق ثابتی ");
        videoItemSet.setUrl("https://cdn.sanatisharif.ir/upload/contentset/departmentlesson/riyazi_tajrobi_1809261626.jpg?w=253&h=142");
        videoItemSets.add(videoItemSet);

        videoItemSet = new VideoItemSet();
        videoItemSet.setId(4);
        videoItemSet.setTitle("صفر تا صد عربی کنکور");
        videoItemSet.setAuthor(" پدرام علیمرادی");
        videoItemSet.setUrl("https://cdn.sanatisharif.ir/upload/contentset/departmentlesson/arabi_1806091641.jpg?w=253&h=142");
        videoItemSets.add(videoItemSet);

        videoItemSet = new VideoItemSet();
        videoItemSet.setId(5);
        videoItemSet.setTitle("صفر تا صد منطق کنکور");
        videoItemSet.setAuthor(" حسام الدین حلالی");
        videoItemSet.setUrl("https://cdn.sanatisharif.ir/upload/contentset/departmentlesson/171005032754.jpg?w=253&h=142");
        videoItemSets.add(videoItemSet);


        MainItem item = new MainItem();
        item.setId(0);
        item.setType(0);
        item.setTitle("کنکور نظام جدید ");
        item.setItems(videoItemSets);
        items.add(item);


        //---------------------------------
        videoItemSets = new ArrayList<>();
        videoItemSet = new VideoItemSet();
        videoItemSet.setId(0);
        videoItemSet.setTitle("صفر تا صد زبان");
        videoItemSet.setAuthor("علی اکبر عزتی");
        videoItemSet.setUrl("https://cdn.sanatisharif.ir/upload/contentset/departmentlesson/zaban11_1810070959.jpg?w=253&h=142");
        videoItemSets.add(videoItemSet);

        videoItemSet = new VideoItemSet();
        videoItemSet.setId(1);
        videoItemSet.setTitle("زیست یازدهم");
        videoItemSet.setAuthor(" جلال موقاری");
        videoItemSet.setUrl("https://cdn.sanatisharif.ir/upload/contentset/departmentlesson/zist_yazdahom_1810011438.jpg?w=253&h=142");
        videoItemSets.add(videoItemSet);

        videoItemSet = new VideoItemSet();
        videoItemSet.setId(2);
        videoItemSet.setTitle("صفر تا صد فیزیک یازدهم");
        videoItemSet.setAuthor(" فرشید داداشی");
        videoItemSet.setUrl("https://cdn.sanatisharif.ir/upload/contentset/departmentlesson/170920041635.jpg?w=253&h=142");
        videoItemSets.add(videoItemSet);

        videoItemSet = new VideoItemSet();
        videoItemSet.setId(3);
        videoItemSet.setTitle("زیست یازدهم");
        videoItemSet.setAuthor(" عباس راسنی");
        videoItemSet.setUrl("https://cdn.sanatisharif.ir/upload/contentset/departmentlesson/171019113948.jpg?w=253&h=142");
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
    }

    private void initView(View view) {

        resizePlayer();
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

        imgDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              /*  DownloadDialogFrg downloadDialogFrg = new DownloadDialogFrg();
                downloadDialogFrg.show(((AppCompatActivity) getActivity()).getSupportFragmentManager(), "dialog");*/

                DownloadDialogFrg.newInstance(downloadUrls).
                        show(((AppCompatActivity) getActivity()).getSupportFragmentManager(), "dialog");

            }
        });
    }

    private void resizePlayer() {

        mediaVideoFrame.getLayoutParams().height = (int) (AppConfig.width * 0.70f);
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
        imgPlay = (ImageButton) controlView.findViewById(R.id.exo_play);
        imgPause = (ImageButton) controlView.findViewById(R.id.exo_pause);
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
    }


    private void initExoPlayer() {

        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
        LoadControl loadControl = new DefaultLoadControl();

        SimpleExoPlayer player = ExoPlayerFactory.newSimpleInstance(new DefaultRenderersFactory(getContext()), trackSelector, loadControl);
        mExoPlayerView.setPlayer(player);

        boolean haveResumePosition = mResumeWindow != C.INDEX_UNSET;

        if (haveResumePosition) {
            player.seekTo(mResumeWindow, mResumePosition);
        }

        ((SimpleExoPlayer) mExoPlayerView.getPlayer()).addListener(new PlayerEventListener());
        ((SimpleExoPlayer) mExoPlayerView.getPlayer()).prepare(mVideoSource, !haveResumePosition, false);
        ((SimpleExoPlayer) mExoPlayerView.getPlayer()).setPlayWhenReady(true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onStop() {
        super.onStop();
        mLifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_STOP);
        releasePlayer();
    }

    @Override
    public void onResume() {
        super.onResume();

        if (mExoPlayerView == null) {

            mExoPlayerView = (SimpleExoPlayerView) getView().findViewById(R.id.exoplayer);

            if (!checkExistVideo())
                mUrl = downloadUrls.get(0).getUrl();
            initFullscreenDialog();
            initFullscreenButton();

           /* String streamUrl = "https://cdn.sanatisharif.ir/media/202/HD_720p/202054dfgf.mp4";
            String userAgent = Util.getUserAgent(AppConfig.currentActivity, AppConfig.context.getApplicationInfo().packageName);
            DefaultHttpDataSourceFactory httpDataSourceFactory = new DefaultHttpDataSourceFactory(userAgent, null, DefaultHttpDataSource.DEFAULT_CONNECT_TIMEOUT_MILLIS, DefaultHttpDataSource.DEFAULT_READ_TIMEOUT_MILLIS, true);
            DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(AppConfig.currentActivity, null, httpDataSourceFactory);
            Uri daUri = Uri.parse(streamUrl);*/

            DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(context,
                    Util.getUserAgent(context, "mediaPlayerSample"));
            mVideoSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(Uri.parse(mUrl));

        }

        initExoPlayer();

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

    @Override
    public void onClick(View view) {

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

    private boolean checkExistVideo() {

        for (int i = 0; i < downloadUrls.size(); i++) {

            String url = downloadUrls.get(i).getUrl();

            String mediaPath = FileManager.getPathFromAllaUrl(url);
            String fileName = FileManager.getFileNameFromUrl(url);
            File file = new File(FileManager.getRootPath() + mediaPath + "/" + fileName);
            if (file.exists()) {
                imgDownload.setVisibility(View.GONE);
                imgReady.setVisibility(View.VISIBLE);
                downloadReady = true;
                mUrl = file.getPath();
                return true;
            }

        }
        return false;
    }

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

}
