package ir.sanatisharif.android.konkur96.api;

import java.util.List;

import ir.sanatisharif.android.konkur96.model.DataCourse;
import ir.sanatisharif.android.konkur96.model.filter.Filter;
import ir.sanatisharif.android.konkur96.model.main_page.MainPagesInfo;
import ir.sanatisharif.android.konkur96.model.user.User;
import ir.sanatisharif.android.konkur96.model.user.UserInfo;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by Mohamad on 12/26/2018.
 */

public interface ApiRetrofit {

  /*
   EX: www.app.net/api/searchtypes/862189/filters?Type=6&SearchText=School

   @GET("/api/searchtypes/{Id}/filters")
    Call<FilterResponse> getFilterList(
        @Path("Id") long customerId,
        @Query("Type") String responseType,
        @Query("SearchText") String searchText);*/


    @GET(" ")
    Call<MainPagesInfo> getMainPage();

    @GET("c/{id}")
    Call<DataCourse> getDetailsCourseByID(@Path("id") String id);

    @GET
    Call<DataCourse> getDetailsCourseByUrl(@Url String url);

    @POST("api/login")
    Call<UserInfo> getLoginUserInfo(@Body User user);

    /**
     * contentOnly
     * c?set=191&contentOnly=1
     */
    @GET("c")
    Call<Filter> getContentOnly(
            @Query("set") String id,
            @Query("contentOnly") int contentOnly);

    @GET("c")
    Call<Filter> getFilterBySearch(
            @Query("search") String search);

    @GET("c")
    Call<Filter> getFilterTags(@Query("tags[]") List<String> tags);

    @GET
    Call<Filter> getFilterTagsByUrl(@Url String url);

}
