package ir.sanatisharif.android.konkur96.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import ir.sanatisharif.android.konkur96.api.Models.ContentModel;
import ir.sanatisharif.android.konkur96.api.Models.ProductModel;

public class ContentCredit implements Parcelable {


    public final static Parcelable.Creator<ContentCredit> CREATOR = new Creator<ContentCredit>() {


        @SuppressWarnings({
                "unchecked"
        })
        public ContentCredit createFromParcel(Parcel in) {
            return new ContentCredit(in);
        }

        public ContentCredit[] newArray(int size) {
            return (new ContentCredit[size]);
        }

    };
    @SerializedName("message")
    @Expose
    private             String                            message;
    @SerializedName("content")
    @Expose
    private             ContentModel                      content;
    @SerializedName("product")
    @Expose
    private             List<ProductModel>                product = null;

    protected ContentCredit(Parcel in) {
        this.message = ((String) in.readValue((String.class.getClassLoader())));
        this.content = ((ContentModel) in.readValue((ContentModel.class.getClassLoader())));
        in.readList(this.product, (ProductModel.class.getClassLoader()));
    }

    public ContentCredit() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ContentModel getContent() {
        return content;
    }

    public void setContent(ContentModel content) {
        this.content = content;
    }

    public List<ProductModel> getProduct() {
        return product;
    }

    public void setProduct(List<ProductModel> product) {
        this.product = product;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(message);
        dest.writeValue(content);
        dest.writeList(product);
    }

    public int describeContents() {
        return 0;
    }

}
