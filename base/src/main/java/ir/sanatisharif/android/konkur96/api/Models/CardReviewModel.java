package ir.sanatisharif.android.konkur96.api.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CardReviewModel implements Parcelable {

    public static final Creator<CardReviewModel>       CREATOR = new Creator<CardReviewModel>() {
        @Override
        public CardReviewModel createFromParcel(Parcel in) {
            return new CardReviewModel(in);
        }

        @Override
        public CardReviewModel[] newArray(int size) {
            return new CardReviewModel[size];
        }
    };
    @SerializedName("items")
    private             ArrayList<ItemCardReviewMOdel> items;
    @SerializedName("orderproductCount")
    private             int                            orderproductCount;
    @SerializedName("price")
    private             PriceModel                     price;

    protected CardReviewModel(Parcel in) {
        items = in.createTypedArrayList(ItemCardReviewMOdel.CREATOR);
        orderproductCount = in.readInt();
        price = in.readParcelable(PriceModel.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(items);
        dest.writeInt(orderproductCount);
        dest.writeParcelable(price, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public ArrayList<ItemCardReviewMOdel> getItems() {
        return items;
    }

    public void setItems(ArrayList<ItemCardReviewMOdel> items) {
        this.items = items;
    }

    public int getOrderproductCount() {
        return orderproductCount;
    }

    public void setOrderproductCount(int orderproductCount) {
        this.orderproductCount = orderproductCount;
    }

    public PriceModel getPrice() {
        return price;
    }

    public void setPrice(PriceModel price) {
        this.price = price;
    }
}
