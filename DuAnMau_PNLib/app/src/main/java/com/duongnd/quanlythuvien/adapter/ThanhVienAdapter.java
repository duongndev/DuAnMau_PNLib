package com.duongnd.quanlythuvien.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.duongnd.quanlythuvien.R;
import com.duongnd.quanlythuvien.data.dao.ThanhVienDAO;
import com.duongnd.quanlythuvien.data.model.ThanhVien;

import java.util.List;

public class ThanhVienAdapter extends RecyclerView.Adapter<ThanhVienAdapter.ThanhVienViewHolder> {
    Context context;
    List<ThanhVien> thanhVienList;
    ThanhVienDAO thanhVienDAO;

    public ThanhVienAdapter(Context context, List<ThanhVien> thanhVienList, ThanhVienDAO thanhVienDAO) {
        this.context = context;
        this.thanhVienList = thanhVienList;
        this.thanhVienDAO = thanhVienDAO;
    }

    @NonNull
    @Override
    public ThanhVienAdapter.ThanhVienViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_thanh_vien, null);
        return new ThanhVienAdapter.ThanhVienViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ThanhVienAdapter.ThanhVienViewHolder holder, int position) {
        ThanhVien thanhVien = thanhVienList.get(position);
        holder.txt_ma_tv.setText("Mã thành viên: " + thanhVien.getMaTV() + "");
        holder.txt_ten_tv.setText("Tên thành viên: " + thanhVien.getHoTen());
        holder.txt_nam_sinh.setText("Năm sinh: " + thanhVien.getNamSinh());
        holder.itemView.setOnClickListener(v -> {

        });
    }

    @Override
    public int getItemCount() {
        return thanhVienList.size();
    }

    public class ThanhVienViewHolder extends RecyclerView.ViewHolder {
        TextView txt_ma_tv, txt_ten_tv, txt_nam_sinh;

        public ThanhVienViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_ma_tv = itemView.findViewById(R.id.txt_ma_thanh_vien);
            txt_ten_tv = itemView.findViewById(R.id.txt_ten_thanh_vien);
            txt_nam_sinh = itemView.findViewById(R.id.txt_nam_sinh_thanh_vien);
        }
    }
}