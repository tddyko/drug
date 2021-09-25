package com.greencross.medigene.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.greencross.medigene.util.Logger;

import java.util.ArrayList;
import java.util.List;

public class DBHelperHealth {
    private final String TAG = DBHelperHealth.class.getSimpleName();

    private DBHelper mHelper;
    public DBHelperHealth(DBHelper helper) {
        mHelper = helper;
    }

    public static String TB_DATA_HEALTHINFO = "tb_data_healthinfo";

    private final String DEF_IDX = "idx";
    private final String DEF_HE_TITLE = "he_title";
    private final String DEF_HE_URL = "he_url";
    private final String DEF_REGDATE = "regdate";

    public void insertDb(HealthData data) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DEF_IDX, data.idx);
        values.put(DEF_HE_TITLE, data.he_title);
        values.put(DEF_HE_URL, data.he_url);
        db.insert(TB_DATA_HEALTHINFO, null, values);

        db.close();
    }

    /**
     *
     * @return
     */

    public List<HealthData> getResult() {
        // 읽기가 가능하게 DB 열기
        SQLiteDatabase db = mHelper.getReadableDatabase();

        // DB에 있는 데이터를 쉽게 처리하기 위해 Cursor를 사용하여 테이블에 있는 모든 데이터 출력
        String sql = "SELECT * FROM "+TB_DATA_HEALTHINFO + " Order by " + DEF_IDX + " ASC ";
        Logger.i(TAG, sql);
        Cursor cursor = db.rawQuery(sql, null);
        int i = 0;
        List<HealthData> dataList = new ArrayList<>();
        while (cursor.moveToNext()) {
            HealthData data = new HealthData();
            data.idx = cursor.getString(cursor.getColumnIndex(DEF_IDX));
            data.he_title = cursor.getString(cursor.getColumnIndex(DEF_HE_TITLE));
            data.he_url = cursor.getString(cursor.getColumnIndex(DEF_HE_URL));
            data.regdate = cursor.getString(cursor.getColumnIndex(DEF_REGDATE));

            dataList.add(data);
            Logger.i(TAG, "healthinfo["+(i++)+"] idx["+data.idx+"], he_title["+data.he_title+"] he_url="+data.he_url+", regdate=" +data.regdate);
        }

        return dataList;
    }


    // DB로우를 삭제하는 함수
    public void DeleteDb(String idx){
        String sql = "DELETE FROM " +TB_DATA_HEALTHINFO + " WHERE idx =='"+idx+"' ";
        Logger.i(TAG, "onDelete.sql = "+sql);
        mHelper.transactionExcute(sql);
    }

    public static class HealthData {
        public String idx;
        public String he_title;
        public String he_url;
        public String regdate;
    }

    // DB를 새로 생성할 때 호출되는 함수
//    public String createDb() {
//        // 새로운 테이블 생성
//        String sql =  "CREATE TABLE "+TB_DATA_HEALTHINFO+" ( "
//                + DEF_IDX           +"  TEXT, PRIMARY KEY(idx) "
//                + DEF_DRUG_TITLE    +" TEXT, "
//                + DEF_DRUG_CATEGORY +" TEXT, "
//                + DEF_DRUG_EFFECT   +" TEXT, "
//                + DEF_DRUG_SOURCE   +" TEXT );";
//        Logger.i(TAG, "onCreate.sql="+sql);
//        return sql;
//    }



//    public void insert(RecipeData data) {
//
//
//        StringBuffer sb = new StringBuffer();
//            String sql = "INSERT INTO " + TB_DATA_HEALTHINFO
//                    + " VALUES('"
//                    + data.idx + "', '"
//                    + data.drug_title + "', '"
//                    + data.drug_category + "', '"
//                    + data.drug_effect + "', '"
//                    + data.drug_source + "', '"
//                    + data.drug_url + "', '"
//                    + data.drug_img  + "');";
////                    + CDateUtil.getForamtyyyy_MM_dd_HH_mm_ss(new Date()) + "');";
//
//            sb.append(sql);
//
//
//        Logger.i(TAG, "insert.sql=" + sb.toString());
//        mHelper.transactionExcute(sb.toString());
//
//        getResult();
//    }



}
