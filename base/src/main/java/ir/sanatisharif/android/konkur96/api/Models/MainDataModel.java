package ir.sanatisharif.android.konkur96.api.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MainDataModel implements Parcelable {

    @SerializedName("id")
    private int id;

    @SerializedName("type")
    private int type;

    @SerializedName("title")
    private String title;

    @SerializedName("order")
    private int order;

    @SerializedName("updated_at")
    private String updated_at;

    @SerializedName("url")
    private String url;

//    @SerializedName("contents")
//    private String contents;

//    @SerializedName("sets")
//    private String sets;

    @SerializedName("products")
    private ArrayList<ProductModel> products;

//    @SerializedName("banners")
//    private String banners;


    protected MainDataModel(Parcel in) {
        id = in.readInt();
        type = in.readInt();
        title = in.readString();
        order = in.readInt();
        updated_at = in.readString();
        url = in.readString();
        products = in.createTypedArrayList(ProductModel.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(type);
        dest.writeString(title);
        dest.writeInt(order);
        dest.writeString(updated_at);
        dest.writeString(url);
        dest.writeTypedList(products);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MainDataModel> CREATOR = new Creator<MainDataModel>() {
        @Override
        public MainDataModel createFromParcel(Parcel in) {
            return new MainDataModel(in);
        }

        @Override
        public MainDataModel[] newArray(int size) {
            return new MainDataModel[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ArrayList<ProductModel> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<ProductModel> products) {
        this.products = products;
    }
}
