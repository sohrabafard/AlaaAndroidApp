package ir.sanatisharif.android.konkur96.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

import ir.sanatisharif.android.konkur96.api.Models.ProductPhotoModel;

public class FullScreenModel implements Parcelable {
    
    public static final Creator<FullScreenModel>     CREATOR = new Creator<FullScreenModel>() {
        @Override
        public FullScreenModel createFromParcel(Parcel in) {
            return new FullScreenModel(in);
        }
        
        @Override
        public FullScreenModel[] newArray(int size) {
            return new FullScreenModel[size];
        }
    };
    private             int                          position;
    //private ArrayList<ImageGalleryModel> imageGalleryModels;
    private             ArrayList<ProductPhotoModel> productPhotoModel;
    
    public FullScreenModel(int position, ArrayList<ProductPhotoModel> productPhotoModel) {
        this.position = position;
        this.productPhotoModel = productPhotoModel;
    }
    
    public FullScreenModel(Parcel in) {
        position = in.readInt();
        productPhotoModel = in.createTypedArrayList(ProductPhotoModel.CREATOR);
    }
    
    public int getPosition() {
        return position;
    }
    
    public void setPosition(int position) {
        this.position = position;
    }
    
    public ArrayList<ProductPhotoModel> getImageGalleryModels() {
        return productPhotoModel;
    }
    
    public void setImageGalleryModels(ArrayList<ProductPhotoModel> productPhotoModel) {
        this.productPhotoModel = productPhotoModel;
    }
    
    @Override
    public int describeContents() {
        return 0;
    }
    
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(position);
        dest.writeTypedList(productPhotoModel);
    }
}
