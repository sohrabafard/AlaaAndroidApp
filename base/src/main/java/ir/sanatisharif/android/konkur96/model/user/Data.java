package ir.sanatisharif.android.konkur96.model.user;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data implements Parcelable {

    public final static Creator<Data> CREATOR = new Creator<Data>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Data createFromParcel(Parcel in) {
            return new Data(in);
        }

        public Data[] newArray(int size) {
            return (new Data[size]);
        }

    };
    @SerializedName("user")
    @Expose
    private             User          user;
    @SerializedName("access_token")
    @Expose
    private             String        accessToken;
    @SerializedName("token_type")
    @Expose
    private             String        tokenType;
    @SerializedName("token_expires_at")
    @Expose
    private             String        tokenExpiresAt;

    protected Data(Parcel in) {
        this.user = ((User) in.readValue((User.class.getClassLoader())));
        this.accessToken = ((String) in.readValue((String.class.getClassLoader())));
        this.tokenType = ((String) in.readValue((String.class.getClassLoader())));
        this.tokenExpiresAt = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Data() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Data withUser(User user) {
        this.user = user;
        return this;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Data withAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public Data withTokenType(String tokenType) {
        this.tokenType = tokenType;
        return this;
    }

    public String getTokenExpiresAt() {
        return tokenExpiresAt;
    }

    public void setTokenExpiresAt(String tokenExpiresAt) {
        this.tokenExpiresAt = tokenExpiresAt;
    }

    public Data withTokenExpiresAt(String tokenExpiresAt) {
        this.tokenExpiresAt = tokenExpiresAt;
        return this;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(user);
        dest.writeValue(accessToken);
        dest.writeValue(tokenType);
        dest.writeValue(tokenExpiresAt);
    }

    public int describeContents() {
        return 0;
    }

}
