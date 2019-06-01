package ir.sanatisharif.android.konkur96.interfaces;

import android.util.Log;

import com.google.firebase.appindexing.Action;
import com.google.firebase.appindexing.FirebaseAppIndex;
import com.google.firebase.appindexing.FirebaseUserActions;
import com.google.firebase.appindexing.Indexable;
import com.google.firebase.appindexing.builders.Actions;

import io.reactivex.annotations.NonNull;

public interface LogUserActionsOnPublicContentInterface {
    
    default void userStartedViewingAParticularPage(@NonNull Data page) {
        indexPage(page);
        FirebaseUserActions.getInstance().start(getPageViewAction(page));
    }
    
    default void userHasFinishedViewingPage(@NonNull Data page) {
        FirebaseUserActions.getInstance().end(getPageViewAction(page));
    }
    
    
    default void indexPage(Data data) {
        String userActionTitle       = "" + data.getUserActionTitle();
        String userActionUrl         = "" + data.getUserActionUrl();
        String userActionPhoto       = "" + data.getUserActionPhoto();
        String userActionDescription = "" + data.getUserActionDescription();
        
        try {
            Indexable recipeToIndex = new Indexable.Builder()
                    .setName(userActionTitle)
                    .setUrl(userActionUrl)
                    .setImage(userActionPhoto)
                    .setDescription(userActionDescription)
                    .build();
            FirebaseAppIndex.getInstance().update(recipeToIndex);
        }
        catch (Exception ex) {
            Log.e("Alaa\\LogUserAction", ex.getMessage());
        }
        
    }
    
    default Action getPageViewAction(Data data) {
        return Actions.newView(data.getUserActionTitle(), data.getUserActionUrl());
    }
    
    interface Data {
        
        String getUserActionTitle();
        
        String getUserActionUrl();
        
        String getUserActionPhoto();
        
        String getUserActionDescription();
    }
}
