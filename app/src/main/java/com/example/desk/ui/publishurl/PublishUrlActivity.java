package com.example.desk.ui.publishurl;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.desk.R;
import com.example.desk.api.APIService;
import com.example.desk.been.ReturnMsg;
import com.example.desk.dao.UserDao;
import com.example.desk.mvp.MVPBaseActivity;
import com.example.desk.util.CommonUtils;
import com.example.desk.util.DialogUtil;
import com.example.desk.util.StaticClass;
import com.example.desk.util.StringUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class PublishUrlActivity extends MVPBaseActivity<PublishUrlContract.View, PublishUrlPresenter> implements PublishUrlContract.View {
    private static final String TAG = "PublishUrlActivity";
    private static final String LINK_ICON = "static/img/link_icon.png";
    @BindView(R.id.btn_toobar)
    Button btnToobar;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.content)
    EditText content;
    @BindView(R.id.icon)
    ImageView mIcon;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.share_content)
    TextView shareContent;
    @BindView(R.id.activity_publish)
    LinearLayout activityPublish;
    private Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_url);

        ButterKnife.bind(this);

        this.setSupportActionBar(toolbar);
        ActionBar actionBar = this.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();


        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if ("text/plain".equals(type)) {
                dealTextMessage(intent);
            } else {
                finish();
            }

        } else {
            finish();
        }

        toolbar.setNavigationOnClickListener((view -> {
            finish();
        }));

        /*if (Intent.ACTION_SEND.equals(action) && type != null) {
            if ("text/plain".equals(type)) {
                dealTextMessage(intent);
            } else if (type.startsWith("image/")) {
                dealPicStream(intent);
            }
        } else if (Intent.ACTION_SEND_MULTIPLE.equals(action) && type != null) {
            if (type.startsWith("image/")) {
                dealMultiplePicStream(intent);
            }
        }*/
    }


    private String shareDesc;
    private String shareTitle;
    private String html;
    private String shareUrl;
    private String icon;
    /**
     * 提取文本内容
     *
     * @param intent
     */
    void dealTextMessage(Intent intent) {
        DialogUtil.showToastClickLoadingDialog(this, "提取内容中...", false, view -> {
            DialogUtil.dismissToatLoadingDialog();
            PublishUrlActivity.this.finish();
        });
        //获取分享链接与内容
        String share = intent.getStringExtra(Intent.EXTRA_TEXT);
        shareTitle = intent.getStringExtra(Intent.EXTRA_TITLE);
        //若标题为null，则截取
        if (TextUtils.isEmpty(shareTitle)) {
            shareTitle = share.substring(0, share.indexOf("http://"));
        }

        //获取描述内容长度
        int len = share.length() > 100 ? 100 : share.length();
        //描述
        shareDesc = share.substring(0, len);
        //提取URL
        shareUrl = StringUtils.getStringUrl(share);
        //
        Observable.create((ObservableEmitter<String> e) -> {
            //获取html网页
            html = StringUtils.getHtml(shareUrl);
            //提取图片url
            List<String> imgSrcList = StringUtils.getImgSrcList(html);
            if (imgSrcList.size() == 0) {
                e.onNext(LINK_ICON); //没有图片，则默认图标
            } else {
                e.onNext(imgSrcList.get(0));
            }
            e.onComplete();

        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(c -> {
                    DialogUtil.dismissToatLoadingDialog();
                    Log.e(TAG, "获取分享链接中的图片:" + c);
                    icon = c;
                    if (!c.equals(LINK_ICON)) {
                        Picasso.with(this).load(c).into(mIcon);
                        // Glide.with(PublishUrlActivity.this).load(c).into(mIcon);
                        title.setText(shareTitle);
                        shareContent.setText(share);
                    }
                }, e -> {
                    DialogUtil.showToastSureBtnErrDialog("提取内容失败..", "确定");
                });


    }

    @OnClick(R.id.btn_toobar)
    public void onViewClicked(View v) {

        String s = content.getText().toString();
        CommonUtils.hideSoftInput(this, v); //关闭键盘
        v.setEnabled(false); //防止多次点击
        DialogUtil.showToastLoadingDialog(this, "正在分享", false);
        mPresenter.addUrlPost(UserDao.getInstance().getUserId(), icon, shareTitle, s, shareDesc, shareUrl, 2, new Observer<ReturnMsg>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(ReturnMsg returnMsg) {
                if (returnMsg.getIs() == APIService.SUCCESS) {
                    Toast.makeText(PublishUrlActivity.this, "分享成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(PublishUrlActivity.this, returnMsg.getMsg(), Toast.LENGTH_SHORT).show();

                }

                if (disposable != null && !disposable.isDisposed()) {
                    disposable.dispose();
                }
                DialogUtil.dismissToatLoadingDialog();
                v.setEnabled(true);
                PublishUrlActivity.this.finish();
            }

            @Override
            public void onError(Throwable e) {
                DialogUtil.showToastErrDialog("分享失败");
                Toast.makeText(PublishUrlActivity.this, "分享失败，请检查网络", Toast.LENGTH_SHORT).show();
                PublishUrlActivity.this.finish();
                v.setEnabled(true);
            }

            @Override
            public void onComplete() {

            }
        });
        /*
        model.addUrlPost(UserDao.getInstance().getUserId(), icon, shareTitle, s, shareDesc, shareUrl, 2, new Observer<ReturnMsg>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onNext(ReturnMsg returnMsg) {

                if (returnMsg.getIs() == RequestApi.SUCCESS) {
                    Toast.makeText(PublishUrlActivity.this, "分享成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(PublishUrlActivity.this, returnMsg.getMsg(), Toast.LENGTH_SHORT).show();

                }

                if (disposable != null && !disposable.isDisposed()) {
                    disposable.dispose();
                }
                DialogUtil.dismissToatLoadingDialog();
                v.setEnabled(true);
                PublishUrlActivity.this.finish();
            }

            @Override
            public void onError(Throwable e) {
                DialogUtil.showToastErrDialog("分享失败");
                Toast.makeText(PublishUrlActivity.this, "分享失败，请检查网络", Toast.LENGTH_SHORT).show();
                PublishUrlActivity.this.finish();
                v.setEnabled(true);
            }

            @Override
            public void onComplete() {

            }
        });
        */

    }
}
