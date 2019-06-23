package com.example.desk.ui.tiyu;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.desk.R;
import com.example.desk.adapter.TiyuAdapter;
import com.example.desk.entity.TiYu;
import com.example.desk.mvp.MVPBaseActivity;
import com.example.desk.util.ShareUtils;
import com.example.desk.util.StaticClass;
import com.example.desk.util.TLog;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class TiyuActivity extends MVPBaseActivity<TiyuContract.View, TiyuPresenter> implements TiyuContract.View {
    @BindView(R.id.tv_tiyu_xuehao)
    TextView tvTiyuXuehao;
    @BindView(R.id.tv_tiyu_avail)
    TextView tvTiyuAvail;
    @BindView(R.id.tv_tiyu_total)
    TextView tvTiyuTotal;
    @BindView(R.id.rycler_tiyu)
    RecyclerView ryclerTiyu;
    @BindView(R.id.tv_html)
    TextView tvHtml;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tiyu);
        ButterKnife.bind(this);
        String xuehao = ShareUtils.getString(getApplicationContext(), StaticClass.userid, "");
       Intent intent = getIntent();
        //9.表示早操，8表示蜘蛛，7表示扩展
        //mPresenter.getTiyuData("2016021051",7);
        //Toast.makeText(TiyuActivity.this,xuehao,Toast.LENGTH_SHORT).show();
        if (intent == null){
            TLog.error("intent 是 null");
        }else {
            String k = intent.getStringExtra("p");
            switch (k){
                case "1":
                    //9.表示早操，8表示蜘蛛，7表示扩展
                    mPresenter.getTiyuData(xuehao,9);
                    break;
                case "2":
                    mPresenter.getTiyuData(xuehao,8);

                    break;
                case "3":
                    mPresenter.getTiyuData(xuehao,7);
                    break;
                default:
                    break;
            }
        }
        progressDialog = new ProgressDialog(TiyuActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("查询中...");
        progressDialog.show();
    }

    @Override
    public void success(List<TiYu> tiYuList) {
        progressDialog.dismiss();
        if (tiYuList.size() == 0){
            Toast.makeText(TiyuActivity.this,"一张卡也没刷,小编连你是谁都不知道。",Toast.LENGTH_SHORT).show();
        }else {
            String xuehao = tiYuList.get(0).getXuehao();
            String name = tiYuList.get(0).getName();
            tvTiyuXuehao.setText(xuehao+"  "+ name);
            int k = 0;
            for (TiYu tiYu : tiYuList) {
                if (tiYu.getIsvalid().equals("有效")) {
                    k++;
                }
            }
            tvTiyuAvail.setText(k + "");
            tvTiyuTotal.setText(tiYuList.size() + "");
            StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager
                    (1, StaggeredGridLayoutManager.VERTICAL);
            ryclerTiyu.setLayoutManager(layoutManager);
            TiyuAdapter adapter = new TiyuAdapter(TiyuActivity.this, tiYuList);
            ryclerTiyu.setAdapter(adapter);
        }

    }

    @Override
    public void onfail() {
        Toast.makeText(TiyuActivity.this,"请使用校内网查看体育刷卡数据",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void parsehtml(String string) {
       //tvHtml.setText(string);
    }
}
