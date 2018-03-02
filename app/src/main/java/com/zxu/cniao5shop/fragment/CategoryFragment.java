package com.zxu.cniao5shop.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.zxu.cniao5shop.Contants;
import com.zxu.cniao5shop.R;
import com.zxu.cniao5shop.adapter.baseadapter.BaseAdapter;
import com.zxu.cniao5shop.adapter.categoryadapter.CategoryAdapter;
import com.zxu.cniao5shop.bean.Category;
import com.zxu.cniao5shop.http.OkHttpHelper;
import com.zxu.cniao5shop.http.SpotsCallBack;

import java.util.List;




public class CategoryFragment extends Fragment {



    private CategoryAdapter mCategoryAdapter;


    private RecyclerView mRecyclerView;



    private OkHttpHelper mHttpHelper = OkHttpHelper.getInstance();


    private int currPage=1;
    private int totalPage=1;
    private int pageSize=10;
    private long category_id=0;


    private  static final int STATE_NORMAL=0;
    private  static final int STATE_REFREH=1;
    private  static final int STATE_MORE=2;

    private int state=STATE_NORMAL;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_category,container,false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_category);
        ViewUtils.inject(this,view);

        requestCategoryData();
        return  view;
    }

    private  void requestCategoryData(){

        mHttpHelper.get(Contants.API.CATEGORY_LIST, new SpotsCallBack<List<Category>>(getContext()) {


            @Override
            public void onSuccess(Response response, List<Category> categories) {

                showCategoryData(categories);
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });

    }

    private  void showCategoryData(List<Category> categories){

        mCategoryAdapter = new CategoryAdapter(getContext(),categories);
        mRecyclerView.setAdapter(mCategoryAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }
}



