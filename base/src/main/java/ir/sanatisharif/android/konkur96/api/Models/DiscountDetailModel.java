package ir.sanatisharif.android.konkur96.api.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class DiscountDetailModel implements Parcelable {

    public static final Creator<DiscountDetailModel> CREATOR = new Creator<DiscountDetailModel>() {
        @Override
        public DiscountDetailModel createFromParcel(Parcel in) {
            return new DiscountDetailModel(in);
        }

        @Override
        public DiscountDetailModel[] newArray(int size) {
            return new DiscountDetailModel[size];
        }
    };
    @SerializedName("productDiscount")
    private             int                          productDiscount;
    @SerializedName("bonDiscount")
    private             int                          bonDiscount;
    @SerializedName("productDiscountAmount")
    private             int                          productDiscountAmount;

    protected DiscountDetailModel(Parcel in) {
        productDiscount = in.readInt();
        bonDiscount = in.readInt();
        productDiscountAmount = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(productDiscount);
        dest.writeInt(bonDiscount);
        dest.writeInt(productDiscountAmount);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public int getProductDiscount() {
        return productDiscount;
    }

    public void setProductDiscount(int productDiscount) {
        this.productDiscount = productDiscount;
    }

    public int getBonDiscount() {
        return bonDiscount;
    }

    public void setBonDiscount(int bonDiscount) {
        this.bonDiscount = bonDiscount;
    }

    public int getProductDiscountAmount() {
        return productDiscountAmount;
    }

    public void setProductDiscountAmount(int productDiscountAmount) {
        this.productDiscountAmount = productDiscountAmount;
    }
}
