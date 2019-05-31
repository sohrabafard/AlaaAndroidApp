package ir.sanatisharif.android.konkur96.api.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import ir.sanatisharif.android.konkur96.model.Video;

public class VideoFilesListModel implements Parcelable {

    public static final Creator<VideoFilesListModel> CREATOR = new Creator<VideoFilesListModel>() {
        @Override
        public VideoFilesListModel createFromParcel(Parcel in) {
            return new VideoFilesListModel(in);
        }

        @Override
        public VideoFilesListModel[] newArray(int size) {
            return new VideoFilesListModel[size];
        }
    };
    @SerializedName("video")
    private             ArrayList<Video>             video;

    protected VideoFilesListModel(Parcel in) {
        video = in.createTypedArrayList(Video.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(video);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public ArrayList<Video> getVideo() {
        return video;
    }

    public void setVideo(ArrayList<Video> video) {
        this.video = video;
    }
}
