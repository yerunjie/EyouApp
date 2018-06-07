package com.fitibo.eyouapp.base;

import android.content.Context;
import com.fitibo.eyouapp.R;
import com.lemon.support.request.SimpleCallBack;
import com.lemon.support.util.ToastUtils;

/**
 * Created by yerunjie on 18/1/13.
 */

public abstract class EyouBaseCallBack<T> implements SimpleCallBack<T> {
    private Context mContext = EyouBaseApplication.sAppContext;

    @Override
    public void onSuccess(T t) {
        onSuccess200(t);
        onFinish(t);
    }

    @Override
    public void onFailure(Object o) {
        ToastUtils.show(mContext, R.string.app_request_fail);
    }

    @Override
    public void onError(Exception e) {
        ToastUtils.show(mContext, R.string.app_request_error);
    }

    /**
     * override for custom case when http success
     *
     * @param t
     */
    public void onFinish(T t) {

    }

    public abstract void onSuccess200(T t);
}
