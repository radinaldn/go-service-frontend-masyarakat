package id.topapp.radinaldn.goservicemasyarakat.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import id.topapp.radinaldn.goservicemasyarakat.R;
import id.topapp.radinaldn.goservicemasyarakat.adapter.OrderankuAdapter;
import id.topapp.radinaldn.goservicemasyarakat.model.Pemesanan;
import id.topapp.radinaldn.goservicemasyarakat.response.ResponsePemesanan;
import id.topapp.radinaldn.goservicemasyarakat.response.ResponseViewPemesanan;
import id.topapp.radinaldn.goservicemasyarakat.rest.ApiClient;
import id.topapp.radinaldn.goservicemasyarakat.rest.ApiInterface;
import id.topapp.radinaldn.goservicemasyarakat.util.SessionManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by radinaldn on 29/12/18.
 */

public class OrderankuFragment extends Fragment {
    private RecyclerView recyclerView;
    private OrderankuAdapter adapter;
    private ArrayList<Pemesanan> pemesananArrayList = new ArrayList<>();
    private static final String ARG_STATUS= "proses";

    ApiInterface apiService;
    SwipeRefreshLayout swipeRefreshLayout;

    public static final String TAG = OrderankuFragment.class.getSimpleName();
    SessionManager sessionManager;
    private String proses, MY_NIK;

    public static OrderankuFragment newInstance(String proses){
        Bundle args = new Bundle();
        args.putString(ARG_STATUS, proses);

        OrderankuFragment fragment = new OrderankuFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public OrderankuFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getArguments();
        if( extras != null){
            proses = extras.getString(ARG_STATUS);
        }

        sessionManager = new SessionManager(getContext());
        MY_NIK = sessionManager.getMasyarakatDetail().get(SessionManager.NIK);

        apiService = ApiClient.getClient().create(ApiInterface.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_orderanku, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);



        swipeRefreshLayout = view.findViewById(R.id.swipe_activity_orderanku);
        swipeRefreshLayout.setColorSchemeResources(R.color.refresh, R.color.refresh1, R.color.refresh2);
        swipeRefreshLayout.setOnRefreshListener( new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Intent intent = getActivity().getIntent();
                getActivity().finish();
                getActivity().startActivity(intent);

            }
        });

        getOrderanku(proses);

        return view;
    }

    public void getOrderanku(final String proses) {
        pemesananArrayList.clear();
        Call<ResponseViewPemesanan> call = apiService.pemesananViewAllByNikAndProses(MY_NIK, proses);

        call.enqueue(new Callback<ResponseViewPemesanan>() {
            @Override
            public void onResponse(Call<ResponseViewPemesanan> call, Response<ResponseViewPemesanan> response) {
                if (response.isSuccessful()){
                    Log.d(TAG, "onResponse: "+response.toString());
                    Log.d(TAG, "onResponse: "+response.body().toString());
                    if(response.body().getPemesanans().size()>0){

                        pemesananArrayList.addAll(response.body().getPemesanans());

                        adapter = new OrderankuAdapter(getContext(), pemesananArrayList, OrderankuFragment.this, proses);

                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setAdapter(adapter);
                        swipeRefreshLayout.setRefreshing(false);
                    } else {
                        Toast.makeText(getContext(), "Data orderan by nik : "+MY_NIK+" dan proses : "+proses+" kosong.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "onResponse error: " +response.toString(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseViewPemesanan> call, Throwable t) {
                Toast.makeText(getContext(), "onFailure : "+t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }
}
