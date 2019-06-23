package com.example.desk.ui.circle;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.desk.api.APIService;
import com.example.desk.api.APIWrapper;
import com.example.desk.api.GsonTools;
import com.example.desk.been.CommentBean;
import com.example.desk.been.CommentConfig;
import com.example.desk.been.FavortsBean;
import com.example.desk.been.PostBean;
import com.example.desk.been.ReturnMsg;
import com.example.desk.dao.UserDao;

import com.example.desk.ui.circle.view.CircleView;
import com.example.desk.util.DialogUtil;
import com.example.desk.util.OfflineACache;
import com.example.desk.util.StringUtils;
import com.example.desk.util.TLog;

import java.util.HashMap;
import java.util.Map;


import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;


import static com.example.desk.api.APIService.TOLOGIN;
import static com.example.desk.util.StaticClass.ALREADEY_LOAD;
import static com.example.desk.util.StaticClass.COMMENT_OP;
import static com.example.desk.util.StaticClass.FAVORT_OP;
import static com.example.desk.util.StaticClass.LOAD_MORE;
import static com.example.desk.util.StaticClass.LOAD_OP;

import static com.example.desk.util.StaticClass.LOAD_REFRESH;
import static com.example.desk.util.StaticClass.NOTE_LOAD;
import static com.example.desk.util.StaticClass.OP_ADD;
import static com.example.desk.util.StaticClass.OP_DELETE;
import static com.example.desk.util.StaticClass.POST_OP;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class CirclePresenter implements CircleContract.Presenter{
    private Disposable disposable;
    private CircleView.IxCircleView view;
    private static final String TAG = "CirclePresenter";
    public static final String OFFLINE_JSON = "offlineJson";
    ////////////////
    private int LOAD_STATE = 0X100;
/////////////////////
        private Context context;
    public CirclePresenter(@NonNull Context context, @NonNull CircleView.IxCircleView view) {
        this.view = view;
        pageIndex = 1;
        this.context = context;
        aCache = OfflineACache.get(context);
        this.context = context;
        requestTags = new HashMap<>();

        Map<Integer, String> Favort = new HashMap<>();
        Favort.put(OP_ADD, "点赞中");
        Favort.put(OP_DELETE, "删除点赞");

        Map<Integer, String> Comment = new HashMap<>();
        Comment.put(OP_ADD, "评价中");
        Comment.put(OP_DELETE, "删除评价");

        Map<Integer, String> Post = new HashMap<>();
        Post.put(OP_ADD, "发表说说");
        Post.put(OP_DELETE, "删除说说");

        requestTags.put(FAVORT_OP, Favort);
        requestTags.put(COMMENT_OP, Comment);
        requestTags.put(POST_OP, Post);
    }

    private int pageSize = 10;
    private int pageIndex = 1;

    private OfflineACache aCache;

    private Map<Integer, Map<Integer, String>> requestTags;

    @Override
    public void loadData(int loadType) {
        if (LOAD_STATE == NOTE_LOAD) {
            view.showLoadProgress("正在加载中..");
        }
        if(loadType == LOAD_MORE) {
            pageIndex++;
        }else {
            pageIndex = 1;
        }
        Map<String, RequestBody> request = new HashMap<>();
        request.put("pageIndex", RequestBody.create(null, pageIndex + ""));
        request.put("pageSize", RequestBody.create(null, pageSize + ""));
        Observable<ReturnMsg> returnMsgObservable = APIWrapper.getInstance().Get("postlist", request);//TODO:
        execute(returnMsgObservable,getObserver(LOAD_OP, loadType, 0));
    }



    @Override
    public void deleteCircle(int circlePosition, int circleId) {
        String userId = UserDao.getInstance().getUserId();
        //circleId, Integer.valueOf(userId), getObserver(POST_OP, OP_DELETE, circlePosition)
        Map<String, RequestBody> request = new HashMap<>();
        request.put("postId", RequestBody.create(null, circleId + ""));
        request.put("userId", RequestBody.create(null, userId + ""));
        Observable<ReturnMsg> o = APIWrapper.getInstance().delete("post", request);
        execute(o,getObserver(POST_OP, OP_DELETE, circlePosition));
    }

    @Override
    public void deleteFavort(int circlePosition, int postId, int userId) {
        TLog.analytics("deleteFavort: postId " + postId + "  userId:" + userId);
        Map<String, RequestBody> request = new HashMap<>();
        request.put("userId", RequestBody.create(null, userId + ""));
        request.put("postId", RequestBody.create(null, postId + ""));
        Observable<ReturnMsg> j = APIWrapper.getInstance().delete("favort", request);
        execute(j,getObserver(FAVORT_OP,OP_DELETE,circlePosition));
    }

    @Override
    public void addFavort(int circlePosition, int circleId) {
        String userId = UserDao.getInstance().getUserId();
        Integer.valueOf(userId);
        getObserver(FAVORT_OP,OP_ADD,circlePosition);
        Map<String, RequestBody> request = new HashMap<>();
        request.put("userId", RequestBody.create(null, userId + ""));
        request.put("postId", RequestBody.create(null, circleId + ""));
        Observable<ReturnMsg> h = APIWrapper.getInstance().create("favort", request);
        execute(h,getObserver(FAVORT_OP, OP_ADD, circlePosition));
    }

    @Override
    public void addComment(String content, CommentConfig config) {
        int userId = Integer.valueOf(UserDao.getInstance().getUserId());
        if (config.commentType == CommentConfig.Type.PUBLIC) {
           // content,0,userId,userId,config.postId,getObserver(COMMENT_OP,OP_ADD,config.circlePosition)

            Map<String, RequestBody> request = new HashMap<>();
            request.put("content", RequestBody.create(null, content));
            request.put("cType", RequestBody.create(null, 0 + ""));
            request.put("userId", RequestBody.create(null, userId + ""));
            request.put("touserId", RequestBody.create(null, userId + ""));
            request.put("postId", RequestBody.create(null, config.postId + ""));
            Observable<ReturnMsg> s = APIWrapper.getInstance().create("comment", request);
            execute(s,getObserver(COMMENT_OP,OP_ADD,config.circlePosition));
        }else {
            //content, 1, userId, config.replyUser.getId(), config.postId, getObserver(COMMENT_OP, OP_ADD, config.circlePosition
            Map<String, RequestBody> request = new HashMap<>();
            request.put("content", RequestBody.create(null, content));
            request.put("cType", RequestBody.create(null, 1 + ""));
            request.put("userId", RequestBody.create(null, userId + ""));
            request.put("touserId", RequestBody.create(null, config.replyUser.getId() + ""));
            request.put("postId", RequestBody.create(null, config.postId + ""));
            Observable<ReturnMsg> s = APIWrapper.getInstance().create("comment", request);
            execute(s,getObserver(COMMENT_OP,OP_ADD,config.circlePosition));
        }
    }

    @Override
    public void deleteComment(int circlePosition, int commentId) {
        //commentId, getObserver(COMMENT_OP, OP_DELETE, circlePosition)
        Map<String, RequestBody> request = new HashMap<>();
        request.put("commentId", RequestBody.create(null, commentId + ""));
        Observable<ReturnMsg> d = APIWrapper.getInstance().delete("comment", request);
        execute(d,getObserver(COMMENT_OP, OP_DELETE, circlePosition));

    }
    private void execute(Observable<ReturnMsg> observeable, final Observer<ReturnMsg> observer) {
        /**
         * RxJava将这个操作符实现为repeat方法。它不是创建一个Observable，
         * 而是重复发射原始Observable的数据序列，这个序列或者是无限的，或者通过repeat(n)指定重复次数
         */
        observeable.repeat(1);
        observeable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ReturnMsg>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ReturnMsg returnMsg) {
                        if (returnMsg.getIs() != TOLOGIN) {
                            Observable.just(returnMsg)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(observer);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        TLog.analytics("onError: 出错啦" + e.getMessage());
                        observer.onError(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }

    /**
     * 获取观察者
     * @param op 操作
     * @param mode 操作模式
     * @param value 回调传递值
     * @return
     */
    private Observer<ReturnMsg> getObserver(final int op, final int mode, final int value) {
        if (op != LOAD_OP){
            com.example.desk.util.DialogUtil.showToastLoadingDialog(context,getTag(op, mode), false);
        }

        Observer<ReturnMsg> re = new Observer<ReturnMsg>() {
            @Override
            public void onError(Throwable e) {
                DialogUtil.dismissToatLoadingDialog();
                Log.e(TAG, "onError: 加载出错:" + e.getMessage());
                if (LOAD_STATE == NOTE_LOAD) {
                    view.showErrorProgress("加载出错 ");
                }

                switch (op) {
                    case LOAD_OP:
                        //关闭加载界面
                        view.closeSwipeView(e.getMessage());
                        if (mode == LOAD_REFRESH) {
                            //Toast.makeText(context, "刷新失败，请检查网络", Toast.LENGTH_LONG).show();
                            view.makeText("刷新失败，请检查网络");
                        } else {
                            pageIndex--;
                            //Toast.makeText(context, "加载失败，请检查网络", Toast.LENGTH_LONG).show();
                            view.makeText("加载失败，请检查网络");
                        }
                        break;
                    case FAVORT_OP:
                        if (mode == OP_ADD) {
                            //Toast.makeText(context, "点赞失败，请检查网络", Toast.LENGTH_LONG).show();
                            view.makeText("点赞失败，请检查网络");
                        } else {
                            //Toast.makeText(context, "取消点赞失败，请检查网络", Toast.LENGTH_LONG).show();
                            view.makeText("取消点赞失败，请检查网络");
                        }
                        break;
                    case POST_OP:
                        if (mode == OP_ADD) {
                            //Toast.makeText(context, "发表说说失败，请检查网络", Toast.LENGTH_LONG).show();
                            view.makeText("发表说说失败，请检查网络");
                        } else {
                            //Toast.makeText(context, "删除说说失败，请检查网络", Toast.LENGTH_LONG).show();
                            view.makeText("删除说说失败，请检查网络");
                        }
                        break;
                    case COMMENT_OP:
                        if (mode == OP_ADD) {
                           // Toast.makeText(context, "发表评价失败，请检查网络", Toast.LENGTH_LONG).show();
                            view.makeText("发表评价失败，请检查网络");
                        } else {
                            //Toast.makeText(context, "删除评价失败，请检查网络", Toast.LENGTH_LONG).show();
                            view.makeText("删除评价失败，请检查网络");
                        }
                        break;
                }
            }

            @Override
            public void onComplete() {

            }

            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
                TLog.analytics("onSubscribe: 关联了");
            }

            @Override
            public void onNext(ReturnMsg returnMsg) {
                DialogUtil.dismissToatLoadingDialog();
                if (returnMsg.getIs() == APIService.SUCCESS) {
                    LOAD_STATE = ALREADEY_LOAD;
                    String json = GsonTools.createGsonString(returnMsg.getData());
                    //TODO:检查打出的json格式
                    TLog.error("onNext: 返回JSON:====================" + returnMsg + "\n value:" + json);

                    switch (op) {
                        case LOAD_OP:
                            if (mode == LOAD_REFRESH || mode == LOAD_MORE) {
                                if (mode == LOAD_REFRESH) {
                                    /**
                                     * 缓存json
                                     */
                                    if (aCache == null){
                                        TLog.error("aCache is null");
                                    }
                                    aCache.put(OFFLINE_JSON, json);

                                }
                                //TODO:怀疑json转bean出现问题
                                view.updateloadData(mode, GsonTools.changeGsonToSafeList(json, PostBean.class));

                            }
                            break;
                        case FAVORT_OP:
                            if (mode == OP_ADD) {
                                view.updateAddFavorite(value, GsonTools.changeGsonToBean(json, FavortsBean.class));

                            } else {

                                double d = (double) returnMsg.getData();
                                int id = StringUtils.stringToInt(d + "");
                                view.updateDeleteFavort(value, id);
                            }
                            break;
                        case POST_OP:
                            if (mode == OP_ADD) {

                            } else {
                                view.updateDeleteCircle(value);
                            }
                            break;

                        case COMMENT_OP:
                            if (mode == OP_ADD) {
                                view.updateAddComment(value, GsonTools.changeGsonToBean(json, CommentBean.class));
                            } else {
                                double d = (double) returnMsg.getData();
                                int id = StringUtils.stringToInt(d + "");
                                view.updateDeleteComment(value, id);
                            }
                            break;


                    }
                } else {
                    TLog.analytics("onNext: 失败 code:" + returnMsg.getIs() + " msg:" + returnMsg.getMsg());
                    view.closeSwipeView(returnMsg.getMsg());
                }

                if (disposable != null && !disposable.isDisposed()) {
                    disposable.dispose();
                }
            }
        };
        return re;
    }

    private String getTag(int op, int mode) {
        return requestTags.get(op).get(mode);
    }

    public void showEditTextBody(CommentConfig commentConfig){
        if (view != null) {
            view.updateEditTextBodyVisible(View.VISIBLE,commentConfig);
        }
    }
}
