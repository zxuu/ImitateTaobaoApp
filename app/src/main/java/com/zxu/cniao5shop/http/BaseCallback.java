package com.zxu.cniao5shop.http;

import com.google.gson.internal.$Gson$Types;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by zx on 2018/2/28.
 */

public abstract class BaseCallback<T> {

    Type mType;

    // 把T转换为Type
    static Type getSuperclassTypeParameter(Class<?> subclass)
    {
        Type superclass = subclass.getGenericSuperclass();
        if (superclass instanceof Class)
        {
            throw new RuntimeException("Missing type parameter.");
        }
        ParameterizedType parameterized = (ParameterizedType) superclass;
        return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
    }


    public BaseCallback()
    {
        mType = getSuperclassTypeParameter(getClass());
    }

    public abstract void onRequestBefore(Request request);


    public abstract void onFailure(Request request, IOException e);

    /**
     *请求成功时调用此方法
     * @param response
     */
    public abstract  void onResponse(Response response);

    public abstract void onSuccess(Response response,T t);
    public abstract void onError(Response response,int code,Exception e);
}
