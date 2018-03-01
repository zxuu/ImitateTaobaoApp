package com.zxu.cniao5shop.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.ViewUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.squareup.okhttp.Response;
import com.zxu.cniao5shop.Contants;
import com.zxu.cniao5shop.R;
import com.zxu.cniao5shop.adapter.DividerItemDecortion;
import com.zxu.cniao5shop.adapter.HotWaresAdapter;
import com.zxu.cniao5shop.bean.hotbean.Page;
import com.zxu.cniao5shop.bean.hotbean.Wares;
import com.zxu.cniao5shop.http.OkHttpHelper;
import com.zxu.cniao5shop.http.SpotsCallBack;

import java.util.List;


/**
 * Created by Ivan on 15/9/22.
 */
public class HotFragment extends Fragment{

    private HotWaresAdapter mAdapter;


//    @ViewInject(R.id.recyclerview_hot)
    private RecyclerView mRecyclerView;

//    @ViewInject(R.id.refresh_view)
    private MaterialRefreshLayout mRefreshLayout;

    private List<Wares> datas;

    private OkHttpHelper httpHelper = OkHttpHelper.getInstance();
    private int currPage = 1;
    private int pageSize = 10;

    private  static final int STATE_NORMAL=0;
    private  static final int STATE_REFREH=1;
    private  static final int STATE_MORE=2;

    private int state=STATE_NORMAL;
    private int totalPage = 1;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_hot,container,false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_hot);

        mRefreshLayout = (MaterialRefreshLayout) view.findViewById(R.id.refresh_view);


        com.lidroid.xutils.ViewUtils.inject(view);

        initRefreshLayout();
        getData();
        return view ;
    }


    private void initRefreshLayout() {
        mRefreshLayout.setLoadMore(true);
        mRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {

                refreshData();
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {

                if (currPage <= totalPage) {
                    loadMoreData();
                } else {
                    Toast.makeText(getContext(),"没有更多数据了",Toast.LENGTH_SHORT)
                            .show();
                    mRefreshLayout.finishRefreshLoadMore();
                }
            }
        });
    }

    private void refreshData() {

        currPage = 1;

        state = STATE_REFREH;

        getData();
    }

    private void loadMoreData() {

        currPage = ++currPage;
        state = STATE_MORE;
        getData();
    }

    private void getData() {

        String url = Contants.API.WARES_HOT+"?curPage="+currPage+"&pageSize="+pageSize;



        httpHelper.get(url, new SpotsCallBack<Page<Wares>>(getContext()) {

            @Override
            public void onSuccess(Response response, Page<Wares> waresPage) {
                datas = waresPage.getList();
                currPage = waresPage.getCurrentPage();

                totalPage = waresPage.getTotalPage();

                showData();
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });
    }

    private void showData() {

        switch (state) {
            case STATE_NORMAL:
                mAdapter = new HotWaresAdapter(datas);
                mRecyclerView.setAdapter(mAdapter);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                // 设计动画
                mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                mRecyclerView.addItemDecoration(new DividerItemDecortion());
                break;
            case STATE_REFREH:
                mAdapter.clearData();
                mAdapter.addData(datas);
                mRecyclerView.scrollToPosition(0);
                mRefreshLayout.finishRefresh();
                break;

            case STATE_MORE:
                mAdapter.addData(mAdapter.getDatas().size(),datas);
                mRecyclerView.scrollToPosition(mAdapter.getDatas().size());
                mRefreshLayout.finishRefreshLoadMore();
                break;
        }


    }
}
