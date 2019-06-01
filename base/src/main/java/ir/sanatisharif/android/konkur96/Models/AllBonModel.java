package ir.sanatisharif.android.konkur96.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class AllBonModel implements Parcelable {
    
    public static final Creator<AllBonModel> CREATOR = new Creator<AllBonModel>() {
        @Override
        public AllBonModel createFromParcel(Parcel in) {
            return new AllBonModel(in);
        }
        
        @Override
        public AllBonModel[] newArray(int size) {
            return new AllBonModel[size];
        }
    };
    @SerializedName("id")
    private             int                  id;
    @SerializedName("bon_id")
    private             int                  bon_id;
    @SerializedName("user_id")
    private             int                  user_id;
    @SerializedName("totalNumber")
    private             int                  totalNumber;
    @SerializedName("usedNumber")
    private             int                  usedNumber;
    @SerializedName("userbonstatus_id")
    private             int                  userbonstatus_id;
    @SerializedName("validSince")
    private             int                  validSince;
    @SerializedName("validUntil")
    private             int                  validUntil;
    @SerializedName("orderproduct_id")
    private             int                  orderproduct_id;
    @SerializedName("created_at")
    private             String               created_at;
    @SerializedName("updated_at")
    private             String               updated_at;
    @SerializedName("deleted_at")
    private             String               deleted_at;
    @SerializedName("pivot")
    private             PivotMode            pivot;
    
    protected AllBonModel(Parcel in) {
        id = in.readInt();
        bon_id = in.readInt();
        user_id = in.readInt();
        totalNumber = in.readInt();
        usedNumber = in.readInt();
        userbonstatus_id = in.readInt();
        validSince = in.readInt();
        validUntil = in.readInt();
        orderproduct_id = in.readInt();
        created_at = in.readString();
        updated_at = in.readString();
        deleted_at = in.readString();
        pivot = in.readParcelable(PivotMode.class.getClassLoader());
    }
    
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(bon_id);
        dest.writeInt(user_id);
        dest.writeInt(totalNumber);
        dest.writeInt(usedNumber);
        dest.writeInt(userbonstatus_id);
        dest.writeInt(validSince);
        dest.writeInt(validUntil);
        dest.writeInt(orderproduct_id);
        dest.writeString(created_at);
        dest.writeString(updated_at);
        dest.writeString(deleted_at);
        dest.writeParcelable(pivot, flags);
    }
    
    @Override
    public int describeContents() {
        return 0;
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getBon_id() {
        return bon_id;
    }
    
    public void setBon_id(int bon_id) {
        this.bon_id = bon_id;
    }
    
    public int getUser_id() {
        return user_id;
    }
    
    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
    
    public int getTotalNumber() {
        return totalNumber;
    }
    
    public void setTotalNumber(int totalNumber) {
        this.totalNumber = totalNumber;
    }
    
    public int getUsedNumber() {
        return usedNumber;
    }
    
    public void setUsedNumber(int usedNumber) {
        this.usedNumber = usedNumber;
    }
    
    public int getUserbonstatus_id() {
        return userbonstatus_id;
    }
    
    public void setUserbonstatus_id(int userbonstatus_id) {
        this.userbonstatus_id = userbonstatus_id;
    }
    
    public int getValidSince() {
        return validSince;
    }
    
    public void setValidSince(int validSince) {
        this.validSince = validSince;
    }
    
    public int getValidUntil() {
        return validUntil;
    }
    
    public void setValidUntil(int validUntil) {
        this.validUntil = validUntil;
    }
    
    public int getOrderproduct_id() {
        return orderproduct_id;
    }
    
    public void setOrderproduct_id(int orderproduct_id) {
        this.orderproduct_id = orderproduct_id;
    }
    
    public String getCreated_at() {
        return created_at;
    }
    
    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
    
    public String getUpdated_at() {
        return updated_at;
    }
    
    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
    
    public String getDeleted_at() {
        return deleted_at;
    }
    
    public void setDeleted_at(String deleted_at) {
        this.deleted_at = deleted_at;
    }
    
    public PivotMode getPivot() {
        return pivot;
    }
    
    public void setPivot(PivotMode pivot) {
        this.pivot = pivot;
    }
}
