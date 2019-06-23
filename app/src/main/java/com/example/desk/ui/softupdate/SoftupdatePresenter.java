package com.example.desk.ui.softupdate;

import android.content.Context;

import com.example.desk.api.APIWrapper;
import com.example.desk.entity.Soft2;
import com.example.desk.mvp.BasePresenterImpl;

import io.reactivex.Observer;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;




/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class SoftupdatePresenter extends BasePresenterImpl<SoftupdateContract.View> implements SoftupdateContract.Presenter{

    private Soft2 softT;
    @Override
    public void CheckUpdate() {
        APIWrapper.getInstance().CheckSoftWareUpdate()
                .subscribeOn(io.reactivex.schedulers.Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                /*
                .subscribe(new Subscriber<Soft2>() {
                    @Override
                    public void onCompleted() {
                        mView.HadNewSoft(softT);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Soft2 soft2) {
                        softT = soft2;
                    }
                });
                */
                .subscribe(new Observer<Soft2>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Soft2 soft2) {
                        softT = soft2;
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        mView.HadNewSoft(softT);
                    }
                });
    }
}
