package ir.sanatisharif.android.konkur96.api.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ErrorBaseModel implements Parcelable {

    public static final Creator<ErrorBaseModel> CREATOR = new Creator<ErrorBaseModel>() {
        @Override
        public ErrorBaseModel createFromParcel(Parcel in) {
            return new ErrorBaseModel(in);
        }

        @Override
        public ErrorBaseModel[] newArray(int size) {
            return new ErrorBaseModel[size];
        }
    };
    @SerializedName("error")
    private             String                  error;
    @SerializedName("code")
    private             int                     code;
    @SerializedName("message")
    private             String                  message;

    protected ErrorBaseModel(Parcel in) {
        error = in.readString();
        code = in.readInt();
        message = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(error);
        dest.writeInt(code);
        dest.writeString(message);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
