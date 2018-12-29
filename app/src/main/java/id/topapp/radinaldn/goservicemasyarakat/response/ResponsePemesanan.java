package id.topapp.radinaldn.goservicemasyarakat.response;

/**
 * Created by radinaldn on 26/12/18.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import id.topapp.radinaldn.goservicemasyarakat.model.Pemesanan;

public class ResponsePemesanan {

    @SerializedName("code")
    @Expose
    private int code;
    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("data")
    @Expose
    private List<Pemesanan> pemesanan = null;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List <Pemesanan> getPemesanan() {
        return pemesanan;
    }

    public void setPemesanan(List <Pemesanan> pemesanan) {
        this.pemesanan = pemesanan;
    }

}