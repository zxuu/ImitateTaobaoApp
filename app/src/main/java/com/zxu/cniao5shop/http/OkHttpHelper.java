package com.zxu.cniao5shop.http;

//import android.os.Looper;
//import android.os.Handler;
//import android.util.Log;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.internal.framed.FrameReader;
import com.zxu.cniao5shop.CniaoApplication;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.LogRecord;

/**
 * Created by zx on 2018/2/28.
 * 封装工作
 */
public class OkHttpHelper {

//    private Handler handler;
//
//    private static OkHttpClient okHttpClient;
//
//    private OkHttpHelper(){
//        okHttpClient = new OkHttpClient();
//        okHttpClient.setConnectTimeout(10, TimeUnit.SECONDS);
//        okHttpClient.setReadTimeout(10,TimeUnit.SECONDS);
//        okHttpClient.setWriteTimeout(30, TimeUnit.SECONDS);
//
//        gson = new Gson();
//        handler = new Handler(Looper.getMainLooper());
//    };
//
//    private Gson gson;
//
//    public static OkHttpHelper getInstance() {
//
//        return new OkHttpHelper();
//    }
//
//    public void get(String url,BaseCallback callback) {
//        Request request = buildRequest(url,null,HttpMethodType.GET);
//        doRequest(request,callback);
//    }
//
//    public void post(String url, Map<String ,String > paras,BaseCallback callback) {
//
//        Request request = buildRequest(url,null,HttpMethodType.POST);
//        doRequest(request,callback);
//    }
//
//    public void doRequest(Request request, final BaseCallback callback) {
//
//        callback.onRequestBefore(request);
//
//        okHttpClient.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Request request, IOException e) {
//
//                callback.onFailure(request,e);
//            }
//
//            @Override
//            public void onResponse(Response response) throws IOException {
//
//                if (response.isSuccessful()) {
//
//                    String result = response.body().string();
//                    if (callback.mType == String .class) {
//
//                        //callback.onSuccess(response,result);
//                        callbackSuccess(callback,response,result);
//
//                    } else {
//                        try {
//
//                            Object object = gson.fromJson(result,callback.mType);
//                            //callback.onSuccess(response,object);
//                            callbackSuccess(callback,response,object);
//
//                        }catch (com.google.gson.JsonParseException e) {
//                            callback.onError(response,response.code(),e);
//                        }
//
//
//                    }
//                } else {
//                    callbackError(callback,response,null);
//                }
//
//                gson.fromJson(response.body().string(),callback.mType);
//            }
//        });
//    }
//
//    private Request buildRequest(String url,Map<String ,String > paras,HttpMethodType
//                                 methodType) {
//
//        Request.Builder builder = new Request.Builder();
//        builder.url(url);
//
//        if(methodType == HttpMethodType.GET) {
//            builder.get();
//        } else if (methodType == HttpMethodType.POST) {
//
//            RequestBody body = buildFromData(paras);
//            builder.post(body);
//        }
//
//        return builder.build();
//    }
//
//    private RequestBody buildFromData(Map<String ,String > paras) {
//
//        FormEncodingBuilder builder = new FormEncodingBuilder();
//        if (paras != null) {
//            for (Map.Entry<String ,String > entry: paras.entrySet()) {
//                builder.add(entry.getKey(),entry.getValue());
//            }
//        }
//
//        return builder.build();
//    }
//
//
//    private void callbackSuccess(final  BaseCallback callback , final Response response, final Object obj ){
//
//        handler.post(new Runnable() {
//            @Override
//            public void run() {
//                callback.onSuccess(response, obj);
//            }
//        });
//    }
//
//
//    private void callbackError(final  BaseCallback callback , final Response response, final Exception e ){
//
//        handler.post(new Runnable() {
//            @Override
//            public void run() {
//                callback.onError(response,response.code(),e);
//            }
//        });
//    }
//
//
//    enum HttpMethodType {
//        GET,
//        POST,
//    }

    public static final String TAG="OkHttpHelper";

    private  static  OkHttpHelper mInstance;
    private OkHttpClient mHttpClient;
    private Gson mGson;

    private Handler mHandler;

    public static final int TOKEN_MISSING=401;
    public static final int TOKEN_ERROR=402;
    public static final int TOKEN_EXPIRE=403;




