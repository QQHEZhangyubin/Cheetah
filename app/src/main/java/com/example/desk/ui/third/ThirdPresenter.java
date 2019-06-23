package com.example.desk.ui.third;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.example.desk.api.APIWrapper;
import com.example.desk.entity.MyState;
import com.example.desk.entity.Status;
import com.example.desk.entity.T5;
import com.example.desk.entity.Token;
import com.example.desk.mvp.BasePresenterImpl;
import com.example.desk.util.StaticClass;
import com.example.desk.util.TLog;
import com.qiniu.android.common.FixedZone;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;


import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class ThirdPresenter extends BasePresenterImpl<ThirdContract.View> implements ThirdContract.Presenter{

    private String change1;
    private MyState myState1;
    private String uploadtouxiangmessage;
    private String librarycookiea2;
   // private String viewstate;
    private String USERID;//全局学号；
    private String PASSWORD;//全局密码

    private String ISDAY;//全局变量，赋值今天还是昨天，用于选择URL

    private boolean ChckeMystate = false;

    private String TOKEN;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 6:
                    mView.EndUseResult("取消预约成功");
                    break;

                case 5:
                    //用于取出座位数据，查询，展示给用户当前状态
                    String statehtml = msg.obj.toString();
                    List<String> tomorrowstate = new ArrayList<>();
                    Document statedocument = Jsoup.parse(statehtml);
                    Element e = statedocument.getElementById("GridView2");
                    if (e != null){
                        Elements trs = e.select("tr");
                        for (Element tr : trs){
                            Elements tds = tr.select("td");
                            for (Element td :tds){
                                TLog.error(td.text());
                                tomorrowstate.add(td.text());
                            }
                        }
                    }
                    List<String> todaystate = new ArrayList<>();
                    Element e1 = statedocument.getElementById("GridView1");
                    if (e1 != null){
                        Elements trs1 = e1.select("tr");
                        for (Element tr1 : trs1){
                            Elements tds1 = tr1.select("td");
                            for (Element td1 :tds1){
                                TLog.error(td1.text());
                                todaystate.add(td1.text());
                            }
                        }
                    }else {
                        TLog.error("GridView1 是 null");
                    }
                    mView.SeeMyState2(todaystate,tomorrowstate);
                    break;

                case 4:
                    OkHttpClient pokhttpclient2= new OkHttpClient.Builder()
                            .connectTimeout(5000,TimeUnit.MILLISECONDS)
                            .build();
                    Request prequest2 = new Request.Builder()
                            .url("http://172.16.47.84/InfoSearch.aspx")
                            .addHeader("Cookie",librarycookiea2)
                            .addHeader("Host","172.16.47.84")
                            .addHeader("Referer","http://172.16.47.84/LeftStu.aspx")
                            .build();
                    pokhttpclient2.newCall(prequest2).enqueue(new okhttp3.Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String hj = response.body().string();
                            TLog.error(hj);
                            //拿到 取消预约座位 所需要的数据
                            if (ChckeMystate){
                                Message message=handler.obtainMessage();
                                message.what=5;
                                message.obj = hj;
                                handler.sendMessage(message);
                            }else {
                                Document document = Jsoup.parse( hj);
                                Elements ele = document.select("input[name=__VIEWSTATE]");
                                Element element=ele.get(0);
                                String viewstate = element.attr("value");
                                Elements elem = document.select("input[name=__EVENTVALIDATION]");
                                String  eventvalidation =  elem.get(0).attr("value");
                                Bundle bundle = new Bundle();
                                bundle.putString("viewstate",viewstate);
                                bundle.putString("eventvalidation",eventvalidation);
                                Message message=handler.obtainMessage();
                                message.what=2;
                                message.setData(bundle);
                                handler.sendMessage(message);
                            }


                        }
                    });

                    break;
                case 2:
                    Bundle bundel = msg.getData();
                    //viewstate = msg.obj.toString();
                    TLog.error("__VIEWSTATE="+bundel.getString("viewstate"));
                    TLog.error("__EVENTVALIDATION="+ bundel.getString("eventvalidation"));
                    OkHttpClient pokhttpclient= new OkHttpClient.Builder()
                            .connectTimeout(5000,TimeUnit.MILLISECONDS)
                            .build();
                    String cancelurl = "";
                    switch (ISDAY){
                        case "TODAY":
                            String http = "http://172.16.47.84/InfoSearch.aspx?";
                            String common = "ScriptManager1=UpdatePanel1%7CGridView1%24ctl02%24Button2&__EVENTTARGET=&__EVENTARGUMENT=&__VIEWSTATE=";
                            String common1 = "&__VIEWSTATEENCRYPTED=&__EVENTVALIDATION=";
                            String common2 = "&TextBox7=&__ASYNCPOST=true&GridView1%24ctl02%24Button2=%E5%8F%96%E6%B6%88%E9%A2%84%E7%BA%A6";
                            String viewstate = bundel.getString("viewstate");
                            String eventvalidation = bundel.getString("eventvalidation");
                            String VL = eventvalidation.replaceAll("=", "%3D").replaceAll("\\+", "%2B");
                            String VS = viewstate.replaceAll("\\+", "%2B").replaceAll("/", "%2F").replaceAll("=", "%3D");
                            cancelurl = http+common+VS+common1+VL+common2;
                             //cancelurl = "http://172.16.47.84/InfoSearch.aspx?ScriptManager1=UpdatePanel1%7CGridView1%24ctl02%24Button2&__EVENTTARGET=&__EVENTARGUMENT=&__VIEWSTATE=bZS80fdM4PelB1wklplayCxDVovse7q7Peq8VYGPNATX7cfSCL5a%2Ble%2F%2BnjwjDHLweSBvB0MbP2nB9hha8iFLmSK0obj2pmxb7MU3bHn6Ef1EO%2BSEPYBEO7DWdpbN0WF3Z%2Fec21n%2FUWgriLlfSCjvQ17xUeDBq9uSa7qd%2BrJvJGZGBPLHo7xo29D4cprddO8l2qKzLvv5rJnZH861KrYpzmLKGtv2tD52VgeyXrwLXhk9kaQS1pq5T9hss%2B9W28QLga9O%2BxDzbsIfjkVophPV09ZPU78eV45gSBG3BaOnpOapA5sOY4WBHWMagARCyN0J8Ng%2F8b1ZtbOcjSQ2XaBu0XJUSeTeY8ozNMe8QuWDEoBtOg95PCbm5RfTI3CpkQTVCrGDPUuQ8vnfZakPzf%2ByElJ0Q893Ya%2F4zW2gqwEhIOCs43OJkHYICFAZCDDzyhLDUVmxPSLO47OEVwZGU8lMuScB8pn9WGDb8YqdQIExVSueW9biOdFId76jJv3tj3zoi0sNGXFwRFaOT4hxFeKlmHy7gMORsAR0MMPl7kApcIIIF67AqE8Ev%2B8nORZ4VA2PqPLxFq%2BkLjxECfmB6ZqHGeoA%2Bbsu%2BuH1QOtmnIV3rtvQzITxwAK7CE79XMs4ATezfo3MRZ8e%2BY4wzDWAycmGCW1S3GujBKr33QN4yTj0K0lmsn2QWnUs9nULalZSAwveUikYabOmb%2BUOqHibnomun2bPQZPlCMgCrrhQ5MDIZevglqNVDSmad9uuhRBmbuS%2FReLiypOx7LEpaGarZyU4Ac4Z81FdBvMUmTXKX8Bwy8%3D&__VIEWSTATEENCRYPTED=&__EVENTVALIDATION=9wa%2BROgfXmcvl9k91z3YujbrJDJI1DHnp6NGtJRKhOo%3D&TextBox7=&__ASYNCPOST=true&GridView1%24ctl02%24Button2=%E5%8F%96%E6%B6%88%E9%A2%84%E7%BA%A6";
                             break;
                        case "TOMORROW":
                            //cancelurl = "http://172.16.47.84/InfoSearch.aspx?ScriptManager1=UpdatePanel2%7CGridView2%24ctl02%24Button2&__EVENTTARGET=&__EVENTARGUMENT=&__VIEWSTATE=bZS80fdM4PelB1wklplayCxDVovse7q7Peq8VYGPNATX7cfSCL5a%2Ble%2F%2BnjwjDHLweSBvB0MbP2nB9hha8iFLrQz1QDHDzhvopfRV%2BF8MjROBKW9hYv4iDU9sGNSf21Jp4Gbb9tshrCHF6hcspPMA7N02SW%2BZSwS7c6SemNxj3i8vboP0fLWe2rCAB304MG4Ei2VA8Dxu8egJk9wgxduC%2FjDgEH0O4z%2B7yXWTERXo2DKNnD4Yy6Xt9y%2FeMP47tCey6ypJc7FNd2m5gWSzrMJQsRR985T11QgYXbMob3kZLcxHbHBskyRPljQRPTohXAXEmDDJv7Wfcn9NigT5ixv3r1T4PZ615QHRfsGgiOr5olwiN2xTJp6DJ%2Fs6kFij%2F1P4gI8zMIcxhLmr0IuHMtGXpr1V5mJZVYdFJTg5%2FWFpTPTj%2FnOapvopnsbVw9jnR6kohuo72xzH%2BoDfuB4wTBVOjmGJG%2FzeeF2ywxnMlJkO8cRLZAsVeKuVlzS0DLyzGtaio3pcXLF3yK5gxNcZtVFu6Atlk1Vp%2BwwzMHcjlWz0zrrN6w7ClzRgXOI7kndDRSNrrpaN2%2BQ0MmAKQih%2B0yKoIZvhZ6Cr7MAMjyMODNRBbAf1nFySUovmwNpD0vU4jQc2gWf3HNCt%2ForVxzGUbaAlVUtxL%2FwWcgrFKTQkPtmxFxAinh4oZIjfg5U7I7g8qh3D%2FFroeDkoERsXMLcpjbUBwbSKL3n1BWlBhXA%2BiN3vXnZuvV3ebnSnH0YQwgaPuuB2%2BDaYyCIoyhe8wJ9Z5LrA4QTVPfA5PKBWdNWVxKB9bw%3D&__VIEWSTATEENCRYPTED=&__EVENTVALIDATION=PDhMrQZqSBQLsxcty5YsNFYbPw%2Ba%2BKE7aw2ANW5X3Gk%3D&TextBox7=&__ASYNCPOST=true&GridView2%24ctl02%24Button2=%E5%8F%96%E6%B6%88%E9%A2%84%E7%BA%A6";
                            String httpT = "http://172.16.47.84/InfoSearch.aspx?";
                            String commonT = "ScriptManager1=UpdatePanel2%7CGridView2%24ctl02%24Button2&__EVENTTARGET=&__EVENTARGUMENT=&__VIEWSTATE=";
                            String common1T = "&__VIEWSTATEENCRYPTED=&__EVENTVALIDATION=";
                            String common2T = "&TextBox7=&__ASYNCPOST=true&GridView2%24ctl02%24Button2=%E5%8F%96%E6%B6%88%E9%A2%84%E7%BA%A6";
                            String viewstateT = bundel.getString("viewstate");
                            String eventvalidationT = bundel.getString("eventvalidation");
                            String VLT = eventvalidationT.replaceAll("=", "%3D").replaceAll("\\+", "%2B");
                            String VST = viewstateT.replaceAll("\\+", "%2B").replaceAll("/", "%2F").replaceAll("=", "%3D");
                            cancelurl = httpT+commonT+VST+common1T+VLT+common2T;
                            break;
                    }
                    Request prequest = new Request.Builder()
                            //.url("http://172.16.47.84/InfoSearch.aspx?ScriptManager1=UpdatePanel1%7CGridView1%24ctl02%24Button2&__EVENTTARGET=&__EVENTARGUMENT=&__VIEWSTATE=bZS80fdM4PelB1wklplayCxDVovse7q7Peq8VYGPNATX7cfSCL5a%2Ble%2F%2BnjwjDHLweSBvB0MbP2nB9hha8iFLmSK0obj2pmxb7MU3bHn6Ef1EO%2BSEPYBEO7DWdpbN0WF3Z%2Fec21n%2FUWgriLlfSCjvQ17xUeDBq9uSa7qd%2BrJvJGZGBPLHo7xo29D4cprddO8l2qKzLvv5rJnZH861KrYpzmLKGtv2tD52VgeyXrwLXhk9kaQS1pq5T9hss%2B9W28QLga9O%2BxDzbsIfjkVophPV09ZPU78eV45gSBG3BaOnpOapA5sOY4WBHWMagARCyN0J8Ng%2F8b1ZtbOcjSQ2XaBu0XJUSeTeY8ozNMe8QuWDEoBtOg95PCbm5RfTI3CpkQT%2F%2F92%2FehgBbzp%2BOFPG%2FnyvbkcWBBrD93EVesCMx%2BF0xUz3aDRYZsDPxSKV3YfMbadScEqx5T9PcYsGHpB%2BfwTM8iXxrEg9L6iUYWB6jAlL8CV9%2BeaMxAi%2Bkyy%2Bs9P%2B4Tdn4FT3vQDluRPE%2F2QAB9WJO2yjYURYVq3WEJIe8LJlZc42lGamBHVbeyfutn5H6HZ7qkqugUfWArwPiD%2FWAv%2FWjocV%2FfA%2BewXrBWshRom%2F6Mt77e5oIe0egZEGqmKMB%2BYmEH0S3keF6W7ylFu%2BWFoW4xtjVVJNDWExeccMOy5nLlhmVmFZTo6nr3sxjdjuE9cu9HBmSj%2BlpALC%2B0P9BkgW30cLe%2FV9Thu6TqZouKqBBSfWJ8cDTpd0BqjSmaARHxo9iOX8cyNI5Y0etJQsr8H8ZMZSok3P1YZpmP55h98fks%3D&__VIEWSTATEENCRYPTED=&__EVENTVALIDATION=Qdui2nj9IF8kQ7ZOtx799gWjRjZ9AeelOxsHE9vrOIY%3D&TextBox7=&__ASYNCPOST=true&GridView1%24ctl02%24Button2=%E5%8F%96%E6%B6%88%E9%A2%84%E7%BA%A6")
                            .url(cancelurl)
                            .addHeader("Accept-Language","zh-CN,zh;q=0.9")
                            .addHeader("Content-Type","application/x-www-form-urlencoded; charset=UTF-8")
                            .addHeader("Cookie",librarycookiea2)
                            .addHeader("Host","172.16.47.84")
                            .addHeader("Origin","http://172.16.47.84")
                           // .addHeader("Referer","http://172.16.47.84/InfoSearch.aspx")
                            //.post(formBody)
                            .build();
                    pokhttpclient.newCall(prequest).enqueue(new okhttp3.Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            TLog.error(response.body().string());
                            Message message=handler.obtainMessage();
                            message.what=6;
                            message.obj = "";
                            handler.sendMessage(message);

                        }
                    });
                    break;

                case 1:
                    Bundle bundellogin = msg.getData();
                    String cookie = bundellogin.getString("cookie").substring(0,bundellogin.getString("cookie").indexOf(";"));
                    TLog.error(cookie);
                    librarycookiea2 =cookie;
                    OkHttpClient library_canceltomorrow_okhttpclient= new OkHttpClient.Builder()
                            .connectTimeout(5000,TimeUnit.MILLISECONDS)
                            .build();
                    FormBody formBody2 = new FormBody.Builder()
                            .add("__EVENTTARGET","")
                            .add("__EVENTARGUMENT","")
                            .add("__VIEWSTATE","/wEPDwUJODE2Mzg3MTk0D2QWAgIDD2QWAgIEDw8WAh4EVGV4dAUY5biQ5Y+35oiW5a+G56CB6ZSZ6K+v77yBZGRk3l/q5CAoXlOamfHOTYdroToqSkkGWTefCZJTqQaEVRo=")
                            .add("__EVENTVALIDATION","/wEWBALBkJDVAQLs0bLrBgLs0fbZDAKM54rGBnjpZ01SieVIZvHrx+LlFQ1pTCE9lOQ/D0f9m//kwtCG")
                            .add("TextBox1",USERID)
                            .add("TextBox2",PASSWORD)
                            .add("Button1","登      录")
                            .build();
                    Request library_canceltomorrow_request = new Request.Builder()
                            .url("http://172.16.47.84/Login.aspx")
                            .addHeader("Cookie",cookie)
                            .post(formBody2)
                            .build();
                    library_canceltomorrow_okhttpclient.newCall(library_canceltomorrow_request).enqueue(new okhttp3.Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                           // TLog.error(response.body().string());
                            /*
                            Document document= Jsoup.parse(response.body().string());
                            Elements elements=document.select("input[name=__VIEWSTATE]");
                            Element element=elements.get(0);
                            String viewstate = element.attr("value");
                            TLog.error(viewstate);
                            */
                            //Log.e(TAG, "onResponse: valuestate"+viewstate );
                            Message message=handler.obtainMessage();
                            message.what=4;//其实没什么传递的
                            message.obj = "";
                            handler.sendMessage(message);
                        }
                    });
                   break;
            }
        }
    };

    @Override
    public void enduse(String userid) {
        APIWrapper.getInstance().EndUse(userid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                /*.subscribe(new Subscriber<T5>() {
                    @Override
                    public void onCompleted() {
                        mView.EndUseResult(change1);
                    }

                    @Override
                    public void onError(Throwable e) {
                        TLog.error(e.getMessage());
                        mView.EndUseResult("与服务器建立联系失败！");
                    }

                    @Override
                    public void onNext(T5 t5) {
                        change1 = t5.getChange1();
                    }
                });*/
                .subscribe(new Observer<T5>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(T5 t5) {
                        change1 = t5.getChange1();
                    }

                    @Override
                    public void onError(Throwable e) {
                        TLog.error(e.getMessage());
                        mView.EndUseResult("与服务器建立联系失败！");
                    }

                    @Override
                    public void onComplete() {
                        mView.EndUseResult(change1);
                    }
                });
    }

    @Override
    public void changestatus(String userid) {
        APIWrapper.getInstance().ChangeStatus(userid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                /*.subscribe(new Subscriber<T5>() {
                    @Override
                    public void onCompleted() {
                        mView.ChangeStatus(change1);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.EndUseResult("与服务器建立联系失败！");
                    }

                    @Override
                    public void onNext(T5 t5) {
                        change1 = t5.getChange1();
                    }
                });*/
                .subscribe(new Observer<T5>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(T5 t5) {
                        change1 = t5.getChange1();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.EndUseResult("与服务器建立联系失败！");
                    }

                    @Override
                    public void onComplete() {
                        mView.ChangeStatus(change1);
                    }
                });
    }

    @Override
    public void Seemystate(String userid) {
        APIWrapper.getInstance().SeeMystate(userid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
               /* .subscribe(new Subscriber<MyState>() {
                    @Override
                    public void onCompleted() {
                        mView.SeeMyState(myState1);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.EndUseResult("与服务器建立联系失败！");
                    }

                    @Override
                    public void onNext(MyState myState) {
                        myState1 = myState;
                    }
                });*/
               .subscribe(new Observer<MyState>() {
                   @Override
                   public void onSubscribe(Disposable d) {

                   }

                   @Override
                   public void onNext(MyState myState) {
                       myState1 = myState;
                   }

                   @Override
                   public void onError(Throwable e) {
                       mView.EndUseResult("与服务器建立联系失败！");
                   }

                   @Override
                   public void onComplete() {
                       mView.SeeMyState(myState1);

                   }
               });
    }

    @Override
    public void UploadTouxiang(String filepath,String userid) {
        TLog.error("filepath == " + filepath);
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        File file = new File(filepath);
        RequestBody r = RequestBody.create(MediaType.parse("image/png"), file);
        builder.addFormDataPart(StaticClass.UploadTouxiang,file.getName(),r);
        MultipartBody.Part p = builder.build().part(0);
        APIWrapper.getInstance().uploadTouxiangImgs(p,userid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                /*.subscribe(new Subscriber<Status>() {
                    @Override
                    public void onCompleted() {
                        TLog.error("成功");
                        mView.Touixiangsuccess(uploadtouxiangmessage);
                    }

                    @Override
                    public void onError(Throwable e) {
                        TLog.error(e.getMessage());
                        mView.Touxiangfail(uploadtouxiangmessage);
                    }

                    @Override
                    public void onNext(Status status) {
                        TLog.error("上传结果：：：" + status.getStatus());
                        uploadtouxiangmessage = status.getStatus();
                    }
                });*/
                .subscribe(new Observer<Status>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Status status) {
                        TLog.error("上传结果：：：" + status.getStatus());
                        uploadtouxiangmessage = status.getStatus();
                    }

                    @Override
                    public void onError(Throwable e) {
                        TLog.error(e.getMessage());
                        mView.Touxiangfail(uploadtouxiangmessage);
                    }

                    @Override
                    public void onComplete() {
                        TLog.error("成功");
                        mView.Touixiangsuccess(uploadtouxiangmessage);
                    }
                });
    }

    @Override
    public void UploadTouxiangQiuYun(String filepath, String userid) {
        //TODO：与自己服务器先建立联系，获取token,在把文件上串到七牛云上，上传成功以后，解析图片的url,搭配userid学号在传给自己服务器，保存到数据库user表里面
        APIWrapper.getInstance().GETTOKEN()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Token>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Token s) {
                        TOKEN = s.getUptoken();
                        TLog.error("TOKEN == " + TOKEN);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        // 重用uploadManager。一般地，只需要创建一个uploadManager对象
                        UploadManager uploadManager = new UploadManager();
                        uploadManager.put(filepath, System.currentTimeMillis()+".png", TOKEN, new UpCompletionHandler() {
                            @Override
                            public void complete(String key, ResponseInfo info, JSONObject response) {
                                if(info.isOK()) {
                                    TLog.error("qiniu"+ "Upload Success");
                                    try {
                                        String touxaingurl = response.getString("key");
                                        APIWrapper.getInstance().uploadTouxiangImgs2(StaticClass.imgIP + touxaingurl,userid)
                                                .subscribeOn(Schedulers.io())
                                                .observeOn(AndroidSchedulers.mainThread())
                                                .subscribe(new Observer<Status>() {
                                                    @Override
                                                    public void onSubscribe(Disposable d) {

                                                    }

                                                    @Override
                                                    public void onNext(Status status) {
                                                        TLog.error("上传结果：：：" + status.getStatus());
                                                        uploadtouxiangmessage = status.getStatus();
                                                    }

                                                    @Override
                                                    public void onError(Throwable e) {
                                                        TLog.error(e.getMessage());
                                                        mView.Touxiangfail(uploadtouxiangmessage);
                                                    }

                                                    @Override
                                                    public void onComplete() {
                                                        TLog.error("成功");
                                                        mView.Touixiangsuccess(uploadtouxiangmessage);
                                                    }
                                                });

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    TLog.error("qiniu"+"Upload Fail");
                                    //如果失败，这里可以把info信息上报自己的服务器，便于后面分析上传错误原因
                                }
                                // TLog.error("qiniu", key + ",\r\n " + info + ",\r\n " + response.toString());
                            }
                        },null);
                    }
                });
    }

    @Override
    public void changestatus2(String userid) {
        //恢复座位
        APIWrapper.getInstance().JieshuZanli(userid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                /*.subscribe(new Subscriber<T5>() {
                    @Override
                    public void onCompleted() {
                        mView.JIESHUZANLISUCCESS(change1);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.FAILZANLI("与服务器建立联系失败！");
                    }

                    @Override
                    public void onNext(T5 t5) {
                        change1 = t5.getChange1();
                    }
                });*/
                .subscribe(new Observer<T5>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(T5 t5) {
                        change1 = t5.getChange1();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.FAILZANLI("与服务器建立联系失败！");
                    }

                    @Override
                    public void onComplete() {
                        mView.JIESHUZANLISUCCESS(change1);
                    }
                });
    }

    @Override
    public void CanCelTomorrow(String tomorrow, boolean b,String userid,String pwd) {
        ISDAY = tomorrow;
        ChckeMystate = b;
        USERID = userid;
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
                    }
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Headers headers = response.headers();
                        List<Cookie> c = Cookie.parseAll(request.url(), headers);
                        String loginhtmlresource = response.body().string();
                        Document d = Jsoup.parse(loginhtmlresource);
                        String loginviewstate = d.select("input[name=__VIEWSTATE]").get(0).attr("value");
                        String logineventvalidation = d.select("input[name=__EVENTVALIDATION]").get(0).attr("value");
                        Bundle bundle = new Bundle();
                        bundle.putString("loginviewstate",loginviewstate);
                        bundle.putString("logineventvalidation",logineventvalidation);
                        bundle.putString("cookie",c.get(0).toString());
                        Message message=handler.obtainMessage();
                        message.what=1;
                        message.setData(bundle);
                        handler.sendMessage(message);
                    }
                });

    }

    @Override
    public void CanCelToday(String today, boolean b,String userid,String pwd) {
        USERID = userid;
        ISDAY = today;
        ChckeMystate = b;
        PASSWORD = pwd;
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
                    }
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Headers headers = response.headers();
                        List<Cookie> c = Cookie.parseAll(request.url(), headers);

                        String loginhtmlresource = response.body().string();
                        Document d = Jsoup.parse(loginhtmlresource);
                        String loginviewstate = d.select("input[name=__VIEWSTATE]").get(0).attr("value");
                        String logineventvalidation = d.select("input[name=__EVENTVALIDATION]").get(0).attr("value");
                        Bundle bundle = new Bundle();
                        bundle.putString("loginviewstate",loginviewstate);
                        bundle.putString("logineventvalidation",logineventvalidation);
                        bundle.putString("cookie",c.get(0).toString());
                        Message message=handler.obtainMessage();
                        message.what=1;
                        message.setData(bundle);
                        handler.sendMessage(message);
                    }
                });
    }

    @Override
    public void Seemystate2(String userid,String pwd) {
        ChckeMystate = true;
        USERID = userid;
        PASSWORD = pwd;
        TLog.error("USERID = " + userid + "PASSWORD = "+ pwd);
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
                    }
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Headers headers = response.headers();
                        List<Cookie> c = Cookie.parseAll(request.url(), headers);

                        String loginhtmlresource = response.body().string();
                        Document d = Jsoup.parse(loginhtmlresource);
                        String loginviewstate = d.select("input[name=__VIEWSTATE]").get(0).attr("value");
                        String logineventvalidation = d.select("input[name=__EVENTVALIDATION]").get(0).attr("value");
                        Bundle bundle = new Bundle();
                        bundle.putString("loginviewstate",loginviewstate);
                        bundle.putString("logineventvalidation",logineventvalidation);
                        bundle.putString("cookie",c.get(0).toString());
                        Message message=handler.obtainMessage();
                        message.what=1;
                        message.setData(bundle);
                        handler.sendMessage(message);
                    }
                });
    }

}
