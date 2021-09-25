package com.greencross.medigene.database.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.greencross.medigene.database.DBHelper;
import com.greencross.medigene.util.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;

/**
 * Created by mrsohn on 2017. 4. 19..
 */

public class DBBackupManager {

    private static final String TAG = DBBackupManager.class.getSimpleName();
    public void exportDB(Context context) {
        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            if (sd.canWrite()) {
                String currentDBPath = "//data//" + context.getPackageName()
                        + "//databases//"+ File.separator + DBHelper.DB_NAME;
                String backupDBPath = DBHelper.DB_NAME; // From SD directory.
                File currentDB = new File(data, currentDBPath);
                File backupDB = new File(sd, backupDBPath);

                FileChannel src = new FileInputStream(currentDB).getChannel();
                FileChannel dst = new FileOutputStream(backupDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();

                Logger.i(TAG, "backupDB path="+backupDB.getParent());
                Logger.i(TAG, "currentDB path="+currentDB.getParent());

                Toast.makeText(context, "Backup Successful!",
                        Toast.LENGTH_SHORT).show();

            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Backup Failed!", Toast.LENGTH_SHORT)
                    .show();
        }
    }


    public void copyDb(final Context context) {
        AssetManager assetManager = context.getAssets();
//        String[] files = null;
//        try {
//            files = assetManager.list("");
//        } catch (IOException e) {
//            Log.e("tag", "Failed to get asset file list.", e);
//        }
//        if (files != null)
//            for (String filename : files) {
            InputStream in = null;
            OutputStream out = null;
            String filename = DBHelper.DB_NAME;
            try {
                in = assetManager.open(filename);
                File data = Environment.getDataDirectory();
                String dbPath = data+ File.separator+"//data//" + context.getPackageName()
                        + "//databases//"+ File.separator;

                if (new File(dbPath).exists() == false) {
                    Logger.i(TAG, "copyDb.mkdirs="+dbPath);
                    new File(dbPath).mkdirs();
                }

                File outFile = new File(dbPath, filename);

                if (outFile.exists()) {
                    Logger.i(TAG, "copyDb.데이터 베이스 있음.");
                    return;
                }

                out = new FileOutputStream(outFile);
                copyFile(in, out);

                if (in != null) {
                    try {
                        in.close();
//                        if (Logger.mUseLogSetting)
//                            Toast.makeText(context, "copyDb.Database 생성", Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        // NOOP
                        e.printStackTrace();

                        if (Logger.mUseLogSetting)
                            Toast.makeText(context, "copyDb.Database 생성 실패", Toast.LENGTH_SHORT).show();
                    }
                }
                if (out != null) {
                    try {
                        out.close();
//                        if (Logger.mUseLogSetting)
//                            Toast.makeText(context, "copyDb.Database 생성", Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        // NOOP
                        e.printStackTrace();
                        if (Logger.mUseLogSetting)
                            Toast.makeText(context, "copyDb.Database 생성 실패", Toast.LENGTH_SHORT).show();
                    }
                }
            } catch(IOException e) {
                Log.e(TAG, "copyDb.Failed to copy asset file: " + filename, e);
            }

//        }
    }
    private void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while((read = in.read(buffer)) != -1){
            out.write(buffer, 0, read);
        }
    }
}