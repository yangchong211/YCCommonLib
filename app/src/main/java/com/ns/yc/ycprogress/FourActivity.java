package com.ns.yc.ycprogress;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;

import com.ns.yc.ycprogresslib.CarlProgressbar;

public class FourActivity extends AppCompatActivity {

    private CarlProgressbar mPb1;
    private CarlProgressbar mPb2;
    private CarlProgressbar mPb3;
    private CarlProgressbar mPb4;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_four);



        mPb1 = findViewById(R.id.pb_1);
        mPb2 = findViewById(R.id.pb_2);
        mPb3 = findViewById(R.id.pb_3);
        mPb4 = findViewById(R.id.pb_4);

        mPb1.setProgress(50);
        mPb2.setProgress(0);



        mPb3.setMax(400);
        View testView = LayoutInflater.from(this).inflate(R.layout.layout_view, null);
        mPb3.setCustomView(testView);
        mPb3.setReachedBarHeight(10);
        mPb3.setUnreachedBarHeight(10);
        mPb3.setProgress(300);


        View testView4 = LayoutInflater.from(this).inflate(R.layout.layout_carl, null);
        mPb4.setCustomView(testView4);
        mPb4.setReachedBarHeight(10);
        mPb4.setUnreachedBarHeight(10);
        mPb4.setMax(100);
        mPb4.setProgress(57);
    }

}
