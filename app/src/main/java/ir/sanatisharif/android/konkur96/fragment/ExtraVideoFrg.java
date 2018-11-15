package ir.sanatisharif.android.konkur96.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.adapter.ExtraVideoItemAdapter;
import ir.sanatisharif.android.konkur96.app.AppConfig;
import ir.sanatisharif.android.konkur96.listener.OnItemClickListener1111;
import ir.sanatisharif.android.konkur96.model.VideoItemSet;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by Mohamad on 10/13/2018.
 */

public class ExtraVideoFrg extends Fragment {

    private RecyclerView myRecyclerView;
    private Toolbar mToolbar;
    private ExtraVideoItemAdapter adapter;
    private ArrayList<VideoItemSet> items = new ArrayList<>();

    public static ExtraVideoFrg newInstance() {

        Bundle args = new Bundle();

        ExtraVideoFrg fragment = new ExtraVideoFrg();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_extra_video, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);
        setDummyData();

    }

    private void setDummyData() {

        VideoItemSet videoItemSet = new VideoItemSet();
        videoItemSet.setId(0);
        videoItemSet.setTitle("صفر تا صد فیزیک کنکور");
        videoItemSet.setAuthor(" کازیان ");
        videoItemSet.setUrl("https://cdn.sanatisharif.ir/upload/contentset/departmentlesson/physics1809261648.jpg?w=253&h=142");
        videoItemSet.setDuration("1:23");
        videoItemSet.setView("300");
        items.add(videoItemSet);

        videoItemSet = new VideoItemSet();
        videoItemSet.setId(1);
        videoItemSet.setTitle("صفر تا صد شیمی کنکور");
        videoItemSet.setAuthor(" مهدی صنیعی ");
        videoItemSet.setUrl("https://cdn.sanatisharif.ir/upload/contentset/departmentlesson/shimi_1809301705.jpg?w=253&h=142");
        videoItemSet.setDuration("1:23");
        videoItemSet.setView("300");
        items.add(videoItemSet);

        videoItemSet = new VideoItemSet();
        videoItemSet.setId(2);
        videoItemSet.setTitle("صفر تا صد حسابان کنکور");
        videoItemSet.setAuthor("  صادق ثابتی ");
        videoItemSet.setUrl("https://cdn.sanatisharif.ir/upload/contentset/departmentlesson/hesaban_1806091555.jpg?w=253&h=142");
        videoItemSet.setDuration("1:23");
        videoItemSet.setView("300");
        items.add(videoItemSet);


        videoItemSet = new VideoItemSet();
        videoItemSet.setId(3);
        videoItemSet.setTitle("صفر تا صد ریاضی تجربی کنکور");
        videoItemSet.setAuthor(" محمد صادق ثابتی ");
        videoItemSet.setUrl("https://cdn.sanatisharif.ir/upload/contentset/departmentlesson/riyazi_tajrobi_1809261626.jpg?w=253&h=142");
        videoItemSet.setDuration("1:23");
        videoItemSet.setView("300");
        items.add(videoItemSet);

        videoItemSet = new VideoItemSet();
        videoItemSet.setId(4);
        videoItemSet.setTitle("صفر تا صد عربی کنکور");
        videoItemSet.setAuthor(" پدرام علیمرادی");
        videoItemSet.setUrl("https://cdn.sanatisharif.ir/upload/contentset/departmentlesson/arabi_1806091641.jpg?w=253&h=142");
        videoItemSet.setDuration("1:23");
        videoItemSet.setView("300");
        items.add(videoItemSet);

        videoItemSet = new VideoItemSet();
        videoItemSet.setId(5);
        videoItemSet.setTitle("صفر تا صد منطق کنکور");
        videoItemSet.setAuthor(" حسام الدین حلالی");
        videoItemSet.setUrl("https://cdn.sanatisharif.ir/upload/contentset/departmentlesson/171005032754.jpg?w=253&h=142");
        videoItemSet.setDuration("1:23");
        videoItemSet.setView("300");
        items.add(videoItemSet);

        adapter.notifyDataSetChanged();

    }

    private void initView(View v) {

        mToolbar = (Toolbar) v.findViewById(R.id.toolbar);
        //recylerview
        myRecyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        myRecyclerView.setNestedScrollingEnabled(false);
        myRecyclerView.setHasFixedSize(true);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(AppConfig.context, LinearLayoutManager.VERTICAL, false));
        adapter = new ExtraVideoItemAdapter(AppConfig.context, items);
        myRecyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new OnItemClickListener1111() {
            @Override
            public void onItemClick(int position, View view, RecyclerView.ViewHolder vh) {

            }
        });
    }

}

