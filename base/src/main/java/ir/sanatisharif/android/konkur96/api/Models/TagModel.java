package ir.sanatisharif.android.konkur96.api.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class TagModel implements Parcelable {
    
    public static final Creator<TagModel> CREATOR = new Creator<TagModel>() {
        @Override
        public TagModel createFromParcel(Parcel in) {
            return new TagModel(in);
        }
        
        @Override
        public TagModel[] newArray(int size) {
            return new TagModel[size];
        }
    };
    @SerializedName("bucket")
    private             String            bucket;
    @SerializedName("tags")
    private             ArrayList<String> tags;
    
    protected TagModel(Parcel in) {
        bucket = in.readString();
        tags = in.createStringArrayList();
    }
    
    public String getBucket() {
        return bucket;
    }
    
    public void setBucket(String bucket) {
        this.bucket = bucket;
    }
    
    public ArrayList<String> getTags() {
        return tags;
    }
    
    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }
    
    @Override
    public int describeContents() {
        return 0;
    }
    
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(bucket);
        dest.writeStringList(tags);
    }
    
    
}
