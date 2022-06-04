package com.yc.common;

import android.app.Application;
import android.util.Log;

import com.yc.apploglib.config.AppLogConfig;
import com.yc.apploglib.config.AppLogFactory;
import com.yc.toolutils.file.AppFileUtils;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        String ycLogPath = AppFileUtils.getCacheFilePath(this, "ycLog");
        AppLogConfig config = new AppLogConfig.Builder()
                //设置日志tag总的标签
                .setLogTag("yc")
                //是否将log日志写到文件
                .isWriteFile(true)
                //是否是debug
                .enableDbgLog(true)
                //设置日志最小级别
                .minLogLevel(Log.VERBOSE)
                //设置输出日志到file文件的路径。前提是将log日志写入到文件设置成true
                .setFilePath(ycLogPath)
                .build();
        //配置
        AppLogFactory.init(config);
    }
}
