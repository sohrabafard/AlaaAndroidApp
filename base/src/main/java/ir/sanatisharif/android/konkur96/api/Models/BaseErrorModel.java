package ir.sanatisharif.android.konkur96.api.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class BaseErrorModel implements Parcelable {

    @SerializedName("error")
    private ErrorModel error;

    protected BaseErrorModel(Parcel in) {
        error = in.readParcelable(ErrorModel.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(error, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BaseErrorModel> CREATOR = new Creator<BaseErrorModel>() {
        @Override
        public BaseErrorModel createFromParcel(Parcel in) {
            return new BaseErrorModel(in);
        }

        @Override
        public BaseErrorModel[] newArray(int size) {
            return new BaseErrorModel[size];
        }
    };

    public ErrorModel getError() {
        return error;
    }

    public void setError(ErrorModel error) {
        this.error = error;
    }
}
