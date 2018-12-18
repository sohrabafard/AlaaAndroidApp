package ir.sanatisharif.android.konkur96.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.activity.SettingActivity;
import ir.sanatisharif.android.konkur96.adapter.ExtraItemAdapter;
import ir.sanatisharif.android.konkur96.app.AppConfig;
import ir.sanatisharif.android.konkur96.app.AppConstants;
import ir.sanatisharif.android.konkur96.listener.OnItemClickListener;
import ir.sanatisharif.android.konkur96.model.CategoryItemSet;
import ir.sanatisharif.android.konkur96.model.Content;
import ir.sanatisharif.android.konkur96.model.Events;

import static ir.sanatisharif.android.konkur96.activity.ActivityBase.toastShow;

/**
 * Created by Mohamad on 10/13/2018.
 */

public class ExtraItemFrg extends BaseFragment {

    private RecyclerView myRecyclerView;
    private Toolbar mToolbar;
    private ExtraItemAdapter adapter;
    private ArrayList<Object> categoryItemSets = new ArrayList<>();
    //fake
    private int type;

    public static ExtraItemFrg newInstance(int type) {

        Bundle args = new Bundle();
        args.putInt("type", type);
        ExtraItemFrg fragment = new ExtraItemFrg();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View createFragmentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_extra_video, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);
        setDummyData();

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:

                toastShow("ssss",0);
                Events.CloseFragment closeFragment = new Events.CloseFragment();
                closeFragment.setTagFragments("");
                EventBus.getDefault().post(closeFragment);

