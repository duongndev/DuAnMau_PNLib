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
import com.duongnd.quanlythuvien.data.model.NhanVien;
import com.duongnd.quanlythuvien.ui.doiMK.DoiPassFragment;
import com.duongnd.quanlythuvien.ui.nhanvien.NhanVienFragment;
import com.duongnd.quanlythuvien.ui.phieumuon.PhieuMuonFragment;
import com.duongnd.quanlythuvien.ui.sach.SachFragment;
import com.duongnd.quanlythuvien.ui.thanhvien.ThanhVienFragment;
import com.duongnd.quanlythuvien.ui.theloai.LoaiSachFragment;
import com.duongnd.quanlythuvien.ui.thongke.DoanhThuFragment;
import com.duongnd.quanlythuvien.ui.thongke.Top10Fragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    int role;
    String hoTen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        NhanVien nhanVien = (NhanVien) getIntent().getSerializableExtra("nhanVien");
        role = nhanVien.getRole();
        hoTen = nhanVien.getHoTen();


        initViews();
        if (savedInstanceState == null) {
            replaceFragment(new PhieuMuonFragment());
        }
        
        setupNavigationDrawer();

    }

    private void setupNavigationDrawer() {
        View headerView = navigationView.getHeaderView(0);
        TextView tvUserName = headerView.findViewById(R.id.tv_user);
        TextView tvRole = headerView.findViewById(R.id.tv_role);
        tvUserName.setText(hoTen);
        Menu menu = navigationView.getMenu();
        MenuItem taoNhanVien = menu.findItem(R.id.nav_tao_nhan_vien);

        taoNhanVien.setVisible(role == 1);
        tvRole.setText("Chức vụ: " + (role == 1 ? "Thủ thư" : "Nhân viên"));

        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_quan_ly_sach) {
                replaceFragment(new SachFragment());
            } else if (id == R.id.nav_quan_ly_the_loai) {
                replaceFragment(new LoaiSachFragment());
            } else if (id == R.id.nav_quan_ly_thanh_vien) {
                replaceFragment(new ThanhVienFragment());
            } else if (id == R.id.nav_quan_ly_phieu_muon) {
                replaceFragment(new PhieuMuonFragment());
            } else if (id == R.id.nav_tao_nhan_vien) {
                replaceFragment(new NhanVienFragment());
            } else if (id == R.id.nav_doi_mat_khau) {
                replaceFragment(new DoiPassFragment());
            } else if (id == R.id.nav_doanh_thu) {
                replaceFragment(new DoanhThuFragment());
            } else if (id == R.id.nav_top10) {
                replaceFragment(new Top10Fragment());
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