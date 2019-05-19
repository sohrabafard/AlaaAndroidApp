package ir.sanatisharif.android.konkur96.model.main_page;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MainBanner implements Parcelable {

    public final static Creator<MainBanner> CREATOR = new Creator<MainBanner>() {


        @SuppressWarnings({
                "unchecked"
        })
        public MainBanner createFromParcel(Parcel in) {
            return new MainBanner(in);
        }

        public MainBanner[] newArray(int size) {
            return (new MainBanner[size]);
        }

    };
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("shortDescription")
    @Expose
    private String shortDescription;
    @SerializedName("link")
    @Expose
    private String link;
    @SerializedName("order")
    @Expose
    private Integer order;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("url")
    @Expose
    private String url;

    protected MainBanner(Parcel in) {
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.title = ((String) in.readValue((Object.class.getClassLoader())));
        this.shortDescription = ((String) in.readValue((Object.class.getClassLoader())));
        this.link = ((String) in.readValue((String.class.getClassLoader())));
        this.order = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.updatedAt = ((String) in.readValue((String.class.getClassLoader())));
        this.url = ((String) in.readValue((String.class.getClassLoader())));
    }

    public MainBanner() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(title);
        dest.writeValue(shortDescription);
        dest.writeValue(link);
        dest.writeValue(order);
        dest.writeValue(updatedAt);
        dest.writeValue(url);
    }

    public int describeContents() {
        return 0;
    }

}
