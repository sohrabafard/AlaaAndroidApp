package ir.sanatisharif.android.konkur96.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class AllPriceModel extends PriceModel implements Parcelable {
    
    @SerializedName("discountDetail")
    private DiscountDetail‌Model discountDetail;
    
    @SerializedName("extraCost")
    private int extraCost;
    
    protected AllPriceModel(Parcel in) {
        super(in);
    }
    
    
    public DiscountDetail‌Model getDiscountDetail() {
        return discountDetail;
    }
    
    public void setDiscountDetail(DiscountDetail‌Model discountDetail) {
        this.discountDetail = discountDetail;
    }
    
    public int getExtraCost() {
        return extraCost;
    }
    
    public void setExtraCost(int extraCost) {
        this.extraCost = extraCost;
    }
    
    
}
