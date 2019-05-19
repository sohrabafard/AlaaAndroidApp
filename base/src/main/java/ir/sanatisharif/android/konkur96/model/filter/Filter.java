package ir.sanatisharif.android.konkur96.model.filter;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Filter implements Parcelable {

    public final static Creator<Filter> CREATOR = new Creator<Filter>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Filter createFromParcel(Parcel in) {
            return new Filter(in);
        }

        public Filter[] newArray(int size) {
            return (new Filter[size]);
        }

    };
    @SerializedName("result")
    @Expose
    private Result result;

    protected Filter(Parcel in) {
        this.result = ((Result) in.readValue((Result.class.getClassLoader())));

    }

    public Filter() {
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(result);
    }

    public int describeContents() {
        return 0;
    }

}
