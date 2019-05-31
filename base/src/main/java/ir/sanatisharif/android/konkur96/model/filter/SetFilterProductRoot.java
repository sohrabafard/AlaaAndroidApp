package ir.sanatisharif.android.konkur96.model.filter;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import ir.sanatisharif.android.konkur96.api.Models.BlockDataModel;

public class SetFilterProductRoot extends Pagination implements Parcelable {

    public final static Creator<SetFilterProductRoot> CREATOR = new Creator<SetFilterProductRoot>() {

        @SuppressWarnings({
                "unchecked"
        })
        public SetFilterProductRoot createFromParcel(Parcel in) {
            return new SetFilterProductRoot(in);
        }

        public SetFilterProductRoot[] newArray(int size) {
            return (new SetFilterProductRoot[size]);
        }

    };
    @SerializedName("data")
    @Expose
    private List<SetFilterProduct> data = null;

    protected SetFilterProductRoot(Parcel in) {
        in.readList(this.data, (BlockDataModel.class.getClassLoader()));
    }

    public List<SetFilterProduct> getData() {
        return data;
    }

    public void setData(List<SetFilterProduct> data) {
        this.data = data;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(data);
    }
}
