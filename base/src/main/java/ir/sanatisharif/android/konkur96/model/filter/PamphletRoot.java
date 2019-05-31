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

public class PamphletRoot extends Pagination implements Parcelable {

    public final static Creator<PamphletRoot> CREATOR         = new Creator<PamphletRoot>() {


        @SuppressWarnings({
                "unchecked"
        })
        public PamphletRoot createFromParcel(Parcel in) {
            return new PamphletRoot(in);
        }

        public PamphletRoot[] newArray(int size) {
            return (new PamphletRoot[size]);
        }

    };
    @SerializedName("data")
    @Expose
    private             List<PamphletCourse>  pamphletCourses = null;

    protected PamphletRoot(Parcel in) {
        in.readList(this.pamphletCourses, (BlockDataModel.class.getClassLoader()));
    }

    public List<PamphletCourse> getData() {
        return pamphletCourses;
    }

    public void setData(List<PamphletCourse> data) {
        this.pamphletCourses = data;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(pamphletCourses);
    }

}
