package ir.sanatisharif.android.konkur96.model.filter;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import ir.sanatisharif.android.konkur96.api.Models.BlockDataModel;


/**
 * Created by Mohamad on 2/23/2019.
 */

public class SetFilterRoot extends Pagination implements Parcelable {

    public final static Creator<SetFilterRoot> CREATOR = new Creator<SetFilterRoot>() {

        @SuppressWarnings({
                "unchecked"
        })
        public SetFilterRoot createFromParcel(Parcel in) {
            return new SetFilterRoot(in);
        }

        public SetFilterRoot[] newArray(int size) {
            return (new SetFilterRoot[size]);
        }

    };
    @SerializedName("data")
    @Expose
    private             List<SetFilterCourse>  data    = null;

    protected SetFilterRoot(Parcel in) {
        in.readList(this.data, (BlockDataModel.class.getClassLoader()));
    }

    public List<SetFilterCourse> getData() {
        return data;
    }

    public void setData(List<SetFilterCourse> data) {
        this.data = data;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(data);
    }
}
