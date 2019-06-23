package com.example.desk.been;

import java.io.Serializable;

/**
 * Created by Credit on 2017/3/6.
 */

public class FavortsBean implements Serializable {
    /**
     * id : 1
     * userId : 2
     * belongId : 1
     * user : {"id":3,"userid":"2016021110","college":"计算机与控制工程学院","classs":"计本171","password":"23d2dbc76fb86ff4317ff11e72696315","birthday":"1996－10－30","email":"984813212@qq.com","gender":"女","userlogo":null}
     * belong : {"id":1,"content":"zhihsfh","createTime":"2016-10-10","type":2,"linkImg":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1557981101&di=1d31e56e04e26f9fcb7d790fb1852952&imgtype=jpg&er=1&src=http%3A%2F%2Fpic1.win4000.com%2Fwallpaper%2F8%2F5788b43e000e7.jpg","linkTitle":"立夏","authorId":1,"videoUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1557981101&di=1d31e56e04e26f9fcb7d790fb1852952&imgtype=jpg&er=1&src=http%3A%2F%2Fpic1.win4000.com%2Fwallpaper%2F8%2F5788b43e000e7.jpg","videoImgUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1557981101&di=1d31e56e04e26f9fcb7d790fb1852952&imgtype=jpg&er=1&src=http%3A%2F%2Fpic1.win4000.com%2Fwallpaper%2F8%2F5788b43e000e7.jpg","linkUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1557981101&di=1d31e56e04e26f9fcb7d790fb1852952&imgtype=jpg&er=1&src=http%3A%2F%2Fpic1.win4000.com%2Fwallpaper%2F8%2F5788b43e000e7.jpg","linkDesc":"夏天来了","author":null,"postImages":null,"postComments":null,"postFavorts":null}
     */

    private int id;




    private int userId;
    private int belongId;

    private UserBean user;

    private PostBean belong;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public PostBean getBelong() {
        return belong;
    }

    public void setBelong(PostBean belong) {
        this.belong = belong;
    }
}
