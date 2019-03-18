package ir.sanatisharif.android.konkur96.api.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class WalletModel implements Parcelable {

    @SerializedName("id")
    private int orderproducts;

    @SerializedName("name")
    private String name;

    @SerializedName("hint")
    private String hint;

    @SerializedName("balance")
    private String blance;

    protected WalletModel(Parcel in) {
        orderproducts = in.readInt();
        name = in.readString();
        hint = in.readString();
        blance = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(orderproducts);
        dest.writeString(name);
        dest.writeString(hint);
        dest.writeString(blance);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<WalletModel> CREATOR = new Creator<WalletModel>() {
        @Override
        public WalletModel createFromParcel(Parcel in) {
            return new WalletModel(in);
        }

        @Override
        public WalletModel[] newArray(int size) {
            return new WalletModel[size];
        }
    };

    public int getOrderproducts() {
        return orderproducts;
    }

    public void setOrderproducts(int orderproducts) {
        this.orderproducts = orderproducts;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public String getBlance() {
        return blance;
    }

    public void setBlance(String blance) {
        this.blance = blance;
    }
}
