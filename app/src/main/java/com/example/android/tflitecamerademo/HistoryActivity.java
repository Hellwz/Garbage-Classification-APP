package com.example.android.tflitecamerademo;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.text.SimpleDateFormat;

import static com.example.android.tflitecamerademo.SplashActivity.hcnt;
import static com.example.android.tflitecamerademo.SplashActivity.history_address;
import static com.example.android.tflitecamerademo.SplashActivity.history_result;
import static com.example.android.tflitecamerademo.SplashActivity.history_time;

public class HistoryActivity extends Activity {
    private Button btn_back;
    private TextView ht1,ht2;
    private ImageView hp1,hp2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        String strtmp;
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        File appDir = new File(Environment.getExternalStorageDirectory(), "Litter");

        if (hcnt>=1)
        {
            strtmp = dateformat.format(history_time[hcnt]);
            ht1 = findViewById(R.id.history_textView1);
            ht1.setText(strtmp +"\n识别结果为：\n"+ history_result[hcnt]);
            ht1.setTextSize(20);
            hp1 = findViewById(R.id.imageView1);
            hp1.setImageURI(Uri.fromFile(new File(appDir, history_address[hcnt])));
        }
        if (hcnt>=2)
        {
            strtmp = dateformat.format(history_time[hcnt-1]);
            ht1 = findViewById(R.id.history_textView2);
            ht1.setText(strtmp +"\n识别结果为：\n"+ history_result[hcnt-1]);
            ht1.setTextSize(20);
            hp1 = findViewById(R.id.imageView2);
            hp1.setImageURI(Uri.fromFile(new File(appDir, history_address[hcnt-1])));
        }
        if (hcnt>=3)
        {
            strtmp = dateformat.format(history_time[hcnt-2]);
            ht1 = findViewById(R.id.history_textView3);
            ht1.setText(strtmp +"\n识别结果为：\n"+ history_result[hcnt-2]);
            ht1.setTextSize(20);
            hp1 = findViewById(R.id.imageView3);
            hp1.setImageURI(Uri.fromFile(new File(appDir, history_address[hcnt-2])));
        }
        if (hcnt>=4)
        {
            strtmp = dateformat.format(history_time[hcnt-3]);
            ht1 = findViewById(R.id.history_textView4);
            ht1.setText(strtmp +"\n识别结果为：\n"+ history_result[hcnt-3]);
            ht1.setTextSize(20);
            hp1 = findViewById(R.id.imageView4);
            hp1.setImageURI(Uri.fromFile(new File(appDir, history_address[hcnt-3])));
        }
        if (hcnt>=5)
        {
            strtmp = dateformat.format(history_time[hcnt-4]);
            ht1 = findViewById(R.id.history_textView5);
            ht1.setText(strtmp +"\n识别结果为：\n"+ history_result[hcnt-4]);
            ht1.setTextSize(20);
            hp1 = findViewById(R.id.imageView5);
            hp1.setImageURI(Uri.fromFile(new File(appDir, history_address[hcnt-4])));
        }

        btn_back = findViewById(R.id.history_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HistoryActivity.this,StaticActivity.class);
                startActivity(intent);
            }
        });
    }
}
