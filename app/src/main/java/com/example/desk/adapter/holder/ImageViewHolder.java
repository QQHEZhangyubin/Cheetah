package com.example.desk.adapter.holder;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;


import com.example.desk.R;
import com.example.desk.adapter.OnRecyclerViewListener;
import com.example.desk.been.ImageBean;
import com.example.desk.been.PostBean;
import com.example.desk.ui.ImagePagerActivity2;
import com.example.desk.ui.circle.CirclePresenter;
import com.example.desk.view.MultiImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Credit on 2017/3/3.
 */

public class ImageViewHolder extends BaseCircleHolder {
    private MultiImageView multiImageView;

    public ImageViewHolder(Context context, ViewGroup root, int layoutRes, int viewType, CirclePresenter circlePresenter, OnRecyclerViewListener l) {
        super(context, root, layoutRes, viewType, circlePresenter, l);
    }

    @Override
    public void initViewStub() {
        if (mViewStub == null) {
            throw new IllegalArgumentException("viewStub is null...");
        }
        mViewStub.setLayoutResource(R.layout.viewstub_imgbody);
        View subView = mViewStub.inflate();
        MultiImageView multiImageView = (MultiImageView) subView.findViewById(R.id.multiImagView);
        if (multiImageView != null) {
            this.multiImageView = multiImageView;
        }

    }

    @Override
    public void bindData(PostBean circleItem, int postion) {
        super.bindData(circleItem, postion);
        if (mdata != null) {
            final List<ImageBean> photos = circleItem.getPostImages();
            if (photos != null && photos.size() > 0) {
                multiImageView.setVisibility(View.VISIBLE);
                multiImageView.setList(photos);
                multiImageView.setOnItemClickListener(new MultiImageView.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        //imagesize是作为loading时的图片size
                        ImagePagerActivity2.ImageSize imageSize = new ImagePagerActivity2.ImageSize(view.getMeasuredWidth(), view.getMeasuredHeight());

                        List<String> photoUrls = new ArrayList<String>();
                        for (ImageBean photoInfo : photos) {
                            photoUrls.add(photoInfo.getUrl());
                        }
                        ImagePagerActivity2.startImagePagerActivity(mContext, photoUrls, position, imageSize);


                    }
                });
            } else {
                multiImageView.setVisibility(View.GONE);
            }
        }
    }
}