    static {
        mInstance = new OkHttpHelper();
    }

    private OkHttpHelper(){

        mHttpClient = new OkHttpClient();
        mHttpClient.setConnectTimeout(10, TimeUnit.SECONDS);
        mHttpClient.setReadTimeout(10,TimeUnit.SECONDS);
        mHttpClient.setWriteTimeout(30,TimeUnit.SECONDS);

        mGson = new Gson();

        mHandler = new Handler(Looper.getMainLooper());

    };

    public static  OkHttpHelper getInstance(){
        return  mInstance;
    }




    public void get(String url,Map<String,String> param,BaseCallback callback){


        Request request = buildGetRequest(url,param);

        request(request,callback);

    }

    public void get(String url, BaseCallback callback){


        get(url,null,callback);

    }


    public void post(String url,Map<String,String> param, BaseCallback callback){

        Request request = buildPostRequest(url,param);
        request(request,callback);
    }





    public  void request(final Request request,final  BaseCallback callback){

        callback.onRequestBefore(request);

        mHttpClient.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Request request, IOException e) {
                callback.onFailure(request,e);

            }

            @Override
            public void onResponse(Response response) throws IOException {

                callback.onResponse(response);

                if(response.isSuccessful()) {

                    String resultStr = response.body().string();

                    Log.d(TAG, "result=" + resultStr);

                    if (callback.mType == String.class){
                        callbackSuccess(callback,response,resultStr);
                    }
                    else {
                        try {

                            Object obj = mGson.fromJson(resultStr, callback.mType);
                            callbackSuccess(callback,response,obj);
                        }
                        catch (com.google.gson.JsonParseException e){ // Json解析的错误
                            callback.onError(response,response.code(),e);
                        }
                    }
                } else if (response.code() == TOKEN_ERROR||response.code() == TOKEN_EXPIRE||
                        response.code() == TOKEN_MISSING) {
                    callbackTokenError(callback,response);
                }
                else {
                    callbackError(callback,response,null);
                }

            }
        });


    }

    private void callbackTokenError(final  BaseCallback callback , final Response response){

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                callback.onTokenError(response,response.code());
            }
        });
    }

    private void callbackSuccess(final  BaseCallback callback , final Response response, final Object obj ){

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                callback.onSuccess(response, obj);
            }
        });
    }


    private void callbackError(final  BaseCallback callback , final Response response, final Exception e ){

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                callback.onError(response,response.code(),e);
            }
        });
    }



    private  Request buildPostRequest(String url,Map<String,String> params){

        return  buildRequest(url,HttpMethodType.POST,params);
    }




    private  Request buildGetRequest(String url,Map<String,String> param){

        return  buildRequest(url,HttpMethodType.GET,param);
    }

    private  Request buildRequest(String url,HttpMethodType methodType,Map<String,String> params){


        Request.Builder builder = new Request.Builder()
                .url(url);

        if (methodType == HttpMethodType.POST){
            RequestBody body = builderFormData(params);
            builder.post(body);
        }
        else if(methodType == HttpMethodType.GET){


            url = buildUrlParams(url, params);
            builder.url(url);
            builder.get();
        }


        return builder.build();
    }



    private RequestBody builderFormData(Map<String,String> params){


        FormEncodingBuilder builder = new FormEncodingBuilder();

        if(params !=null){

            for (Map.Entry<String,String> entry :params.entrySet() ){

                builder.add(entry.getKey(),entry.getValue());
            }
            String token = CniaoApplication.getInstance().getToken();
            if (!TextUtils.isEmpty(token)) {
                builder.add("token", token);
            }
        }

        return  builder.build();

    }



    enum  HttpMethodType{

        GET,
        POST,

    }


    // 拼接url
    private   String buildUrlParams(String url ,Map<String,String> params) {

        if(params == null)
            params = new HashMap<>(1);

        String token = CniaoApplication.getInstance().getToken();
        if(!TextUtils.isEmpty(token))
            params.put("token",token);


        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            sb.append(entry.getKey() + "=" + entry.getValue());
            sb.append("&");
        }
        String s = sb.toString();
        if (s.endsWith("&")) {
            s = s.substring(0, s.length() - 1);
        }

        if(url.indexOf("?")>0){
            url = url +"&"+s;
        }else{
            url = url +"?"+s;
        }

        return url;
    }


}
