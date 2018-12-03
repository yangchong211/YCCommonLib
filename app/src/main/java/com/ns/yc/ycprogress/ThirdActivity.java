package com.ns.yc.ycprogress;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.ns.yc.ycprogresslib.NumberProgressbar;
import com.ns.yc.ycprogresslib.OnNumberProgressListener;
import com.ns.yc.ycprogresslib.ProgressBarUtils;

import java.util.Timer;

public class ThirdActivity extends AppCompatActivity implements View.OnClickListener {

    private NumberProgressbar bar1;
    private NumberProgressbar bar2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        bar1 = (NumberProgressbar) findViewById(R.id.bar1);
        //设置倒计时总时间
        bar1.setTimeMillis(10000);
        //设置最大进度条的值
        bar1.setMax(100);
        //设置进度条文本的颜色
        bar1.setProgressTextColor(this.getResources().getColor(R.color.colorAccent));
        //设置进度条文本的大小
        bar1.setProgressTextSize(ProgressBarUtils.sp2px(this,14));
        //设置百分比文字内容是否可见
        bar1.setNumberTextVisibility(ProgressBarUtils.NumberTextVisibility.Visible);
        //设置百分比进度条的高度
        bar1.setReachedBarHeight(10);
        //设置未更新百分比进度条的高度
        bar1.setUnreachedBarHeight(10);
        //设置百分比进度条的颜色
        bar1.setReachedBarColor(this.getResources().getColor(R.color.redTab));
        //设置未更新百分比进度条的颜色
        bar1.setUnreachedBarColor(this.getResources().getColor(R.color.blackText2));
        //设置百分比进度条的监听
        bar1.setOnProgressBarListener(new OnNumberProgressListener() {
            @Override
            public void onProgressChange(int current, int max) {

            }
        });


        bar2 = (NumberProgressbar) findViewById(R.id.bar2);
        bar2.setTimeMillis(15000);

        findViewById(R.id.btn_11).setOnClickListener(this);
        findViewById(R.id.btn_12).setOnClickListener(this);
        findViewById(R.id.btn_13).setOnClickListener(this);
        findViewById(R.id.btn_21).setOnClickListener(this);
        findViewById(R.id.btn_22).setOnClickListener(this);
        findViewById(R.id.btn_23).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_11:
                bar1.start();
                break;
            case R.id.btn_12:
                bar1.stop();
                break;
            case R.id.btn_13:
                bar1.reStart();
                break;
            case R.id.btn_21:
                bar2.start();
                break;
            case R.id.btn_22:
                bar2.stop();
                break;
            case R.id.btn_23:
                bar2.reStart();
                break;
        }
    }
}
