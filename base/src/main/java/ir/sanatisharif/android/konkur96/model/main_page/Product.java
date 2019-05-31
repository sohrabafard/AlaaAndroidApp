package ir.sanatisharif.android.konkur96.model.main_page;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import ir.sanatisharif.android.konkur96.api.Models.BlockDataModel;
import ir.sanatisharif.android.konkur96.api.Models.ProductModel;
import ir.sanatisharif.android.konkur96.model.filter.Pagination;

/**
 * Created by Mohamad on 2/17/2019.
 */

public class Product extends Pagination implements Parcelable {

    public final static Creator<Product>   CREATOR = new Creator<Product>() {

        @SuppressWarnings({
                "unchecked"
        })
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        public Product[] newArray(int size) {
            return (new Product[size]);
        }

    };
    @SerializedName("data")
    @Expose
    private             List<ProductModel> data    = null;

    protected Product(Parcel in) {
        in.readList(this.data, (BlockDataModel.class.getClassLoader()));
    }

    public List<ProductModel> getData() {
        return data;
    }

    public void setData(List<ProductModel> data) {
        this.data = data;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(data);
    }
}
