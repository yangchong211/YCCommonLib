package com.ns.yc.ycprogress;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.ns.yc.ycprogresslib.CircleProgressbar;


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
public class SecondActivity extends AppCompatActivity {

    private CircleProgressbar pb_1;
    private CircleProgressbar pb_2;
    private Button btn_0;
    private Button btn_1;
    private Button btn_2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        init();
    }

    private void init() {
        btn_0 = (Button) findViewById(R.id.btn_0);
        btn_1 = (Button) findViewById(R.id.btn_1);
        btn_2 = (Button) findViewById(R.id.btn_2);
        pb_1 = (CircleProgressbar) findViewById(R.id.pb_1);
        pb_2 = (CircleProgressbar) findViewById(R.id.pb_2);

        //设置类型
        pb_1.setProgressType(CircleProgressbar.ProgressType.COUNT);
        pb_1.setInCircleColor(getResources().getColor(R.color.redTab));
        pb_1.setOutLineColor(getResources().getColor(R.color.grayLine));
        pb_1.setCountdownProgressListener(1, progressListener);
        pb_1.setOutLineWidth(2);
        pb_1.setProgressLineWidth(5);
        pb_1.setProgress(60);
        pb_1.setTimeMillis(3000);


        //设置类型
        pb_2.setProgressType(CircleProgressbar.ProgressType.COUNT);
        pb_2.setOutLineColor(getResources().getColor(R.color.grayLine));
        pb_2.setCountdownProgressListener(2, progressListener);
        pb_2.setOutLineWidth(2);
        pb_2.setProgressLineWidth(5);
        pb_2.setProgress(30);
        pb_1.setTimeMillis(5000);


        btn_0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pb_1.reStart();
                pb_2.reStart();
            }
        });
        btn_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pb_1.stop();
                pb_2.stop();
            }
        });
        btn_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pb_1.start();
                pb_2.start();
            }
        });
    }


    private CircleProgressbar.OnCountdownProgressListener progressListener = new CircleProgressbar.OnCountdownProgressListener() {
        @Override
        public void onProgress(int what, int progress) {
            if (what == 1) {
                pb_1.setText(progress + "%");
            } else if (what == 2) {
                pb_2.setText(progress + "%");
            }
            // 比如在首页，这里可以判断进度，进度到了100或者0的时候，你可以做跳过操作。
        }
    };

}
