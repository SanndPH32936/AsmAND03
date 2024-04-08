package com.example.asmand03;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TrangChu extends AppCompatActivity {
    boolean tang = true;
    RecyclerView rcPhones ;
    Button btnThem ,btnSapXep;
    AdapterPhone adapterPhone ;
    EditText edtTimKiem ;
    List<PhoneModel> list = new ArrayList<>();
    List<PhoneModel> listTemp = new ArrayList<>();
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
                .baseUrl("http://"+ApiServer.IPv4+":3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiServer apiServer =retrofit.create(ApiServer.class);

        Call<List<PhoneModel>> call = apiServer.getPhones();

        call.enqueue(new Callback<List<PhoneModel>>() {
            @Override
            public void onResponse(Call<List<PhoneModel>> call, Response<List<PhoneModel>> response) {
                if (response.isSuccessful()){
                        list = response.body();
                        listTemp = list ;
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


        edtTimKiem.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                List<PhoneModel> models = new ArrayList<>();

               for (PhoneModel item : listTemp) {
                        if (item.getName().contains(charSequence.toString().trim())) {
                            models.add(item);
                        }
                    }
                list = models;

                rcPhones.setAdapter(new AdapterPhone(TrangChu.this,list));


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    btnSapXep.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            tang = !tang;
            if (tang){
                tangDanTheoGia();
            }else {
                giamDanTheoGia();
            }


            adapterPhone.notifyDataSetChanged();
        }
    });



    }

    private void giamDanTheoGia() {
        list.sort(new Comparator<PhoneModel>() {
            @Override
            public int compare(PhoneModel model, PhoneModel t1) {
                if (model.getPrice() < t1.getPrice()){
                    return  1;
                }else if(model.getPrice() > t1.getPrice()){
                    return -1;
                }else {
                    return 0;
                }
            }
        });
    }

    private void tangDanTheoGia() {
       list.sort(new Comparator<PhoneModel>() {
           @Override
           public int compare(PhoneModel model, PhoneModel t1) {
                if (model.getPrice() < t1.getPrice()){
                    return  -1;
                }else if(model.getPrice() > t1.getPrice()){
                    return 1;
               }else {
                    return 0;
                }
           }
       });
    }

    private void anhXa() {
        rcPhones = findViewById(R.id.rcPhones);
        btnThem = findViewById(R.id.btnThem);
        edtTimKiem = findViewById(R.id.edtTimKiem);
        btnSapXep = findViewById(R.id.btnSapXep);
    }
}