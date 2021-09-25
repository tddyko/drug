package com.greencross.medigene.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.greencross.medigene.util.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mrsohn on 2017. 3. 20..
 * CREATE TABLE `tb_data_druginfo` (
 *   `idx`          INTEGER NOT NULL
 * , `drug_title`   TEXT
 * , `drug_category` TEXT
 * , `drug_effect`  TEXT
 * , `drug_source`  TEXT
 * , `drug_url`     TEXT
 * , `drug_img`     TEXT
 * , PRIMARY KEY(`idx`) )
 */

public class DBHelperDrugInfo {
    private final String TAG = DBHelperDrugInfo.class.getSimpleName();

    private DBHelper mHelper;
    public DBHelperDrugInfo(DBHelper helper) {
        mHelper = helper;
    }

    public static String TB_DATA_DRUG_INFO = "tb_data_druginfo";
    public static String TB_DATA_DRUG_ITEMS = "tb_data_drug";
    public static String TB_DATA_DRUG_JOIN = "tb_data_drugjoin";
    public static String TB_DATA_FAVORIES = "tb_data_favorites";
    public static String TB_DATA_HISTORY = "tb_data_history";

    private final String DEF_IDX = "idx";
    private final String DEF_DRUG_TITLE = "drug_title";
    private final String DEF_DRUG_CATEGORY = "drug_category";
    private final String DEF_DRUG_EFFECT = "drug_effect";
    private final String DEF_DRUG_SOURCE = "drug_source";
    private final String DEF_DRUG_URL = "drug_url";
    private final String DEF_DRUG_IMG = "drug_img";
    private final String DEF_DRUG_VALUE = "drug_value";

    /**
     * 블루투스 디바이스에서 데이터를 받았을 때
     */
    public void insert(DrugData data) {

        StringBuffer sb = new StringBuffer();
            String sql = "INSERT INTO " + TB_DATA_DRUG_INFO
                    + " VALUES('"
                    + data.idx + "', '"
                    + data.drug_title + "', '"
                    + data.drug_category + "', '"
                    + data.drug_effect + "', '"
                    + data.drug_source + "', '"
                    + data.drug_url + "', '"
                    + data.drug_img  + "');";
//                    + CDateUtil.getForamtyyyy_MM_dd_HH_mm_ss(new Date()) + "');";
                    
            sb.append(sql);


        Logger.i(TAG, "insert.sql=" + sb.toString());
        mHelper.transactionExcute(sb.toString());

        getResult(null);
    }

        /**
     *
     * @return
     */

    public List<DrugData> getResult(String keyword) {
        // 읽기가 가능하게 DB 열기
        SQLiteDatabase db = mHelper.getReadableDatabase();

        // DB에 있는 데이터를 쉽게 처리하기 위해 Cursor를 사용하여 테이블에 있는 모든 데이터 출력
        String sql = "SELECT * FROM "+TB_DATA_DRUG_INFO;
        if (!TextUtils.isEmpty(keyword)){
            sql += " Where " + DEF_DRUG_TITLE + " like '%" + keyword + "%' ";
        }

        Logger.i(TAG, sql);
        Cursor cursor = db.rawQuery(sql, null);
        int i = 0;

        List<DrugData> dataList = new ArrayList<>();
        while (cursor.moveToNext()) {
            DrugData data = new DrugData();
            data.idx = cursor.getString(cursor.getColumnIndex(DEF_IDX));
            data.drug_title = cursor.getString(cursor.getColumnIndex(DEF_DRUG_TITLE));
            data.drug_category = cursor.getString(cursor.getColumnIndex(DEF_DRUG_CATEGORY));
            data.drug_effect = cursor.getString(cursor.getColumnIndex(DEF_DRUG_EFFECT));
            data.drug_source = cursor.getString(cursor.getColumnIndex(DEF_DRUG_SOURCE));
            data.drug_url= cursor.getString(cursor.getColumnIndex(DEF_DRUG_URL));
            data.drug_img = cursor.getString(cursor.getColumnIndex(DEF_DRUG_IMG));

            dataList.add(data);
            Logger.i(TAG, "step["+(i++)+"] idx["+data.idx+"], drug_title["+data.drug_title+"] drug_effect="+data.drug_effect+", img=" +data.drug_img);
        }
        return dataList;
    }
//" Order by datetime("+ SUGAR_REGDATE +") desc, cast(" + SUGAR_IDX + " as BIGINT) DESC LIMIT 1;";

