package ir.sanatisharif.android.konkur96.api.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class AttributesModel implements Parcelable {

    public static final Creator<AttributesModel>  CREATOR = new Creator<AttributesModel>() {
        @Override
        public AttributesModel createFromParcel(Parcel in) {
            return new AttributesModel(in);
        }

        @Override
        public AttributesModel[] newArray(int size) {
            return new AttributesModel[size];
        }
    };
    @SerializedName("main")
    private             ArrayList<AttributeModel> main;
    @SerializedName("information")
    private             ArrayList<AttributeModel> information;
    @SerializedName("extra")
    private             ArrayList<AttributeModel> extra;

    protected AttributesModel(Parcel in) {
        main = in.createTypedArrayList(AttributeModel.CREATOR);
        information = in.createTypedArrayList(AttributeModel.CREATOR);
        extra = in.createTypedArrayList(AttributeModel.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(main);
        dest.writeTypedList(information);
        dest.writeTypedList(extra);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public ArrayList<AttributeModel> getMain() {
        return main;
    }

    public void setMain(ArrayList<AttributeModel> main) {
        this.main = main;
    }

    public ArrayList<AttributeModel> getInformation() {
        return information;
    }

    public void setInformation(ArrayList<AttributeModel> information) {
        this.information = information;
    }

    public ArrayList<AttributeModel> getExtra() {
        return extra;
    }

    public void setExtra(ArrayList<AttributeModel> extra) {
        this.extra = extra;
    }
}
