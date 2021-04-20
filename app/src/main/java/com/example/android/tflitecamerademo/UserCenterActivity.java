package com.example.android.tflitecamerademo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.android.tflitecamerademo.SplashActivity.hcnt;

public class UserCenterActivity extends Activity {
    private Button U_blue;
    private Button U_green;
    private Button U_red;
    private Button U_gray;
    private Button btn_1;
    private Button btn_2;
    private Button btn_3;
    private TextView text;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_center);
        U_blue = (Button)findViewById(R.id.U_btn1);
        U_green = (Button)findViewById(R.id.U_btn2);
        U_red = (Button)findViewById(R.id.U_btn3);
        U_gray = (Button)findViewById(R.id.U_btn4);
        btn_1 = findViewById(R.id.U_dynamic);
        btn_2 = findViewById(R.id.U_static);
        btn_3 = findViewById(R.id.U_help);
        text = (TextView)findViewById(R.id.grade);

        U_blue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder= new AlertDialog.Builder(UserCenterActivity.this);
                builder.setView(R.layout.layout_pop_blue);
                builder.show();

            }
        });
        U_green.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder= new AlertDialog.Builder(UserCenterActivity.this);
                builder.setView(R.layout.layout_pop_green);
                builder.show();

            }
        });
        U_red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder= new AlertDialog.Builder(UserCenterActivity.this);
                builder.setView(R.layout.layout_pop_red);
                builder.show();

            }
        });
        U_gray.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder= new AlertDialog.Builder(UserCenterActivity.this);
                builder.setView(R.layout.layout_pop_gray);
                builder.show();

            }
        });
        btn_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserCenterActivity.this,CameraActivity.class);
                startActivity(intent);
            }
        });
        btn_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserCenterActivity.this,StaticActivity.class);
                startActivity(intent);
            }
        });
        btn_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserCenterActivity.this,HelpActivity.class);
                startActivity(intent);
            }
        });

        text.setText("你的当前积分为："+hcnt);
    }
}
