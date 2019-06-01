package ir.sanatisharif.android.konkur96.model.user;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Gender implements Parcelable {
    
    public final static Parcelable.Creator<Gender> CREATOR = new Creator<Gender>() {
        
        
        @SuppressWarnings({
                "unchecked"
        })
        public Gender createFromParcel(Parcel in) {
            return new Gender(in);
        }
        
        public Gender[] newArray(int size) {
            return (new Gender[size]);
        }
        
    };
    @SerializedName("id")
    @Expose
    private             Integer                    id;
    @SerializedName("name")
    @Expose
    private             String                     name;
    @SerializedName("description")
    @Expose
    private             String                     description;
    
    protected Gender(Parcel in) {
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.description = ((String) in.readValue((String.class.getClassLoader())));
    }
    
    public Gender() {
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
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(name);
        dest.writeValue(description);
    }
    
    public int describeContents() {
        return 0;
    }
}
