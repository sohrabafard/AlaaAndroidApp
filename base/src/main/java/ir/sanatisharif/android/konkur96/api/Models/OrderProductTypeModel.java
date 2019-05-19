package ir.sanatisharif.android.konkur96.api.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class OrderProductTypeModel implements Parcelable {

    public static final Creator<OrderProductTypeModel> CREATOR = new Creator<OrderProductTypeModel>() {
        @Override
        public OrderProductTypeModel createFromParcel(Parcel in) {
            return new OrderProductTypeModel(in);
        }

        @Override
        public OrderProductTypeModel[] newArray(int size) {
            return new OrderProductTypeModel[size];
        }
    };
    @SerializedName("name")
    private String name;
    @SerializedName("displayName")
    private String displayName;

    protected OrderProductTypeModel(Parcel in) {
        name = in.readString();
        displayName = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(displayName);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
