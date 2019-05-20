package ir.sanatisharif.android.konkur96.api;

import retrofit2.Call;
import retrofit2.http.HEAD;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Url;

public interface HeadRequestInterface {

    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @HEAD
    Call<Void> get(@Url String url, @Header("Authorization") String token);
}
