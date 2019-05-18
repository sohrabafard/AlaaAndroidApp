package ir.sanatisharif.android.konkur96.api.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class myProductsModel implements Parcelable {

    public static final Creator<myProductsModel> CREATOR = new Creator<myProductsModel>() {
        @Override
        public myProductsModel createFromParcel(Parcel in) {
            return new myProductsModel(in);
        }

        @Override
        public myProductsModel[] newArray(int size) {
            return new myProductsModel[size];
        }
    };
    @SerializedName("user_id")
    private int user_id;
    @SerializedName("data")
    private ArrayList<MyProductsDataModel> data;

    protected myProductsModel(Parcel in) {
        user_id = in.readInt();
        data = in.createTypedArrayList(MyProductsDataModel.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(user_id);
        dest.writeTypedList(data);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public ArrayList<MyProductsDataModel> getData() {
        return data;
    }

    public void setData(ArrayList<MyProductsDataModel> data) {
        this.data = data;
    }
}
