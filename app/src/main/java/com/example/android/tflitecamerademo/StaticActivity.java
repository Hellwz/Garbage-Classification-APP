package com.example.android.tflitecamerademo;

import android.Manifest;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v13.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Button;
import android.view.*;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class StaticActivity extends Activity {

    private AutoFitTextureView textureView;
    private SurfaceView sfv_preview;
    private Button btn_take_photo;
    private Camera camera = null;
    private ImageButton fbtn;
    private boolean Flashbool = false;
    private ImageButton photoBtn;
    private Button btn_back;
    private ImageButton hisotryBtn;
    //surfaceHolder是surface的监听器，提供访问和控制SurfaceView背后的Surface 相关的方法
    private SurfaceHolder.Callback cpHolderCallback = new SurfaceHolder.Callback()
    {
        @Override
        public void surfaceCreated(SurfaceHolder holder)
        //当surface对象创建后，该方法就会被立即调用。
        {
            startPreview();
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
        //当surface发生任何结构性的变化时（格式或者大小），该方法就会被立即调用
        {
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder)
        //当surface对象在将要销毁前，该方法会被立即调用。
        {
            stopPreview();
        }
    };

    @Override
    public boolean onTouchEvent(MotionEvent event) {//屏幕触摸事件
        if (event.getAction()== MotionEvent.ACTION_DOWN) {//按下时自动对焦
            camera.autoFocus(null);
            //af= true;
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_static);
        bindViews();
    }

    private void bindViews()
    {
        sfv_preview = (SurfaceView) findViewById(R.id.sfv_preview);
        btn_take_photo = (Button) findViewById(R.id.getcamera);
        sfv_preview.getHolder().addCallback(cpHolderCallback);
        //获得sfv_preview的surfaceview,
        //再由 addCallback为SurfaceHolder添加一个SurfaceHolder.Callback回调接口。

        btn_take_photo.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)//按下“开始拍照”按钮
            {
                camera.takePicture(null, null, new Camera.PictureCallback()
                        //拍照函数
                {
                    @Override
                    public void onPictureTaken(byte[] data, Camera camera)
                    {
                        String path = "";//定义路径
                        if ((path = saveFile(data)) != null)//如果路径存在
                        {
                            Intent it = new Intent(StaticActivity.this, PreviewActivity.class);
                            it.putExtra("path", path);
                            startActivity(it);
                            //用intent实现从当前界面跳转到预览照片的界面
                        }
                        else
                        {
                            Toast.makeText(StaticActivity.this, "保存照片失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        fbtn = findViewById(R.id.FlashButton);
        fbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Flashbool == false)
                {
                    Camera.Parameters parameters = camera.getParameters();
                    parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                    camera.setParameters( parameters );
                    Toast.makeText(StaticActivity.this,"已打开闪光灯（手电筒）",Toast.LENGTH_SHORT).show();
                    Flashbool=true;
                }
                else
                {
                    Camera.Parameters parameters = camera.getParameters();
                    parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                    camera.setParameters( parameters );
                    Toast.makeText(StaticActivity.this,"已关闭闪光灯（手电筒）",Toast.LENGTH_SHORT).show();
                    Flashbool=false;
                }
            }
        });

        photoBtn = findViewById(R.id.photoButton);
        photoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(StaticActivity.this, PhotoActivity.class);
                startActivity(it);
            }
        });
        hisotryBtn = findViewById(R.id.historyButton);
        hisotryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(StaticActivity.this, HistoryActivity.class);
                startActivity(it);
            }
        });
        btn_back = findViewById(R.id.s_ph_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(StaticActivity.this,CameraActivity.class);
                startActivity(it);
            }
        });
    }



    //保存临时文件的方法
    private String saveFile(byte[] bytes){
        try {
            File file = File.createTempFile("img","");
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bytes);
            fos.flush();
            fos.close();
            return file.getAbsolutePath();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return "";
    }
    //开始预览
    private void startPreview()
    {
        camera = Camera.open();
        try
        {
            camera.setPreviewDisplay(sfv_preview.getHolder());
            camera.setDisplayOrientation(90);   //让相机旋转90度
            Camera.Parameters params = camera.getParameters();
            params.setPictureSize(1920, 1080);
            camera.setParameters(params);
            camera.startPreview();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    //停止预览
    private void stopPreview() {
        camera.stopPreview();
        camera.release();
        camera = null;
    }

}
//闪退解决方法
//https://blog.csdn.net/cosine_w/article/details/87992662
//https://blog.csdn.net/weixin_42398534/article/details/86770237
