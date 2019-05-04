
package ir.sanatisharif.android.konkur96.model.main_page;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NextApiUrl implements Parcelable
{

    @SerializedName("v1")
    @Expose
    private String v1;
    public final static Creator<NextApiUrl> CREATOR = new Creator<NextApiUrl>() {


        @SuppressWarnings({
            "unchecked"
        })
        public NextApiUrl createFromParcel(Parcel in) {
            return new NextApiUrl(in);
        }

        public NextApiUrl[] newArray(int size) {
            return (new NextApiUrl[size]);
        }

    }
    ;

    protected NextApiUrl(Parcel in) {
        this.v1 = ((String) in.readValue((String.class.getClassLoader())));
    }

    public NextApiUrl() {
    }

    public String getV1() {
        return v1;
    }

    public void setV1(String v1) {
        this.v1 = v1;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(v1);
    }

    public int describeContents() {
        return  0;
    }

}
