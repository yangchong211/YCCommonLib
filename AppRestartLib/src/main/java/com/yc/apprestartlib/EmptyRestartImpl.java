package com.yc.apprestartlib;

import android.content.Context;

import com.yc.toolutils.AppLogUtils;

/**
 * <pre>
 *     @author yangchong
 *     GitHub : https://github.com/yangchong211/YCCommonLib
 *     email : yangchong211@163.com
 *     time  : 2018/11/9
 *     desc  : 重启APP接口空实现
 *     revise:
 * </pre>
 */
public class EmptyRestartImpl implements IRestartApp {
    @Override
    public void restartApp(Context context) {
        AppLogUtils.w("IRestartApp:", "restart app empty");
    }
}
