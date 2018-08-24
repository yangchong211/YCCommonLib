package com.ns.yc.ycprogress;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.ns.yc.ycprogresslib.RingProgressBar;

import junit.framework.Assert;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * <pre>
 *     @author 杨充
 *     blog  :
 *     time  : 2016/2/10
 *     desc  : 自定义进度条，新芽，沙丘大学下载进度条
 *     revise: 参考案例：夏安明博客http://blog.csdn.net/xiaanming/article/details/10298163
 *             案例地址：https://github.com/yangchong211
 * </pre>
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        findViewById(R.id.btn_1).setOnClickListener(this);
        findViewById(R.id.btn_2).setOnClickListener(this);
        findViewById(R.id.btn_3).setOnClickListener(this);
        findViewById(R.id.btn_4).setOnClickListener(this);


        //获取一个JAVA对象的大小，可以将一个对象进行序列化为二进制的Byte，便可以查看大小
        Integer value = 10;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos ;
        try {
            oos = new ObjectOutputStream(bos);
            oos.writeObject(value);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 读出当前对象的二进制流信息
        Log.e("打印日志----",bos.size()+"");
        //打印日志----: 81
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_1:
                startActivity(new Intent(this,FirstActivity.class));
                break;
            case R.id.btn_2:
                startActivity(new Intent(this,SecondActivity.class));
                break;
            case R.id.btn_3:
                startThread();
                break;
            case R.id.btn_4:
                startThread1();
                break;
            default:
                break;
        }
    }

    int count = 0;
    private void startThread() {
        for (int i = 0;i < 200; i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int k = 0; k < 50; k++){
                        count++;
                    }
                }
            }).start();
        }
        // 休眠10秒，以确保线程都已启动
        try {
            Thread.sleep(1000*10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            Log.e("打印日志----",count+"");
        }
    }


    private static AtomicInteger atomicInteger = new AtomicInteger(1);
    static Integer count1 = Integer.valueOf(0);
    private void startThread1() {
        for (int i = 0;i < 200; i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int k = 0; k < 50; k++){
                        // getAndIncrement: 先获得值，再自增1，返回值为自增前的值
                        count1 = atomicInteger.getAndIncrement();
                    }
                }
            }).start();
        }
        // 休眠10秒，以确保线程都已启动
        try {
            Thread.sleep(1000*10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            Log.e("打印日志----",count1+"");
        }
    }


}
