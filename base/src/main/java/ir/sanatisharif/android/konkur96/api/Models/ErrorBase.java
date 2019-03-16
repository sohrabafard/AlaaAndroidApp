package ir.sanatisharif.android.konkur96.api.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ErrorBase implements Parcelable {

    @SerializedName("error")
    private ErrorBaseModel error;

    protected ErrorBase(Parcel in) {
        error = in.readParcelable(ErrorBaseModel.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(error, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ErrorBase> CREATOR = new Creator<ErrorBase>() {
        @Override
        public ErrorBase createFromParcel(Parcel in) {
            return new ErrorBase(in);
        }

        @Override
        public ErrorBase[] newArray(int size) {
            return new ErrorBase[size];
        }
    };

    public ErrorBaseModel getError() {
        return error;
    }

    public void setError(ErrorBaseModel error) {
        this.error = error;
    }
}
