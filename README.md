# YCProgress
进度条
## 目录介绍
- **1.使用方法**
- **2.关于进度条介绍**
- **3.版本更新说明**
- **4.关于效果图展示**

### 1.使用方法
- 1.1 集成库：compile 'cn.yc:YCProgressLib:1.2.5'
- 1.2 在布局中
```
<com.ns.yc.ycprogresslib.CircleProgressbar
    android:id="@+id/pb_1"
    android:layout_width="100dp"
    android:layout_height="100dp"
    android:text="进度条" />

```
- 1.3 使用方法
```
//设置类型
pb.setProgressType(CircleProgressbar.ProgressType.COUNT);
//设置圆形的填充颜色
pb.setInCircleColor(getResources().getColor(R.color.redTab));
//设置外部轮廓的颜色
pb.setOutLineColor(getResources().getColor(R.color.grayLine));
//设置进度监听
pb.setCountdownProgressListener(1, progressListener);
//设置外部轮廓的颜色
pb.setOutLineWidth(2);
//设置进度条线的宽度
pb.setProgressLineWidth(5);
//设置进度
pb.setProgress(60);
//设置倒计时总时间
pb.setTimeMillis(3000);
```


### 3.版本更新说明
- v1.0  更新于2016/2/10
- v1.1  更新于2016/8/12
- v1.2.5 更新于2018年8月24日

### 4.关于效果图展示
- ![image]()
- ![image]()