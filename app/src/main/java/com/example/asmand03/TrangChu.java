package com.example.asmand03;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asmand03.adapter.AdapterPhone;
import com.example.asmand03.api.ApiServer;
import com.example.asmand03.model.PhoneModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TrangChu extends AppCompatActivity {
    RecyclerView rcPhones ;
    Button btnThem ;
    AdapterPhone adapterPhone ;
    List<PhoneModel> list = new ArrayList<>();
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser user = auth.getCurrentUser();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trang_chu);
        anhXa();
        findViewById(R.id.btnDangXuat).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    auth.signOut();
                    startActivity(new Intent(TrangChu.this,DangNhap.class));
                    finishAffinity();
            }
        });
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TrangChu.this, ThemPhone.class));
            }
        });

        Retrofit retrofit =new Retrofit.Builder()
                .baseUrl("http://192.168.1.6:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiServer apiServer =retrofit.create(ApiServer.class);

        Call<List<PhoneModel>> call = apiServer.getPhones();

        call.enqueue(new Callback<List<PhoneModel>>() {
            @Override
            public void onResponse(Call<List<PhoneModel>> call, Response<List<PhoneModel>> response) {
                if (response.isSuccessful()){
                        list = response.body();
                        adapterPhone = new AdapterPhone(getApplicationContext(),list);
                    GridLayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),2);
                    rcPhones.setLayoutManager(layoutManager);
                    rcPhones.setAdapter(adapterPhone);
                    adapterPhone.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<PhoneModel>> call, Throwable t) {

            }
        });





    }

    private void anhXa() {
        rcPhones = findViewById(R.id.rcPhones);
        btnThem = findViewById(R.id.btnThem);
    }
}