package ir.sanatisharif.android.konkur96.model.main_page.lastVersion;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Android implements Parcelable {

    public final static Creator<Android> CREATOR = new Creator<Android>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Android createFromParcel(Parcel in) {
            return new Android(in);
        }

        public Android[] newArray(int size) {
            return (new Android[size]);
        }

    };
    @SerializedName("last_version")
    @Expose
    private             Integer          lastVersion;
    @SerializedName("type")
    @Expose
    private             Type             type;
    @SerializedName("url")
    @Expose
    private             Url              url;

    protected Android(Parcel in) {
        this.lastVersion = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.type = ((Type) in.readValue((Type.class.getClassLoader())));
        this.url = ((Url) in.readValue((Url.class.getClassLoader())));
    }

    public Android() {
    }

    public Integer getLastVersion() {
        return lastVersion;
    }

    public void setLastVersion(Integer lastVersion) {
        this.lastVersion = lastVersion;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Url getUrl() {
        return url;
    }

    public void setUrl(Url url) {
        this.url = url;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(lastVersion);
        dest.writeValue(type);
        dest.writeValue(url);
    }

    public int describeContents() {
        return 0;
    }

}
