package ir.sanatisharif.android.konkur96.model.filter;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FilterResult implements Parcelable {

    public final static Creator<FilterResult> CREATOR = new Creator<FilterResult>() {

        @Override
        public FilterResult createFromParcel(Parcel in) {
            return new FilterResult(in);
        }

        @Override
        public FilterResult[] newArray(int size) {
            return (new FilterResult[size]);
        }

    };
    @SerializedName("video")
    @Expose
    private             VideoRoot             media;

    @SerializedName("pamphlet")
    @Expose
    private             PamphletRoot          pamphlet;

    @SerializedName("article")
    @Expose
    private             ArticleRoot           article;

    @SerializedName("set")
    @Expose
    private             SetFilterRoot         set;

    @SerializedName("product")
    @Expose
    private             SetFilterProductRoot  product;


    protected FilterResult(Parcel in) {
        this.media = ((VideoRoot) in.readValue((VideoRoot.class.getClassLoader())));
        this.pamphlet = ((PamphletRoot) in.readValue((Object.class.getClassLoader())));
        this.article = ((ArticleRoot) in.readValue((Object.class.getClassLoader())));
        this.set = ((SetFilterRoot) in.readValue((Object.class.getClassLoader())));
        this.product = ((SetFilterProductRoot) in.readValue((Object.class.getClassLoader())));
    }


    public VideoRoot getVideo() {
        return media;
    }

    public void setVideo(VideoRoot video) {
        this.media = video;
    }

    public PamphletRoot getPamphlet() {
        return pamphlet;
    }

    public void setPamphlet(PamphletRoot pamphlet) {
        this.pamphlet = pamphlet;
    }

    public ArticleRoot getArticle() {
        return article;
    }

    public void setArticle(ArticleRoot article) {
        this.article = article;
    }

    public SetFilterRoot getSet() {
        return set;
    }

    public void setSet(SetFilterRoot set) {
        this.set = set;
    }

    public SetFilterProductRoot getProduct() {
        return product;
    }

    public void setProduct(SetFilterProductRoot product) {
        this.product = product;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(media);
        dest.writeValue(pamphlet);
        dest.writeValue(article);
        dest.writeValue(set);
        dest.writeValue(product);
    }

    @Override
    public int describeContents() {
        return 0;
    }

}
