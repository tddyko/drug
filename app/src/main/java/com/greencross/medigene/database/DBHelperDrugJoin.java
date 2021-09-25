package com.greencross.medigene.database;

/**
 * Created by mrsohn on 2017. 3. 20..
 CREATE TABLE "tb_data_drugjoin" ( `info_idx` INTEGER NOT NULL, `drug_idx` INTEGER NOT NULL )
 */

public class DBHelperDrugJoin {
    private final String TAG = DBHelperDrugJoin.class.getSimpleName();

    private DBHelper mHelper;
    public DBHelperDrugJoin(DBHelper helper) {
        mHelper = helper;
    }

    private String DEF_INFO_IDX = "info_idx";
    private String DEF_DRUG_IDX = "drug_idx";

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

//    /**
//     *
//     * @param value
//     * @return
//     */
//    public boolean getResult(String value) {
//        final SQLiteDatabase database = mHelper.getWritableDatabase();
//
//        String selection = DEF_IDX + " = ?";
//        String[] selectionArgs = { value };
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
//            String resultId = cursor.getString(cursor.getColumnIndex(DEF_DRUG_NAME));
//            Logger.i(TAG, "resultId="+resultId);
//        }
//        cursor.close();
//
//        Logger.i(TAG, "cursor.getCount()="+cursor.getCount());
//        return cursor.getCount() == 0;
//    }
}
