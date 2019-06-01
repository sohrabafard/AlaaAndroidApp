package ir.sanatisharif.android.konkur96.Models;

public class PaymentVerificationRequest {
    
    final String MerchantID;
    final int    Amount;
    final String Authority;
    
    public PaymentVerificationRequest(String MerchantID, int Amount, String Authority) {
        
        this.MerchantID = MerchantID;
        this.Amount = Amount;
        this.Authority = Authority;
        
    }
}
