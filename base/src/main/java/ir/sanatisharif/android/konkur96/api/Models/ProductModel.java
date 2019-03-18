package ir.sanatisharif.android.konkur96.api.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public class ProductModel implements Parcelable {

    @SerializedName("id")
    private int id;

    @SerializedName("redirectUrl")
    private String redirectUrl;

    @SerializedName("name")
    private String name;

    @SerializedName("isFree")
    private int isFree;

    @SerializedName("amount")
    private long amount;

    @SerializedName("shortDescription")
    private String shortDescription;

    @SerializedName("longDescription")
    private String longDescription;

    @SerializedName("tags")
    private TagModel tags;

    @SerializedName("introVideo")
    private String introVideo;

    @SerializedName("order")
    private int order;

//    @SerializedName("page_view")
//    private String page_view;

    @SerializedName("updated_at")
    private String updated_at;

//    @SerializedName("gift")
//    private String gift;

    @SerializedName("type")
    private TypeModel type;

    @SerializedName("photo")
    private String photo;

    @SerializedName("attributes")
    @Expose
    private AttributesModel attributes;

//    @SerializedName("samplePhotos")
//    @Expose
//    private Map<String, ProductPhotoModel> samplePhotos;

    @SerializedName("samplePhotos")
    @Expose
    private ArrayList<ProductPhotoModel> samplePhotos;

    @SerializedName("price")
    private PriceModel price;

    @SerializedName("bons")
    private ArrayList<BonModel> bons;

    @SerializedName("children")
    private ArrayList<ProductModel> children;

    @SerializedName("sets")
    private ArrayList<ProductSetModel> sets;


    protected ProductModel(Parcel in) {
        id = in.readInt();
        redirectUrl = in.readString();
        name = in.readString();
        isFree = in.readInt();
        amount = in.readLong();
        shortDescription = in.readString();
        longDescription = in.readString();
        tags = in.readParcelable(TagModel.class.getClassLoader());
        introVideo = in.readString();
        order = in.readInt();
        updated_at = in.readString();
        type = in.readParcelable(TypeModel.class.getClassLoader());
        photo = in.readString();
        attributes = in.readParcelable(AttributesModel.class.getClassLoader());
        samplePhotos = in.createTypedArrayList(ProductPhotoModel.CREATOR);
        price = in.readParcelable(PriceModel.class.getClassLoader());
        bons = in.createTypedArrayList(BonModel.CREATOR);
        children = in.createTypedArrayList(ProductModel.CREATOR);
        sets = in.createTypedArrayList(ProductSetModel.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(redirectUrl);
        dest.writeString(name);
        dest.writeInt(isFree);
        dest.writeLong(amount);
        dest.writeString(shortDescription);
        dest.writeString(longDescription);
        dest.writeParcelable(tags, flags);
        dest.writeString(introVideo);
        dest.writeInt(order);
        dest.writeString(updated_at);
        dest.writeParcelable(type, flags);
        dest.writeString(photo);
        dest.writeParcelable(attributes, flags);
        dest.writeTypedList(samplePhotos);
        dest.writeParcelable(price, flags);
        dest.writeTypedList(bons);
        dest.writeTypedList(children);
        dest.writeTypedList(sets);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ProductModel> CREATOR = new Creator<ProductModel>() {
        @Override
        public ProductModel createFromParcel(Parcel in) {
            return new ProductModel(in);
        }

        @Override
        public ProductModel[] newArray(int size) {
            return new ProductModel[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIsFree() {
        return isFree;
    }

    public void setIsFree(int isFree) {
        this.isFree = isFree;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public TagModel getTags() {
        return tags;
    }

    public void setTags(TagModel tags) {
        this.tags = tags;
    }

    public String getIntroVideo() {
        return introVideo;
    }

    public void setIntroVideo(String introVideo) {
        this.introVideo = introVideo;
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

    public TypeModel getType() {
        return type;
    }

    public void setType(TypeModel type) {
        this.type = type;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public AttributesModel getAttributes() {
        return attributes;
    }

    public void setAttributes(AttributesModel attributes) {
        this.attributes = attributes;
    }

    public ArrayList<ProductPhotoModel> getSamplePhotos() {
        return samplePhotos;
    }

    public void setSamplePhotos(ArrayList<ProductPhotoModel> samplePhotos) {
        this.samplePhotos = samplePhotos;
    }

    public PriceModel getPrice() {
        return price;
    }

    public void setPrice(PriceModel price) {
        this.price = price;
    }

    public ArrayList<BonModel> getBons() {
        return bons;
    }

    public void setBons(ArrayList<BonModel> bons) {
        this.bons = bons;
    }

    public ArrayList<ProductModel> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<ProductModel> children) {
        this.children = children;
    }

    public ArrayList<ProductSetModel> getSets() {
        return sets;
    }

    public void setSets(ArrayList<ProductSetModel> sets) {
        this.sets = sets;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return id == ((ProductModel) o).id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}