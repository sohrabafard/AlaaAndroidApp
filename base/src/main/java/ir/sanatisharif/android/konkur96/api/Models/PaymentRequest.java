package ir.sanatisharif.android.konkur96.api.Models;

public class PaymentRequest {

    final String MerchantID;
    final int    Amount;
    final String CallbackURL;
    final String Description;

    public PaymentRequest(String MerchantID, int Amount, String CallbackURL, String Description) {

        this.MerchantID = MerchantID;
        this.Amount = Amount;
        this.CallbackURL = CallbackURL;
        this.Description = Description;
    }
}
