package ir.sanatisharif.android.konkur96.api.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ErrorBase  {

    @SerializedName("error")
    private ErrorBaseModel error;


    public ErrorBaseModel getError() {
        return error;
    }

    public void setError(ErrorBaseModel error) {
        this.error = error;
    }
}
