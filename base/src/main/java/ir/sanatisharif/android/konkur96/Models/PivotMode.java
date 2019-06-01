package ir.sanatisharif.android.konkur96.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class PivotMode implements Parcelable {
    
    public static final Creator<PivotMode> CREATOR = new Creator<PivotMode>() {
        @Override
        public PivotMode createFromParcel(Parcel in) {
            return new PivotMode(in);
        }
        
        @Override
        public PivotMode[] newArray(int size) {
            return new PivotMode[size];
        }
    };
    @SerializedName("orderproduct_id")
    private             int                orderproduct_id;
    @SerializedName("userbon_id")
    private             int                userbon_id;
    @SerializedName("usageNumber")
    private             int                usageNumber;
    @SerializedName("discount")
    private             int                discount;
    
    protected PivotMode(Parcel in) {
        orderproduct_id = in.readInt();
        userbon_id = in.readInt();
        usageNumber = in.readInt();
        discount = in.readInt();
    }
    
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(orderproduct_id);
        dest.writeInt(userbon_id);
        dest.writeInt(usageNumber);
        dest.writeInt(discount);
    }
    
    @Override
    public int describeContents() {
        return 0;
    }
    
    public int getOrderproduct_id() {
        return orderproduct_id;
    }
    
    public void setOrderproduct_id(int orderproduct_id) {
        this.orderproduct_id = orderproduct_id;
    }
    
    public int getUserbon_id() {
        return userbon_id;
    }
    
    public void setUserbon_id(int userbon_id) {
        this.userbon_id = userbon_id;
    }
    
    public int getUsageNumber() {
        return usageNumber;
    }
    
    public void setUsageNumber(int usageNumber) {
        this.usageNumber = usageNumber;
    }
    
    public int getDiscount() {
        return discount;
    }
    
    public void setDiscount(int discount) {
        this.discount = discount;
    }
}
