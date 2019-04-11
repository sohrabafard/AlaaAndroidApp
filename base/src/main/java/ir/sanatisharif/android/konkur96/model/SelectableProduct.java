package ir.sanatisharif.android.konkur96.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

import ir.sanatisharif.android.konkur96.api.Models.ProductModel;

public class SelectableProduct implements Parcelable {


    private ProductModel model;
    private boolean checked;
    private SelectableProduct parent;
    private ArrayList<SelectableProduct> childs;

    public SelectableProduct(ProductModel model, boolean checked, SelectableProduct parent, ArrayList<SelectableProduct> childs){

        this.model = model;
        this.checked = checked;
        this.parent = parent;
        this.childs =childs;
    }

    protected SelectableProduct(Parcel in) {
        model = in.readParcelable(ProductModel.class.getClassLoader());
        checked = in.readByte() != 0;
        parent = in.readParcelable(SelectableProduct.class.getClassLoader());
        childs = in.createTypedArrayList(SelectableProduct.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(model, flags);
        dest.writeByte((byte) (checked ? 1 : 0));
        dest.writeParcelable(parent, flags);
        dest.writeTypedList(childs);
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

    public SelectableProduct getParent() {
        return parent;
    }

    public void setParent(SelectableProduct parent) {
        this.parent = parent;
    }

    public ArrayList<SelectableProduct> getChilds() {
        return childs;
    }

    public void setChilds(ArrayList<SelectableProduct> childs) {
        this.childs = childs;
    }
}
