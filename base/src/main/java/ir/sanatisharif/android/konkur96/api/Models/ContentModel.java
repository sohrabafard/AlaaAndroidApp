package ir.sanatisharif.android.konkur96.api.Models;

import android.os.Parcel;
import android.util.Log;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import ir.sanatisharif.android.konkur96.app.AppConstants;
import ir.sanatisharif.android.konkur96.interfaces.LogUserActionsOnPublicContentInterface;
import ir.sanatisharif.android.konkur96.api.Models.filter.FilterBaseModel;
import ir.sanatisharif.android.konkur96.model.main_page.ApiUrl;
import ir.sanatisharif.android.konkur96.model.main_page.Author;
import ir.sanatisharif.android.konkur96.model.main_page.File;
import ir.sanatisharif.android.konkur96.model.main_page.NextApiUrl;
import ir.sanatisharif.android.konkur96.model.main_page.Pivot;
import ir.sanatisharif.android.konkur96.model.main_page.PreviousApiUrl;
import ir.sanatisharif.android.konkur96.model.main_page.Tags;

public class ContentModel extends PaginationDataModel implements LogUserActionsOnPublicContentInterface.Data, FilterBaseModel {

    public final static Creator<ContentModel> CREATOR = new Creator<ContentModel>() {

        @Override
        public ContentModel createFromParcel(Parcel in) {
            return new ContentModel(in);
        }

        @Override
        public ContentModel[] newArray(int size) {
            return (new ContentModel[size]);
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
    private SetModel set;

    protected ContentModel(Parcel in) {
        this.name = in.readString();
        this.description = in.readString();
        this.context = in.readString();
        this.thumbnail = in.readString();
        this.createdAt = in.readString();
        this.updatedAt = in.readString();
        this.url = in.readString();
        this.nextUrl = in.readString();
        this.previousUrl = in.readString();


        this.id = in.readInt();
        this.contenttypeId = in.readInt();
        this.duration = in.readInt();
        this.isFree = in.readInt();
        this.order = in.readInt();
        this.pageView = in.readInt();

        this.tags = ((Tags) in.readValue((Tags.class.getClassLoader())));
        this.file = ((File) in.readValue((File.class.getClassLoader())));
        this.author = in.readParcelable(Author.class.getClassLoader());
        this.apiUrl = in.readParcelable((ApiUrl.class.getClassLoader()));
        this.nextApiUrl = in.readParcelable((NextApiUrl.class.getClassLoader()));
        this.previousApiUrl = in.readParcelable((PreviousApiUrl.class.getClassLoader()));
        this.pivot = in.readParcelable((Pivot.class.getClassLoader()));
        this.set = in.readParcelable((SetModel.class.getClassLoader()));

    }

    public ContentModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ContentModel withId(int id) {
        this.id = id;
        return this;
    }

    public int getContenttypeId() {
        return contenttypeId;
    }

    public void setContenttypeId(int contenttypeId) {
        this.contenttypeId = contenttypeId;
    }

    public ContentModel withContenttypeId(int contenttypeId) {
        this.contenttypeId = contenttypeId;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ContentModel withName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ContentModel withDescription(String description) {
        this.description = description;
        return this;
    }

    public Tags getTags() {
        return tags;
    }

    public void setTags(Tags tags) {
        this.tags = tags;
    }

    public ContentModel withTags(Tags tags) {
        this.tags = tags;
        return this;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public ContentModel withContext(String context) {
        this.context = context;
        return this;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public ContentModel withFile(File file) {
        this.file = file;
        return this;
    }

    public Object getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public ContentModel withDuration(int duration) {
        this.duration = duration;
        return this;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public ContentModel withThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
        return this;
    }

    public int getIsFree() {
        return isFree;
    }

    public void setIsFree(int isFree) {
        this.isFree = isFree;
    }

    public ContentModel withIsFree(int isFree) {
        this.isFree = isFree;
        return this;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public ContentModel withOrder(int order) {
        this.order = order;
        return this;
    }

    public Object getPageView() {
        return pageView;
    }

    public void setPageView(Object pageView) {
        this.pageView = pageView;
    }

    public ContentModel withPageView(Object pageView) {
        this.pageView = pageView;
        return this;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public ContentModel withCreatedAt(String createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public ContentModel withUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ContentModel withUrl(String url) {
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

    public ContentModel withAuthor(Author author) {
        this.author = author;
        return this;
    }

    public SetModel getSet() {
        return set;
    }

    public void setSet(SetModel set) {
        this.set = set;
    }

    public ContentModel withSet(SetModel set) {
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

    @Override
    public String getUserActionTitle() {
        return getName();
    }

    @Override
    public String getUserActionUrl() {
        Log.i("Alaa\\ContentModel", "getUserActionUrl : url" + getUrl());
        return getUrl();
    }

    @Override
    public String getUserActionPhoto() {
        Log.i("Alaa\\ContentModel", "getUserActionPhoto ");
        return getThumbnail();
    }

    @Override
    public String getUserActionDescription() {
        Log.i("Alaa\\ContentModel", "getUserActionDescription ");
        return getDescription();
    }

    @Override
    public int getViewType() {
        if (this.contenttypeId == 8)
            return AppConstants.FILTER_VIDEO;
        if (this.contenttypeId == 1)
            return AppConstants.FILTER_PAMPHLET;
        if (this.contenttypeId == 9)
            return AppConstants.FILTER_ARTICLE;
        return -1;
    }
}
