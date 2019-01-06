package id.topapp.radinaldn.goservicemasyarakat.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by radinaldn on 06/01/19.
 */

public class PemesananUnrated {

    @SerializedName("id_pemesanan")
    @Expose
    private Integer idPemesanan;
    @SerializedName("nik")
    @Expose
    private String nik;
    @SerializedName("id_teknisi")
    @Expose
    private Integer idTeknisi;
    @SerializedName("alamat")
    @Expose
    private String alamat;
    @SerializedName("lat")
    @Expose
    private Double lat;
    @SerializedName("keluhan")
    @Expose
    private String keluhan;
    @SerializedName("lng")
    @Expose
    private Double lng;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("jenis_servis")
    @Expose
    private String jenisServis;
    @SerializedName("biaya")
    @Expose
    private Integer biaya;
    @SerializedName("proses")
    @Expose
    private String proses;
    @SerializedName("kategori_bayar")
    @Expose
    private String kategoriBayar;
    @SerializedName("foto_sebelum")
    @Expose
    private String fotoSebelum;
    @SerializedName("foto_sesudah")
    @Expose
    private String fotoSesudah;
    @SerializedName("ket")
    @Expose
    private String ket;
    @SerializedName("rating")
    @Expose
    private String rating;
    @SerializedName("komentar_rating")
    @Expose
    private String komentarRating;

    public Integer getIdPemesanan() {
        return idPemesanan;
    }

    public void setIdPemesanan(Integer idPemesanan) {
        this.idPemesanan = idPemesanan;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public Integer getIdTeknisi() {
        return idTeknisi;
    }

    public void setIdTeknisi(Integer idTeknisi) {
        this.idTeknisi = idTeknisi;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public String getKeluhan() {
        return keluhan;
    }

    public void setKeluhan(String keluhan) {
        this.keluhan = keluhan;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getJenisServis() {
        return jenisServis;
    }

    public void setJenisServis(String jenisServis) {
        this.jenisServis = jenisServis;
    }

    public Integer getBiaya() {
        return biaya;
    }

    public void setBiaya(Integer biaya) {
        this.biaya = biaya;
    }

    public String getProses() {
        return proses;
    }

    public void setProses(String proses) {
        this.proses = proses;
    }

    public String getKategoriBayar() {
        return kategoriBayar;
    }

    public void setKategoriBayar(String kategoriBayar) {
        this.kategoriBayar = kategoriBayar;
    }

    public String getFotoSebelum() {
        return fotoSebelum;
    }

    public void setFotoSebelum(String fotoSebelum) {
        this.fotoSebelum = fotoSebelum;
    }

    public String getFotoSesudah() {
        return fotoSesudah;
    }

    public void setFotoSesudah(String fotoSesudah) {
        this.fotoSesudah = fotoSesudah;
    }

    public String getKet() {
        return ket;
    }

    public void setKet(String ket) {
        this.ket = ket;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getKomentarRating() {
        return komentarRating;
    }

    public void setKomentarRating(String komentarRating) {
        this.komentarRating = komentarRating;
    }

}