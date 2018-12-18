package ir.sanatisharif.android.konkur96.activity;


import android.arch.lifecycle.ViewModel;

/**
 * Created by Mohamad on 11/21/2018.
 */

public class MyViewModel extends ViewModel {

    int rotationCount;

    public int getRotationCount() {
        rotationCount++;
        return rotationCount;
    }
}

