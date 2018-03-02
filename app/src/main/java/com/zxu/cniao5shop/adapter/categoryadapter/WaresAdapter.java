package com.zxu.cniao5shop.adapter.categoryadapter;

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
public class WaresAdapter extends SimpleAdapter<Wares> {



    public WaresAdapter(Context context, List<Wares> datas) {
        super(context, R.layout.template_grid_wares, datas);
    }

    @Override
    protected void convert(BaseViewHolder viewHoder, Wares item) {

        viewHoder.getTextView(R.id.text_title_cate).setText(item.getName());
        viewHoder.getTextView(R.id.text_price_cate).setText("￥"+item.getPrice());
        SimpleDraweeView draweeView = (SimpleDraweeView) viewHoder.getView(R.id.drawee_view);
        draweeView.setImageURI(Uri.parse(item.getImgUrl()));
    }



}
