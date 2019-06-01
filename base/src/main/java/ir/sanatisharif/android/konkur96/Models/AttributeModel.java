package ir.sanatisharif.android.konkur96.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class AttributeModel implements Parcelable {
    
    public static final Creator<AttributeModel>       CREATOR = new Creator<AttributeModel>() {
        @Override
        public AttributeModel createFromParcel(Parcel in) {
            return new AttributeModel(in);
        }
        
        @Override
        public AttributeModel[] newArray(int size) {
            return new AttributeModel[size];
        }
    };
    @SerializedName("type")
    private             String                        type;
    @SerializedName("title")
    private             String                        title;
    @SerializedName("control")
    private             String                        control;
    @SerializedName("data")
    private             ArrayList<AttributeDataModel> data;
    
    protected AttributeModel(Parcel in) {
        type = in.readString();
        title = in.readString();
        control = in.readString();
        data = in.createTypedArrayList(AttributeDataModel.CREATOR);
    }
    
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(type);
        dest.writeString(title);
        dest.writeString(control);
        dest.writeTypedList(data);
    }
    
    @Override
    public int describeContents() {
        return 0;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getControl() {
        return control;
    }
    
    public void setControl(String control) {
        this.control = control;
    }
    
    public ArrayList<AttributeDataModel> getData() {
        return data;
    }
    
    public void setData(ArrayList<AttributeDataModel> data) {
        this.data = data;
    }
    
    
}
