package id.topapp.radinaldn.goservicemasyarakat.adapter;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import id.topapp.radinaldn.goservicemasyarakat.R;
import id.topapp.radinaldn.goservicemasyarakat.activity.DetailPemesananActivity;
import id.topapp.radinaldn.goservicemasyarakat.activity.OrderankuActivity;
import id.topapp.radinaldn.goservicemasyarakat.config.ServerConfig;
import id.topapp.radinaldn.goservicemasyarakat.fragment.OrderankuFragment;
import id.topapp.radinaldn.goservicemasyarakat.model.Pemesanan;
import id.topapp.radinaldn.goservicemasyarakat.response.ResponseBayarDenganSaldo;
import id.topapp.radinaldn.goservicemasyarakat.rest.ApiClient;
import id.topapp.radinaldn.goservicemasyarakat.rest.ApiInterface;
import id.topapp.radinaldn.goservicemasyarakat.util.SessionManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by radinaldn on 29/12/18.
 */

public class OrderankuAdapter extends RecyclerView.Adapter<OrderankuAdapter.OrderankuViewHolder> {

    private static final String TAG_ID_PEMESANAN = "id_pemesanan";
    private Context context;

    private ArrayList<Pemesanan> dataList;
    private static final String TAG = OrderankuAdapter.class.getSimpleName();

    ApiInterface apiService;
    SessionManager sessionManager;
    private static String NIK;
    private OrderankuFragment fragment;
    private String proses;

    public OrderankuAdapter(Context context, ArrayList<Pemesanan> dataList, OrderankuFragment fragment, String proses) {
        this.context = context;
        this.dataList = dataList;
        this.fragment = fragment;
        this.proses = proses;
    }

    @NonNull
    @Override
    public OrderankuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        final View view = layoutInflater.inflate(R.layout.orderanku_item, parent, false);

        apiService = ApiClient.getClient().create(ApiInterface.class);
        sessionManager = new SessionManager(context);
        NIK = sessionManager.getMasyarakatDetail().get(SessionManager.NIK);

