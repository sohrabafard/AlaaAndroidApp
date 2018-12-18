package ir.sanatisharif.android.konkur96.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.util.ArrayList;

import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.activity.SettingActivity;
import ir.sanatisharif.android.konkur96.adapter.MainItemAdapter;
import ir.sanatisharif.android.konkur96.adapter.VideoDownloadedAdapter;
import ir.sanatisharif.android.konkur96.app.AppConfig;
import ir.sanatisharif.android.konkur96.app.AppConstants;
import ir.sanatisharif.android.konkur96.helper.FileManager;
import ir.sanatisharif.android.konkur96.model.BannerItem;
import ir.sanatisharif.android.konkur96.model.CategoryItemSet;
import ir.sanatisharif.android.konkur96.model.Content;
import ir.sanatisharif.android.konkur96.model.Events;
import ir.sanatisharif.android.konkur96.model.MainItem;
import ir.sanatisharif.android.konkur96.model.Video;
import ir.sanatisharif.android.konkur96.model.ViewSlider;
import ir.sanatisharif.android.konkur96.ui.GlideApp;
import ir.sanatisharif.android.konkur96.ui.view.CircleTransform;

import static ir.sanatisharif.android.konkur96.activity.MainActivity.addFrg;

/**
 * Created by Mohamad on 10/13/2018.
 */

public class DashboardMainFrg1 extends BaseFragment {

    private Button btnRegister;
    private ImageView imgUser;
    private Toolbar mToolbar;
    private RecyclerView myRecyclerView;

    private MainItemAdapter adapter;
    private ArrayList<MainItem> items = new ArrayList<>();

    public static DashboardMainFrg1 newInstance() {

        Bundle args = new Bundle();

        DashboardMainFrg1 fragment = new DashboardMainFrg1();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View createFragmentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dashboard1, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        initUi(view);
        setDummyData();

        GlideApp.with(AppConfig.context)
                .load("http://yakhmakgroup.ir/jokLike/v1/images/imageLogo/1765554797035373646.jpg")
                .transform(new CircleTransform(AppConfig.context))
                .into(imgUser);


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addFrg(RegisterFrg.newInstance(), "RegisterFrg");
            }
        });
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


        RecyclerView myRecyclerView;

        btnRegister = view.findViewById(R.id.btnRegister);
        imgUser = view.findViewById(R.id.imgUser);
        myRecyclerView = view.findViewById(R.id.recyclerView);

        setToolbar(mToolbar, "داشبورد");

        myRecyclerView.setNestedScrollingEnabled(false);
        myRecyclerView.setHasFixedSize(true);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(AppConfig.context, LinearLayoutManager.VERTICAL, false));
        adapter = new MainItemAdapter(AppConfig.context, items);
        adapter.setSize(AppConfig.width, AppConfig.height);
        myRecyclerView.setAdapter(adapter);

    }

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


        ArrayList<Video> videos = (ArrayList<Video>) items.get(0).getItems();//item 0 is video list

        videos.remove(videoDeleted.getPosition());
        items.get(0).setItems(videos);
        adapter.notifyItemRemoved(videoDeleted.getPosition());
    }

    private void setDummyData() {


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


            adapter.notifyDataSetChanged();
        }

        MainItem item = new MainItem();
        item.setId(0);
        item.setType(AppConstants.VIDEO_OFFLINE_ITEM);
        item.setTitle("دانلود شده ها");
        item.setItems(videos);
        items.add(item);

        //row 1-> category
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


        item = new MainItem();
        item.setId(0);
        item.setType(AppConstants.CATEGORY_ITEM_SET);
        item.setTitle("محبوبترین");
        item.setItems(videoItemSets);
        items.add(item);

        //-------------------content-----------------------------------------

        //row 3 -> category

        videoItemSets = new ArrayList<>();
        videoItemSet = new CategoryItemSet();
        videoItemSet.setId(0);
        videoItemSet.setTitle("زیست کنکور");
        videoItemSet.setAuthor(" ابوالفضل جعفری");
        videoItemSet.setImageUrl("https://cdn.sanatisharif.ir/upload/contentset/departmentlesson/171125105021.jpg?w=253&h=142");
        videoItemSets.add(videoItemSet);

        videoItemSet = new CategoryItemSet();
        videoItemSet.setId(1);
        videoItemSet.setTitle("آرایه های ادبی");
        videoItemSet.setAuthor(" ابوالفضل جعفری");
        videoItemSet.setImageUrl("https://cdn.sanatisharif.ir/upload/contentset/departmentlesson/170917011741.jpg?w=253&h=142");
        videoItemSets.add(videoItemSet);

        videoItemSet = new CategoryItemSet();
        videoItemSet.setId(2);
        videoItemSet.setTitle("مشاوره");
        videoItemSet.setAuthor(" محمد علی امینی راد");
        videoItemSet.setImageUrl("https://cdn.sanatisharif.ir/upload/contentset/departmentlesson/140701010549.jpg?w=253&h=142");
        videoItemSets.add(videoItemSet);

        videoItemSet = new CategoryItemSet();
        videoItemSet.setId(3);
        videoItemSet.setTitle("شیمی شب کنکور");
        videoItemSet.setAuthor(" مهدی صنیعی");
        videoItemSet.setImageUrl("https://cdn.sanatisharif.ir/upload/contentset/departmentlesson/170920034146.jpg?w=253&h=142");
        videoItemSets.add(videoItemSet);


        videoItemSet = new CategoryItemSet();
        videoItemSet.setId(3);
        videoItemSet.setTitle("نکته و تست فیزیک");
        videoItemSet.setAuthor(" پیمان طلوعی");
        videoItemSet.setImageUrl("https://cdn.sanatisharif.ir/upload/contentset/departmentlesson/170925055613.jpg?w=253&h=142");
        videoItemSets.add(videoItemSet);


        item = new MainItem();
        item.setId(1);
        item.setType(AppConstants.CATEGORY_ITEM_SET);
        item.setTitle("کنکور نظام قدیم");
        item.setItems(videoItemSets);
        items.add(item);


        adapter.notifyDataSetChanged();
    }
}


