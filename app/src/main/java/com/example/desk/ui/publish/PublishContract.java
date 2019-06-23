package com.example.desk.ui.publish;

import android.content.Context;

import com.example.desk.been.ReturnMsg;
import com.example.desk.mvp.BasePresenter;
import com.example.desk.mvp.BaseView;

import java.util.List;

import io.reactivex.Observer;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class PublishContract {
    interface View extends BaseView {
        
    }

    interface  Presenter extends BasePresenter<View> {
        void addPost(String userId, String content, int type, List<String> imgs, Observer<ReturnMsg> observer);
        void addPostQiniuYun(String userId, String content, int type, List<String> imgs, Observer<ReturnMsg> observer);
    }
}
