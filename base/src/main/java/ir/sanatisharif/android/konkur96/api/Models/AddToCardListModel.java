package ir.sanatisharif.android.konkur96.api.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class AddToCardListModel extends ErrorBase implements Parcelable {

    @SerializedName("orderproducts")
    private ArrayList<AddToCardModel> orderproducts;

    protected AddToCardListModel(Parcel in) {
        super(in);
    }


    public ArrayList<AddToCardModel> getOrderproducts() {
        return orderproducts;
    }

    public void setOrderproducts(ArrayList<AddToCardModel> orderproducts) {
        this.orderproducts = orderproducts;
    }
}
