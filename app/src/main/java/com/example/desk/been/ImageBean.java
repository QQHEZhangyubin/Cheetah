package com.example.desk.been;

import java.io.Serializable;

/**
 * Created by Credit on 2017/3/6.
 */

public class ImageBean implements Serializable {
    /**
     * id : 1
     * url : https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1557981101&di=1d31e56e04e26f9fcb7d790fb1852952&imgtype=jpg&er=1&src=http%3A%2F%2Fpic1.win4000.com%2Fwallpaper%2F8%2F5788b43e000e7.jpg
     * size : dfs
     * name : 2016021052
     * belongId : 2
     * belong : {"id":1,"content":"zhihsfh","createTime":"2016-10-10","type":2,"linkImg":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1557981101&di=1d31e56e04e26f9fcb7d790fb1852952&imgtype=jpg&er=1&src=http%3A%2F%2Fpic1.win4000.com%2Fwallpaper%2F8%2F5788b43e000e7.jpg","linkTitle":"立夏","authorId":1,"videoUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1557981101&di=1d31e56e04e26f9fcb7d790fb1852952&imgtype=jpg&er=1&src=http%3A%2F%2Fpic1.win4000.com%2Fwallpaper%2F8%2F5788b43e000e7.jpg","videoImgUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1557981101&di=1d31e56e04e26f9fcb7d790fb1852952&imgtype=jpg&er=1&src=http%3A%2F%2Fpic1.win4000.com%2Fwallpaper%2F8%2F5788b43e000e7.jpg","linkUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1557981101&di=1d31e56e04e26f9fcb7d790fb1852952&imgtype=jpg&er=1&src=http%3A%2F%2Fpic1.win4000.com%2Fwallpaper%2F8%2F5788b43e000e7.jpg","linkDesc":"夏天来了","author":null,"postImages":null,"postComments":null,"postFavorts":null}
     */
    private int id;
    private String url;
    private String size;
    private String name;
    private int belongId;
    private PostBean belong;


    private boolean localUrl = false;

    public boolean isLocalUrl() {
        return localUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBelongId() {
        return belongId;
    }

    public void setBelongId(int belongId) {
        this.belongId = belongId;
    }

    public PostBean getBelong() {
        return belong;
    }

    public void setBelong(PostBean belong) {
        this.belong = belong;
    }

    public void setLocalUrl(boolean localUrl) {
        this.localUrl = localUrl;
    }

}
