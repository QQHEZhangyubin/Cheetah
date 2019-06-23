package com.example.desk.been;

import java.io.Serializable;

/**
 * Created by Credit on 2017/3/6.
 */

public class CommentBean implements Serializable {
    /**
     * id : 1
     * userId : null
     * toReplyUserId : null
     * content : 你睡得不对
     * belongId : null
     * type : 0
     * user : {"id":5,"userid":"2016162127","college":"哲法学院","classs":"法学162","password":"b474ab2ebdfd5b3275c50620b6e95bb2","birthday":"1997-01-16","email":"yangkuo6000@126.com","gender":"男","userlogo":null}
     * toreplyuser : {"id":2,"userid":"2016021051","college":"计算机与控制工程学院","classs":"软件161","password":"25f9e794323b453885f5181f1b624d0b","birthday":"1998-10-23","email":"13608428279@163.com","gender":"男","userlogo":"http://172.16.63.128:8080/photo/2019_04_12_12_04_954.png"}
     * belong : {"id":1,"content":"zhihsfh","createTime":"2016-10-10","type":2,"linkImg":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1557981101&di=1d31e56e04e26f9fcb7d790fb1852952&imgtype=jpg&er=1&src=http%3A%2F%2Fpic1.win4000.com%2Fwallpaper%2F8%2F5788b43e000e7.jpg","linkTitle":"立夏","authorId":1,"videoUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1557981101&di=1d31e56e04e26f9fcb7d790fb1852952&imgtype=jpg&er=1&src=http%3A%2F%2Fpic1.win4000.com%2Fwallpaper%2F8%2F5788b43e000e7.jpg","videoImgUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1557981101&di=1d31e56e04e26f9fcb7d790fb1852952&imgtype=jpg&er=1&src=http%3A%2F%2Fpic1.win4000.com%2Fwallpaper%2F8%2F5788b43e000e7.jpg","linkUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1557981101&di=1d31e56e04e26f9fcb7d790fb1852952&imgtype=jpg&er=1&src=http%3A%2F%2Fpic1.win4000.com%2Fwallpaper%2F8%2F5788b43e000e7.jpg","linkDesc":"夏天来了","author":null,"postImages":null,"postComments":null,"postFavorts":null}
     */

    public static final int PUBLIC = 0;
    public static final int REPLAY = 1;
    private int id;
    private int type;
    private UserBean user;
    private UserBean toreplyuser;
    private String content;
    private PostBean belong;

    private int userId;
    private int belongId;
    private int toReplyUserId;

    public static int getPUBLIC() {
        return PUBLIC;
    }

    public static int getREPLAY() {
        return REPLAY;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public UserBean getToreplyuser() {
        return toreplyuser;
    }

    public void setToreplyuser(UserBean toreplyuser) {
        this.toreplyuser = toreplyuser;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public PostBean getBelong() {
        return belong;
    }

    public void setBelong(PostBean belong) {
        this.belong = belong;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getBelongId() {
        return belongId;
    }

    public void setBelongId(int belongId) {
        this.belongId = belongId;
    }

    public int getToReplyUserId() {
        return toReplyUserId;
    }

    public void setToReplyUserId(int toReplyUserId) {
        this.toReplyUserId = toReplyUserId;
    }
}
