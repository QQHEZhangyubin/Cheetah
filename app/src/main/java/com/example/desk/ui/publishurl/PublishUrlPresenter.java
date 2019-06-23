package com.example.desk.ui.publishurl;

import android.content.Context;

import com.example.desk.api.APIWrapper;
import com.example.desk.been.ReturnMsg;
import com.example.desk.mvp.BasePresenterImpl;
import com.example.desk.util.TLog;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;

import static com.example.desk.api.APIService.TOLOGIN;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class PublishUrlPresenter extends BasePresenterImpl<PublishUrlContract.View> implements PublishUrlContract.Presenter{

    @Override
    public void addUrlPost(String userId, String icon, String title, String content, String shareDesc, String url, int type, Observer<ReturnMsg> observer) {
        Map<String, RequestBody> photos = new HashMap<>();
        photos.put("content", RequestBody.create(null, content));
        photos.put("userId", RequestBody.create(null, userId));
        photos.put("type", RequestBody.create(null, type + ""));
        photos.put("shareIcon", RequestBody.create(null, icon));
        photos.put("shareTitle", RequestBody.create(null, title));
        photos.put("shareDesc", RequestBody.create(null, shareDesc));
        photos.put("shareUrl", RequestBody.create(null, url));
        Observable<ReturnMsg> p = APIWrapper.getInstance().create("post", photos);
        execute(p,observer);
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
                        TLog.analytics("onNext: 返回的数据+" + returnMsg.toString());
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
