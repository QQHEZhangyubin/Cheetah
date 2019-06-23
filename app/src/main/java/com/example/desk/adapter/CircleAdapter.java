package com.example.desk.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.example.desk.BaseRecyclerAdapter;
import com.example.desk.R;
import com.example.desk.adapter.holder.BaseViewHolder;
import com.example.desk.adapter.holder.HeadVIewHolder;
import com.example.desk.adapter.holder.ImageViewHolder;
import com.example.desk.adapter.holder.UrlViewHolder;
import com.example.desk.adapter.holder.VideoViewHolder;
import com.example.desk.been.PostBean;
import com.example.desk.ui.circle.CirclePresenter;
import com.example.desk.ui.videolist.visibility.items.ListItem;
import com.example.desk.ui.videolist.visibility.scroll.ItemsProvider;

public class CircleAdapter extends BaseRecyclerAdapter<PostBean> implements ItemsProvider {
    public final static int HEAD_TYPE = 100;

    public final static int TYPE_IMG = 1;
    public final static int TYPE_URL = 2;
    public final static int TYPE_VIDEO = 3;


    public static final int HEADVIEW_SIZE = 1;
    private RecyclerView mRecylerView;
    private CirclePresenter presenter;

    public CircleAdapter(Context mContext) {
        super(mContext);
    }

    public CircleAdapter(Context mContext, CirclePresenter presenter, RecyclerView mRecylerView) {
        super(mContext);
        this.presenter = presenter;
        this.mRecylerView = mRecylerView;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == HEAD_TYPE) {
            return new HeadVIewHolder(mContext, parent, R.layout.head_circle, listener);
        } else if (viewType == TYPE_URL) {
            return new UrlViewHolder(mContext, parent, R.layout.adapter_circle_item, viewType, presenter, listener);
        } else if (viewType == TYPE_VIDEO) {
            return new VideoViewHolder(mContext, parent, R.layout.adapter_circle_item, viewType, presenter, listener);
        } else {
            return new ImageViewHolder(mContext, parent, R.layout.adapter_circle_item, viewType, presenter, listener);
        }
    }
    @Override
    public int getItemCount() {
        return mData == null ? 1 : mData.size() + HEADVIEW_SIZE;
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        BaseViewHolder baseViewHolder = (BaseViewHolder) holder;

        if (position >= HEADVIEW_SIZE) {
            baseViewHolder.bindData(mData.get(position - HEADVIEW_SIZE), position - HEADVIEW_SIZE);
        } else {
            baseViewHolder.bindData(null, position);
        }
    }

    @Override
    public ListItem getListItem(int position) {
        RecyclerView.ViewHolder holder = mRecylerView.findViewHolderForAdapterPosition(position);
        if (holder instanceof ListItem) {
            return (ListItem) holder;
        }
        return null;
    }

    @Override
    public int listItemSize() {
        return getItemCount();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return HEAD_TYPE;
        }
        return mData.get(position - HEADVIEW_SIZE).getType();
    }

}
