package ir.sanatisharif.android.konkur96.api.Models.filter;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import ir.sanatisharif.android.konkur96.api.Models.ContentModel;
import ir.sanatisharif.android.konkur96.api.Models.PaginationModel;
import ir.sanatisharif.android.konkur96.api.Models.ProductModel;
import ir.sanatisharif.android.konkur96.api.Models.SetModel;

public class FilterResult implements Parcelable {
    
    public static final Creator<FilterResult> CREATOR = new Creator<FilterResult>() {
        @Override
        public FilterResult createFromParcel(Parcel source) {
            return new FilterResult(source);
        }
        
        @Override
        public FilterResult[] newArray(int size) {
            return new FilterResult[size];
        }
    };
    @SerializedName("video")
    @Expose
    private PaginationModel<ContentModel> video;
    @SerializedName("pamphlet")
    @Expose
    private PaginationModel<ContentModel> pamphlet;
    @SerializedName("article")
    @Expose
    private PaginationModel<ContentModel> article;
    @SerializedName("set")
    @Expose
    private PaginationModel<SetModel> set;
    @SerializedName("product")
    @Expose
    private PaginationModel<ProductModel> product;
    
    protected FilterResult(Parcel in) {
        this.video = in.readParcelable(PaginationModel.class.getClassLoader());
        this.pamphlet = in.readParcelable(PaginationModel.class.getClassLoader());
        this.article = in.readParcelable(PaginationModel.class.getClassLoader());
        this.set = in.readParcelable(PaginationModel.class.getClassLoader());
        this.product = in.readParcelable(PaginationModel.class.getClassLoader());
    }
    
    @Override
    public int describeContents() {
        return 0;
    }
    
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.video, flags);
        dest.writeParcelable(this.pamphlet, flags);
        dest.writeParcelable(this.article, flags);
        dest.writeParcelable(this.set, flags);
        dest.writeParcelable(this.product, flags);
    }
    
    public PaginationModel<ContentModel> getVideo() {
        return video;
    }
    
    public void setVideo(PaginationModel<ContentModel> media) {
        this.video = media;
    }
    
    public PaginationModel<ContentModel> getPamphlet() {
        return pamphlet;
    }
    
    public void setPamphlet(PaginationModel<ContentModel> pamphlet) {
        this.pamphlet = pamphlet;
    }
    
    public PaginationModel<ContentModel> getArticle() {
        return article;
    }
    
    public void setArticle(PaginationModel<ContentModel> article) {
        this.article = article;
    }
    
    public PaginationModel<SetModel> getSet() {
        return set;
    }
    
    public void setSet(PaginationModel<SetModel> set) {
        this.set = set;
    }
    
    public PaginationModel<ProductModel> getProduct() {
        return product;
    }
    
    public void setProduct(PaginationModel<ProductModel> product) {
        this.product = product;
    }
}
