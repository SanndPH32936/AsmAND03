package com.example.asmand03;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
import com.google.firebase.auth.FirebaseUser;

public class DangNhap extends AppCompatActivity {

    Button btnDangNhap ,btnHuy;
    TextView btnDangKy ;
    TextInputEditText edtEmail , edtPass ;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser user = auth.getCurrentUser();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);
        anhXa();
        if (user != null){
            startActivity(new Intent(DangNhap.this,TrangChu.class));
            finishAffinity();
        }
        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DangNhap.this,DangKy.class));

            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = edtEmail.getText().toString(), pass = edtPass.getText().toString() ;
                if (email.isEmpty() || pass .isEmpty()){
                    Toast.makeText(DangNhap.this, "Không được bỏ trống form", Toast.LENGTH_SHORT).show();

                }else {
                    auth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                startActivity(new Intent(DangNhap.this,TrangChu.class));
                                finishAffinity();
                            }    else {
                                Toast.makeText(DangNhap.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
            }
        });

    }

    private void anhXa() {
        btnDangNhap = findViewById(R.id.btnLogin);

        btnHuy = findViewById(R.id.btnCancelDN);
        btnDangKy = findViewById(R.id.tvDangkyDN);
        edtEmail = findViewById(R.id.edtEmailDN);
        edtPass = findViewById(R.id.edtPassDN);
    }
}