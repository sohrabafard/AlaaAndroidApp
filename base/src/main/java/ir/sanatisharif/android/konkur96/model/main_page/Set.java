package ir.sanatisharif.android.konkur96.model.main_page;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Set implements Parcelable {

    public final static Creator<Set> CREATOR = new Creator<Set>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Set createFromParcel(Parcel in) {
            return new Set(in);
        }

        public Set[] newArray(int size) {
            return (new Set[size]);
        }

    };
    @SerializedName("id")
    @Expose
    private             Integer      id;
    @SerializedName("name")
    @Expose
    private             String       name;
    @SerializedName("description")
    @Expose
    private             Object       description;
    @SerializedName("photo")
    @Expose
    private             String       photo;
    @SerializedName("tags")
    @Expose
    private             Tags         tags;
    @SerializedName("created_at")
    @Expose
    private             String       createdAt;
    @SerializedName("updated_at")
    @Expose
    private             String       updatedAt;
    @SerializedName("contents_count")
    @Expose
    private             Integer      contentsCount;
    @SerializedName("url")
    @Expose
    private             String       url;
    @SerializedName("apiUrl")
    @Expose
    private             ApiUrl       apiUrl;
    @SerializedName("shortName")
    @Expose
    private             String       shortName;
    @SerializedName("author")
    @Expose
    private             Author       author;
    @SerializedName("contentUrl")
    @Expose
    private             String       contentUrl;
    private             int          type;

    protected Set(Parcel in) {
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.description = in.readValue((Object.class.getClassLoader()));
        this.photo = ((String) in.readValue((String.class.getClassLoader())));
        this.tags = ((Tags) in.readValue((Tags.class.getClassLoader())));
        this.createdAt = ((String) in.readValue((String.class.getClassLoader())));
        this.updatedAt = ((String) in.readValue((String.class.getClassLoader())));
        this.contentsCount = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.url = ((String) in.readValue((String.class.getClassLoader())));
        this.apiUrl = ((ApiUrl) in.readValue((ApiUrl.class.getClassLoader())));
        this.shortName = ((String) in.readValue((String.class.getClassLoader())));
        this.author = ((Author) in.readValue((Author.class.getClassLoader())));
        this.contentUrl = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Set() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getDescription() {
        return description;
    }

    public void setDescription(Object description) {
        this.description = description;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Tags getTags() {
        return tags;
    }

    public void setTags(Tags tags) {
        this.tags = tags;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getContentsCount() {
        return contentsCount;
    }

    public void setContentsCount(Integer contentsCount) {
        this.contentsCount = contentsCount;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ApiUrl getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(ApiUrl apiUrl) {
        this.apiUrl = apiUrl;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getContentUrl() {
        return contentUrl;
    }

    public void setContentUrl(String contentUrl) {
        this.contentUrl = contentUrl;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(name);
        dest.writeValue(description);
        dest.writeValue(photo);
        dest.writeValue(tags);
        dest.writeValue(createdAt);
        dest.writeValue(updatedAt);
        dest.writeValue(contentsCount);
        dest.writeValue(url);
        dest.writeValue(apiUrl);
        dest.writeValue(shortName);
        dest.writeValue(author);
        dest.writeValue(contentUrl);
    }

    public int describeContents() {
        return 0;
    }

}
