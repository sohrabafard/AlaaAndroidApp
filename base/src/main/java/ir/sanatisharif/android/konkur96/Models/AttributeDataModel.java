package ir.sanatisharif.android.konkur96.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class AttributeDataModel implements Parcelable {
    
    public static final Creator<AttributeDataModel> CREATOR = new Creator<AttributeDataModel>() {
        @Override
        public AttributeDataModel createFromParcel(Parcel in) {
            return new AttributeDataModel(in);
        }
        
        @Override
        public AttributeDataModel[] newArray(int size) {
            return new AttributeDataModel[size];
        }
    };
    @SerializedName("name")
    private             String                      name;
    @SerializedName("id")
    private             int                         id;
    
    protected AttributeDataModel(Parcel in) {
        name = in.readString();
        id = in.readInt();
    }
    
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(id);
    }
    
    @Override
    public int describeContents() {
        return 0;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    
}
