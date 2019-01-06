package id.topapp.radinaldn.goservicemasyarakat.model;

/**
 * Created by radinaldn on 28/12/18.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Teknisi {

    @SerializedName("id_teknisi")
    @Expose
    private String idTeknisi;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("nama_toko")
    @Expose
    private String namaToko;
    @SerializedName("nama_pemilik")
    @Expose
    private String namaPemilik;
    @SerializedName("nik_pemilik")
    @Expose
    private String nikPemilik;
    @SerializedName("layanan")
    @Expose
    private String layanan;
    @SerializedName("alamat")
    @Expose
    private String alamat;
    @SerializedName("no_hp")
    @Expose
    private String noHp;
    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("lng")
    @Expose
    private String lng;
    @SerializedName("foto")
    @Expose
    private String foto;
    @SerializedName("status_akun")
    @Expose
    private String statusAkun;
    @SerializedName("jarak")
    @Expose
    private String jarak;
    @SerializedName("total_rating")
    @Expose
    private int totalRating;
    @SerializedName("jumlah_pemesanan")
    @Expose
    private int jumlahPemesanan;

    public String getIdTeknisi() {
        return idTeknisi;
    }

    public void setIdTeknisi(String idTeknisi) {
        this.idTeknisi = idTeknisi;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNamaToko() {
        return namaToko;
    }

    public void setNamaToko(String namaToko) {
        this.namaToko = namaToko;
    }

    public String getNamaPemilik() {
        return namaPemilik;
    }

    public void setNamaPemilik(String namaPemilik) {
        this.namaPemilik = namaPemilik;
    }

    public String getNikPemilik() {
        return nikPemilik;
    }

    public void setNikPemilik(String nikPemilik) {
        this.nikPemilik = nikPemilik;
    }

    public String getLayanan() {
        return layanan;
    }

    public void setLayanan(String layanan) {
        this.layanan = layanan;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getNoHp() {
        return noHp;
    }

    public void setNoHp(String noHp) {
        this.noHp = noHp;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getStatusAkun() {
        return statusAkun;
    }

    public void setStatusAkun(String statusAkun) {
        this.statusAkun = statusAkun;
    }

    public String getJarak() {
        return jarak;
    }

    public void setJarak(String jarak) {
        this.jarak = jarak;
    }

    public int getTotalRating() {
        return totalRating;
    }

    public void setTotalRating(int totalRating) {
        this.totalRating = totalRating;
    }

    public int getJumlahPemesanan() {
        return jumlahPemesanan;
    }

    public void setJumlahPemesanan(int jumlahPemesanan) {
        this.jumlahPemesanan = jumlahPemesanan;
    }
}
