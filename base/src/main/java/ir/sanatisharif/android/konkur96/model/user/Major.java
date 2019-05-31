package ir.sanatisharif.android.konkur96.model.user;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Major implements Parcelable {

    public final static Creator<Major> CREATOR = new Creator<Major>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Major createFromParcel(Parcel in) {
            return new Major(in);
        }

        public Major[] newArray(int size) {
            return (new Major[size]);
        }

    };
    @SerializedName("id")
    @Expose
    private             int            id;
    @SerializedName("name")
    @Expose
    private             String         name;
    @SerializedName("description")
    @Expose
    private             String         description;

    protected Major(Parcel in) {
        this.id = ((int) in.readValue((int.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.description = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Major() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Major withId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Major withName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Major withDescription(String description) {
        this.description = description;
        return this;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(name);
        dest.writeValue(description);
    }

    public int describeContents() {
        return 0;
    }

}
