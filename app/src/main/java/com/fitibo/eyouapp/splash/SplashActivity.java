package com.fitibo.eyouapp.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.fitibo.eyouapp.R;
import com.fitibo.eyouapp.base.EyouBaseActivity;
import com.fitibo.eyouapp.base.EyouBaseApplication;
import com.fitibo.eyouapp.login.LoginActivity;
import com.fitibo.eyouapp.main.MainActivity;

public class SplashActivity extends EyouBaseActivity {
    private static final int SPLASH_TIME = 2000;
    private static final int WHAT_SPLASH = 0;
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case WHAT_SPLASH:
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                    break;
            }
        }
    };
    private LinearLayout ll_splash_btn_group;
    private Button btn_user_login;
    private ImageView iv_splash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            finish();
            return;
        }
        ll_splash_btn_group = (LinearLayout) findViewById(R.id.ll_splash_btn_group);
        btn_user_login = (Button) findViewById(R.id.btn_user_login);
        btn_user_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            }
        });
        iv_splash = (ImageView) findViewById(R.id.iv_splash);

        hideTitleBar();
        if (EyouBaseApplication.isLogin()) {
            mHandler.sendEmptyMessageDelayed(WHAT_SPLASH, SPLASH_TIME);
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(SPLASH_TIME);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    ll_splash_btn_group.setVisibility(View.VISIBLE);
                    iv_splash.setVisibility(View.GONE);
                }
            });
        }
    }
}
