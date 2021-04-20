package com.example.android.tflitecamerademo;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.ArrayList;

public class LogOnActivity extends Activity {

    private EditText user_name;
    private EditText user_password;
    private Button user_logon;
    private Button user_register;
    private Button user_visitor;
    private String username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_on);
        //用户名按键设置
        DBOpenHelper mDBOpenHelper = new DBOpenHelper(this);

        user_name = findViewById(R.id.name);
        user_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d("edittext",charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                username = user_name.getText().toString().trim();
            }
        });
        //密码按键设置
        user_password = findViewById(R.id.code);
        user_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d("edittext",charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                password = user_password.getText().toString().trim();
            }
        });
        //登录按键设置
        user_logon = findViewById(R.id.log_on);
        user_logon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<User> data = mDBOpenHelper.getAllData();
                boolean match = false;

                if(TextUtils.isEmpty(username)){
                    Toast.makeText(LogOnActivity.this,"请输入用户名",Toast.LENGTH_SHORT).show();
                }
                //判断密码是否为空
                else if(TextUtils.isEmpty(password)){
                    Toast.makeText(LogOnActivity.this,"请输入密码",Toast.LENGTH_SHORT).show();
                }
                else {
                    for (int i = 0; i < data.size(); i++) {
                        User user = data.get(i);
                        if (username.equals(user.getName()) && password.equals(user.getPassword())) {
                            match = true;
                            break;
                        } else {
                            match = false;
                        }
                    }

                    if (match) {
                        Bundle bundle = new Bundle();
                        bundle.putString("UserState", "User");
                        Toast.makeText(LogOnActivity.this, "登录成功！进入用户模式", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LogOnActivity.this, CameraActivity.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                       // finish(); 返回时造成程序退出
                    }
                    else {
                        Toast.makeText(LogOnActivity.this, "用户名或密码输入不正确", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        //注册按键设置
        user_register = findViewById(R.id.register);
        user_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LogOnActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
        //游客按键设置
        user_visitor = findViewById(R.id.visitor);
        user_visitor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("UserState", "Visitor");
                Toast.makeText(LogOnActivity.this,"进入游客模式！",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LogOnActivity.this,CameraVisitorActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}
