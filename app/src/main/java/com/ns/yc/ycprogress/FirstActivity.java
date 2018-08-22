package com.ns.yc.ycprogress;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.ns.yc.ycprogresslib.RingProgressBar;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 博客地址
 * https://mp.weixin.qq.com/s?__biz=MzAxMTI4MTkwNQ==&mid=2650821214&idx=1&sn=d0c120c9dc21669c7acac9c250991e3c&chksm=80b786c0b7c00fd62c7be336df91885614e0184b153350602bcd1ff39618d6d80cc7a6999e20&mpshare=1&scene=23&srcid=0630qVgVu8maUKvxvz5GPHCk#rd
 */
public class FirstActivity extends AppCompatActivity {


    private RingProgressBar bar_percent;
    private RingProgressBar bar_null;
    private RingProgressBar bar_all;

    private boolean isProgressGoing;
    private int max;
    private int progress;

    private Timer mTimer;
    private TimerTask mTimerTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        init();
    }

    private void init() {
        initData();
        initView();
    }


    private void initData() {
        max = 100;
        readyProgress();
    }


    private void initView() {
        bar_percent = (RingProgressBar) findViewById(R.id.bar_percent);
        bar_null = (RingProgressBar) findViewById(R.id.bar_null);
        bar_all = (RingProgressBar) findViewById(R.id.bar_all);

        bar_percent.setProgress(65);
        bar_null.setProgress(89);

        bar_all.setProgressMax(max);
        bar_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(FirstActivity.this, "进度条被点击", Toast.LENGTH_SHORT).show();
            }
        });
        bar_all.setOnButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isProgressGoing) {
                    if (progress == max) {
                        progress = 0;
                        bar_all.setProgress(progress);
                    }
                    startProgress();
                } else {
                    stopProgress();
                }

            }
        });

    }


    private void readyProgress() {
        if (mTimer == null) {
            mTimer = new Timer();
        }
        if (mTimerTask == null) {
            mTimerTask = new TimerTask() {
                @Override
                public void run() {
                    if (!isProgressGoing) {
                        return;
                    }
                    if (++progress >= max) {
                        progress = max;
                        bar_all.setProgress(progress);
                        stopProgress();
                        return;
                    }
                    bar_all.setProgress(progress);
                }
            };
        }
    }

    private void startProgress() {
        isProgressGoing = true;

        stopTimerTask();
        readyProgress();
        mTimer.schedule(mTimerTask, 1000, 100);
    }

    private void stopTimerTask() {
        if (mTimerTask != null) {
            mTimerTask.cancel();
        }
        if (mTimer != null) {
            mTimer.cancel();
        }
        mTimerTask = null;
        mTimer = null;
    }

    private void stopProgress() {
        isProgressGoing = false;
        stopTimerTask();
    }


}
