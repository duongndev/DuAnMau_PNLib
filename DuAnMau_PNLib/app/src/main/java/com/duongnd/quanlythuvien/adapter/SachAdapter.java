package com.duongnd.quanlythuvien.adapter;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.duongnd.quanlythuvien.R;
import com.duongnd.quanlythuvien.adapter.spinner.LoaiSachSpinner;
import com.duongnd.quanlythuvien.data.dao.LoaiSachDAO;
import com.duongnd.quanlythuvien.data.dao.SachDAO;
import com.duongnd.quanlythuvien.data.model.LoaiSach;
import com.duongnd.quanlythuvien.data.model.Sach;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SachAdapter extends RecyclerView.Adapter<SachAdapter.SachViewHolder> {

    Context context;
    List<Sach> listSach;
    ArrayList<LoaiSach> loaiSachsList;
    LoaiSachDAO loaiSachDAO;
    SachDAO sachDAO;
    int ms, mst;

    public SachAdapter(Context context, List<Sach> listSach, SachDAO sachDAO) {
        this.context = context;
        this.listSach = listSach;
        this.sachDAO = sachDAO;
    }

    @NonNull
    @Override
    public SachAdapter.SachViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sach, parent, false);
        return new SachViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SachAdapter.SachViewHolder holder, int position) {
        Sach sachs = listSach.get(position);
        holder.txt_ma_sach.setText("Ma sach: " + sachs.getMaSach() + "");
        holder.txt_ten_sach.setText("Ten sach: "+ sachs.getTenSach());
        holder.txt_tac_gia.setText("Tac gia: " + sachs.getTacGia());

        Locale locale = new Locale("vn", "VN");
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(locale);
        String tien = numberFormat.format(sachs.getGiaThue());
        holder.txt_gia_sach.setText("Gia thue: " + tien);

        String tenLoai;
        try {
            LoaiSachDAO loaiSachDao = new LoaiSachDAO(context);
            LoaiSach loaiSach = loaiSachDao.layLoaiSachTheoMa(Integer.parseInt(String.valueOf(sachs.getMaLoai())));
            tenLoai = loaiSach.getTenLoai();
        } catch (Exception e) {
            tenLoai = "Đã xóa loại sách";
        }

        holder.txt_ten_loai_sach.setText(tenLoai);



        holder.img_update_sach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogSach(sachs);
            }
        });

        holder.img_delete_sach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context)
                        .setTitle("Xoa sach")
                        .setMessage("Ban co muon xoa " + sachs.getTenSach() + " khong ?")
                        .setPositiveButton("Co", (dialog, which) -> {
                            sachDAO = new SachDAO(context);
                            int result = sachDAO.xoaSach(sachs.getMaLoai());
                            if (result > 0) {
                                listSach.clear();
                                listSach.addAll(sachDAO.getAllSach());
                                notifyDataSetChanged();
                                dialog.dismiss();
                                Toast.makeText(context, "Xoa thanh cong", Toast.LENGTH_SHORT).show();
                            } else {
                                dialog.dismiss();
                                Toast.makeText(context, "Xoa khong thanh cong", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("Khong", (dialog, which) -> {
                            dialog.dismiss();
                        });

                builder.show();
            }
        });

    }

    private void showDialogSach(Sach sach){
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_dialog_sach, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        TextView txt_title = (TextView) view.findViewById(R.id.txt_title_layout_them_sach);
        EditText edt_ten_sach = (EditText) view.findViewById(R.id.edt_ten_sach_layout_them_sach);
        EditText edt_gia_sach = (EditText) view.findViewById(R.id.edt_gia_sach_layout_them_sach);
        EditText edt_tac_gia = (EditText) view.findViewById(R.id.edt_tac_gia_sach_layout_them_sach);
        Spinner spinner_loai_sach = (Spinner) view.findViewById(R.id.spin_loai_sach_layout_them_sach);

        loaiSachsList = new ArrayList<>();
        loaiSachDAO = new LoaiSachDAO(view.getContext());
        loaiSachsList = (ArrayList<LoaiSach>) loaiSachDAO.layTatCaLoaiSach();
        LoaiSachSpinner loaiSachSpinner = new LoaiSachSpinner(context,loaiSachsList);
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
        txt_title.setText("Sua Sach");
        edt_ten_sach.setText(sach.getTenSach());
        edt_gia_sach.setText(Integer.toString(sach.getGiaThue()));
        edt_tac_gia.setText(sach.getTacGia());
        builder.setView(view);


        builder.setPositiveButton("Sua", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sach.setTenSach(edt_ten_sach.getText().toString());
                sach.setGiaThue(Integer.parseInt(edt_gia_sach.getText().toString()));
                sach.setTacGia(edt_tac_gia.getText().toString());
                sach.setMaLoai(ms);
                sachDAO = new SachDAO(context);
                sachDAO.suaSach(sach);
                listSach.clear();
                listSach.addAll(sachDAO.getAllSach());
                notifyDataSetChanged();
                dialog.dismiss();
                Toast.makeText(context, "Sua thanh cong", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Huy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });


        builder.create().show();
    }

    @Override
    public int getItemCount() {
        if (listSach != null) {
            return listSach.size();
        }
        return 0;
    }

    public static class SachViewHolder extends RecyclerView.ViewHolder {
        TextView txt_ma_sach, txt_ten_sach, txt_tac_gia, txt_gia_sach, txt_ten_loai_sach;
        ImageView img_delete_sach, img_update_sach;

        public SachViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_ma_sach = itemView.findViewById(R.id.txt_ma_sach);
            txt_ten_sach = itemView.findViewById(R.id.txt_ten_sach);
            txt_gia_sach = itemView.findViewById(R.id.txt_gia_sach);
            txt_tac_gia = itemView.findViewById(R.id.txt_tac_gia_sach);
            txt_ten_loai_sach = itemView.findViewById(R.id.txt_ten_loai_sach);
            img_update_sach = itemView.findViewById(R.id.img_edit_sach);
            img_delete_sach = itemView.findViewById(R.id.img_delete_sach);
        }
    }
}