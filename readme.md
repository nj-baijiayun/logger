使用统一的Logger主要为了方便大家在开发过程中，快速定位问题

## 快速集成
在工程目标的`build.gradle`文件添加仓库地址
```
allprojects {
    repositories {
        google()
        jcenter()
        maven {
            //release仓库地址
            url = 'http://172.20.2.114:8081/repository/maven-releases/'
        }
        maven {
            //snapshot仓库地址
            url = 'http://172.20.2.114:8081/repository/maven-releases/'
        }
    }
}

```
在dependencies下添加依赖
```
dependencies {
    //当前最新版本为1.0.5
    implementation 'com.nj.baijiayun:logger:1.0.5'
}
```
当前最新版本为1.0.5 [版本说明](./changelog.md)


## 使用说明

### 初始化

在使用Logger功能以前需要进行初始化

```java
//设置log开关
Logger.setEnable(BuildConfig.DEBUG);
//设置log打印等级，最低为Log.INFO,最高为Log.ASSERT
Logger.setPriority(Logger.MIN_LOG_PRIORITY);
//设置全局Tag
Logger.setTag("[Logger]");
//初始化
Logger.init();
```
### 使用

初始化完成之后可以使用`Logger`下的各种方法打印log

#### 普通Log
```java
Logger.d("test log");
```
结果
```
2019-04-30 16:13:50.866 28749-28749/com.nj.baijiayun.logger D/Logger: ┌────────────────────────────────────────────────────────────────────────────────────────────────────────────────
2019-04-30 16:13:50.867 28749-28749/com.nj.baijiayun.logger D/Logger: │ MainActivity$1.onClick  (MainActivity.java:50)
2019-04-30 16:13:50.867 28749-28749/com.nj.baijiayun.logger D/Logger: ├┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄
2019-04-30 16:13:50.867 28749-28749/com.nj.baijiayun.logger D/Logger: │ test log
2019-04-30 16:13:50.867 28749-28749/com.nj.baijiayun.logger D/Logger: └────────────────────────────────────────────────────────────────────────────────────────────────────────────────

```

#### 自定义Tag
```java
Logger.dTag("tag", "test log");
```
结果

```
2019-04-30 16:17:14.758 7257-7257/com.nj.baijiayun.logger D/[Logger]-tag: ┌────────────────────────────────────────────────────────────────────────────────────────────────────────────────
2019-04-30 16:17:14.759 7257-7257/com.nj.baijiayun.logger D/[Logger]-tag: │ MainActivity$1.onClick  (MainActivity.java:50)
2019-04-30 16:17:14.759 7257-7257/com.nj.baijiayun.logger D/[Logger]-tag: ├┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄
2019-04-30 16:17:14.759 7257-7257/com.nj.baijiayun.logger D/[Logger]-tag: │ test log
2019-04-30 16:17:14.759 7257-7257/com.nj.baijiayun.logger D/[Logger]-tag: └────────────────────────────────────────────────────────────────────────────────────────────────────────────────
```

