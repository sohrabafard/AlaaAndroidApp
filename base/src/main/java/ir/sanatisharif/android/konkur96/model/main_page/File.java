package ir.sanatisharif.android.konkur96.model.main_page;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import ir.sanatisharif.android.konkur96.model.Video;

public class File implements Parcelable {

    public final static Creator<File> CREATOR = new Creator<File>() {

        @SuppressWarnings({
                "unchecked"
        })
        public File createFromParcel(Parcel in) {
            return new File(in);
        }

        public File[] newArray(int size) {
            return (new File[size]);
        }

    };
    @SerializedName("video")
    @Expose
    private             List<Video>   video   = null;

    @SerializedName("pamphlet")
    @Expose
    private List<Video> pamphlet = null;

    protected File(Parcel in) {
        in.readList(this.video, (Video.class.getClassLoader()));
        in.readList(this.pamphlet, (Video.class.getClassLoader()));
    }

    public File() {
    }

    public List<Video> getPamphlet() {
        return pamphlet;
    }

    public void setPamphlet(List<Video> pamphlet) {
        this.pamphlet = pamphlet;
    }

    public List<Video> getVideo() {
        return video;
    }

    public void setVideo(List<Video> video) {
        this.video = video;
    }

    public File withVideo(List<Video> video) {
        this.video = video;
        return this;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(video);
        dest.writeList(pamphlet);
    }

    public int describeContents() {
        return 0;
    }

}
