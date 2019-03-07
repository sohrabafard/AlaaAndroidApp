package ir.sanatisharif.android.konkur96.api.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MainModel implements Parcelable {

    @SerializedName("mainBanner")
    private ArrayList<MainBannerModel> mainBanner;

    @SerializedName("block")
    private BlockModel block;

    protected MainModel(Parcel in) {
        mainBanner = in.createTypedArrayList(MainBannerModel.CREATOR);
        block = in.readParcelable(BlockModel.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(mainBanner);
        dest.writeParcelable(block, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MainModel> CREATOR = new Creator<MainModel>() {
        @Override
        public MainModel createFromParcel(Parcel in) {
            return new MainModel(in);
        }

        @Override
        public MainModel[] newArray(int size) {
            return new MainModel[size];
        }
    };

    public ArrayList<MainBannerModel> getMainBanner() {
        return mainBanner;
    }

    public void setMainBanner(ArrayList<MainBannerModel> mainBanner) {
        this.mainBanner = mainBanner;
    }

    public BlockModel getBlock() {
        return block;
    }

    public void setBlock(BlockModel block) {
        this.block = block;
    }
}
