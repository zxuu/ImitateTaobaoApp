package com.zxu.cniao5shop.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.squareup.okhttp.Response;
import com.zxu.cniao5shop.Contants;
import com.zxu.cniao5shop.CreateOrderActivity;
import com.zxu.cniao5shop.MainActivity;
import com.zxu.cniao5shop.R;
import com.zxu.cniao5shop.adapter.cartadapter.CartAdapter;
import com.zxu.cniao5shop.bean.gouwuche.ShoppingCart;
import com.zxu.cniao5shop.http.OkHttpHelper;
import com.zxu.cniao5shop.http.SpotsCallBack;
import com.zxu.cniao5shop.mine.User;
import com.zxu.cniao5shop.utils.CartProvider;
import com.zxu.cniao5shop.widget.CnToolbar;

import java.util.List;

import static com.zxu.cniao5shop.http.OkHttpHelper.TAG;


public class CartFragment extends BaseFragment implements View.OnClickListener{

    public static final int ACTION_EDIT=1;
    public static final int ACTION_CAMPLATE=2;


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

    private OkHttpHelper httpHelper = OkHttpHelper.getInstance();


//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//
//
//
//        View view = inflater.inflate(R.layout.fragment_cart,container,false);
//
//        ViewUtils.inject(this,view);
//
//
//        cartProvider = new CartProvider(getContext());
//
//        showData();
//
//        return  view;
//    }

    @Override
    public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cart,container,false);

    }

    @Override
    public void init() {

        cartProvider = new CartProvider(getActivity());

        changeToolbar();
        showData();


    }


    @OnClick(R.id.btn_del)
    public void delCart(View view){

        mAdapter.delCart();
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
        mToolbar.getRightButton().setOnClickListener(this);

        mToolbar.getRightButton().setTag(ACTION_EDIT);
    }



    private void showDelControl(){
        mToolbar.getRightButton().setText("完成");
        mTextTotal.setVisibility(View.GONE);
        mBtnOrder.setVisibility(View.GONE);
        mBtnDel.setVisibility(View.VISIBLE);
        mToolbar.getRightButton().setTag(ACTION_CAMPLATE);

        mAdapter.checkAll_None(false);
        mCheckBox.setChecked(false);

    }

    private void  hideDelControl(){

        mTextTotal.setVisibility(View.VISIBLE);
        mBtnOrder.setVisibility(View.VISIBLE);


        mBtnDel.setVisibility(View.GONE);
        mToolbar.setRightButtonText("编辑");
        mToolbar.getRightButton().setTag(ACTION_EDIT);

        mAdapter.checkAll_None(true);
        mAdapter.showTotalPrice();

        mCheckBox.setChecked(true);
    }





    @Override
    public void onClick(View v) {

        int action = (int) v.getTag();
        if(ACTION_EDIT == action){

            showDelControl();
        }
        else if(ACTION_CAMPLATE == action){

            hideDelControl();
        }

    }

    @OnClick(R.id.btn_order)
    public void toOrder(View view) {
//        httpHelper.get(Contants.API.USER_DETAIL, new SpotsCallBack<User>(getContext()) {
//            @Override
//            public void onSuccess(Response response, User user) {
//
//                Log.d(TAG, "onSuccess: ");
//            }
//
//            @Override
//            public void onError(Response response, int code, Exception e) {
//
//                Log.d(TAG, "onError: ");
//            }
//        });

        Intent intent = new Intent(getActivity(), CreateOrderActivity.class);


        startActivity(intent,true);
    }





}
