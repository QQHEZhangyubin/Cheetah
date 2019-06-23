package com.example.desk.adapter.holder;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.desk.R;
import com.example.desk.adapter.OnRecyclerViewListener;
import com.example.desk.been.PostBean;
import com.example.desk.ui.circle.CirclePresenter;
import com.example.desk.ui.webpreview.WebPreviewActivity;
import com.example.desk.util.StringUtils;

/**
 * Created by Credit on 2017/3/3.
 */

public class UrlViewHolder extends BaseCircleHolder {
    public LinearLayout urlBody;
    /**
     * 链接的图片
     */
    public ImageView urlImageIv;
    /**
     * 链接的标题
     */
    public TextView urlContentTv;

    public UrlViewHolder(Context context, ViewGroup root, int layoutRes, int viewType, CirclePresenter circlePresenter, OnRecyclerViewListener l) {
        super(context, root, layoutRes, viewType, circlePresenter, l);
    }

    @Override
    public void initViewStub() {
        if (mViewStub == null) {
            throw new IllegalArgumentException("viewStub is null...");
        }

        mViewStub.setLayoutResource(R.layout.viewstub_urlbody);
        View subViw = mViewStub.inflate();
        LinearLayout urlBodyView = (LinearLayout) subViw.findViewById(R.id.urlBody);
        if (urlBodyView != null) {
            urlBody = urlBodyView;
            urlImageIv = (ImageView) subViw.findViewById(R.id.urlImageIv);
            urlContentTv = (TextView) subViw.findViewById(R.id.urlContentTv);

            urlBody.setOnClickListener(v -> {
                if (mdata != null) {
                    Intent intent = new Intent(mContext, WebPreviewActivity.class);
                    intent.putExtra("PostBean", mdata);
                    mContext.startActivity(intent);
                }

            });
        }
    }

    @Override
    public void bindData(PostBean circleItem, int postion) {
        super.bindData(circleItem, postion);
        if (mdata != null) {
            String linkImg = circleItem.getLinkImg();
            String linkTitle = circleItem.getLinkTitle();
            if (linkImg.contains("http")) {
                Glide.with(mContext).load(linkImg).into(urlImageIv);
            } else {
                Glide.with(mContext).load(StringUtils.getImageDefaultURL(linkImg)).into(urlImageIv);
            }
            urlContentTv.setText(linkTitle);
            urlBody.setVisibility(View.VISIBLE);
            mUrlTipTv.setVisibility(View.VISIBLE);
        }
    }
}
