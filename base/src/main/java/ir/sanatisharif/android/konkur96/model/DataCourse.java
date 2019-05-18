package ir.sanatisharif.android.konkur96.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


import ir.sanatisharif.android.konkur96.app.AppConstants;
import ir.sanatisharif.android.konkur96.model.filter.FilterBaseModel;
import ir.sanatisharif.android.konkur96.model.main_page.ApiUrl;
import ir.sanatisharif.android.konkur96.model.main_page.Author;
import ir.sanatisharif.android.konkur96.model.main_page.File;
import ir.sanatisharif.android.konkur96.model.main_page.NextApiUrl;
import ir.sanatisharif.android.konkur96.model.main_page.Pivot;
import ir.sanatisharif.android.konkur96.model.main_page.PreviousApiUrl;
import ir.sanatisharif.android.konkur96.model.main_page.Set;
import ir.sanatisharif.android.konkur96.model.main_page.Tags;

public class DataCourse implements Parcelable {

    public final static Creator<DataCourse> CREATOR = new Creator<DataCourse>() {


        @SuppressWarnings({
                "unchecked"
        })
        public DataCourse createFromParcel(Parcel in) {
            return new DataCourse(in);
        }

        public DataCourse[] newArray(int size) {
            return (new DataCourse[size]);
        }

    };
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("contenttype_id")
    @Expose
    private int contenttypeId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("tags")
    @Expose
    private Tags tags;
    @SerializedName("context")
    @Expose
    private String context;
    @SerializedName("file")
    @Expose
    private File file;
    @SerializedName("duration")
    @Expose
    private int duration;
    @SerializedName("thumbnail")
    @Expose
    private String thumbnail;
    @SerializedName("isFree")
    @Expose
    private int isFree;
    @SerializedName("order")
    @Expose
    private int order;
    @SerializedName("page_view")
    @Expose
    private Object pageView;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("url")
    @Expose
    private String url;
    private ApiUrl apiUrl;
    @SerializedName("nextUrl")
    @Expose
    private String nextUrl;
    @SerializedName("nextApiUrl")
    @Expose
    private NextApiUrl nextApiUrl;
    @SerializedName("previousUrl")
    @Expose
    private String previousUrl;
    @SerializedName("previousApiUrl")
    @Expose
    private PreviousApiUrl previousApiUrl;
    @SerializedName("author")
    @Expose
    private Author author;
    @SerializedName("pivot")
    @Expose
    private Pivot pivot;
    @SerializedName("set")
    @Expose
    private Set set;