    public List<DrugData> getHistoryResult() {
        // 읽기가 가능하게 DB 열기
        SQLiteDatabase db = mHelper.getReadableDatabase();

        // DB에 있는 데이터를 쉽게 처리하기 위해 Cursor를 사용하여 테이블에 있는 모든 데이터 출력
        String sql  = " SELECT c.idx, c.drug_title, b.drug_value"
                    + " FROM "+ TB_DATA_HISTORY +" as a"
                    + " INNER JOIN "+ TB_DATA_DRUG_INFO +" as c ON a.idx = c.idx"
                    + " INNER JOIN ("
                    + "     SELECT MIN(drug_value) as drug_value, info_idx, drug_name"
                    + "     FROM tb_data_drugjoin"
                    + "     INNER JOIN tb_data_drug ON tb_data_drugjoin.drug_idx = tb_data_drug.idx"
                    + "     GROUP BY tb_data_drugjoin.info_idx"
                    + ") as b ON a.idx = b.info_idx "
                    + " ORDER BY a.seqno DESC";

        Logger.i(TAG, sql);
        Cursor cursor = db.rawQuery(sql, null);
        int i = 0;

        List<DrugData> dataList = new ArrayList<>();
        while (cursor.moveToNext()) {
            DrugData data = new DrugData();
            data.idx = cursor.getString(cursor.getColumnIndex(DEF_IDX));
            data.drug_title = cursor.getString(cursor.getColumnIndex(DEF_DRUG_TITLE));
            data.drug_value = cursor.getString(cursor.getColumnIndex(DEF_DRUG_VALUE));

            dataList.add(data);
            Logger.i(TAG, "DrugItem["+(i++)+"] idx["+data.idx+"], drug_title["+data.drug_title+"], drug_value["+data.drug_value+"]" );
        }
        return dataList;
    }

    public List<DrugData> getFavoritesResult() {
        // 읽기가 가능하게 DB 열기
        SQLiteDatabase db = mHelper.getReadableDatabase();

        String sql  = " SELECT c.idx, c.drug_title, b.drug_value"
                + " FROM "+ TB_DATA_FAVORIES +" as a"
                + " INNER JOIN "+ TB_DATA_DRUG_INFO +" as c ON a.idx = c.idx"
                + " INNER JOIN ("
                + "     SELECT MIN(drug_value) as drug_value, info_idx, drug_name"
                + "     FROM tb_data_drugjoin"
                + "     INNER JOIN tb_data_drug ON tb_data_drugjoin.drug_idx = tb_data_drug.idx"
                + "     GROUP BY tb_data_drugjoin.info_idx"
                + ") as b ON a.idx = b.info_idx "
                + " ORDER BY a.seqno DESC";

        Logger.i(TAG, sql);
        Cursor cursor = db.rawQuery(sql, null);
        int i = 0;

        List<DrugData> dataList = new ArrayList<>();
        while (cursor.moveToNext()) {
            DrugData data = new DrugData();
            data.idx = cursor.getString(cursor.getColumnIndex(DEF_IDX));
            data.drug_title = cursor.getString(cursor.getColumnIndex(DEF_DRUG_TITLE));
            data.drug_value = cursor.getString(cursor.getColumnIndex(DEF_DRUG_VALUE));

            dataList.add(data);
            Logger.i(TAG, "DrugItem["+(i++)+"] idx["+data.idx+"], drug_title["+data.drug_title+"], drug_value["+data.drug_value+"]" );
        }
        return dataList;
    }


