
package ir.sanatisharif.android.konkur96.model;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Tags implements Parcelable
{

    @SerializedName("bucket")
    @Expose
    private String bucket;
    @SerializedName("tags")
    @Expose
    private List<String> tags = null;
    public final static Creator<Tags> CREATOR = new Creator<Tags>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Tags createFromParcel(Parcel in) {
            return new Tags(in);
        }

        public Tags[] newArray(int size) {
            return (new Tags[size]);
        }

    }
    ;

    protected Tags(Parcel in) {
        this.bucket = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.tags, (String.class.getClassLoader()));
    }

    public Tags() {
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public Tags withBucket(String bucket) {
        this.bucket = bucket;
        return this;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public Tags withTags(List<String> tags) {
        this.tags = tags;
        return this;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(bucket);
        dest.writeList(tags);
    }

    public int describeContents() {
        return  0;
    }

}