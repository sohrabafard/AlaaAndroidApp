
package ir.sanatisharif.android.konkur96.model.main_page;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MainPagesInfo implements Parcelable
{

    @SerializedName("mainBanner")
    @Expose
    private List<MainBanner> mainBanner = null;
    @SerializedName("block")
    @Expose
    private Block block;
    public final static Creator<MainPagesInfo> CREATOR = new Creator<MainPagesInfo>() {


        @SuppressWarnings({
            "unchecked"
        })
        public MainPagesInfo createFromParcel(Parcel in) {
            return new MainPagesInfo(in);
        }

        public MainPagesInfo[] newArray(int size) {
            return (new MainPagesInfo[size]);
        }

    }
    ;

    protected MainPagesInfo(Parcel in) {
        in.readList(this.mainBanner, (MainBanner.class.getClassLoader()));
        this.block = ((Block) in.readValue((Block.class.getClassLoader())));
    }

    public MainPagesInfo() {
    }

    public List<MainBanner> getMainBanner() {
        return mainBanner;
    }

    public void setMainBanner(List<MainBanner> mainBanner) {
        this.mainBanner = mainBanner;
    }

    public Block getBlock() {
        return block;
    }

    public void setBlock(Block block) {
        this.block = block;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(mainBanner);
        dest.writeValue(block);
    }

    public int describeContents() {
        return  0;
    }

}
