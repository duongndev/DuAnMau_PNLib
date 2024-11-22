package com.duongnd.quanlythuvien.ui.phieumuon;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.duongnd.quanlythuvien.R;
import com.duongnd.quanlythuvien.adapter.PhieuMuonAdapter;
import com.duongnd.quanlythuvien.adapter.spinner.SachSpinner;
import com.duongnd.quanlythuvien.adapter.spinner.ThanhVienSpinner;
import com.duongnd.quanlythuvien.data.dao.PhieuMuonDAO;
import com.duongnd.quanlythuvien.data.dao.SachDAO;
import com.duongnd.quanlythuvien.data.dao.ThanhVienDAO;
import com.duongnd.quanlythuvien.data.model.NhanVien;
import com.duongnd.quanlythuvien.data.model.PhieuMuon;
import com.duongnd.quanlythuvien.data.model.Sach;
import com.duongnd.quanlythuvien.data.model.ThanhVien;
import com.duongnd.quanlythuvien.utils.PhieuMuonInterface;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PhieuMuonFragment extends Fragment {

    RecyclerView recyclerView_phieu_muon;
    FloatingActionButton btn_them_phieu_muon;

    PhieuMuonDAO phieuMuonDAO;
    PhieuMuonAdapter phieuMuonAdapter;
    List<PhieuMuon> phieuMuonList;

    SachSpinner sachSpinner;
    List<Sach> sachList;
    SachDAO sachDAO;

    ThanhVienSpinner thanhVienSpinner;
    List<ThanhVien> thanhVienList;
    ThanhVienDAO thanhVienDAO;
    Calendar calendar;
    int maSach, maTV, role, giaThue;


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_phieu_muon, container, false);
        recyclerView_phieu_muon = view.findViewById(R.id.recyclerView_phieu_muon);
        btn_them_phieu_muon = view.findViewById(R.id.them_phieu_muon);

        phieuMuonList = new ArrayList<>();
        phieuMuonDAO = new PhieuMuonDAO(requireContext());
        phieuMuonList = phieuMuonDAO.layTatCaPhieuMuon();
        phieuMuonAdapter = new PhieuMuonAdapter(requireContext(), phieuMuonList, phieuMuonDAO);
        recyclerView_phieu_muon.setAdapter(phieuMuonAdapter);
        calendar = Calendar.getInstance();
        NhanVien nhanVien = (NhanVien) requireActivity().getIntent().getSerializableExtra("nhanVien");
        role = nhanVien.getRole();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btn_them_phieu_muon.setOnClickListener(v -> {
            showDialogThem();
        });

        phieuMuonAdapter.setClickPhieuMuonInterface(new PhieuMuonInterface() {
            @Override
            public void clickPhieuMuon(View view, PhieuMuon phieuMuon) {
                showPopupMenu(view, phieuMuon);
            }

        });
    }

    private void showPopupMenu(View view, PhieuMuon phieuMuon) {
        PopupMenu popupMenu = new PopupMenu(requireContext(), view);
        popupMenu.inflate(R.menu.option_menu);
        popupMenu.setOnMenuItemClickListener(menuItem -> {
            if (menuItem.getItemId() == R.id.item_menu_sua) {
                showDialogSua(phieuMuon);
                return true;
            } else if (menuItem.getItemId() == R.id.item_menu_xoa) {
                showDialogXoa(phieuMuon);
                return true;
            } else {
                return false;
            }
        });
        popupMenu.show();
    }

    private void showDialogThem() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog_phieu_muon, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();

        TextView txt_title = view.findViewById(R.id.txt_title_phieu_muon);
        txt_title.setText("Thêm Phiếu Mượn");
        Button btn_add = view.findViewById(R.id.btn_add_phieu_muon);
        Button btn_cancel = view.findViewById(R.id.btn_cancel_phieu_muon);
        Spinner spn_thanh_vien = view.findViewById(R.id.spn_thanh_vien);
        Spinner spn_sach = view.findViewById(R.id.spn_sach);
        TextView txt_ngay_muon = view.findViewById(R.id.txt_ngay_muon);
        TextView txt_ngay_tra = view.findViewById(R.id.txt_ngay_tra);

        // spinner sach
        setSachSpinner(spn_sach);
        spn_sach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                maSach = sachList.get(position).getMaSach();
                giaThue = sachList.get(position).getGiaThue();
                Log.d("TAG", "onItemSelected: " + maSach);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        // spinner thanh vien
        setThanhVienSpinner(spn_thanh_vien);
        spn_thanh_vien.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                maTV = thanhVienList.get(position).getMaTV();
                Log.d("TAG", "onItemSelected: " + maTV);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        // them ngay muon
        txt_ngay_muon.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    requireContext(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                    String ngay = "";
                    String thang = "";
                    if (i2 < 10) {
                        ngay = "0" + i2;
                    } else {
                        ngay = String.valueOf(i2);
                    }

                    if ((i1 + 1) < 10) {
                        thang = "0" + (i1 + 1);
                    } else {
                        thang = String.valueOf(i1 + 1);
                    }
                    txt_ngay_muon.setText(ngay + "/" + thang + "/" + i);
                }
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.show();
        });

        // them ngay tra
        txt_ngay_tra.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                    String ngay = "";
                    String thang = "";
                    if (i2 < 10) {
                        ngay = "0" + i2;
                    } else {
                        ngay = String.valueOf(i2);
                    }

                    if ((i1 + 1) < 10) {
                        thang = "0" + (i1 + 1);
                    } else {
                        thang = String.valueOf(i1 + 1);
                    }
                    txt_ngay_tra.setText(ngay + "/" + thang + "/" + i);
                }
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.show();
        });


        // btn them phieu muon
        btn_add.setOnClickListener(v -> {
            if (validateNgay(txt_ngay_muon.getText().toString(), txt_ngay_tra.getText().toString())) {
                Log.d("TAG", "showDialog: ma sach " + maSach + "\ngia thue " + giaThue + "\nma thanh vien " + maTV + "\nngay muon " + txt_ngay_muon.getText().toString() + "\nngay tra " + txt_ngay_tra.getText().toString() + "\nma nhan vien " + role);
                addPhieuMuon(role, maSach, giaThue, maTV, txt_ngay_muon.getText().toString(), txt_ngay_tra.getText().toString());
                dialog.dismiss();
            } else {
                Toast.makeText(requireContext(), "Ngay muon hoac ngay tra khong hop le", Toast.LENGTH_SHORT).show();
            }
        });

        // btn huy
        btn_cancel.setOnClickListener(v -> {
            dialog.dismiss();
        });

    }


    private void showDialogSua(PhieuMuon phieuMuon) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog_phieu_muon, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();

        TextView txt_title = view.findViewById(R.id.txt_title_phieu_muon);
        txt_title.setText("Sửa Phiếu Mượn");
        Button btn_add = view.findViewById(R.id.btn_add_phieu_muon);
        Button btn_cancel = view.findViewById(R.id.btn_cancel_phieu_muon);
        Spinner spn_thanh_vien = view.findViewById(R.id.spn_thanh_vien);
        Spinner spn_sach = view.findViewById(R.id.spn_sach);
        TextView txt_ngay_muon = view.findViewById(R.id.txt_ngay_muon);
        TextView txt_ngay_tra = view.findViewById(R.id.txt_ngay_tra);

        txt_ngay_muon.setText(phieuMuon.getNgayMuon());
        txt_ngay_tra.setText(phieuMuon.getNgayTra());

        // spinner sach
        setSachSpinner(spn_sach);
        spn_sach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                maSach = sachList.get(position).getMaSach();
                giaThue = sachList.get(position).getGiaThue();
                Log.d("TAG", "onItemSelected: " + maSach);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // spinner thanh vien
        setThanhVienSpinner(spn_thanh_vien);
        spn_thanh_vien.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                maTV = thanhVienList.get(position).getMaTV();
                Log.d("TAG", "onItemSelected: " + maTV);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        // them ngay muon
        txt_ngay_muon.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    requireContext(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                    String ngay = "";
                    String thang = "";
                    if (i2 < 10) {
                        ngay = "0" + i2;
                    } else {
                        ngay = String.valueOf(i2);
                    }

                    if ((i1 + 1) < 10) {
                        thang = "0" + (i1 + 1);
                    } else {
                        thang = String.valueOf(i1 + 1);
                    }
                    txt_ngay_muon.setText(ngay + "/" + thang + "/" + i);
                }
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.show();
        });

        // them ngay tra
        txt_ngay_tra.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                    String ngay = "";
                    String thang = "";
                    if (i2 < 10) {
                        ngay = "0" + i2;
                    } else {
                        ngay = String.valueOf(i2);
                    }

                    if ((i1 + 1) < 10) {
                        thang = "0" + (i1 + 1);
                    } else {
                        thang = String.valueOf(i1 + 1);
                    }
                    txt_ngay_tra.setText(ngay + "/" + thang + "/" + i);
                }
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.show();
        });


        // btn sua phieu muon
        btn_add.setText("Sửa");
        btn_add.setOnClickListener(v -> {
            if (validateNgay(txt_ngay_muon.getText().toString(), txt_ngay_tra.getText().toString())) {
                Log.d("TAG", "showDialog: ma sach " + maSach + "\ngia thue " + giaThue + "\nma thanh vien " + maTV + "\nngay muon " + txt_ngay_muon.getText().toString() + "\nngay tra " + txt_ngay_tra.getText().toString() + "\nma nhan vien " + role);
                addPhieuMuon(role, maSach, giaThue, maTV, txt_ngay_muon.getText().toString(), txt_ngay_tra.getText().toString());
                dialog.dismiss();
            } else {
                Toast.makeText(requireContext(), "Ngay muon hoac ngay tra khong hop le", Toast.LENGTH_SHORT).show();
            }
        });

        // btn huy
        btn_cancel.setOnClickListener(v -> {
            dialog.dismiss();
        });

    }


    private void showDialogXoa(PhieuMuon phieuMuon) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Xóa phiếu mượn");
        builder.setMessage("Bạn có chắc muốn xóa phiếu mượn " + phieuMuon.getMaPM() + " không?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                long kq = phieuMuonDAO.xoaPhieuMuon(phieuMuon.getMaPM());
                if (kq > 0) {
                    Toast.makeText(requireContext(), "Xoa phieu muon thanh cong", Toast.LENGTH_SHORT).show();
                    loadPhieuMuon();
                    dialog.dismiss();
                } else {
                    Toast.makeText(requireContext(), "Xoa phieu muon that bai", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }
        });
        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }


    // kiem tra xem chuoi ngay co hop le khong va kiem tra ngay tra co lon hon ngay muon khong
    private boolean validateNgay(String ngayMuon, String ngayTra) {
        if (!ngayMuon.isEmpty() && !ngayTra.isEmpty()) {
            Date ngayMuonDate = null;
            Date ngayTraDate = null;
            try {
                ngayMuonDate = new SimpleDateFormat("dd/MM/yyyy").parse(ngayMuon);
                ngayTraDate = new SimpleDateFormat("dd/MM/yyyy").parse(ngayTra);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (ngayTraDate != null) {
                return ngayTraDate.compareTo(ngayMuonDate) > 0;
            }
        }
        return false;
    }

    private void addPhieuMuon(int maNV, int maSach, int giaThue, int maTV, String ngayMuon, String ngayTra) {
        PhieuMuon phieuMuon = new PhieuMuon();
        phieuMuon.setMaNV(maNV);
        phieuMuon.setMaSach(maSach);
        phieuMuon.setTienThue(giaThue);
        phieuMuon.setMaTV(maTV);
        phieuMuon.setNgayMuon(ngayMuon);
        phieuMuon.setNgayTra(ngayTra);
        phieuMuon.setTraSach(0);
        long result = phieuMuonDAO.themPhieuMuon(phieuMuon);
        if (result > 0) {
            Toast.makeText(requireContext(), "Them phieu muon thanh cong", Toast.LENGTH_SHORT).show();
            Log.d("TAG", "addPhieuMuon: " + phieuMuon.getMaPM());
            loadPhieuMuon();
        } else {
            Toast.makeText(requireContext(), "Them phieu muon that bai", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadPhieuMuon() {
        phieuMuonList.clear();
        phieuMuonList.addAll(phieuMuonDAO.layTatCaPhieuMuon());
        phieuMuonAdapter.notifyDataSetChanged();
    }

    private void setSachSpinner(Spinner sp_sach) {
        sachDAO = new SachDAO(requireContext());
        sachList = new ArrayList<>();
        sachList = sachDAO.getAllSach();
        sachSpinner = new SachSpinner(requireContext(), (ArrayList<Sach>) sachList);
        sp_sach.setAdapter(sachSpinner);
    }

    private void setThanhVienSpinner(Spinner sp_thanh_vien) {
        thanhVienDAO = new ThanhVienDAO(requireContext());
        thanhVienList = new ArrayList<>();
        thanhVienList = thanhVienDAO.getAllThanhVien();
        thanhVienSpinner = new ThanhVienSpinner(requireContext(), (ArrayList<ThanhVien>) thanhVienList);
        sp_thanh_vien.setAdapter(thanhVienSpinner);
    }


}