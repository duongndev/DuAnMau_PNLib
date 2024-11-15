package com.duongnd.quanlythuvien.data.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.duongnd.quanlythuvien.data.database.Database;
import com.duongnd.quanlythuvien.data.model.Sach;

import java.util.ArrayList;

public class ThongKeDAO {
    private SQLiteDatabase sqLiteDatabase;

    public ThongKeDAO(Context context) {
        Database database = new Database(context);
        sqLiteDatabase = database.getWritableDatabase();
    }


    public int getDoanhThu(String startDate, String endDate) {
        startDate = startDate.replace("/","");
        endDate = endDate.replace("/","");
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT SUM(tienThue) FROM PhieuMuon WHERE substr(Ngay,7) || substr(Ngay,4,2) || substr(Ngay,1,2) BETWEEN ? and ?",new String[]{startDate,endDate});
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            return cursor.getInt(0);
        }

        return 0;
    }

    public ArrayList<Sach> getTop10Sasch(){
        ArrayList<Sach> sachArrayList = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT pm.maSach, sc.TenSach, COUNT(pm.maSach) AS SoLanMuon FROM PhieuMuon pm, Sach sc WHERE pm.maSach = sc.maSach GROUP by pm.maSach, sc.tenSach ORDER by COUNT(pm.maSach) DESC LIMIT 10",null);
        while (cursor.moveToNext()){
            Sach sach = new Sach();
            sach.setMaSach(cursor.getInt(0));
            sach.setTenSach(cursor.getString(1));
            sachArrayList.add(sach);
        }
        return sachArrayList;
    }

}