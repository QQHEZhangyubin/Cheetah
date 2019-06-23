package com.example.desk.ui.publishvideo;


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
import android.widget.Toast;

import com.example.desk.R;
import com.example.desk.api.APIService;
import com.example.desk.api.GsonTools;
import com.example.desk.been.PostBean;
import com.example.desk.been.ReturnMsg;
import com.example.desk.dao.UserDao;
import com.example.desk.mvp.MVPBaseActivity;
import com.example.desk.ui.videopreview.VideoPreviewActivity;
import com.example.desk.util.DialogUtil;
import com.example.desk.util.NetUtils;
import com.example.desk.util.TLog;
import com.mabeijianxi.smallvideorecord2.MediaRecorderActivity;
import com.qiniu.android.common.FixedZone;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class PublishVideoActivity extends MVPBaseActivity<PublishVideoContract.View, PublishVideoPresenter> implements PublishVideoContract.View {
    @BindView(R.id.btn_toobar)
    Button btnToobar;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.content)
    EditText content;
    @BindView(R.id.icon)
    ImageView mIcon;
    @BindView(R.id.activity_publish_video)
    LinearLayout activityPublishVideo;
    private String output_directory;
    private String video_uri;
    private String video_screenshot;
    private Disposable disposable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_video);
        ButterKnife.bind(this);
        this.setSupportActionBar(mToolbar);
        ActionBar actionBar = this.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        initEvent();
        setData();


    }

    private void setData() {
        Intent intent = getIntent();
        if (intent != null) {
            output_directory = intent.getStringExtra(MediaRecorderActivity.OUTPUT_DIRECTORY);
            video_uri = intent.getStringExtra(MediaRecorderActivity.VIDEO_URI);
            video_screenshot = intent.getStringExtra(MediaRecorderActivity.VIDEO_SCREENSHOT);

            if (!TextUtils.isEmpty(video_screenshot)) {
                //Glide.with(PublishVideoActivity.this).load(new File(video_screenshot)).into(mIcon);
                Picasso.with(this).load(new File(video_screenshot)).into(mIcon);
            }
        }
    }

    private void initEvent() {
        mToolbar.setNavigationOnClickListener((view -> {
            onBackPressed();
        }));
    }

    @OnClick(R.id.icon)
    public void seeVideo(View v) {
        if (!TextUtils.isEmpty(video_uri)) {
            Intent intent = new Intent(this, VideoPreviewActivity.class);
            intent.putExtra(VideoPreviewActivity.VIDEO_PATH, video_uri);
            startActivity(intent);
        }
    }

    //TODO:View 为final
    @OnClick(R.id.btn_toobar)
    public void publish(final View v) {
        if (!NetUtils.isNetworkAvailable(this)) {
            Toast.makeText(this, "网络不可用", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(video_uri) || TextUtils.isEmpty(video_screenshot)) {
            Toast.makeText(this, "获取视频路径失败", Toast.LENGTH_SHORT).show();
            return;
        }
        String s = content.getText().toString();
        v.setEnabled(false);
        DialogUtil.showToastLoadingDialog(this, "正在上传视频", false);
       /*
        model.addVideoPost(UserDao.getInstance().getUserId(), s, 3, video_uri, video_screenshot, new Observer<ReturnMsg>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onNext(ReturnMsg returnMsg) {
                DialogUtil.dismissToatLoadingDialog();
                if (returnMsg.getIs() == APIService.SUCCESS) {
                    setResult(returnMsg);
                } else {
                    v.setEnabled(true);
                    TLog.error("onNext:上传失败: " + returnMsg.getMsg());
                    Toast.makeText(PublishVideoActivity.this, "上传失败：" + returnMsg.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable e) {
                DialogUtil.showToastErrDialog("上传失败");
                v.setEnabled(true);

                TLog.error("onError:上传失败: " + e.getMessage());
                Toast.makeText(PublishVideoActivity.this, "上传失败：" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onComplete() {

            }
        });
        */
       //TODO：先从自己服务器得到token ,再把video_uri,video_screenshot上传到七牛云

        mPresenter.addVideoPostQiNiuYun(UserDao.getInstance().getUserId(), s, 3, video_uri, video_screenshot, new Observer<ReturnMsg>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onNext(ReturnMsg returnMsg) {
                DialogUtil.dismissToatLoadingDialog();
                if (returnMsg.getIs() == APIService.SUCCESS) {
                    setResult(returnMsg);
                } else {
                    v.setEnabled(true);
                    TLog.error("onNext:上传失败: " + returnMsg.getMsg());
                    Toast.makeText(PublishVideoActivity.this, "上传失败：" + returnMsg.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable e) {
                DialogUtil.showToastErrDialog("上传失败");
                v.setEnabled(true);

                TLog.error("onError:上传失败: " + e.getMessage());
                Toast.makeText(PublishVideoActivity.this, "上传失败：" + e.getMessage(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onComplete() {

            }
        });
/*
        mPresenter.addVideoPost(UserDao.getInstance().getUserId(), s, 3, video_uri, video_screenshot, new Observer<ReturnMsg>() {
            @Override
            public void onSubscribe(Disposable d) {
                 disposable = d;

            }

            @Override
            public void onNext(ReturnMsg returnMsg) {
                DialogUtil.dismissToatLoadingDialog();
                if (returnMsg.getIs() == APIService.SUCCESS) {
                    setResult(returnMsg);
                } else {
                    v.setEnabled(true);
                    TLog.error("onNext:上传失败: " + returnMsg.getMsg());
                    Toast.makeText(PublishVideoActivity.this, "上传失败：" + returnMsg.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable e) {
                DialogUtil.showToastErrDialog("上传失败");
                v.setEnabled(true);

                TLog.error("onError:上传失败: " + e.getMessage());
                Toast.makeText(PublishVideoActivity.this, "上传失败：" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onComplete() {

            }
        });
*/
    }

    public void setResult(ReturnMsg returnMsg) {
        PostBean bean = GsonTools.changeGsonToBean(returnMsg.getData(), PostBean.class);
        Intent intent = new Intent();
        intent.putExtra("PostBean", bean);
        //intent.setAction(CircleActivity.VIDEO_BROAD_ACTION);
        sendBroadcast(intent);
        onBackPressed();
        //TODO:
    }

    @Override
    public void onBackPressed() {
        /**
         * 上传之后或后退，删除视频
         */
        File file = new File(video_uri);
        file.delete();
        File file1 = new File(video_screenshot);
        file1.delete();
        super.onBackPressed();
        finish();
    }
}
