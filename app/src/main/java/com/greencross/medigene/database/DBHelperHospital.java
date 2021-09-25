package com.greencross.medigene.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.greencross.medigene.util.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by godaewon on 2017. 7. 4..
 */

public class DBHelperHospital {

    private final String TAG = DBHelperHospital.class.getSimpleName();

    private DBHelper mHelper;
    public DBHelperHospital(DBHelper helper) {
        mHelper = helper;
    }

    public static String TB_HOSPITAL = "tb_hospital";

    private final String DEF_IDX = "idx";
    private final String DEF_NAME = "name";
    private final String DEF_ADDRESS = "address";
    private final String DEF_TEL = "tel";
    private final String DEF_AREA = "area";


    public List<HospitalData> getResult() {
        // 읽기가 가능하게 DB 열기
        SQLiteDatabase db = mHelper.getReadableDatabase();

        // DB에 있는 데이터를 쉽게 처리하기 위해 Cursor를 사용하여 테이블에 있는 모든 데이터 출력
        String sql = "SELECT * FROM "+TB_HOSPITAL + " Order by " + DEF_IDX + " ASC ";
        Logger.i(TAG, sql);
        Cursor cursor = db.rawQuery(sql, null);
        int i = 0;
        List<HospitalData> dataList = new ArrayList<>();
        while (cursor.moveToNext()) {
            HospitalData data = new HospitalData();
            data.idx = cursor.getString(cursor.getColumnIndex(DEF_IDX));
            data.name = cursor.getString(cursor.getColumnIndex(DEF_NAME));
            data.address = cursor.getString(cursor.getColumnIndex(DEF_ADDRESS));
            data.tel = cursor.getString(cursor.getColumnIndex(DEF_TEL));
            data.area = cursor.getString(cursor.getColumnIndex(DEF_AREA));

            dataList.add(data);
            Logger.i(TAG, "HospitalData["+(i++)+"] idx["+data.idx+"], name["+data.name+"] address="+data.address+", tel=" +data.tel +", area=" +data.area);
        }
        return dataList;
    }

    public List<HospitalData> getAreaResult(String area) {
        // 읽기가 가능하게 DB 열기
        SQLiteDatabase db = mHelper.getReadableDatabase();

        // DB에 있는 데이터를 쉽게 처리하기 위해 Cursor를 사용하여 테이블에 있는 모든 데이터 출력
        String sql = "SELECT * FROM "+TB_HOSPITAL
                    + " WHERE area = '"+ area+"'"
                    + " ORDER BY " + DEF_IDX + " ASC";
        Logger.i(TAG, sql);
        List<HospitalData> dataList = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);
        int i = 0;
        while (cursor.moveToNext()) {
            HospitalData data = new HospitalData();
            data.idx = cursor.getString(cursor.getColumnIndex(DEF_IDX));
            data.name = cursor.getString(cursor.getColumnIndex(DEF_NAME));
            data.address = cursor.getString(cursor.getColumnIndex(DEF_ADDRESS));
            data.tel = cursor.getString(cursor.getColumnIndex(DEF_TEL));
            data.area = cursor.getString(cursor.getColumnIndex(DEF_AREA));

            dataList.add(data);
            Logger.i(TAG, "HospitalData["+(i++)+"] idx["+data.idx+"], name["+data.name+"] address="+data.address+", tel=" +data.tel +", area=" +data.area);
        }
        return dataList;
    }


    public static class HospitalData {
        public String idx;
        public String name;
        public String address;
        public String tel;
        public String area;
    }
}
