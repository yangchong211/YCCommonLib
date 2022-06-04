package com.yc.common;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.yc.apploglib.AppLogHelper;
import com.yc.apploglib.config.AppLogFactory;
import com.yc.apploglib.printer.AbsPrinter;

public class CommonActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_main);
        init();
    }

    private void init() {
        findViewById(R.id.btn_log1).setOnClickListener(this);
        findViewById(R.id.btn_log2).setOnClickListener(this);
        findViewById(R.id.btn_2).setOnClickListener(this);
        findViewById(R.id.btn_3).setOnClickListener(this);
        findViewById(R.id.btn_4).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_log1:
                testLog1();
                break;
            case R.id.btn_log2:
                testLog2();
                break;
            case R.id.btn_2:

                break;
            case R.id.btn_3:

                break;
            case R.id.btn_4:

                break;
            default:
                break;
        }
    }

    private void testLog1() {
        AppLogHelper.v("MainActivity: ","verbose log");
        AppLogHelper.v("MainActivity log verbose no tag");
        AppLogHelper.d("MainActivity: ","debug log");
        AppLogHelper.d("MainActivity log info no tag");
        AppLogHelper.i("MainActivity: ","info log");
        AppLogHelper.i("MainActivity log info no tag");
        AppLogHelper.w("MainActivity: ","warn log");
        AppLogHelper.w("MainActivity log warn no tag");
        AppLogHelper.e("MainActivity: ","error log");
        AppLogHelper.e("MainActivity log error no tag");
    }


    private void testLog2() {
        //当然，如果不满足你的要求，开发者可以自己实现日志输出的形式。
        AppLogFactory.addPrinter(new AbsPrinter() {
            @NonNull
            @Override
            public String name() {
                return "yc";
            }

            @Override
            public void println(int level, String tag, String message, Throwable tr) {
                //todo 这块全局回调日志，你可以任意实现自定义操作
            }
        });
        AppLogHelper.v("MainActivity: ","verbose log");
        AppLogHelper.v("MainActivity log verbose no tag");
        AppLogHelper.d("MainActivity: ","debug log");
        AppLogHelper.d("MainActivity log info no tag");
        AppLogHelper.i("MainActivity: ","info log");
        AppLogHelper.i("MainActivity log info no tag");
        AppLogHelper.w("MainActivity: ","info log");
        AppLogHelper.w("MainActivity log info no tag");
    }

}
