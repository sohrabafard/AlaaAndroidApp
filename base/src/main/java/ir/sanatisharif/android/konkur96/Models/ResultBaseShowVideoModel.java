package ir.sanatisharif.android.konkur96.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ResultBaseShowVideoModel implements Parcelable {
    
    public static final Creator<ResultBaseShowVideoModel>
                                             CREATOR =
            new Creator<ResultBaseShowVideoModel>() {
                @Override
                public ResultBaseShowVideoModel createFromParcel(Parcel in) {
                    return new ResultBaseShowVideoModel(in);
                }
                
                @Override
                public ResultBaseShowVideoModel[] newArray(int size) {
                    return new ResultBaseShowVideoModel[size];
                }
            };
    @SerializedName("result")
    private             ResultShowVideoModel result;
    
    protected ResultBaseShowVideoModel(Parcel in) {
        result = in.readParcelable(ResultShowVideoModel.class.getClassLoader());
    }
    
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(result, flags);
    }
    
    @Override
    public int describeContents() {
        return 0;
    }
    
    public ResultShowVideoModel getResult() {
        return result;
    }
    
    public void setResult(ResultShowVideoModel result) {
        this.result = result;
    }
}
