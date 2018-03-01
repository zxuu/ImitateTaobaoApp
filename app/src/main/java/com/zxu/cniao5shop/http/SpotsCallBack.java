package com.zxu.cniao5shop.http;

import android.content.Context;
import android.support.v7.app.AlertDialog;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import dmax.dialog.SpotsDialog;

/**
 * Created by zx on 2018/3/1.
 */

public abstract class SpotsCallBack<T> extends BaseCallback<T> {

    private SpotsDialog mDialog;


    public SpotsCallBack(Context context) {
         mDialog = new SpotsDialog(context);
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
}
