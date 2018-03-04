package com.zxu.cniao5shop.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.squareup.picasso.Picasso;
import com.zxu.cniao5shop.CniaoApplication;
import com.zxu.cniao5shop.Contants;
import com.zxu.cniao5shop.LoginActivity;
import com.zxu.cniao5shop.R;
import com.zxu.cniao5shop.mine.User;

import de.hdodenhof.circleimageview.CircleImageView;



public class MineFragment extends BaseFragment{

    @ViewInject(R.id.img_head)
    private CircleImageView mImgHead;

    @ViewInject(R.id.txt_username)
    private TextView mTxUserName;


    @Override
    public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return  inflater.inflate(R.layout.fragment_mine,container,false);
    }

    @Override
    public void init() {

        User user = CniaoApplication.getInstance().getUser();

        showUser(user);

    }

    @OnClick(value = {R.id.img_head,R.id.txt_username})
    private void toLogin(View view) {

        Intent intent = new Intent(getActivity(), LoginActivity.class);

        startActivityForResult(intent, Contants.REQUEST_CODE);
    }

    //拿到用户信息后回掉到mineFragment
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        User user = CniaoApplication.getInstance().getUser();

        showUser(user);
    }



    private void showUser(User user) {

        if (user != null) {
            mTxUserName.setText(user.getUsername());

            Picasso.with(getActivity()).load(user.getLogo_url()).into(mImgHead);
        } else {
            mTxUserName.setText(R.string.to_login);
        }
    }


    // 退出登录
    @OnClick(R.id.btn_logout)
    public void logOut(View view ) {
        CniaoApplication.getInstance().clearUser();
        showUser(null);
    }
}
