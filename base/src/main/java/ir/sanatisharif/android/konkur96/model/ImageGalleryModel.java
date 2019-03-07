package ir.sanatisharif.android.konkur96.model;

import android.os.Parcel;
import android.os.Parcelable;

public class ImageGalleryModel implements Parcelable {

    private String imagePath;
    private String imageTitle;
    private String imageDesc;

    public ImageGalleryModel(Parcel in) {
        imagePath = in.readString();
        imageTitle = in.readString();
        imageDesc = in.readString();
    }

    public static final Creator<ImageGalleryModel> CREATOR = new Creator<ImageGalleryModel>() {
        @Override
        public ImageGalleryModel createFromParcel(Parcel in) {
            return new ImageGalleryModel(in);
        }

        @Override
        public ImageGalleryModel[] newArray(int size) {
            return new ImageGalleryModel[size];
        }
    };

    public ImageGalleryModel() {

    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getImageTitle() {
        return imageTitle;
    }

    public void setImageTitle(String imageTitle) {
        this.imageTitle = imageTitle;
    }

    public String getImageDesc() {
        return imageDesc;
    }

    public void setImageDesc(String imageDesc) {
        this.imageDesc = imageDesc;
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(imagePath);
        dest.writeString(imageTitle);
        dest.writeString(imageDesc);
    }


}
