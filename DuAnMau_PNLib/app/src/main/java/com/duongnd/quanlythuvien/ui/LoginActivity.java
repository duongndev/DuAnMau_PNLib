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
import com.duongnd.quanlythuvien.data.dao.ThuThuDAO;
import com.duongnd.quanlythuvien.data.model.NhanVien;

public class LoginActivity extends AppCompatActivity {

    EditText etUsername, etPassword;
    CheckBox cbRememberMe;
    SharedPreferences sharedPreferences;
    Button btnLogin;
    NhanVienDAO nhanVienDAO;
    ThuThuDAO thuThuDAO;

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
        thuThuDAO = new ThuThuDAO(this);

        etPassword = findViewById(R.id.etPassword);
        etUsername = findViewById(R.id.etUsername);
        cbRememberMe = findViewById(R.id.cbRememberPassword);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(v -> {
            String username = etUsername.getText().toString();
            String password = etPassword.getText().toString();

        });

    }

    public void getLogin(String username, String password){
        if (username.isEmpty() || password.isEmpty()){
            Toast.makeText(this, "Vui lòng nhập tên đăng nhập và mật khẩu", Toast.LENGTH_SHORT).show();
        } else {
            if (validateLogin(username, password)){
                Toast.makeText(this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("username", username);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Tên đăng nhập và mật khẩu không chính xác", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean validateLogin(String username, String password){
        return thuThuDAO.checkLogin(username, password);
    }



    private void getUsers(String username){
        NhanVien nhanVien = nhanVienDAO.layNhanVienTheoTenDangNhap(username);
    }


}