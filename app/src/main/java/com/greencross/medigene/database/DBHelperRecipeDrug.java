package com.greencross.medigene.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.greencross.medigene.util.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by godaewon on 2017. 6. 28..
 */

public class DBHelperRecipeDrug {
    private final String TAG = DBHelperRecipeDrug.class.getSimpleName();

    private DBHelper mHelper;
    public DBHelperRecipeDrug(DBHelper helper) {
        mHelper = helper;
    }
    /**
     * 기준치 데이터
     */

    public static String TB_RECIPE_DRUG = "tb_data_recipe_drug";
    public static String TB_DATA_DRUG = "tb_data_drug";
    private String DEF_RECIPE_IDX = "recipe_idx";         // 처방전 고유키
    private String DEF_DRUG_IDX = "drug_idx";            // 약정보 고유키

    // DB를 새로 생성할 때 호출되는 함수
//    public String createDb() {
//        // 새로운 테이블 생성
//        String sql =  " CREATE TABLE if not exists "+ TB_RECIPE_DRUG +" ("
//            + DEF_RECIPE_IDX +" VARCHAR(100) NOT NULL, "
//            + DEF_DRUG_IDX +" VARCHAR(100) NOT NULL )";
//        Logger.i(TAG, "onCreate.sql="+sql);
//        return sql;
//    }


    public void insertDb(String recipe_idx, String drug_idx) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DEF_RECIPE_IDX, recipe_idx);
        values.put(DEF_DRUG_IDX, drug_idx);
        db.insert(TB_RECIPE_DRUG, null, values);

        db.close();
    }

    public List<RecipeDrugData> getResult() {
        // 읽기가 가능하게 DB 열기
        SQLiteDatabase db = mHelper.getReadableDatabase();

        // DB에 있는 데이터를 쉽게 처리하기 위해 Cursor를 사용하여 테이블에 있는 모든 데이터 출력
        String sql = "SELECT * FROM "+TB_RECIPE_DRUG;
        Logger.i(TAG, sql);
        Cursor cursor = db.rawQuery(sql, null);

        List<RecipeDrugData> dataList = new ArrayList<>();

        while (cursor.moveToNext()){
            RecipeDrugData data = new RecipeDrugData();
            data.recipe_idx = cursor.getString(cursor.getColumnIndex(DEF_RECIPE_IDX));
            data.drug_idx = cursor.getString(cursor.getColumnIndex(DEF_DRUG_IDX));

            dataList.add(data);

            Logger.i(TAG, "recipe_idx["+data.recipe_idx+"] drug_idx["+data.drug_idx+"]");
        }

        return dataList;
    }

    public String getDataWriteCheck(RecipeDrugData data){
        // 읽기가 가능하게 DB 열기
        SQLiteDatabase db = mHelper.getReadableDatabase();

        // DB에 있는 데이터를 쉽게 처리하기 위해 Cursor를 사용하여 테이블에 있는 모든 데이터 출력
        String sql = "SELECT COUNT(*) as count FROM "+TB_RECIPE_DRUG +" WHERE "+ DEF_RECIPE_IDX +" = "+ data.recipe_idx + " AND "+ DEF_DRUG_IDX +" = "+ data.drug_idx;
        Logger.i(TAG, sql);
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToNext();

        return cursor.getString(cursor.getColumnIndex("count"));
    }

    // 처방전 IDX로 DB로우 삭제
    public void DeleteDb(String idx){
        String sql = "DELETE FROM " +TB_RECIPE_DRUG + " WHERE "+ DEF_RECIPE_IDX + "=" +idx;
        Logger.i(TAG, "onDelete.sql = "+sql);
        mHelper.transactionExcute(sql);
    }

    //처방전에 약정보 로우 가져오기
    public List<DBHelperDrug.DrugData> getDrugValues(String idx){
        SQLiteDatabase db = mHelper.getReadableDatabase();

        List<DBHelperDrug.DrugData> datas = new ArrayList<>();

        String sql = "SELECT * FROM "+ TB_RECIPE_DRUG
                +" INNER JOIN "+ TB_DATA_DRUG +" ON "+ TB_DATA_DRUG +".idx = "+ TB_RECIPE_DRUG +"."+ DEF_DRUG_IDX
                +" WHERE "+ DEF_RECIPE_IDX +" = "+ idx;

        Logger.i(TAG, sql);
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            DBHelperDrug.DrugData data = new DBHelperDrug.DrugData();
            data.drug_idx = cursor.getString(cursor.getColumnIndex("drug_idx"));
            data.drug_name = cursor.getString(cursor.getColumnIndex("drug_name"));
            data.drug_value = cursor.getString(cursor.getColumnIndex("drug_value"));

            datas.add(data);
        }

        return datas;
    }

    // DB 업그레이드를 위해 버전이 변경될 때 호출되는 함수
    public String upgradeDb() {
        return "DROP TABLE "+ TB_RECIPE_DRUG +";";
    }

    public static class RecipeDrugData {
        public String recipe_idx;
        public String drug_idx;
    }
}
