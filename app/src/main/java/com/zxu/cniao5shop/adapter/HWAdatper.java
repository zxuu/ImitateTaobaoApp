package com.zxu.cniao5shop.adapter;

import android.content.Context;
import android.net.Uri;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zxu.cniao5shop.R;
import com.zxu.cniao5shop.adapter.baseadapter.BaseViewHolder;
import com.zxu.cniao5shop.adapter.baseadapter.SimpleAdapter;
import com.zxu.cniao5shop.bean.hotbean.Wares;

import java.util.List;



/**
 * Created by <a href="http://www.cniao5.com">菜鸟窝</a>
 * 一个专业的Android开发在线教育平台
 */
public class HWAdatper extends SimpleAdapter<Wares> {


    public HWAdatper(Context context, List<Wares> datas) {
        super(context, R.layout.template_hot_wares, datas);
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, Wares wares) {
        SimpleDraweeView draweeView = (SimpleDraweeView) viewHolder.getView(R.id.drawee_view);
        draweeView.setImageURI(Uri.parse(wares.getImgUrl()));

        viewHolder.getTextView(R.id.text_title).setText(wares.getName());
//      TextView textView = (TextView) viewHolder.getView(R.id.text_title);
    }
}
