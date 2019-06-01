package ir.sanatisharif.android.konkur96.ui.alaacontent;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.crashlytics.android.Crashlytics;

import ir.sanatisharif.android.konkur96.Models.ContentModel;
import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.interfaces.LogUserActionsOnPublicContentInterface;
import ir.sanatisharif.android.konkur96.model.ContentCredit;

public class AlaaContentFragment extends Fragment {
    
    private AlaaContentViewModel mViewModel;
    
    private LogUserActionsOnPublicContentInterface mUserAction;
    
    
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
        
        mViewModel.userCanSeeContent().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean userCanSee) {
                if (userCanSee) {
                    handleWhenUserCanSeeContent();
                } else {
                    handleWhenUserCanNotSeeContent();
                }
            }
        });
    }
    
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mUserAction = (LogUserActionsOnPublicContentInterface) context;
        }
        catch (ClassCastException ex) {
            throw new ClassCastException(context.toString()
                                         +
                                         " must implement LogUserActionsOnPublicContentInterface");
        }
    }
    
    @Override
    public void onStart() {
        super.onStart();
    }
    
    @Override
    public void onStop() {
        try {
            if (mUserAction != null && mViewModel.getContent().getValue() != null) {
                mUserAction.userHasFinishedViewingPage(mViewModel.getContent().getValue());
            }
        }
        catch (Exception ex) {
            Crashlytics.logException(ex);
        }
        super.onStop();
    }
    
    @Override
    public void onDetach() {
        mUserAction = null;
        super.onDetach();
    }
    
    private void handleWhenUserCanSeeContent() {
        mViewModel.getContent().observe(this, new Observer<ContentModel>() {
            @Override
            public void onChanged(ContentModel contentModel) {
                if (contentModel != null) {
                    mUserAction.userStartedViewingAParticularPage(contentModel);
                }
            }
        });
    }
    
    private void handleWhenUserCanNotSeeContent() {
        mViewModel.getError().observe(this, new Observer<ContentCredit>() {
            @Override
            public void onChanged(ContentCredit contentCredit) {
                if(contentCredit != null){
                
                }
            }
        });
    }
    
}
