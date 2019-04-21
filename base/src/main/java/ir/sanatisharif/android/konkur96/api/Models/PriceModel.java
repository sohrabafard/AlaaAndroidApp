package ir.sanatisharif.android.konkur96.api.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PriceModel implements Parcelable {

    @SerializedName("base")
    private int base;

    @SerializedName("discount")
    private int discount;

    @SerializedName("final")
    private int mfinal;

    protected PriceModel(Parcel in) {
        base = in.readInt();
        discount = in.readInt();
        mfinal = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(base);
        dest.writeInt(discount);
        dest.writeInt(mfinal);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PriceModel> CREATOR = new Creator<PriceModel>() {
        @Override
        public PriceModel createFromParcel(Parcel in) {
            return new PriceModel(in);
        }

        @Override
        public PriceModel[] newArray(int size) {
            return new PriceModel[size];
        }
    };

    public int getBase() {
        return base;
    }

    public void setBase(int base) {
        this.base = base;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public int getMfinal() {
        return mfinal;
    }

    public void setMfinal(int mfinal) {
        this.mfinal = mfinal;
    }
}
