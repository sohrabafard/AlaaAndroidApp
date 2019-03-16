package ir.sanatisharif.android.konkur96.api.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ItemCardReviewMOdel implements Parcelable {

    @SerializedName("grand")
    private GrandModel grand;

    @SerializedName("orderproducts")
    private AddToCardModel orderproducts;

    protected ItemCardReviewMOdel(Parcel in) {
        grand = in.readParcelable(GrandModel.class.getClassLoader());
        orderproducts = in.readParcelable(AddToCardModel.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(grand, flags);
        dest.writeParcelable(orderproducts, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ItemCardReviewMOdel> CREATOR = new Creator<ItemCardReviewMOdel>() {
        @Override
        public ItemCardReviewMOdel createFromParcel(Parcel in) {
            return new ItemCardReviewMOdel(in);
        }

        @Override
        public ItemCardReviewMOdel[] newArray(int size) {
            return new ItemCardReviewMOdel[size];
        }
    };

    public GrandModel getGrand() {
        return grand;
    }

    public void setGrand(GrandModel grand) {
        this.grand = grand;
    }

    public AddToCardModel getOrderproducts() {
        return orderproducts;
    }

    public void setOrderproducts(AddToCardModel orderproducts) {
        this.orderproducts = orderproducts;
    }
}
