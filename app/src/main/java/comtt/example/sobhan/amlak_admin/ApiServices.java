package comtt.example.sobhan.amlak_admin;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiServices {

    @FormUrlEncoded
    @POST("/test_connect.php")
    Call<ResponseBody> insertData(@Field("username") String username, @Field("password") String password , @Field("senfi_code") String family);

    @FormUrlEncoded
    @POST("/Login.php")
    Call<user_ret> Logindata(@Field("username") String username, @Field("password") String password);

    @FormUrlEncoded
    @POST("/get_agahi.php")
    Call<agahi_list> Get_agahi(@Field("name") String shoper_name);

    @FormUrlEncoded
    @POST("/get_agahi_rent.php")
    Call<agahi_list_rent> Get_agahi_rent(@Field("name") String shoper_name);

    @FormUrlEncoded
    @POST("/sabt_agahi.php")
    Call<test_get_sale> Sabt_agahi(@Field("name") String name, @Field("info") String info , @Field("cost") String cost,@Field("shoper") String shoper, @Field("phone") String phone , @Field("metr") int metr,@Field("sanad") boolean sanad,@Field("vila") boolean vila,@Field("area") String area,@Field("address") String address,@Field("image") String image,@Field("title") String title);

    @FormUrlEncoded
    @POST("/sabt_agahi_rent.php")
    Call<ResponseBody> Sabt_agahi_rent(@Field("name") String name, @Field("info") String info , @Field("cost") String cost,@Field("rahn") String rahn,@Field("shoper") String shoper, @Field("phone") String phone , @Field("metr") int metr,@Field("area") String area, @Field("address") String address,@Field("image") String image,@Field("title") String title);

    @FormUrlEncoded
    @POST("/delete_agahi_forosh.php")
    Call<ResponseBody> delete_agahi_forosh(@Field("id") int id);

    @FormUrlEncoded
    @POST("/delete_agahi_rent.php")
    Call<ResponseBody> delete_agahi_rent(@Field("id") int id);

    @FormUrlEncoded
    @POST("/alter_forosh.php")
    Call<ResponseBody> alter_forosh(@Field("name") String name, @Field("info") String info , @Field("cost") String cost, @Field("phone") String phone , @Field("metr") int metr, @Field("id") int id,@Field("address") String address, @Field("area") String area,@Field("type") boolean type, @Field("sanad") boolean sanad, @Field("image_change") String image_change, @Field("check_change") boolean check_change);

    @FormUrlEncoded
    @POST("/alter_rent.php")
    Call<ResponseBody> alter_rent(@Field("name") String name, @Field("info") String info , @Field("cost") String cost, @Field("rahn") String rahn, @Field("phone") String phone , @Field("metr") int metr, @Field("id") int id, @Field("area") String area, @Field("address") String address, @Field("image_change") String image_change, @Field("check_change") boolean check_change);

    @FormUrlEncoded
    @POST("/change_password.php")
    Call<ResponseBody> change_password(@Field("id") int id,@Field("password") String password);

    @FormUrlEncoded
    @POST("/image_profile_edit.php")
    @Headers({
            "Accept: application/json",
            "User-Agent: Your-App-Name",
            "Cache-Control: max-age=640000"
    })
    Call<ResponseBody> change_profile_image(@Field("id") int id,@Field("image") String image);

}
