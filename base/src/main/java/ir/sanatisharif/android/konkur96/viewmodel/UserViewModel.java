package ir.sanatisharif.android.konkur96.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.ArrayList;

import ir.sanatisharif.android.konkur96.model.User;

/**
 * Created by Mohamad on 12/23/2018.
 */

public class UserViewModel extends ViewModel {

    private String name;
    private String lastName;

    private MutableLiveData<UserViewModel> getMutableLiveData = new MutableLiveData<>();
    private ArrayList<UserViewModel> userViewModels;

    public UserViewModel() {
    }

    public UserViewModel(User user) {
        this.name = user.getName();
        this.lastName = user.getLastName();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
