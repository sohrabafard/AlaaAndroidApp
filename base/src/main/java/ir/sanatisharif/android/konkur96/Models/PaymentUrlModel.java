package ir.sanatisharif.android.konkur96.Models;

import com.google.gson.annotations.SerializedName;

public class PaymentUrlModel extends ErrorBase {
    
    @SerializedName("url")
    private String url;
    
    public String getUrl() {
        return url;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }
}
