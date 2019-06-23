package com.example.desk.been;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Credit on 2017/3/6.
 */

public class PostBean extends ReturnMsg {

    /**
     * id : 1
     * content : zhihsfh
     * createTime : 54545
     * type : 2
     * linkImg : https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1557981101&di=1d31e56e04e26f9fcb7d790fb1852952&imgtype=jpg&er=1&src=http%3A%2F%2Fpic1.win4000.com%2Fwallpaper%2F8%2F5788b43e000e7.jpg
     * linkTitle : 立夏
     * authorId : null
     * videoUrl : https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1557981101&di=1d31e56e04e26f9fcb7d790fb1852952&imgtype=jpg&er=1&src=http%3A%2F%2Fpic1.win4000.com%2Fwallpaper%2F8%2F5788b43e000e7.jpg
     * videoImgUrl : https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1557981101&di=1d31e56e04e26f9fcb7d790fb1852952&imgtype=jpg&er=1&src=http%3A%2F%2Fpic1.win4000.com%2Fwallpaper%2F8%2F5788b43e000e7.jpg
     * linkUrl : https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1557981101&di=1d31e56e04e26f9fcb7d790fb1852952&imgtype=jpg&er=1&src=http%3A%2F%2Fpic1.win4000.com%2Fwallpaper%2F8%2F5788b43e000e7.jpg
     * linkDesc : 夏天来了
     * author : {"id":1,"userid":"2016021050","college":"计算机与控制工程学院","classs":"软件161","password":"25f9e794323b453885f5181f1b624d0b","birthday":"1998-10-23","email":"13608428279@163.com","gender":"男","userlogo":null}
     * postImages : [{"id":1,"url":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1557981101&di=1d31e56e04e26f9fcb7d790fb1852952&imgtype=jpg&er=1&src=http%3A%2F%2Fpic1.win4000.com%2Fwallpaper%2F8%2F5788b43e000e7.jpg","size":"dfs","name":"2016021052","belongId":null,"belong":{"id":1,"content":"zhihsfh","createTime":"2016-10-10","type":2,"linkImg":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1557981101&di=1d31e56e04e26f9fcb7d790fb1852952&imgtype=jpg&er=1&src=http%3A%2F%2Fpic1.win4000.com%2Fwallpaper%2F8%2F5788b43e000e7.jpg","linkTitle":"立夏","authorId":1,"videoUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1557981101&di=1d31e56e04e26f9fcb7d790fb1852952&imgtype=jpg&er=1&src=http%3A%2F%2Fpic1.win4000.com%2Fwallpaper%2F8%2F5788b43e000e7.jpg","videoImgUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1557981101&di=1d31e56e04e26f9fcb7d790fb1852952&imgtype=jpg&er=1&src=http%3A%2F%2Fpic1.win4000.com%2Fwallpaper%2F8%2F5788b43e000e7.jpg","linkUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1557981101&di=1d31e56e04e26f9fcb7d790fb1852952&imgtype=jpg&er=1&src=http%3A%2F%2Fpic1.win4000.com%2Fwallpaper%2F8%2F5788b43e000e7.jpg","linkDesc":"夏天来了","author":null,"postImages":null,"postComments":null,"postFavorts":null}}]
     * postComments : [{"id":1,"userId":null,"toReplyUserId":null,"content":"你睡得不对","belongId":null,"type":0,"user":{"id":5,"userid":"2016162127","college":"哲法学院","classs":"法学162","password":"b474ab2ebdfd5b3275c50620b6e95bb2","birthday":"1997-01-16","email":"yangkuo6000@126.com","gender":"男","userlogo":null},"toreplyuser":{"id":2,"userid":"2016021051","college":"计算机与控制工程学院","classs":"软件161","password":"25f9e794323b453885f5181f1b624d0b","birthday":"1998-10-23","email":"13608428279@163.com","gender":"男","userlogo":"http://172.16.63.128:8080/photo/2019_04_12_12_04_954.png"},"belong":{"id":1,"content":"zhihsfh","createTime":"2016-10-10","type":2,"linkImg":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1557981101&di=1d31e56e04e26f9fcb7d790fb1852952&imgtype=jpg&er=1&src=http%3A%2F%2Fpic1.win4000.com%2Fwallpaper%2F8%2F5788b43e000e7.jpg","linkTitle":"立夏","authorId":1,"videoUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1557981101&di=1d31e56e04e26f9fcb7d790fb1852952&imgtype=jpg&er=1&src=http%3A%2F%2Fpic1.win4000.com%2Fwallpaper%2F8%2F5788b43e000e7.jpg","videoImgUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1557981101&di=1d31e56e04e26f9fcb7d790fb1852952&imgtype=jpg&er=1&src=http%3A%2F%2Fpic1.win4000.com%2Fwallpaper%2F8%2F5788b43e000e7.jpg","linkUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1557981101&di=1d31e56e04e26f9fcb7d790fb1852952&imgtype=jpg&er=1&src=http%3A%2F%2Fpic1.win4000.com%2Fwallpaper%2F8%2F5788b43e000e7.jpg","linkDesc":"夏天来了","author":null,"postImages":null,"postComments":null,"postFavorts":null}}]
     * postFavorts : [{"id":1,"userId":null,"belongId":null,"user":{"id":3,"userid":"2016021110","college":"计算机与控制工程学院","classs":"计本171","password":"23d2dbc76fb86ff4317ff11e72696315","birthday":"1996－10－30","email":"984813212@qq.com","gender":"女","userlogo":null},"belong":{"id":1,"content":"zhihsfh","createTime":"2016-10-10","type":2,"linkImg":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1557981101&di=1d31e56e04e26f9fcb7d790fb1852952&imgtype=jpg&er=1&src=http%3A%2F%2Fpic1.win4000.com%2Fwallpaper%2F8%2F5788b43e000e7.jpg","linkTitle":"立夏","authorId":1,"videoUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1557981101&di=1d31e56e04e26f9fcb7d790fb1852952&imgtype=jpg&er=1&src=http%3A%2F%2Fpic1.win4000.com%2Fwallpaper%2F8%2F5788b43e000e7.jpg","videoImgUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1557981101&di=1d31e56e04e26f9fcb7d790fb1852952&imgtype=jpg&er=1&src=http%3A%2F%2Fpic1.win4000.com%2Fwallpaper%2F8%2F5788b43e000e7.jpg","linkUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1557981101&di=1d31e56e04e26f9fcb7d790fb1852952&imgtype=jpg&er=1&src=http%3A%2F%2Fpic1.win4000.com%2Fwallpaper%2F8%2F5788b43e000e7.jpg","linkDesc":"夏天来了","author":null,"postImages":null,"postComments":null,"postFavorts":null}},{"id":2,"userId":null,"belongId":null,"user":{"id":5,"userid":"2016162127","college":"哲法学院","classs":"法学162","password":"b474ab2ebdfd5b3275c50620b6e95bb2","birthday":"1997-01-16","email":"yangkuo6000@126.com","gender":"男","userlogo":null},"belong":{"id":1,"content":"zhihsfh","createTime":"2016-10-10","type":2,"linkImg":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1557981101&di=1d31e56e04e26f9fcb7d790fb1852952&imgtype=jpg&er=1&src=http%3A%2F%2Fpic1.win4000.com%2Fwallpaper%2F8%2F5788b43e000e7.jpg","linkTitle":"立夏","authorId":1,"videoUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1557981101&di=1d31e56e04e26f9fcb7d790fb1852952&imgtype=jpg&er=1&src=http%3A%2F%2Fpic1.win4000.com%2Fwallpaper%2F8%2F5788b43e000e7.jpg","videoImgUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1557981101&di=1d31e56e04e26f9fcb7d790fb1852952&imgtype=jpg&er=1&src=http%3A%2F%2Fpic1.win4000.com%2Fwallpaper%2F8%2F5788b43e000e7.jpg","linkUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1557981101&di=1d31e56e04e26f9fcb7d790fb1852952&imgtype=jpg&er=1&src=http%3A%2F%2Fpic1.win4000.com%2Fwallpaper%2F8%2F5788b43e000e7.jpg","linkDesc":"夏天来了","author":null,"postImages":null,"postComments":null,"postFavorts":null}},{"id":3,"userId":null,"belongId":null,"user":{"id":4,"userid":"2016021155","college":"计算机与控制工程学院","classs":"计本163","password":"26ed6bc535c57fc5ab4cb4dc95f49c0c","birthday":"0000-00-00","email":"1234567890@qq.com","gender":"男","userlogo":null},"belong":{"id":1,"content":"zhihsfh","createTime":"2016-10-10","type":2,"linkImg":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1557981101&di=1d31e56e04e26f9fcb7d790fb1852952&imgtype=jpg&er=1&src=http%3A%2F%2Fpic1.win4000.com%2Fwallpaper%2F8%2F5788b43e000e7.jpg","linkTitle":"立夏","authorId":1,"videoUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1557981101&di=1d31e56e04e26f9fcb7d790fb1852952&imgtype=jpg&er=1&src=http%3A%2F%2Fpic1.win4000.com%2Fwallpaper%2F8%2F5788b43e000e7.jpg","videoImgUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1557981101&di=1d31e56e04e26f9fcb7d790fb1852952&imgtype=jpg&er=1&src=http%3A%2F%2Fpic1.win4000.com%2Fwallpaper%2F8%2F5788b43e000e7.jpg","linkUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1557981101&di=1d31e56e04e26f9fcb7d790fb1852952&imgtype=jpg&er=1&src=http%3A%2F%2Fpic1.win4000.com%2Fwallpaper%2F8%2F5788b43e000e7.jpg","linkDesc":"夏天来了","author":null,"postImages":null,"postComments":null,"postFavorts":null}}]
     */


