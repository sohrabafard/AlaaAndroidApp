package ir.sanatisharif.android.konkur96.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ErrorModel implements Parcelable {
    
    public static final Creator<ErrorModel> CREATOR = new Creator<ErrorModel>() {
        @Override
        public ErrorModel createFromParcel(Parcel in) {
            return new ErrorModel(in);
        }
        
        @Override
        public ErrorModel[] newArray(int size) {
            return new ErrorModel[size];
        }
    };
    @SerializedName("code")
    private             String              code;
    @SerializedName("message")
    private             String              message;
    
    protected ErrorModel(Parcel in) {
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
