
package ir.sanatisharif.android.konkur96.model.main_page;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pivot implements Parcelable
{

    @SerializedName("block_id")
    @Expose
    private Integer blockId;
    @SerializedName("blockable_id")
    @Expose
    private Integer blockableId;
    @SerializedName("blockable_type")
    @Expose
    private String blockableType;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    public final static Creator<Pivot> CREATOR = new Creator<Pivot>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Pivot createFromParcel(Parcel in) {
            return new Pivot(in);
        }

        public Pivot[] newArray(int size) {
            return (new Pivot[size]);
        }

    }
    ;

    protected Pivot(Parcel in) {
        this.blockId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.blockableId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.blockableType = ((String) in.readValue((String.class.getClassLoader())));
        this.createdAt = ((String) in.readValue((String.class.getClassLoader())));
        this.updatedAt = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Pivot() {
    }

    public Integer getBlockId() {
        return blockId;
    }

    public void setBlockId(Integer blockId) {
        this.blockId = blockId;
    }

    public Integer getBlockableId() {
        return blockableId;
    }

    public void setBlockableId(Integer blockableId) {
        this.blockableId = blockableId;
    }

    public String getBlockableType() {
        return blockableType;
    }

    public void setBlockableType(String blockableType) {
        this.blockableType = blockableType;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(blockId);
        dest.writeValue(blockableId);
        dest.writeValue(blockableType);
        dest.writeValue(createdAt);
        dest.writeValue(updatedAt);
    }

    public int describeContents() {
        return  0;
    }

}
