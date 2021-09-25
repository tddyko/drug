package com.greencross.medigene;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.greencross.medigene.component.CDialog;
import com.greencross.medigene.database.util.DBBackupManager;
import com.greencross.medigene.util.Logger;
import com.greencross.medigene.util.SharedPref;

import java.util.ArrayList;
import java.util.List;


public class SplashActivity extends AppCompatActivity {
    private final String TAG = SplashActivity.class.getSimpleName();

    private final int REQUEST_PERMISSIONS_REQUEST_CODE = 33;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        setTheme(android.R.style.Theme_NoTitleBar);
        Logger.initLogger(this);
        SharedPref.getInstance().initContext(SplashActivity.this);

    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();

        Handler hd = new Handler();
        hd.postDelayed(new Splashhandler(), 1500); // 1초 후에 hd Handler 실행
    }


    /**
     * 권한 설정
     * @return
     */
    private String[] getGrandtedPermissions() {
        List<String> permissions = new ArrayList<>();

        int isGrandted = ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
        if (isGrandted != PackageManager.PERMISSION_GRANTED)
            permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        isGrandted = ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (isGrandted != PackageManager.PERMISSION_GRANTED)
            permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);

        String[] permissionArr = new String[permissions.size()];
        permissionArr = permissions.toArray(permissionArr);
        return permissionArr;

    }

    private void reqPermissions() {
        final String[] permissions = getGrandtedPermissions();
        if (permissions.length > 0) {
            Log.i(TAG, "Displaying permission rationale to provide additional context.");
            ActivityCompat.requestPermissions(SplashActivity.this
                    , permissions
                    , REQUEST_PERMISSIONS_REQUEST_CODE);
        } else {
            startMainActivity();
        }
    }

    /**
     * Asssets 에 있는 db 파일을 복사한다.
     */
    private void copyDb() {
        new DBBackupManager().copyDb(SplashActivity.this);
    }



    private class Splashhandler implements Runnable {
        public void run() {
            reqPermissions();
        }
    }

    /**
     * 메인화면 진입
     */
    private void startMainActivity() {
        copyDb();

        startActivity(new Intent(getApplication(), MainActivity.class)); // 로딩이 끝난후 이동할 Activity
        SplashActivity.this.finish(); // 로딩페이지 Activity Stack에서 제거
    }


    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.i(TAG, "onRequestPermissionResult");
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length <= 0) {
                // If user interaction was interrupted, the permission request is cancelled and you
                // receive empty arrays.
                Log.i(TAG, "User interaction was cancelled.");
                startMainActivity();
            } else if (isPermissionGransteds(grantResults)) {
                // Permission was granted.
                startMainActivity();

            } else {
                // Permission denied.

                CDialog.showDlg(SplashActivity.this, getString(R.string.permission_setting_access), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        reqPermissions();
                    }
                }, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
            }
        }
    }

    /**
     * 권한이 정상적으로 설정 되었는지 확인
     * @param grantResults
     * @return
     */
    private boolean isPermissionGransteds(int[] grantResults) {
        for (int isGranted : grantResults) {
            return isGranted == PackageManager.PERMISSION_GRANTED;
        }
        return true;
    }
}