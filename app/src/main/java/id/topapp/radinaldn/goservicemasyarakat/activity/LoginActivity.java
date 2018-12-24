package id.topapp.radinaldn.goservicemasyarakat.activity;

import android.Manifest;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import id.topapp.radinaldn.goservicemasyarakat.R;
import id.topapp.radinaldn.goservicemasyarakat.model.Masyarakat;
import id.topapp.radinaldn.goservicemasyarakat.response.ResponseLogin;
import id.topapp.radinaldn.goservicemasyarakat.rest.ApiClient;
import id.topapp.radinaldn.goservicemasyarakat.rest.ApiInterface;
import id.topapp.radinaldn.goservicemasyarakat.util.AbsRuntimePermission;
import id.topapp.radinaldn.goservicemasyarakat.util.SessionManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AbsRuntimePermission {

    private static final int REQUEST_PERMISSION = 10;
    Button btLogin, btRegister;
    TextInputEditText etNik, etPassword;
    ApiInterface apiService;
    SessionManager sessionManager;
    public static final String TAG = LoginActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //request permission here
        requestAppPermissions(new String[]{
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.RECORD_AUDIO},
                R.string.msg,REQUEST_PERMISSION);

        sessionManager = new SessionManager(this);
        apiService = ApiClient.getClient().create(ApiInterface.class);
        btLogin = findViewById(R.id.btLogin);
        btRegister = findViewById(R.id.btRegister);
        etNik = findViewById(R.id.etnik);
        etPassword = findViewById(R.id.etpassword);

        if(sessionManager.isLoggedIn()){
            Intent i = new Intent(LoginActivity.this, MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(i);
            finish();
        }

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etNik.getText().toString()!=null && etPassword.getText().toString()!=null){
                    actionLogin(etNik.getText().toString(), etPassword.getText().toString());
                } else {
                    Toast.makeText(getApplicationContext(), "Kolom NIK dan Password tidak boleh kosong", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onPermissionGranted(int requestcode) {
        Toast.makeText(getApplicationContext(), "Permission granted", Toast.LENGTH_LONG).show();
    }

    private void actionLogin(String nik, String password) {

        Log.d(TAG, "actionLogin: nik : "+nik+", password : "+password);
        
        apiService.login(nik, password).enqueue(new Callback<ResponseLogin>() {
            @Override
            public void onResponse(Call<ResponseLogin> call, Response<ResponseLogin> response) {
                if (response.isSuccessful()){
                    if (response.body().getCode() == 200){
                        Log.d(TAG, "onResponse: Dapat terhubung ke server");
                        Log.d(TAG, "onResponse: code : " +response.body().getCode()+", message : "+response.body().getMessage());

                        Masyarakat masyarakat = response.body().getMasyarakat();
                        sessionManager.createLoginSession(
                                masyarakat.getNik(),
                                masyarakat.getNama(),
                                masyarakat.getTempatLahir(),
                                masyarakat.getTanggalLahir(),
                                masyarakat.getJk(),
                                masyarakat.getAlamat(),
                                masyarakat.getAgama(),
                               masyarakat.getStatusKawin(),
                                masyarakat.getPekerjaan(),
                                masyarakat.getKewarganegaraan(),
                                masyarakat.getFoto(),
                                masyarakat.getNoHp(),
                                masyarakat.getSaldo()
                                );

                        Log.d(TAG, "onResponse: dapat data masyarakat");
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
                        finish();
                        startActivity(intent);
                        Toast.makeText(getApplicationContext(), "Selamat datang "+masyarakat.getNama(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseLogin> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Gagal konek ke server", Toast.LENGTH_LONG).show();
                Log.e(TAG, "onFailure: "+ t.getLocalizedMessage());
            }
        });
    }
}
