package ir.sanatisharif.android.konkur96.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.greenrobot.eventbus.EventBus;

import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.model.Events;

/**
 * Created by Mohamad on 10/13/2018.
 */

public class CongressMainFrg extends BaseFragment {

    Button btnChangeLiveData;

    public static CongressMainFrg newInstance() {

        Bundle args = new Bundle();

        CongressMainFrg fragment = new CongressMainFrg();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View createFragmentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_congress, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


     btnChangeLiveData=view.findViewById(R.id.btnChangeLiveData);

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

}

