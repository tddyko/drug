package com.greencross.medigene.database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.greencross.medigene.database.util.DBBackupManager;
import com.greencross.medigene.util.Logger;

/**
 * Created by mrsohn on 2017. 3. 20..
 */

public class DBHelper extends SQLiteOpenHelper {
    private final String TAG                    = DBHelper.class.getSimpleName();
    private static int DB_VERSION               = 2;
    public static String DB_NAME                = "DrugDB.db";
    private  Context mContext;

    private DBHelperMember mMemberDb = new DBHelperMember(DBHelper.this);
    private DBHelperDrug mDrugDb = new DBHelperDrug(DBHelper.this);
    private DBHelperDrugInfo mDrugInfoDb = new DBHelperDrugInfo(DBHelper.this);
    private DBHelperDrugJoin mDrugJoinDb = new DBHelperDrugJoin(DBHelper.this);
    private DBHelperDataRecipe mDataRecipe = new DBHelperDataRecipe(DBHelper.this);
    private DBHelperHealth mHealthinfo = new DBHelperHealth(DBHelper.this);
    private DBHelperRecipeDrug mRecipeDrug = new DBHelperRecipeDrug(DBHelper.this);
    private DBHelperHospital mHospital = new DBHelperHospital(DBHelper.this);

    // DBHelper 생성자로 관리할 DB 이름과 버전 정보를 받음
    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        mContext = context;
    }

    // DB를 새로 생성할 때 호출되는 함수
    @Override
    public void onCreate(SQLiteDatabase db) {
        // 새로운 테이블 생성
//        db.execSQL(mMemberDb.createDb());
    }

    public void copyDb(Context context) {
        new DBBackupManager().copyDb(context);
    }

    // DB 업그레이드를 위해 버전이 변경될 때 호출되는 함수
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        if (oldVersion != newVersion) {
            Logger.i(TAG, "DBHelper:onUpgrade.oldVersion=" + oldVersion + ", newVersion=" + newVersion);
            onCreate(db);
        }
    }


    public void deleteAll() {
        SQLiteDatabase db = getWritableDatabase();

        db.close();
    }


    public void transactionExcute(String sql) {
        SQLiteDatabase db = getWritableDatabase();

        try {
            db.beginTransaction();
            db.execSQL(sql);

            db.setTransactionSuccessful();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }

        db.close();
    }


    public DBHelperMember getMemberDb() {
        return mMemberDb;
    }

    public DBHelperDrug getDrugDb() {
        return mDrugDb;
    }

    public DBHelperDrugInfo getDrugInfoDb() {
        return mDrugInfoDb;
    }

    public DBHelperDrugJoin getDrugJoinDb() {
        return mDrugJoinDb;
    }

    public DBHelperDataRecipe getmDataRecipe() {
        return mDataRecipe;
    }

    public DBHelperHealth getHealthInfo() {
        return mHealthinfo;
    }

    public void setmDataRecipe(DBHelperDataRecipe mDataRecipe) {
        this.mDataRecipe = mDataRecipe;
    }

    public DBHelperRecipeDrug getRecipeDrug() {
        return mRecipeDrug = mRecipeDrug;
    }

    public DBHelperHospital getHospital() {
        return mHospital = mHospital;
    }

}