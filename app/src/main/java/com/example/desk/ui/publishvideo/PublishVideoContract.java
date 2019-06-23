package com.example.desk.ui.publishvideo;

import android.content.Context;

import com.example.desk.been.ReturnMsg;
import com.example.desk.mvp.BasePresenter;
import com.example.desk.mvp.BaseView;

import io.reactivex.Observer;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class PublishVideoContract {
    interface View extends BaseView {
        
    }

    interface  Presenter extends BasePresenter<View> {
        public void addVideoPost(String userId, String content, int type, String videoPath, String videoImg, Observer<ReturnMsg> observer);
        void addVideoPostQiNiuYun(String userId, String content, int type, String videoPath, String videoImg,Observer<ReturnMsg> observer);
    }
}
