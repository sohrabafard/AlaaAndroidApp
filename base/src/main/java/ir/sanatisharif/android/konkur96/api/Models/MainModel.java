package ir.sanatisharif.android.konkur96.api.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MainModel implements Parcelable {
    
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
    
    @SerializedName("mainBanner")
    @Expose
    private ArrayList<MainBannerModel> mainBanner;
    
    @SerializedName("block")
    @Expose
    private PaginationModel<BlockDataModel> block;
    
    protected MainModel(Parcel in) {
        mainBanner = in.createTypedArrayList(MainBannerModel.CREATOR);
        block = in.readParcelable(PaginationModel.class.getClassLoader());
    }
    
    public ArrayList<MainBannerModel> getMainBanner() {
        return mainBanner;
    }
    
    public void setMainBanner(ArrayList<MainBannerModel> mainBanner) {
        this.mainBanner = mainBanner;
    }
    
    public PaginationModel<BlockDataModel> getBlock() {
        return block;
    }
    
    public void setBlock(PaginationModel<BlockDataModel> block) {
        this.block = block;
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
}
