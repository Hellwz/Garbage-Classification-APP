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

public class RegisterActivity extends Activity {

    private EditText Register_name;
    private EditText Register_code;
    private EditText Register_re_code;
    private Button Register_sure;
    private String username, password, unchecked_pass, checked_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        DBOpenHelper mDBOpenHelper = new DBOpenHelper(this);

        Register_name = findViewById(R.id.R_name);
        Register_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d("edittext",charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                username = Register_name.getText().toString().trim();
            }
        });
        Register_code = findViewById(R.id.R_code);
        Register_code.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d("edittext",charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                unchecked_pass = Register_code.getText().toString().trim();
            }
        });
        Register_re_code = findViewById(R.id.R_code_re);
        Register_re_code.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.d("edittext",charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                checked_pass = Register_re_code.getText().toString().trim();
            }
        });

        Register_sure = findViewById(R.id.sure);
        Register_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(username)){
                    Toast.makeText(RegisterActivity.this,"请输入用户名",Toast.LENGTH_SHORT).show();
                }
                //判断密码是否为空
                else if(TextUtils.isEmpty(unchecked_pass)){
                    Toast.makeText(RegisterActivity.this,"请输入密码",Toast.LENGTH_SHORT).show();
                }
                //判断二次密码是否为空
                else if(TextUtils.isEmpty(checked_pass)){
                    Toast.makeText(RegisterActivity.this,"请再次输入密码确认",Toast.LENGTH_SHORT).show();
                }
                else if(!unchecked_pass.equals(checked_pass)){
                    Toast.makeText(RegisterActivity.this,"两次输入密码不一致",Toast.LENGTH_SHORT).show();
                }
                else {
                    password = checked_pass;
                    mDBOpenHelper.add(username, password);
                    Bundle bundle = new Bundle();
                    bundle.putString("UserState", "User");
                    Toast.makeText(RegisterActivity.this, "注册成功！进入用户模式", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this, CameraActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });
    }
}
