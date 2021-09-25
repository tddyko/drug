package com.greencross.medigene.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.greencross.medigene.util.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mrsohn on 2017. 3. 20..
 — 처방전
 CREATE TABLE tb_data_recipe (
 idx            VARCHAR(100) NULL , — 고유번호.
 re_name        VARCHAR(100) NULL,      — 처방전이름
 re_date        VARCHAR(100) NULL,      — 처방일시 (년월일시분까지들어가야함)
 re_symp        VARCHAR(100) NULL,  — 증상
 re_hospital    VARCHAR(100) NULL,  — 처방병원
 re_howtake     VARCHAR(100) NULL, — 복용방법
 re_img         VARCHAR(100) NULL,   — 이미지명
 regdate        DATETIME DEFAULT CURRENT_TIMESTAMP — 자동날짜.
 );

 — 처방전의 약품명
 CREATE TABLE tb_data_recipe_drug (
 idx  VARCHAR(100) NULL , — 고유번호.
 re_drugname  VARCHAR(100) NULL       — 약품명
 );

 — 설명 : 처방전 등록할때 처방전과 약품명이 1:다로 들어가며 idx값이 동일하여야 함.
 */

public class DBHelperDataRecipe {
    private final String TAG = DBHelperDataRecipe.class.getSimpleName();

    private DBHelper mHelper;
    public DBHelperDataRecipe(DBHelper helper) {
        mHelper = helper;
    }

    public static String TB_DATA_RECIPE = "tb_data_recipe";

    private final String DEF_IDX = "idx";
    private final String DEF_RE_NAME = "re_name";
    private final String DEF_RE_DATE = "re_date";
    private final String DEF_RE_SYMP = "re_symp";
    private final String DEF_RE_HOSPITAL = "re_hospital";
    private final String DEF_RE_HOWTAKE  = "re_howtake";
    private final String DEF_RE_IMG  = "re_img";
    private final String DEF_REGDATE = "regdate";

    public void insertDb(RecipeData data) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DEF_IDX, data.idx);
        values.put(DEF_RE_NAME, data.re_name);
        values.put(DEF_RE_DATE, data.re_date);
        values.put(DEF_RE_SYMP, data.re_symp);
        values.put(DEF_RE_HOSPITAL, data.re_hospital);
        values.put(DEF_RE_HOWTAKE , data.re_howtake);
        values.put(DEF_RE_IMG , data.re_img);
//        values.put(DEF_REGDATE, data.regdate);

        db.insert(TB_DATA_RECIPE, null, values);

        db.close();
    }

    /**
     *
     * @return
     */

    public List<RecipeData> getResult() {
        // 읽기가 가능하게 DB 열기
        SQLiteDatabase db = mHelper.getReadableDatabase();

        // DB에 있는 데이터를 쉽게 처리하기 위해 Cursor를 사용하여 테이블에 있는 모든 데이터 출력
        String sql = "SELECT * FROM "+TB_DATA_RECIPE +" ORDER BY " + DEF_REGDATE +" DESC";
        Logger.i(TAG, sql);
        Cursor cursor = db.rawQuery(sql, null);
        int i = 0;
        List<RecipeData> dataList = new ArrayList<>();
        while (cursor.moveToNext()) {
            RecipeData data = new RecipeData();
            data.idx = cursor.getString(cursor.getColumnIndex(DEF_IDX));
            data.re_name = cursor.getString(cursor.getColumnIndex(DEF_RE_NAME));
            data.re_date = cursor.getString(cursor.getColumnIndex(DEF_RE_DATE));
            data.re_symp = cursor.getString(cursor.getColumnIndex(DEF_RE_SYMP));
            data.re_hospital = cursor.getString(cursor.getColumnIndex(DEF_RE_HOSPITAL));
            data.re_howtake = cursor.getString(cursor.getColumnIndex(DEF_RE_HOWTAKE ));
            data.re_img = cursor.getString(cursor.getColumnIndex(DEF_RE_IMG));
            data.regdate = cursor.getString(cursor.getColumnIndex(DEF_REGDATE));

            dataList.add(data);
            Logger.i(TAG, "step["+(i++)+"] idx["+data.idx+"], re_name["+data.re_name+"] re_hospital="+data.re_hospital+", re_howtake=" +data.re_howtake);
        }

        return dataList;
    }


    // DB로우를 삭제하는 함수
    public void DeleteDb(String idx){
        String sql = "DELETE FROM " +TB_DATA_RECIPE + " WHERE idx =='"+idx+"' ";
        Logger.i(TAG, "onDelete.sql = "+sql);
        mHelper.transactionExcute(sql);
    }

    // UPDATE
    public void updateDb(RecipeData data){
        SQLiteDatabase db = mHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DEF_RE_NAME, data.re_name);
        values.put(DEF_RE_DATE, data.re_date);
        values.put(DEF_RE_SYMP, data.re_symp);
        values.put(DEF_RE_HOSPITAL, data.re_hospital);
        values.put(DEF_RE_HOWTAKE, data.re_howtake);
        values.put(DEF_RE_IMG, data.re_img);

        db.update(TB_DATA_RECIPE, values, DEF_IDX+" = "+ data.idx, null);
        db.close();
    }

    public RecipeData  getDataSelect(String idx){
        SQLiteDatabase db = mHelper.getReadableDatabase();

        String sql = "SELECT * FROM "+TB_DATA_RECIPE +" WHERE "+ DEF_IDX +" = "+ idx;
        Logger.i(TAG, sql);
        Cursor cursor = db.rawQuery(sql, null);
        RecipeData data = new RecipeData();

        if(cursor.getCount() > 0){
            cursor.moveToNext();
            data.idx = cursor.getString(cursor.getColumnIndex(DEF_IDX));
            data.re_name = cursor.getString(cursor.getColumnIndex(DEF_RE_NAME));
            data.re_date = cursor.getString(cursor.getColumnIndex(DEF_RE_DATE));
            data.re_symp = cursor.getString(cursor.getColumnIndex(DEF_RE_SYMP));
            data.re_hospital = cursor.getString(cursor.getColumnIndex(DEF_RE_HOSPITAL));
            data.re_howtake = cursor.getString(cursor.getColumnIndex(DEF_RE_HOWTAKE ));
            data.re_img = cursor.getString(cursor.getColumnIndex(DEF_RE_IMG));
            data.regdate = cursor.getString(cursor.getColumnIndex(DEF_REGDATE));
        }

        return data;
    }

    public static class RecipeData {
        public String idx;
        public String re_name;
        public String re_date;
        public String re_symp;
        public String re_hospital;
        public String re_howtake;
        public String re_img;
        public String regdate;
    }

    // DB를 새로 생성할 때 호출되는 함수
//    public String createDb() {
//        // 새로운 테이블 생성
//        String sql =  "CREATE TABLE "+TB_DATA_RECIPE+" ( "
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
//            String sql = "INSERT INTO " + TB_DATA_RECIPE
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
