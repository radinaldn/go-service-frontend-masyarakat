package id.topapp.radinaldn.goservicemasyarakat.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.messaging.RemoteMessage;
import com.pusher.pushnotifications.PushNotificationReceivedListener;
import com.pusher.pushnotifications.PushNotifications;
import com.stepstone.apprating.AppRatingDialog;
import com.stepstone.apprating.listener.RatingDialogListener;

import java.util.Arrays;

import id.topapp.radinaldn.goservicemasyarakat.R;
import id.topapp.radinaldn.goservicemasyarakat.config.ServerConfig;
import id.topapp.radinaldn.goservicemasyarakat.response.ResponseAddPemesananRating;
import id.topapp.radinaldn.goservicemasyarakat.response.ResponseViewPemesananUnrated;
import id.topapp.radinaldn.goservicemasyarakat.rest.ApiClient;
import id.topapp.radinaldn.goservicemasyarakat.rest.ApiInterface;
import id.topapp.radinaldn.goservicemasyarakat.util.ConnectionDetector;
import id.topapp.radinaldn.goservicemasyarakat.util.SessionManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements RatingDialogListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private TextView mTextMessage;
    private static final String TAG_JENIS_SERVIS = "jenis_servis";

    // init TAG servis
    private static final String TAG_TV = "tv";
    private static final String TAG_KULKAS = "kulkas";
    private static final String TAG_AC = "ac";
    private static final String TAG_MESIN_CUCI = "mesin cuci";
    private ConnectionDetector cd;
    private SessionManager sessionManager;
    ApiInterface apiService;
    private static String ID_PEMESANAN;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_orderanku:


                    Intent intent = new Intent(MainActivity.this, OrderankuActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.navigation_dashboard:

                    return true;
                case R.id.navigation_profil:

                    Intent intent3 = new Intent(MainActivity.this, ProfilActivity.class);
                    intent3.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent3);
                    finish();
                    overridePendingTransition(0, 0);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cd = new ConnectionDetector(this);
        apiService = ApiClient.getClient().create(ApiInterface.class);
        sessionManager = new SessionManager(this);

        // init for push notification w/ pusher
        PushNotifications.start(getApplicationContext(), ServerConfig.PUSHNOTIF_INSTANCE_ID);
        PushNotifications.subscribe(sessionManager.getMasyarakatDetail().get(SessionManager.NIK));

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_dashboard);

        if (cd.isConnectingToInternet()) {
            getPemesanansUnrated();
        }
    }

    private void getPemesanansUnrated() {
        apiService.pemesananViewAllUnrated(sessionManager.getMasyarakatDetail().get(SessionManager.NIK)).enqueue(new Callback<ResponseViewPemesananUnrated>() {
            @Override
            public void onResponse(Call<ResponseViewPemesananUnrated> call, Response<ResponseViewPemesananUnrated> response) {
                if (response.isSuccessful()){
                    if (response.body().getPemesanansUnrated().size()>0){
                        ID_PEMESANAN = response.body().getPemesanansUnrated().get(0).getIdPemesanan();
                        showRatingDialog(
                                response.body().getPemesanansUnrated().get(0).getJenisServis(),
                                response.body().getPemesanansUnrated().get(0).getNamaToko()
                        );
                    } else {
                        Log.d(TAG, "onResponse: Tidak ada data PemesananUnrated");
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "response pemesanan unrated : "+response.toString(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseViewPemesananUnrated> call, Throwable t) {

                t.printStackTrace();
            }
        });
    }

    public void actionTelevisi(View view) {
        Intent i = new Intent(MainActivity.this, PemesananActivity.class);
        i.putExtra(TAG_JENIS_SERVIS, TAG_TV);
        startActivity(i);
    }

    public void actionKulkas(View view) {
        Intent i = new Intent(MainActivity.this, PemesananActivity.class);
        i.putExtra(TAG_JENIS_SERVIS, TAG_KULKAS);
        startActivity(i);
    }

    public void actionAC(View view) {
        Intent i = new Intent(MainActivity.this, PemesananActivity.class);
        i.putExtra(TAG_JENIS_SERVIS, TAG_AC);
        startActivity(i);
    }

    public void actionMesinCuci(View view) {
        Intent i = new Intent(MainActivity.this, PemesananActivity.class);
        i.putExtra(TAG_JENIS_SERVIS, TAG_MESIN_CUCI);
        startActivity(i);
    }

    private void showRatingDialog(String jenisServis, String namaTeknisi) {
        new AppRatingDialog.Builder()
                .setPositiveButtonText("Kirim")
                .setNegativeButtonText("Batal")
                .setNeutralButtonText("Nanti")
                .setNoteDescriptions(Arrays.asList("Sangat Buruk", "Buruk", "Ok", "Bagus", "Sangat Bagus !!!"))
                .setDefaultRating(2)
                .setTitle("Berikan penilaianmu")
                .setDescription("Servis "+jenisServis+" "+namaTeknisi)
                .setCommentInputEnabled(true)
                .setDefaultComment("Pelayanan yang sangat baik !")
                .setStarColor(R.color.starColor)
                .setNoteDescriptionTextColor(R.color.noteDescriptionTextColor)
                .setTitleTextColor(R.color.textColorPrimary)
                .setDescriptionTextColor(R.color.textColorSecondary)
                .setHint("Tuliskan komentarmu disini ...")
                .setHintTextColor(R.color.textColorDisabled)
                .setCommentTextColor(R.color.commentTextColor)
                .setCommentBackgroundColor(R.color.colorPrimaryDark)
                .setWindowAnimation(R.style.MyDialogFadeAnimation)
                .setCancelable(false)
                .setCanceledOnTouchOutside(false)
                .create(MainActivity.this)
                //.setTargetFragment(this, TAG) // only if listener is implemented by fragment
                .show();
    }

    @Override
    public void onPositiveButtonClicked(int rate, String comment) {
        Toast.makeText(getApplicationContext(), "id_teknisi : "+ ID_PEMESANAN +"\nRating : "+rate+"\nComment : "+comment, Toast.LENGTH_LONG).show();
        if (cd.isConnectingToInternet()) {
            apiService.pemesananAddRatingAndKomentar(ID_PEMESANAN, rate, comment).enqueue(new Callback<ResponseAddPemesananRating>() {
                @Override
                public void onResponse(Call<ResponseAddPemesananRating> call, Response<ResponseAddPemesananRating> response) {
                    if (response.isSuccessful()){
                        if (response.body().getCode() == 200){
                            Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            ID_PEMESANAN = null;
                            getPemesanansUnrated();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "response add rating unsuccessful : "+response.toString(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseAddPemesananRating> call, Throwable t) {

                    t.printStackTrace();
                }
            });
        }
    }

    @Override
    public void onNegativeButtonClicked() {
//        Toast.makeText(getApplicationContext(), "Anda menekan tombol Batal", Toast.LENGTH_SHORT).show();
//        if (cd.isConnectingToInternet()) {
//            getPemesanansUnrated();
//        }
    }

    @Override
    public void onNeutralButtonClicked() {
//        Toast.makeText(getApplicationContext(), "Anda menekan tombol Nanti", Toast.LENGTH_SHORT).show();
//        if (cd.isConnectingToInternet()) {
//            getPemesanansUnrated();
//        }
    }

}
