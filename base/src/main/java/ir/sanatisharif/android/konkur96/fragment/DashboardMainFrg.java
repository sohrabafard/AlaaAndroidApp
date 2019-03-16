package ir.sanatisharif.android.konkur96.fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.common.wrappers.InstantApps;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.account.AccountInfo;
import ir.sanatisharif.android.konkur96.adapter.MainItemAdapter;
import ir.sanatisharif.android.konkur96.app.AppConfig;
import ir.sanatisharif.android.konkur96.app.AppConstants;
import ir.sanatisharif.android.konkur96.helper.FileManager;
import ir.sanatisharif.android.konkur96.model.Events;
import ir.sanatisharif.android.konkur96.model.MainItem;
import ir.sanatisharif.android.konkur96.model.Video;
import ir.sanatisharif.android.konkur96.model.user.User;
import ir.sanatisharif.android.konkur96.ui.GlideApp;
import ir.sanatisharif.android.konkur96.ui.view.CircleTransform;

import static ir.sanatisharif.android.konkur96.activity.MainActivity.addFrg;
import static ir.sanatisharif.android.konkur96.app.AppConstants.ACCOUNT_TYPE;

/**
 * Created by Mohamad on 10/13/2018.
 */

public class DashboardMainFrg extends BaseFragment {

    private User user;
    private AccountInfo accountInfo;
    private LinearLayout linStudentInfo, linDashboardHeader, linStudentImage;
    private ImageView imgUser;
    private Toolbar mToolbar;
    private RecyclerView myRecyclerView;
    private TextView txtNationalCode, txtMobile, txtFullName, txtField, btnOpenCard;

    private MainItemAdapter adapter;
    private List<MainItem> items = new ArrayList<>();

    public static DashboardMainFrg newInstance() {

        Bundle args = new Bundle();

        DashboardMainFrg fragment = new DashboardMainFrg();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View createFragmentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        accountInfo = new AccountInfo(getContext(), getActivity());
        user = accountInfo.getInfo(ACCOUNT_TYPE);
        initUi(view);
        setData();

    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_dashbard, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:

                Events.CloseFragment closeFragment = new Events.CloseFragment();
                closeFragment.setTagFragments("");
                EventBus.getDefault().post(closeFragment);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initUi(View view) {

        setToolbar(mToolbar, "داشبورد");

        //init
        linDashboardHeader = view.findViewById(R.id.linDashboardHeader);
        linStudentInfo = view.findViewById(R.id.linStudentInfo);
        imgUser = view.findViewById(R.id.imgUser);
        myRecyclerView = view.findViewById(R.id.recyclerView);
        txtFullName = view.findViewById(R.id.txtFullName);
        txtField = view.findViewById(R.id.txtField);
        txtMobile = view.findViewById(R.id.txtMobile);
        txtNationalCode = view.findViewById(R.id.txtNationalCode);
        btnOpenCard = view.findViewById(R.id.btn_open_card);

        //set adapter recyclerview
        myRecyclerView.setNestedScrollingEnabled(false);
        myRecyclerView.setHasFixedSize(true);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(AppConfig.context, LinearLayoutManager.VERTICAL, false));
        adapter = new MainItemAdapter(AppConfig.context, items,GlideApp.with(this));
        adapter.setSize(AppConfig.width, AppConfig.height);
        myRecyclerView.setAdapter(adapter);

        //------ overlay linear student over header
    }

    //<editor-fold desc="set data">
    private void setData() {
        if (user != null) {
            GlideApp.with(AppConfig.context)
                    .load("")
                    .transform(new CircleTransform(getContext()))
                    .placeholder(R.mipmap.ic_launcher)
                    .into(imgUser);

            if (user.getLastName() != null && user.getFirstName() != null)
                txtFullName.setText(user.getFirstName() + " " + user.getLastName());
            if (user.getInfo().getMajor().getName() != null)
                txtField.setText(user.getInfo().getMajor().getName());
            if (user.getMobile() != null)
                txtMobile.setText(user.getMobile());
            if (user.getNationalCode() != null)
                txtNationalCode.setText(user.getNationalCode());
        }

        loadVideoOffline();

        btnOpenCard.setOnClickListener(view -> addFrg(CardFragment.newInstance(),"CardFragment"));
    }
    //</editor-fold>


    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void getVideos(Events.VideoDeleted videoDeleted) {
//
//        List<Video> videos = items.get(0).getVideos();//item 0 is video list
//        videos.remove(videoDeleted.getPosition());
//        Log.i("LOG", "getVideos: "+videoDeleted.getPosition());
//        items.get(0).setVideos(videos);
//        adapter.notifyItemRemoved(videoDeleted.getPosition());
    }

    private void loadVideoOffline() {

        MainItem item;

        // Only runs on API levels < 26.
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            // Only runs in an Installed App.
            if (!InstantApps.isInstantApp(getContext())) {

                if (FileManager.checkFileExist(FileManager.getMediaPath())) {

                    //add header to recycler view
                    item = new MainItem();
                    item.setId(0);
                    item.setTitle("دانلود شده ها");
                    item.setUrl(AppConstants.MORE_VIDEO_OFFLINE);
                    item.setType(AppConstants.HEADER_DATA);
                    items.add(item);

                    //get video offline
                    FileManager.getInstance().clearFilesList();
                    FileManager.getInstance().getFilesInDirs(new File(FileManager.getMediaPath()));
                    ArrayList<File> files = FileManager.getInstance().getFilesArrayList();
                    ArrayList<Video> videos = new ArrayList<>();

                    if (files != null) {

                        for (File f : files) {

                            Video v = new Video();
                            v.setName(f.getName());
                            v.setPath(f.getPath());
                            v.setSize(f.length() + "");
                            videos.add(v);
                        }
                    }
                    item = new MainItem();
                    item.setId(0);
                    item.setType(AppConstants.VIDEO_OFFLINE_ITEM);
                    item.setVideos(videos);
                    items.add(item);

                }
            }
        }


        adapter.notifyDataSetChanged();

    }
}


