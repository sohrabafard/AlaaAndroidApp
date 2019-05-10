package ir.sanatisharif.android.konkur96.model.main_page;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import ir.sanatisharif.android.konkur96.model.Author;
import ir.sanatisharif.android.konkur96.model.DataCourse;
import ir.sanatisharif.android.konkur96.model.File;
import ir.sanatisharif.android.konkur96.model.Tags;

/**
 * Created by Mohamad on 11/15/2018.
 */

public class Content extends DataCourse {

//    @SerializedName("id")
//    @Expose
//    private Integer id;
//    @SerializedName("contenttype_id")
//    @Expose
//    private Integer contenttypeId;
//    @SerializedName("name")
//    @Expose
//    private String name;
//    @SerializedName("description")
//    @Expose
//    private String description;
//    @SerializedName("tags")
//    @Expose
//    private ir.sanatisharif.android.konkur96.model.Tags tags;
//    @SerializedName("context")
//    @Expose
//    private String context;
//    @SerializedName("file")
//    @Expose
//    private ir.sanatisharif.android.konkur96.model.File file;
//    @SerializedName("duration")
//    @Expose
//    private int duration;
//    @SerializedName("thumbnail")
//    @Expose
//    private String thumbnail;
//    @SerializedName("isFree")
//    @Expose
//    private Integer isFree;
//    @SerializedName("order")
//    @Expose
//    private Integer order;
//    @SerializedName("page_view")
//    @Expose
//    private Object pageView;
//    @SerializedName("created_at")
//    @Expose
//    private String createdAt;
//    @SerializedName("updated_at")
//    @Expose
//    private String updatedAt;
//    @SerializedName("url")
//    @Expose
//    private String url;
//    @SerializedName("apiUrl")
//    @Expose
//    private ApiUrl apiUrl;
//    @SerializedName("nextUrl")
//    @Expose
//    private String nextUrl;
//    @SerializedName("nextApiUrl")
//    @Expose
//    private NextApiUrl nextApiUrl;
//    @SerializedName("previousUrl")
//    @Expose
//    private String previousUrl;
//    @SerializedName("previousApiUrl")
//    @Expose
//    private PreviousApiUrl previousApiUrl;
//    @SerializedName("author")
//    @Expose
//    private ir.sanatisharif.android.konkur96.model.Author author;
//    @SerializedName("pivot")
//    @Expose
//    private Pivot pivot;
//    @SerializedName("set")
//    @Expose
//    private Set set;
//    public final static Parcelable.Creator<Content> CREATOR = new Creator<Content>() {
//
//
//        @SuppressWarnings({
//                "unchecked"
//        })
//        public Content createFromParcel(Parcel in) {
//            return new Content(in);
//        }
//
//        public Content[] newArray(int size) {
//            return (new Content[size]);
//        }
//
//    };
//
//    protected Content(Parcel in) {
//        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
//        this.contenttypeId = ((Integer) in.readValue((Integer.class.getClassLoader())));
//        this.name = ((String) in.readValue((String.class.getClassLoader())));
//        this.description = ((String) in.readValue((String.class.getClassLoader())));
//        this.tags = ((ir.sanatisharif.android.konkur96.model.Tags) in.readValue((ir.sanatisharif.android.konkur96.model.Tags.class.getClassLoader())));
//        this.context = ((String) in.readValue((Object.class.getClassLoader())));
//        this.file = ((ir.sanatisharif.android.konkur96.model.File) in.readValue((ir.sanatisharif.android.konkur96.model.File.class.getClassLoader())));
//        this.duration = ((int) in.readValue((Object.class.getClassLoader())));
//        this.thumbnail = ((String) in.readValue((String.class.getClassLoader())));
//        this.isFree = ((Integer) in.readValue((Integer.class.getClassLoader())));
//        this.order = ((Integer) in.readValue((Integer.class.getClassLoader())));
//        this.pageView = ((Object) in.readValue((Object.class.getClassLoader())));
//        this.createdAt = ((String) in.readValue((String.class.getClassLoader())));
//        this.updatedAt = ((String) in.readValue((String.class.getClassLoader())));
//        this.url = ((String) in.readValue((String.class.getClassLoader())));
//        this.apiUrl = ((ApiUrl) in.readValue((ApiUrl.class.getClassLoader())));
//        this.nextUrl = ((String) in.readValue((String.class.getClassLoader())));
//        this.nextApiUrl = ((NextApiUrl) in.readValue((NextApiUrl.class.getClassLoader())));
//        this.previousUrl = ((String) in.readValue((String.class.getClassLoader())));
//        this.previousApiUrl = ((PreviousApiUrl) in.readValue((PreviousApiUrl.class.getClassLoader())));
//        this.author = ((ir.sanatisharif.android.konkur96.model.Author) in.readValue((ir.sanatisharif.android.konkur96.model.Author.class.getClassLoader())));
//        this.pivot = ((Pivot) in.readValue((Pivot.class.getClassLoader())));
//        this.set = ((Set) in.readValue((Set.class.getClassLoader())));
//    }
//
//    public Content() {
//    }
//
//    public Integer getId() {
//        return id;
//    }
//
//    public void setId(Integer id) {
//        this.id = id;
//    }
//
//    public Integer getContenttypeId() {
//        return contenttypeId;
//    }
//
//    public void setContenttypeId(Integer contenttypeId) {
//        this.contenttypeId = contenttypeId;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
//
//    public ir.sanatisharif.android.konkur96.model.Tags getTags() {
//        return tags;
//    }
//
//    public void setTags(Tags tags) {
//        this.tags = tags;
//    }
//
//    public Object getContext() {
//        return context;
//    }
//
//    public void setContext(String context) {
//        this.context = context;
//    }
//
//    public ir.sanatisharif.android.konkur96.model.File getFile() {
//        return file;
//    }
//
//    public void setFile(File file) {
//        this.file = file;
//    }
//
//    public Object getDuration() {
//        return duration;
//    }
//
//    public void setDuration(int duration) {
//        this.duration = duration;
//    }
//
//    public String getThumbnail() {
//        return thumbnail;
//    }
//
//    public void setThumbnail(String thumbnail) {
//        this.thumbnail = thumbnail;
//    }
//
//    public Integer getIsFree() {
//        return isFree;
//    }
//
//    public void setIsFree(Integer isFree) {
//        this.isFree = isFree;
//    }
//
//    public Integer getOrder() {
//        return order;
//    }
//
//    public void setOrder(Integer order) {
//        this.order = order;
//    }
//
//    public Object getPageView() {
//        return pageView;
//    }
//
//    public void setPageView(Object pageView) {
//        this.pageView = pageView;
//    }
//
//    public String getCreatedAt() {
//        return createdAt;
//    }
//
//    public void setCreatedAt(String createdAt) {
//        this.createdAt = createdAt;
//    }
//
//    public String getUpdatedAt() {
//        return updatedAt;
//    }
//
//    public void setUpdatedAt(String updatedAt) {
//        this.updatedAt = updatedAt;
//    }
//
//    public String getUrl() {
//        return url;
//    }
//
//    public void setUrl(String url) {
//        this.url = url;
//    }
//
//    public ApiUrl getApiUrl() {
//        return apiUrl;
//    }
//
//    public void setApiUrl(ApiUrl apiUrl) {
//        this.apiUrl = apiUrl;
//    }
//
//    public String getNextUrl() {
//        return nextUrl;
//    }
//
//    public void setNextUrl(String nextUrl) {
//        this.nextUrl = nextUrl;
//    }
//
//    public NextApiUrl getNextApiUrl() {
//        return nextApiUrl;
//    }
//
//    public void setNextApiUrl(NextApiUrl nextApiUrl) {
//        this.nextApiUrl = nextApiUrl;
//    }
//
//    public String getPreviousUrl() {
//        return previousUrl;
//    }
//
//    public void setPreviousUrl(String previousUrl) {
//        this.previousUrl = previousUrl;
//    }
//
//    public PreviousApiUrl getPreviousApiUrl() {
//        return previousApiUrl;
//    }
//
//    public void setPreviousApiUrl(PreviousApiUrl previousApiUrl) {
//        this.previousApiUrl = previousApiUrl;
//    }
//
//    public ir.sanatisharif.android.konkur96.model.Author getAuthor() {
//        return author;
//    }
//
//    public void setAuthor(Author author) {
//        this.author = author;
//    }
//
//    public Pivot getPivot() {
//        return pivot;
//    }
//
//    public void setPivot(Pivot pivot) {
//        this.pivot = pivot;
//    }
//
//    public Set getSet() {
//        return set;
//    }
//
//    public void setSet(Set set) {
//        this.set = set;
//    }
//
//
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel dest, int i) {
//
//        dest.writeValue(id);
//        dest.writeValue(contenttypeId);
//        dest.writeValue(name);
//        dest.writeValue(description);
//        dest.writeValue(tags);
//        dest.writeValue(context);
//        dest.writeValue(file);
//        dest.writeValue(duration);
//        dest.writeValue(thumbnail);
//        dest.writeValue(isFree);
//        dest.writeValue(order);
//        dest.writeValue(pageView);
//        dest.writeValue(createdAt);
//        dest.writeValue(updatedAt);
//        dest.writeValue(url);
//        dest.writeValue(apiUrl);
//        dest.writeValue(nextUrl);
//        dest.writeValue(nextApiUrl);
//        dest.writeValue(previousUrl);
//        dest.writeValue(previousApiUrl);
//        dest.writeValue(author);
//        dest.writeValue(pivot);
//        dest.writeValue(set);
//    }

}
