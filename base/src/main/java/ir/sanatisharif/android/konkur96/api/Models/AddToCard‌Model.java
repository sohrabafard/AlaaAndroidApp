package ir.sanatisharif.android.konkur96.api.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class AddToCard‌Model extends ErrorBase‌Model implements Parcelable {

    @SerializedName("order_id")
    private int order_id;

    @SerializedName("id")
    private int id;

    @SerializedName("orderproducttype")
    private OrderProductTypeModel orderproducttype;

    @SerializedName("product")
    private ProductModel product;

    @SerializedName("grandId")
    private String grandId;

    @SerializedName("price")
    private AllPrice‌Model price;

    @SerializedName("bons")
    private ArrayList<AllBon‌Model> bons;

    protected AddToCard‌Model(Parcel in) {
        super(in);
    }

//    @SerializedName("attributevalues")
//    private String attributevalues;

//    @SerializedName("photo")
//    private String photo;

//    @SerializedName("grandProduct")
//    private String grandProduct;


    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public OrderProductTypeModel getOrderproducttype() {
        return orderproducttype;
    }

    public void setOrderproducttype(OrderProductTypeModel orderproducttype) {
        this.orderproducttype = orderproducttype;
    }

    public ProductModel getProduct() {
        return product;
    }

    public void setProduct(ProductModel product) {
        this.product = product;
    }

    public String getGrandId() {
        return grandId;
    }

    public void setGrandId(String grandId) {
        this.grandId = grandId;
    }

    public AllPrice‌Model getPrice() {
        return price;
    }

    public void setPrice(AllPrice‌Model price) {
        this.price = price;
    }

    public ArrayList<AllBon‌Model> getBons() {
        return bons;
    }

    public void setBons(ArrayList<AllBon‌Model> bons) {
        this.bons = bons;
    }
}