    public List<DrugItem> getItemsResult(String idx) {
        // 읽기가 가능하게 DB 열기
        SQLiteDatabase db = mHelper.getReadableDatabase();

        // DB에 있는 데이터를 쉽게 처리하기 위해 Cursor를 사용하여 테이블에 있는 모든 데이터 출력
        String sql = " SELECT "+TB_DATA_DRUG_ITEMS+".idx, "+ TB_DATA_DRUG_ITEMS+".drug_name, "+TB_DATA_DRUG_ITEMS+".drug_value FROM "+TB_DATA_DRUG_ITEMS
                + " INNER JOIN "+ TB_DATA_DRUG_JOIN +" ON "+TB_DATA_DRUG_ITEMS +".idx = "+TB_DATA_DRUG_JOIN+".drug_idx "
                + " WHERE "+TB_DATA_DRUG_JOIN+".info_idx='" + idx +"' ";

        Logger.i(TAG, sql);
        Cursor cursor = db.rawQuery(sql, null);
        int i = 0;

        List<DrugItem> dataList = new ArrayList<>();
        while (cursor.moveToNext()) {
            DrugItem data = new DrugItem();
            data.idx = cursor.getString(cursor.getColumnIndex("idx"));
            data.drug_name = cursor.getString(cursor.getColumnIndex("drug_name"));
            data.drug_value = cursor.getString(cursor.getColumnIndex("drug_value"));

            dataList.add(data);
            Logger.i(TAG, "DrugItem["+(i++)+"] idx["+data.idx+"], drug_name["+data.drug_name+"] drug_value="+data.drug_value);
        }
        return dataList;
    }


