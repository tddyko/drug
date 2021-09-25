package com.greencross.medigene.prescription;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.greencross.medigene.R;
import com.greencross.medigene.base.BaseFragment;
import com.greencross.medigene.base.CommonActionBar;
import com.greencross.medigene.base.value.Define;
import com.greencross.medigene.util.CDateUtil;
import com.greencross.medigene.util.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

/**
 * Created by mrsohn on 2017. 3. 14..
 */

public class ImageFragment extends BaseFragment {

    private final String TAG = ImageFragment.class.getSimpleName();
    private Uri contentUri;                                 // 앨범,카메라에서 받아온 경로 위치

    private String mIdx = CDateUtil.getForamtyyMMddHHmmssSS(new Date(System.currentTimeMillis()));
    private ImageView mResultIv;

    private final int REQUEST_IMAGE_CAPTURE = 111;
    private final int REQUEST_IMAGE_ALBUM = 222;
    private final int REQUEST_IMAGE_CROP = 333;

    public static Fragment newInstance() {
        ImageFragment fragment = new ImageFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sample_image_fragment, container, false);
        Bundle bundle = getArguments();
        return view;
    }

    /**
     * 액션바 세팅
     */
    @Override
    public void loadActionbar(CommonActionBar actionBar) {
        super.loadActionbar(actionBar);
        actionBar.setActionBarTitle("카메라, 사진 샘플");
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mResultIv = (ImageView) view.findViewById(R.id.sample_result_iv);
        view.findViewById(R.id.sample_gallery_btn).setOnClickListener(mClickListener);
        view.findViewById(R.id.sample_camera_btn).setOnClickListener(mClickListener);

    }

    private File createTempFile() {
        String fileName = mIdx + ".png";
        File file = null;
        try {
            file = new File(Define.IMAGE_SAVE_PATH);
            if (!file.exists()) {
                file.mkdirs();
                Logger.i(TAG, "createTempFile.mkdirs=" + file.getPath());
            }

            file = new File(file.getPath(), fileName);
            Logger.i(TAG, "createTempFile.getapth=" + file.getPath());
            file.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return file;
    }

    View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int vId = v.getId();
            if (vId == R.id.sample_camera_btn) {
                selectCamera();
            } else if (vId == R.id.sample_gallery_btn) {
                selectGallery();
            }
        }
    };

    /**
     * 갤러리 실행
     */
    private void selectGallery() {
        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE
                , Manifest.permission.WRITE_EXTERNAL_STORAGE
                , Manifest.permission.CAMERA};
        reqPermissions(permissions, new IPermission() {
            @Override
            public void result(boolean isGranted) {
                if (isGranted)
                    selectImage();
            }
        });
    }

    private void selectCamera() {
        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE
                , Manifest.permission.WRITE_EXTERNAL_STORAGE
                , Manifest.permission.CAMERA};
        reqPermissions(permissions, new IPermission() {
            @Override
            public void result(boolean isGranted) {
                if (isGranted) {
                    String idx = CDateUtil.getForamtyyMMddHHmmssSS(new Date(System.currentTimeMillis()));
                    try {
                        contentUri = Uri.fromFile(createTempFile());

                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
                        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public File getTempFile(Context context, String url) {
        File file = null;
        try {
            String fileName = Uri.parse(url).getLastPathSegment();
            file = File.createTempFile(fileName, null, context.getCacheDir());
        } catch (IOException e) {
            // Error while creating file
        }
        return file;
    }

    /**
     * 갤러리 실행
     */
    public void selectImage() {
        //버튼 클릭시 처리로직
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_IMAGE_ALBUM);
    }

    /**
     * EXIF정보를 회전각도로 변환하는 메서드
     *
     * @param exifOrientation EXIF 회전각
     * @return 실제 각도
     */
    public int exifOrientationToDegrees(int exifOrientation) {
        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
            return 90;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
            return 180;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
            return 270;
        }
        return 0;
    }

    /**
     * 이미지를 회전시킵니다.
     *
     * @param bitmap 비트맵 이미지
     * @return 회전된 이미지
     */
    public Bitmap rotate(Bitmap bitmap, String path) {
        Logger.i(TAG, "rotate.path=" + path);
        // 이미지를 상황에 맞게 회전시킨다
        try {
            ExifInterface exif = new ExifInterface(path);
            int exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            int degrees = exifOrientationToDegrees(exifOrientation);

            Bitmap retBitmap = bitmap;

            if (degrees != 0 && bitmap != null) {
                Matrix m = new Matrix();
                m.setRotate(degrees, (float) bitmap.getWidth() / 2, (float) bitmap.getHeight() / 2);

                try {
                    Bitmap converted = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m, true);
                    if (bitmap != converted) {
                        retBitmap = converted;
                        bitmap.recycle();
                        bitmap = null;
                    }
                } catch (OutOfMemoryError ex) {
                    // 메모리가 부족하여 회전을 시키지 못할 경우 그냥 원본을 반환합니다.
                }
            }
            return retBitmap;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    /**
     * 사진을 저장 합니다.
     * @param bitmap
     * @param path
     * @return
     */
    public String saveBitmapToFile(Bitmap bitmap, String path) {
        File tempFile = new File(path);
        try {
            FileOutputStream out = new FileOutputStream(tempFile);
            if (out != null) {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, out); // 넘거 받은 bitmap을 png로 저장해줌
                out.close(); // 마무리로 닫아줍니다.
            }
            Logger.i(TAG, "saveBitmapToFile.path=" + path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Logger.i(TAG, "saveBitmapToFile=" + tempFile.getAbsolutePath());
        return tempFile.getAbsolutePath(); // 임시파일 저장경로를 리턴해주면 끝!
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_ALBUM) {
            if (data != null) {
                contentUri = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), contentUri);
                    contentUri = Uri.fromFile(createTempFile());
                    saveBitmapToFile(bitmap, contentUri.getPath());
                    rotate(bitmap, contentUri.getPath());
                    cropImage(contentUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            try {
                Logger.i(TAG, "contentUri=" + contentUri.toString());
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), contentUri);
                bitmap = rotate(bitmap, contentUri.getPath());
                saveBitmapToFile(bitmap, contentUri.getPath());
                cropImage(contentUri);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (requestCode == REQUEST_IMAGE_CROP) {
            if (data != null) {
                Bundle extras = data.getExtras();
                if (extras != null) {
                    Bitmap bitmap = (Bitmap) extras.get("data");
                    Logger.i(TAG, "REQUEST_IMAGE_CROP.contentUri");
                    mResultIv.setImageBitmap(bitmap);
                    Toast.makeText(getContext(), "사진 저장:"+savePath(mIdx), Toast.LENGTH_SHORT).show();
                    saveBitmapToFile(bitmap, savePath(mIdx));
                }
            }
        }
    }

    public String savePath(String idx) {
        return Define.IMAGE_SAVE_PATH+File.separator+idx+".png";
    }

    private void cropImage(Uri contentUri) {
        if (contentUri != null) {
            File file = new File(contentUri.toString());
            Logger.i(TAG, "cropImage.exists=" + file.exists());
            Logger.i(TAG, "cropImage.isFile=" + file.isFile());
            Logger.i(TAG, "cropImage.length=" + file.length());
        }
        Intent cropIntent = new Intent("com.android.camera.action.CROP");
        // indicate image type and Uri of image
        cropIntent.setDataAndType(contentUri, "image/*");
        // set crop properties
        cropIntent.putExtra("crop", "true");
        // indicate aspect of desired crop
        cropIntent.putExtra("aspectX", 1);
        cropIntent.putExtra("aspectY", 1);
        // indicate output X and Y
        cropIntent.putExtra("outputX", 250);
        cropIntent.putExtra("outputY", 250);
        // retrieve data on return
        cropIntent.putExtra("return-data", true);
        startActivityForResult(cropIntent, REQUEST_IMAGE_CROP);
    }
}
