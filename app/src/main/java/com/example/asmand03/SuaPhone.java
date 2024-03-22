package com.example.asmand03;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.asmand03.adapter.AdapterPhone;
import com.example.asmand03.api.ApiServer;
import com.example.asmand03.model.PhoneModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SuaPhone extends AppCompatActivity {
    EditText edtTen , edtBrand , edtDes , edtPrice,edtImg;
    Button btnSua;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_phone);
        anhXa();

        Bundle bundle = getIntent().getExtras();

        if (bundle == null){
            return;
        }
        setChu(bundle.getString("ten"), bundle.getString("brand") , bundle.getString("des"), ""+bundle.getInt("price"),bundle.getString("img"));
        PhoneModel model = new PhoneModel(bundle.getString("ten"),bundle.getString("brand"),bundle.getInt("price"),bundle.getString("des"),bundle.getString("img"));
        Toast.makeText(this, "Đã có", Toast.LENGTH_SHORT).show();

        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                suaPhone(bundle.getString("id"),model);
            }
        });

    }

    private void anhXa() {
        edtTen = findViewById(R.id.edtTenSpUp);
        edtBrand   = findViewById(R.id.edtBrandSpUp);
        edtDes= findViewById(R.id.edtDesSpUp);
        edtImg= findViewById(R.id.edtImgSpUp);
        edtPrice = findViewById(R.id.edtGiaSpUp);
        btnSua = findViewById(R.id.btnSuaSpUp);
    }

    private  void setChu(String ten , String brand , String des , String price , String img){
        edtTen.setText(ten);  edtBrand.setText(brand);  edtDes.setText(des);  edtPrice .setText(price);edtImg.setText(img);
    }

    private  void  suaPhone (String phoneId,PhoneModel phone){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.6:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiServer apiServer = retrofit.create(ApiServer.class);

        Call<PhoneModel> call = apiServer.updatePhone(phoneId,phone);

        call.enqueue(new Callback<PhoneModel>() {
            @Override
            public void onResponse(Call<PhoneModel> call, Response<PhoneModel> response) {
                if (response.isSuccessful()){
                        PhoneModel upPhone= response.body();

                        startActivity(new Intent(SuaPhone.this,TrangChu.class));
                    Toast.makeText(SuaPhone.this, "Ok", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(SuaPhone.this, "Sai", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PhoneModel> call, Throwable t) {

            }
        });
    }
}