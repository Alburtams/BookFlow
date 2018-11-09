package com.hust.bookflow.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.hust.bookflow.R;
import com.hust.bookflow.model.bean.MessageBean;
import com.hust.bookflow.model.httputils.BookFlowHttpMethods;
import com.hust.bookflow.utils.ToastUtils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import rx.Subscriber;

public class RegisterActivity extends AppCompatActivity {
    private EditText name;
    private EditText stuid;
    private EditText schoname;
    private EditText pwd;
    private EditText repwd;
    private EditText email;
    private Button register_button;
    private Subscriber<MessageBean> mSubscriber;
    private ProgressBar regProgBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        name = (EditText) findViewById(R.id.name);
        stuid = (EditText) findViewById(R.id.stuid);
        //schoname=(EditText)findViewById(R.id.schoname);
        pwd = (EditText) findViewById(R.id.pwd);
        repwd = (EditText) findViewById(R.id.repeat_pwd);
        //email=(EditText)findViewById(R.id.email);
        register_button = (Button) findViewById(R.id.register_button);
        regProgBar = (ProgressBar) findViewById(R.id.register_progress);

        register_button.setOnClickListener(new View.OnClickListener() {
            @Override//点击跳转注册页面
            public void onClick(View view) {
                if (!pwd.getText().toString().equals(repwd.getText().toString())) {
                    ToastUtils.show(RegisterActivity.this, "两次输入密码不匹配，请确认密码！");
                }
                attempRegister();
            }
        });
    }

    private void attempRegister() {
        showProgressbar();
        String mname = name.getText().toString();
        String mstuid = stuid.getText().toString();
        //String mschoname=schoname.getText().toString();
        String mpwd = pwd.getText().toString();
        //String memail=email.getText().toString();
        mSubscriber = new Subscriber<MessageBean>() {
            @Override
            public void onCompleted() {
                closeProgressbar();
            }

            @Override
            public void onError(Throwable e) {
                closeProgressbar();
            }

            @Override
            public void onNext(MessageBean messageBean) {
                if (messageBean != null) {
                    if (messageBean.getResult() == 200) {
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        ToastUtils.show(RegisterActivity.this, "注册成功");
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(RegisterActivity.this, messageBean.getErrMsg(), Toast.LENGTH_LONG).show();
                    }
                } else {
                    ToastUtils.show(RegisterActivity.this, "注册失败");
                }
            }
        };
        BookFlowHttpMethods.getInstance().register(mSubscriber, mstuid, mname, encode(mpwd));
    }

    public static String encode(String password) {
        // MessageDigest专门用于加密的类
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(password.getBytes("UTF-8"));
            byte[] result = messageDigest.digest(); // 得到加密后的字符组数
            StringBuffer sb = new StringBuffer();
            for (byte b : result) {
                int num = b & 0xff; // 这里的是为了将原本是byte型的数向上提升为int型，从而使得原本的负数转为了正数
                String hex = Integer.toHexString(num); //这里将int型的数直接转换成16进制表示
                //16进制可能是为1的长度，这种情况下，需要在前面补0，
                if (hex.length() == 1) {
                    sb.append(0);
                }
                sb.append(hex);
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void showProgressbar() {
        regProgBar.setVisibility(View.VISIBLE);
    }

    private void closeProgressbar() {
        regProgBar.setVisibility(View.GONE);
    }
}
