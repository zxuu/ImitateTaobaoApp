package com.zxu.cniao5shop.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zxu.cniao5shop.R;
import com.zxu.cniao5shop.adapter.baseadapter.BaseViewHolder;
import com.zxu.cniao5shop.adapter.baseadapter.SimpleAdapter;
import com.zxu.cniao5shop.bean.gouwuche.ShoppingCart;
import com.zxu.cniao5shop.bean.hotbean.Wares;
import com.zxu.cniao5shop.utils.CartProvider;
import com.zxu.cniao5shop.utils.ToastUtils;

import java.util.List;



/**
 * Created by <a href="http://www.cniao5.com">菜鸟窝</a>
 * 一个专业的Android开发在线教育平台
 */
public class HWAdatper extends SimpleAdapter<Wares> {

    CartProvider provider;


    public HWAdatper(Context context, List<Wares> datas) {
        super(context, R.layout.template_hot_wares, datas);

        provider = new CartProvider(context);
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, final Wares wares) {
        SimpleDraweeView draweeView = (SimpleDraweeView) viewHolder.getView(R.id.drawee_view);
        draweeView.setImageURI(Uri.parse(wares.getImgUrl()));

        viewHolder.getTextView(R.id.text_title).setText(wares.getName());

        Button button = viewHolder.getButton(R.id.btn_add_hot);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //点击添加到购物车
//                ShoppingCart cart = (ShoppingCart) wares;

                provider.put(convertData(wares));

                ToastUtils.show(context,"已添加到购物车");
            }
        });
    }

    public ShoppingCart convertData(Wares item){

        ShoppingCart cart = new ShoppingCart();

        cart.setId(item.getId());
        cart.setDescription(item.getDescription());
        cart.setImgUrl(item.getImgUrl());
        cart.setName(item.getName());
        cart.setPrice(item.getPrice());

        return cart;
    }

}
