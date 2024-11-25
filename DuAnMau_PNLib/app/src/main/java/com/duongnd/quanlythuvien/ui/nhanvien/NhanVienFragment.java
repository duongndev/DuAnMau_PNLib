package com.duongnd.quanlythuvien.ui.nhanvien;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.duongnd.quanlythuvien.R;
import com.duongnd.quanlythuvien.data.dao.NhanVienDAO;
import com.duongnd.quanlythuvien.data.model.NhanVien;

public class NhanVienFragment extends Fragment {
    EditText edt_ho_ten, edt_ten_dang_nhap, edt_pass, edt_confirm_pass;
    Button btn_add_nhan_vien, btn_cancel_nhan_vien;
    NhanVienDAO nhanVienDAO;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_nhan_vien, container, false);
        edt_ho_ten = view.findViewById(R.id.ed_txtHoTenTT);
        edt_ten_dang_nhap = view.findViewById(R.id.edt_ten_dang_nhap);
        edt_pass = view.findViewById(R.id.edt_pass);
        edt_confirm_pass = view.findViewById(R.id.edt_confirm_pass);
        btn_add_nhan_vien = view.findViewById(R.id.btn_add_nhan_vien);
        btn_cancel_nhan_vien = view.findViewById(R.id.btn_cancel_nhan_vien);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btn_add_nhan_vien.setOnClickListener(v -> {
            String hoTen = edt_ho_ten.getText().toString();
            String tenDangNhap = edt_ten_dang_nhap.getText().toString();
            String matKhau = edt_pass.getText().toString();
            String confirmMatKhau = edt_confirm_pass.getText().toString();

            if (hoTen.isEmpty() || tenDangNhap.isEmpty() || matKhau.isEmpty() || confirmMatKhau.isEmpty()) {
                Toast.makeText(getContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            } else {
                NhanVien nhanVien = new NhanVien();
                nhanVien.setHoTen(hoTen);
                nhanVien.setTenDangNhap(tenDangNhap);
                nhanVien.setMatKhau(matKhau);
                nhanVien.setRole(2);
                nhanVienDAO = new NhanVienDAO(getContext());
                long result = nhanVienDAO.themNhanVien(nhanVien);
                if (result > 0) {
                    Toast.makeText(getContext(), "Thêm nhân viên thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Thêm nhân viên thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_cancel_nhan_vien.setOnClickListener(v -> {
            edt_ho_ten.setText("");
            edt_ten_dang_nhap.setText("");
            edt_pass.setText("");
            edt_confirm_pass.setText("");
        });

    }
}