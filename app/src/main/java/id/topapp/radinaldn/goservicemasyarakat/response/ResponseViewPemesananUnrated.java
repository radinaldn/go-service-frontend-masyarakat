package id.topapp.radinaldn.goservicemasyarakat.response;

/**
 * Created by radinaldn on 06/01/19.
 */


import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import id.topapp.radinaldn.goservicemasyarakat.model.Pemesanan;
import id.topapp.radinaldn.goservicemasyarakat.model.PemesananUnrated;

public class ResponseViewPemesananUnrated {

    @SerializedName("master")
    @Expose
    private List<Pemesanan> master = null;

    public List<Pemesanan> getPemesanansUnrated() {
        return master;
    }

    public void setMaster(List<Pemesanan> master) {
        this.master = master;
    }

}