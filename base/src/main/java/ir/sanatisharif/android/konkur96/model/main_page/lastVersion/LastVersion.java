
package ir.sanatisharif.android.konkur96.model.main_page.lastVersion;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LastVersion implements Parcelable
{

    @SerializedName("android")
    @Expose
    private Android android;

    public final static Creator<LastVersion> CREATOR = new Creator<LastVersion>() {


        @SuppressWarnings({
            "unchecked"
        })
        public LastVersion createFromParcel(Parcel in) {
            return new LastVersion(in);
        }

        public LastVersion[] newArray(int size) {
            return (new LastVersion[size]);
        }

    }
    ;

    protected LastVersion(Parcel in) {
        this.android = ((Android) in.readValue((Android.class.getClassLoader())));

    }

    public LastVersion() {
    }

    public Android getAndroid() {
        return android;
    }

    public void setAndroid(Android android) {
        this.android = android;
    }


    public int describeContents() {
        return  0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }

}
