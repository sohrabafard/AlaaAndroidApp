package ir.sanatisharif.android.konkur96.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ir.sanatisharif.android.konkur96.R;

/**
 * Created by Mohamad on 10/13/2018.
 */

public class CongressMainFrg extends Fragment {

    public static CongressMainFrg newInstance() {

        Bundle args = new Bundle();

        CongressMainFrg fragment = new CongressMainFrg();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_congress, container, false);
    }

}

