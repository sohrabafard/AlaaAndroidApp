package ir.sanatisharif.android.konkur96.api.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MyProductsDataModel implements Parcelable {
    
    public static final Creator<MyProductsDataModel> CREATOR = new Creator<MyProductsDataModel>() {
        @Override
        public MyProductsDataModel createFromParcel(Parcel in) {
            return new MyProductsDataModel(in);
        }
        
        @Override
        public MyProductsDataModel[] newArray(int size) {
            return new MyProductsDataModel[size];
        }
    };
    @SerializedName("id")
    private             int                          id;
    @SerializedName("order")
    private             int                          order;
    @SerializedName("title")
    private             String                       title;
    @SerializedName("url")
    private             String                       url;
    @SerializedName("offer")
    private             Boolean                      offer;
    @SerializedName("products")
    private             ArrayList<ProductModel>      products;
    
    protected MyProductsDataModel(Parcel in) {
        id = in.readInt();
        order = in.readInt();
        title = in.readString();
        url = in.readString();
        byte tmpOffer = in.readByte();
        offer = tmpOffer == 0 ? null : tmpOffer == 1;
        products = in.createTypedArrayList(ProductModel.CREATOR);
    }
    
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(order);
        dest.writeString(title);
        dest.writeString(url);
        dest.writeByte((byte) (offer == null ? 0 : offer ? 1 : 2));
        dest.writeTypedList(products);
    }
    
    @Override
    public int describeContents() {
        return 0;
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getOrder() {
        return order;
    }
    
    public void setOrder(int order) {
        this.order = order;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getUrl() {
        return url;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }
    
    public Boolean getOffer() {
        return offer;
    }
    
    public void setOffer(Boolean offer) {
        this.offer = offer;
    }
    
    public ArrayList<ProductModel> getProducts() {
        return products;
    }
    
    public void setProducts(ArrayList<ProductModel> products) {
        this.products = products;
    }
}