                break;

        }

        return super.onOptionsItemSelected(item);
    }
    private void setDummyData() {

        CategoryItemSet videoItemSet = new CategoryItemSet();
        //category dummy
        if (getArguments().getInt("type") == AppConstants.CATEGORY_ITEM_SET) {

            videoItemSet.setId(0);
            videoItemSet.setTitle("صفر تا صد فیزیک کنکور");
            videoItemSet.setAuthor(" کازیان ");
            videoItemSet.setImageUrl("https://cdn.sanatisharif.ir/upload/contentset/departmentlesson/physics1809261648.jpg?w=460&h=259");
            videoItemSet.setDuration("1:23");
            videoItemSet.setView("300");
            categoryItemSets.add(videoItemSet);

            videoItemSet = new CategoryItemSet();
            videoItemSet.setId(1);
            videoItemSet.setTitle("صفر تا صد شیمی کنکور");
            videoItemSet.setAuthor(" مهدی صنیعی ");
            videoItemSet.setImageUrl("https://cdn.sanatisharif.ir/upload/contentset/departmentlesson/shimi_1809301705.jpg?w=460&h=259");
            videoItemSet.setDuration("1:23");
            videoItemSet.setView("300");
            categoryItemSets.add(videoItemSet);

            videoItemSet = new CategoryItemSet();
            videoItemSet.setId(2);
            videoItemSet.setTitle("صفر تا صد حسابان کنکور");
            videoItemSet.setAuthor("  صادق ثابتی ");
            videoItemSet.setImageUrl("https://cdn.sanatisharif.ir/upload/contentset/departmentlesson/hesaban_1806091555.jpg?w=460&h=259");
            videoItemSet.setDuration("1:23");
            videoItemSet.setView("300");
            categoryItemSets.add(videoItemSet);


            videoItemSet = new CategoryItemSet();
            videoItemSet.setId(3);
            videoItemSet.setTitle("صفر تا صد ریاضی تجربی کنکور");
            videoItemSet.setAuthor(" محمد صادق ثابتی ");
            videoItemSet.setImageUrl("https://cdn.sanatisharif.ir/upload/contentset/departmentlesson/riyazi_tajrobi_1809261626.jpg?w=460&h=259");
            videoItemSet.setDuration("1:23");
            videoItemSet.setView("300");
            categoryItemSets.add(videoItemSet);

            videoItemSet = new CategoryItemSet();
            videoItemSet.setId(4);
            videoItemSet.setTitle("صفر تا صد عربی کنکور");
            videoItemSet.setAuthor(" پدرام علیمرادی");
            videoItemSet.setImageUrl("https://cdn.sanatisharif.ir/upload/contentset/departmentlesson/arabi_1806091641.jpg?w=460&h=259");
            videoItemSet.setDuration("1:23");
            videoItemSet.setView("300");
            categoryItemSets.add(videoItemSet);

            videoItemSet = new CategoryItemSet();
            videoItemSet.setId(5);
            videoItemSet.setTitle("صفر تا صد منطق کنکور");
            videoItemSet.setAuthor(" حسام الدین حلالی");
            videoItemSet.setImageUrl("https://cdn.sanatisharif.ir/upload/contentset/departmentlesson/171005032754.jpg?w=460&h=259");
            videoItemSet.setDuration("1:23");
            videoItemSet.setView("300");
            categoryItemSets.add(videoItemSet);

        }
        //content dummy

        if (getArguments().getInt("type") == AppConstants.CONTENT_ITEM_SET) {
            Content content = new Content();
            content.setId(0);
            content.setDocType(R.drawable.ic_pdf);
            content.setTitle("صفر تا صد فیزیک کنکور");
            content.setAuthor(" کازیان ");
            content.setImageUrl("https://cdn.sanatisharif.ir/upload/contentset/departmentlesson/physics1809261648.jpg?w=460&h=259");
            content.setView("300");
            categoryItemSets.add(content);

            content = new Content();
            content.setId(1);
            content.setDocType(R.drawable.ic_pdf);
            content.setTitle("صفر تا صد شیمی کنکور");
            content.setAuthor(" مهدی صنیعی ");
            content.setImageUrl("https://cdn.sanatisharif.ir/upload/contentset/departmentlesson/shimi_1809301705.jpg?w=460&h=259");
            content.setView("300");
            categoryItemSets.add(content);

            content = new Content();
            content.setId(2);
            content.setDocType(R.drawable.ic_html);
            content.setTitle("صفر تا صد حسابان کنکور");
            content.setAuthor("  صادق ثابتی ");
            content.setImageUrl("https://cdn.sanatisharif.ir/upload/contentset/departmentlesson/hesaban_1806091555.jpg?w=460&h=259");
            content.setView("300");
            categoryItemSets.add(content);

            content = new Content();
            content.setId(4);
            content.setDocType(R.drawable.ic_html);
            content.setTitle("صفر تا صد عربی کنکور");
            content.setAuthor(" پدرام علیمرادی");
            content.setImageUrl("https://cdn.sanatisharif.ir/upload/contentset/departmentlesson/arabi_1806091641.jpg?w=460&h=259");
            content.setView("300");
            categoryItemSets.add(content);

            content = new Content();
            content.setId(5);
            content.setDocType(R.drawable.ic_html);
            content.setTitle("صفر تا صد منطق کنکور");
            content.setAuthor(" حسام الدین حلالی");
            content.setImageUrl("https://cdn.sanatisharif.ir/upload/contentset/departmentlesson/171005032754.jpg?w=460&h=259");
            content.setView("300");
            categoryItemSets.add(content);

        }
        adapter.updateList(categoryItemSets);
        adapter.notifyDataSetChanged();

    }

    private void initView(View v) {

        setHasOptionsMenu(true);
        mToolbar = (Toolbar) v.findViewById(R.id.toolbar);
        setToolbar(mToolbar,"");
        //recylerview
        myRecyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        myRecyclerView.setNestedScrollingEnabled(false);
        myRecyclerView.setHasFixedSize(true);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(AppConfig.context, LinearLayoutManager.VERTICAL, false));
        adapter = new ExtraItemAdapter(AppConfig.context, getArguments().getInt("type"));
        myRecyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position, Object item, View view, RecyclerView.ViewHolder vh) {

            }
        });
    }

}

