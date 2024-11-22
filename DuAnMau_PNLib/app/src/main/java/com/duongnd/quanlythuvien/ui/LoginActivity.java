package com.duongnd.quanlythuvien.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.duongnd.quanlythuvien.R;
import com.duongnd.quanlythuvien.data.dao.NhanVienDAO;
import com.duongnd.quanlythuvien.data.model.NhanVien;

public class LoginActivity extends AppCompatActivity {

    EditText etUsername, etPassword;
    CheckBox cbRememberMe;
    Button btnLogin;
    NhanVienDAO nhanVienDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);


        nhanVienDAO = new NhanVienDAO(this);

        etPassword = findViewById(R.id.etPassword);
        etUsername = findViewById(R.id.etUsername);
        cbRememberMe = findViewById(R.id.cbRememberPassword);
        btnLogin = findViewById(R.id.btnLogin);

        SharedPreferences sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
        etUsername.setText(sharedPreferences.getString("username", ""));
        etPassword.setText(sharedPreferences.getString("password", ""));
        cbRememberMe.setChecked(sharedPreferences.getBoolean("rememberMe", false));


        btnLogin.setOnClickListener(v -> {
            String username = etUsername.getText().toString();
            String password = etPassword.getText().toString();
            checkLogin(username, password);
        });

    }

    private void checkLogin(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập tên đăng nhập và mật khẩu", Toast.LENGTH_SHORT).show();
        } else {
            NhanVien nhanVien = nhanVienDAO.checkDangNhap(username, password);
            if (nhanVien != null) {
                saveLogin(username, password, cbRememberMe.isChecked());
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra("nhanVien", nhanVien);
                startActivity(intent);
                Toast.makeText(this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Tên đăng nhập và mật khẩu không chính xác", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void saveLogin(String username, String password, boolean remember) {
        SharedPreferences sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (!remember) {
            editor.clear();
        } else {
            editor.putString("username", username);
            editor.putString("password", password);
            editor.putBoolean("remember", true);
        }
        editor.apply();
    }

}