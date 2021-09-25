package com.greencross.medigene.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.greencross.medigene.util.Logger;
import com.greencross.medigene.util.SharedPref;

/**
 * Created by mrsohn on 2017. 3. 20..
 */

public class DBHelperMember {
    private final String TAG = DBHelperMember.class.getSimpleName();

    private DBHelper mHelper;
    public DBHelperMember(DBHelper helper) {
        mHelper = helper;
    }
    /**
     * 기준치 데이터
     */
    public static String TB_MEMBER = "tb_member";                 // 기준치 데이터 저장 (key값으로 Value값 가져옴)
    private String DEF_MEM_EMAIL = "def_mem_email";             // 이메일
    private String DEF_MEM_PASSWORD = "def_mem_password";               // 패스워드
    private String DEF_MEM_NAME = "def_mem_name";
    private String DEF_MEM_BIRTH= "def_mem_birth";
    private String DEF_MEM_SEX = "def_mem_sex";
    private String DEF_MEM_PHONE = "def_mem_phone";
    private String DEF_MEM_INTERNAL = "def_mem_internal";

    // DB를 새로 생성할 때 호출되는 함수
//    public String createDb() {
//        // 새로운 테이블 생성
//        String sql =  " CREATE TABLE if not exists "+ TB_MEMBER +" ("
//            + DEF_MEM_EMAIL +" VARCHAR(100) NULL, "
//            + DEF_MEM_PASSWORD +" VARCHAR(100) NULL )";
//        Logger.i(TAG, "onCreate.sql="+sql);
//        return sql;
//    }

    public boolean isRegistMember(String id) {
        final SQLiteDatabase database = mHelper.getWritableDatabase();

        String selection = DEF_MEM_EMAIL + " = ?";
        String[] selectionArgs = { id };

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
            String resultId = cursor.getString(cursor.getColumnIndex(DEF_MEM_EMAIL));
            Logger.i(TAG, "resultId="+resultId);
        }
        cursor.close();

        Logger.i(TAG, "cursor.getCount()="+cursor.getCount());
        return cursor.getCount() == 0;
    }

    public boolean isValidLoginInfo(String id, String pwd) {
        final SQLiteDatabase database = mHelper.getWritableDatabase();

        String selection = DEF_MEM_EMAIL+" = ? and "+DEF_MEM_PASSWORD+ " = ?";
        String[] selectionArgs = {id , pwd };

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
            String resultId = cursor.getString(cursor.getColumnIndex(DEF_MEM_EMAIL));
            Logger.i(TAG, "resultId="+resultId);
        }
        cursor.close();

        Logger.i(TAG, "cursor.getCount()="+cursor.getCount());
        return cursor.getCount() == 1;
    }

    public void insertDb(String id, String pwd, String name, String birth, String sex, String phone, String inter) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DEF_MEM_EMAIL, id);
        values.put(DEF_MEM_PASSWORD, pwd);
        values.put(DEF_MEM_NAME, name);
        values.put(DEF_MEM_BIRTH, birth);
        values.put(DEF_MEM_SEX, sex);
        values.put(DEF_MEM_PHONE, phone);
        values.put(DEF_MEM_INTERNAL, inter);
        db.insert(TB_MEMBER, null, values);

        db.close();
    }

    public void editMemberDb(String pwd) {

        String email =  SharedPref.getInstance().getPreferences(SharedPref.SAVED_LOGIN_ID);
        SQLiteDatabase db = mHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DEF_MEM_PASSWORD, pwd);

        db.update(TB_MEMBER, values, DEF_MEM_EMAIL+"=?", new String[]{email} );
        db.close();
    }

    // DB 업그레이드를 위해 버전이 변경될 때 호출되는 함수
    public String upgradeDb() {
        return "DROP TABLE "+ TB_MEMBER +";";
    }
}
