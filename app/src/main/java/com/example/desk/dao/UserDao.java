package com.example.desk.dao;

import android.util.Log;

import com.example.desk.MyApplication;
import com.example.desk.api.GsonTools;
import com.example.desk.been.UserBean;
import com.example.desk.util.OfflineACache;


/**
 * Created by Credit on 2017/3/3.
 * 用户信息保存dao
 */

public class UserDao {

    private static final String TAG = "UserDao";

    private static UserDao instance;

    private static OfflineACache aCache;

    private UserDao() {
    }


    public synchronized static UserDao getInstance() {
        if (instance == null) {
            instance = new UserDao();
            aCache = OfflineACache.get(MyApplication.getInstance());
        }
        return instance;
    }

    public String getUserId() {
        return aCache.getAsString("id");
    }

    public String getName() {
        return aCache.getAsString("userid");
    }

    public String getCollge() {
        return aCache.getAsString("college");
    }

    public String getSex() {
        return aCache.getAsString("sex");
    }

    public String getBirth() {
        return aCache.getAsString("birth");
    }

    public String getPwd() {
        return aCache.getAsString("pwd");
    }

    public String getPic() {
        return aCache.getAsString("pic");
    }

    public UserBean getUser() {
        UserBean user = GsonTools.changeGsonToBean(aCache.getAsString("user"), UserBean.class);
        if (user == null) {
            user = new UserBean();
        }
        return user;
    }

    public void saveUser(UserBean user) {
        aCache.put("id", user.getId() + "");
        aCache.put("userid", user.getUserid());
        aCache.put("college", user.getCollege());
        aCache.put("classs", user.getClasss());
        aCache.put("pic", user.getUserlogo());
        aCache.put("pwd", user.getPassword());
        aCache.put("sex", user.getGender());
        aCache.put("birth", user.getBirthday());
        String json = GsonTools.createGsonString(user);
        aCache.put("user", json);
        Log.e(TAG, "保存用户信息saveUser: " + user);
    }

    public void clearUser() {
        aCache.put("id", "");
        aCache.put("userid", "");
        aCache.put("college", "");
        aCache.put("classs", "");
        aCache.put("pic", "");
        aCache.put("pwd", "");
        aCache.put("sex", "");
        aCache.put("birth", "");
        aCache.put("user", "");
    }
}
