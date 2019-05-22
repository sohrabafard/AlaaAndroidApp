package ir.sanatisharif.android.konkur96.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Video implements Parcelable {

    public final static Creator<Video> CREATOR = new Creator<Video>() {


        public Video createFromParcel(Parcel in) {
            return new Video(in);
        }

        public Video[] newArray(int size) {
            return (new Video[size]);
        }

    };
    @SerializedName("uuid")
    @Expose
    private String uuid;
    @SerializedName("disk")
    @Expose
    private String disk;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("fileName")
    @Expose
    private String fileName;
    @SerializedName("size")
    @Expose
    private Object size;
    @SerializedName("caption")
    @Expose
    private String caption;
    @SerializedName("res")
    @Expose
    private String res;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("ext")
    @Expose
    private String ext;
    @SerializedName("link")
    @Expose
    private String link;
    private String path;
    private boolean checked;
    private String name;

    protected Video(Parcel in) {
        this.uuid = ((String) in.readValue((String.class.getClassLoader())));
        this.disk = ((String) in.readValue((String.class.getClassLoader())));
        this.url = ((String) in.readValue((String.class.getClassLoader())));
        this.fileName = ((String) in.readValue((String.class.getClassLoader())));
        this.size = in.readValue((Object.class.getClassLoader()));
        this.caption = ((String) in.readValue((String.class.getClassLoader())));
        this.res = ((String) in.readValue((String.class.getClassLoader())));
        this.type = ((String) in.readValue((String.class.getClassLoader())));
        this.ext = ((String) in.readValue((String.class.getClassLoader())));
        this.link = ((String) in.readValue((String.class.getClassLoader())));
        this.path = ((String) in.readValue((String.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.checked = ((Boolean) in.readValue((String.class.getClassLoader())));
    }

    public Video() {
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Video withUuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public String getDisk() {
        return disk;
    }

    public void setDisk(String disk) {
        this.disk = disk;
    }

    public Video withDisk(String disk) {
        this.disk = disk;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public Object getSize() {
        return size;
    }

    public void setSize(Object size) {
        this.size = size;
    }


    public String getCaption() {
        return caption;
    }


    public String getRes() {
        return res;
    }

    public void setRes(String res) {
        this.res = res;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(uuid);
        dest.writeValue(disk);
        dest.writeValue(url);
        dest.writeValue(fileName);
        dest.writeValue(size);
        dest.writeValue(caption);
        dest.writeValue(res);
        dest.writeValue(type);
        dest.writeValue(ext);
        dest.writeValue(link);
        dest.writeValue(path);
        dest.writeValue(checked);
        dest.writeValue(name);
    }

    public int describeContents() {
        return 0;
    }

}
