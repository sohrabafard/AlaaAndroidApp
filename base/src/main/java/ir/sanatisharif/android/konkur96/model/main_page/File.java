
package ir.sanatisharif.android.konkur96.model.main_page;

import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import ir.sanatisharif.android.konkur96.model.Video;
import ir.sanatisharif.android.konkur96.model.filter.Pamphlet;

public class File implements Parcelable {

    @SerializedName("video")
    @Expose
    private List<Video> video = null;

    @SerializedName("pamphlet")
    @Expose
    private List<Video> pamphlet = null;


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
