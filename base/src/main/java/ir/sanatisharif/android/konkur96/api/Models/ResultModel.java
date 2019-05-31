package ir.sanatisharif.android.konkur96.api.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResultModel implements Parcelable {

    public static final Creator<ResultModel> CREATOR = new Creator<ResultModel>() {
        @Override
        public ResultModel createFromParcel(Parcel in) {
            return new ResultModel(in);
        }

        @Override
        public ResultModel[] newArray(int size) {
            return new ResultModel[size];
        }
    };
    @SerializedName("result")
    private             ProductDataModel     result;
    @SerializedName("tags")
    private             ArrayList<String>    tags;

    protected ResultModel(Parcel in) {
        result = in.readParcelable(ProductDataModel.class.getClassLoader());
        tags = in.createStringArrayList();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(result, flags);
        dest.writeStringList(tags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public ProductDataModel getResult() {
        return result;
    }

    public void setResult(ProductDataModel result) {
        this.result = result;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }
}
