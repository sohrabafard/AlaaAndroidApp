package ir.sanatisharif.android.konkur96.api.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class AddToCardModel extends ErrorBase implements Parcelable {

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
    private AllPriceModel price;

    @SerializedName("bons")
    private ArrayList<AllBonModel> bons;

    protected AddToCardModel(Parcel in) {
        super(in);
        order_id = in.readInt();
        id = in.readInt();
        orderproducttype = in.readParcelable(OrderProductTypeModel.class.getClassLoader());
        product = in.readParcelable(ProductModel.class.getClassLoader());
        grandId = in.readString();
        price = in.readParcelable(AllPriceModel.class.getClassLoader());
        bons = in.createTypedArrayList(AllBonModel.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(order_id);
        dest.writeInt(id);
        dest.writeParcelable(orderproducttype, flags);
        dest.writeParcelable(product, flags);
        dest.writeString(grandId);
        dest.writeParcelable(price, flags);
        dest.writeTypedList(bons);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AddToCardModel> CREATOR = new Creator<AddToCardModel>() {
        @Override
        public AddToCardModel createFromParcel(Parcel in) {
            return new AddToCardModel(in);
        }

        @Override
        public AddToCardModel[] newArray(int size) {
            return new AddToCardModel[size];
        }
    };

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

    public AllPriceModel getPrice() {
        return price;
    }

    public void setPrice(AllPriceModel price) {
        this.price = price;
    }

    public ArrayList<AllBonModel> getBons() {
        return bons;
    }

    public void setBons(ArrayList<AllBonModel> bons) {
        this.bons = bons;
    }
}
