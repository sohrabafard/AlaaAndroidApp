
package ir.sanatisharif.android.konkur96.model.main_page.lastVersion;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Url implements Parcelable
{

    @SerializedName("play_store")
    @Expose
    private String playStore;
    @SerializedName("bazaar")
    @Expose
    private String bazaar;
    @SerializedName("direct")
    @Expose
    private String direct;
    public final static Creator<Url> CREATOR = new Creator<Url>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Url createFromParcel(Parcel in) {
            return new Url(in);
        }

        public Url[] newArray(int size) {
            return (new Url[size]);
        }

    }
    ;

    protected Url(Parcel in) {
        this.playStore = ((String) in.readValue((String.class.getClassLoader())));
        this.bazaar = ((String) in.readValue((String.class.getClassLoader())));
        this.direct = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Url() {
    }

    public String getPlayStore() {
        return playStore;
    }

    public void setPlayStore(String playStore) {
        this.playStore = playStore;
    }

    public String getBazaar() {
        return bazaar;
    }

    public void setBazaar(String bazaar) {
        this.bazaar = bazaar;
    }

    public String getDirect() {
        return direct;
    }

    public void setDirect(String direct) {
        this.direct = direct;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(playStore);
        dest.writeValue(bazaar);
        dest.writeValue(direct);
    }

    public int describeContents() {
        return  0;
    }

}
