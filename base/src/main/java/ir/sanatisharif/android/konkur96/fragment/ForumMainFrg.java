package ir.sanatisharif.android.konkur96.fragment;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.viewmodel.UserViewModel;

/**
 * Created by Mohamad on 10/13/2018.
 */

public class ForumMainFrg extends Fragment {

    private RecyclerView recyclerView;

    public static ForumMainFrg newInstance() {

        Bundle args = new Bundle();

        ForumMainFrg fragment = new ForumMainFrg();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.user_frg, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ViewModel viewModel = ViewModelProviders.of(this).get(UserViewModel.class);

        recyclerView=view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

    }
}