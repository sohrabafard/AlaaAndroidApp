package ir.sanatisharif.android.konkur96.interfaces;

import com.google.firebase.appindexing.Action;
import com.google.firebase.appindexing.FirebaseAppIndex;
import com.google.firebase.appindexing.FirebaseUserActions;
import com.google.firebase.appindexing.Indexable;
import com.google.firebase.appindexing.builders.Actions;

import io.reactivex.annotations.NonNull;

public interface LogUserActionsOnPublicContentInterface {

    interface Data {

        String getUserActionTitle();

        String getUserActionUrl();

        String getUserActionPhoto();

        String getUserActionDescription();
    }

    default void userStartedViewingAParticularPage(@NonNull Data page){
        indexRecipe(page);
        FirebaseUserActions.getInstance().start(getRecipeViewAction(page));
    }
    default void userHasFinishedViewingPage(@NonNull Data page){
        FirebaseUserActions.getInstance().end(getRecipeViewAction(page));
    }

    default void indexRecipe(Data data) {
        Indexable recipeToIndex = new Indexable.Builder()
                .setName(data.getUserActionTitle())
                .setUrl(data.getUserActionUrl())
                .setImage(data.getUserActionPhoto())
                .setDescription(data.getUserActionDescription())
                .build();

        FirebaseAppIndex.getInstance().update(recipeToIndex);
    }

    default Action getRecipeViewAction(Data data) {
        return Actions.newView(data.getUserActionTitle(),data.getUserActionUrl());
    }
}