    public DrugData getResultDetail(String idx) {
        // 읽기가 가능하게 DB 열기
        SQLiteDatabase db = mHelper.getReadableDatabase();

        // DB에 있는 데이터를 쉽게 처리하기 위해 Cursor를 사용하여 테이블에 있는 모든 데이터 출력
        String sql = "SELECT * FROM "+TB_DATA_DRUG_INFO + " Where "+ DEF_IDX +"="+idx;

        Logger.i(TAG, sql);
        Cursor cursor = db.rawQuery(sql, null);

        DrugData data = new DrugData();
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            try {
                data.idx = cursor.getString(cursor.getColumnIndex(DEF_IDX));
                data.drug_title = cursor.getString(cursor.getColumnIndex(DEF_DRUG_TITLE));
                data.drug_category = cursor.getString(cursor.getColumnIndex(DEF_DRUG_CATEGORY));
                data.drug_effect = cursor.getString(cursor.getColumnIndex(DEF_DRUG_EFFECT));
                data.drug_source = cursor.getString(cursor.getColumnIndex(DEF_DRUG_SOURCE));
                data.drug_url= cursor.getString(cursor.getColumnIndex(DEF_DRUG_URL));
                data.drug_img = cursor.getString(cursor.getColumnIndex(DEF_DRUG_IMG));
                Logger.i(TAG, "getResultDetail idx["+data.idx+"], drug_title["+data.drug_title+"] drug_effect="+data.drug_effect+", img=" +data.drug_img);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                cursor.close();
            }
        }
        // 히스토리 등록.
        setHistoryDb(idx);
        return data;
    }

    public DrugData getDrugDataValue(String idx){
        //읽기가 가능하게 DB열기
        SQLiteDatabase db = mHelper.getReadableDatabase();

        String sql  = "SELECT a.idx, a.drug_title, b.drug_value"
                + " FROM "+ TB_DATA_DRUG_INFO +" as a"
                + " INNER JOIN("
                + "         SELECT MIN(drug_value) as drug_value, info_idx, drug_name"
                + "         FROM tb_data_drugjoin"
                + "         INNER JOIN tb_data_drug ON tb_data_drugjoin.drug_idx = tb_data_drug.idx"
                + "         GROUP BY tb_data_drugjoin.info_idx"
                + ") as b ON a.idx = b.info_idx"
                + " WHERE a.idx = "+ idx;

        Cursor cursor = db.rawQuery(sql, null);
        DrugData data = new DrugData();
        while(cursor.moveToNext()){
            data.idx = cursor.getString(cursor.getColumnIndex(DEF_IDX));
            data.drug_title = cursor.getString(cursor.getColumnIndex(DEF_DRUG_TITLE));
            data.drug_value = cursor.getString(cursor.getColumnIndex(DEF_DRUG_VALUE));
        }

        return data;
    }

    public DrugData getDrugInfoDetail(String idx){
        //읽기가 가능하게 DB열기
        SQLiteDatabase db = mHelper.getReadableDatabase();

        String sql = "SELECT * FROM "+ TB_DATA_DRUG_INFO +" WHERE "+ DEF_IDX +" = "+ idx;
        Logger.i(TAG, sql);
        Cursor cursor = db.rawQuery(sql, null);
        DrugData data = new DrugData();
        if(cursor.getCount() > 0){
            cursor.moveToNext();
            try{
                data.idx = cursor.getString(cursor.getColumnIndex(DEF_IDX));
                data.drug_title = cursor.getString(cursor.getColumnIndex(DEF_DRUG_TITLE));
                data.drug_category = cursor.getString(cursor.getColumnIndex(DEF_DRUG_CATEGORY));
                data.drug_effect = cursor.getString(cursor.getColumnIndex(DEF_DRUG_EFFECT));
                data.drug_source = cursor.getString(cursor.getColumnIndex(DEF_DRUG_SOURCE));
                data.drug_url = cursor.getString(cursor.getColumnIndex(DEF_DRUG_URL));
                data.drug_img = cursor.getString(cursor.getColumnIndex(DEF_DRUG_IMG));
            } catch (Exception e){
                e.printStackTrace();
            } finally {
                cursor.close();
            }
        }

        return data;
    }

    public void setFavoritesDb(int idx) {

        DeleteFavoritesDb(idx);

        SQLiteDatabase db = mHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DEF_IDX, idx);
        db.insert(TB_DATA_FAVORIES, null, values);

        db.close();
    }

    public void setHistoryDb(String idx) {

        // 항상지우고 넣음.
        DeleteHistoryDb(idx);

        SQLiteDatabase db = mHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DEF_IDX, idx);
        db.insert(TB_DATA_HISTORY, null, values);

        db.close();
    }
    // DB로우를 삭제하는 함수
    public void DeleteDb(String idx){
        String sql = "DELETE FROM " +TB_DATA_DRUG_INFO + " WHERE idx =='"+idx+"' ";
        Logger.i(TAG, "onDelete.sql = "+sql);
        mHelper.transactionExcute(sql);
    }

    // DB로우를 삭제하는 함수
    public void DeleteHistoryDb(String idx){
        String sql = "DELETE FROM " +TB_DATA_HISTORY + " WHERE idx =='"+idx+"' ";
        Logger.i(TAG, "onDelete.sql = "+sql);
        mHelper.transactionExcute(sql);
    }
    // DB로우를 삭제하는 함수
    public void DeleteFavoritesDb(int idx){
        String sql = "DELETE FROM " +TB_DATA_FAVORIES + " WHERE idx =='"+idx+"' ";
        Logger.i(TAG, "onDelete.sql = "+sql);
        mHelper.transactionExcute(sql);
    }
    public static class DrugData {
        public String idx;
        public String drug_title;
        public String drug_category;
        public String drug_effect;
        public String drug_source;
        public String drug_url;
        public String drug_img;
        public String drug_value;
        public String date;
    }

    public static class DrugItem {
        public String idx;
        public String drug_name;
        public String drug_value;
    }
    // DB를 새로 생성할 때 호출되는 함수
//    public String createDb() {
//        // 새로운 테이블 생성
//        String sql =  "CREATE TABLE "+TB_DATA_DRUG_INFO+" ( "
//                + DEF_IDX           +"  TEXT, PRIMARY KEY(idx) "
//                + DEF_DRUG_TITLE    +" TEXT, "
//                + DEF_DRUG_CATEGORY +" TEXT, "
//                + DEF_DRUG_EFFECT   +" TEXT, "
//                + DEF_DRUG_SOURCE   +" TEXT );";
//        Logger.i(TAG, "onCreate.sql="+sql);
//        return sql;
//    }


}