    private int id;
    private String content;
    private String createTime;
    private int type;
    private String linkImg;
    private String linkTitle;
    private int authorId;
    private String videoUrl;
    private String videoImgUrl;
    private String linkUrl;
    private String linkDesc;
    private UserBean author;
    private List<ImageBean> postImages;
    private List<CommentBean> postComments;
    private List<FavortsBean> postFavorts;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getLinkImg() {
        return linkImg;
    }

    public void setLinkImg(String linkImg) {
        this.linkImg = linkImg;
    }

    public String getLinkTitle() {
        return linkTitle;
    }

    public void setLinkTitle(String linkTitle) {
        this.linkTitle = linkTitle;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getVideoImgUrl() {
        return videoImgUrl;
    }

    public void setVideoImgUrl(String videoImgUrl) {
        this.videoImgUrl = videoImgUrl;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public String getLinkDesc() {
        return linkDesc;
    }

    public void setLinkDesc(String linkDesc) {
        this.linkDesc = linkDesc;
    }

    public UserBean getAuthor() {
        return author;
    }

    public void setAuthor(UserBean author) {
        this.author = author;
    }

    public List<ImageBean> getPostImages() {
        return postImages;
    }

    public void setPostImages(List<ImageBean> postImages) {
        this.postImages = postImages;
    }

    public List<CommentBean> getPostComments() {
        return postComments;
    }

    public void setPostComments(List<CommentBean> postComments) {
        this.postComments = postComments;
    }

    public List<FavortsBean> getPostFavorts() {
        return postFavorts;
    }

    public void setPostFavorts(List<FavortsBean> postFavorts) {
        this.postFavorts = postFavorts;
    }

    private boolean expand;


    private String localPath = "";
    //尚未播放过
    private boolean NODE_PALY = true;

    private boolean videoPaly = false;
    private boolean videoLoadSuccess = false;

    public synchronized boolean isVideoPaly() {
        return videoPaly;
    }

    public synchronized void setVideoPaly(boolean videoPaly) {
        this.videoPaly = videoPaly;
    }

    public synchronized boolean isVideoLoadSuccess() {
        return videoLoadSuccess;
    }

    public synchronized void setVideoLoadSuccess(boolean videoLoadSuccess) {
        this.videoLoadSuccess = videoLoadSuccess;
    }

    public synchronized boolean isNODE_PALY() {
        return NODE_PALY;
    }

    public synchronized void setNODE_PALY(boolean NODE_PALY) {
        this.NODE_PALY = NODE_PALY;
    }


    public String getLocalPath() {
        return localPath;
    }

    public void setLocalPath(String localPath) {
        this.localPath = localPath;
    }

    public boolean isExpand() {
        return expand;
    }

    public void setExpand(boolean expand) {
        this.expand = expand;
    }








    public boolean getCurUserFavortId(String userId) {
        if (postFavorts != null && postFavorts.size() > 0) {

            for (FavortsBean f : postFavorts) {
                if (String.valueOf(f.getUser().getId()).equals(userId)) {
                    return true;
                }
            }
        }

        return false;
    }
}
