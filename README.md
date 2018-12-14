# YCProgress progress bar 


#### Directory description
- 0.How to use 
- 1.This library advantage bright spot
- 2.Use introduction
    - 2.1ring percentage progress bar
    - 2.2straight line percentage progress bar
    - 2.3imitation antivirus type percentage progress bar
- 3.Pay attention to key points
- 4.Effect display
- 5.Other presentations


### 0.How to use 
#### 0.1 Used
- Add this in your root build.gradle file (not your module build.gradle file): 
    ```
    allprojects {
        repositories {
            maven { url "https://jitpack.io" }
        }
    }
    ```
- Then, add the library to your module build.gradle 
    ```
    compile 'cn.yc:YCProgressLib:1.2.6'
    ```

#### 0.2 function declaration
- Custom progress bar, including circular percentage progress bar, linear percentage progress bar, and imitation 360 anti-virus percentage progress bar. Free to set progress bar type, outer contour color, center circle color, custom percentage unit property, progress bar color, and so on. With progress monitoring, you can set the percentage. Scenarios used are: start page countdown, download progress bar display, anti-virus progress bar display.


#### 0.3 About language
- [Chinese中文文档](https://github.com/yangchong211/YCProgress/blob/master/README_CH.md)
- [English英文文档](https://github.com/yangchong211/YCProgress/blob/master/README.md)


#### 04 Case demonstration animation
- ![image](https://github.com/yangchong211/YCProgress/blob/master/image/progress.gif)


### 1.Advantages and bright spots of this library
- Ring percentage progress bar
    - simple and compact, supports setting multiple properties. You can set the color of the inner circle and the outer circle, and the edge width of the ring.
    - support for setting total countdown time, call start to start countdown, call stop to pause countdown, or customize setting progress
- Percentage progress bar for imitation antivirus type
    - Support for setting multiple types, such as setting the percentage unit type or setting the null type [that is, not displaying the intermediate percentage]
    - Support to set progress bar color, unupdated progress bar color; set percentage text size, color; support setting unit and other properties
    - Support for multi-thread access and add synchronized keyword embellishment for setting setProgress,. Sets the progress progress, to throw an exception if it is less than 0 or more than 100. Avoid other problems caused by developer use.
- Straight line percentage progress ba
    - Supports setting the text size of the percentage progress bar, the font color, and the color of the percentage progress bar update and the percentage progress bar not updated
    - Support for setting the height of the progress bar, setting the maximum progress bar, setting the progress bar progress, and setting whether the percentage text is visible
    - You can set the total countdown time, you can set the start, pause, restart, and so on. Support percentage progress bar progress monitoring
- Other common properties of progress ba
    - For the progress bar, for methods that set the color, add the annotation @ ColorInt, to restrict developers from invoking color resources
    - Use annotations instead of enumerations, and use annotations to limit the types passed in when called by developers for methods of setting enumerations. Concrete visible code cases!
    - The comments are very detailed, and as an open source lib library, I think itundefineds important for the consumer to be clear at a glance. It is convenient to call at the same time, knowing the function of each method.
    - Code is small, if you want to learn and deep custom controls, you can start simple. This project fits well!


### 2.Introduction to use
#### 2.1 Ring percentage progress ba
- In the layout
    ```
    //You can also set the attr property in the layout
    <com.ns.yc.ycprogresslib.CircleProgressbar
        android:id="@+id/pb_1"
        android:layout_width="100dp"
        android:layout_height="100dp"
        android:text="进度条" />
    
    ```
- usage method
    ```
    //设置类型
    pb_1.setProgressType(ProgressBarUtils.ProgressType.COUNT);
    //设置圆形的填充颜色
    pb_1.setInCircleColor(getResources().getColor(R.color.redTab));
    //设置外部轮廓的颜色
    pb_1.setOutLineColor(getResources().getColor(R.color.grayLine));
    //设置进度监听
    pb_1.setCountdownProgressListener(1, progressListener);
    //设置外部轮廓的颜色
    pb_1.setOutLineWidth(2);
    //设置进度条线的宽度
    pb_1.setProgressLineWidth(5);
    //设置进度
    pb_1.setProgress(60);
    //设置倒计时总时间
    pb_1.setTimeMillis(3000);
    //设置进度条颜色
    pb_1.setProgressColor(getResources().getColor(R.color.colorPrimary));
    
    //开始
    pb_1.start();
    //暂停
    pb_1.stop();
    //重新开始
    pb_1.reStart();
    ```



#### 2.2 Straight line percentage progress ba
- In the layout
    ```
    <com.ns.yc.ycprogresslib.NumberProgressbar
        android:id="@+id/bar1"
        android:layout_width="match_parent"
        android:layout_height="wrap_content" />
    
    <com.ns.yc.ycprogresslib.NumberProgressbar
        android:id="@+id/bar2"
        android:layout_marginTop="10dp"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        app:progress_max="100"
        app:progress_reached_bar_height="3dp"
        app:progress_unreached_bar_height="3dp"
        app:progress_reached_color="@color/colorPrimary"
        app:progress_unreached_color="@color/gray3"
        app:progress_text_size="14sp"
        app:progress_text_color="@color/colorAccent"
        app:progress_text_visibility="visible"/>
    ```
- usage method
    ```
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
    
    //开始
    bar1.start();
    //暂停
    bar1.stop();
    ```


#### 2.3 Percentage progress bar for imitation antivirus type
- In the layout
    ```
    <com.ns.yc.ycprogresslib.RingProgressBar
        android:id="@+id/bar_percent"
        android:layout_width="100dp"
        android:layout_height="100dp"/>
    ```
- usage method
    ```
    bar_percent = (RingProgressBar) findViewById(R.id.bar_percent);
    //设置进度
    bar_percent.setProgress(0);
    //设置更新进度条颜色
    bar_percent.setDotColor(this.getResources().getColor(R.color.colorAccent));
    //设置未更新部分的进度条颜色
    bar_percent.setDotBgColor(this.getResources().getColor(R.color.blackText));
    //设置百分比文字颜色
    bar_percent.setPercentTextColor(this.getResources().getColor(R.color.blackText1));
    //设置百分比文字大小
    bar_percent.setPercentTextSize(ProgressBarUtils.dp2px(this,16.0f));
    //设置展示的类型
    bar_percent.setShowMode(ProgressBarUtils.RingShowMode.SHOW_MODE_PERCENT);
    //设置单位的文字内容
    bar_percent.setUnitText("%");
    //设置单位的文字大小
    bar_percent.setUnitTextSize(ProgressBarUtils.dp2px(this,16.0f));
    //设置单位的文字颜色
    bar_percent.setUnitTextColor(this.getResources().getColor(R.color.blackText1));
    ```
- Multiple types can be set
    - First: percentage unit [supports setting your own unit, such as setting%, or setting milliseconds, etc.]
    - second: null display mode [that is, does not display the middle part]




### 3.Pay attention to key points
- 3.1 Whether it is a circular progress bar or a straight progress bar, when calling setProgress to set the progress, the function of verifying progress is added. Because if the value is set above 100 or less than 0, the method works!
    ```
    /**
     * 验证进度。
     *
     * @param progress      你要验证的进度值。
     * @return              返回真正的进度值。
     */
    private int validateProgress(int progress) {
        if (progress > 100){
            progress = 100;
        } else if (progress < 0){
            progress = 0;
        }
        return progress;
    }
    ```
- 3.2 For CircleProgressbar and NumberProgressbar custom controls, if you call the start method to start iterating through the setProgress, program, notice:
    ```
    /**
     * 当自定义控件销毁时，则调用该方法
     */
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stop();
    }
    ```



### 4.Effect display
![image](https://github.com/yangchong211/YCProgress/blob/master/image/1.jpg)
![image](https://github.com/yangchong211/YCProgress/blob/master/image/2.jpg)
![image](https://github.com/yangchong211/YCProgress/blob/master/image/3.jpg)
![image](https://github.com/yangchong211/YCProgress/blob/master/image/4.jpg)
![image](https://github.com/yangchong211/YCProgress/blob/master/image/5.jpg)
![image](https://github.com/yangchong211/YCProgress/blob/master/image/6.jpg)


### 5.Other presentations
#### Introduction to other elements
![image](https://upload-images.jianshu.io/upload_images/4432347-7100c8e5a455c3ee.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


#### Version update instructions
- v1.0.0 update was used in 2016-2-10 for the investment community to download the update progress bar. Learn the custom control
- v1.1.1 update on 2016-8-12 for the ring progress bar, Add a custom attr attribute
- v1.1.2 update on 2017-3-10 to add a countdown total time for the loop progress bar, Start and stop methods
- v1.1.3 update on 2017-5-27 for setting progress methods, adding checksum, Cannot be less than 0 or greater than 100
- v1.2.5 update on Aug. 24, 2018 with a straight line percentage progress bar, Add annotations to some methods
- v1.2.6 update added a detailed comment on November 30, 2018
- v1.2.7 update updated on 2018-12-3 targetSdkVersion version 27
- about The straight-line percentage progress bar refers to the coders NumberProgressBar project: https://github.com/daimajia/NumberProgressBar


####  About blog Summary links
- 1.[Technology blog summary](https://www.jianshu.com/p/614cb839182c)
- 2.[Open source project summary](https://blog.csdn.net/m0_37700275/article/details/80863574)
- 3.[Life blog summary](https://blog.csdn.net/m0_37700275/article/details/79832978)
     


#### About recommend
- Great summary of blog notes, including Java basics and in-depth knowledge, Android technology blog, Python learning notes, and so on, but also includes the usual development of bug summary, of course, also collected a large number of interview questions after work. Long-term update, maintenance and revision, continuous improvement. Open source files are in markdown format!
- chained address ： https://github.com/yangchong211/YCBlogs
- If you feel good, you can star, thank you! Of course, you are also welcome to put forward suggestions, everything starts at a slight, quantitative change causes qualitative change!



#### About LICENSE
```
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

#### Thanks
- Refer to the coders progress bar case ：https://github.com/daimajia/NumberProgressBar









