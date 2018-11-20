package com.hust.bookflow.activity;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.hust.bookflow.R;
import com.hust.bookflow.model.bean.MessageBean;
import com.hust.bookflow.model.httputils.BookFlowHttpMethods;
import com.hust.bookflow.utils.SnackBarUtils;
import com.hust.bookflow.utils.ToastUtils;
import com.hust.bookflow.utils.UIUtils;
import com.hust.bookflow.utils.UserUtils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


import rx.Subscriber;

import static android.app.ActivityOptions.makeSceneTransitionAnimation;


public class LoginActivity extends AppCompatActivity {
    private EditText stuID;
    private EditText mPasswordView;
    private Button mEmailSignInButton;
    private TextView register_textview;
    private Subscriber<MessageBean> mSubscriber;
    private MessageBean mmessageBean;
    private CoordinatorLayout atvbookcoorl;
    private String stuid;
    private String password;
    private CheckBox remember_pwd;
    private CheckBox auto_login;
    private SharedPreferences preferences;
    private ImageButton nav_header_button;
    private TextView nav_header_logintxt;
    private ProgressBar loginProgBar;

    //private int mflag;

    public static void toActivity(Activity activity) {
        Intent intent = new Intent(activity, LoginActivity.class);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            activity.startActivity(intent,
                    makeSceneTransitionAnimation(activity).toBundle());
        } else {
            activity.startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.atvbookcoorl = (CoordinatorLayout) findViewById(R.id.atv_book_coorl);

        remember_pwd = (CheckBox) findViewById(R.id.remember_pwd);
        auto_login = (CheckBox) findViewById(R.id.auto_login);
        stuID = (EditText) findViewById(R.id.stuID);
        mPasswordView = (EditText) findViewById(R.id.password);
        //mflag=0;
        preferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);

        loginProgBar = (ProgressBar) findViewById(R.id.login_progress);

        remember_pwd.setChecked(true);//默认记住密码

        if (preferences.getBoolean("rem_isCheck", false)) {
            //设置默认是记录密码状态
            remember_pwd.setChecked(true);
            stuID.setText(preferences.getString("account", ""));
            mPasswordView.setText(preferences.getString("password", ""));
            Log.e("自动恢复保存的账号密码", "自动恢复保存的账号密码");

            //判断自动登陆多选框状态
            if (preferences.getBoolean("auto_isCheck", false)) {
                //设置默认是自动登录状态
                auto_login.setChecked(true);
                //跳转界面
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                LoginActivity.this.startActivity(intent);
                Toast.makeText(getApplicationContext(), "已自动登录", Toast.LENGTH_SHORT);
                Log.e("自动登陆", "自动登陆");
            }
        }
        mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
                //记住登录名密码
                // System.out.print(mflag);

            }
        });
        register_textview = (TextView) findViewById(R.id.register);
        register_textview.setOnClickListener(new OnClickListener() {
            @Override//点击跳转注册页面
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    //MD5对密码进行加密
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

    private void attemptLogin() {

        stuid = stuID.getText().toString();
        password = mPasswordView.getText().toString();

        if (!TextUtils.isEmpty(stuid) && !TextUtils.isEmpty(password)) {
            showProgressbar();
            mSubscriber = new Subscriber<MessageBean>() {
                @Override
                public void onCompleted() {
                    closeProgressbar();
                    MessageBean a = mmessageBean;
                }

                @Override
                public void onError(Throwable e) {
                    closeProgressbar();
                    MessageBean a = mmessageBean;
                }

                @Override
                public void onNext(MessageBean messageBean) {
                    if (messageBean != null) {
                        mmessageBean = messageBean;
                        if (mmessageBean.getResult() == 200) {
                            if (remember_pwd.isChecked()) {
                                SharedPreferences.Editor editor = preferences.edit();//获取编辑器
                                editor.putString("account", stuid);
                                editor.putString("password", password);
                                editor.putBoolean("rem_isCheck", remember_pwd.isChecked());
                                editor.putBoolean("auto_isCheck", auto_login.isChecked());
                                editor.apply();
                            }
                            ToastUtils.show(LoginActivity.this, "登录成功");
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            ToastUtils.show(LoginActivity.this, "登录失败，用户名或密码错误");
                        }
                    } else {
                        SnackBarUtils.showSnackBar(atvbookcoorl, UIUtils.getString(LoginActivity.this, R.string.error));
                    }
                }
            };
            BookFlowHttpMethods.getInstance().login(mSubscriber, stuid, encode(password));
        }
    }

    private void showProgressbar() {
        loginProgBar.setVisibility(View.VISIBLE);
    }

    private void closeProgressbar() {
        loginProgBar.setVisibility(View.GONE);
    }
}

