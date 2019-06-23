package com.example.desk.room2;

import android.content.Context;

import com.example.desk.mvp.BasePresenter;
import com.example.desk.mvp.BaseView;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class Room2Contract {
    interface View extends BaseView {
      void   display(String result);

        void display2(String t);
    }

    interface  Presenter extends BasePresenter<View> {
        void getLibraryData(Context context, String url);
        void  yuyuelibrary(Context context,String seatid);
        void  yuyueminglibrary(Context context,String seatid);
    }
}
