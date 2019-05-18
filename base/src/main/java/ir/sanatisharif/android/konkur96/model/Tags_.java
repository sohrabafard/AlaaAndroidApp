package ir.sanatisharif.android.konkur96.model;

import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Tags_ implements Parcelable {

    public final static Creator<Tags_> CREATOR = new Creator<Tags_>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Tags_ createFromParcel(Parcel in) {
            return new Tags_(in);
        }

        public Tags_[] newArray(int size) {
            return (new Tags_[size]);
        }

    };
    @SerializedName("bucket")
    @Expose
    private String bucket;
    @SerializedName("tags")
    @Expose
    private List<String> tags = null;

    protected Tags_(Parcel in) {
        this.bucket = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.tags, (String.class.getClassLoader()));
    }

    public Tags_() {
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public Tags_ withBucket(String bucket) {
        this.bucket = bucket;
        return this;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public Tags_ withTags(List<String> tags) {
        this.tags = tags;
        return this;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(bucket);
        dest.writeList(tags);
    }

    public int describeContents() {
        return 0;
    }

}
