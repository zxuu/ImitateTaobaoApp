package com.zxu.cniao5shop.adapter;

import android.content.Context;
import android.net.Uri;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zxu.cniao5shop.R;
import com.zxu.cniao5shop.adapter.baseadapter.BaseViewHolder;
import com.zxu.cniao5shop.adapter.baseadapter.SimpleAdapter;
import com.zxu.cniao5shop.bean.gouwuche.ShoppingCart;

import java.util.List;




/**
 * Created by <a href="http://www.cniao5.com">菜鸟窝</a>
 * 一个专业的Android开发在线教育平台
 */
public class WareOrderAdapter extends SimpleAdapter<ShoppingCart> {




    public WareOrderAdapter(Context context, List<ShoppingCart> datas) {
        super(context, R.layout.template_order_wares, datas);

    }

    @Override
    protected void convert(BaseViewHolder viewHoder, final ShoppingCart item) {

//        viewHoder.getTextView(R.id.text_title).setText(item.getName());
//        viewHoder.getTextView(R.id.text_price).setText("￥"+item.getPrice());
        SimpleDraweeView draweeView = (SimpleDraweeView) viewHoder.getView(R.id.drawee_view);
        draweeView.setImageURI(Uri.parse(item.getImgUrl()));

    }


    public float getTotalPrice(){

        float sum=0;
        if(!isNull())
            return sum;

        for (ShoppingCart cart:
                datas) {

                sum += cart.getCount()*cart.getPrice();
        }

        return sum;

    }


    private boolean isNull(){

        return (datas !=null && datas.size()>0);
    }






}
