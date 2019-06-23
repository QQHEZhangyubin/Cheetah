package com.example.desk.ui.publishvideo;

import android.content.Context;

import com.example.desk.api.APIWrapper;
import com.example.desk.been.ReturnMsg;
import com.example.desk.entity.Token;
import com.example.desk.mvp.BasePresenterImpl;
import com.example.desk.util.StaticClass;
import com.example.desk.util.StringUtils;
import com.example.desk.util.TLog;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

import static com.example.desk.api.APIService.TOLOGIN;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class PublishVideoPresenter extends BasePresenterImpl<PublishVideoContract.View> implements PublishVideoContract.Presenter{

    private String imgshoottoken;
    private String videotoken;
    private String imgshooturl;//七牛云返回的视频截图url
    private String videourl;//七牛云返回的视频url
    @Override
    public void addVideoPost(String userId, String content, int type, String videoPath, String videoImg, Observer<ReturnMsg> observer) {
        //TODO：根据videoPath,videoImg本地路径上传到七牛云，在把七牛云返回结果存到本地
        Map<String, RequestBody> request = new HashMap<>();
        request.put("content", RequestBody.create(null, content));
        request.put("userId", RequestBody.create(null, userId));
        request.put("type", RequestBody.create(null, type + ""));

        String VideoFileName = StringUtils.getURLFileName(videoPath);
        RequestBody video = RequestBody.create(MediaType.parse("'video/mpeg'"), new File(videoPath));
        request.put("video\"; filename=\"" + VideoFileName, video);

        String videoImgName = StringUtils.getURLFileName(videoImg);
        RequestBody photo = RequestBody.create(MediaType.parse("image/png"), new File(videoImg));
        request.put("videoImg\"; filename=\"" + videoImgName, photo);
        Observable<ReturnMsg> x = APIWrapper.getInstance().create("post", request);
        execute(x,observer);
    }

    @Override
    public void addVideoPostQiNiuYun(String userId, String content, int type, String videoPath, String videoImg,Observer<ReturnMsg> observer) {
        APIWrapper.getInstance().GETTOKENVIDEO()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Token>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Token token) {
                     videotoken = token.getUptoken();//获取服务端传回的videotoken
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                        // 重用uploadManager。一般地，只需要创建一个uploadManager对象
                        UploadManager uploadManager = new UploadManager();
                        uploadManager.put(videoImg, System.currentTimeMillis()+"123.jpg", videotoken, new UpCompletionHandler() {
                            @Override
                            public void complete(String key, ResponseInfo info, JSONObject response) {
                                //先看看是不是主线程，预期是
                                if (info.isOK()){
                                    try {
                                        imgshooturl = response.getString("key");
                                        TLog.error("imgshoot upload success " + imgshooturl);
                                        uploadManager.put(videoPath, System.currentTimeMillis()+".mp4", videotoken, new UpCompletionHandler() {
                                            @Override
                                            public void complete(String key, ResponseInfo info, JSONObject response) {
                                                //先看看是不是主线程，预期是
                                                if (info.isOK()){
                                                    try {
                                                        videourl = response.getString("key");
                                                        TLog.error("video upload success " + videourl);
                                                        Map<String, RequestBody> request = new HashMap<>();
                                                        request.put("content", RequestBody.create(null, content));
                                                        request.put("userId", RequestBody.create(null, userId));
                                                        request.put("type", RequestBody.create(null, type + ""));
                                                        request.put("videourl", RequestBody.create(null, StaticClass.videoIP+videourl));
                                                        request.put("imgshooturl", RequestBody.create(null, StaticClass.videoIP+imgshooturl));
                                                        Observable<ReturnMsg> x = APIWrapper.getInstance().create("post", request);
                                                        execute(x,observer);
                                                    } catch (JSONException e) {

                                                    }
                                                }
                                            }
                                        },null);
                                        /*
                                       */
                                    } catch (JSONException e) {
                                    }
                                }
                            }
                        },null);


                    }
                });
    }

    private void execute(Observable<ReturnMsg> observeable, final Observer<ReturnMsg> observer) {
        /**
         * RxJava将这个操作符实现为repeat方法。它不是创建一个Observable，
         * 而是重复发射原始Observable的数据序列，这个序列或者是无限的，或者通过repeat(n)指定重复次数
         */
        observeable.repeat(1);
        observeable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ReturnMsg>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ReturnMsg returnMsg) {
                        TLog.error("onNext: 返回的数据+" + returnMsg.toString());
                        if (returnMsg.getIs() != TOLOGIN) {
                            Observable.just(returnMsg)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(observer);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        TLog.analytics("onError: 出错啦" + e.getMessage());
                        observer.onError(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }
}
