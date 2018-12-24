package id.topapp.radinaldn.goservicemasyarakat.util;

/**
 * Created by radinaldn on 22/12/18.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


import java.util.HashMap;

/**
 * Created by radinaldn on 03/07/18.
 */

public class SessionManager {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Context _context;

    public static final String IS_LOGGED_IN = "isLoggedIn";
    public static final String NIK = "nik";
    public static final String PASSWORD = "password";
    public static final String NAMA = "nama";
    public static final String TEMPAT_LAHIR = "tempat_lahir";
    public static final String TANGGAL_LAHIR = "tanggal_lahir";
    public static final String JK = "jk";
    public static final String ALAMAT = "alamat";
    public static final String AGAMA = "agama";
    public static final String STATUS_KAWIN = "status_kawin";
    public static final String PEKERJAAN = "pekerjaan";
    public static final String KEWARGANEGARAAN = "kewarganegaraan";
    public static final String FOTO = "foto";
    public static final String NO_HP = "no_hp";
    public static final String SALDO = "saldo";

    public static final String HAS_LAST_LOCATION = "hasLastLocation";
    public static final String LATITUDE = "latitude";
    public static final String LONGITUDE = "longitude";
    public static final String LAST_LOCATED = "lastLocated";

    public static final String SHARE_LOC_IS_ON = "shareLocIsOn";

    public Context get_context(){
        return _context;
    }

    // constructor
    public SessionManager(Context context){
        this._context = context;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = sharedPreferences.edit();
    }

    public void createLoginSession(String nik, String nama, String tempat_lahir,
                                   String tanggal_lahir, String jk, String alamat,
                                   String agama, String status_kawin, String pekerjaan,
                                   String kewarganegaraan, String foto, String no_hp, String saldo){

        editor.putBoolean(IS_LOGGED_IN, true);
        editor.putString(NIK, nik);
        editor.putString(NAMA, nama);
        editor.putString(TEMPAT_LAHIR, tempat_lahir);
        editor.putString(TANGGAL_LAHIR, tanggal_lahir);
        editor.putString(JK, jk);
        editor.putString(ALAMAT, alamat);
        editor.putString(AGAMA, agama);
        editor.putString(STATUS_KAWIN, status_kawin);
        editor.putString(PEKERJAAN, pekerjaan);
        editor.putString(KEWARGANEGARAAN, kewarganegaraan);
        editor.putString(FOTO, foto);
        editor.putString(NO_HP, no_hp);
        editor.putString(SALDO, saldo);
        editor.commit();

    }

    public void createMyLocationSession(String latitude, String longitude, String last_located){
        editor.putBoolean(HAS_LAST_LOCATION, true);
        editor.putString(LATITUDE, latitude);
        editor.putString(LONGITUDE, longitude);
        editor.putString(LAST_LOCATED, last_located);
        editor.commit();

    }

    public void setStatusSwitchShareLoc(boolean status){
        editor.putBoolean(SHARE_LOC_IS_ON, status);
        editor.commit();
    }

    public boolean getStatusSwitchShareLoc(){
        return sharedPreferences.getBoolean(SHARE_LOC_IS_ON, false);
    }

    public HashMap<String, String> getMasyarakatDetail(){
        HashMap<String,String> masyarakat = new HashMap<>();
        masyarakat.put(NIK, sharedPreferences.getString(NIK,null));
        masyarakat.put(PASSWORD, sharedPreferences.getString(PASSWORD, null));
        masyarakat.put(NAMA, sharedPreferences.getString(NAMA,null));
        masyarakat.put(TEMPAT_LAHIR, sharedPreferences.getString(TEMPAT_LAHIR,null));
        masyarakat.put(TANGGAL_LAHIR, sharedPreferences.getString(TANGGAL_LAHIR,null));
        masyarakat.put(NAMA, sharedPreferences.getString(NAMA,null));
        masyarakat.put(JK, sharedPreferences.getString(JK,null));
        masyarakat.put(ALAMAT, sharedPreferences.getString(ALAMAT,null));
        masyarakat.put(AGAMA, sharedPreferences.getString(AGAMA,null));
        masyarakat.put(STATUS_KAWIN, sharedPreferences.getString(STATUS_KAWIN,null));
        masyarakat.put(PEKERJAAN, sharedPreferences.getString(PEKERJAAN,null));
        masyarakat.put(KEWARGANEGARAAN, sharedPreferences.getString(KEWARGANEGARAAN,null));
        masyarakat.put(FOTO, sharedPreferences.getString(FOTO,null));
        masyarakat.put(NO_HP, sharedPreferences.getString(NO_HP,null));
        masyarakat.put(SALDO, sharedPreferences.getString(SALDO,null));

        return masyarakat;
    }

    public HashMap<String, String> getMyLocationDetail(){
        HashMap<String, String> location = new HashMap<>();
        location.put(LATITUDE, sharedPreferences.getString(LATITUDE, null));
        location.put(LONGITUDE, sharedPreferences.getString(LONGITUDE, null));
        location.put(LAST_LOCATED, sharedPreferences.getString(LAST_LOCATED, null));
        return location;
    }

    public void logoutMasyarakat(){
        editor.clear();
        editor.commit();
    }

    public boolean isLoggedIn(){
        return sharedPreferences.getBoolean(IS_LOGGED_IN, false);
    }

    public boolean hasLastLocation(){
        return sharedPreferences.getBoolean(HAS_LAST_LOCATION, false);
    }
}
