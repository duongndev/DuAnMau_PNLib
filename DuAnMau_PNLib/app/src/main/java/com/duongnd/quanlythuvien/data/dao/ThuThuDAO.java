package com.duongnd.quanlythuvien.data.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.duongnd.quanlythuvien.data.database.Database;
import com.duongnd.quanlythuvien.data.model.ThuThu;

public class ThuThuDAO {
    private SQLiteDatabase sqLiteDatabase;

    public ThuThuDAO(Context context) {
        Database database = new Database(context);
        sqLiteDatabase = database.getWritableDatabase();
    }

    public boolean insert(ThuThu thuThu){
        ContentValues values = new ContentValues();
        values.put("maTT",thuThu.getMaTT());
        values.put("hoTen",thuThu.getHoten());
        values.put("matKhau",thuThu.getMatKhau());
        long data = sqLiteDatabase.insert("ThuThu",null,values);
        return (data > 0);
    }


    public boolean checkThuThu(int maTT){
        Cursor cursor = sqLiteDatabase.rawQuery("select * from ThuThu where MaTT = ?",new String[]{String.valueOf(maTT)});
        int row = cursor.getCount();
        return (row > 0);
    }

    public boolean checkLogin(String maTT,String matKhau){
        Cursor cursor = sqLiteDatabase.rawQuery("select * from ThuThu where maTT = ? and matKhau = ?",new String[]{maTT, matKhau});
        if(cursor.getCount() != 0){
            return true;
        }else{
            return false;
        }
    }


    public boolean capNhatMatKhau(int maTT, String oldPass, String newPass){
        Cursor cursor = sqLiteDatabase.rawQuery("select * from ThuThu where maTT = ? and matKhau = ?", new String[]{String.valueOf(maTT),oldPass});
        if (cursor.getCount() > 0){
            ContentValues values = new ContentValues();
            values.put("matKhau", newPass);
            long check = sqLiteDatabase.update("ThuThu",values,"maTT = ?",new String[]{String.valueOf(maTT)});
            return check != -1;
        }
        return false;
    }
}