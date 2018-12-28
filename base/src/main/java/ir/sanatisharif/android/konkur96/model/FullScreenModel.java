package ir.sanatisharif.android.konkur96.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class FullScreenModel implements Parcelable {

    private int position;
    private ArrayList<ImageGalleryModel> imageGalleryModels;


    public FullScreenModel(int position, ArrayList<ImageGalleryModel> imageGalleryModels) {
        this.position = position;
        this.imageGalleryModels = imageGalleryModels;
    }

    public FullScreenModel(Parcel in) {
        position = in.readInt();
        imageGalleryModels = in.createTypedArrayList(ImageGalleryModel.CREATOR);
    }

    public static final Creator<FullScreenModel> CREATOR = new Creator<FullScreenModel>() {
        @Override
        public FullScreenModel createFromParcel(Parcel in) {
            return new FullScreenModel(in);
        }

        @Override
        public FullScreenModel[] newArray(int size) {
            return new FullScreenModel[size];
        }
    };

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public ArrayList<ImageGalleryModel> getImageGalleryModels() {
        return imageGalleryModels;
    }

    public void setImageGalleryModels(ArrayList<ImageGalleryModel> imageGalleryModels) {
        this.imageGalleryModels = imageGalleryModels;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(position);
        dest.writeTypedList(imageGalleryModels);
    }
}
