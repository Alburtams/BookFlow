package com.hust.bookflow.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.hust.bookflow.R;
import com.hust.bookflow.utils.Constants;
import com.hust.bookflow.utils.PreferncesUtils;

/**
 * Created by Mr. K on 2018/10/19.
 * 显示扫码后的信息
 */

public class ScanDetailsActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView textView;
    private ImageButton btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         *启动Activity,设置主题
         */
        String nowtheme = PreferncesUtils.getString(this, Constants.PREF_KEY_THEME, "1");
        if (nowtheme.equals("1")) {
            setTheme(R.style.AppTheme);
        } else {
            setTheme(R.style.AppTheme_Light);

        }
        setContentView(R.layout.activity_scandetails);
        init();
    }

    /**
     * 初始化View
     */
    private void init() {
        toolbar = (Toolbar)findViewById(R.id.atv_scandetails_toolbar);
//        toolbar.setTitle("扫码信息");
        textView = (TextView)findViewById(R.id.tv_showdata);
        btn = (ImageButton)findViewById(R.id.btn_back2);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent intent = getIntent();
//        String scandata = intent.getExtras().getString(Constant.INTENT_EXTRA_KEY_QR_SCAN);
        String scandata = intent.getStringExtra("show_data");
        textView.setText(scandata);
    }
}
