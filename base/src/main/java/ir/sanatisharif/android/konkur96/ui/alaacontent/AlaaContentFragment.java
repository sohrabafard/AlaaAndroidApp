package ir.sanatisharif.android.konkur96.ui.alaacontent;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

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
