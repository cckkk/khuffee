package com.example.cafe.khuffee.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cafe.khuffee.R;
import com.example.cafe.khuffee.network.Connection;
import com.example.cafe.khuffee.network.NetworkHandler;
import com.example.cafe.khuffee.network.NetworkHandlerInterface;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;

import classes.User;


public class SignupActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tvContract;
    private CheckBox chboxAgree;
    private EditText editId;
    private EditText editPass;
    private EditText editConfirm;
    private EditText editName;
    private EditText editCall;
    private Button btnOk;
    private ScrollView scrollWhole;

    private User user;

    private boolean flgCheckId;
    private boolean flgInsertUser;
    private boolean flgWorking;
    private NetworkHandler networkHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        tvContract = (TextView) findViewById(R.id.tv_contract);
        tvContract.setMovementMethod(new ScrollingMovementMethod());

        chboxAgree = (CheckBox) findViewById(R.id.chbox_agree);
        editId = (EditText) findViewById(R.id.edit_id);
        editPass = (EditText) findViewById(R.id.edit_pass);
        editConfirm = (EditText) findViewById(R.id.edit_pass2);
        editName = (EditText)findViewById(R.id.edit_name);
        editCall = (EditText)findViewById(R.id.edit_call);

        btnOk = (Button) findViewById(R.id.btn_ok);

        scrollWhole = (ScrollView)findViewById(R.id.scroll_whole);

        btnOk.setOnClickListener(this);


        tvContract.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                scrollWhole.requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        networkHandler = new NetworkHandler();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_ok) {
            int ret = 0x06;
            String message = "필수항목을 입력해주세요.";
            try {
                ret = signUp();
            } catch (IOException e) {
                e.printStackTrace();
            }
            switch (ret) {
                case 0x00:
                    message = "회원가입이 완료되었습니다.";
                    break;
                case 0x01:
                    message = "약관에 동의해야합니다.";
                    break;
                case 0x02:
                    message = "비밀번호가 일치하지 않습니다.";
                    break;
                case 0x03:
                    message = "이름을 입력하셔야 합니다.";
                    break;
                case 0x04:
                    message = "전화번호를 입력하셔야 합니다";
                    break;
                case 0x05:
                    message = "이미 존재하는 아이디입니다";
                    break;
            }
            Toast.makeText(SignupActivity.this, message, Toast.LENGTH_SHORT).show();
            if(ret == 0x00) {
                finish();
            }
        }
    }

    private int signUp() throws IOException {
        if(!chboxAgree.isChecked()) {
            return 0x01;
        }
        if(editPass.getText().toString().compareTo(editConfirm.getText().toString()) != 0) {
            return 0x02;
        }
        if(editName.getText().toString().length() == 0) {
            return 0x03;
        }
        if(editCall.getText().toString().length() == 0) {
            return 0x04;
        }

        networkHandler.checkID(editId.getText().toString());

        while(networkHandler.isWorking()) {
            Log.e("aa", "AA");
        }

        if(networkHandler.getCheckId()) {
            return 0x05;
        }

        user = new User(editId.getText().toString(), editPass.getText().toString(), editName.getText().toString(), editCall.getText().toString(), 0);
        networkHandler.insertUser(user);

        while(networkHandler.isWorking()) {
            Log.e("aa", "AA");
        }

        if(networkHandler.getInsertUser()) {
            return 0x00;
        }
        return 0x06;
    }

    @Override
    public void finish() {
        Intent intent = new Intent();
        if(user != null) {
            intent.putExtra("user", user);
            setResult(RESULT_OK, intent);
        }
        else {
            setResult(RESULT_CANCELED);
        }
        super.finish();
    }

}
