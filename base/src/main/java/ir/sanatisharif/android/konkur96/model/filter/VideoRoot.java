package ir.sanatisharif.android.konkur96.model.filter;

import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import ir.sanatisharif.android.konkur96.model.main_page.Datum;


public class VideoRoot extends Pagination implements Parcelable {

    public final static Creator<VideoRoot> CREATOR = new Creator<VideoRoot>() {


        @SuppressWarnings({
                "unchecked"
        })
        public VideoRoot createFromParcel(Parcel in) {
            return new VideoRoot(in);
        }

        public VideoRoot[] newArray(int size) {
            return (new VideoRoot[size]);
        }

    };
    @SerializedName("data")
    @Expose
    private List<VideoCourse> data = null;

    public VideoRoot() {
    }

    protected VideoRoot(Parcel in) {
        in.readList(this.data, (Datum.class.getClassLoader()));
    }

    public List<VideoCourse> getData() {
        return data;
    }

    public void setData(List<VideoCourse> data) {
        this.data = data;
    }


    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(data);
    }

    public int describeContents() {
        return 0;
    }

}