        return new OrderankuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderankuViewHolder holder, final int position) {

        // convert saldo
        // convert format saldo into rupiah
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        String biayaInRP = formatRupiah.format(Double.parseDouble(dataList.get(position).getBiaya()));

        holder.tv_id_pemesanan.setText(dataList.get(position).getIdPemesanan());

        switch (dataList.get(position).getProses()){
            case "Diproses":
                Picasso.with(context)
                        .load(ServerConfig.FOTO_PEMESANAN_PATH+dataList.get(position).getFotoSebelum())
                        .placeholder(R.drawable.dummy_ava)
                        .error(R.drawable.dummy_ava)
                        .centerCrop()
                        .fit()
                        .into(holder.iv_foto);
                holder.tv_nama_toko.setText(dataList.get(position).getNamaToko());
                break;
            case "Selesai":
                Picasso.with(context)
                        .load(ServerConfig.FOTO_PEMESANAN_PATH+dataList.get(position).getFotoSesudah())
                        .placeholder(R.drawable.dummy_ava)
                        .error(R.drawable.dummy_ava)
                        .centerCrop()
                        .fit()
                        .into(holder.iv_foto);
                holder.tv_nama_toko.setText(dataList.get(position).getNamaToko());

                break;
            case "Dibayar":
                Picasso.with(context)
                        .load(ServerConfig.FOTO_PEMESANAN_PATH+dataList.get(position).getFotoSesudah())
                        .placeholder(R.drawable.dummy_ava)
                        .error(R.drawable.dummy_ava)
                        .centerCrop()
                        .fit()
                        .into(holder.iv_foto);
                holder.tv_nama_toko.setText(dataList.get(position).getNamaToko());
                break;
        }

        switch (dataList.get(position).getJenisServis().toLowerCase()){
            case "tv":
                Picasso.with(context)
                        .load(R.drawable.tv)
                        .into(holder.iv_kategori);
                break;
            case "kulkas":
                Picasso.with(context)
                        .load(R.drawable.refrigerator)
                        .into(holder.iv_kategori);
                break;
            case "ac":
                Picasso.with(context)
                        .load(R.drawable.air_conditioner)
                        .into(holder.iv_kategori);
                break;
            case "mesin cuci":
                Picasso.with(context)
                        .load(R.drawable.washing_machine)
                        .into(holder.iv_kategori);
                break;
        }



        holder.tv_kategori_bayar.setText(dataList.get(position).getKategoriBayar());
        holder.tv_keluhan.setText(dataList.get(position).getKeluhan());
        switch (dataList.get(position).getKategoriBayar()){
            case "Cash":

                holder.tv_kategori_bayar.setBackgroundColor(context.getResources().getColor(R.color.colorMintDark));

                if (dataList.get(position).getProses().equalsIgnoreCase("Selesai")){
                    holder.tv_kategori_bayar.setText("Lakukan pembayaran senilai "+biayaInRP+" secara tunai");
                }
                break;
            case "Saldo":

                holder.tv_kategori_bayar.setBackgroundColor(context.getResources().getColor(R.color.colorBlueJeansDark));

                if (dataList.get(position).getProses().equalsIgnoreCase("Selesai")){
                    holder.tv_kategori_bayar.setVisibility(View.GONE);
                    holder.bt_bayar.setVisibility(View.VISIBLE);
                    holder.bt_bayar.setText("Bayar (Saldo)\n"+biayaInRP);
                    holder.bt_bayar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            actionBayarSaldo(NIK, dataList.get(position).getIdPemesanan());
                            // Aksi kilk bayar saldo disini
                        }
                    });
                }



                break;

        }

        holder.tv_created_at.setText(dataList.get(position).getCreatedAt());
        holder.iv_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+dataList.get(position).getNoHp()));
                context.startActivity(intent);
            }
        });
    }

    private void actionBayarSaldo(String nik, String idPemesanan) {
        apiService.pemesananBayarDenganSaldo(nik, idPemesanan).enqueue(new Callback<ResponseBayarDenganSaldo>() {
            @Override
            public void onResponse(Call<ResponseBayarDenganSaldo> call, Response<ResponseBayarDenganSaldo> response) {
                Log.d(TAG, "onResponse: "+response.toString());
                if (response.isSuccessful()){

                    Log.d(TAG, "onResponse: "+response.body().toString());
                    if (response.body().getCode() == 200){
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                        // fungsi ini belum jalan
                        Intent i = new Intent(context, DetailPemesananActivity.class);
                        i.putExtra(TAG_ID_PEMESANAN, response.body().getPemesanan().getIdPemesanan());
                        context.startActivity(i);

                    } else if (response.body().getMessage().equalsIgnoreCase("Saldo anda tidak mencukupi!")) {
                        if(context instanceof OrderankuActivity){
                            ((OrderankuActivity)context).showSnackbarSaldo();
                        }
                    } else {
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.d(TAG, "onResponse: response unsecessful");
                }
            }

            @Override
            public void onFailure(Call<ResponseBayarDenganSaldo> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @Override
    public int getItemCount() {
        return (dataList != null) ? dataList.size() : 0;
    }

    public class OrderankuViewHolder extends RecyclerView.ViewHolder {

        TextView tv_id_pemesanan, tv_nama_toko, tv_keluhan, tv_kategori_bayar, tv_created_at;
        ImageView iv_foto, iv_kategori, iv_call;
        Button bt_bayar;

        public OrderankuViewHolder(final View itemView) {
            super(itemView);
            tv_id_pemesanan = itemView.findViewById(R.id.tv_id_pemesanan);
            tv_nama_toko = itemView.findViewById(R.id.tv_nama_toko);
            tv_keluhan = itemView.findViewById(R.id.tv_keluhan);
            tv_created_at = itemView.findViewById(R.id.tv_created_at);
            iv_foto = itemView.findViewById(R.id.iv_foto);
            iv_kategori = itemView.findViewById(R.id.iv_kategori);
            tv_kategori_bayar = itemView.findViewById(R.id.tv_kategori_bayar);
            iv_call = itemView.findViewById(R.id.iv_call);
            bt_bayar = itemView.findViewById(R.id.btBayar);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(itemView.getContext(), DetailPemesananActivity.class);
                    i.putExtra(TAG_ID_PEMESANAN, tv_id_pemesanan.getText());

                    // Aktifkan untuk mode debugging
                    //Toast.makeText(itemView.getContext(), "ID_MENGAJAR : "+tv_idmengajar.getText(), Toast.LENGTH_SHORT).show();

                    itemView.getContext().startActivity(i);
                }
            });

        }
    }
}
