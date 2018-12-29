package id.topapp.radinaldn.goservicemasyarakat.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import id.topapp.radinaldn.goservicemasyarakat.R;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private static final String TAG_JENIS_SERVIS = "jenis_servis";

    // init TAG servis
    private static final String TAG_TV = "tv";
    private static final String TAG_KULKAS = "kulkas";
    private static final String TAG_AC = "ac";
    private static final String TAG_MESIN_CUCI = "mesin cuci";

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

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_dashboard);
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
}