#### 网络请求log
```java
//在创建OkHttpClient时需要从Logger获取OkHttpClient
final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(Logger.getOkHttpInterceptor())
                .build();
```
结果
```

2019-04-30 16:20:18.683 7257-7622/com.nj.baijiayun.logger I/[Logger]: ┌────────────────────────────────────────────────────────────────────────────────────────────────────────────────
2019-04-30 16:20:18.683 7257-7622/com.nj.baijiayun.logger I/[Logger]: │--> GET https://zywx.api.zhiyun88.com/api/app/courseInfo/basis_id=51/st=0
2019-04-30 16:20:18.683 7257-7622/com.nj.baijiayun.logger I/[Logger]: │--> END GET
2019-04-30 16:20:18.684 7257-7622/com.nj.baijiayun.logger I/[Logger]: └────────────────────────────────────────────────────────────────────────────────────────────────────────────────
2019-04-30 16:20:19.398 7257-7622/com.nj.baijiayun.logger I/[Logger]: ┌────────────────────────────────────────────────────────────────────────────────────────────────────────────────
2019-04-30 16:20:19.398 7257-7622/com.nj.baijiayun.logger I/[Logger]: │<-- 200 https://zywx.api.zhiyun88.com/api/app/courseInfo/basis_id=51/st=0 (714ms)
2019-04-30 16:20:19.399 7257-7622/com.nj.baijiayun.logger I/[Logger]: │server: nginx
2019-04-30 16:20:19.399 7257-7622/com.nj.baijiayun.logger I/[Logger]: │content-type: application/json
2019-04-30 16:20:19.399 7257-7622/com.nj.baijiayun.logger I/[Logger]: │x-powered-by: PHP/7.2.6
2019-04-30 16:20:19.399 7257-7622/com.nj.baijiayun.logger I/[Logger]: │cache-control: private, must-revalidate
2019-04-30 16:20:19.400 7257-7622/com.nj.baijiayun.logger I/[Logger]: │date: Tue, 30 Apr 2019 08:20:19 GMT
2019-04-30 16:20:19.400 7257-7622/com.nj.baijiayun.logger I/[Logger]: │etag: "b412dc7ec335d60e8abe43ef0c268d30dbc38e20"
2019-04-30 16:20:19.400 7257-7622/com.nj.baijiayun.logger I/[Logger]: │set-cookie: laravel_session=Kkpsoq4oI7baZlQhXGvgqUxTJsHsLDkEsVe3XGNj; expires=Tue, 30-Apr-2019 10:20:19 GMT; Max-Age=7200; path=/; httponly
2019-04-30 16:20:19.403 7257-7622/com.nj.baijiayun.logger I/[Logger]: │
2019-04-30 16:20:19.449 7257-7622/com.nj.baijiayun.logger I/[Logger]: │{
        "code": 200,
        "msg": "成功",
        "data": {
            "spellGroup": null
        }
    }
2019-04-30 16:20:19.450 7257-7622/com.nj.baijiayun.logger I/[Logger]: │<-- END HTTP (1989-byte body)
2019-04-30 16:20:19.450 7257-7622/com.nj.baijiayun.logger I/[Logger]: └────────────────────────────────────────────────────────────────────────────────────────────────────────────────
```

### crash log
结果
```
2019-04-30 16:21:31.377 7257-7257/? E/[Logger]: ┌────────────────────────────────────────────────────────────────────────────────────────────────────────────────
2019-04-30 16:21:31.378 7257-7257/? E/[Logger]: │ CrashLoggerHandler.uncaughtException  (CrashLoggerHandler.java:24)
2019-04-30 16:21:31.378 7257-7257/? E/[Logger]: ├┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄
2019-04-30 16:21:31.378 7257-7257/? E/[Logger]: │ java.lang.NullPointerException: test crash
2019-04-30 16:21:31.378 7257-7257/? E/[Logger]: │ 	at com.nj.baijiayun.app.MainActivity$3.onClick(MainActivity.java:74)
2019-04-30 16:21:31.378 7257-7257/? E/[Logger]: │ 	at android.view.View.performClick(View.java:6387)
2019-04-30 16:21:31.378 7257-7257/? E/[Logger]: │ 	at android.view.View$PerformClick.run(View.java:25217)
2019-04-30 16:21:31.378 7257-7257/? E/[Logger]: │ 	at android.os.Handler.handleCallback(Handler.java:790)
2019-04-30 16:21:31.378 7257-7257/? E/[Logger]: │ 	at android.os.Handler.dispatchMessage(Handler.java:99)
2019-04-30 16:21:31.379 7257-7257/? E/[Logger]: │ 	at android.os.Looper.loop(Looper.java:187)
2019-04-30 16:21:31.379 7257-7257/? E/[Logger]: │ 	at android.app.ActivityThread.main(ActivityThread.java:7021)
2019-04-30 16:21:31.379 7257-7257/? E/[Logger]: │ 	at java.lang.reflect.Method.invoke(Native Method)
2019-04-30 16:21:31.379 7257-7257/? E/[Logger]: │ 	at com.android.internal.os.RuntimeInit$MethodAndArgsCaller.run(RuntimeInit.java:514)
2019-04-30 16:21:31.379 7257-7257/? E/[Logger]: │ 	at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:888)
2019-04-30 16:21:31.379 7257-7257/? E/[Logger]: └────────────────────────────────────────────────────────────────────────────────────────────────────────────────
```