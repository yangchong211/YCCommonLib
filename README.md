# 框架公共组件层
#### 目录介绍
- 01.框架公共组件层
- 02.组件化建设
- 03.公共组件库依赖
- 04.Activity栈管理库
- 05.通用存储库
- 06.Log日志打印库
- 07.App重新启动库
- 08.通用工具类库
- 09.通用基类封装库
- 10.反射工具类库
- 11.Intent封装库
- 12.基础接口库
- 13.Fragment周期监听
- 14.异常&事件&日志上报库
- 15.LruCache库
- 16.LruDisk磁盘库



### 01.框架公共组件层
- 组件化开发中基础公共库，activity栈管理；fragment周期监听；Lru缓存库；反射库；分区存储；Log日志打印和存储；通用缓存库(支持sp，mmkv，lru，disk，fastsp等多种存储方式切换)；App重启；通用全面的工具类Utils；通用基类fragment，adapter，activity等简单封装；intent内容打印到控制台库；通用基础接口



### 02.组件化建设
- 该基础组件库属于下面组件化架构图中：基础组件-工具库
- [组件化整体架构图](https://github.com/yangchong211/YCCommonLib/blob/master/ImageAll/%E7%BB%84%E4%BB%B6%E5%8C%96%E5%AE%9E%E8%B7%B5%E6%9E%B6%E6%9E%84%E5%9B%BE.png)



### 03.公共组件库依赖
- 关于依赖库如下所示，可以根据需求选择性使用：
    ``` java
    //base基类
    implementation 'com.github.yangchong211.YCCommonLib:BaseClassLib:1.3.5'
    //工具类utils
    implementation 'com.github.yangchong211.YCCommonLib:ToolUtilsLib:1.3.5'
    //activity栈管理
    implementation 'com.github.yangchong211.YCCommonLib:ActivityManager:1.3.5'
    //通用缓存存储库，支持sp，fastsp，mmkv，lruCache，DiskLruCache等
    implementation 'com.github.yangchong211.YCCommonLib:AppBaseStore:1.3.5'
    //通用日志输出库
    implementation 'com.github.yangchong211.YCCommonLib:AppLogLib:1.3.5'
    //app重启库
    implementation 'com.github.yangchong211.YCCommonLib:AppRestartLib:1.3.5'
    //intent内容输出到控制台
    implementation 'com.github.yangchong211.YCCommonLib:SafeIntentLib:1.3.5'
    //通用组件接口库
    implementation 'com.github.yangchong211.YCCommonLib:AppCommonInter:1.3.5'
    //各种广播监听哭
    implementation 'com.github.yangchong211.YCCommonLib:AppStatusLib:1.3.5'
    //基建库
    implementation 'com.github.yangchong211.YCCommonLib:ArchitectureLib:1.3.5'
    //同上上报库
    implementation 'com.github.yangchong211.YCCommonLib:EventUploadLib:1.3.5'
    //权限库
    implementation 'com.github.yangchong211.YCCommonLib:AppPermission:1.3.5'
    //Lru磁盘缓存库
    implementation 'com.github.yangchong211.YCCommonLib:AppLruDisk:1.3.5'
    //Lru磁盘缓存库
    implementation 'com.github.yangchong211.YCCommonLib:AppLruDisk:1.3.5'
    //Lru内存缓存库
    implementation 'com.github.yangchong211.YCCommonLib:AppLruCache:1.3.5'
    //fragment生命周期监听库
    implementation 'com.github.yangchong211.YCCommonLib:FragmentManager:1.3.5'
    //反射工具库
    implementation 'com.github.yangchong211.YCCommonLib:ReflectionLib:1.3.5'
    //App启动优化库
    implementation 'com.github.yangchong211.YCCommonLib:ParallelTaskLib:1.3.5'
    ```


### 04.Activity栈管理库
- 非常好用的activity任务栈管理库，自动化注册。完全解耦合的activity栈管理，拿来即可用，或者栈顶Activity，移除添加，推出某个页面，获取应用注册Activity列表等，可以注册监听某个页面的生命周期，小巧好用。
    ``` java
    //添加 activity
    ActivityManager.getInstance().add(this);
    //移除 activity
    ActivityManager.getInstance().remove(this);
    //结束指定的Activity
    ActivityManager.getInstance().finish(this);
    //结束所有Activity
    ActivityManager.getInstance().finishAll();
    //退出应用程序。先回退到桌面，然后在杀死进程
    ActivityManager.getInstance().appExist();
    //这个是监听目标Activity的生命周期变化
    ActivityManager.getInstance().registerActivityLifecycleListener(
            CommonActivity.class,new ActivityLifecycleListener(){
                @Override
                public void onActivityCreated(@Nullable Activity activity, Bundle savedInstanceState) {
                    super.onActivityCreated(activity, savedInstanceState);
                }
            });
    //移除栈顶的activity
    ActivityManager.getInstance().pop();
    //获取栈顶的Activity
    Activity activity = ActivityManager.getInstance().peek();
    //判断activity是否处于栈顶
    ActivityManager.getInstance().isActivityTop(this,"MainActivity");
    //返回AndroidManifest.xml中注册的所有Activity的class
    ActivityManager.getInstance().getActivitiesClass(
            this, AppUtils.getAppPackageName(),null);
    ```



### 05.通用存储库
- 通用存储库，支持二级缓存，LRU缓存，磁盘缓存(可以使用sp，mmkv，或者DiskLruCache)。不管你使用那种方式的存储，都是一套通用的api，使用几乎是零成本。
- 第一步：通用存储库初始化
    ``` java
    CacheConfig.Builder builder = CacheConfig.Companion.newBuilder();
    //设置是否是debug模式
    CacheConfig cacheConfig = builder.debuggable(BuildConfig.DEBUG)
            //设置外部存储根目录
            .extraLogDir(null)
            //设置lru缓存最大值
            .maxCacheSize(100)
            //内部存储根目录
            .logDir(null)
            //创建
            .build();
    CacheInitHelper.INSTANCE.init(MainApplication.getInstance(),cacheConfig);
    //最简单的初始化
    //CacheInitHelper.INSTANCE.init(CacheConfig.Companion.newBuilder().build());
    ```
- 第二步：存储数据和获取数据
    ```
    //存储数据
    dataCache.saveBoolean("cacheKey1",true);
    dataCache.saveFloat("cacheKey2",2.0f);
    dataCache.saveInt("cacheKey3",3);
    dataCache.saveLong("cacheKey4",4);
    dataCache.saveString("cacheKey5","doubi5");
    dataCache.saveDouble("cacheKey6",5.20);
    
    //获取数据
    boolean data1 = dataCache.readBoolean("cacheKey1", false);
    float data2 = dataCache.readFloat("cacheKey2", 0);
    int data3 = dataCache.readInt("cacheKey3", 0);
    long data4 = dataCache.readLong("cacheKey4", 0);
    String data5 = dataCache.readString("cacheKey5", "");
    double data6 = dataCache.readDouble("cacheKey5", 0.0);
    ```
- 第三步：一些存储说明
    - 关于设置磁盘缓存的路径，需要注意一些问题。建议使用该库默认的路径
    ``` java
    /**
     * log路径，通常这个缓存比较私有的内容
     * 比如sp，mmkv，存储的用户数据
     * 内部存储根目录，举个例子：
     * file:data/user/0/包名/files
     * cache:/data/user/0/包名/cache
     */
    val logDir: String?
    
    /**
     * 额外的log路径，通常缓存一些不私密的内存
     * 比如缓存图片，缓存视频，缓存下载文件，缓存日志等
     *
     * 外部存储根目录，举个例子
     * files:/storage/emulated/0/Android/data/包名/files
     * cache:/storage/emulated/0/Android/data/包名/cache
     */
    val extraLogDir: File?
    ```


### 06.Log日志打印库
- 通用日志库框架，专用LogCat工具，主要功能全局配置log输出, 个性化设置Tag，可以设置日志打印级别，支持打印复杂对象，可以实现自定义日志接口，支持简化版本将日志写入到文件中。小巧好用！
- 第一步：初始化操作
    ``` java
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
    ```
- 第二步：使用Log日志，十分简单，如下所示
    ``` java
    //自己带有tag标签
    AppLogHelper.d("MainActivity: ","debug log");
    //使用全局tag标签
    AppLogHelper.d("MainActivity log info no tag");
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
    ```



### 07.App重新启动库
- 背景说明：前项目中切换环境，崩溃重启需要重新启动功能。该库支持多种方式，小巧好用！
- 第一种方式，开启一个新的服务KillSelfService，用来重启本APP。
    ``` java
    RestartAppHelper.restartApp(this,RestartFactory.SERVICE);
    ```
- 第二种方式，使用闹钟延时，使用PendingIntent延迟意图，然后重启app
    ``` java
    RestartAppHelper.restartApp(this,RestartFactory.ALARM);
    ```
- 第三种方式，检索获取项目中LauncherActivity，然后设置该activity的flag和component启动app
    ``` java
    RestartAppHelper.restartApp(this,RestartFactory.MAINIFEST);
    ```


### 08.通用工具类库
- ToolUtils 是一个 Android 工具库。便于开发人员，快捷高效开发需求。
- 常用的基本上都有，还包括图片压缩，日历事件，异常上报工具，网络，手机方向监听，防爆力点击等工具类。
    ``` java
    FastClickUtils              防爆力点击，可以自定义设置间距时间
    PerfectClickListener        避免在1秒内出发多次点击
    AppFileUtils                file文件工具类
    FileShareUtils              file文件分享工具类
    ScreenShotUtils             监听屏幕被截图事件
    SensorManagerUtils          手机系统方向监听器工具类
    AppNetworkUtils             网络工具类
    AppProcessUtils             进程工具类，高效获取进程名称
    CompressUtils               图片压缩工具类
    ExceptionReporter           异常上报工具类
    OnceInvokeUtils             避免多次执行工具类
    StatusBarUtils              状态栏工具类
    
    //还有很多其他常用工具类，这里就不一一罗列呢
    ……
    ```


### 09.通用基类封装库


### 10.反射工具类库


### 11.Intent封装库
- 通用打印Intent对象内容到log日志栏中，支持普通intent和延迟PendingIntent。超级方便检查，可以打印action，category，data，flags，extras等等
    ``` java
    //打印intent所有的数据
    IntentLogger.print("intent test : ", intent)
    //打印intent中component
    IntentLogger.printComponentName("intent component : " , intent)
    //打印intent中extras参数
    IntentLogger.printExtras("intent test : ", intent)
    //打印intent中flags参数
    IntentLogger.printFlags("intent test : ", intent)
    
    //PendingIntent
    //打印intent所有的数据
    PendingIntentLogger.print("intent test : ", intent)
    //打印intent中content
    PendingIntentLogger.printContentIntent("intent content : " , intent)
    //打印intent的tag
    PendingIntentLogger.printTag("intent tag : " , intent)
    ```


### 12.基础接口库
- 背景说明：由于组件化开发中有很多基础组件，由于某些需求，需要统计一些事件，异常上报到平台上，获取控制降级，自定义日志打印等，因此采用接口回调方式实现
- IEventTrack，event事件接口，一般用于特殊事件上报作用
- IExceptionTrack，异常事件接口，一般可以用在组件库中catch捕获的时候，上报日志到服务平台操作
- ILogger，log自定义日志接口，一般用于组件库日志打印，暴露给外部开发者
- IMonitorToggle，AB测试开关接口，也可以叫降级开关，一般用于组件库某功能降级操作，暴露给开发者设置



### 13.Fragment周期监听
- 如何监听Fragment各个生命周期回调？
    - FragmentManager#registerFragmentLifecycleCallbacks()注册回调?
    - 实现LifecycleObserver接口？
    - 继承BaseFragment？
- 几种方式优缺点分析
    - XxxLifecycleCallbacks方式优点：可以统一监听所有Fragment，方便管理；不侵入已有代码，耦合性较低；可以操作第三方Fragment的声明周期。
    - XxxLifecycleCallbacks方式缺点：仅能在相应周期回调后操作，是这一方法唯一的缺点。
    - BaseFragment方式：这一方法就是实现一个基类Fragment，在里面实现一些通用的方法，让项目中的fragment都继承它。但是该方法缺点也很明显，由于Java的继承机制只允许一个类拥有唯一父类，所以该方法无法用于第三方框架也使用该方式的场景，并且侵入性强。
- api调用如下所示，直接拿来用即可
    ``` java
    //一般在onCreate的方法中注册
    FragmentManager.Companion.getInstance().registerActivityLifecycleListener(activity,lifecycleListener);
    //一般在onDestroy的方法中解绑注册
    FragmentManager.Companion.getInstance().unregisterActivityLifecycleListener(activity,lifecycleListener);
    ```
- 关于fragment生命周期回调，这个FragmentLifecycleListener是一个抽象类，包含了绝大多数常见的方法，可根据自己的需求自由实现
    ``` java
    private final FragmentLifecycleListener lifecycleListener = new FragmentLifecycleListener() {
        @Override
        public void onFragmentCreated(@NotNull androidx.fragment.app.FragmentManager fm, @NotNull Fragment f, @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
            super.onFragmentCreated(fm, f, savedInstanceState);
        }
    
        @Override
        public void onFragmentDestroyed(@NotNull androidx.fragment.app.FragmentManager fm, @NotNull Fragment f) {
            super.onFragmentDestroyed(fm, f);
        }
    };
    ```



### 14.异常&事件&日志上报库
- 关于事件埋点，异常上报到APM，日志打印到file文件等。这些比较重的逻辑一般写在app壳工程，而想统计一些基础库中的埋点事件，catch异常上报APM，该怎么做？
- lib封装库尽可能小和功能独立，不建议依赖APM，埋点库等。我想要一个功能库，你却给我一个大礼包库，想想这个是不是太难了。
- api调用如下所示，直接拿来用即可。下面这些调用可以用在lib库中，特轻量级上报
    ``` java
    //上报日志
    LoggerReporter.report("DiskLruCacheHelper" , "lru disk file path : " + directory.getPath());
    LoggerReporter.report("DiskLruCacheHelper clear cache");
    //上报异常
    ExceptionReporter.report("Unable to get from disk cache", e);
    ExceptionReporter.report(ioe);
    //上报event事件，通常用来埋点
    EventReporter.report("initCacheSuccess")
    EventReporter.report("initCacheSuccess",map)
    ```
- 需要在壳工程代码中，添加具体实现类。代码如下所示：
    ``` java
    //LoggerReporter，ExceptionReporter，EventReporter都是类似的
    public class LoggerReporterImpl extends LoggerReporter {
        @Override
        protected void reportLog(String eventName) {
            
        }
    
        @Override
        protected void reportLog(String eventName, String message) {
    
        }
    }
    ```
- 最后在壳工程中初始化一下。
    ``` java
    ExceptionReporter.setExceptionReporter(ExceptionHelperImpl())
    LoggerReporter.setEventReporter(LoggerReporterImpl())
    EventReporter.setEventReporter(EventReporterImpl())
    ```

