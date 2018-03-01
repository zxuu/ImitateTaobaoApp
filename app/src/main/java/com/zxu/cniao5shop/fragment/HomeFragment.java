package com.zxu.cniao5shop.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.zxu.cniao5shop.Contants;
import com.zxu.cniao5shop.R;
import com.zxu.cniao5shop.adapter.DividerItemDecortion;
import com.zxu.cniao5shop.adapter.HomeCategoryAdapter;
import com.zxu.cniao5shop.bean.Banner;
import com.zxu.cniao5shop.bean.HomeCategory;
import com.zxu.cniao5shop.bean.recyclerbean.Campaign;
import com.zxu.cniao5shop.bean.recyclerbean.HomeCampaign;
import com.zxu.cniao5shop.http.BaseCallback;
import com.zxu.cniao5shop.http.OkHttpHelper;
import com.zxu.cniao5shop.http.SpotsCallBack;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;


/**
 * Created by Ivan on 15/9/25.
 */
public class HomeFragment extends Fragment {

    private OkHttpHelper httpHelper = OkHttpHelper.getInstance();

    private RecyclerView mRecyclerView;

    private SliderLayout mSliderLayout;

    private PagerIndicator indicator;

    private HomeCategoryAdapter mAdapter;

    private Gson mGson = new Gson();

    private List<Banner> mBanner;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_home,container,false);
        mSliderLayout = (SliderLayout) view.findViewById(R.id.slider);

        indicator = (PagerIndicator) view.findViewById(R.id.custom_indicator);

        requestImages();

