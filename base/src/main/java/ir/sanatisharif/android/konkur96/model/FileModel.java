package ir.sanatisharif.android.konkur96.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class FileModel implements Parcelable {

    @SerializedName("video")
    @Expose
    private ArrayList<FileDiskModel> video ;

    @SerializedName("pamphlet")
    @Expose
    private ArrayList<FileDiskModel> pamphlet ;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.video);
        dest.writeTypedList(this.pamphlet);
    }

    public FileModel() {
    }

    protected FileModel(Parcel in) {
        this.video = in.createTypedArrayList(FileDiskModel.CREATOR);
        this.pamphlet = in.createTypedArrayList(FileDiskModel.CREATOR);
    }

    public static final Creator<FileModel> CREATOR = new Creator<FileModel>() {
        @Override
        public FileModel createFromParcel(Parcel source) {
            return new FileModel(source);
        }

        @Override
        public FileModel[] newArray(int size) {
            return new FileModel[size];
        }
    };

    public ArrayList<FileDiskModel> getVideo() {
        return video;
    }

    public void setVideo(ArrayList<FileDiskModel> video) {
        this.video = video;
    }

    public ArrayList<FileDiskModel> getPamphlet() {
        return pamphlet;
    }

    public void setPamphlet(ArrayList<FileDiskModel> pamphlet) {
        this.pamphlet = pamphlet;
    }
}
