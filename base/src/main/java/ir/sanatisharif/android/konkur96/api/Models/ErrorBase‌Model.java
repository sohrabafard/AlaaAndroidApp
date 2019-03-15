package ir.sanatisharif.android.konkur96.api.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ErrorBase‌Model implements Parcelable {

    @SerializedName("error")
    private String code;

    @SerializedName("message")
    private String message;


    protected ErrorBase‌Model(Parcel in) {
        code = in.readString();
        message = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(code);
        dest.writeString(message);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ErrorBase‌Model> CREATOR = new Creator<ErrorBase‌Model>() {
        @Override
        public ErrorBase‌Model createFromParcel(Parcel in) {
            return new ErrorBase‌Model(in);
        }

        @Override
        public ErrorBase‌Model[] newArray(int size) {
            return new ErrorBase‌Model[size];
        }
    };

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
