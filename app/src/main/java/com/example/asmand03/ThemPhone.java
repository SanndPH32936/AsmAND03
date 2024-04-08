package com.example.asmand03;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.asmand03.api.ApiServer;
import com.example.asmand03.model.PhoneModel;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ThemPhone extends AppCompatActivity {
    EditText edtTen , edtBrand , edtDes , edtPrice,edtImg;
    Button btnThem;
    ImageView imgPhone ;
    ArrayList<File> dsImage ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_phone);
        anhXa();
        dsImage =new ArrayList<>();

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewPhone();
               
            }
        });

        imgPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage();
            }
        });



    }
    ActivityResultLauncher<Intent> getImage = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult o) {
                    if (o.getResultCode() == Activity.RESULT_OK){
                        dsImage.clear();
                        Intent data = o.getData();

                        if (data.getClipData() != null){
                            int count = data.getClipData().getItemCount();
                            for (int i=0 ; i<count ; i++){
                                Uri imageUri = data.getClipData().getItemAt(i).getUri();
                                File file = createFileFormUri(imageUri , "image"+i);

                                dsImage.add(file);
                            }
                        }else  if (data.getData() != null){
                            // Trường hợp chỉ chọn một hình ảnh
                            Uri imageUri = data.getData();
                            // Thực hiện các xử lý với imageUri
                            File file = createFileFormUri(imageUri, "image" );
                            dsImage.add(file);
                        }


                        imgPhone.setImageURI(Uri.fromFile(dsImage.get(0)));
                    }
                }
            });

    private void chooseImage() {

        Log.d("123123", "chooseAvatar: " +123123);
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
        getImage.launch(intent);

    }


    private File createFileFormUri (Uri path, String name) {
        File _file = new File(ThemPhone.this.getCacheDir(), name + ".png");
        try {
            InputStream in = ThemPhone.this.getContentResolver().openInputStream(path);
            OutputStream out = new FileOutputStream(_file);
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) >0) {
                out.write(buf, 0, len);
            }
            out.close();
            in.close();
            Log.d("123123", "createFileFormUri: " +_file);
            return _file;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
    private RequestBody getRequestBody(String value) {
        return RequestBody.create(MediaType.parse("multipart/form-data"),value);
    }
    private void addNewPhone() {

       try {

           String ten = edtTen.getText().toString(),
                   th = edtBrand.getText().toString(),
                    img = edtImg.getText().toString(),
                   des =edtDes.getText().toString() ;
           int     gia= Integer.parseInt(edtPrice.getText().toString()) ;

            PhoneModel model = new PhoneModel(ten,th,gia,des,img);


           Retrofit retrofit = new Retrofit.Builder()
                   .baseUrl("http://"+ApiServer.IPv4+":3000/")
                   .addConverterFactory(GsonConverterFactory.create())
                   .build();

           ApiServer apiServer = retrofit.create(ApiServer.class);

           Call<List<PhoneModel>> call = apiServer.addPhone(model);
    
            call.enqueue(new Callback<List<PhoneModel>>() {
                @Override
                public void onResponse(Call<List<PhoneModel>> call, Response<List<PhoneModel>> response) {
                    if (response.isSuccessful()){

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
                imgPhone = findViewById(R.id.imgPhoneThem);
    }
}