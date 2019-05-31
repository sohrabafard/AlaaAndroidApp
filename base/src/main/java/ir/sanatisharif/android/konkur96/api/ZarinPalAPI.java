package ir.sanatisharif.android.konkur96.api;

import io.reactivex.Observable;
import ir.sanatisharif.android.konkur96.api.Models.GETPriceModel;
import ir.sanatisharif.android.konkur96.api.Models.PaymentRequest;
import ir.sanatisharif.android.konkur96.api.Models.PaymentResponse;
import ir.sanatisharif.android.konkur96.api.Models.PaymentVerificationRequest;
import ir.sanatisharif.android.konkur96.api.Models.PaymentVerificationResponse;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ZarinPalAPI {

    String BASE_URL = "https://www.zarinpal.com/pg/rest/WebGate/";

    @Headers({"ContentModel-Type: application/json"})
    @POST("PaymentRequest.json")
    Observable<PaymentResponse> paymentRequest(@Body PaymentRequest body);


    @Headers({"ContentModel-Type: application/json"})
    @POST("PaymentVerification.json")
    Observable<PaymentVerificationResponse> paymentVerification(@Body PaymentVerificationRequest body);

}