//        initSlider();

        initRecyclerView(view);
        return view ;
    }

    private void requestImages() {
        String url = "http://112.124.22.238:8081/course_api/banner/query?type=1";
//
//        OkHttpClient client = new OkHttpClient();
//
//        Request request = new Request.Builder()
//                .url(url)
//                .build();
//
//            client.newCall(request).enqueue(new Callback() {
//                @Override
//                public void onFailure(Request request, IOException e) {
//
//                }
//
//                @Override
//                public void onResponse(Response response) throws IOException {
//
//                    if (response.isSuccessful()) {
//                        String json = response.body().string();
//
//                        Type type = new TypeToken<List<Banner>>(){}.getType();
//                        mBanner = mGson.fromJson(json,type);
//
//                        initSlider();
//                    }
//                }
//            });

        httpHelper.get(url, new SpotsCallBack<List<Banner>>(getContext()) {

            @Override
            public void onSuccess(Response response, List<Banner> banners) {
                mBanner = banners;
                initSlider();
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });
    }

    private void initRecyclerView(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
//
//        List<HomeCategory> datas = new ArrayList<>(15);
//
//        HomeCategory category = new HomeCategory("热门活动",R.drawable.img_big_1,R.drawable.img_1_small1,R.drawable.img_1_small2);
//        datas.add(category);
//
//        category = new HomeCategory("有利可图",R.drawable.img_big_4,R.drawable.img_4_small1,R.drawable.img_4_small2);
//        datas.add(category);
//        category = new HomeCategory("品牌街",R.drawable.img_big_2,R.drawable.img_2_small1,R.drawable.img_2_small2);
//        datas.add(category);
//
//        category = new HomeCategory("金融街 包赚翻",R.drawable.img_big_1,R.drawable.img_3_small1,R.drawable.imag_3_small2);
//        datas.add(category);
//
//        category = new HomeCategory("超值购",R.drawable.img_big_0,R.drawable.img_0_small1,R.drawable.img_0_small2);
//        datas.add(category);
//
//        mAdapter = new HomeCategoryAdapter(datas);
//
//        mRecyclerView.setAdapter(mAdapter);
//
//        //设置边距
//        mRecyclerView.addItemDecoration(new DividerItemDecortion());
//
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        httpHelper.get(Contants.API.CAMPAIGN_HOME, new BaseCallback<List<HomeCampaign>>() {
            @Override
            public void onRequestBefore(Request request) {

            }

            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) {

            }

            @Override
            public void onSuccess(Response response, List<HomeCampaign> homeCampaigns) {
                initData(homeCampaigns);
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });

    }

    private void initData(List<HomeCampaign> homeCampaigns) {

        mAdapter = new HomeCategoryAdapter(homeCampaigns,getActivity());
        mAdapter.setOnCampaignClickListenner(new HomeCategoryAdapter.OnCampaignClickListenner() {
            @Override
            public void onClick(View view, Campaign campaign) {
                Toast.makeText(getContext(),"title=" + campaign.getTitle(),Toast.LENGTH_SHORT)
                        .show();
            }
        });

        mRecyclerView.setAdapter(mAdapter);
        //设置边距
        mRecyclerView.addItemDecoration(new DividerItemDecortion());

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
    }

    private void initSlider() {


        if (mBanner != null) {
            for (Banner banner: mBanner) {

                TextSliderView textSliderView = new TextSliderView(this.getActivity());
                textSliderView.image(banner.getImgUrl());
                textSliderView.description(banner.getName());
                textSliderView.setScaleType(BaseSliderView.ScaleType.Fit);
                mSliderLayout.addSlider(textSliderView);
            }
        }


//        TextSliderView textSliderView = new TextSliderView(this.getActivity());
//        textSliderView.image("http://m.360buyimg.com/mobilecms/s300x98_jfs/t2416/102/20949846/13425/a3027ebc/55e6d1b9Ne6fd6d8f.jpg");
//        textSliderView.description("新品推荐");
//        textSliderView.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
//            @Override
//            public void onSliderClick(BaseSliderView slider) {
//                Toast.makeText(HomeFragment.this.getActivity(),"新品推荐",Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        TextSliderView textSliderView2 = new TextSliderView(this.getActivity());
//        textSliderView2.image("http://m.360buyimg.com/mobilecms/s300x98_jfs/t1507/64/486775407/55927/d72d78cb/558d2fbaNb3c2f349.jpg");
//        textSliderView2.description("时尚男装");
//        textSliderView2.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
//            @Override
//            public void onSliderClick(BaseSliderView slider) {
//                Toast.makeText(HomeFragment.this.getActivity(),"时尚男装",Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        TextSliderView textSliderView3 = new TextSliderView(this.getActivity());
//        textSliderView3.image("http://m.360buyimg.com/mobilecms/s300x98_jfs/t1363/77/1381395719/60705/ce91ad5c/55dd271aN49efd216.jpg");
//        textSliderView3.description("家电秒杀");
//        textSliderView3.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
//            @Override
//            public void onSliderClick(BaseSliderView slider) {
//                Toast.makeText(HomeFragment.this.getActivity(),"家电秒杀",Toast.LENGTH_SHORT).show();
//            }
//        });


//        mSliderLayout.addSlider(textSliderView);
//        mSliderLayout.addSlider(textSliderView2);
//        mSliderLayout.addSlider(textSliderView3);

//        mSliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        // 自定义指示器
//        mSliderLayout.setCustomIndicator(indicator);
        mSliderLayout.setCustomAnimation(new DescriptionAnimation());
        mSliderLayout.setPresetTransformer(SliderLayout.Transformer.RotateUp);
        mSliderLayout.setDuration(3000);

        // 点击事件监听
        mSliderLayout.addOnPageChangeListener(new ViewPagerEx.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.d(TAG, "onPageScrolled: hahahahahahahah");
            }

            @Override
            public void onPageSelected(int position) {

                Log.d(TAG, "onPageSelected: hahahahahahhahh");
            }

            @Override
            public void onPageScrollStateChanged(int state) {

                Log.d(TAG, "onPageScrollStateChanged: hahahhaha");
            }
        });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        mSliderLayout.stopAutoCycle();
    }
}
