package com.duongnd.quanlythuvien.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.duongnd.quanlythuvien.R;
import com.duongnd.quanlythuvien.data.dao.PhieuMuonDAO;
import com.duongnd.quanlythuvien.data.dao.SachDAO;
import com.duongnd.quanlythuvien.data.dao.ThanhVienDAO;
import com.duongnd.quanlythuvien.data.model.PhieuMuon;
import com.duongnd.quanlythuvien.data.model.Sach;

import java.util.List;

public class PhieuMuonAdapter extends RecyclerView.Adapter<PhieuMuonAdapter.PhieuMuonViewHolder> {

    Context context;
    List<PhieuMuon> phieuMuonList;
    PhieuMuonDAO phieuMuonDAO;
    SachDAO sachDAO;
    ThanhVienDAO thanhVienDAO;

    public PhieuMuonAdapter(Context context, List<PhieuMuon> phieuMuonList, PhieuMuonDAO phieuMuonDAO) {
        this.context = context;
        this.phieuMuonList = phieuMuonList;
        this.phieuMuonDAO = phieuMuonDAO;
    }

    @NonNull
    @Override
    public PhieuMuonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_phieu_muon, parent, false);
        return new PhieuMuonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhieuMuonViewHolder holder, int position) {
        PhieuMuon phieuMuon = phieuMuonList.get(position);
        holder.txt_ma_phieu_muon.setText("Mã phiếu mượn: " + phieuMuon.getMaPM() + "");
        holder.txt_ngay_muon_sach.setText(phieuMuon.getNgayMuon());
        holder.txt_ngay_tra_sach.setText(phieuMuon.getNgayTra());
        holder.txt_item_gia_thue.setText("Tiền thuê: " + phieuMuon.getTienThue());

        String tenThanhVien;
        try {
            thanhVienDAO = new ThanhVienDAO(context);
            thanhVienDAO.getThanhVien(phieuMuon.getMaTV());
            tenThanhVien = thanhVienDAO.getThanhVien(phieuMuon.getMaTV()).getHoTen();
        } catch (Exception e) {
            tenThanhVien = "Đã xoá thành viên";
        }
        holder.txt_item_ten_nguoi_muon.setText("Người mượn: " + tenThanhVien);

        String tenSach;
        try {
            sachDAO = new SachDAO(context);
            Sach sach = sachDAO.getSach(phieuMuon.getMaSach());
            tenSach = sach.getTenSach();
        } catch (Exception e) {
            tenSach = "Đã xoá sách";
        }

        holder.txt_item_ten_sach.setText("Tên sách: " + tenSach);


        if (phieuMuon.getTrangThai() == 0) {
            holder.txt_trang_thai_phieu_muon.setText("Chưa trả sách");
            holder.txt_trang_thai_phieu_muon.setTextColor(context.getResources().getColor(R.color.red));
            holder.btn_tra_sach.setVisibility(View.VISIBLE);
        } else {
            holder.txt_trang_thai_phieu_muon.setText("Đã trả sách");
            holder.txt_trang_thai_phieu_muon.setTextColor(context.getResources().getColor(R.color.green));
            holder.btn_tra_sach.setVisibility(View.GONE);
        }


        holder.btn_tra_sach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               phieuMuonDAO = new PhieuMuonDAO(context);
               boolean checkTraSach = phieuMuonDAO.thayDoiTrangThai(phieuMuonList.get(holder.getAdapterPosition()).getMaPM());
                if (checkTraSach) {
                    phieuMuonList.clear();
                    phieuMuonList = phieuMuonDAO.getAllPhieuMuon();
                    notifyDataSetChanged();
                } else {
                    Toast.makeText(context, "Tra sach that bai", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return phieuMuonList.size();
    }

    public class PhieuMuonViewHolder extends RecyclerView.ViewHolder {
        TextView txt_ma_phieu_muon, txt_ngay_muon_sach, txt_ngay_tra_sach, txt_trang_thai_phieu_muon, txt_item_ten_nguoi_muon, txt_item_ten_sach, txt_item_gia_thue;
        Button btn_tra_sach;

        public PhieuMuonViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_ma_phieu_muon = itemView.findViewById(R.id.txt_item_ma_phieu_muon);
            txt_ngay_muon_sach = itemView.findViewById(R.id.txt_item_ngay_muon_sach);
            txt_ngay_tra_sach = itemView.findViewById(R.id.txt_item_ngay_tra_sach);
            txt_trang_thai_phieu_muon = itemView.findViewById(R.id.txt_item_trang_thai_phieu_muon);
            txt_item_ten_nguoi_muon = itemView.findViewById(R.id.txt_item_ten_nguoi_muon);
            txt_item_ten_sach = itemView.findViewById(R.id.txt_item_ten_sach_muon);
            txt_item_gia_thue = itemView.findViewById(R.id.txt_item_gia_muon);
            btn_tra_sach = itemView.findViewById(R.id.btn_tra_sach);
        }
    }
}