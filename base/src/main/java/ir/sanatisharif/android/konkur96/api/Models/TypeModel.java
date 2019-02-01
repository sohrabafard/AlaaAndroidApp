package ir.sanatisharif.android.konkur96.api.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class TypeModel implements Parcelable {

    @SerializedName("id")
    private int id;

    @SerializedName("type")
    private String type;

    @SerializedName("hint")
    private String hint;

    protected TypeModel(Parcel in) {
        id = in.readInt();
        type = in.readString();
        hint = in.readString();
    }

    public static final Creator<TypeModel> CREATOR = new Creator<TypeModel>() {
        @Override
        public TypeModel createFromParcel(Parcel in) {
            return new TypeModel(in);
        }

        @Override
        public TypeModel[] newArray(int size) {
            return new TypeModel[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(type);
        dest.writeString(hint);
    }



}
