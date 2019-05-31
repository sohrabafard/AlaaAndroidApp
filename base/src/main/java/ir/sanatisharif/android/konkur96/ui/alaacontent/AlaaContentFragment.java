package ir.sanatisharif.android.konkur96.ui.alaacontent;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ir.sanatisharif.android.konkur96.R;

public class AlaaContentFragment extends Fragment {

    private AlaaContentViewModel mViewModel;

    public static AlaaContentFragment newInstance() {
        return new AlaaContentFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.alaa_content_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(AlaaContentViewModel.class);
        // TODO: Use the ViewModel
    }

}
