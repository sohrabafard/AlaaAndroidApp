package ir.sanatisharif.android.konkur96.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class PaymentResponse implements Parcelable {
    
    public static final Creator<PaymentResponse> CREATOR = new Creator<PaymentResponse>() {
        @Override
        public PaymentResponse createFromParcel(Parcel in) {
            return new PaymentResponse(in);
        }
        
        @Override
        public PaymentResponse[] newArray(int size) {
            return new PaymentResponse[size];
        }
    };
    @SerializedName("Status")
    private             int                      Status;
    @SerializedName("Authority")
    private             String                   Authority;
    
    protected PaymentResponse(Parcel in) {
        Status = in.readInt();
        Authority = in.readString();
    }
    
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(Status);
        dest.writeString(Authority);
    }
    
    @Override
    public int describeContents() {
        return 0;
    }
    
    public int getStatus() {
        return Status;
    }
    
    public void setStatus(int status) {
        Status = status;
    }
    
    public String getAuthority() {
        return Authority;
    }
    
    public void setAuthority(String authority) {
        Authority = authority;
    }
}
