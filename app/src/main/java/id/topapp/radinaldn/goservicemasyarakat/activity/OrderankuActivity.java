package id.topapp.radinaldn.goservicemasyarakat.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import id.topapp.radinaldn.goservicemasyarakat.R;
import id.topapp.radinaldn.goservicemasyarakat.adapter.OrderankuViewPagerAdapter;
import id.topapp.radinaldn.goservicemasyarakat.fragment.OrderankuFragment;
import id.topapp.radinaldn.goservicemasyarakat.rest.ApiClient;
import id.topapp.radinaldn.goservicemasyarakat.rest.ApiInterface;

public class OrderankuActivity extends AppCompatActivity {

    private TextView mTextMessage;
    ViewPager viewPager;
    TabLayout tabLayout;
    ApiInterface apiService;
    View parentLayout;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_orderanku:



                    return true;
                case R.id.navigation_dashboard:

                    Intent intent2 = new Intent(OrderankuActivity.this, MainActivity.class);
                    intent2.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent2);
                    finish();
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.navigation_profil:

                    Intent intent3 = new Intent(OrderankuActivity.this, ProfilActivity.class);
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
        setContentView(R.layout.activity_orderanku);

        parentLayout = findViewById(android.R.id.content);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_orderanku);

        apiService = ApiClient.getClient().create(ApiInterface.class);

//        Toolbar toolbar = findViewById(R.id.toolbar_kehadiran_dosen);
//        setSupportActionBar(toolbar);
//
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                goToMainActivity();
//            }
//        });
//
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = findViewById(R.id.viewpager_kehadiran_dosen);
        tabLayout = findViewById(R.id.tabs_kehadiran_dosen);

        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        OrderankuViewPagerAdapter adapter = new OrderankuViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(OrderankuFragment.newInstance("Diproses"), "Diproses");
        adapter.addFragment(OrderankuFragment.newInstance("Selesai"), "Selesai");
        adapter.addFragment(OrderankuFragment.newInstance("Dibayar"), "Dibayar");
        viewPager.setAdapter(adapter);
    }

    private void goToMainActivity(){
        Intent intent = new Intent(OrderankuActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
        finish();
    }

    public void showSnackbarSaldo(){
        Snackbar.make(parentLayout, "Saldo anda tidak mencukupi!", Snackbar.LENGTH_LONG).setAction("Isi Saldo", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(OrderankuActivity.this, SaldoActivity.class);
                startActivity(i);
            }
        }).show();
    }

}
