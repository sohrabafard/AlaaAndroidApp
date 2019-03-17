package ir.sanatisharif.android.konkur96.api.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class PaymentVerificationResponse implements Parcelable {

    @SerializedName("Status")
    private int Status;

    @SerializedName("RefID")
    private int RefID;


    protected PaymentVerificationResponse(Parcel in) {
        Status = in.readInt();
        RefID = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(Status);
        dest.writeInt(RefID);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PaymentVerificationResponse> CREATOR = new Creator<PaymentVerificationResponse>() {
        @Override
        public PaymentVerificationResponse createFromParcel(Parcel in) {
            return new PaymentVerificationResponse(in);
        }

        @Override
        public PaymentVerificationResponse[] newArray(int size) {
            return new PaymentVerificationResponse[size];
        }
    };

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public int getRefID() {
        return RefID;
    }

    public void setRefID(int refID) {
        RefID = refID;
    }
}
