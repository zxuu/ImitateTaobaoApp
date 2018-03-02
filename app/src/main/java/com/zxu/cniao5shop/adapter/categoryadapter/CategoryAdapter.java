package com.zxu.cniao5shop.adapter.categoryadapter;

import android.content.Context;

import com.zxu.cniao5shop.R;
import com.zxu.cniao5shop.adapter.baseadapter.BaseViewHolder;
import com.zxu.cniao5shop.adapter.baseadapter.SimpleAdapter;
import com.zxu.cniao5shop.bean.Category;

import java.util.List;

/**
 * Created by zx on 2018/3/2.
 */

public class CategoryAdapter extends SimpleAdapter<Category> {

    public CategoryAdapter(Context context, List<Category> datas) {
        super(context, R.layout.template_single_text, datas);
    }

    @Override
    protected void convert(BaseViewHolder viewHoder, Category item) {

        viewHoder.getTextView(R.id.textView_category).setText(item.getName());
    }
}
