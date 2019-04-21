package ir.sanatisharif.android.konkur96.model.filter;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import ir.sanatisharif.android.konkur96.model.main_page.Datum;

public class SetFilterProductRoot extends Pagination implements Parcelable {

    @SerializedName("data")
    @Expose
    private List<SetFilterProduct> data = null;

    public List<SetFilterProduct> getData() {
        return data;
    }

    public void setData(List<SetFilterProduct> data) {
        this.data= data;
    }

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

    protected SetFilterProductRoot(Parcel in) {
        in.readList(this.data, (Datum.class.getClassLoader()));
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(data);
    }
}
