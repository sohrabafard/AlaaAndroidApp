package ir.sanatisharif.android.konkur96.api.Models.filter;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class FilterModel implements Parcelable {

    public final static Creator<FilterModel> CREATOR = new Creator<FilterModel>() {

        @Override
        public FilterModel createFromParcel(Parcel in) {
            return new FilterModel(in);
        }

        @Override
        public FilterModel[] newArray(int size) {
            return (new FilterModel[size]);
        }

    };

    @SerializedName("result")
    @Expose
    private FilterResult result;

    @SerializedName("tags")
    @Expose
    private ArrayList<String> tags;

    protected FilterModel(Parcel in) {
        this.result = in.readParcelable((FilterResult.class.getClassLoader()));

    }


    public FilterResult getResult() {
        return result;
    }

    public void setResult(FilterResult result) {
        this.result = result;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(result);
        dest.writeStringList(tags);
    }

    @Override
    public int describeContents() {
        return 0;
    }
    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

}
