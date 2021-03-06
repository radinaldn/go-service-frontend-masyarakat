package id.topapp.radinaldn.goservicemasyarakat.response;

/**
 * Created by radinaldn on 06/01/19.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import id.topapp.radinaldn.goservicemasyarakat.model.PemesananUnrated;

public class ResponseAddPemesananRating {

    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private PemesananUnrated data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public PemesananUnrated getData() {
        return data;
    }

    public void setData(PemesananUnrated data) {
        this.data = data;
    }

}