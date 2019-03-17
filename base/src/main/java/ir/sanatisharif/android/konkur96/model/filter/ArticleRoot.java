package ir.sanatisharif.android.konkur96.model.filter;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import ir.sanatisharif.android.konkur96.model.main_page.Datum;

/**
 * Created by Mohamad on 2/23/2019.
 */

public class ArticleRoot extends Pagination implements Parcelable {

    @SerializedName("data")
    @Expose
    private List<ArticleCourse> data = null;

    public List<ArticleCourse> getData() {
        return data;
    }

    public void setData(List<ArticleCourse> data) {
        this.data = data;
    }

    public final static Creator<ArticleRoot> CREATOR = new Creator<ArticleRoot>() {


        @SuppressWarnings({
                "unchecked"
        })
        public ArticleRoot createFromParcel(Parcel in) {
            return new ArticleRoot(in);
        }

        public ArticleRoot[] newArray(int size) {
            return (new ArticleRoot[size]);
        }

    };

    protected ArticleRoot(Parcel in) {
        in.readList(this.data, (Datum.class.getClassLoader()));
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(data);
    }
}