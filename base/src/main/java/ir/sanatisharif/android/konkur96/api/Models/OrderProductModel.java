package ir.sanatisharif.android.konkur96.api.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class OrderProductModel implements Parcelable {
    
    public static final Creator<OrderProductModel> CREATOR = new Creator<OrderProductModel>() {
        @Override
        public OrderProductModel createFromParcel(Parcel in) {
            return new OrderProductModel(in);
        }
        
        @Override
        public OrderProductModel[] newArray(int size) {
            return new OrderProductModel[size];
        }
    };
    @SerializedName("id")
    private             int                        id;
    @SerializedName("order_id")
    private             int                        orderId;
    @SerializedName("quantity")
    private             int                        quantity;
    @SerializedName("product")
    private             ProductModel               product;
    @SerializedName("grandId")
    @Expose
    private             int                        grandId;
    @SerializedName("price")
    @Expose
    private             PriceModel                 price;
    @SerializedName("bons")
    @Expose
    private             BonModel                   bons;
    @SerializedName("attributevalues")
    @Expose
    private             ArrayList<AttributesModel> attributeValues;
    @SerializedName("photo")
    @Expose
    private             String                     photo;
    
    protected OrderProductModel(Parcel in) {
        id = in.readInt();
        orderId = in.readInt();
        quantity = in.readInt();
        product = in.readParcelable(ProductModel.class.getClassLoader());
        grandId = in.readInt();
        price = in.readParcelable(PriceModel.class.getClassLoader());
        bons = in.readParcelable(BonModel.class.getClassLoader());
        attributeValues = in.createTypedArrayList(AttributesModel.CREATOR);
        photo = in.readString();
    }
    
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(orderId);
        dest.writeInt(quantity);
        dest.writeParcelable(product, flags);
        dest.writeInt(grandId);
        dest.writeParcelable(price, flags);
        dest.writeParcelable(bons, flags);
        dest.writeTypedList(attributeValues);
        dest.writeString(photo);
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
    
    public int getOrderId() {
        return orderId;
    }
    
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
    
    public int getQuantity() {
        return quantity;
    }
    
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    public ProductModel getProduct() {
        return product;
    }
    
    public void setProduct(ProductModel product) {
        this.product = product;
    }
    
    public int getGrandId() {
        return grandId;
    }
    
    public void setGrandId(int grandId) {
        this.grandId = grandId;
    }
    
    public PriceModel getPrice() {
        return price;
    }
    
    public void setPrice(PriceModel price) {
        this.price = price;
    }
    
    public BonModel getBons() {
        return bons;
    }
    
    public void setBons(BonModel bons) {
        this.bons = bons;
    }
    
    public ArrayList<AttributesModel> getAttributeValues() {
        return attributeValues;
    }
    
    public void setAttributeValues(ArrayList<AttributesModel> attributeValues) {
        this.attributeValues = attributeValues;
    }
    
    public String getPhoto() {
        return photo;
    }
    
    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
