package ir.sanatisharif.android.konkur96.model;

import android.os.Parcel;
import android.os.Parcelable;

import ir.sanatisharif.android.konkur96.api.Models.ProductModel;

public class SelectableProduct implements Parcelable {


    private ProductModel model;
    private boolean checked;

    public SelectableProduct(ProductModel model, boolean checked) {
        this.model = model;
        this.checked = checked;

    }

    protected SelectableProduct(Parcel in) {
        model = in.readParcelable(ProductModel.class.getClassLoader());
        checked = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(model, flags);
        dest.writeByte((byte) (checked ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SelectableProduct> CREATOR = new Creator<SelectableProduct>() {
        @Override
        public SelectableProduct createFromParcel(Parcel in) {
            return new SelectableProduct(in);
        }

        @Override
        public SelectableProduct[] newArray(int size) {
            return new SelectableProduct[size];
        }
    };

    public ProductModel getModel() {
        return model;
    }

    public void setModel(ProductModel model) {
        this.model = model;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
