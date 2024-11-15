package com.duongnd.quanlythuvien.ui.sach;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.duongnd.quanlythuvien.R;
import com.duongnd.quanlythuvien.adapter.SachAdapter;
import com.duongnd.quanlythuvien.adapter.spinner.LoaiSachSpinner;
import com.duongnd.quanlythuvien.data.dao.LoaiSachDAO;
import com.duongnd.quanlythuvien.data.dao.SachDAO;
import com.duongnd.quanlythuvien.data.model.LoaiSach;
import com.duongnd.quanlythuvien.data.model.Sach;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class SachFragment extends Fragment {

    RecyclerView recyclerView_sach;
    FloatingActionButton btn_add_sach;
    SachDAO sachDAO;
    LoaiSachDAO loaiSachDAO;
    LoaiSachSpinner loaiSachSpinner;
    SachAdapter sachAdapter;
    List<Sach> sachList;
    List<LoaiSach> loaiSachsList;
    int ms, mst;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sach, container, false);
        recyclerView_sach = view.findViewById(R.id.recyclerView_sach);
        btn_add_sach = view.findViewById(R.id.them_sach);
        recyclerView_sach.setHasFixedSize(true);

        //Chèn một kẻ ngang giữa các phần tử
        DividerItemDecoration dividerHorizontal = new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL);
        recyclerView_sach.addItemDecoration(dividerHorizontal);

        sachDAO = new SachDAO(requireContext());
        sachList = new ArrayList<>();
        sachList = sachDAO.getAllSach();
        sachAdapter = new SachAdapter(requireContext(), sachList, sachDAO);
        recyclerView_sach.setAdapter(sachAdapter);
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btn_add_sach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogThemSach(v);
            }
        });
    }

    private void dialogThemSach(View v) {
        Sach sach = new Sach();
        LayoutInflater inflater = LayoutInflater.from(requireContext());
        View view = inflater.inflate(R.layout.layout_dialog_sach, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        TextView txt_title = (TextView) view.findViewById(R.id.txt_title_layout_them_sach);
        EditText edt_ten_sach = (EditText) view.findViewById(R.id.edt_ten_sach_layout_them_sach);
        EditText edt_gia_sach = (EditText) view.findViewById(R.id.edt_gia_sach_layout_them_sach);
        EditText edt_nam_xuat_ban = (EditText) view.findViewById(R.id.edt_nam_xuat_ban_layout_them_sach);
        Spinner spinner_loai_sach = (Spinner) view.findViewById(R.id.spin_loai_sach_layout_them_sach);

        loaiSachsList = new ArrayList<>();
        loaiSachDAO = new LoaiSachDAO(requireContext());
        loaiSachsList = loaiSachDAO.layTatCaLoaiSach();
        LoaiSachSpinner loaiSachSpinner = new LoaiSachSpinner(requireContext(), (ArrayList<LoaiSach>) loaiSachsList);
        spinner_loai_sach.setAdapter(loaiSachSpinner);

        spinner_loai_sach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ms = loaiSachsList.get(position).getMaLoai();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mst = 0;
        for (int i = 0; i < loaiSachsList.size(); i++) {
            if (loaiSachsList.get(i).getMaLoai() == sach.getMaLoai()) {
                mst = i;
                break;
            }
        }
        spinner_loai_sach.setSelection(mst);
        txt_title.setText("Thêm sách");
        builder.setView(view);

        builder.setPositiveButton("Thêm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sach.setTenSach(edt_ten_sach.getText().toString());
                sach.setGiaThue(Integer.parseInt(edt_gia_sach.getText().toString()));
                sach.setNamXuatBan(Integer.parseInt(edt_nam_xuat_ban.getText().toString()));
                sach.setMaLoai(ms);
                sachDAO = new SachDAO(requireContext());
                long result = sachDAO.themSach(sach);
                Log.d("TAG", "onClick them sach: " + result + "");
                if (result > 0) {
                    sachList.clear();
                    sachList.addAll(sachDAO.getAllSach());
                    sachAdapter.notifyDataSetChanged();
                    dialog.dismiss();
                    Toast.makeText(requireContext(), "Them thanh cong", Toast.LENGTH_SHORT).show();
                } else {
                    dialog.dismiss();
                    Toast.makeText(requireContext(), "Them that bai", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}