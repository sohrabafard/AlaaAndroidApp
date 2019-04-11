package ir.sanatisharif.android.konkur96.api.Models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class AddToCardListModel extends ErrorBase {

    @SerializedName("orderproducts")
    private ArrayList<AddToCardModel> orderproducts;


    public ArrayList<AddToCardModel> getOrderproducts() {
        return orderproducts;
    }

    public void setOrderproducts(ArrayList<AddToCardModel> orderproducts) {
        this.orderproducts = orderproducts;
    }
}
