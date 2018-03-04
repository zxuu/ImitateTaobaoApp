package com.zxu.cniao5shop.http;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.zxu.cniao5shop.CniaoApplication;
import com.zxu.cniao5shop.LoginActivity;
import com.zxu.cniao5shop.R;
import com.zxu.cniao5shop.utils.ToastUtils;

import java.io.IOException;

import dmax.dialog.SpotsDialog;

/**
 * Created by zx on 2018/3/1.
 */

public abstract class SpotsCallBack<T> extends BaseCallback<T> {

    private SpotsDialog mDialog;

    private Context mContext;


    public SpotsCallBack(Context context) {
        mContext = context;
         mDialog = new SpotsDialog(context);
    }

    private void initSpotsDialog() {
        mDialog = new SpotsDialog(mContext,"拼命加载中...");
    }



    public  void showDialog(){
        mDialog.show();
    }

    public  void dismissDialog(){
        mDialog.dismiss();
    }


    public void setMessage(String message){
        mDialog.setMessage(message);
    }


    @Override
    public void onFailure(Request request, IOException e) {
        dismissDialog();
    }


    @Override
    public void onRequestBefore(Request request) {
        showDialog();
    }

    @Override
    public void onResponse(Response response) {
        dismissDialog();
    }

    @Override
    public void onTokenError(Response response, int code) {
        ToastUtils.show(mContext, R.string.token_error);

        Intent intent = new Intent(mContext, LoginActivity.class);

        mContext.startActivity(intent);

        //验证失效清除本地数据
        CniaoApplication.getInstance().clearUser();
    }
}
