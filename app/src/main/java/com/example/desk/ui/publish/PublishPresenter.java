package com.example.desk.ui.publish;

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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

public class PublishPresenter extends BasePresenterImpl<PublishContract.View> implements PublishContract.Presenter{

    private String imgToken;
    @Override
    public void addPost(String userId, String content, int type, List<String> imgs, Observer<ReturnMsg> observer) {
        Map<String, RequestBody> photos = new HashMap<>();
        photos.put("content", RequestBody.create(null, content));
        photos.put("userId", RequestBody.create(null, userId));
        photos.put("type", RequestBody.create(null, type + ""));
        if (imgs != null && imgs.size() > 0) {
            photos.put("haveimg", RequestBody.create(null, "have"));
            for (String url : imgs) {
                String urlFileName = StringUtils.getURLFileName(url);
                RequestBody photo = RequestBody.create(MediaType.parse("image/png"), new File(url));
                photos.put("photos\"; filename=\"" + urlFileName, photo);
            }
        } else {
            photos.put("haveimg", RequestBody.create(null, "done"));
        }

        Observable<ReturnMsg> z = APIWrapper.getInstance().create("post1", photos);
        execute(z,observer);
    }

    @Override
    public void addPostQiniuYun(String userId, String content, int type, List<String> imgs, Observer<ReturnMsg> observer) {
        APIWrapper.getInstance().GETTOKEN()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Token>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Token token) {
                        imgToken = token.getUptoken();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        UploadManager uploadManager = new UploadManager();
                        List<String> IMGLIST = new ArrayList<>();
                        for (String imgurl:
                             imgs) {
                            uploadManager.put(imgurl, System.currentTimeMillis()+".jpg", imgToken, new UpCompletionHandler() {
                                @Override
                                public void complete(String key, ResponseInfo info, JSONObject response) {
                                    if (info.isOK()){
                                        try {
                                            String imgres = response.getString("key");
                                            TLog.error("imgres upload success " + imgres);
                                            IMGLIST.add(StaticClass.imgIP +imgres);
                                            if (IMGLIST.size() == imgs.size()){
                                                TLog.error("第一次我被执行");
                                                String mListStr = listToString2(IMGLIST);
                                                TLog.error(mListStr);
                                                Map<String, RequestBody> photos = new HashMap<>();
                                                photos.put("content", RequestBody.create(null, content));
                                                photos.put("userId", RequestBody.create(null, userId));
                                                photos.put("type", RequestBody.create(null, type + ""));
                                                photos.put("imgs",RequestBody.create(null,mListStr));
                                                photos.put("haveimg", RequestBody.create(null, "have"));
                                                Observable<ReturnMsg> z = APIWrapper.getInstance().create("post1", photos);
                                                execute(z,observer);
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }

                                private String listToString2(List<String> mList) {

                                    String convertedListStr = "";
                                    if (null != mList && mList.size() > 0) {
                                        String[] mListArray = mList.toArray(new String[mList.size()]);
                                        for (int i = 0; i < mListArray.length; i++) {
                                            if (i < mListArray.length - 1) {
                                                convertedListStr += mListArray[i] + ",";
                                            } else {
                                                convertedListStr += mListArray[i];
                                            }
                                        }
                                        return convertedListStr;
                                    } else return "List is null!!!";
                                }

                            },null);
                        }
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
                        TLog.error("onError: 出错啦" + e.toString());
                        observer.onError(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }
}
