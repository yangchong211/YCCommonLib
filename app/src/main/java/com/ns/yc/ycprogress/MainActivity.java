package com.ns.yc.ycprogress;

import android.content.Intent;
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
            default:
                break;
        }
    }
}
