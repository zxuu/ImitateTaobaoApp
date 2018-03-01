package com.zxu.cniao5shop.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zxu.cniao5shop.R;


/**
 * Created by Ivan on 15/9/22.
 */
public class MineFragment extends Fragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {



        return  inflater.inflate(R.layout.fragment_mine,container,false);
    }
}
