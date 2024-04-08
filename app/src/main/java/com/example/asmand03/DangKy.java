package com.example.asmand03;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class DangKy extends AppCompatActivity {

    TextInputEditText edtEmail , edtPass ,edtCheckPass ;
    Button btnDangKy , btnHuy;
    FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);
        anhXa();
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = edtEmail.getText().toString() , pass = edtPass.getText().toString() , checkpass = edtCheckPass.getText().toString();
                if (email.isEmpty()|| pass.isEmpty()||checkpass.isEmpty()){
                    Toast.makeText(DangKy.this, "Không được để form trống", Toast.LENGTH_SHORT).show();
                }else if (!pass.equals(checkpass)) {
                    Toast.makeText(DangKy.this, "Mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
                }else {
                    dangKy (email,pass);

                }
            }
        });

    }

    private void dangKy( String email ,String pass) {
        auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(DangKy.this, "Đăng kí thành công", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(DangKy.this, TrangChu.class));
                }    else {
                    Toast.makeText(DangKy.this, "Đăng kí thất bại"+task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void anhXa() {
        edtEmail = findViewById(R.id.edtEmailDK);
        edtPass = findViewById(R.id.edtPassDK);
        edtCheckPass  = findViewById(R.id.edtCheckPassDK);
        btnDangKy = findViewById(R.id.btnDangKyDK);
        btnHuy = findViewById(R.id.btnCancelDK);
    }
}