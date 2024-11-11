package com.duongnd.quanlythuvien.ui.theloai;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.duongnd.quanlythuvien.R;
import com.duongnd.quanlythuvien.adapter.LoaiSachAdapter;
import com.duongnd.quanlythuvien.data.dao.LoaiSachDAO;
import com.duongnd.quanlythuvien.data.model.LoaiSach;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class TheLoaiFragment extends Fragment {


    RecyclerView recyclerView_loai_sach;
    FloatingActionButton btn_add_loai_sach;
    LoaiSachDAO loaiSachDAO;
    LoaiSachAdapter loaiSachAdapter;
    List<LoaiSach> loaiSachList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_the_loai, container, false);
        recyclerView_loai_sach = view.findViewById(R.id.recyclerView_loai_sach);
        btn_add_loai_sach = view.findViewById(R.id.add_loai_sach);
        recyclerView_loai_sach.setHasFixedSize(true);

        //Chèn một kẻ ngang giữa các phần tử
        DividerItemDecoration dividerHorizontal = new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL);
        recyclerView_loai_sach.addItemDecoration(dividerHorizontal);

        loaiSachDAO = new LoaiSachDAO(requireContext());
        loaiSachList = new ArrayList<>();
        loaiSachList = loaiSachDAO.layTatCaLoaiSach();
        loaiSachAdapter = new LoaiSachAdapter(requireContext(), loaiSachList, loaiSachDAO);
        recyclerView_loai_sach.setAdapter(loaiSachAdapter);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        btn_add_loai_sach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogThemLoaiSach(v);
            }
        });

    }

    private void dialogThemLoaiSach(View view) {
        LayoutInflater inflater = LayoutInflater.from(requireContext());
        view = inflater.inflate(R.layout.layout_loai_sach, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setView(view);
        TextView tv_title_dialog_loai_sach = view.findViewById(R.id.tv_title_dialog_loai_sach);
        EditText edt_ten_loai_sach = view.findViewById(R.id.edt_dialog_ten_loai_sach);
        EditText edt_mo_ta = view.findViewById(R.id.edt_dialog_mo_ta_loai_sach);
        builder.setCancelable(true);
        tv_title_dialog_loai_sach.setText("Them loai sach");
        builder.setPositiveButton("Thêm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String tenLoai = edt_ten_loai_sach.getText().toString();
                String moTai = edt_mo_ta.getText().toString();
                if (tenLoai.isEmpty() || moTai.isEmpty()) {
                    Toast.makeText(getContext(), "Vui long nhap day du thong tin", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    LoaiSach loaiSach = new LoaiSach();
                    loaiSach.setTenLoai(tenLoai);
                    loaiSach.setMoTa(moTai);
                    loaiSachDAO.themLoaiSach(loaiSach);
                    getAllLoaiSach();
                }

            }
        });

        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    private void dialogSuaLoaiSach(LoaiSach loaiSach) {
        LayoutInflater inflater = LayoutInflater.from(requireContext());
        View view = inflater.inflate(R.layout.layout_loai_sach, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setView(view);
        TextView tv_title_dialog_loai_sach = (TextView) view.findViewById(R.id.tv_title_dialog_loai_sach);
        EditText edt_ten_loai_sach = (EditText) view.findViewById(R.id.edt_dialog_ten_loai_sach);
        EditText edt_mo_ta = (EditText) view.findViewById(R.id.edt_dialog_mo_ta_loai_sach);
        tv_title_dialog_loai_sach.setText("Sua loai sach");
        edt_ten_loai_sach.setText(loaiSach.getTenLoai());
        edt_mo_ta.setText(loaiSach.getMoTa());
        builder.setCancelable(true);
        builder.setPositiveButton("Sua", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String tenLoai = edt_ten_loai_sach.getText().toString();
                String moTai = edt_mo_ta.getText().toString();
                if (tenLoai.isEmpty() || moTai.isEmpty()) {
                    Toast.makeText(getContext(), "Vui long nhap day du thong tin", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    loaiSach.setTenLoai(tenLoai);
                    loaiSach.setMoTa(moTai);
                    loaiSachDAO.suaLoaiSach(loaiSach);
                    loaiSachAdapter.notifyDataSetChanged();
                    getAllLoaiSach();
                    Toast.makeText(getContext(), "Sua thanh cong", Toast.LENGTH_SHORT).show();
                }

            }
        });

        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    private void getAllLoaiSach() {
        loaiSachList = loaiSachDAO.layTatCaLoaiSach();
        loaiSachAdapter = new LoaiSachAdapter(requireContext(), loaiSachList, loaiSachDAO);
        recyclerView_loai_sach.setAdapter(loaiSachAdapter);
    }
}