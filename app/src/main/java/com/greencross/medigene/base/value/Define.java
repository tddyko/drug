package com.greencross.medigene.base.value;

import android.os.Environment;

import java.io.File;

public class Define {
    private static Define instance;

    public static String IMAGE_SAVE_PATH = Environment.getExternalStorageDirectory().getPath()+ File.separator+"MediGen"+File.separator+".nomedia";

    public static Define getInstance() {
        if (instance == null) {
            instance = new Define();
        }
        return instance;
    }

    public static String getFoodPhotoPath(String idx) {
        return Define.IMAGE_SAVE_PATH+File.separator+idx+".png";
    }

    /**
     * 액션바 설정 관련
     */
    public enum ACTION_BAR {
        NO_RIGHT_MENU
        , RIGHT_MENU1
        , RIGHT_MENU2
    }
}
