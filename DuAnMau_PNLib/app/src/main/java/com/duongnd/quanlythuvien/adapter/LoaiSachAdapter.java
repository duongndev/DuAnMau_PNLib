package com.duongnd.quanlythuvien.adapter;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.duongnd.quanlythuvien.R;
import com.duongnd.quanlythuvien.data.dao.LoaiSachDAO;
import com.duongnd.quanlythuvien.data.model.LoaiSach;
import com.duongnd.quanlythuvien.utils.LoaiSachInterface;

import java.util.List;

public class LoaiSachAdapter extends RecyclerView.Adapter<LoaiSachAdapter.LoaiSachViewHolder> {

    Context context;
    List<LoaiSach> listLoaiSach;
    LoaiSachDAO loaiSachDAO;
    LoaiSachInterface loaiSachInterface;

    public void setClickLoaiSachInterface(LoaiSachInterface loaiSachInterface) {
        this.loaiSachInterface = loaiSachInterface;
    }


    public LoaiSachAdapter(Context context, List<LoaiSach> listLoaiSach, LoaiSachDAO loaiSachDAO) {
        this.context = context;
        this.listLoaiSach = listLoaiSach;
        this.loaiSachDAO = loaiSachDAO;
    }


    @NonNull
    @Override
    public LoaiSachViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loai_sach, parent, false);
        return new LoaiSachViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull LoaiSachViewHolder holder, int position) {

        LoaiSach loaiSach = listLoaiSach.get(position);
        if (loaiSach == null) {
            return;
        } else {
            holder.txt_ma_loai_sach.setText("Mã loại sách:" + loaiSach.getMaLoai());
            holder.txt_ten_loai_sach.setText("Tên loại sách: " + loaiSach.getTenLoai());
        }

        holder.img_delete_loai_sach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context)
                        .setTitle("Xoa sach")
                        .setMessage("Ban co muon xoa " + loaiSach.getTenLoai() + " khong ?")
                        .setPositiveButton("Co", (dialog, which) -> {
                            loaiSachDAO = new LoaiSachDAO(context);
                            int result = loaiSachDAO.xoaLoaiSach(loaiSach.getMaLoai());
                            switch (result) {
                                case -1:
                                    dialog.dismiss();
                                    Toast.makeText(context, "Xoa khong thanh cong", Toast.LENGTH_SHORT).show();
                                    break;
                                case 0:
                                    dialog.dismiss();
                                    Toast.makeText(context, "Ban can xoa cac cuon sach trong the loai nay truoc", Toast.LENGTH_SHORT).show();
                                    break;
                                case 1:
                                    listLoaiSach.clear();
                                    listLoaiSach.addAll(loaiSachDAO.layTatCaLoaiSach());
                                    notifyDataSetChanged();
                                    dialog.dismiss();
                                    Toast.makeText(context, "Xoa thanh cong", Toast.LENGTH_SHORT).show();
                                    break;

                            }
                        })
                        .setNegativeButton("Khong", (dialog, which) -> {
                            dialog.dismiss();
                        });

                builder.show();
            }
        });

        holder.img_update_loai_sach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogLoaiSach(loaiSach);
            }
        });



    }

    @Override
    public int getItemCount() {
        if (listLoaiSach != null) {
            return listLoaiSach.size();
        }
        return 0;
    }

    private void showDialogLoaiSach(LoaiSach loaiSach) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_dialog_loai_sach, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(view);
        TextView tv_title_dialog_loai_sach = (TextView) view.findViewById(R.id.tv_title_dialog_loai_sach);
        EditText edt_ten_loai_sach = (EditText) view.findViewById(R.id.edt_dialog_ten_loai_sach);
        tv_title_dialog_loai_sach.setText("Sua loai sach");
        edt_ten_loai_sach.setText(loaiSach.getTenLoai());
        builder.setCancelable(true);
        builder.setPositiveButton("Sua", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String tenLoai = edt_ten_loai_sach.getText().toString().trim();

                if (tenLoai.isEmpty()) {
                    Toast.makeText(context, "Vui long nhap day du thong tin", Toast.LENGTH_SHORT).show();
                } else {
                    loaiSachDAO = new LoaiSachDAO(context);
                    loaiSach.setTenLoai(tenLoai);
                    long result = loaiSachDAO.suaLoaiSach(loaiSach);
                    if (result > 0) {
                        listLoaiSach.clear();
                        listLoaiSach.addAll(loaiSachDAO.layTatCaLoaiSach());
                        notifyDataSetChanged();
                        dialog.dismiss();
                        Toast.makeText(context, "Cap nhat thanh cong", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Cap nhat that bai", Toast.LENGTH_SHORT).show();
                    }
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

    public static class LoaiSachViewHolder extends RecyclerView.ViewHolder {
        TextView txt_ma_loai_sach, txt_ten_loai_sach;
        ImageView img_delete_loai_sach, img_update_loai_sach;

        public LoaiSachViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_ma_loai_sach = (TextView) itemView.findViewById(R.id.tv_ma_loai_sach);
            txt_ten_loai_sach = (TextView) itemView.findViewById(R.id.tv_ten_loai_sach);
            img_delete_loai_sach = (ImageView) itemView.findViewById(R.id.img_delete_loai_sach);
            img_update_loai_sach = (ImageView) itemView.findViewById(R.id.img_edit_loai_sach);
        }
    }
}