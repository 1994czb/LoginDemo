package com.example.administrator.logindemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;

import api.LoginApi;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import utils.RetrofitManager;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_regitster;
    private TextView tv_forgrt_mima;
    private Button btn_login;
    private EditText ed_login_password;
    private EditText ed_login_phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        ed_login_password = (EditText) findViewById(R.id.ed_login_password);
        ed_login_phone = (EditText) findViewById(R.id.ed_login_phone);
        tv_regitster = (TextView) findViewById(R.id.tv_regitster);
        tv_regitster.setOnClickListener(this);
        tv_forgrt_mima = (TextView) findViewById(R.id.tv_forgrt_mima);
        tv_forgrt_mima.setOnClickListener(this);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                Login();
                break;
        }
    }

    /**
     * 判断手机格式 是否正确的方法
     * ^
     */
    public static boolean isMobileNO(String mobiles) {
    /*
    移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
    联通：130、131、132、152、155、156、185、186
    电信：133、153、180、189、（1349卫通）
    总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
    */
        String telRegex = "[1][358]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobiles)) {
            return false;
        } else {
            //return telRegex.matches(mobiles);
            return Pattern.matches(telRegex, mobiles);
        }
    }

    private void Login() {
        String phone = ed_login_phone.getText().toString();
        String password = ed_login_password.getText().toString();
        if (TextUtils.isEmpty(phone) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "请填写必填信息", Toast.LENGTH_SHORT).show();
            return;
        } else {
            if (!(password.length() >= 6 && password.length() < 25)) {
                Toast.makeText(this, "用户名或密码格式不正确", Toast.LENGTH_SHORT).show();
                return;
            } else {
                Observable<ResponseBody> register = RetrofitManager.getDefault().create(LoginApi.class).register(phone, password);

                register
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<ResponseBody>() {
                            @Override
                            public void accept(ResponseBody responseBody) throws Exception {
                                Toast.makeText(MainActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                Toast.makeText(MainActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        }
    }
}
