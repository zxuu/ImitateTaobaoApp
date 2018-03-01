package com.zxu.cniao5shop.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zxu.cniao5shop.R;
import com.zxu.cniao5shop.bean.HomeCategory;
import com.zxu.cniao5shop.bean.recyclerbean.Campaign;
import com.zxu.cniao5shop.bean.recyclerbean.HomeCampaign;

import java.util.List;

/**
 * Created by zx on 2018/2/28.
 */

public class HomeCategoryAdapter extends RecyclerView.Adapter<HomeCategoryAdapter.ViewHolder> {

    private static int VIEW_TYPE_L = 0;
    private static int VIEW_TYPE_R = 1;

    private LayoutInflater mInflater;

    private List<HomeCampaign> mDatas;

    private Context mContext;

    private OnCampaignClickListenner mListener;

    public void setOnCampaignClickListenner(OnCampaignClickListenner listenner) {
        this.mListener = listenner;
    }


    public HomeCategoryAdapter(List<HomeCampaign> datas, Context context) {
        mDatas = datas;
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        mInflater = LayoutInflater.from(parent.getContext());
        if (viewType == VIEW_TYPE_R) {
            return new ViewHolder(mInflater.inflate(R.layout.template_home_cardview2
            ,parent,false));
        }
        return new ViewHolder(mInflater.inflate(R.layout.template_home_cardview
                ,parent,false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        HomeCampaign category = mDatas.get(position);
//        holder.textTitle.setText(category.getTitle());
//        holder.imageViewBig.setImageResource(category.getCpOne());
//        holder.imageViewSmallTop.setImageResource(category.getImgSmallTop());
//        holder.imageViewSmallBottom.setImageResource(category.getImgSmallBottom());

        // 从网上下载图片并装载到view里面
        holder.textTitle.setText(category.getTitle());
        Picasso.with(mContext).load(category.getCpOne().getImgUrl()).into(holder.imageViewBig);
        Picasso.with(mContext).load(category.getCpTwo().getImgUrl()).into(holder.imageViewSmallTop);
        Picasso.with(mContext).load(category.getCpThree().getImgUrl()).into(holder.imageViewSmallBottom);

    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }


    @Override
    public int getItemViewType(int position) {
        if (position % 2 == 0 ) {
            return VIEW_TYPE_R;
        } else
            return VIEW_TYPE_L;
    }

     class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView textTitle;
        ImageView imageViewBig;
        ImageView imageViewSmallTop;
        ImageView imageViewSmallBottom;

        public ViewHolder(View itemView) {
            super(itemView);

            textTitle = (TextView) itemView.findViewById(R.id.text_title);
            imageViewBig = (ImageView) itemView.findViewById(R.id.imgview_big);
            imageViewSmallTop = (ImageView) itemView.findViewById(R.id.imgview_small_top);
            imageViewSmallBottom = (ImageView) itemView.findViewById(R.id.imgview_small_bottom);

            imageViewBig.setOnClickListener(this);
            imageViewSmallBottom.setOnClickListener(this);
            imageViewSmallTop.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            HomeCampaign homeCampaign = mDatas.get(getLayoutPosition());
            if (mListener != null) {
                switch (view.getId()) {
                    case R.id.imgview_big:
                        mListener.onClick(view,homeCampaign.getCpOne());
                        break;
                    case R.id.imgview_small_bottom:
                        mListener.onClick(view,homeCampaign.getCpThree());
                        break;
                    case R.id.imgview_small_top:
                        mListener.onClick(view,homeCampaign.getCpTwo());
                        break;
                }
            }
         }
    }

    public interface OnCampaignClickListenner{

        void onClick(View view , Campaign campaign);

    }
}
