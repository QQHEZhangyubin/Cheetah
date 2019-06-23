package com.example.desk.util;

import android.os.Environment;

import com.example.desk.MyApplication;

/**
 * Created by 13608 on 2018/5/16.
 */

public class StaticClass {

   public static final String librarycookie= "tiyucookie";
   public static final String userid = "userid";
   public static final String userlogo = "userlogo";
   public static final String password = "password";
   public static final int HANDLER_SPLASH = 1001;
   public static final String UploadTouxiang = "touxiangfile";
   public static final String FabiaoShuoShuo = "file2";
   public static final String Keeppass = "keeppass";
   public static final String imgIP = "http://pswn9tut0.bkt.clouddn.com/";//存在七牛云图片url的域名部分
   public static final String videoIP = "http://pt536zu37.bkt.clouddn.com/";//存在七牛云视频和视频第一帧的域名部分
   public final static String APP_ROOT_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" +  MyApplication.getInstance().getPackageName();
   public final static String DOWNLOAD_DIR = "/downlaod/";
   public static final int DOWNLOADAPK_ID = 10;

   public static final int NOTE_LOAD = 0X100;

   public static final int ALREADEY_LOAD = 0X150;

   public static final int OP_ADD = 0x200;

   public static final int OP_DELETE = 0x210;

   public static final int LOAD_OP = 0x300;

   public static final int LOAD_MORE = 0x310;

   public static final int LOAD_REFRESH = 0x320;

   public static final int FAVORT_OP = 0x500;


   public static final int POST_OP = 0x600;

   public static final int COMMENT_OP = 0x700;


}
