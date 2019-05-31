package ir.sanatisharif.android.konkur96.api.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ProductIndexResultModel implements Parcelable {

    public static final Creator<ProductIndexResultModel>
                                                      CREATOR =
            new Creator<ProductIndexResultModel>() {
                @Override
                public ProductIndexResultModel createFromParcel(Parcel in) {
                    return new ProductIndexResultModel(in);
                }

                @Override
                public ProductIndexResultModel[] newArray(int size) {
                    return new ProductIndexResultModel[size];
                }
            };
    @SerializedName("result")
    @Expose
    private             PaginationModel<ProductModel> result;

    @SerializedName("tags")
    @Expose
    private ArrayList<String> tags;

    protected ProductIndexResultModel(Parcel in) {
        result = in.readParcelable(PaginationModel.class.getClassLoader());
        tags = in.createStringArrayList();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(result, flags);
        dest.writeStringList(tags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public PaginationModel<ProductModel> getResult() {
        return result;
    }

    public void setResult(PaginationModel<ProductModel> result) {
        this.result = result;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }
}
