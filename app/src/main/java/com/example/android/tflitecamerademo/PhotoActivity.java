package com.example.android.tflitecamerademo;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import static android.content.ContentValues.TAG;

public class PhotoActivity extends Activity {

    private ImageView imageView;
    private ImageClassifier classifier;
    private TextView text;
    private Button btn_back;

    private Bitmap scaleBitmap(Bitmap origin, int newWidth, int newHeight) {
        if (origin == null) {
            return null;
        }
        int height = origin.getHeight();
        int width = origin.getWidth();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);// 使用后乘
        Bitmap newBM = Bitmap.createBitmap(origin, 0, 0, width, height, matrix, false);
        //if (!origin.isRecycled()) {
        //    origin.recycle();
        //}
        return newBM;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        imageView = findViewById(R.id.imageView);
        openAlbum();
        btn_back = findViewById(R.id.ph_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PhotoActivity.this,StaticActivity.class);
                startActivity(intent);
            }
        });
    }

    //启动相册的方法
    private void openAlbum(){
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent,2);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 2){
            //判断安卓版本
            if (resultCode == RESULT_OK&&data!=null){
                if (Build.VERSION.SDK_INT>=19)
                    handImage(data);
                else handImageLow(data);
            }
        }
    }

    //安卓版本大于4.4的处理方法
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void handImage(Intent data){
        String path =null;
        Uri uri = data.getData();
        //根据不同的uri进行不同的解析
        if (DocumentsContract.isDocumentUri(this,uri)){
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())){
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID+"="+id;
                path = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,selection);
            }else if("com.android.providers.downloads.documents".equals(uri.getAuthority())){
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),Long.valueOf(docId));
                path = getImagePath(contentUri,null);
            }
        }else if ("content".equalsIgnoreCase(uri.getScheme())){
            path = getImagePath(uri,null);
        }else if ("file".equalsIgnoreCase(uri.getScheme())){
            path = uri.getPath();
        }
        //展示图片
        displayImage(path);
    }


    //安卓小于4.4的处理方法
    private void handImageLow(Intent data){
        Uri uri = data.getData();
        String path = getImagePath(uri,null);
        displayImage(path);
    }

    //content类型的uri获取图片路径的方法
    private String getImagePath(Uri uri,String selection) {
        String path = null;
        Cursor cursor = getContentResolver().query(uri,null,selection,null,null);
        if (cursor!=null){
            if (cursor.moveToFirst()){
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    //根据路径展示图片的方法
    private void displayImage(String imagePath){
        if (imagePath != null){
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            imageView.setImageBitmap(bitmap);
            text = findViewById(R.id.textView_photo);
            Bitmap bitmap2 = scaleBitmap(bitmap,ImageClassifier.DIM_IMG_SIZE_X, ImageClassifier.DIM_IMG_SIZE_Y);
            try {
                classifier = new ImageClassifier(PhotoActivity.this);
            } catch (IOException e) {
                Log.e(TAG, "Failed to initialize an image classifier.");
            }
            String textToShow = classifier.classifyFrame(bitmap2);
            //bitmap.recycle();//不要回收！！！
            //bitmap2.recycle();
            String textToShow2="";
            int tmp=0;
            while (textToShow.charAt(tmp)!='\n') tmp++;
            tmp++;
            while (textToShow.charAt(tmp)!=':')
            {
                textToShow2+=textToShow.charAt(tmp);
                tmp++;
            }
            text.setText("    您所识别的垃圾为\n    "+textToShow2);
            classifier.close();
            text.setTextColor(Color.parseColor("#ff5e9cff"));//设置颜色
            text.setTextSize(30);
        }else{
            Toast.makeText(this,"fail to set image",Toast.LENGTH_SHORT).show();
        }
    }
}
