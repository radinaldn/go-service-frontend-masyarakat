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

import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import id.topapp.radinaldn.goservicemasyarakat.R;
import id.topapp.radinaldn.goservicemasyarakat.config.ServerConfig;
import id.topapp.radinaldn.goservicemasyarakat.response.ResponseViewSaldo;
import id.topapp.radinaldn.goservicemasyarakat.rest.ApiClient;
import id.topapp.radinaldn.goservicemasyarakat.rest.ApiInterface;
import id.topapp.radinaldn.goservicemasyarakat.util.SessionManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfilActivity extends AppCompatActivity {


    TextView tvNik, tvNama, tvTtl, tvJk, tvAlamat, tvAgama, tvStatusKawin, tvPekerjaan, tvKewarganegaraan, tvNoHp, tvSaldo;
    CircleImageView ivFoto;
    SessionManager sessionManager;
    ApiInterface apiService;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_orderanku:


                    Intent intent = new Intent(ProfilActivity.this, OrderankuActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.navigation_dashboard:

                    Intent intent2 = new Intent(ProfilActivity.this, MainActivity.class);
                    intent2.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent2);
                    finish();
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.navigation_profil:

                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        apiService = ApiClient.getClient().create(ApiInterface.class);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_profil);

        sessionManager = new SessionManager(this);
        tvNik = findViewById(R.id.tvNik);
        tvNama = findViewById(R.id.tvNama);
        tvTtl = findViewById(R.id.tvTtl);
        tvJk = findViewById(R.id.tvJk);
        tvAlamat = findViewById(R.id.tvAlamat);
        tvAgama = findViewById(R.id.tvAgama);
        tvStatusKawin = findViewById(R.id.tvStatusKawin);
        tvPekerjaan = findViewById(R.id.tvPekerjaan);
        tvKewarganegaraan = findViewById(R.id.tvKewarganegaraan);
        tvNoHp = findViewById(R.id.tvNoHp);
        tvSaldo = findViewById(R.id.tvSaldo);
        ivFoto = findViewById(R.id.ivFoto);

        tvNik.setText(sessionManager.getMasyarakatDetail().get(SessionManager.NIK));
        tvNama.setText(sessionManager.getMasyarakatDetail().get(SessionManager.NAMA));
        tvTtl.setText(sessionManager.getMasyarakatDetail().get(SessionManager.TEMPAT_LAHIR)+", "+sessionManager.getMasyarakatDetail().get(SessionManager.TANGGAL_LAHIR));
        tvJk.setText(sessionManager.getMasyarakatDetail().get(SessionManager.JK));
        tvAlamat.setText(sessionManager.getMasyarakatDetail().get(SessionManager.ALAMAT));
        tvAgama.setText(sessionManager.getMasyarakatDetail().get(SessionManager.AGAMA));
        tvStatusKawin.setText(sessionManager.getMasyarakatDetail().get(SessionManager.STATUS_KAWIN));
        tvPekerjaan.setText(sessionManager.getMasyarakatDetail().get(SessionManager.PEKERJAAN));
        tvKewarganegaraan.setText(sessionManager.getMasyarakatDetail().get(SessionManager.KEWARGANEGARAAN));
        tvNoHp.setText(sessionManager.getMasyarakatDetail().get(SessionManager.NO_HP));

        // get user saldo
        getSaldoRealtime();
        // convert format saldo into rupiah


        Log.d("TAG", "foto: "+ServerConfig.MASYARAKAT_PROFIL_PATH+sessionManager.getMasyarakatDetail().get(SessionManager.FOTO));
        Picasso.with(this)
                .load(ServerConfig.MASYARAKAT_PROFIL_PATH+sessionManager.getMasyarakatDetail().get(SessionManager.FOTO))
                .error(R.drawable.dummy_ava)
                .centerCrop()
                .fit()
                .into(ivFoto);
    }

    private void getSaldoRealtime() {

        apiService.masyarakatViewSaldo(sessionManager.getMasyarakatDetail().get(SessionManager.NIK)).enqueue(new Callback<ResponseViewSaldo>() {
            @Override
            public void onResponse(Call<ResponseViewSaldo> call, Response<ResponseViewSaldo> response) {
                if (response.isSuccessful()){
                    System.out.println(response.toString());
                    System.out.println(response.body().toString());

                    if (response.body().getCode() == 200){
                        Locale localeID = new Locale("in", "ID");
                        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
                        String saldoFormatted = formatRupiah.format(Double.parseDouble(response.body().getSaldo().toString()));
                        Log.d("TAG", "saldo: "+saldoFormatted);

                        tvSaldo.setText("Saldo Anda:\n"+saldoFormatted);
                        tvSaldo.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent i = new Intent(ProfilActivity.this, SaldoActivity.class);
                                startActivity(i);
                            }
                        });
                        Log.d("TAG", "tvSaldo: "+sessionManager.getMasyarakatDetail().get("Saldo Anda:\n"+saldoFormatted));
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseViewSaldo> call, Throwable t) {
t.printStackTrace();
            }
        });


    }

    private void goToMainActivity(){
        Intent intent = new Intent(ProfilActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        goToMainActivity();
    }

    public void actionLogout(View view) {
        Intent intent = new Intent(ProfilActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
        finish();
        sessionManager.logoutMasyarakat();
    }
}
