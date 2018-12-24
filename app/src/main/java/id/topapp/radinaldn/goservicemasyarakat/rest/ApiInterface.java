package id.topapp.radinaldn.goservicemasyarakat.rest;

import id.topapp.radinaldn.goservicemasyarakat.response.ResponseLogin;
import id.topapp.radinaldn.goservicemasyarakat.response.ResponseRegister;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by radinaldn on 22/12/18.
 */

public interface ApiInterface {

    @FormUrlEncoded
    @POST("masyarakat/login")
    Call<ResponseLogin> login(
            @Field("nik") String nik,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("masyarakat/register")
    Call<ResponseRegister> register(
            @Field("nik") String nik,
            @Field("password_1") String password_1,
            @Field("password_2") String password_2,
            @Field("nama") String nama,
            @Field("tempat_lahir") String tempat_lahir,
            @Field("tanggal_lahir") String tanggal_lahir,
            @Field("jk") String jk,
            @Field("alamat") String alamat,
            @Field("agama") String agama,
            @Field("status_kawin") String status_kawin,
            @Field("pekerjaan") String pekerjaan,
            @Field("kewarganegaraan") String kewarganegaraan,
            @Field("foto") String foto,
            @Field("no_hp") String no_hp
    );

}
