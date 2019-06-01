package ir.sanatisharif.android.konkur96.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class GETPriceModel extends BaseErrorModel implements Parcelable {
    
    @SerializedName("outOfStock")
    private String outOfStock;
    
    @SerializedName("cost")
    private PriceModel cost;
    
    protected GETPriceModel(Parcel in) {
        super(in);
    }
    
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
