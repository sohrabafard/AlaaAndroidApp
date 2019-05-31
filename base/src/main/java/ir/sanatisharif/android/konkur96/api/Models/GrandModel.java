package ir.sanatisharif.android.konkur96.api.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GrandModel implements Parcelable {

    public static final Creator<GrandModel> CREATOR = new Creator<GrandModel>() {
        @Override
        public GrandModel createFromParcel(Parcel in) {
            return new GrandModel(in);
        }

        @Override
        public GrandModel[] newArray(int size) {
            return new GrandModel[size];
        }
    };
    @SerializedName("id")
    private             int                 id;
    @SerializedName("name")
    private             String              name;
    @SerializedName("url")
    private             String              url;
    @SerializedName("apiUrl")
    private             ApiUrlModel         apiUrl;
    @SerializedName("photo")
    private             String              photo;
    @SerializedName("attributes")
    @Expose
    private             AttributesModel     attributes;

    protected GrandModel(Parcel in) {
        id = in.readInt();
        name = in.readString();
        url = in.readString();
        apiUrl = in.readParcelable(ApiUrlModel.class.getClassLoader());
        photo = in.readString();
        attributes = in.readParcelable(AttributesModel.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(url);
        dest.writeParcelable(apiUrl, flags);
        dest.writeString(photo);
        dest.writeParcelable(attributes, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
