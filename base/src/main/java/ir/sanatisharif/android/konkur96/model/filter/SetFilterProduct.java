package ir.sanatisharif.android.konkur96.model.filter;

import android.os.Parcel;

import ir.sanatisharif.android.konkur96.api.Models.ProductModel;
import ir.sanatisharif.android.konkur96.app.AppConstants;


public class SetFilterProduct extends ProductModel implements FilterBaseModel {

    protected SetFilterProduct(Parcel in) {
        super(in);
    }

    @Override
    public int getViewType() {
        return AppConstants.FILTER_PRODUCT;
    }
}
