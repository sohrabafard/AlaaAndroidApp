package ir.sanatisharif.android.konkur96.api.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ItemCardReviewMOdel extends AddToCardListModel implements Parcelable {
    
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
    @SerializedName("grand")
    private             ProductModel                 grand;
    
    protected ItemCardReviewMOdel(Parcel in) {
        grand = in.readParcelable(ProductModel.class.getClassLoader());
    }
    
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(grand, flags);
    }
    
    @Override
    public int describeContents() {
        return 0;
    }
    
    public ProductModel getGrand() {
        return grand;
    }
    
    public void setGrand(ProductModel grand) {
        this.grand = grand;
    }
}
