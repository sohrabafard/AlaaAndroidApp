package ir.sanatisharif.android.konkur96.api.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class BlockModel implements Parcelable {

    public static final Creator<BlockModel> CREATOR = new Creator<BlockModel>() {
        @Override
        public BlockModel createFromParcel(Parcel in) {
            return new BlockModel(in);
        }

        @Override
        public BlockModel[] newArray(int size) {
            return new BlockModel[size];
        }
    };
    @SerializedName("current_page")
    @Expose
    private int current_page;

    @SerializedName("data")
    @Expose
    private ArrayList<BlockDataModel> data;

    @SerializedName("first_page_url")
    @Expose
    private String first_page_url;

    @SerializedName("from")
    @Expose
    private int from;

    @SerializedName("last_page")
    @Expose
    private int last_page;

    @SerializedName("last_page_url")
    @Expose
    private String last_page_url;

    @SerializedName("next_page_url")
    @Expose
    private String next_page_url;

    @SerializedName("path")
    @Expose
    private String path;

    @SerializedName("per_page")
    @Expose
    private int per_page;

    @SerializedName("prev_page_url")
    @Expose
    private String prev_page_url;

    @SerializedName("to")
    @Expose
    private int to;

    @SerializedName("total")
    @Expose
    private int total;

    protected BlockModel(Parcel in) {
        current_page = in.readInt();
        data = in.createTypedArrayList(BlockDataModel.CREATOR);
        first_page_url = in.readString();
        from = in.readInt();
        last_page = in.readInt();
        last_page_url = in.readString();
        next_page_url = in.readString();
        path = in.readString();
        per_page = in.readInt();
        prev_page_url = in.readString();
        to = in.readInt();
        total = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(current_page);
        dest.writeTypedList(data);
        dest.writeString(first_page_url);
        dest.writeInt(from);
        dest.writeInt(last_page);
        dest.writeString(last_page_url);
        dest.writeString(next_page_url);
        dest.writeString(path);
        dest.writeInt(per_page);
        dest.writeString(prev_page_url);
        dest.writeInt(to);
        dest.writeInt(total);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public int getCurrentPage() {
        return current_page;
    }

    public void setCurrentPage(int current_page) {
        this.current_page = current_page;
    }

    public ArrayList<BlockDataModel> getData() {
        return data;
    }

    public void setData(ArrayList<BlockDataModel> data) {
        this.data = data;
    }

    public String getFirstPageUrl() {
        return first_page_url;
    }

    public void setFirstPageUrl(String first_page_url) {
        this.first_page_url = first_page_url;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getLastPage() {
        return last_page;
    }

    public void setLastPage(int last_page) {
        this.last_page = last_page;
    }

    public String getLastPageUrl() {
        return last_page_url;
    }

    public void setLastPageUrl(String last_page_url) {
        this.last_page_url = last_page_url;
    }

    public String getNextPageUrl() {
        return next_page_url;
    }

    public void setNextPageUrl(String next_page_url) {
        this.next_page_url = next_page_url;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getPerPage() {
        return per_page;
    }

    public void setPerPage(int per_page) {
        this.per_page = per_page;
    }

    public String getPrevPageUrl() {
        return prev_page_url;
    }

    public void setPrevPageUrl(String prev_page_url) {
        this.prev_page_url = prev_page_url;
    }

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
