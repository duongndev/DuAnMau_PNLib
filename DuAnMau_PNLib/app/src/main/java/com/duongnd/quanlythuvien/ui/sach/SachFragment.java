package com.duongnd.quanlythuvien.ui.sach;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.duongnd.quanlythuvien.R;
import com.duongnd.quanlythuvien.adapter.LoaiSachAdapter;
import com.duongnd.quanlythuvien.adapter.SachAdapter;
import com.duongnd.quanlythuvien.data.dao.LoaiSachDAO;
import com.duongnd.quanlythuvien.data.dao.SachDAO;
import com.duongnd.quanlythuvien.data.model.Sach;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class SachFragment extends Fragment {

    RecyclerView recyclerView_sach;
    FloatingActionButton btn_add_sach;
    SachDAO sachDAO;
    SachAdapter sachAdapter;
    List<Sach> sachList;

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
}