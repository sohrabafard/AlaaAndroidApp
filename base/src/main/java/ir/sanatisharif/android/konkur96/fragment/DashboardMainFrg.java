package ir.sanatisharif.android.konkur96.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ir.sanatisharif.android.konkur96.R;

/**
 * Created by Mohamad on 10/13/2018.
 */

public class DashboardMainFrg extends Fragment {


    public static DashboardMainFrg newInstance() {

        Bundle args = new Bundle();

        DashboardMainFrg fragment = new DashboardMainFrg();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}


