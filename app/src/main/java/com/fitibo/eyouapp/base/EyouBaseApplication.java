package com.fitibo.eyouapp.base;

import android.app.Application;
import com.lemon.support.request.RequestManager;
import okhttp3.Cookie;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Timer;

/**
 * Created by yerunjie
 */

public class EyouBaseApplication extends Application {
    public static EyouBaseApplication sAppContext;
    public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public void onCreate() {
        super.onCreate();
        sAppContext = this;
    }

    public static boolean isLogin() {
        List<Cookie> cookies = RequestManager.create(sAppContext).getCookieJar().getCookieStore().getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.name().equals("X-TOKEN")) {
                return true;
            }
        }
        return false;
    }

}