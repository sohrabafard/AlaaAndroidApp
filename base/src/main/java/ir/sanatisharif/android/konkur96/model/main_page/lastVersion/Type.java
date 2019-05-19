package ir.sanatisharif.android.konkur96.model.main_page.lastVersion;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Type implements Parcelable {

    public final static Creator<Type> CREATOR = new Creator<Type>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Type createFromParcel(Parcel in) {
            return new Type(in);
        }

        public Type[] newArray(int size) {
            return (new Type[size]);
        }

    };
    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("hint")
    @Expose
    private String hint;

    protected Type(Parcel in) {
        this.code = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.hint = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Type() {
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(code);
        dest.writeValue(hint);
    }

    public int describeContents() {
        return 0;
    }

}
