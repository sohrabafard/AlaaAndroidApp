
package ir.sanatisharif.android.konkur96.model.filter;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import ir.sanatisharif.android.konkur96.model.main_page.Product;
import ir.sanatisharif.android.konkur96.model.main_page.Set;

public class Result implements Parcelable
{

    @SerializedName("video")
    @Expose
    private VideoRoot media;
    @SerializedName("pamphlet")
    @Expose
    private PamphletRoot pamphlet;
    @SerializedName("article")
    @Expose
    private ArticleRoot article;
    @SerializedName("set")
    @Expose
    private SetFilterRoot set;
    @SerializedName("product")
    @Expose
    private Product product;

    public final static Creator<Result> CREATOR = new Creator<Result>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Result createFromParcel(Parcel in) {
            return new Result(in);
        }

        public Result[] newArray(int size) {
            return (new Result[size]);
        }

    }
    ;

    protected Result(Parcel in) {
        this.media = ((VideoRoot) in.readValue((VideoRoot.class.getClassLoader())));
        this.pamphlet = ((PamphletRoot) in.readValue((Object.class.getClassLoader())));
        this.article = ((ArticleRoot) in.readValue((Object.class.getClassLoader())));
        this.set = ((SetFilterRoot) in.readValue((Object.class.getClassLoader())));
        this.product = ((Product) in.readValue((Object.class.getClassLoader())));
    }

    public Result() {
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

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(media);
        dest.writeValue(pamphlet);
        //dest.writeValue(article);
        dest.writeValue(set);
        dest.writeValue(product);
    }

    public int describeContents() {
        return  0;
    }

}
