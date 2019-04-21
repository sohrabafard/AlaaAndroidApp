package ir.sanatisharif.android.konkur96.api.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class MainBannerModel implements Parcelable {

    @SerializedName("id")
    private int id;

    @SerializedName("title")
    private String title;

    @SerializedName("shortDescription")
    private String shortDescription;

    @SerializedName("link")
    private String link;

    @SerializedName("order")
    private int order;

    @SerializedName("updated_at")
    private String updated_at;

    @SerializedName("url")
    private String url;

    protected MainBannerModel(Parcel in) {
        id = in.readInt();
        title = in.readString();
        shortDescription = in.readString();
        link = in.readString();
        order = in.readInt();
        updated_at = in.readString();
        url = in.readString();
    }

    public static final Creator<MainBannerModel> CREATOR = new Creator<MainBannerModel>() {
        @Override
        public MainBannerModel createFromParcel(Parcel in) {
            return new MainBannerModel(in);
        }

        @Override
        public MainBannerModel[] newArray(int size) {
            return new MainBannerModel[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(shortDescription);
        dest.writeString(link);
        dest.writeInt(order);
        dest.writeString(updated_at);
        dest.writeString(url);
    }
}
