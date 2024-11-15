package com.duongnd.quanlythuvien.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.duongnd.quanlythuvien.R;
import com.duongnd.quanlythuvien.ui.phieumuon.PhieuMuonFragment;
import com.duongnd.quanlythuvien.ui.sach.SachFragment;
import com.duongnd.quanlythuvien.ui.theloai.LoaiSachFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    int role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
//        role = getIntent().getIntExtra("role", 1);


        initViews();
        if (savedInstanceState == null) {
            replaceFragment(new LoaiSachFragment());
        }


        setupNavigationDrawer();

    }

    private void setupNavigationDrawer() {
        View headerView = navigationView.getHeaderView(0);
        TextView tvUserName = headerView.findViewById(R.id.tv_user);
        TextView tvRole = headerView.findViewById(R.id.tv_role);
        tvUserName.setText(getIntent().getStringExtra("hoTen"));
        Menu menu = navigationView.getMenu();
        MenuItem taoNhanVien = menu.findItem(R.id.nav_tao_nhan_vien);

//        taoNhanVien.setVisible(role == 1);
//        tvRole.setText("Chuc vu: " + (role == 1 ? "Thu Thu" : "Nhan Vien"));

        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_quan_ly_sach) {
                replaceFragment(new SachFragment());
            } else if (id == R.id.nav_quan_ly_the_loai) {
                replaceFragment(new LoaiSachFragment());
            } else if (id == R.id.nav_quan_ly_thanh_vien) {
                Toast.makeText(MainActivity.this, "Quan Ly Thanh Vien", Toast.LENGTH_SHORT).show();
            } else if (id == R.id.nav_quan_ly_phieu_muon) {
                replaceFragment(new PhieuMuonFragment());
            } else if (id == R.id.nav_tao_nhan_vien) {
                Toast.makeText(MainActivity.this, "Tao Nhan Vien", Toast.LENGTH_SHORT).show();
            } else if (id == R.id.nav_doi_mat_khau) {
                Toast.makeText(this, "Đổi mật khẩu", Toast.LENGTH_SHORT).show();
            } else if (id == R.id.nav_doanh_thu) {
                Toast.makeText(this, "Doanh Thu", Toast.LENGTH_SHORT).show();
            } else if (id == R.id.nav_top10) {
                Toast.makeText(this, "Top 10 sách", Toast.LENGTH_SHORT).show();
            }
            setTitle(item.getTitle());
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }

    private void initViews() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav, R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}