package ir.sanatisharif.android.konkur96.api.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class AttributesModel implements Parcelable {

    @SerializedName("main")
    private ArrayList<AttributesModel> main;

    @SerializedName("information")
    private ArrayList<AttributesModel> information;

    @SerializedName("extra")
    private ArrayList<AttributesModel> extra;


    protected AttributesModel(Parcel in) {
        main = in.createTypedArrayList(AttributesModel.CREATOR);
        information = in.createTypedArrayList(AttributesModel.CREATOR);
        extra = in.createTypedArrayList(AttributesModel.CREATOR);
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

    public static final Creator<AttributesModel> CREATOR = new Creator<AttributesModel>() {
        @Override
        public AttributesModel createFromParcel(Parcel in) {
            return new AttributesModel(in);
        }

        @Override
        public AttributesModel[] newArray(int size) {
            return new AttributesModel[size];
        }
    };

    public ArrayList<AttributesModel> getMain() {
        return main;
    }

    public void setMain(ArrayList<AttributesModel> main) {
        this.main = main;
    }

    public ArrayList<AttributesModel> getInformation() {
        return information;
    }

    public void setInformation(ArrayList<AttributesModel> information) {
        this.information = information;
    }

    public ArrayList<AttributesModel> getExtra() {
        return extra;
    }

    public void setExtra(ArrayList<AttributesModel> extra) {
        this.extra = extra;
    }
}
