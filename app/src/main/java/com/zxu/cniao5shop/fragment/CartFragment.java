package com.zxu.cniao5shop.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.zxu.cniao5shop.MainActivity;
import com.zxu.cniao5shop.R;
import com.zxu.cniao5shop.adapter.cartadapter.CartAdapter;
import com.zxu.cniao5shop.bean.gouwuche.ShoppingCart;
import com.zxu.cniao5shop.utils.CartProvider;
import com.zxu.cniao5shop.widget.CnToolbar;

import java.util.List;


public class CartFragment extends Fragment{


    @ViewInject(R.id.recycler_view)
    private RecyclerView mRecyclerView;

    @ViewInject(R.id.checkbox_all)
    private CheckBox mCheckBox;

    @ViewInject(R.id.txt_total)
    private TextView mTextTotal;

    @ViewInject(R.id.btn_order)
    private Button mBtnOrder;

    @ViewInject(R.id.btn_del)
    private Button mBtnDel;

    private CnToolbar mToolbar;


    private CartAdapter mAdapter;
    private CartProvider cartProvider;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {



        View view = inflater.inflate(R.layout.fragment_cart,container,false);

        ViewUtils.inject(this,view);


        cartProvider = new CartProvider(getContext());

        showData();

        return  view;
    }




    private void showData() {
        List<ShoppingCart> carts = cartProvider.getAll();

        mAdapter = new CartAdapter(getContext(),carts,mCheckBox,mTextTotal);

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
    }

    public void refData() {
        mAdapter.clearData();
        List<ShoppingCart> carts = cartProvider.getAll();

        mAdapter.addData(carts);

        //点击更改价格
        mAdapter.showTotalPrice();
    }



    @Override
    public void onAttach(Context context) {

        super.onAttach(context);

        if (context instanceof MainActivity) {
            MainActivity activity = (MainActivity) context;

            mToolbar = (CnToolbar) activity.findViewById(R.id.toolbar);

            changeToolbar();
        }
    }

    public void changeToolbar() {
        mToolbar.hideSearchView();
        mToolbar.showTitleView();
        mToolbar.setTitle(R.string.cart);

        mToolbar.setRightButtonText("编辑");
        mToolbar.getRightButton().setVisibility(View.VISIBLE);
        mToolbar.getRightButton().setOnClickListener(null);
    }


}
