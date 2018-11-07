package com.hust.bookflow.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hust.bookflow.R;
import com.hust.bookflow.model.bean.MessageBean;
import com.hust.bookflow.model.httputils.BookFlowHttpMethods;

import rx.Subscriber;

public class RegisterActivity extends AppCompatActivity {
    private EditText name;
    private EditText stuid;
    private EditText schoname;
    private EditText pwd;
    private EditText email;
    private Button register_button;
    private Subscriber<MessageBean> mSubscriber;
    private MessageBean register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        name=(EditText)findViewById(R.id.name) ;
        stuid=(EditText)findViewById(R.id.stuid) ;
        //schoname=(EditText)findViewById(R.id.schoname);
        pwd=(EditText)findViewById(R.id.pwd);
        //email=(EditText)findViewById(R.id.email);
        register_button=(Button)findViewById(R.id.register_button);
        register_button.setOnClickListener(new View.OnClickListener() {
            @Override//点击跳转注册页面
            public void onClick(View view) {
                attempRegister();
            }
        });
    }

    private void attempRegister(){
        String mname=name.getText().toString();
        String mstuid=stuid.getText().toString();
        //String mschoname=schoname.getText().toString();
        String mpwd=pwd.getText().toString();
        //String memail=email.getText().toString();
        mSubscriber = new Subscriber<MessageBean>() {
            @Override
            public void onCompleted() {
            }
            @Override
            public void onError(Throwable e) {
            }
            @Override
            public void onNext(MessageBean o) {
                register = o;
                if(register.getResult()==200){
                    Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(RegisterActivity.this, register.getErrMsg(), Toast.LENGTH_LONG).show();
                }
            }
        };
        BookFlowHttpMethods.getInstance().register(mSubscriber,mstuid,mname,mpwd);
    }

}
