package com.duongnd.quanlythuvien.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.duongnd.quanlythuvien.R;
import com.duongnd.quanlythuvien.data.dao.NhanVienDAO;
import com.duongnd.quanlythuvien.data.model.NhanVien;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsername, etPassword;
    private CheckBox cbRememberMe;
    private SharedPreferences sharedPreferences;
    private Button btnLogin;
    NhanVienDAO nhanVienDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main_login), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE);

        nhanVienDAO = new NhanVienDAO(this);

        etPassword = findViewById(R.id.etPassword);
        etUsername = findViewById(R.id.etUsername);
        cbRememberMe = findViewById(R.id.cbRememberPassword);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(v -> {
            String username = etUsername.getText().toString();
            String password = etPassword.getText().toString();
            if (validateLogin(username, password)) {
                Toast.makeText(this, "Login thanh cong", Toast.LENGTH_SHORT).show();
                NhanVien nhanVien = nhanVienDAO.layNhanVienTheoTenDangNhap(username);
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra("hoTen", nhanVien.getHoTen());
                intent.putExtra("role", nhanVien.getRole());
                startActivity(intent);
            } else {
                Toast.makeText(this, "Login that bai", Toast.LENGTH_SHORT).show();
            }

        });

    }


    private boolean validateLogin(String username, String password){
        return nhanVienDAO.checkDangNhap(username, password);
    }



    private void getUsers(String username){
        NhanVien nhanVien = nhanVienDAO.layNhanVienTheoTenDangNhap(username);
    }


}