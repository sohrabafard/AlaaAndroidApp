package ir.sanatisharif.android.konkur96.api.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class GETPriceModel implements Parcelable {

    @SerializedName("outOfStock")
    private String outOfStock;

    @SerializedName("cost")
    private PriceModel cost;

    protected GETPriceModel(Parcel in) {
        outOfStock = in.readString();
        cost = in.readParcelable(PriceModel.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(outOfStock);
        dest.writeParcelable(cost, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<GETPriceModel> CREATOR = new Creator<GETPriceModel>() {
        @Override
        public GETPriceModel createFromParcel(Parcel in) {
            return new GETPriceModel(in);
        }

        @Override
        public GETPriceModel[] newArray(int size) {
            return new GETPriceModel[size];
        }
    };

    public String getOutOfStock() {
        return outOfStock;
    }

    public void setOutOfStock(String outOfStock) {
        this.outOfStock = outOfStock;
    }

    public PriceModel getCost() {
        return cost;
    }

    public void setCost(PriceModel cost) {
        this.cost = cost;
    }
}
