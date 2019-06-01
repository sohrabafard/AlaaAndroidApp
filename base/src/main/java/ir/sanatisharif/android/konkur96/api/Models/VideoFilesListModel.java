package ir.sanatisharif.android.konkur96.api.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import ir.sanatisharif.android.konkur96.model.FileDiskModel;

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
    @SerializedName("fileDiskModel")
    private             ArrayList<FileDiskModel>     fileDiskModel;
    
    protected VideoFilesListModel(Parcel in) {
        fileDiskModel = in.createTypedArrayList(FileDiskModel.CREATOR);
    }
    
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(fileDiskModel);
    }
    
    @Override
    public int describeContents() {
        return 0;
    }
    
    public ArrayList<FileDiskModel> getVideo() {
        return fileDiskModel;
    }
    
    public void setFileDiskModel(ArrayList<FileDiskModel> fileDiskModel) {
        this.fileDiskModel = fileDiskModel;
    }
}
