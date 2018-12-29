package id.topapp.radinaldn.goservicemasyarakat.rest;

import id.topapp.radinaldn.goservicemasyarakat.response.ResponseFindTeknisi;
import id.topapp.radinaldn.goservicemasyarakat.response.ResponseLogin;
import id.topapp.radinaldn.goservicemasyarakat.response.ResponsePemesanan;
import id.topapp.radinaldn.goservicemasyarakat.response.ResponseRegister;
import id.topapp.radinaldn.goservicemasyarakat.response.ResponseViewPemesanan;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

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

    @FormUrlEncoded
    @POST("pemesanan/add")
    Call<ResponsePemesanan> pemesananAdd(
            @Field("nik") String nik,
            @Field("id_teknisi") String id_teknisi,
            @Field("alamat") String alamat,
            @Field("lat") String lat,
            @Field("lng") String lng,
            @Field("jenis_servis") String jenis_servis,
            @Field("foto_sebelum") String foto_sebelum,
            @Field("keluhan") String keluhan,
            @Field("kategori_bayar") String kategori_bayar
    );

    @GET("teknisi/find-nearby")
    Call<ResponseFindTeknisi> teknisiFindNearby(
            @Query("myLat") double myLat,
            @Query("myLng") double myLng,
            @Query("jarak") double jarak
    );

    @GET("pemesanan/view-all-by-nik-and-proses")
    Call<ResponseViewPemesanan> pemesananViewAllByNikAndProses(
            @Query("nik") String nik,
            @Query("proses") String proses
    );

}
