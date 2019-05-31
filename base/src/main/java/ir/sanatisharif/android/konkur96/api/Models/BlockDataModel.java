package ir.sanatisharif.android.konkur96.api.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import ir.sanatisharif.android.konkur96.model.main_page.SetModel;

public class BlockDataModel implements Parcelable {

    public static final Creator<BlockDataModel> CREATOR = new Creator<BlockDataModel>() {
        @Override
        public BlockDataModel createFromParcel(Parcel in) {
            return new BlockDataModel(in);
        }

        @Override
        public BlockDataModel[] newArray(int size) {
            return new BlockDataModel[size];
        }
    };

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("type")
    @Expose
    private int type;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("order")
    @Expose
    private int order;

    @SerializedName("updated_at")
    @Expose
    private String updated_at;

    @SerializedName("offer")
    @Expose
    private boolean offer;


    @SerializedName("url")
    @Expose
    private String url;

    @SerializedName("contents")
    @Expose
    private ArrayList<ContentModel> contents;

    @SerializedName("products")
    @Expose
    private ArrayList<ProductModel> products;

    @SerializedName("banners")
    @Expose
    private ArrayList<MainBannerModel> banners;

    @SerializedName("sets")
    @Expose
    private ArrayList<SetModel> sets;


    protected BlockDataModel(Parcel in) {
        id = in.readInt();
        type = in.readInt();
        title = in.readString();
        order = in.readInt();
        offer = in.readByte() != 0;
        updated_at = in.readString();
        url = in.readString();

        products = in.createTypedArrayList(ProductModel.CREATOR);
        banners = in.createTypedArrayList(MainBannerModel.CREATOR);
        contents = in.createTypedArrayList(ContentModel.CREATOR);
        sets = in.createTypedArrayList(SetModel.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(type);
        dest.writeString(title);
        dest.writeInt(order);
        dest.writeByte((byte) (offer ? 1 : 0));
        dest.writeString(updated_at);
        dest.writeString(url);
        dest.writeTypedList(products);
        dest.writeTypedList(banners);
        dest.writeTypedList(sets);
        dest.writeTypedList(contents);
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

    public boolean isOffer() {
        return offer;
    }

    public void setOffer(boolean offer) {
        this.offer = offer;
    }

    public String getUpdatedAt() {
        return updated_at;
    }

    public void setUpdatedAt(String updated_at) {
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

    public ArrayList<MainBannerModel> getBanners() {
        return banners;
    }

    public void setBanners(ArrayList<MainBannerModel> banners) {
        this.banners = banners;
    }

    public ArrayList<ContentModel> getContents() {
        return contents;
    }

    public void setContents(ArrayList<ContentModel> contents) {
        this.contents = contents;
    }

    public ArrayList<SetModel> getSets() {
        return sets;
    }

    public void setSets(ArrayList<SetModel> sets) {
        this.sets = sets;
    }
}
