package id.topapp.radinaldn.goservicemasyarakat.model;

/**
 * Created by radinaldn on 26/12/18.
 */


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pemesanan {

    @SerializedName("id_pemesanan")
    @Expose
    private String idPemesanan;
    @SerializedName("nik")
    @Expose
    private String nik;
    @SerializedName("id_teknisi")
    @Expose
    private String idTeknisi;
    @SerializedName("alamat")
    @Expose
    private String alamat;
    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("keluhan")
    @Expose
    private String keluhan;
    @SerializedName("lng")
    @Expose
    private String lng;
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
    private String biaya;
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
    private int rating;

    @SerializedName("komentar_rating")
    @Expose
    private String komentarRating;

    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("password")
    @Expose
    private String password;
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
    @SerializedName("no_hp")
    @Expose
    private String noHp;
    @SerializedName("siu")
    @Expose
    private String siu;
    @SerializedName("foto")
    @Expose
    private String foto;
    @SerializedName("status_akun")
    @Expose
    private String statusAkun;
    @SerializedName("saldo")
    @Expose
    private String saldo;


    public String getIdPemesanan() {
        return idPemesanan;
    }

    public void setIdPemesanan(String idPemesanan) {
        this.idPemesanan = idPemesanan;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public String getIdTeknisi() {
        return idTeknisi;
    }

    public void setIdTeknisi(String idTeknisi) {
        this.idTeknisi = idTeknisi;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getKeluhan() {
        return keluhan;
    }

    public void setKeluhan(String keluhan) {
        this.keluhan = keluhan;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
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

    public String getBiaya() {
        return biaya;
    }

    public void setBiaya(String biaya) {
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

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getKomentarRating() {
        return komentarRating;
    }

    public void setKomentarRating(String komentarRating) {
        this.komentarRating = komentarRating;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getNoHp() {
        return noHp;
    }

    public void setNoHp(String noHp) {
        this.noHp = noHp;
    }

    public String getSiu() {
        return siu;
    }

    public void setSiu(String siu) {
        this.siu = siu;
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

    public String getSaldo() {
        return saldo;
    }

    public void setSaldo(String saldo) {
        this.saldo = saldo;
    }

}