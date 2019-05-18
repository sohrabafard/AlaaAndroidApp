package ir.sanatisharif.android.konkur96.model.user;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserInfo implements Parcelable {

    public final static Creator<UserInfo> CREATOR = new Creator<UserInfo>() {


        @SuppressWarnings({
                "unchecked"
        })
        public UserInfo createFromParcel(Parcel in) {
            return new UserInfo(in);
        }

        public UserInfo[] newArray(int size) {
            return (new UserInfo[size]);
        }

    };
    @SerializedName("status")
    @Expose
    private int status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("redirectTo")
    @Expose
    private String redirectTo;
    @SerializedName("data")
    @Expose
    private Data data;

    protected UserInfo(Parcel in) {
        this.status = ((int) in.readValue((int.class.getClassLoader())));
        this.msg = ((String) in.readValue((String.class.getClassLoader())));
        this.redirectTo = ((String) in.readValue((String.class.getClassLoader())));
        this.data = ((Data) in.readValue((Data.class.getClassLoader())));
    }

    public UserInfo() {
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public UserInfo withStatus(int status) {
        this.status = status;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public UserInfo withMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public String getRedirectTo() {
        return redirectTo;
    }

    public void setRedirectTo(String redirectTo) {
        this.redirectTo = redirectTo;
    }

    public UserInfo withRedirectTo(String redirectTo) {
        this.redirectTo = redirectTo;
        return this;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public UserInfo withData(Data data) {
        this.data = data;
        return this;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(status);
        dest.writeValue(msg);
        dest.writeValue(redirectTo);
        dest.writeValue(data);
    }

    public int describeContents() {
        return 0;
    }

}
