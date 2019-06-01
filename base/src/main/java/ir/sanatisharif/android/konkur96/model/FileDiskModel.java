package ir.sanatisharif.android.konkur96.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FileDiskModel implements Parcelable {
    
    public static final Creator<FileDiskModel> CREATOR = new Creator<FileDiskModel>() {
        @Override
        public FileDiskModel createFromParcel(Parcel source) {
            return new FileDiskModel(source);
        }
        
        @Override
        public FileDiskModel[] newArray(int size) {
            return new FileDiskModel[size];
        }
    };
    @SerializedName("uuid")
    @Expose
    private             String                 uuid;
    @SerializedName("disk")
    @Expose
    private             String                 disk;
    @SerializedName("url")
    @Expose
    private             String                 url;
    @SerializedName("fileName")
    @Expose
    private             String                 fileName;
    @SerializedName("size")
    @Expose
    private             String                 size;
    @SerializedName("caption")
    @Expose
    private             String                 caption;
    @SerializedName("res")
    @Expose
    private             String                 res;
    @SerializedName("type")
    @Expose
    private             String                 type;
    @SerializedName("ext")
    @Expose
    private             String                 ext;
    @SerializedName("link")
    @Expose
    private             String                 link;
    private             String                 path;
    private             boolean                checked;
    private             String                 name;
    
    public FileDiskModel() {
    }
    
    protected FileDiskModel(Parcel in) {
        this.uuid = in.readString();
        this.disk = in.readString();
        this.url = in.readString();
        this.fileName = in.readString();
        this.size = in.readString();
        this.caption = in.readString();
        this.res = in.readString();
        this.type = in.readString();
        this.ext = in.readString();
        this.link = in.readString();
        this.path = in.readString();
        this.checked = in.readByte() != 0;
        this.name = in.readString();
    }
    
    @Override
    public int describeContents() {
        return 0;
    }
    
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.uuid);
        dest.writeString(this.disk);
        dest.writeString(this.url);
        dest.writeString(this.fileName);
        dest.writeString(this.size);
        dest.writeString(this.caption);
        dest.writeString(this.res);
        dest.writeString(this.type);
        dest.writeString(this.ext);
        dest.writeString(this.link);
        dest.writeString(this.path);
        dest.writeByte(this.checked ? (byte) 1 : (byte) 0);
        dest.writeString(this.name);
    }
    
    public String getUuid() {
        return uuid;
    }
    
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
    
    public String getDisk() {
        return disk;
    }
    
    public void setDisk(String disk) {
        this.disk = disk;
    }
    
    public String getUrl() {
        return url;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }
    
    public String getFileName() {
        return fileName;
    }
    
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    
    public Object getSize() {
        return size;
    }
    
    public void setSize(String size) {
        this.size = size;
    }
    
    public String getCaption() {
        return caption;
    }
    
    public void setCaption(String caption) {
        this.caption = caption;
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
    
    public String getExt() {
        return ext;
    }
    
    public void setExt(String ext) {
        this.ext = ext;
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
}
