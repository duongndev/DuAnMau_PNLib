package com.duongnd.quanlythuvien.adapter.spinner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.duongnd.quanlythuvien.R;
import com.duongnd.quanlythuvien.data.model.ThanhVien;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ThanhVienSpinner extends ArrayAdapter<ThanhVien> {

    Context context;
    ArrayList<ThanhVien> thanhViensList;


    public ThanhVienSpinner(@NonNull Context context, ArrayList<ThanhVien> thanhViensList) {
        super(context, 0, thanhViensList);
        this.context = context;
        this.thanhViensList = thanhViensList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.custom_spinner, null);
        }
        ThanhVien thanhVien = thanhViensList.get(position);
        if (thanhVien != null) {
            TextView tv_ls = view.findViewById(R.id.txt_item_spinner);
            tv_ls.setText(thanhVien.getHoTen());
        }
        return view;
    }

    @Override
    public View getDropDownView(int position, @Nullable @org.jetbrains.annotations.Nullable View convertView, @NonNull @NotNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.custom_spinner, null);
        }
        ThanhVien thanhVien = thanhViensList.get(position);
        if (thanhVien != null) {
            TextView tv_ls = view.findViewById(R.id.txt_item_spinner);
            tv_ls.setText(thanhVien.getHoTen());
        }
        return view;

    }

}