package com.greencross.medigene.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.greencross.medigene.util.Logger;

import static com.greencross.medigene.database.DBHelperMember.TB_MEMBER;

/**
 * Created by mrsohn on 2017. 3. 20..
 * CREATE TABLE "tb_data_drug" ( `idx` INTEGER NOT NULL, `drug_name` TEXT NOT NULL, `drug_value` INTEGER NOT NULL )
 */

public class DBHelperDrug {
    private final String TAG = DBHelperDrug.class.getSimpleName();

    private DBHelper mHelper;
    public DBHelperDrug(DBHelper helper) {
        mHelper = helper;
    }

    public static String TB_DATA_DRUG = "tb_data_drug";                 // 기준치 데이터 저장 (key값으로 Value값 가져옴)
    private String  DEF_IDX = "def_idx";
    private String  DEF_DRUG_NAME = "def_drug_name";
    private String  DEF_DRUG_VALUE = "def_drug_value";

    // DB를 새로 생성할 때 호출되는 함수
    public String createDb() {
        // 새로운 테이블 생성
        String sql =  "CREATE TABLE "+TB_DATA_DRUG+" ( "+DEF_IDX+" INTEGER NOT NULL, "+DEF_DRUG_NAME+" TEXT NOT NULL, "+DEF_DRUG_VALUE+" INTEGER NOT NULL )";
        Logger.i(TAG, "onCreate.sql="+sql);
        return sql;
    }

    /**
     *
     * @param value
     * @return
     */
    public boolean getResult(String value) {
        final SQLiteDatabase database = mHelper.getWritableDatabase();

        String selection = DEF_IDX + " = ?";
        String[] selectionArgs = { value };

        Cursor cursor = database.query(
                TB_MEMBER,
                null, // The columns to return; all if null.
                selection, // The columns for the WHERE clause
                selectionArgs, // The values for the WHERE clause
                null, // Don't group the rows
                null, // Don't filter by row groups
                null // The sort order
        );

        while (cursor.moveToNext()) {
            String resultId = cursor.getString(cursor.getColumnIndex(DEF_DRUG_NAME));
            Logger.i(TAG, "resultId="+resultId);
        }
        cursor.close();

        Logger.i(TAG, "cursor.getCount()="+cursor.getCount());
        return cursor.getCount() == 0;
    }
//
//    public boolean isValidLoginInfo(String id, String pwd) {
//        final SQLiteDatabase database = mHelper.getWritableDatabase();
//
//        String selection = DEF_MEM_EMAIL+" = ? and "+DEF_MEM_PASSWORD+ " = ?";
//        String[] selectionArgs = {id , pwd };
//
//        Cursor cursor = database.query(
//                TB_MEMBER,
//                null, // The columns to return; all if null.
//                selection, // The columns for the WHERE clause
//                selectionArgs, // The values for the WHERE clause
//                null, // Don't group the rows
//                null, // Don't filter by row groups
//                null // The sort order
//        );
//
//        while (cursor.moveToNext()) {
//            String resultId = cursor.getString(cursor.getColumnIndex(DEF_MEM_EMAIL));
//            Logger.i(TAG, "resultId="+resultId);
//        }
//        cursor.close();
//
//        Logger.i(TAG, "cursor.getCount()="+cursor.getCount());
//        return cursor.getCount() == 1;
//    }
//
//    public void insertDb(String id, String pwd) {
//        SQLiteDatabase db = mHelper.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(DEF_MEM_EMAIL, id);
//        values.put(DEF_MEM_PASSWORD, pwd);
//        db.insert(TB_MEMBER, null, values);
//
//        db.close();
//    }
//
//    // DB 업그레이드를 위해 버전이 변경될 때 호출되는 함수
//    public String upgradeDb() {
//        return "DROP TABLE "+ TB_MEMBER +";";
//    }

    public static class DrugData {
        public String drug_idx;
        public String drug_name;
        public String drug_value;
    }
}
