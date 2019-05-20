package ir.sanatisharif.android.konkur96.api;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.HEAD;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Url;

public interface HeadRequestInterface {
    @Headers({"Content-Type: application/json", "Accept: application/json", "X-Requested-With: XMLHttpRequest"})
    @HEAD
    Observable<Response> getLocation(@Url String url, @Header("Authorization") String token);
}
