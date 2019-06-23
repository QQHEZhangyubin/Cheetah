package com.example.desk.room2;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.desk.R;
import com.example.desk.adapter.DeskAdapter;
import com.example.desk.entity.Desk;
import com.example.desk.entity.Library;
import com.example.desk.mvp.MVPBaseActivity;
import com.example.desk.ui.register.RegisterActivity;
import com.example.desk.ui.room.RoomActivity;
import com.example.desk.util.ShareUtils;
import com.example.desk.util.StaticClass;
import com.xuexiang.xqrcode.ui.CaptureActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class Room2Activity extends MVPBaseActivity<Room2Contract.View, Room2Presenter> implements Room2Contract.View {

    @BindView(R.id.iv_welcomeLibrary)
    ImageView ivWelcomeLibrary;
    @BindView(R.id.toolbarLibrary)
    Toolbar toolbarLibrary;
    @BindView(R.id.collapsing_toolbarLibrary)
    CollapsingToolbarLayout collapsingToolbarLibrary;
    @BindView(R.id.appBarLibrary)
    AppBarLayout appBarLibrary;
    @BindView(R.id.recyclerViewLibrary)
    RecyclerView recyclerViewLibrary;
    private Desk tempdesk;
    private String day;
    private AlertDialog alertDialog;
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room2);
        ButterKnife.bind(this);
        //TODO：

        String url = getIntent().getStringExtra("urlkey");
        int re = url.indexOf("STod.aspx");
        if (re != -1){
            day = "TODAY";
        }else {
            day = "TOMORROW";
        }

        ShareUtils.deleShare(getContext().getApplicationContext(),StaticClass.librarycookie);

        mPresenter.getLibraryData(Room2Activity.this,url);
        Glide.with(Room2Activity.this).load(R.mipmap.welcome).into(ivWelcomeLibrary);
        setSupportActionBar(toolbarLibrary);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        collapsingToolbarLibrary.setTitle(getIntent().getStringExtra("library_room"));
    }

    @Override
    public void display(String result) {
        Document d = Jsoup.parse(result);
        Elements imgTags = d.select("img[src]");
        List<String> status = new ArrayList<>();
        for (int i = 2; i < imgTags.size(); i++) {
            String src2 = imgTags.get(i).attr("src");
            Pattern pattern = Pattern.compile("[^\u4E00-\u9FA5]");
            Matcher matcher = pattern.matcher(src2);
            status.add(matcher.replaceAll(""));
        }
        List<String> seatid = new ArrayList<>();
        Elements aTags = d.select("a[href]");
        for (int i = 1; i < aTags.size(); i++) {
            String aTag = aTags.get(i).attr("href");
            String h = aTag.substring(aTag.indexOf("=") + 1, aTag.length());
            seatid.add(h);
        }
        List<Desk> libraries = new ArrayList<>();
        for (int i = 0; i < seatid.size(); i++) {
            Desk library = new Desk(seatid.get(i), status.get(i));
            libraries.add(library);
        }

        DeskAdapter.Two two = new DeskAdapter.Two() {
            @Override
            public void RequestDesk(Desk desk) {
                if (desk.getState().equals("可用")){
                    tempdesk = desk;
                   // Toast.makeText(Room2Activity.this,tempdesk.getSeatid(),Toast.LENGTH_SHORT).show();
                    switch (day){
                        case "TODAY":
                            AlertDialog.Builder builder = new AlertDialog.Builder(Room2Activity.this);
                            builder.setTitle("提醒");
                            builder.setMessage("确认要选择该座位吗？");
                            builder.setPositiveButton("确定选此座位", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    mPresenter.yuyuelibrary(Room2Activity.this,tempdesk.getSeatid());
                                }
                            });
                            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    alertDialog.dismiss();
                                }
                            });
                            alertDialog = builder.create();
                            alertDialog.show();
                            break;
                        case "TOMORROW":
                            AlertDialog.Builder builder2 = new AlertDialog.Builder(Room2Activity.this);
                            builder2.setTitle("提醒");
                            builder2.setMessage("确认要选择该座位吗？");
                            builder2.setPositiveButton("确定选此座位", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    mPresenter.yuyueminglibrary(Room2Activity.this,tempdesk.getSeatid());
                                }
                            });
                            builder2.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    alertDialog.dismiss();
                                }
                            });
                            alertDialog = builder2.create();
                            alertDialog.show();
                            break;
                    }
                }else {

                    Toast.makeText(Room2Activity.this,"当前位置不可选择",Toast.LENGTH_SHORT).show();
                }

            }
        };

        StaggeredGridLayoutManager layoutManager=new StaggeredGridLayoutManager
                (6,StaggeredGridLayoutManager.VERTICAL);
        recyclerViewLibrary.setLayoutManager(layoutManager);

        DeskAdapter adapter = new DeskAdapter(Room2Activity.this, libraries);
        adapter.setTwo(two);
        recyclerViewLibrary.setAdapter(adapter);

    }

    @Override
    public void display2(String t) {
        if (t.equals("id', 'bgDiv")){
            //主要控制今日预约返回的数据
            Toast.makeText(Room2Activity.this,"预约成功,请按规定时间去图书馆签到",Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(Room2Activity.this,t,Toast.LENGTH_SHORT).show();
        }

    }
}
