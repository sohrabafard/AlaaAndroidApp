package ir.sanatisharif.android.konkur96.api.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class VideoFilesModel implements Parcelable {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String description;

    @SerializedName("file")
    private VideoFilesListModel file;

    @SerializedName("thumbnail")
    private String thumbnail;

    protected VideoFilesModel(Parcel in) {
        id = in.readInt();
        name = in.readString();
        description = in.readString();
        file = in.readParcelable(VideoFilesListModel.class.getClassLoader());
        thumbnail = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeParcelable(file, flags);
        dest.writeString(thumbnail);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<VideoFilesModel> CREATOR = new Creator<VideoFilesModel>() {
        @Override
        public VideoFilesModel createFromParcel(Parcel in) {
            return new VideoFilesModel(in);
        }

        @Override
        public VideoFilesModel[] newArray(int size) {
            return new VideoFilesModel[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public VideoFilesListModel getFile() {
        return file;
    }

    public void setFile(VideoFilesListModel file) {
        this.file = file;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
