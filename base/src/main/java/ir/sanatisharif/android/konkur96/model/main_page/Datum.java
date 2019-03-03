
package ir.sanatisharif.android.konkur96.model.main_page;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import ir.sanatisharif.android.konkur96.model.Content;

public class Datum implements Parcelable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("order")
    @Expose
    private Integer order;
    @SerializedName("updated_at")
    @Expose
    private Object updatedAt;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("offer")
    @Expose
    private Boolean offer;
    @SerializedName("contents")
    @Expose
    private List<Content> contents = null;
    @SerializedName("sets")
    @Expose
    private List<Set> sets = null;
    @SerializedName("products")
    @Expose
    private List<Product> products = null;
    @SerializedName("banners")
    @Expose
    private List<Banner> banners = null;
    public final static Creator<Datum> CREATOR = new Creator<Datum>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Datum createFromParcel(Parcel in) {
            return new Datum(in);
        }

        public Datum[] newArray(int size) {
            return (new Datum[size]);
        }

    }
    ;

    protected Datum(Parcel in) {
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.title = ((String) in.readValue((String.class.getClassLoader())));
        this.order = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.updatedAt = ((Object) in.readValue((Object.class.getClassLoader())));
        this.url = ((String) in.readValue((String.class.getClassLoader())));
        this.offer = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        in.readList(this.contents, (Object.class.getClassLoader()));
        in.readList(this.sets, (Set.class.getClassLoader()));
        in.readList(this.products, (Object.class.getClassLoader()));
        in.readList(this.banners, (Object.class.getClassLoader()));
    }

    public Datum() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Object getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Object updatedAt) {
        this.updatedAt = updatedAt;
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

    public List<Content> getContents() {
        return contents;
    }

    public void setContents(List<Content> contents) {
        this.contents = contents;
    }

    public List<Set> getSets() {
        return sets;
    }

    public void setSets(List<Set> sets) {
        this.sets = sets;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<Banner> getBanners() {
        return banners;
    }

    public void setBanners(List<Banner> banners) {
        this.banners = banners;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(title);
        dest.writeValue(order);
        dest.writeValue(updatedAt);
        dest.writeValue(url);
        dest.writeValue(offer);
        dest.writeList(contents);
        dest.writeList(sets);
        dest.writeList(products);
        dest.writeList(banners);
    }

    public int describeContents() {
        return  0;
    }

}
