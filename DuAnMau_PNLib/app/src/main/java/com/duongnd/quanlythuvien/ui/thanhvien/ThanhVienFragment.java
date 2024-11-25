package com.duongnd.quanlythuvien.ui.thanhvien;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.duongnd.quanlythuvien.R;
import com.duongnd.quanlythuvien.adapter.ThanhVienAdapter;
import com.duongnd.quanlythuvien.data.dao.ThanhVienDAO;
import com.duongnd.quanlythuvien.data.model.ThanhVien;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ThanhVienFragment extends Fragment {

    ThanhVienDAO thanhVienDAO;
    RecyclerView recyclerView_thanh_vien;
    FloatingActionButton btn_add_thanh_vien;
    ThanhVienAdapter thanhVienAdapter;
    List<ThanhVien> thanhVienList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_thanh_vien, container, false);
        recyclerView_thanh_vien = view.findViewById(R.id.recyclerView_thanh_vien);
        btn_add_thanh_vien = view.findViewById(R.id.them_thanh_vien);
        recyclerView_thanh_vien.setHasFixedSize(true);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadData();
        btn_add_thanh_vien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogThemThanhVien();
            }
        });
    }

    private void dialogThemThanhVien() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog_thanh_vien, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();

        TextView txt_title = view.findViewById(R.id.txt_title_dialog_thanh_vien);
        txt_title.setText("Thêm Thanh viên");
        Button btn_add = view.findViewById(R.id.btn_item_thanh_vien);
        Button btn_cancel = view.findViewById(R.id.btn_cancel_item_thanh_vien);
        EditText edt_ten_thanh_vien = view.findViewById(R.id.edt_dialog_ten_thanh_vien);
        EditText edt_nam_sinh = view.findViewById(R.id.edt_dialog_nam_sinh_thanh_vien);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenThanhVien = edt_ten_thanh_vien.getText().toString().trim();
                String namSinh = edt_nam_sinh.getText().toString().trim();
                if (tenThanhVien.isEmpty() || namSinh.isEmpty()) {
                    Toast.makeText(getContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    themThanhVien(tenThanhVien, Integer.parseInt(namSinh));
                    dialog.dismiss();
                }
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }

    private void themThanhVien(String hoTen, int namSinh){
        ThanhVien thanhVien = new ThanhVien();
        thanhVien.setHoTen(hoTen);
        thanhVien.setNamSinh(namSinh);
       long result = thanhVienDAO.themThanhVien(thanhVien);
        if (result > 0){
            Toast.makeText(getContext(), "Thêm thanh viên thành công", Toast.LENGTH_SHORT).show();
            loadData();
        } else {
            Toast.makeText(getContext(), "Them thanh vien that bai", Toast.LENGTH_SHORT).show();
        }

    }


    private void loadData() {
        thanhVienDAO = new ThanhVienDAO(getContext());
        thanhVienList = thanhVienDAO.getAllThanhVien();
        thanhVienAdapter = new ThanhVienAdapter(requireContext(), thanhVienList, thanhVienDAO);
        recyclerView_thanh_vien.setAdapter(thanhVienAdapter);
        thanhVienAdapter.notifyDataSetChanged();
    }
}