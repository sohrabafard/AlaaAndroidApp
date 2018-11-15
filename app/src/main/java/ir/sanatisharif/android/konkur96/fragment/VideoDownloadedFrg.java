package ir.sanatisharif.android.konkur96.fragment;


import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.activity.ActivityBase;
import ir.sanatisharif.android.konkur96.adapter.VideoDownloadedAdapter;
import ir.sanatisharif.android.konkur96.app.AppConfig;
import ir.sanatisharif.android.konkur96.helper.FileManager;
import ir.sanatisharif.android.konkur96.listener.OnItemCheckedListener;
import ir.sanatisharif.android.konkur96.listener.OnItemLongListener;
import ir.sanatisharif.android.konkur96.model.Video;
import ir.sanatisharif.android.konkur96.ui.view.MDToast;

import static android.net.Uri.EMPTY;

/**
 * Created by Mohamad on 10/13/2018.
 */

public class VideoDownloadedFrg extends Fragment {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.txtToolbarTitle)
    TextView txtToolbarTitle;


    private Menu menu;
    private VideoDownloadedAdapter adapter;
    private GridLayoutManager manager;
    private ArrayList<Video> videos = new ArrayList<>();
    boolean visibleMenu = false;

    public static VideoDownloadedFrg newInstance() {

        Bundle args = new Bundle();

        VideoDownloadedFrg fragment = new VideoDownloadedFrg();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_video_downloaded, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);

        initUI(view);
        getListVideos();
    }

    @Override
    public void onDetach() {
        super.onDetach();

        videos.clear();
        if (adapter == null)
            adapter = null;

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_video_downloded_fragment, menu);
        this.menu = menu;
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.actionDelete) {

            int deleteCount = 0;
            // convert list to array
            // clear video list
            // hard delete video file
            // and add array into list
            Video[] video_temp = videos.toArray(new Video[videos.size()]);
            videos.clear();
            for (int i = 0; i < video_temp.length; i++) {

                if (video_temp[i].isChecked()) {
                    if (FileManager.deleteFileAndCheck(FileManager.getVideoPath() + video_temp[i].getName())) {
                        deleteCount++;
                    }
                } else
                    videos.add(video_temp[i]);
            }

            if (deleteCount != 0)
                ActivityBase.toastShow(deleteCount + "ویدیو حذف شدند. ", MDToast.TYPE_SUCCESS);

            resetCheckBox();
            adapter.notifyDataSetChanged();
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onBack() {

        MenuItem item = menu.findItem(R.id.actionDelete);
        visibleMenu = item.isVisible();
        if (visibleMenu) {
            item.setVisible(false);
            visibleMenu = false;
            adapter.setVisibleChk(visibleMenu);

            //reset checkBox
            resetCheckBox();

            adapter.notifyDataSetChanged();

            return true;
        }

        return false;
    }

    //--------------methods----------------

    private void initUI(View view) {

        setHasOptionsMenu(true);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        //((AppCompatActivity) getActivity()).getSupportActionBar().setTitle();
        txtToolbarTitle.setText("نمایش ویدیوها");

        adapter = new VideoDownloadedAdapter(AppConfig.context, videos, R.layout.media_layout);
        manager = new GridLayoutManager(AppConfig.context, 3);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemLongListener(new OnItemLongListener() {
            @Override
            public void onItemClick(int position, Object item, View view, RecyclerView.ViewHolder vh) {

                if (!visibleMenu) {
                    visibleMenu = true;
                    showMenu();
                    adapter.setVisibleChk(visibleMenu);
                    adapter.notifyDataSetChanged();
                }

            }
        });

        adapter.setOnItemCheckedListener(new OnItemCheckedListener() {
            @Override
            public void onItemClick(int position, Object item, View view, RecyclerView.ViewHolder vh, boolean check) {

                Video v = videos.get(position);
                v.setChecked(check);
                videos.set(position, v);

            }
        });
    }

    private void resetCheckBox() {
        int index = 0;
        for (Video v : videos) {
            if (v.isChecked()) {
                v.setChecked(false);
                videos.set(index, v);
            }

        }
    }

    private void showMenu() {

        MenuItem item = menu.findItem(R.id.actionDelete);
        item.setVisible(true);
    }

    private void getListVideos() {

        if (FileManager.checkFileExist(FileManager.getVideoPath())) {
            List<File> temp = FileManager.getFileList();

            if (temp == null) {
                ActivityBase.toastShow("خالی است", MDToast.TYPE_INFO);
            } else {

                for (File f : temp) {

                    Video v = new Video();
                    v.setName(f.getName());
                    v.setSize(f.length() + "");
                    videos.add(v);
                }
                adapter.notifyDataSetChanged();
            }
        }
    }
}

