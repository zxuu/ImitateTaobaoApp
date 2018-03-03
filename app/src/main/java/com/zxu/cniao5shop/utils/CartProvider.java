package com.zxu.cniao5shop.utils;

import android.content.Context;
import android.util.SparseArray;

import com.google.gson.reflect.TypeToken;
import com.zxu.cniao5shop.bean.gouwuche.ShoppingCart;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by <a href="http://www.cniao5.com">菜鸟窝</a>
 * 一个专业的Android开发在线教育平台
 */
public class CartProvider {



    public static final String CART_JSON="cart_json";

    private SparseArray<ShoppingCart> datas =null;


    private  Context mContext;


    public CartProvider(Context context){

        mContext = context;
        //存储到SparseArray
       datas = new SparseArray<>(10);
       //从本地读入到内存
        listToSparse();

    }



    public void put(ShoppingCart cart){

                                      //key
       ShoppingCart temp =  datas.get(cart.getId().intValue());
        //如果购物车里存在
        if(temp !=null){
            temp.setCount(temp.getCount()+1);
        }
        else{
            temp = cart;
            temp.setCount(1);
        }

        datas.put(cart.getId().intValue(),temp);

        commit();

    }

    public void update(ShoppingCart cart){

        datas.put(cart.getId().intValue(),cart);
        commit();
    }

    public void delete(ShoppingCart cart){
        datas.delete(cart.getId().intValue());
        commit();
    }

    public List<ShoppingCart> getAll(){

        return  getDataFromLocal();
    }


    public void commit(){


        List<ShoppingCart> carts = sparseToList();

        //存储到本地
        PreferencesUtils.putString(mContext,CART_JSON,JSONUtil.toJSON(carts));

    }

    //SparseArray里的数据转换为List
    private List<ShoppingCart> sparseToList(){


        int size = datas.size();

        List<ShoppingCart> list = new ArrayList<>(size);
        for (int i=0;i<size;i++){

            list.add(datas.valueAt(i));
        }
        return list;

    }


    //保存到SparseArray
    private void listToSparse(){

        List<ShoppingCart> carts =  getDataFromLocal();

        if(carts!=null && carts.size()>0){

            for (ShoppingCart cart:
                    carts) {

                datas.put(cart.getId().intValue(),cart);
            }
        }

    }



    public  List<ShoppingCart> getDataFromLocal(){

        String json = PreferencesUtils.getString(mContext,CART_JSON);
        List<ShoppingCart> carts =null;
        if(json !=null ){

            carts = JSONUtil.fromJson(json,new TypeToken<List<ShoppingCart>>(){}.getType());

        }

        return  carts;

    }




}
