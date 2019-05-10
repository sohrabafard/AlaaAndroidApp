package ir.sanatisharif.android.konkur96.api.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ItemCardReviewMOdel extends AddToCardListModel implements Parcelable {

    @SerializedName("grand")
    private String grand;


    protected ItemCardReviewMOdel(Parcel in) {
        grand = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(grand);
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

    public String getGrand() {
        return grand;
    }

    public void setGrand(String grand) {
        this.grand = grand;
    }
}
