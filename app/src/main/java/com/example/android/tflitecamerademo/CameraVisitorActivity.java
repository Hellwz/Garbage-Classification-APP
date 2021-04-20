package com.example.android.tflitecamerademo;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class CameraVisitorActivity extends Activity {
    private Button btn_1;
    private Button btn_2;
    private Button btn_3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        if (null == savedInstanceState) {
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, Camera2BasicFragment.newInstance())
                    .commit();
        }
        btn_1 = findViewById(R.id.F_static);
        btn_2 = findViewById(R.id.F_help);
        btn_3 = findViewById(R.id.F_mine);
        btn_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(CameraVisitorActivity.this,"请先登录后使用静态识别功能",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(CameraVisitorActivity.this, LogOnActivity.class);
                startActivity(intent);
            }
        });
        btn_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(CameraVisitorActivity.this,"请先登录后使用用户帮助功能",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(CameraVisitorActivity.this, LogOnActivity.class);
                startActivity(intent);
            }
        });
        btn_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(CameraVisitorActivity.this,"请先登录后进入用户中心界面",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(CameraVisitorActivity.this, LogOnActivity.class);
                startActivity(intent);
            }
        });
    }
}
