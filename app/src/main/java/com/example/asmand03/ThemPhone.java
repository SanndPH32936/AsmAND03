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

import com.example.asmand03.api.ApiServer;
import com.example.asmand03.model.PhoneModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ThemPhone extends AppCompatActivity {
    EditText edtTen , edtBrand , edtDes , edtPrice,edtImg;
    Button btnThem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_phone);
        anhXa();

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewPhone();
               
            }
        });



    }

    private void addNewPhone() {
       try {
           String ten = edtTen.getText().toString(),
                   th = edtBrand.getText().toString(),
                   img =  edtImg.getText().toString(),
                   des =edtDes.getText().toString() ;
           int     gia= Integer.parseInt(edtPrice.getText().toString()) ;

           PhoneModel newPhone = new PhoneModel(ten,th,gia,des,img);
           Retrofit retrofit = new Retrofit.Builder()
                   .baseUrl("http://192.168.1.6:3000/")
                   .addConverterFactory(GsonConverterFactory.create())
                   .build();

           ApiServer apiServer = retrofit.create(ApiServer.class);
           Call<List<PhoneModel>> call = apiServer.addPhone(newPhone);
    
            call.enqueue(new Callback<List<PhoneModel>>() {
                @Override
                public void onResponse(Call<List<PhoneModel>> call, Response<List<PhoneModel>> response) {
                    if (response.isSuccessful()){

                        Toast.makeText(ThemPhone.this, "Ok", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<PhoneModel>> call, Throwable t) {

                }
            });

           startActivity(new Intent(ThemPhone.this , TrangChu.class));
       }catch (NumberFormatException e){
           Toast.makeText(this, "Giá phải là 1 số", Toast.LENGTH_SHORT).show();
       }





    }

    private void anhXa() {
        edtTen = findViewById(R.id.edtTenSpNew);
             edtBrand   = findViewById(R.id.edtBrandSpNew);
                edtDes= findViewById(R.id.edtDesSpNew);
                edtImg= findViewById(R.id.edtImgSpNew);
                edtPrice = findViewById(R.id.edtGiaSpNew);
                btnThem = findViewById(R.id.btnThemSpNew);
    }
}