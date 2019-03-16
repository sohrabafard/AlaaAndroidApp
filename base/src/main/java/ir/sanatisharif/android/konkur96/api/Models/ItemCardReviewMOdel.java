package ir.sanatisharif.android.konkur96.api.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ItemCardReviewMOdel extends AddToCardListModel implements Parcelable {

    @SerializedName("grand")
    private GrandModel grand;


    protected ItemCardReviewMOdel(Parcel in) {
        grand = in.readParcelable(GrandModel.class.getClassLoader());
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(grand, i);
    }
}
