package ir.sanatisharif.android.konkur96.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import ir.sanatisharif.android.konkur96.api.Models.PaginationDataModel;

/**
 * Created by Mohamad on 2/23/2019.
 */

public class PaginationModel<T extends PaginationDataModel> implements Parcelable {

    @SerializedName("current_page")
    @Expose
    private             Integer             currentPage;

    @SerializedName("first_page_url")
    @Expose
    private             String              firstPageUrl;

    @SerializedName("from")
    @Expose
    private             Integer             from;

    @SerializedName("last_page")
    @Expose
    private             Integer             lastPage;

    @SerializedName("last_page_url")
    @Expose
    private             String              lastPageUrl;

    @SerializedName("next_page_url")
    @Expose
    private             String              nextPageUrl;

    @SerializedName("path")
    @Expose
    private             String              path;

    @SerializedName("per_page")
    @Expose
    private             Integer             perPage;

    @SerializedName("prev_page_url")
    @Expose
    private             String              prevPageUrl;

    @SerializedName("to")
    @Expose
    private             Integer             to;

    @SerializedName("total")
    @Expose
    private             Integer             total;

    @SerializedName("data")
    @Expose
    private ArrayList<T> data;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.currentPage);
        dest.writeString(this.firstPageUrl);
        dest.writeValue(this.from);
        dest.writeValue(this.lastPage);
        dest.writeString(this.lastPageUrl);
        dest.writeString(this.nextPageUrl);
        dest.writeString(this.path);
        dest.writeValue(this.perPage);
        dest.writeString(this.prevPageUrl);
        dest.writeValue(this.to);
        dest.writeValue(this.total);
        dest.writeList(this.data);
    }

    protected PaginationModel(Parcel in) {
        this.currentPage = (Integer) in.readValue(Integer.class.getClassLoader());
        this.firstPageUrl = in.readString();
        this.from = (Integer) in.readValue(Integer.class.getClassLoader());
        this.lastPage = (Integer) in.readValue(Integer.class.getClassLoader());
        this.lastPageUrl = in.readString();
        this.nextPageUrl = in.readString();
        this.path = in.readString();
        this.perPage = (Integer) in.readValue(Integer.class.getClassLoader());
        this.prevPageUrl = in.readString();
        this.to = (Integer) in.readValue(Integer.class.getClassLoader());
        this.total = (Integer) in.readValue(Integer.class.getClassLoader());

        if (this.total != null && this.total == 0)
            this.data = null;
        else
            this.data = (ArrayList<T>) in.createTypedArrayList(T.CREATOR);
    }

    public static final Creator<PaginationModel> CREATOR = new Creator<PaginationModel>() {
        @Override
        public PaginationModel createFromParcel(Parcel source) {
            return new PaginationModel(source);
        }

        @Override
        public PaginationModel[] newArray(int size) {
            return new PaginationModel[size];
        }
    };
}
