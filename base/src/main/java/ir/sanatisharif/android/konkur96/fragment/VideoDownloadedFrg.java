package ir.sanatisharif.android.konkur96.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;

import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.activity.ActivityBase;
import ir.sanatisharif.android.konkur96.adapter.VideoDownloadedAdapter;
import ir.sanatisharif.android.konkur96.app.AppConfig;
import ir.sanatisharif.android.konkur96.app.AppConstants;
import ir.sanatisharif.android.konkur96.helper.FileManager;
import ir.sanatisharif.android.konkur96.listener.OnItemCheckedListener;
import ir.sanatisharif.android.konkur96.listener.OnItemLongListener;
import ir.sanatisharif.android.konkur96.model.Events;
import ir.sanatisharif.android.konkur96.model.FileDiskModel;
import ir.sanatisharif.android.konkur96.ui.view.MDToast;

/**
 * Created by Mohamad on 10/13/2018.
 */

public class VideoDownloadedFrg extends BaseFragment {

    RecyclerView recyclerView;
    Toolbar      mToolbar;
    boolean      visibleMenu = false;
    private Menu                     menu;
    private VideoDownloadedAdapter   adapter;
    private GridLayoutManager        manager;
    private ArrayList<FileDiskModel> fileDiskModels = new ArrayList<>();

    public static VideoDownloadedFrg newInstance() {

        Bundle             args     = new Bundle();
        VideoDownloadedFrg fragment = new VideoDownloadedFrg();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View createFragmentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_video_downloaded, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initUI(view);
        getListVideos();
    }

    @Override
    public void onDetach() {
        super.onDetach();

        fileDiskModels.clear();
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

        if (item.getItemId() == android.R.id.home) {
            Events.CloseFragment closeFragment = new Events.CloseFragment();
            closeFragment.setTagFragments("");
            EventBus.getDefault().post(closeFragment);

        } else if (item.getItemId() == R.id.actionDelete) {

            Events.VideoDeleted videoDeleted = new Events.VideoDeleted();
            int                 deleteCount  = 0;
            // convert list to array
            // clear video list
            // hard delete video file
            // and add array into list
            FileDiskModel[]
                    fileDiskModel_temp = fileDiskModels.toArray(new FileDiskModel[fileDiskModels.size()]);
            fileDiskModels.clear();
            for (int i = 0; i < fileDiskModel_temp.length; i++) {

                if (fileDiskModel_temp[i].isChecked()) {
                    if (FileManager.deleteFileAndCheck(fileDiskModel_temp[i].getPath())) {
                        deleteCount++;

                        videoDeleted.setPosition(i);
                        EventBus.getDefault().post(videoDeleted);
                    }
                } else
                    fileDiskModels.add(fileDiskModel_temp[i]);
            }

            if (deleteCount != 0)
                ActivityBase.toastShow(deleteCount + "  ویدیو حذف شد.  ", MDToast.TYPE_SUCCESS);

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
        setToolbar(mToolbar, "نمایش ویدیوها");

        recyclerView = view.findViewById(R.id.recyclerView);
        adapter =
                new VideoDownloadedAdapter(AppConfig.context, fileDiskModels, AppConstants.VIDEO_SHOW_GRID);
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

                FileDiskModel v = fileDiskModels.get(position);
                v.setChecked(check);
                fileDiskModels.set(position, v);

            }
        });
    }

    private void resetCheckBox() {
        int index = 0;
        for (FileDiskModel v : fileDiskModels) {
            index++;
            if (v.isChecked()) {
                v.setChecked(false);
                fileDiskModels.set(index, v);
            }
        }
    }

    private void showMenu() {

        MenuItem item = menu.findItem(R.id.actionDelete);
        item.setVisible(true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        fileDiskModels.clear();
    }

    private void getListVideos() {

        //get video offline
        FileManager.getInstance().clearFilesList();
        FileManager.getInstance().getFilesInDirs(new File(FileManager.getMediaPath()));
        ArrayList<File> files = FileManager.getInstance().getFilesArrayList();
        fileDiskModels.clear();
        if (files != null) {

            for (File f : files) {

                FileDiskModel v = new FileDiskModel();
                v.setName(f.getName());
                v.setPath(f.getPath());
                v.setSize(f.length() + "");
                fileDiskModels.add(v);
            }

            adapter.notifyDataSetChanged();
        } else
            ActivityBase.toastShow("خالی است", MDToast.TYPE_INFO);
    }
}

