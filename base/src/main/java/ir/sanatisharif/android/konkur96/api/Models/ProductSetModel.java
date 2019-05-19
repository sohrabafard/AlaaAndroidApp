package ir.sanatisharif.android.konkur96.api.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ProductSetModel implements Parcelable {

    public static final Creator<ProductSetModel> CREATOR = new Creator<ProductSetModel>() {
        @Override
        public ProductSetModel createFromParcel(Parcel in) {
            return new ProductSetModel(in);
        }

        @Override
        public ProductSetModel[] newArray(int size) {
            return new ProductSetModel[size];
        }
    };
    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("photo")
    private String photo;
    @SerializedName("shortName")
    private String shortName;
    @SerializedName("contentUrl")
    private String contentUrl;

    protected ProductSetModel(Parcel in) {
        id = in.readInt();
        name = in.readString();
        photo = in.readString();
        shortName = in.readString();
        contentUrl = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(photo);
        dest.writeString(shortName);
        dest.writeString(contentUrl);
    }

    @Override
    public int describeContents() {
        return 0;
    }

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

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getContentUrl() {
        return contentUrl;
    }

    public void setContentUrl(String contentUrl) {
        this.contentUrl = contentUrl;
    }
}
