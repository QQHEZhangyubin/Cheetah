package com.example.desk.ui.tiyu;

import com.example.desk.entity.TiYu;
import com.example.desk.mvp.BasePresenter;
import com.example.desk.mvp.BaseView;

import java.util.List;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class TiyuContract {
    interface View extends BaseView {
        void success(List<TiYu> tiYuList);
        void onfail();
        void  parsehtml(String string);
    }

    interface  Presenter extends BasePresenter<View> {
        void getTiyuData(String xuehao, int i);//查锻炼数据

    }
}
