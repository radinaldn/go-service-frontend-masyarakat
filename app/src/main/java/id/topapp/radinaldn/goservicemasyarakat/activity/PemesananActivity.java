package id.topapp.radinaldn.goservicemasyarakat.activity;

import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import org.ankit.gpslibrary.MyTracker;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import id.topapp.radinaldn.goservicemasyarakat.R;
import id.topapp.radinaldn.goservicemasyarakat.config.ServerConfig;
import id.topapp.radinaldn.goservicemasyarakat.model.Teknisi;
import id.topapp.radinaldn.goservicemasyarakat.response.ResponseFindTeknisi;
import id.topapp.radinaldn.goservicemasyarakat.response.ResponsePemesanan;
import id.topapp.radinaldn.goservicemasyarakat.rest.ApiClient;
import id.topapp.radinaldn.goservicemasyarakat.rest.ApiInterface;
import id.topapp.radinaldn.goservicemasyarakat.rest.MyGoogleApiClient;
import id.topapp.radinaldn.goservicemasyarakat.rest.MyGoogleApiInterface;
import id.topapp.radinaldn.goservicemasyarakat.rest.ResponseReverseGeocoding;
import id.topapp.radinaldn.goservicemasyarakat.util.ConnectionDetector;
import id.topapp.radinaldn.goservicemasyarakat.util.HttpFileUpload;
import id.topapp.radinaldn.goservicemasyarakat.util.SessionManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PemesananActivity extends AppCompatActivity implements OnMapReadyCallback {


    TextView tvKategori, tvLatLng, tvDeskripsiTeknisi;
    EditText etKeluhan, etAlamat;
    RadioGroup rgKategoriBayar;
    Button btKirim;
    ApiInterface apiService;
    MyGoogleApiInterface googleApiService;

    CircleImageView civFotoTeknisi;

    private GoogleMap mMap;
    private static final int REQUEST_CODE_PERMISSION = 2;
    String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;
    ImageView ivHideMap, btKamera, ivFoto, ivGPS;

    Boolean isHide = false;

    File destFile;
    File file;
    private SimpleDateFormat dateFormatter;
    private Uri imageCaptureUri;
    Bitmap bmp;
    public static final String DATE_FORMAT = "yyyyMMdd_HHmmss";
    public static final String IMAGE_DIRECTORY = "Go-Service";
    private Boolean upflag = false;
    String fname;
    String finalPhotoName, hasilGeocode, ID_TEKNISI;
    private static final String TAG = PemesananActivity.class.getSimpleName();
    ConnectionDetector cd;
    SessionManager sessionManager;
    private ProgressDialog pDialog;
    MyTracker tracker;

    String [] kategori_bayars = {"Saldo", "Cash"};
    private static final String TAG_JENIS_SERVIS = "jenis_servis";
    private static String JENIS_SERVIS;

    private Double myLat, myLng;
    Marker myMarker, teknisiMarker;
    ArrayList <Teknisi> teknisis;
    View parentLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pemesanan);

        // obtain user location
        new GetMyLocation().execute();

        //get Extra from previous Activity
        JENIS_SERVIS = getIntent().getStringExtra(TAG_JENIS_SERVIS);

        parentLayout = findViewById(android.R.id.content);
        cd = new ConnectionDetector(this);
        tvKategori = findViewById(R.id.tvKategori);
        tvDeskripsiTeknisi = findViewById(R.id.tvDeskripsiTeknisi);
        etKeluhan = findViewById(R.id.etKeluhan);
        etAlamat = findViewById(R.id.etAlamat);
        rgKategoriBayar = findViewById(R.id.rgKategoriBayar);
        btKamera = findViewById(R.id.btKamera);
        btKirim = findViewById(R.id.btKirim);
        ivFoto = findViewById(R.id.ivFoto);
        tvLatLng = findViewById(R.id.tvLatLng);
        ivGPS = findViewById(R.id.ivGPS);

        civFotoTeknisi = findViewById(R.id.civFotoTeknisi);
        ivHideMap = findViewById(R.id.ivHideMap);
        apiService = ApiClient.getClient().create(ApiInterface.class);
        googleApiService = MyGoogleApiClient.getGoogleApiClient().create(MyGoogleApiInterface.class);
        sessionManager = new SessionManager(this);
        tracker = new MyTracker(this);

        tvKategori.setText(JENIS_SERVIS);

        dateFormatter = new SimpleDateFormat(
                DATE_FORMAT, Locale.US);

        file = new File(Environment.getExternalStorageDirectory()
                + "/" + IMAGE_DIRECTORY);
        if (!file.exists()) {
            file.mkdirs();
        }


        // Maps
        final SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        ivHideMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isHide){
                    ViewGroup.LayoutParams params = mapFragment.getView().getLayoutParams();
                    params.height = 100;
                    mapFragment.getView().setLayoutParams(params);
                    ivHideMap.setImageResource(R.drawable.ic_keyboard_arrow_down);
                    isHide = true;
                } else {
                    ViewGroup.LayoutParams params = mapFragment.getView().getLayoutParams();
                    params.height = 300;
                    mapFragment.getView().setLayoutParams(params);
                    ivHideMap.setImageResource(R.drawable.ic_keyboard_arrow_up);
                    isHide = false;
                }
            }
        });

        btKamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finalPhotoName = "Pre_"+sessionManager.getMasyarakatDetail().get(SessionManager.NIK)+"_" +dateFormatter.format(new Date()) + ".jpg";
                clickCamera();
            }
        });

        btKirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kirimPesanan();
            }
        });

        ivGPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new GetMyLocation().execute();

                if (myLat!=null){
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(myLat,myLng)));
                    moveMarker(myLat, myLng);
                }
            }
        });

    }

    private void kirimPesanan() {
        String nik = sessionManager.getMasyarakatDetail().get(SessionManager.NIK);
        String id_teknisi = ID_TEKNISI;
        String alamat = etAlamat.getText().toString();
        String lat = myLat.toString();
        String lng = myLng.toString();
        String jenis_servis = JENIS_SERVIS;
        String foto_sebelum = finalPhotoName;
        String keluhan = etKeluhan.getText().toString();
        String kategori_bayar = kategori_bayars[rgKategoriBayar.indexOfChild(rgKategoriBayar.findViewById(rgKategoriBayar.getCheckedRadioButtonId()))];

        Toast.makeText(getApplicationContext(),
                "nik : "+nik+
                        "\nid_teknisi : "+id_teknisi+
                        "\nialamat : "+alamat+
                        "\nlat: "+lat+
                        "\nlng : "+lng+
                        "\njenis_servis : "+jenis_servis+
                        "\nfoto_sebelum : "+foto_sebelum+
                        "\nkeluhan : "+keluhan+
                        "\nkategori_bayar : "+kategori_bayar,
                Toast.LENGTH_LONG).show();

         //save foto into local storage

        if (destFile!=null){
            saveFile(destFile);
        } else {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.belum_mengambil_gambar), Toast.LENGTH_SHORT).show();
        }

        apiService.pemesananAdd(nik, id_teknisi, alamat, lat, lng, jenis_servis, foto_sebelum, keluhan, kategori_bayar).enqueue(new Callback<ResponsePemesanan>() {
            @Override
            public void onResponse(Call<ResponsePemesanan> call, Response<ResponsePemesanan> response) {
                if (response.isSuccessful()){
                    if (response.body().getCode() == 200){
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.kirim_pesanan_berhasil)+"\nID Pemesanan : "+response.body().getPemesanan().get(0).getIdPemesanan(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.kirim_pesanan_gagal), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponsePemesanan> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void clickCamera() {
        destFile = new File(file, finalPhotoName);
        imageCaptureUri = Uri.fromFile(destFile);

        Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, imageCaptureUri);
        startActivityForResult(intentCamera, 101);
    }

    private void goToMainActivity(){
        Intent intent = new Intent(PemesananActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        goToMainActivity();
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setMinZoomPreference(8);


        googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(0.507068,101.447779)));

        if (myLat!=null){
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(myLat,myLng)));

            // add marker

            moveMarker(myLat, myLng);

            // add teknisi's marker
            apiService.teknisiFindNearby(myLat, myLng, 50).enqueue(new Callback<ResponseFindTeknisi>() {
                @Override
                public void onResponse(Call<ResponseFindTeknisi> call, Response<ResponseFindTeknisi> response) {
                    if (response.isSuccessful()){
                        if (response.body().getTeknisi().size() > 0){
                            Toast.makeText(getApplicationContext(), "Ada "+response.body().getTeknisi().size()+" didekatmu.", Toast.LENGTH_SHORT).show();

                            // add teknisi marker
                            teknisis = new ArrayList<>();
                            teknisis.addAll(response.body().getTeknisi());

                            System.out.println("ada "+teknisis.size()+" teknisi");

                            if (teknisiMarker!= null) teknisiMarker.remove();

                            for (int i=0; i<teknisis.size(); i++){
                                Log.d(TAG, "onResponse: perulangan teknisi ke-"+i);

                                Double latitude = Double.parseDouble(teknisis.get(i).getLat());
                                Double longitude= Double.parseDouble(teknisis.get(i).getLng());
                                String id_teknisi = teknisis.get(i).getIdTeknisi();
                                final String nama_toko = teknisis.get(i).getNamaToko();
                                final String alamat = teknisis.get(i).getAlamat();
                                final String no_hp = teknisis.get(i).getNoHp();
                                final String foto = teknisis.get(i).getFoto();



                                Log.d(TAG, "addTeknisiMarker: menambahkan marker "+nama_toko);

                                teknisiMarker = mMap.addMarker(new
                                        MarkerOptions().position(new LatLng(latitude, longitude)).title(Integer.toString(i)).snippet(nama_toko+"\n"+alamat+"\n"+no_hp).icon(bitmapDescriptorFromVector(PemesananActivity.this, R.drawable.teknisi_marker)));


                                googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                                    @Override
                                    public boolean onMarkerClick(final Marker marker) {

                                        if (!marker.getTitle().equalsIgnoreCase("Posisi saya")){
                                            AlertDialog.Builder builder = new AlertDialog.Builder(PemesananActivity.this);
                                            builder.setTitle(teknisis.get(Integer.valueOf(marker.getTitle())).getNamaToko());
                                            builder.setMessage(teknisis.get(Integer.valueOf(marker.getTitle())).getAlamat()+"\n"+teknisis.get(Integer.valueOf(marker.getTitle())).getNoHp()+"\nAnda ingin memilih teknisi ini?");
                                            builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    Picasso.with(PemesananActivity.this).load(ServerConfig.TEKNISI_PROFIL_PATH+teknisis.get(Integer.valueOf(marker.getTitle())).getFoto()).centerCrop().fit().into(civFotoTeknisi);
                                                    Log.d(TAG, "load foto : "+ServerConfig.TEKNISI_PROFIL_PATH+foto);
                                                    tvDeskripsiTeknisi.setText(teknisis.get(Integer.valueOf(marker.getTitle())).getNamaToko()+"\n"+teknisis.get(Integer.valueOf(marker.getTitle())).getAlamat()+"\n"+teknisis.get(Integer.valueOf(marker.getTitle())).getNoHp());
                                                    ID_TEKNISI = teknisis.get(Integer.valueOf(marker.getTitle())).getIdTeknisi();

                                                }
                                            });

                                            builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.dismiss();
                                                }
                                            });

                                            builder.create().show();
                                        } else {
                                            marker.showInfoWindow();
                                        }


                                        return true;
                                    }


                                });
                            }


                        } else {
                            Toast.makeText(getApplicationContext(), "Maaf, tidak ada teknisi didekat anda.\nTutup dan bukka lagi halaman ini.", Toast.LENGTH_SHORT).show();
                        }
                    }

                }

                @Override
                public void onFailure(Call<ResponseFindTeknisi> call, Throwable t) {

                }
            });
        }


    }


    private void moveMarker(double latitude, double longitude) {

        if (myMarker!=null) myMarker.remove();


        myMarker = mMap.addMarker(new
                MarkerOptions().position(new LatLng(latitude, longitude)).title("Posisi saya").snippet("Lat&Lng : "+myLat+","+myLng).icon(bitmapDescriptorFromVector(this, R.drawable.user_marker)));


        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(latitude, longitude)));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(15));

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            switch (requestCode){
                case 101:
                    if (resultCode == Activity.RESULT_OK){
                        upflag = true;
                        Log.d(TAG +". Pick camera image ", "Selected image uri path : "+imageCaptureUri);

                        bmp = decodeFile(destFile, finalPhotoName);
                        ivFoto.setVisibility(View.VISIBLE);
                        ivFoto.setImageBitmap(bmp);
                        btKamera.setVisibility(View.GONE);
                    }
                    break;
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private Bitmap decodeFile(File f, String final_photo_name) {
        Bitmap b = null;

        //Decode image size
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;

        FileInputStream fis = null;
        try {
            fis = new FileInputStream(f);
            BitmapFactory.decodeStream(fis, null, o);
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        int IMAGE_MAX_SIZE = 1024;
        int scale = 1;
        if (o.outHeight > IMAGE_MAX_SIZE || o.outWidth > IMAGE_MAX_SIZE) {
            scale = (int) Math.pow(2, (int) Math.ceil(Math.log(IMAGE_MAX_SIZE /
                    (double) Math.max(o.outHeight, o.outWidth)) / Math.log(0.5)));
        }

        //Decode with inSampleSize
        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        try {
            fis = new FileInputStream(f);
            b = BitmapFactory.decodeStream(fis, null, o2);
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "Width :" + b.getWidth() + " Height :" + b.getHeight());

        fname = final_photo_name;
        destFile = new File(file, fname);

        return b;



    }

    // for saving image to local storage, method ini master untuk upload foto
    private void saveFile(File destination){
        if(destination.exists()) destination.delete();

        try{
            FileOutputStream out = new FileOutputStream(destFile);
            bmp.compress(Bitmap.CompressFormat.JPEG, 50, out);
            out.flush();
            out.close();

            if (cd.isConnectingToInternet()){
                new UploadFoto().execute();
            } else {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.no_internet_message), Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    class UploadFoto extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(PemesananActivity.this);
            pDialog.setCancelable(false);
            pDialog.setMessage("Mohon menunggu, sedang mengupload gambar..");
            pDialog.show();

        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                // set your file path here
                FileInputStream fstrm = new FileInputStream(destFile);
                // set your server page url (and the title/description)
                HttpFileUpload hfu = new HttpFileUpload(ServerConfig.UPLOAD_FOTO_ENDPOINT, "ftitle", "fdescription", finalPhotoName);
                upflag = hfu.Send_Now(fstrm);

            } catch (FileNotFoundException e){
                // file not found
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            if (upflag){
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.upload_gambar_berhasil), Toast.LENGTH_LONG).show();
                // selesaikan activity
                finish();
                restartActivity();
            } else {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.upload_gambar_gagal), Toast.LENGTH_LONG).show();
            }
        }

        private void restartActivity(){
            Intent i =getBaseContext().getPackageManager()
                    .getLaunchIntentForPackage(getBaseContext().getPackageName());

            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
        }
    }

    class GetMyLocation extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {

            pDialog = new ProgressDialog(PemesananActivity.this);
            pDialog.setCancelable(false);
            pDialog.setMessage("Mohon menunggu, sedang mengambil lokasi..");
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            if (tracker==null){
                tracker = new MyTracker(PemesananActivity.this);
            }

            return null;
        }

        @Override
        protected void onPostExecute(String file_url) {
            if (pDialog.isShowing()) {
                pDialog.dismiss();
            }
            getLocation();
            Toast.makeText(getApplicationContext(), "Selesai mendapatkan lokasi ", Toast.LENGTH_LONG).show();
            // selesaikan activity

        }
    }

    void getLocation(){


        myLat = tracker.getLatitude();
        myLng = tracker.getLongitude();

        System.out.println(tracker.getLatitude());

        System.out.println(tracker.getLongitude());
        System.out.println(tracker.getLocation());

        tvLatLng.setText("Lat : "+String.valueOf(tracker.getLatitude()) +"\nLng : "+String.valueOf(tracker.getLongitude()));

        String latlng = myLat+","+myLng;
        String lokasiGeocode = getKotaByGeocodeJson(latlng, ServerConfig.GOOGLE_API_KEY);

        etAlamat.setText(lokasiGeocode);

    }

    private String getKotaByGeocodeJson(String latlng, String googleApiKey) {
        googleApiService.geocodeJson(latlng, googleApiKey).enqueue(new Callback<ResponseReverseGeocoding>() {
            @Override
            public void onResponse(Call<ResponseReverseGeocoding> call, Response<ResponseReverseGeocoding> response) {
                if (response.isSuccessful()){
                    if (response.body().getStatus().equalsIgnoreCase("OK")){

                        hasilGeocode = response.body().getResults().get(0).getFormattedAddress();
                    }
                } else {
                    Log.e(TAG, "onResponse Failed: "+response.toString());
                }
            }

            @Override
            public void onFailure(Call<ResponseReverseGeocoding> call, Throwable t) {
                t.getLocalizedMessage();
            }
        });

        return hasilGeocode;
    }

    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }
}
