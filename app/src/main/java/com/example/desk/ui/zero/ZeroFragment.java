package com.example.desk.ui.zero;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.desk.R;
import com.example.desk.mvp.MVPBaseFragment;
import com.example.desk.room2.Room2Activity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class ZeroFragment extends MVPBaseFragment<ZeroContract.View, ZeroPresenter> implements ZeroContract.View {
    @BindView(R.id.today_bt_1)
    Button todayBt1;
    @BindView(R.id.tomorrow_bt_1)
    Button tomorrowBt1;
    Unbinder unbinder;
    @BindView(R.id.today_bt_2)
    Button todayBt2;
    @BindView(R.id.today_bt_3)
    Button todayBt3;
    @BindView(R.id.today_bt_4)
    Button todayBt4;
    @BindView(R.id.today_bt_5)
    Button todayBt5;
    @BindView(R.id.today_bt_6)
    Button todayBt6;
    @BindView(R.id.today_bt_7)
    Button todayBt7;
    @BindView(R.id.today_bt_8)
    Button todayBt8;
    @BindView(R.id.today_bt_9)
    TextView todayBt9;
    @BindView(R.id.tomorrow_bt_2)
    Button tomorrowBt2;
    @BindView(R.id.tomorrow_bt_3)
    Button tomorrowBt3;
    @BindView(R.id.tomorrow_bt_4)
    Button tomorrowBt4;
    @BindView(R.id.tomorrow_bt_5)
    Button tomorrowBt5;
    @BindView(R.id.tomorrow_bt_6)
    Button tomorrowBt6;
    @BindView(R.id.tomorrow_bt_7)
    Button tomorrowBt7;
    @BindView(R.id.tomorrow_bt_8)
    Button tomorrowBt8;
    @BindView(R.id.tomorrow_bt_9)
    Button tomorrowBt9;

    public ZeroFragment() {
    }

    //单例模式
    public static ZeroFragment newInstance() {
        ZeroFragment zeroFragment = new ZeroFragment();
        return zeroFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_zero, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.today_bt_1, R.id.tomorrow_bt_1, R.id.today_bt_2, R.id.today_bt_3, R.id.today_bt_4, R.id.today_bt_5, R.id.today_bt_6, R.id.today_bt_7, R.id.today_bt_8, R.id.today_bt_9,
            R.id.tomorrow_bt_2, R.id.tomorrow_bt_3, R.id.tomorrow_bt_4, R.id.tomorrow_bt_5, R.id.tomorrow_bt_6, R.id.tomorrow_bt_7, R.id.tomorrow_bt_8, R.id.tomorrow_bt_9})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.today_bt_1:
                String url1 = "http://172.16.47.84/AppSTod.aspx?roomid=1201&hei=608&wd=1366";
                Intent intent1 = new Intent(getActivity(), Room2Activity.class);
                intent1.putExtra("urlkey", url1);
                intent1.putExtra("library_room", "东区201今日座位情况");
                startActivity(intent1);
                break;
            case R.id.today_bt_2:
                String url2 = "http://172.16.47.84/AppSTod.aspx?roomid=1401&hei=999&wd=1366";
                Intent intent2 = new Intent(getActivity(), Room2Activity.class);
                intent2.putExtra("urlkey", url2);
                intent2.putExtra("library_room", "东区401今日座位情况");
                startActivity(intent2);
                break;
            case R.id.today_bt_3:
                String url3 = "http://172.16.47.84/AppSTod.aspx?roomid=2101&hei=608&wd=1366";
                Intent intent3 = new Intent(getActivity(), Room2Activity.class);
                intent3.putExtra("urlkey", url3);
                intent3.putExtra("library_room", "中区101今日座位情况");
                startActivity(intent3);
                break;
            case R.id.today_bt_4:
                String url4 = "http://172.16.47.84/AppSTod.aspx?roomid=2201&hei=999&wd=1366";
                Intent intent4 = new Intent(getActivity(), Room2Activity.class);
                intent4.putExtra("urlkey", url4);
                intent4.putExtra("library_room", "中区201今日座位情况");
                startActivity(intent4);
                break;
            case R.id.today_bt_5:
                String url5 = "http://172.16.47.84/AppSTod.aspx?roomid=2206&hei=608&wd=1366";
                Intent intent5 = new Intent(getActivity(), Room2Activity.class);
                intent5.putExtra("urlkey", url5);
                intent5.putExtra("library_room", "中区206今日座位情况");
                startActivity(intent5);
                break;
            case R.id.today_bt_6:
                String url6 = "http://172.16.47.84/AppSTod.aspx?roomid=2211&hei=608&wd=1366";
                Intent intent6 = new Intent(getActivity(), Room2Activity.class);
                intent6.putExtra("urlkey", url6);
                intent6.putExtra("library_room", "中区211今日座位情况");
                startActivity(intent6);
                break;
            case R.id.today_bt_7:
                String url7 = "http://172.16.47.84/AppSTod.aspx?roomid=3207&hei=999&wd=1366";
                Intent intent7 = new Intent(getActivity(), Room2Activity.class);
                intent7.putExtra("urlkey", url7);
                intent7.putExtra("library_room", "西区207今日座位情况");
                startActivity(intent7);
                break;
            case R.id.today_bt_8:
                String url8 = "http://172.16.47.84/AppSTod.aspx?roomid=3401&hei=999&wd=1366";
                Intent intent8 = new Intent(getActivity(), Room2Activity.class);
                intent8.putExtra("urlkey", url8);
                intent8.putExtra("library_room", "西区401今日座位情况");
                startActivity(intent8);
                break;
            case R.id.today_bt_9:
                String url9 = "http://172.16.47.84/AppSTod.aspx?roomid=3408&hei=608&wd=1366";
                Intent intent9 = new Intent(getActivity(), Room2Activity.class);
                intent9.putExtra("urlkey", url9);
                intent9.putExtra("library_room", "西区408今日座位情况");
                startActivity(intent9);
                break;


            case R.id.tomorrow_bt_1:
                String url1_tomorrow = "http://172.16.47.84/AppSTom.aspx?roomid=1201&hei=632&wd=1366";
                Intent intent1_tomorrow = new Intent(getActivity(), Room2Activity.class);
                intent1_tomorrow.putExtra("urlkey", url1_tomorrow);
                intent1_tomorrow.putExtra("library_room", "东区201明日座位情况");
                startActivity(intent1_tomorrow);
                break;
            case R.id.tomorrow_bt_2:
                String url2_tomorrow = "http://172.16.47.84/AppSTom.aspx?roomid=1401&hei=999&wd=1366";
                Intent intent2_tomorrow = new Intent(getActivity(), Room2Activity.class);
                intent2_tomorrow.putExtra("urlkey", url2_tomorrow);
                intent2_tomorrow.putExtra("library_room", "东区401明日座位情况");
                startActivity(intent2_tomorrow);
                break;
            case R.id.tomorrow_bt_3:
                String url3_tomorrow = "http://172.16.47.84/AppSTom.aspx?roomid=2101&hei=608&wd=1366";
                Intent intent3_tomorrow = new Intent(getActivity(), Room2Activity.class);
                intent3_tomorrow.putExtra("urlkey", url3_tomorrow);
                intent3_tomorrow.putExtra("library_room", "中区101明日座位情况");
                startActivity(intent3_tomorrow);
                break;
            case R.id.tomorrow_bt_4:
                String url4_tomorrow = "http://172.16.47.84/AppSTom.aspx?roomid=2201&hei=999&wd=1366";
                Intent intent4_tomorrow = new Intent(getActivity(), Room2Activity.class);
                intent4_tomorrow.putExtra("urlkey", url4_tomorrow);
                intent4_tomorrow.putExtra("library_room", "中区201明日座位情况");
                startActivity(intent4_tomorrow);
                break;
            case R.id.tomorrow_bt_5:
                String url5_tomorrow = "http://172.16.47.84/AppSTom.aspx?roomid=2206&hei=608&wd=1366";
                Intent intent5_tomorrow = new Intent(getActivity(), Room2Activity.class);
                intent5_tomorrow.putExtra("urlkey", url5_tomorrow);
                intent5_tomorrow.putExtra("library_room", "中区206明日座位情况");
                startActivity(intent5_tomorrow);
                break;
            case R.id.tomorrow_bt_6:
                String url6_tomorrow = "http://172.16.47.84/AppSTom.aspx?roomid=2211&hei=608&wd=1366";
                Intent intent6_tomorrow = new Intent(getActivity(), Room2Activity.class);
                intent6_tomorrow.putExtra("urlkey", url6_tomorrow);
                intent6_tomorrow.putExtra("library_room", "中区211明日座位情况");
                startActivity(intent6_tomorrow);
                break;
            case R.id.tomorrow_bt_7:
                String url7_tomorrow = "http://172.16.47.84/AppSTom.aspx?roomid=3207&hei=999&wd=1366";
                Intent intent7_tomorrow = new Intent(getActivity(), Room2Activity.class);
                intent7_tomorrow.putExtra("urlkey", url7_tomorrow);
                intent7_tomorrow.putExtra("library_room", "西区207明日座位情况");
                startActivity(intent7_tomorrow);
                break;
            case R.id.tomorrow_bt_8:
                String url8_tomorrow = "http://172.16.47.84/AppSTom.aspx?roomid=3401&hei=999&wd=1366";
                Intent intent8_tomorrow = new Intent(getActivity(), Room2Activity.class);
                intent8_tomorrow.putExtra("urlkey", url8_tomorrow);
                intent8_tomorrow.putExtra("library_room", "西区401明日座位情况");
                startActivity(intent8_tomorrow);
                break;
            case R.id.tomorrow_bt_9:
                String url9_tomorrow = "http://172.16.47.84/AppSTom.aspx?roomid=3408&hei=608&wd=1366";
                Intent intent9_tomorrow = new Intent(getActivity(), Room2Activity.class);
                intent9_tomorrow.putExtra("urlkey", url9_tomorrow);
                intent9_tomorrow.putExtra("library_room", "西区408明日座位情况");
                startActivity(intent9_tomorrow);
                break;
        }
    }

}
