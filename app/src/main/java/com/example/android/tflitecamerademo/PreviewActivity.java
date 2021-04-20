package com.example.android.tflitecamerademo;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.app.Activity;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.String;

import static android.content.ContentValues.TAG;
import static com.example.android.tflitecamerademo.SplashActivity.hcnt;
import static com.example.android.tflitecamerademo.SplashActivity.history_address;
import static com.example.android.tflitecamerademo.SplashActivity.history_result;
import static com.example.android.tflitecamerademo.SplashActivity.history_time;

public class PreviewActivity extends Activity {

    private ImageView img;
    private ImageClassifier classifier;
    private TextView text;
    private Button btn_back;
    private static long nowtime;

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
        /*if (!origin.isRecycled()) {
            origin.recycle();
        }*/
        return newBM;
    }
    Bitmap adjustPhotoRotation(Bitmap bm, final int orientationDegree) {
        Matrix m = new Matrix();
        m.setRotate(orientationDegree, (float) bm.getWidth() / 2, (float) bm.getHeight() / 2);
        try {
            Bitmap bm1 = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), m, true);
            return bm1;
        } catch (OutOfMemoryError ex) {
        }
        return null;
    }
    public static Bitmap convertViewToBitmap(View view)
    {
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
        return bitmap;
    }

    public static void saveMyBitmap(Context context, Bitmap bmp) {

        // 首先保存图片
        File appDir = new File(Environment.getExternalStorageDirectory(), "Litter");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        nowtime = System.currentTimeMillis();
        String fileName = nowtime + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 其次把文件插入到系统图库
        /*try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    file.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }*/
        Toast.makeText(context,"已保存图片为"+file,Toast.LENGTH_SHORT).show();
        // 最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + file)));
    }

    //private HistoryDB HistoryDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        //开数据库
        //HistoryDBHelper = new HistoryDB( this, "History.db" ,null,1 );
        //SQLiteDatabase db = HistoryDBHelper.getWritableDatabase();
        //
        img =findViewById(R.id.imageView_static) ;
        text=findViewById(R.id.textView_static);
        btn_back = findViewById(R.id.preview_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PreviewActivity.this,StaticActivity.class);
                startActivity(intent);
            }
        });
        //ImageView img = new ImageView(this);
        String path = getIntent().getStringExtra("path");
        if(path != null){
            img.setImageURI(Uri.fromFile(new File(path)));
            //Bitmap bitmap = ((BitmapDrawable)img.getDrawable()).getBitmap();
            Bitmap bitmap=convertViewToBitmap(img);
            bitmap = adjustPhotoRotation(bitmap,90);
            saveMyBitmap(getApplicationContext(),bitmap);
            //FileInputStream fis = new FileInputStream());
            //Bitmap bitmap  = BitmapFactory.decodeStream(fis);
            Bitmap bitmap2 = scaleBitmap(bitmap,ImageClassifier.DIM_IMG_SIZE_X, ImageClassifier.DIM_IMG_SIZE_Y);
            try {
                classifier = new ImageClassifier(PreviewActivity.this);
            } catch (IOException e) {
                Log.e(TAG, "Failed to initialize an image classifier.");
            }
            String textToShow = classifier.classifyFrame(bitmap2);
            //bitmap.recycle();//已在函数中
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

            hcnt++;
            history_time[hcnt]=nowtime;
            history_result[hcnt]=textToShow2;
            history_address[hcnt]=nowtime + ".jpg";
            /*ContentValues values= new ContentValues();
            values.put("time",nowtime);
            values.put("result",textToShow2);
            values.put("fname",nowtime + ".jpg");
            db.insert("History", null, values);*/
            //text.setText(path);
        }
    }
}
