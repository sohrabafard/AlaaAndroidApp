package ir.sanatisharif.android.konkur96.api.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ProductPhotoModel implements Parcelable {

    @SerializedName("title")
    private String title;

    @SerializedName("description")
    private String description;

    @SerializedName("url")
    private String url;

    protected ProductPhotoModel(Parcel in) {
        title = in.readString();
        description = in.readString();
        url = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(url);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ProductPhotoModel> CREATOR = new Creator<ProductPhotoModel>() {
        @Override
        public ProductPhotoModel createFromParcel(Parcel in) {
            return new ProductPhotoModel(in);
        }

        @Override
        public ProductPhotoModel[] newArray(int size) {
            return new ProductPhotoModel[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
