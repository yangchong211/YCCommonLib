package com.yc.common;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;

import androidx.annotation.Nullable;

import com.yc.activitymanager.AbsLifecycleListener;
import com.yc.activitymanager.ActivityManager;
import com.yc.appcompress.AppCompress;
import com.yc.appcontextlib.AppToolUtils;
import com.yc.appencryptlib.Md5EncryptUtils;
import com.yc.appfilelib.AppFileUtils;
import com.yc.apphandlerlib.SoftHandlerHolder;
import com.yc.applrucache.SystemLruCache;
import com.yc.applrudisk.DiskLruCacheHelper;
import com.yc.appmediastore.AppFileUriUtils;
import com.yc.apppermission.PermissionHelper;
import com.yc.apprestartlib.RestartManager;
import com.yc.appscreenlib.AppShotsUtils;
import com.yc.appstatuslib.AppStatusManager;
import com.yc.appstatuslib.info.AppBatteryInfo;
import com.yc.appwifilib.WifiHelper;
import com.yc.eventuploadlib.EventReporter;
import com.yc.fileiohelper.FileIoUtils;
import com.yc.fragmentmanager.FragmentManager;
import com.yc.imagetoollib.AppBitmapUtils;
import com.yc.intent.utils.IntentLogUtils;
import com.yc.networklib.AppNetworkUtils;
import com.yc.parallel.ParallelTaskDispatcher;
import com.yc.reflectionlib.ReflectUtils;
import com.yc.toolmemorylib.AppMemoryUtils;
import com.yc.toolutils.AppActivityUtils;
import com.yc.toolutils.AppInfoUtils;
import com.yc.toolutils.AppLogUtils;
import com.yc.zipfilelib.AppZipUtils;


import java.io.File;
import java.io.IOException;
import java.util.List;

public class Test {

    private void test1(Activity activity){
        //添加 activity
        ActivityManager.getInstance().add(activity);
        //移除 activity
        ActivityManager.getInstance().remove(activity);
        //结束指定的Activity
        ActivityManager.getInstance().finish(activity);
        //结束所有Activity
        ActivityManager.getInstance().finishAll();
        //退出应用程序。先回退到桌面，然后在杀死进程
        ActivityManager.getInstance().appExist();
        //这个是监听目标Activity的生命周期变化
        ActivityManager.getInstance().registerActivityLifecycleListener(
                CommonActivity.class,new AbsLifecycleListener(){
                    @Override
                    public void onActivityCreated(@Nullable Activity activity, Bundle savedInstanceState) {
                        super.onActivityCreated(activity, savedInstanceState);
                    }
                });
        //移除栈顶的activity
        ActivityManager.getInstance().pop();
        //获取栈顶的Activity
        Activity activity1 = ActivityManager.getInstance().peek();
        //判断activity是否处于栈顶
        ActivityManager.getInstance().isActivityTop(activity,"MainActivity");
        //返回AndroidManifest.xml中注册的所有Activity的class
        ActivityManager.getInstance().getActivitiesClass(
                activity, AppInfoUtils.getAppPackageName(),null);
    }

    private void test2(){
        AppCompress.getInstance().compressSize("");
        AppCompress.getInstance().compressSizePath("");
        AppCompress.getInstance().compressQuality("");
    }

    private void test3(){
        AppToolUtils.checkNotNull("");
        AppToolUtils.getApp();
    }

    private void test4(Context context){
        String string = "yangchong";
        //对字符串md5加密
        String md1 = Md5EncryptUtils.getMD5(string);
        AppLogUtils.d("md5计算字符串1: " + md1);
        //对字符串md5加密
        String md2 = Md5EncryptUtils.encryptMD5ToString(string);
        AppLogUtils.d("md5计算字符串2: " + md2);
        //对字符串md5加密，加盐处理
        String md3 = Md5EncryptUtils.encryptMD5ToString(string,"doubi");
        AppLogUtils.d("md5计算字符串，加盐处理3: " + md3);
        byte[] bytes = string.getBytes();
        //对字节数据md5加密
        String md4 = Md5EncryptUtils.encryptMD5ToString(bytes);
        AppLogUtils.d("md5计算字节数组4: " + md4);
        //对字节数据md5加密，加盐处理
        String md5 = Md5EncryptUtils.encryptMD5ToString(bytes,"doubi".getBytes());
        AppLogUtils.d("md5计算字节数组，加盐处理5: " + md5);

        String txt = AppFileUtils.getExternalFilePath(context, "txt");
        //对文件进行md5加密
        String md6 = Md5EncryptUtils.encryptMD5File1(txt);
        AppLogUtils.d("md5计算文件路径6: " + md6);
        //对文件进行md5加密
        String md7 = Md5EncryptUtils.encryptMD5File2(new File(txt));
        AppLogUtils.d("md5计算文件File7: " + md7);

        String fileName = txt + File.separator + "yc1.txt";
        String md8 = Md5EncryptUtils.encryptMD5File1(fileName);
        AppLogUtils.d("md5计算文件txt路径8: " + md8);
        String md9 = Md5EncryptUtils.encryptMD5File2(new File(fileName));
        AppLogUtils.d("md5计算文件txt路径9: " + md9);
    }

    private void test5(){
        SoftHandlerHolder softHandlerHolder = new SoftHandlerHolder(new SoftHandlerHolder.OnReceiveMessageListener() {
            @Override
            public void handlerMessage(Message msg) {

            }
        });
    }

    private void test6(){
        SystemLruCache<String, String> lruCache = new SystemLruCache<String, String>(1000);
        DiskLruCacheHelper diskLruCacheHelper = new DiskLruCacheHelper();
        diskLruCacheHelper.put("1","1");
    }

    private void test7(Context context){
        Uri uri = null;
        File file = AppFileUriUtils.uri2File(context, uri);
    }

    private void test8(Context context){
        PermissionHelper.getInstance().hasPermissions(context,null);
    }

    private void test9(Context context){
        RestartManager.getInstance().restartApp(context,RestartManager.LAUNCHER);
    }

    private void test10(Activity activity){
        AppShotsUtils.activityShot(activity);
    }

    private void test11(){
        AppStatusManager.Builder builder = new AppStatusManager.Builder();
        AppStatusManager statusManager = builder.builder();
        AppBatteryInfo batteryInfo = statusManager.getBatteryInfo();
    }

    private void test12(){
        WifiHelper.getInstance().closeWifi();
    }


    private void test13(){
        EventReporter.report("1");
    }

    private void test14(){
        FileIoUtils.copyFile1("","");
    }

    private void test15(){
        FragmentManager instance = FragmentManager.Companion.getInstance();
    }

    private void test16(){
        byte[] bytes = AppBitmapUtils.bitmap2Bytes(null);
    }

    private void test17(){
        AppNetworkUtils.openWirelessSettings();
    }


    private void test18(){
        ParallelTaskDispatcher parallelTaskDispatcher = ParallelTaskDispatcher.create();
    }

    private void test19(){
        ReflectUtils.getInstance(Test.class);
    }


    private void test20(){
        String dumpBundle = IntentLogUtils.dumpBundle(null);
    }

    private void test21(){
        AppFileUtils.deleteFile("");
    }

    private void test22(){
        int currentPid = AppMemoryUtils.getCurrentPid();
    }

    private void test23(){
        String launcherActivity = AppActivityUtils.getLauncherActivity();
        try {
            List<String> comments = AppZipUtils.getComments("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
