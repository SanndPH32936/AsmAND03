package com.example.asmand03.adapter;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asmand03.R;
import com.example.asmand03.SuaPhone;
import com.example.asmand03.api.ApiServer;
import com.example.asmand03.model.PhoneModel;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AdapterPhone extends RecyclerView.Adapter<AdapterPhone.HolderAdapter>{
    Context context;
    List<PhoneModel> list ;

    public AdapterPhone(Context context, List<PhoneModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public HolderAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_rcphones,parent,false);
        return new HolderAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderAdapter holder, int position) {
        PhoneModel ph = list.get(position);
        holder.tvTen .setText(ph.getName().toUpperCase());
        holder.tvPrice.setText(""+ph.getPrice() +" VNĐ");
        holder.tvBrand.setText(ph.getBrand());
        holder.tvId.setText("ID: "+ph.get_id());
        Picasso.get().load(ph.getImg()).into(holder.imgPhone);

        holder.btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://192.168.1.6:3000/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                ApiServer apiServer = retrofit.create(ApiServer.class);

                String phoneId = ph.get_id();
                Call<List<PhoneModel>>call= apiServer.deletePhone(phoneId);

                call.enqueue(new Callback<List<PhoneModel>>() {
                    @Override
                    public void onResponse(Call<List<PhoneModel>> call, Response<List<PhoneModel>> response) {
                        if (response.isSuccessful()){
                            list.remove(position);
                            notifyDataSetChanged();
                            Toast.makeText(context, "Xóa ok", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(context, "sai", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<PhoneModel>> call, Throwable t) {
                        list.remove(position);
                        notifyDataSetChanged();
                    }
                });
            }
        });


        holder.btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SuaPhone.class);
                Bundle bundle = new Bundle();
                bundle.putString("id",ph.get_id());
                bundle.putString("ten",ph.getName());
                bundle.putString("brand",ph.getBrand());
                bundle.putInt("price",ph.getPrice());
                bundle.putString("des",ph.getDes());
                bundle.putString("img",ph.getImg());
                intent.putExtras(bundle);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class  HolderAdapter extends RecyclerView.ViewHolder {

        TextView tvTen, tvId ,tvBrand ,tvPrice;
        ImageView imgPhone;
        ImageButton btnXoa , btnSua ;
        public HolderAdapter(@NonNull View itemView) {
            super(itemView);

            tvId = itemView.findViewById(R.id.tvIdPhone);
            tvBrand = itemView.findViewById(R.id.tvBrandPhone);
            tvTen = itemView.findViewById(R.id.tvTenPhone);
            tvPrice = itemView.findViewById(R.id.tvGiaPhone);

            btnSua = itemView.findViewById(R.id.btnSua);
            btnXoa = itemView.findViewById(R.id.btnXoa);
            imgPhone = itemView.findViewById(R.id.imgAvatarPhone);

        }
    }
}
