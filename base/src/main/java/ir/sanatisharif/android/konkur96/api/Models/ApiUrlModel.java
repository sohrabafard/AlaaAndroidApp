package ir.sanatisharif.android.konkur96.api.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ApiUrlModel implements Parcelable {

    @SerializedName("v1")
    private String v1;

    protected ApiUrlModel(Parcel in) {
        v1 = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(v1);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ApiUrlModel> CREATOR = new Creator<ApiUrlModel>() {
        @Override
        public ApiUrlModel createFromParcel(Parcel in) {
            return new ApiUrlModel(in);
        }

        @Override
        public ApiUrlModel[] newArray(int size) {
            return new ApiUrlModel[size];
        }
    };

    public String getV1() {
        return v1;
    }

    public void setV1(String v1) {
        this.v1 = v1;
    }
}
