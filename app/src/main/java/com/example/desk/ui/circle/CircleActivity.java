package com.example.desk.ui.circle;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Rect;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;

import android.view.MotionEvent;
import android.view.View;

import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.example.desk.MainActivity;
import com.example.desk.R;
import com.example.desk.adapter.CircleAdapter;
import com.example.desk.api.GsonTools;
import com.example.desk.been.CommentBean;
import com.example.desk.been.CommentConfig;
import com.example.desk.been.FavortsBean;
import com.example.desk.been.PostBean;
import com.example.desk.ui.circle.view.CircleView;
import com.example.desk.ui.publish.PublishActivity;
import com.example.desk.ui.publishvideo.PublishVideoActivity;
import com.example.desk.ui.videolist.visibility.calculator.SingleListViewItemActiveCalculator;
import com.example.desk.ui.videolist.visibility.scroll.RecyclerViewItemPositionGetter;
import com.example.desk.util.CommonUtils;
import com.example.desk.util.DialogUtil;
import com.example.desk.util.OfflineACache;
import com.example.desk.util.StaticClass;
import com.example.desk.util.TLog;
import com.example.desk.view.CommentListView;
import com.example.desk.view.DividerItemDecoration;
import com.mabeijianxi.smallvideorecord2.MediaRecorderActivity;
import com.mabeijianxi.smallvideorecord2.model.MediaRecorderConfig;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class CircleActivity extends AppCompatActivity implements CircleView.IxCircleView,View.OnClickListener, OnRefreshListener, OnLoadMoreListener {
    @BindView(R.id.tv2)
    TextView tv2;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.tv_toobar)
    TextView tvToobar;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.load_pro)
    ProgressBar mLoadPro;
    @BindView(R.id.tv_load_text)
    TextView mTvload;
    @BindView(R.id.reload_ll)
    LinearLayout mReload;
    @BindView(R.id.ll_load_pro)
    LinearLayout mLlLoad;
    @BindView(R.id.ivRefresh)
    ImageView ivRefresh;
    @BindView(R.id.swipe_target)
    RecyclerView swipeTarget;
    @BindView(R.id.ivLoadMore)
    ImageView ivLoadMore;
    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;
    @BindView(R.id.circleEt)
    EditText mCircleEdit;
    @BindView(R.id.sendIv)
    ImageView sendIv;
    @BindView(R.id.editTextBodyLl)
    LinearLayout mEditTextBody;
    @BindView(R.id.activity_circle)
    LinearLayout activityCircle;
    Unbinder unbinder;
    private CircleAdapter circleAdapter;
    private CommentConfig commentConfig;
    private LinearLayoutManager layoutManager;
    private int currentPosistionScrollY = 0;
    private int currentPosistion = 0;
    private int screenHeight;
    private int editTextBodyHeight;
    private int currentKeyboardH;
    private int selectCircleItemH;
    private int selectCommentItemOffset;
    private OfflineACache aCache;//缓存框架
    private VideoBroadCast videoBroadCast;
    /**
     * 视频列表滑动自动播放/停止
     */
    private SingleListViewItemActiveCalculator mCalculator;
    private static final int PUBILISH_REQUEST_CODE = 123;
    public static final String VIDEO_BROAD_ACTION = "video_pubilsh_success";
    private CirclePresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle);
        ButterKnife.bind(this);
        initValue();
        initEvent();
    }

    private int mScrollState;
    @Override
    protected void onResume() {
        super.onResume();
        if (comLastLoadTime(3)) {
            if (circleAdapter.getItemCount() > 0) {
                progressBar.setVisibility(View.VISIBLE);
            }
            presenter.loadData(StaticClass.LOAD_REFRESH);
        }
    }
    //第一次加载时间
    protected long lastTime = 0;
    /**
     * 当前时间与上一次加载时间比较，是否大于min分钟
     *
     * @return
     */
    private boolean comLastLoadTime(int min) {
        if (System.currentTimeMillis() - lastTime > 1000 * 60 * min) {
            lastTime = System.currentTimeMillis();
            return true;
        }
        return false;
    }

    private void initEvent() {
        tvToobar.setOnClickListener(this);
        mReload.setOnClickListener(this);
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);
        toolbar.setNavigationOnClickListener(view->{
            onBackPressed();
        });

        swipeTarget.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                mScrollState = newState;
                if (newState == RecyclerView.SCROLL_STATE_IDLE && circleAdapter.getItemCount() > 0) {
                    mCalculator.onScrollStateIdle();
                }

                if (newState == RecyclerView.SCROLL_STATE_IDLE && (!ViewCompat.canScrollVertically(recyclerView, 1))) {
                    swipeToLoadLayout.setLoadingMore(true);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                mCalculator.onScrolled(mScrollState);
            }
        });

        swipeTarget.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (mEditTextBody.getVisibility() == View.VISIBLE) {
                    updateEditTextBodyVisible(View.GONE, null);
                    return true;
                }
                return false;
            }
        });


        sendIv.setOnClickListener(this);

    }

    private void initValue() {
        aCache = OfflineACache.get(this);
        setViewTreeObserver();

        layoutManager = new LinearLayoutManager(this);
        //presenter = new CirclePresenter(getActivity(), this);

        presenter = new CirclePresenter(this, this);
        circleAdapter = new CircleAdapter(this, presenter, swipeTarget);

        mCalculator = new SingleListViewItemActiveCalculator(circleAdapter,
                new RecyclerViewItemPositionGetter(layoutManager, swipeTarget));

        swipeTarget.setLayoutManager(layoutManager);
        swipeTarget.addItemDecoration(new DividerItemDecoration(this, true, DividerItemDecoration.VERTICAL_LIST));


        swipeTarget.setAdapter(circleAdapter);


        /**
         * 注册视频发表成功广播
         */
        videoBroadCast = new VideoBroadCast();
        IntentFilter filter = new IntentFilter();
        filter.addAction(VIDEO_BROAD_ACTION);

        this.registerReceiver(videoBroadCast, filter);

        /**
         * 获取缓存json
         */
        String JSON = aCache.getAsString(CirclePresenter.OFFLINE_JSON);
        if (!TextUtils.isEmpty(JSON)) {
            List<PostBean> list = GsonTools.changeGsonToSafeList(JSON, PostBean.class);
            if (list != null && list.size() > 0) {
                closeLoadView();
                swipeTarget.setVisibility(View.VISIBLE);
                circleAdapter.changeData(list);

            }
        }


    }
    /**
     * 发表视频成功广播监听
     */
    private class VideoBroadCast extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            TLog.analytics("onReceive: 广播接收");
            if (intent != null) {
                PostBean msg = (PostBean) intent.getSerializableExtra("PostBean");
                if (msg != null) {
                    circleAdapter.addDataFirst(msg);
                }
            }
        }
    }

    private void setViewTreeObserver() {
        /**
         * 获取View的观察者
         */
        ViewTreeObserver viewTreeObserver = swipeToLoadLayout.getViewTreeObserver();
        /**
         *   interface          ViewTreeObserver.OnGlobalLayoutListener
         当在一个视图树中全局布局发生改变或者视图树中的某个视图的可视状态发生改变时，所要调用的回调函数的接口类

         */
        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                Rect r = new Rect();

                /**
                 * 状态栏高度
                 View的getWindowVisibleDisplayFrame(Rect outRect)附值outRect后，outRect.top()即是状态栏高度

                 标题高度
                 View的getWindowVisibleDisplayFrame(Rect outRect1)附值outRect后，outRect.height()-view.getheight()即是标题高度。
                 */

                swipeToLoadLayout.getWindowVisibleDisplayFrame(r); //获取当前view位置参数
                int statusBarH = getStatusBarHeight();//状态栏高度
                int screenH = swipeToLoadLayout.getRootView().getHeight(); //获取整个屏幕高度
                if (r.top != statusBarH) { //判断是否沉浸式状态栏
                    //在这个demo中r.top代表的是状态栏高度，在沉浸式状态栏时r.top＝0，通过getStatusBarHeight获取状态栏高度
                    r.top = statusBarH;
                }
                int keyboardH = screenH - (r.bottom - r.top); //获取弹出的键盘高度

                TLog.analytics("screenH＝ " + screenH + " &keyboardH = " + keyboardH + " &r.bottom=" + r.bottom + " &top=" + r.top + " &statusBarH=" + statusBarH);
                if (keyboardH == currentKeyboardH) {//有变化时才处理，否则会陷入死循环
                    return;
                }

                currentKeyboardH = keyboardH;
                screenHeight = screenH;//应用屏幕的高度

                editTextBodyHeight = mEditTextBody.getHeight();  //获取输入框所有高度

                if (keyboardH < 150) {//说明是隐藏键盘的情况
                    updateEditTextBodyVisible(View.GONE, null);
                    return;
                }
                //偏移listview
                if (layoutManager != null && commentConfig != null) {
                    currentPosistion = commentConfig.circlePosition + CircleAdapter.HEADVIEW_SIZE;

                    currentPosistionScrollY = getListviewOffset(commentConfig);
                    layoutManager.scrollToPositionWithOffset(currentPosistion, currentPosistionScrollY);
                }
            }
        });
    }

    /**
     * 测量偏移量
     *
     * @param commentConfig
     * @return
     */
    private int getListviewOffset(CommentConfig commentConfig) {
        if (commentConfig == null)
            return 0;
        //这里如果你的listview上面还有其它占高度的控件，则需要减去该控件高度，listview的headview除外。
        //int listviewOffset = mScreenHeight - mSelectCircleItemH - mCurrentKeyboardH - mEditTextBodyHeight;
        int listviewOffset = screenHeight - selectCircleItemH - currentKeyboardH - editTextBodyHeight - toolbar.getHeight();
        if (commentConfig.commentType == CommentConfig.Type.REPLY) {
            //回复评论的情况
            listviewOffset = listviewOffset + selectCommentItemOffset;
        }
        return listviewOffset;
    }

    /**
     * 获取状态栏高度
     *
     * @return
     */

    private int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    @Override
    public void showErrorProgress(String msg) {
        if (circleAdapter.getItemCount() > 0) {
            this.swipeToLoadLayout.setRefreshing(false);
            this.swipeToLoadLayout.setLoadingMore(false);
            DialogUtil.showErrBtnDismissDialog(this, "加载出错，请检查网络", false);
        } else {
            swipeToLoadLayout.setVisibility(View.GONE);
            showLoadErrView();
        }
    }

    @Override
    public void showNoDataProgress() {
        swipeTarget.setVisibility(View.GONE);
        showloadNoDataView("服务器出错 ");
    }

    /**
     * 显示加载没有数据页面
     *
     * @param msg
     */
    private void showloadNoDataView(String msg) {
        progressBar.setVisibility(View.GONE);
        if (mLlLoad != null) {
            mLlLoad.setVisibility(View.VISIBLE);
            mTvload.setVisibility(View.VISIBLE);
            if (!TextUtils.isEmpty(msg)) {
                mTvload.setText(msg);
            } else {
                mTvload.setText("暂没有记录");
            }
            mLoadPro.setVisibility(View.GONE);
            mReload.setVisibility(View.GONE);
        }
    }

    @Override
    public void showLoadProgress(String msg) {
        if (circleAdapter.getItemCount() == 0) {
            swipeToLoadLayout.setVisibility(View.GONE);
            showLoadView(msg);
        }
    }

    @Override
    public void closeSwipeView(String msg) {
        progressBar.setVisibility(View.GONE);
        if (swipeToLoadLayout.isLoadingMore()) {
            swipeToLoadLayout.setLoadingMore(false);
        }
        if (swipeToLoadLayout.isRefreshing()) {
            swipeToLoadLayout.setRefreshing(false);
        }
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateDeleteCircle(int circlePosition) {
        List<PostBean> list = circleAdapter.getmData();
        if (list != null && list.size() > circlePosition) {
            list.remove(circlePosition);
            circleAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void updateAddFavorite(int circlePosition, FavortsBean addItem) {
        TLog.analytics("updateAddFavorite: 添加点赞成功" + addItem.toString());
        List<PostBean> list = circleAdapter.getmData();
        if (list != null && list.size() > circlePosition) {
            list.get(circlePosition).getPostFavorts().add(addItem);
            circleAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void updateDeleteFavort(int circlePosition, int userId) {
        TLog.error("updateDeleteFavort: 删除点赞成功" + userId);
        List<PostBean> list = circleAdapter.getmData();
        if (list != null && list.size() > circlePosition) {
            List<FavortsBean> favorts = list.get(circlePosition).getPostFavorts();
            int pos = -1;
            for (int i = 0; i < favorts.size(); i++) {
                if (favorts.get(i).getUser().getId() == userId) {
                    pos = i;
                    break;
                }

            }
            if (pos != -1) {
                favorts.remove(pos);
                circleAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void updateAddComment(int circlePosition, CommentBean addItem) {
        List<PostBean> list = circleAdapter.getmData();
        if (list != null && list.size() > circlePosition) {
            list.get(circlePosition).getPostComments().add(addItem);
            circleAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void updateDeleteComment(int circlePosition, int commentId) {
        TLog.analytics("updateAddComment: 删除评价" + commentId);
        List<PostBean> list = circleAdapter.getmData();
        if (list != null && list.size() > circlePosition) {
            List<CommentBean> comments = list.get(circlePosition).getPostComments();
            int pos = -1;
            for (int i = 0; i < comments.size(); i++) {
                if (comments.get(i).getId() == commentId) {
                    pos = i;
                    break;
                }

            }
            if (pos != -1) {
                comments.remove(pos);
                circleAdapter.notifyDataSetChanged();
            }

        }
    }

    @Override
    public void updateEditTextBodyVisible(int visibility, CommentConfig commentConfig) {
        mCircleEdit.setText("");
        this.commentConfig = commentConfig;
        mEditTextBody.setVisibility(visibility);

        measureCircleItemHighAndCommentItemOffset(commentConfig);

        if (View.VISIBLE == visibility) {
            mCircleEdit.requestFocus();
            //弹出键盘
            CommonUtils.showSoftInput(mCircleEdit.getContext(), mCircleEdit);

        } else if (View.GONE == visibility) {
            //隐藏键盘
            CommonUtils.hideSoftInput(mCircleEdit.getContext(), mCircleEdit);
            if (currentPosistion != 0 && currentPosistionScrollY != 0) {
                layoutManager.scrollToPositionWithOffset(currentPosistion, 0);
            }
        }
    }

    @Override
    public void updateloadData(int loadType, List<PostBean> datas) {
        //TODO：检查返回的datas数据完整性
//        TLog.log("=="+datas.get(0).getPostFavorts().get(0).getUser().getUserid());
        //Toast.makeText(CircleActivity.this,"=="+datas.get(0).getPostFavorts().get(0).getUser().getUserid(),Toast.LENGTH_LONG).show();
        progressBar.setVisibility(View.GONE);
        closeLoadView();
        swipeToLoadLayout.setVisibility(View.VISIBLE);
        swipeToLoadLayout.setRefreshing(false);
        swipeToLoadLayout.setLoadingMore(false);


        if (loadType == StaticClass.LOAD_REFRESH) {
            if (datas.size() > 0) {
                circleAdapter.changeData(datas);
            } else {
                Toast.makeText(this, "服务器空空的~~~", Toast.LENGTH_SHORT).show();
            }
        } else {
            if (datas.size() > 0) {
                circleAdapter.addData(datas);
            } else {
                Toast.makeText(this, "没有更多的数据啦", Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    public void makeText(String s) {
        Toast.makeText(this,s,Toast.LENGTH_SHORT).show();
    }

    /**
     * 显示加载页面
     *
     * @param msg
     */

    public void showLoadView(String msg) {
        if (mLlLoad != null) {
            mLlLoad.setVisibility(View.VISIBLE);
            mLoadPro.setVisibility(View.VISIBLE);
            mTvload.setVisibility(View.VISIBLE);
            if (!TextUtils.isEmpty(msg)) {
                mTvload.setText(msg);
            }
            mReload.setVisibility(View.GONE);
        }
    }
    /**
     * 显示加载出错页面
     */
    public void showLoadErrView() {
        progressBar.setVisibility(View.GONE);
        if (mLlLoad != null) {
            mLlLoad.setVisibility(View.VISIBLE);
            mLoadPro.setVisibility(View.GONE);
            mTvload.setVisibility(View.GONE);
            mReload.setVisibility(View.VISIBLE);
        }

    }
    /**
     * 关闭加载页面
     */
    private void closeLoadView() {

        if (mLlLoad != null) {
            mLlLoad.setVisibility(View.GONE);
            mLoadPro.setVisibility(View.GONE);
            mTvload.setVisibility(View.GONE);
            mReload.setVisibility(View.GONE);
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.reload_ll:
                lastTime = 0;
                presenter.loadData(StaticClass.LOAD_REFRESH);
                break;
            case R.id.sendIv: //发布评论
                if (presenter != null) {
                    String content = mCircleEdit.getText().toString().trim();
                    if (TextUtils.isEmpty(content)) {
                        Toast.makeText(this, "评论内容不能为空...", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    presenter.addComment(content,commentConfig);
                }
                updateEditTextBodyVisible(View.GONE, null);
                break;
            case R.id.tv_toobar:

                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                dialog.setItems(new String[]{"发表图文", "发表视频"}, (d, i) -> {
                    d.dismiss();
                    switch (i) {
                        case 0://发表图文
                            startActivityForResult(new Intent(this, PublishActivity.class), PUBILISH_REQUEST_CODE);
                            break;
                        case 1: //发表视频

                            /**
                             * 发表成功，之后使用VideoBroadCast广播，获取获取发表的内容
                             */


                            MediaRecorderConfig config = new MediaRecorderConfig.Buidler()
                                   // .doH264Compress(true)
                                    .fullScreen(true)
                                    .smallVideoWidth(480) //宽度
                                    .smallVideoHeight(360)  //高度
                                    .recordTimeMax(6 * 1000) //时长
                                    .maxFrameRate(20) //帧数
                                    .minFrameRate(8)  //最小帧数
                                    .captureThumbnailsTime(1) //提取第一帧当缩略图
                                    .recordTimeMin((int) (1.5 * 1000)) //最小时间
                                    .build();
                            //打开视频录制界面
                            MediaRecorderActivity.goSmallVideoRecorder(this, PublishVideoActivity.class.getName(), config);


                            break;
                    }
                });

                dialog.show();

                break;
        }
    }


    @Override
    public void onLoadMore() {
        presenter.loadData(StaticClass.LOAD_MORE);
    }

    @Override
    public void onRefresh() {
        presenter.loadData(StaticClass.LOAD_REFRESH);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (resultCode == RESULT_OK) {
            if (requestCode == PUBILISH_REQUEST_CODE) {
                if (data != null) {
                    PostBean msg = (PostBean) data.getSerializableExtra("PostBean");
                    if (msg != null) {
                        circleAdapter.addDataFirst(msg);
                    }
                }
            }
        }
    }

    private void measureCircleItemHighAndCommentItemOffset(CommentConfig commentConfig) {
        if (commentConfig == null)
            return;
        int firstPosition = layoutManager.findFirstVisibleItemPosition();
        //只能返回当前可见区域（列表可滚动）的子项
        View selectCircleItem = layoutManager.getChildAt(commentConfig.circlePosition + CircleAdapter.HEADVIEW_SIZE - firstPosition);

        if (selectCircleItem != null) {
            selectCircleItemH = selectCircleItem.getHeight();
        }

        if (commentConfig.commentType == CommentConfig.Type.REPLY && selectCircleItem != null) {
            //回复评论的情况
            CommentListView commentLv = (CommentListView) selectCircleItem.findViewById(R.id.commentList);
            if (commentLv != null) {
                //找到要回复的评论view,计算出该view距离所属动态底部的距离
                View selectCommentItem = commentLv.getChildAt(commentConfig.commentPosition);
                if (selectCommentItem != null) {
                    //选择的commentItem距选择的CircleItem底部的距离
                    selectCommentItemOffset = 0;
                    View parentView = selectCommentItem;
                    do {
                        int subItemBottom = parentView.getBottom();
                        parentView = (View) parentView.getParent();
                        if (parentView != null) {
                            selectCommentItemOffset += (parentView.getHeight() - subItemBottom);
                        }
                    } while (parentView != null && parentView != selectCircleItem);
                }
            }
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(videoBroadCast);

    }

    @Override
    public void onBackPressed() {
        CircleActivity.this.finish();
        /*
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("是否退出");
        builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //aCache.put(CirclePresenter.OFFLINE_JSON, "");
                //startActivity(new Intent(CircleActivity.this, MainActivity.class));
                //CircleActivity.this.finish();
            }
        });
        builder.setNeutralButton("取消", null);
        builder.show();
        */
    }

}