    protected DataCourse(Parcel in) {
        this.id = ((int) in.readValue((int.class.getClassLoader())));
        this.contenttypeId = ((int) in.readValue((int.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.description = ((String) in.readValue((String.class.getClassLoader())));
        this.tags = ((Tags) in.readValue((Tags.class.getClassLoader())));
        this.context = ((String) in.readValue((Object.class.getClassLoader())));
        this.file = ((File) in.readValue((File.class.getClassLoader())));
        this.duration = ((int) in.readValue((Object.class.getClassLoader())));
        this.thumbnail = ((String) in.readValue((String.class.getClassLoader())));
        this.isFree = ((int) in.readValue((int.class.getClassLoader())));
        this.order = ((int) in.readValue((int.class.getClassLoader())));
        this.pageView = in.readValue((Object.class.getClassLoader()));
        this.createdAt = ((String) in.readValue((String.class.getClassLoader())));
        this.updatedAt = ((String) in.readValue((String.class.getClassLoader())));
        this.url = ((String) in.readValue((String.class.getClassLoader())));
        this.author = ((Author) in.readValue((Author.class.getClassLoader())));
        this.apiUrl = ((ApiUrl) in.readValue((ApiUrl.class.getClassLoader())));
        this.nextUrl = ((String) in.readValue((String.class.getClassLoader())));
        this.nextApiUrl = ((NextApiUrl) in.readValue((NextApiUrl.class.getClassLoader())));
        this.previousUrl = ((String) in.readValue((String.class.getClassLoader())));
        this.previousApiUrl = ((PreviousApiUrl) in.readValue((PreviousApiUrl.class.getClassLoader())));
        this.pivot = ((Pivot) in.readValue((Pivot.class.getClassLoader())));
        this.set = ((Set) in.readValue((Set.class.getClassLoader())));

    }

    public DataCourse() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public DataCourse withId(int id) {
        this.id = id;
        return this;
    }

    public int getContenttypeId() {
        return contenttypeId;
    }

    public void setContenttypeId(int contenttypeId) {
        this.contenttypeId = contenttypeId;
    }

    public DataCourse withContenttypeId(int contenttypeId) {
        this.contenttypeId = contenttypeId;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DataCourse withName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public DataCourse withDescription(String description) {
        this.description = description;
        return this;
    }

    public Tags getTags() {
        return tags;
    }

    public void setTags(Tags tags) {
        this.tags = tags;
    }

    public DataCourse withTags(Tags tags) {
        this.tags = tags;
        return this;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public DataCourse withContext(String context) {
        this.context = context;
        return this;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public DataCourse withFile(File file) {
        this.file = file;
        return this;
    }

    public Object getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public DataCourse withDuration(int duration) {
        this.duration = duration;
        return this;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public DataCourse withThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
        return this;
    }

    public int getIsFree() {
        return isFree;
    }

    public void setIsFree(int isFree) {
        this.isFree = isFree;
    }

    public DataCourse withIsFree(int isFree) {
        this.isFree = isFree;
        return this;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public DataCourse withOrder(int order) {
        this.order = order;
        return this;
    }

    public Object getPageView() {
        return pageView;
    }

    public void setPageView(Object pageView) {
        this.pageView = pageView;
    }

    public DataCourse withPageView(Object pageView) {
        this.pageView = pageView;
        return this;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public DataCourse withCreatedAt(String createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public DataCourse withUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public DataCourse withUrl(String url) {
        this.url = url;
        return this;
    }

    public Pivot getPivot() {
        return pivot;
    }

    public void setPivot(Pivot pivot) {
        this.pivot = pivot;
    }

    public ApiUrl getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(ApiUrl apiUrl) {
        this.apiUrl = apiUrl;
    }

    public String getNextUrl() {
        return nextUrl;
    }

    public void setNextUrl(String nextUrl) {
        this.nextUrl = nextUrl;
    }

    public NextApiUrl getNextApiUrl() {
        return nextApiUrl;
    }

    public void setNextApiUrl(NextApiUrl nextApiUrl) {
        this.nextApiUrl = nextApiUrl;
    }

    public String getPreviousUrl() {
        return previousUrl;
    }

    public void setPreviousUrl(String previousUrl) {
        this.previousUrl = previousUrl;
    }

    public PreviousApiUrl getPreviousApiUrl() {
        return previousApiUrl;
    }

    public void setPreviousApiUrl(PreviousApiUrl previousApiUrl) {
        this.previousApiUrl = previousApiUrl;
    }


    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public DataCourse withAuthor(Author author) {
        this.author = author;
        return this;
    }

    public Set getSet() {
        return set;
    }

    public void setSet(Set set) {
        this.set = set;
    }

    public DataCourse withSet(Set set) {
        this.set = set;
        return this;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(contenttypeId);
        dest.writeValue(name);
        dest.writeValue(description);
        dest.writeValue(tags);
        dest.writeValue(context);
        dest.writeValue(file);
        dest.writeValue(duration);
        dest.writeValue(thumbnail);
        dest.writeValue(isFree);
        dest.writeValue(order);
        dest.writeValue(pageView);
        dest.writeValue(createdAt);
        dest.writeValue(updatedAt);
        dest.writeValue(url);
        dest.writeValue(apiUrl);
        dest.writeValue(nextUrl);
        dest.writeValue(nextApiUrl);
        dest.writeValue(previousUrl);
        dest.writeValue(previousApiUrl);
        dest.writeValue(author);
        dest.writeValue(pivot);
        dest.writeValue(set);
    }

    public int describeContents() {
        return 0;
    }
}
