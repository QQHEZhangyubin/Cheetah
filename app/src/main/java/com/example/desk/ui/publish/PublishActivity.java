package com.example.desk.ui.publish;


import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.desk.R;
import com.example.desk.api.APIService;
import com.example.desk.api.GsonTools;
import com.example.desk.been.PostBean;
import com.example.desk.been.ReturnMsg;
import com.example.desk.dao.UserDao;
import com.example.desk.mvp.MVPBaseActivity;
import com.example.desk.ui.ImagePagerActivity2;
import com.example.desk.util.CommonUtils;
import com.example.desk.util.DialogUtil;
import com.example.desk.util.ImageUtils;
import com.example.desk.util.NetUtils;
import com.example.desk.util.TLog;
import com.example.desk.view.MultiImageView;
import com.example.photopicker.PhotoPicker;
import com.example.photopicker.PhotoPreview;

import java.io.File;
import java.util.ArrayList;
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

public class PublishActivity extends MVPBaseActivity<PublishContract.View, PublishPresenter> implements PublishContract.View {
    @BindView(R.id.btn_toobar)
    Button btnToobar;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.content)
    EditText mContent;
    @BindView(R.id.multiImagView)
    MultiImageView mMultiImagView;
    //@BindView(R.id.select_image)
    //Button  mSelectImage;
    @BindView(R.id.activity_publish)
    LinearLayout activityPublish;
    private Disposable disposable;
    private int REQUEST_CODE = 100;
    private ArrayList<String> photos = new ArrayList<>();
    private String savePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "cirlce";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);
        ButterKnife.bind(this);

        this.setSupportActionBar(mToolbar);
        ActionBar actionBar = this.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        //单张图片时，显示小图
        mMultiImagView.setShowThumbnailsMode(true);
        initEvent();
    }

    private void initEvent() {
        /*
        mMultiImagView.setOnItemClickListener((view, position) -> {
            //imagesize是作为loading时的图片size
            Toast.makeText(PublishActivity.this,"sa",Toast.LENGTH_SHORT).show();
            ImagePagerActivity2.ImageSize imageSize = new ImagePagerActivity2.ImageSize(view.getMeasuredWidth(), view.getMeasuredHeight());
            ImagePagerActivity2.startImagePagerActivityForResult(PublishActivity.this, REQUEST_CODE, photos, position, imageSize, true);
        });*/
        mToolbar.setNavigationOnClickListener((view -> {
            finish();
        }));
    }


    @OnClick(R.id.btn_toobar)
    public void publish(final View v) {

        if (!NetUtils.isNetworkConnected(this)) {
            Toast.makeText(this, "网络不可用", Toast.LENGTH_SHORT).show();
            return;
        }
        String s = mContent.getText().toString();
        if (TextUtils.isEmpty(s)) {
            Toast.makeText(this, "填写15个字以上才是好同志！！！", Toast.LENGTH_SHORT).show();
            return;
        }


        CommonUtils.hideSoftInput(this, v);
        v.setEnabled(false);
        DialogUtil.showToastClickLoadingDialog(this, "正在发表中..", false, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogUtil.dismissToatLoadingDialog();
            }
        });
        Observable.create((ObservableEmitter<List<String>> e) -> {
            /**
             * 压缩图片，再上传。 在wifi：max size 256kb,在手机网络下:max 128kb
             */

            List<String> list;
            if (NetUtils.isWifi(PublishActivity.this)) {
                list = ImageUtils.compressImages(photos, savePath, 256l);
            } else {
                list = ImageUtils.compressImages(photos, savePath, 128l);
            }
            e.onNext(list);
            e.onComplete();
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(r -> {
                    TLog.analytics("publish: 处理后的图片 :" + r.toString());
                    /*model.addPost(UserDao.getInstance().getUserId(), s, 1, r, new Observer<ReturnMsg>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            disposable = d;
                        }

                        @Override
                        public void onNext(ReturnMsg returnMsg) {
                            TLog.analytics("onNext: " + returnMsg.toString());
                            DialogUtil.dismissToatLoadingDialog();
                            v.setEnabled(true);
                            if (returnMsg.getIs() == RequestApi.SUCCESS) {
                                setResult(returnMsg);
                            } else {
                                Toast.makeText(PublishActivity.this, returnMsg.getMsg(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            v.setEnabled(true);
                            DialogUtil.showToastErrDialog("发送失败" + e.getMessage());
                            TLog.error("上传失败 onError: " + e.getMessage());
                        }

                        @Override
                        public void onComplete() {

                        }
                    });*/
                    //TODO：先把 r (图片本地路径集合上传到七牛云，在把七牛云返回结果中的图片url封装成list(也可以理解为r2,在上传到自己服务器))
                    if (r.size() == 0 || r == null){
                    mPresenter.addPost(UserDao.getInstance().getUserId(), s, 1, r, new Observer<ReturnMsg>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            disposable = d;
                        }

                        @Override
                        public void onNext(ReturnMsg returnMsg) {
                            TLog.analytics("onNext: " + returnMsg.toString());
                            DialogUtil.dismissToatLoadingDialog();
                            v.setEnabled(true);
                            if (returnMsg.getIs() == APIService.SUCCESS) {
                                setResult(returnMsg);
                            } else {
                                Toast.makeText(PublishActivity.this, returnMsg.getMsg(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            v.setEnabled(true);
                            //DialogUtil.showToastErrDialog("发送失败/或者相应不及时，请返回上一页面查看是否发布成功" + e.getMessage().toString());
                            DialogUtil.showToastErrDialog("发送失败/响应不及时，请返回上一页面刷新查看");
                            TLog.error("上传失败 onError: " + e.getMessage());
                           /* if (e.getMessage() == null)
                            {
                                setResult2();
                            }*/
                        }

                        @Override
                        public void onComplete() {

                        }
                    });

                    }else {
                        mPresenter.addPostQiniuYun(UserDao.getInstance().getUserId(), s, 1, r, new Observer<ReturnMsg>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                                disposable = d;
                            }

                            @Override
                            public void onNext(ReturnMsg returnMsg) {
                                TLog.analytics("onNext: " + returnMsg.toString());
                                DialogUtil.dismissToatLoadingDialog();
                                v.setEnabled(true);
                                if (returnMsg.getIs() == APIService.SUCCESS) {
                                    setResult(returnMsg);
                                } else {
                                    Toast.makeText(PublishActivity.this, returnMsg.getMsg(), Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                v.setEnabled(true);

                                TLog.error("上传失败 onError: " + e.getMessage());
                                DialogUtil.showToastErrDialog("发送失败/响应不及时，请返回上一页面刷新查看");

                            }

                            @Override
                            public void onComplete() {

                            }
                        });
                    }


                });

    }

    private void setResult2() {
        this.finish();
    }

    /*

    @OnClick(R.id.select_image)
    public void SelectIamge(View v) {
        // R.id.select_image
        PhotoPicker.builder()
                .setPhotoCount(6)
                .setGridColumnCount(4)
                .setSelected(photos)
                .start(this);
    }

    */
    public void setResult(ReturnMsg msg) {
        PostBean bean = GsonTools.changeGsonToBean(msg.getData(), PostBean.class);
        Intent intent = new Intent();
        intent.putExtra("PostBean", bean);
        this.setResult(RESULT_OK, intent);
        this.finish();

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        File file = new File(savePath);
        if (file.exists()) {
            file.delete();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == PhotoPicker.REQUEST_CODE || requestCode == PhotoPreview.REQUEST_CODE || requestCode == REQUEST_CODE) {
                if (data != null) {
                    if (requestCode == REQUEST_CODE) {
                        photos = data.getStringArrayListExtra(ImagePagerActivity2.INTENT_IMGURLS);
                    } else {
                        photos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);

                    }
                   // getResult();
                }
            }


        }

    }
    /*
    private void getResult() {
        if (photos != null) {
            mMultiImagView.setStringList(photos);
            if (photos.size() > 5) {
                mSelectImage.setVisibility(View.GONE);
            } else {
                mSelectImage.setVisibility(View.VISIBLE);
            }
        }
    }
    */
}
