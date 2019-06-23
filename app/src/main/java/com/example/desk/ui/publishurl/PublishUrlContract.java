package com.example.desk.ui.publishurl;

import android.content.Context;

import com.example.desk.been.ReturnMsg;
import com.example.desk.mvp.BasePresenter;
import com.example.desk.mvp.BaseView;

import io.reactivex.Observer;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class PublishUrlContract {
    interface View extends BaseView {
        
    }

    interface  Presenter extends BasePresenter<View> {
        void addUrlPost(String userId, String icon, String title, String content, String shareDesc, String url, int type, Observer<ReturnMsg> observer);
    }
}
