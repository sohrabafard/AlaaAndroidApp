package ir.sanatisharif.android.konkur96.api.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ResultShowVideoModel implements Parcelable {

    public static final Creator<ResultShowVideoModel> CREATOR = new Creator<ResultShowVideoModel>() {
        @Override
        public ResultShowVideoModel createFromParcel(Parcel in) {
            return new ResultShowVideoModel(in);
        }

        @Override
        public ResultShowVideoModel[] newArray(int size) {
            return new ResultShowVideoModel[size];
        }
    };
    @SerializedName("video")
    private PageVideoShowVideoModel video;

    protected ResultShowVideoModel(Parcel in) {
        video = in.readParcelable(PageVideoShowVideoModel.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(video, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public PageVideoShowVideoModel getVideo() {
        return video;
    }

    public void setVideo(PageVideoShowVideoModel video) {
        this.video = video;
    }
}
