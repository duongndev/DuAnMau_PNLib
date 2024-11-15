package com.duongnd.quanlythuvien.ui.phieumuon;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.duongnd.quanlythuvien.R;
import com.duongnd.quanlythuvien.adapter.PhieuMuonAdapter;
import com.duongnd.quanlythuvien.data.dao.PhieuMuonDAO;
import com.duongnd.quanlythuvien.data.model.PhieuMuon;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class PhieuMuonFragment extends Fragment {

    RecyclerView recyclerView_phieu_muon;
    FloatingActionButton btn_them_phieu_muon;
    PhieuMuonDAO phieuMuonDAO;
    PhieuMuonAdapter phieuMuonAdapter;
    List<PhieuMuon> phieuMuonList;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_phieu_muon, container, false);
        recyclerView_phieu_muon = view.findViewById(R.id.recyclerView_phieu_muon);
        btn_them_phieu_muon = view.findViewById(R.id.them_phieu_muon);

        phieuMuonDAO = new PhieuMuonDAO(requireContext());
        phieuMuonList = new ArrayList<>();
        phieuMuonList = phieuMuonDAO.getAllPhieuMuon();
        phieuMuonAdapter = new PhieuMuonAdapter(requireContext(), phieuMuonList, phieuMuonDAO);
        recyclerView_phieu_muon.setAdapter(phieuMuonAdapter);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}