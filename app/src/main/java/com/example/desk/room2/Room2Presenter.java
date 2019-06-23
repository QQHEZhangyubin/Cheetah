package com.example.desk.room2;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.example.desk.mvp.BasePresenterImpl;
import com.example.desk.util.ShareUtils;
import com.example.desk.util.StaticClass;
import com.example.desk.util.TLog;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

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

public class Room2Presenter extends BasePresenterImpl<Room2Contract.View> implements Room2Contract.Presenter{
    private String librarycookiea;
    private Context mContext;
    private String URl ;
    private String LibraryXuehao;
    private String Library_Mima;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 6:
                    //用来显示预约成功，你已经预约过座位了，你被禁止预约信息
                    String t = msg.obj.toString();
                    mView.display2(t);
                    break;

                case 4:
                    OkHttpClient tomorrowokhttpclient= new OkHttpClient.Builder()
                            .connectTimeout(5000,TimeUnit.MILLISECONDS)
                            .build();

                    Request tomorrowrequest = new Request.Builder()
                            .url("http://172.16.47.84/Skip.aspx")
                            .addHeader("Cookie",librarycookiea)
                            .addHeader("Host","172.16.47.84")
                            //.addHeader("Referer","http://172.16.47.84/DayNavigation.aspx")
                            .build();
                    tomorrowokhttpclient.newCall(tomorrowrequest).enqueue(new okhttp3.Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                                String tomorrowresult = response.body().string();
                                Document tomorrowhtml = Jsoup.parse(tomorrowresult);
                                Message message=handler.obtainMessage();
                                message.what=6;
                                message.obj = tomorrowhtml.select("title").text();
                                handler.sendMessage(message);

                        }
                    });
                    break;
                case 3:
                    String result = msg.obj.toString();
                    mView.display(result);
                    break;

                case 2:
                    OkHttpClient chazuookhttpclient= new OkHttpClient.Builder()
                            .connectTimeout(5000,TimeUnit.MILLISECONDS)
                            .build();

                    Request chazuorequest = new Request.Builder()
                            .url(URl)
                            .addHeader("Cookie",librarycookiea)
                            .addHeader("Host","172.16.47.84")
                            .addHeader("Referer","http://172.16.47.84/DayNavigation.aspx")
                            .build();
                    chazuookhttpclient.newCall(chazuorequest).enqueue(new okhttp3.Callback() {
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
                    String cookie = msg.obj.toString().substring(0,msg.obj.toString().indexOf(";"));
                    TLog.error(cookie);

                    librarycookiea = cookie;
                    ShareUtils.putString(mContext.getApplicationContext(),StaticClass.librarycookie,cookie);

                    OkHttpClient library_login_okhttpclient= new OkHttpClient.Builder()
                            .connectTimeout(5000,TimeUnit.MILLISECONDS)
                            .build();
                    FormBody formBody = new FormBody.Builder()
                            .add("__EVENTTARGET","")
                            .add("__EVENTARGUMENT","")
                            .add("__VIEWSTATE","/wEPDwUJODE2Mzg3MTk0D2QWAgIDD2QWAgIEDw8WAh4EVGV4dAUY5biQ5Y+35oiW5a+G56CB6ZSZ6K+v77yBZGRk3l/q5CAoXlOamfHOTYdroToqSkkGWTefCZJTqQaEVRo=")
                            .add("__EVENTVALIDATION","/wEWBALBkJDVAQLs0bLrBgLs0fbZDAKM54rGBnjpZ01SieVIZvHrx+LlFQ1pTCE9lOQ/D0f9m//kwtCG")
                            .add("TextBox1",LibraryXuehao)
                            .add("TextBox2",Library_Mima)
                            .add("Button1","登      录")
                            .build();
                    Request library_login_request = new Request.Builder()
                            .url("http://172.16.47.84/Login.aspx")
                            .addHeader("Cookie",cookie)
                            .post(formBody)
                            .build();
                    library_login_okhttpclient.newCall(library_login_request).enqueue(new okhttp3.Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            Message message=handler.obtainMessage();
                            message.what=2;//其实没什么传递的
                            message.obj=response.headers().toString();
                            handler.sendMessage(message);
                        }
                    });
                    break;
            }
        }
    };
    @Override
    public void getLibraryData(Context context, String url) {
        mContext = context;
        URl = url;
        String library_xuehao = ShareUtils.getString(mContext.getApplicationContext(), StaticClass.userid, "");
        String library_mima = ShareUtils.getString(mContext.getApplicationContext(),StaticClass.password,"");
        LibraryXuehao = library_xuehao;

        Library_Mima = library_mima;

        OkHttpClient okhttpclient= new OkHttpClient.Builder()
                .connectTimeout(5000,TimeUnit.MILLISECONDS)
                .build();
        Request request = new Request.Builder()
                .url("http://172.16.47.84/Login.aspx")
                .build();
        okhttpclient.newCall(request)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        mView.display2("请使用校内网查看图书馆座位数据");
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

    @Override
    public void yuyuelibrary(Context room2Activity,String seatid) {
        OkHttpClient yuyueokhttpclient= new OkHttpClient.Builder()
                .connectTimeout(5000,TimeUnit.MILLISECONDS)
                .build();
        Request yuyuerequest = new Request.Builder()
                .addHeader("Cookie",ShareUtils.getString(room2Activity.getApplicationContext(),StaticClass.librarycookie,""))
                .addHeader("Host","172.16.47.84")
                .addHeader("Referer","http://172.16.47.84/AppSTod.aspx?roomid=1201&hei=608&wd=1366")
                .url("http://172.16.47.84/SkipToday.aspx?" + "seatid="+seatid)
                .build();
        yuyueokhttpclient.newCall(yuyuerequest).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                    String h = response.body().string();
                Document d = Jsoup.parse(h);
                Elements alert = d.getElementsByTag("script");

                String result =  alert.get(0).data().substring(alert.get(0).data().indexOf("('")+2,alert.get(0).data().indexOf("')"));
                //TLog.error(result  );
                Message message=handler.obtainMessage();
                message.what=6;
                message.obj=result;
                handler.sendMessage(message);

            }
        });

    }

    @Override
    public void yuyueminglibrary(Context context, String seatid) {
        OkHttpClient yuyueokhttpclient= new OkHttpClient.Builder()
                .connectTimeout(5000,TimeUnit.MILLISECONDS)
                .build();
        Request yuyuerequest = new Request.Builder()
                .addHeader("Cookie",ShareUtils.getString(context.getApplicationContext(),StaticClass.librarycookie,""))
                .addHeader("Host","172.16.47.84")
                .addHeader("Referer","http://172.16.47.84/AppSTom.aspx?roomid=1201&hei=608&wd=1366")
                .url("http://172.16.47.84/ClickVerifyCode.aspx?" + "seatid="+seatid)
                .build();
        yuyueokhttpclient.newCall(yuyuerequest).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String h = response.body().string();

                Document d = Jsoup.parse(h);
                Elements alert = d.getElementsByTag("script");
                //String result =  alert.get(0).data().substring(alert.get(0).data().indexOf("('")+2,alert.get(0).data().indexOf("')"));
                //TLog.error(alert.size()+"");
                if (alert.size() ==3){
                    Message message=handler.obtainMessage();
                    message.what=4;
                    message.obj="";
                    handler.sendMessage(message);
                }else if (alert.size() == 4){
                    Message message=handler.obtainMessage();
                    message.what=6;
                    message.obj= alert.get(0).data().substring(alert.get(0).data().indexOf("('")+2,alert.get(0).data().indexOf("')"));
                    handler.sendMessage(message);
                }


            }
        });
    }
}
