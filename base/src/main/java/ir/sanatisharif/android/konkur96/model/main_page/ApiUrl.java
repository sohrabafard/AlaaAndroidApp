package ir.sanatisharif.android.konkur96.model.main_page;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ApiUrl implements Parcelable {

    public final static Creator<ApiUrl> CREATOR = new Creator<ApiUrl>() {


        @SuppressWarnings({
                "unchecked"
        })
        public ApiUrl createFromParcel(Parcel in) {
            return new ApiUrl(in);
        }

        public ApiUrl[] newArray(int size) {
            return (new ApiUrl[size]);
        }

    };
    @SerializedName("v1")
    @Expose
    private             String          v1;

    protected ApiUrl(Parcel in) {
        this.v1 = ((String) in.readValue((String.class.getClassLoader())));
    }

    public ApiUrl() {
    }

    public String getV1() {
        return v1;
    }

    public void setV1(String v1) {
        this.v1 = v1;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(v1);
    }

    public int describeContents() {
        return 0;
    }

}
