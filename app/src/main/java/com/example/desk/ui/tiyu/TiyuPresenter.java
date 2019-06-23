package com.example.desk.ui.tiyu;

import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.example.desk.entity.TiYu;
import com.example.desk.mvp.BasePresenterImpl;
import com.example.desk.util.TLog;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class TiyuPresenter extends BasePresenterImpl<TiyuContract.View> implements TiyuContract.Presenter{
    private String cookiea;
    private String Quanjuxuehao;//全局的学号
    private int STATUE ; //全局的状态码，状态码不同，查询的数据不同
    private String url;
    private List<TiYu> tiYuList = new ArrayList<>();
    private  Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 4:
                    //4拿到网页分页内容，接着解析
                    String zaoresult2 = msg.obj.toString();
                    Document d2 = Jsoup.parse(zaoresult2);
                    Element E2 = d2.getElementById("dataTable");
                    Elements p2 = E2.getElementsByClass("grid_number");
                    Elements t2 = E2.getElementsByClass("grid_data");
                    List<String> stringList2 = new ArrayList<>();
                    for (int i = 0; i < p2.size(); i++) {
                        stringList2.add(p2.get(i).text());//添加每行数据的序号
                    }
                    List<String> strings2 = new ArrayList<>();
                    for (int i = 0; i <t2.size() ; i++) {
                        strings2.add(t2.get(i).text());//添加每行数据的内容
                    }
                    switch (STATUE ){

                        case 9:
                            for (int jj = 0; jj<stringList2.size();jj++) {
                                TiYu dataQuery = new TiYu();
                                dataQuery.setNumid(Integer.parseInt(stringList2.get(jj)));
                                for (int ii = 6*jj; ii < 6*(jj+1); ii++) {
                                    dataQuery.setXuehao(strings2.get(ii));
                                    ii++;
                                    dataQuery.setName(strings2.get(ii));
                                    ii++;
                                    dataQuery.setCardId(strings2.get(ii));
                                    ii++;
                                    dataQuery.setD(strings2.get(ii));
                                    ii++;
                                    dataQuery.setIsvalid(strings2.get(ii));
                                    ii++;
                                }
                                tiYuList.add(dataQuery);
                            }
                            break;
                        case 8:
                        case 7:
                            for (int jj = 0; jj<stringList2.size();jj++) {
                                TiYu dataQuery = new TiYu();
                                dataQuery.setNumid(Integer.parseInt(stringList2.get(jj)));
                                for (int ii = 8*jj; ii < 8*(jj+1); ii++) {
                                    dataQuery.setXuehao(strings2.get(ii));
                                    ii++;
                                    dataQuery.setName(strings2.get(ii));
                                    ii++;
                                    dataQuery.setCardId(strings2.get(ii));
                                    ii++;
                                    String date = strings2.get(ii);
                                    ii++;
                                    String datetime = strings2.get(ii);

                                    dataQuery.setD(date + " " + datetime);
                                    ii++;
                                    dataQuery.setIsvalid(strings2.get(ii));
                                    ii++;
                                    ii++;
                                }
                                tiYuList.add(dataQuery);
                            }
                            break;
                    }

                    mView.success(tiYuList);
                    break;

                case 3:
                    //3拿到网页内容后,去解析,看看存不存在分页，存在分页的话，再去访问
                    String result = msg.obj.toString();
                    Document d = Jsoup.parse(result);
                    //找分页
                    String sudg = d.getElementById("PageNavBar").getElementById("PageSelectorMemo").text();
                    String g = sudg.substring(sudg.length() - 3, sudg.length() - 1);
                    String regEx="[^0-9]";
                    Pattern ph = Pattern.compile(regEx);
                    Matcher m = ph.matcher(g);
                    System.out.println( m.replaceAll("").trim());
                    int totoalrecord = Integer.parseInt(m.replaceAll("").trim());
                    int flag = totoalrecord > 20 ? 2 :1;

                    Element ll = d.getElementById("dataTable");
                    Elements p = ll.getElementsByClass("grid_number");
                    Elements tt = ll.getElementsByClass("grid_data");
                    List<String> stringList = new ArrayList<>();
                    for (int i = 0; i < p.size(); i++) {
                        stringList.add(p.get(i).text());//添加每行数据的序号
                    }
                    List<String> strings = new ArrayList<>();
                    for (int i = 0; i <tt.size() ; i++) {
                        strings.add(tt.get(i).text());//添加每行数据的内容
                    }
                    switch (STATUE){
                        case 9:
                            url = "http://172.16.50.71/personQueryZC_personalDetailQuery.html?pageNum=2";
                            for (int jj = 0; jj<stringList.size();jj++) {
                                TiYu dataQuery = new TiYu();
                                dataQuery.setNumid(Integer.parseInt(stringList.get(jj)));
                                for (int i = 6*jj; i < 6*(jj+1); i++) {
                                    //System.out.println(strings.get(i));
                                    dataQuery.setXuehao(strings.get(i));
                                    i++;
                                    dataQuery.setName(strings.get(i));
                                    i++;
                                    dataQuery.setCardId(strings.get(i));
                                    i++;
                                    dataQuery.setD(strings.get(i));
                                    i++;
                                    dataQuery.setIsvalid(strings.get(i));
                                    i++;
                                }
                                tiYuList.add(dataQuery);
                            }
                            break;
                        case 8:
                            url = "http://172.16.50.71/attendanceZZXX_list.html?pageNum=2";
                            for (int jj = 0; jj<stringList.size();jj++) {
                                TiYu dataQuery = new TiYu();
                                dataQuery.setNumid(Integer.parseInt(stringList.get(jj)));
                                for (int i = 8*jj; i < 8*(jj+1); i++) {
                                    //System.out.println(strings.get(i));
                                    dataQuery.setXuehao(strings.get(i));
                                    i++;
                                    dataQuery.setName(strings.get(i));
                                    i++;
                                    dataQuery.setCardId(strings.get(i));
                                    i++;
                                    String date = strings.get(i);//得到刷卡日期
                                    i++;
                                    String datetime = strings.get(i); //得到刷卡具体时间
                                    dataQuery.setD(date+" "+datetime);
                                    i++;
                                    dataQuery.setIsvalid(strings.get(i));
                                    i++;
                                    i++;
                                }
                                tiYuList.add(dataQuery);
                            }
                            break;
                        case 7:
                            url = "http://172.16.50.71/attendanceSTTZ_list.html?pageNum=2";
                            for (int jj = 0; jj<stringList.size();jj++) {
                                TiYu dataQuery = new TiYu();
                                dataQuery.setNumid(Integer.parseInt(stringList.get(jj)));
                                for (int i = 8*jj; i < 8*(jj+1); i++) {
                                    //System.out.println(strings.get(i));
                                    dataQuery.setXuehao(strings.get(i));
                                    i++;
                                    dataQuery.setName(strings.get(i));
                                    i++;
                                    dataQuery.setCardId(strings.get(i));
                                    i++;
                                    String date = strings.get(i);//得到刷卡日期
                                    i++;
                                    String datetime = strings.get(i); //得到刷卡具体时间
                                    dataQuery.setD(date+" "+datetime);
                                    i++;
                                    dataQuery.setIsvalid(strings.get(i));
                                    i++;
                                    i++;
                                }
                                tiYuList.add(dataQuery);
                            }
                            break;
                    }
                    if (flag ==2){
                        OkHttpClient zaocaookhttpclient= new OkHttpClient.Builder()
                                .connectTimeout(5000,TimeUnit.MILLISECONDS)
                                .build();
                        Request zaocaorequest = new Request.Builder()
                                .url(url)
                                .addHeader("Cookie",cookiea)
                                .addHeader("Host","172.16.50.71")
                                .addHeader("Referer","http://172.16.50.71/home_index.html")
                                .build();
                        zaocaookhttpclient.newCall(zaocaorequest).enqueue(new okhttp3.Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {

                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                String result = response.body().string();
                                Message message=handler.obtainMessage();
                                message.what=4;
                                message.obj=result;
                                handler.sendMessage(message);
                            }
                        });

                    }else {
                        mView.success(tiYuList);
                    }

                    mView.parsehtml(result);
                    break;
                case 2:
                    //来到2之后抓取早操网页，提交给3
                    OkHttpClient zaocaookhttpclient= new OkHttpClient.Builder()
                            .connectTimeout(5000,TimeUnit.MILLISECONDS)
                            .build();
                    switch (STATUE){
                        case 9:
                            url = "http://172.16.50.71/personQueryZC_personalDetailQuery.html";
                            break;
                        case 8:
                            url = "http://172.16.50.71/attendanceZZXX_list.html";
                            break;
                        case 7:
                            url = "http://172.16.50.71/attendanceSTTZ_list.html";
                            break;
                    }
                    Request zaocaorequest = new Request.Builder()
                            .url(url)
                            .addHeader("Cookie",cookiea)
                            .addHeader("Host","172.16.50.71")
                            .addHeader("Referer","http://172.16.50.71/home_index.html")
                            .build();
                        zaocaookhttpclient.newCall(zaocaorequest).enqueue(new okhttp3.Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {

                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                String result = response.body().string();
                                Message message=handler.obtainMessage();
                                message.what=3;
                                message.obj=result;
                                handler.sendMessage(message);
                            }
                        });


                    break;
                case 1:
                    //拿到访问页面的cookie
                    //去登陆，登陆成功去 case 2
                    String cookie = msg.obj.toString().substring(0,43);
                    cookiea = cookie;
                    TLog.error(msg.obj.toString().substring(0,43));
                   OkHttpClient loginokhttpclient= new OkHttpClient.Builder()
                            .connectTimeout(5000,TimeUnit.MILLISECONDS)
                            .build();
                    FormBody formBody = new FormBody.Builder()
                            .add("loginName",Quanjuxuehao)
                            .add("password",Quanjuxuehao)
                            .build();
                    Request loginrequest = new Request.Builder()
                            .url("http://172.16.50.71/user_login.html")
                            .addHeader("Cookie",cookie)
                            .post(formBody)
                            .build();
                    loginokhttpclient.newCall(loginrequest).enqueue(new okhttp3.Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            Message message=handler.obtainMessage();
                            message.what=2;
                            message.obj=response.headers().toString();
                            handler.sendMessage(message);
                        }
                    });

                    break;
            }
        }
    };

    @Override
    public void getTiyuData(String xuehao, int i) {
        Quanjuxuehao = xuehao;
        STATUE = i;
        fisrtwelcome(xuehao);//访问第一个页面拿到cookie,然后设为全局变量
    }
    private void fisrtwelcome(String xuehao){
        OkHttpClient okhttpclient= new OkHttpClient.Builder()
                .connectTimeout(5000,TimeUnit.MILLISECONDS)
                .build();
        Request request = new Request.Builder()
                .url("http://172.16.50.71/user_login.html")
                .build();
        okhttpclient.newCall(request)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        mView.onfail();
                    }
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Headers headers = response.headers();
                        List<Cookie> c = Cookie.parseAll(request.url(), headers);
                        Message message=handler.obtainMessage();
                        message.what=1;
                        message.obj=c.get(0).toString();
                        handler.sendMessage(message);
                    }
                });
    }
}
